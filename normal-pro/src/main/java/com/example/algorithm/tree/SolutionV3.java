package com.example.algorithm.tree;

import com.example.algorithm.tree.common.ListNode;
import com.example.algorithm.tree.common.TreeNode;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SolutionV3 {

    // 二叉树构建 前序遍历和中序遍历
    int preIdx = 0;
    int[] preOrder;
    HashMap<Integer, Integer> idxMap = new HashMap<Integer, Integer>();
    public TreeNode bstFromPreOrder(int[] preOrder) {
        this.preOrder = preOrder;
        // 拷贝，并将拷贝到的数组排序
        int [] inorder = Arrays.copyOf(preOrder, preOrder.length);
        Arrays.sort(inorder);
        // 将排序的数组值 ids存储到HashMap中
        int idx = 0;
        for (Integer val : inorder) {
            idxMap.put(val, idx++);
        }
        return helper(0, inorder.length);
    }

    public TreeNode helper(int inLeft, int inRight) {
        if (inLeft == inRight) {
            return null;
        }
        // 构建root节点
        int rootVal = preOrder[preIdx];
        TreeNode root = new TreeNode(rootVal);
        int index = idxMap.get(rootVal);
        preIdx++;
        root.left = helper(inLeft, index);
        root.right = helper(index + 1, inRight);
        return root;
    }




    /**
     * 给定一颗二叉树，其中每个节点都含有一个整数数值（该值或正或负）
     * 设计一个算法，打印节点数值总和等于某个给定值的所有路径的数量。
     * 注意，路径不一定非得从二叉树的根节点或叶子节点开始或结束，但是其方向必须向下（只能从父节点指向子节点）
     * 示例：
     *  给定如下二叉树，以及目标和sum=22
     *                              5
     *                            /   \
     *                           4     8
     *                         /     /   \
     *                        11    13    4
     *                       / \         / \
     *                      7  2        5  1
     *  返回：3
     *  解释：和为22的路径有：[5,4,11,2],[5,8,4,5],[4,11,7]
     *  提示：节点总数<=10000
     * @param root
     * @param sum
     * @return
     */
    private int ret = 0;
    public int pathSum(TreeNode root, int sum) {
        preOrder(root, sum);
        return ret;
    }

    private void dfs(TreeNode root, int sum) {
        if (root == null) {
            return;
        }
        if (root.val == sum) {
            ret++;
        }
        dfs(root.left, sum - root.val);
        dfs(root.right, sum - root.val);
    }

    private void preOrder(TreeNode root, int sum) {
       if (root == null) {
           return;
       }
       dfs(root, sum);
       preOrder(root.left, sum);
       preOrder(root.right, sum);
    }


    /**
     * 合法二叉搜索树
     * 实现一个函数，检查一颗二叉树是否为二叉搜索树。
     * 示例1：
     * 输入：
     *              2
     *            /  \
     *           1    3
     * 输出：true
     * 示例2：
     * 输入：
     *                  5
     *                /   \
     *               1    4
     *                   / \
     *                  3   6
     * 输出：false
     * 解释：输入为：[5,1,4,null,null,3,6]
     * 根节点的值为5，但是其右子节点值为4.
     * @param root
     * @return
     */
    // 合法二叉搜索树的定义：
    // 左子节点的值小于等于根节点的值
    // 右子节点的值大于等于根节点的值

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode root, long min, long max) {
        if (root == null) {
            return true;
        }
        return (
                root.val > min &&
                root.val < max &&
                isValidBST(root.left, min, root.val) &&
                isValidBST(root.right, root.val, max)
        );
    }

    /**
     * 完全二叉树的节点个数
     * 给出一个完全二叉树，求出该二叉树的节点个数
     * 说明：完全二叉树的定义如下：在完全二叉树中，除了最底层的节点没有填满外，其他层的节点数都达到了最大值。
     * 并且最下面的节点都集中在树种最左边的位置。若是最底层为h层，则该层包含个节点。
     * 输入:
     *     1
     *    / \
     *   2   3
     *  / \  /
     * 4  5 6
     *
     * 输出: 6
     * @param root
     * @return
     */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int d = computeDepth(root);
        if (d == 0) {
            return 1;
        }
        int left = 1;
        int right = (int) Math.pow(2, d) - 1;
        int pivot;
        while (left <= right) {
            pivot = left + (right - left) / 2;
            if (exists(pivot, d, root)) {
                left = pivot + 1;
            } else {
                right = pivot - 1;
            }
        }
        return (int)Math.pow(2, d) - 1 + left;
    }

    public int computeDepth(TreeNode node) {
        int d = 0;
        while (node.left != null) {
            node = node.left;
            ++d;
        }
        return d;
    }

    public boolean exists(int idx, int d, TreeNode node) {
        int left = 0, right = (int)Math.pow(2, d) - 1;
        int pivot;
        for(int i = 0; i < d; ++i) {
            pivot = left + (right - left) / 2;
            if (idx <= pivot) {
                node = node.left;
                right = pivot;
            }
            else {
                node = node.right;
                left = pivot + 1;
            }
        }
        return node != null;
    }

    /**
     * 有序列表转换为二叉搜索树
     * 给定一个单链表，其中的元素按照升序排序，将其转换为高度平衡的二叉搜索树。
     * 本题中，一个高度平衡二叉搜索树是指   一个二叉树每个节点的左右两个子树的高度差的绝对值不超过1。
     * 示例:
     *      给定的有序链表： [-10, -3, 0, 5, 9],
     *      一个可能的答案是：[0, -3, 9, -10, null, 5], 它可以表示下面这个高度平衡二叉搜索树：
     *       0
     *      / \
     *    -3   9
     *    /   /
     *  -10  5
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head) {
        return buildTree(head, null);
    }

    public TreeNode buildTree(ListNode left, ListNode right) {
        if (left == right) {
            return null;
        }
        ListNode mid = getMid(left, right);
        TreeNode root = new TreeNode(mid.val);
        root.left = buildTree(left, mid);
        root.right = buildTree(mid.next, right);
        return root;
    }

    public ListNode getMid(ListNode left, ListNode right) {
        ListNode slow = left;
        ListNode fast = left;
        while (fast != right && fast.next != right) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 奇偶链表
     * 给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。
     * 请注意：这里的奇数节点和偶数节点指的是节点编号的奇偶性。而非节点值的奇偶性。
     * 请尝试使用原地算法完成。你的算法的空间复杂度应为 O(1)，时间复杂度应为 O(nodes)，nodes 为节点总数。
     * @param head
     * @return
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return head;
        }
        // 奇数节点
        ListNode odd = head;
        // 偶数节点
        ListNode evenHead = head.next;
        ListNode even = evenHead;
        while (even != null && even.next != null) {
            // 首先更新odd的next节点
            odd.next = even.next;
            // 然后更新odd节点
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    /**
     * 反转链表
     * 反转从位置m到n的链表，请使用一趟扫描完成反转。
     * 1 ≤ m ≤ n ≤ 链表长度。
     * 示例：
     *    输入: 1->2->3->4->5->NULL, m = 2, n = 4
     *    输出: 1->4->3->2->5->NULL
     * @param head
     * @param m
     * @param n
     * @return
     */
    private boolean stop;

    private ListNode left;

    public void recurseAndReverse(ListNode right, int m, int n) {
        if (n == 1) {
            return;
        }
        right = right.next;
        if (m > 1) {
            this.left = this.left.next;
        }
        this.recurseAndReverse(right, m - 1, n - 1);
        if (this.left == right || right.next == this.left) {
            this.stop = true;
        }
        if (!this.stop) {
            int t = this.left.val;
            this.left.val = right.val;
            right.val = t;
            this.left = this.left.next;
        }
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        this.left = head;
        this.stop = false;
        this.recurseAndReverse(head, m, n);
        return head;
    }


    public int[] shuffle(int[] nums, int n) {
        int[] res = new int[nums.length];
        for(int i = 0; i < n; i ++) {
            res[2 * i] = nums[i];
            res[2 * i + 1] = nums[n+i];
        }
        return res;
    }


    /**
     * 一维数组的动态和
     * 给你一个数组nums。数组动态和的计算公式为  runningSum[i] = sum(nums[0]…nums[i]) 。
     * 输入：nums = [1,2,3,4]
     * 解释：动态和计算过程为 [1, 1+2, 1+2+3, 1+2+3+4] 。
     * @param nums
     * @return
     */
    public int[] runningSum(int[] nums) {
        for(int i = 1; i < nums.length; i ++) {
            nums[i] = nums[i] + nums[i - 1];
        }
        return nums;
    }


    public ListNode partition(ListNode head, int x) {
        int maxSize = 10;
        int coreSize = 10;
        long keepAliveTime = 100;
        TimeUnit secondsUnit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>();
        SelfThreadFactory threadFactory = new SelfThreadFactory();
        SelfRejectedExecutionHandler rejectHandler = new SelfRejectedExecutionHandler();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(coreSize, maxSize, keepAliveTime, secondsUnit, blockingQueue, threadFactory, rejectHandler);
        executor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        return null;
        // 使用redis作为分布式锁，redis的java客户端。
    }


    public static class SelfThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            return null;
        }
    }

    public static class SelfRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        }
    }

    /**
     * 长按键入
     * 你的朋友正在使用键盘输入他的名字name，偶尔在输入字符c时，按键可能会被长按。而字符可能被输入一次或多次。
     * 你将会检查键盘输入的字符typed，如果它对应的可能是你朋友的名字（其中一些字符可能会被长按），那么就返回true。
     * 示例1：
     *      输入：name="alex", typed="aaleex"; 输出true。
     *      解释：'alex'中的'a'和'e'被长按
     * 示例2：
     *      输入:name="saeed", typed="ssaaedd"； 输出false。
     *      解释：'e'一定需要被键入两次，但在typed的输出中不是这样
     * @param name
     * @param typed
     * @return
     */
    public boolean isLongPressedName(String name, String typed) {
        int i = 0;
        int j = 0;
        while (j < typed.length()) {
            if (i < name.length() && name.charAt(i) == typed.charAt(j)) {
                i ++;
                j ++;
            } else if (j > 0 && typed.charAt(j) == typed.charAt(j - 1)) {
                j ++;
            } else {
                return false;
            }
        }
        return i == name.length();
    }

    /**
     * 合并排序的数组
     * 给定两个排序后的数组A和B，其中A的末端有足够的缓冲空间容纳B。
     * 编写一个方法，将B合并入A并排序。
     * 初始化A和B的元素数量分别为m和n。
     * 示例:
     *     输入:
     *          A=[1,2,3,0,0,0], m=3
     *          B=[2,5,6], n=3
     *     输出:
     *          [1,2,2,3,5,6]
     * @param A
     * @param m
     * @param B
     * @param n
     */
    public void merge(int[] A, int m, int[] B, int n) {
        int pa = 0, pb = 0;
        int[] sorted = new int[m + n];
        int cur;
        while (pa < m || pb < n) {
            if (pa == m) {
                cur = B[pb++];
            } else if (pb == n) {
                cur = A[pa++];
            } else if (A[pa] < B[pb]) {
                cur = A[pa++];
            } else {
                cur = B[pb++];
            }
            sorted[pa + pb - 1] = cur;
        }
        for (int i = 0; i != m + n; ++i) {
            A[i] = sorted[i];
        }
    }

    /**
     * 比较含退格的字符串
     * 给定S和T两个字符串，当他们分别被输入到空白的文本编辑器后，判断二者是否相等，并返回结果。#代表退格字符。
     * 注意：如果对空文本输入退格字符，文本继续为空。
     * 示例1：
     *      输入：S="ab#c", T="ad#c"   输出:true  解释:S和T都会变成"ac"
     *      输入: S="ab##", T="c#d#"   输出:true  解释:S和T都会变成""
     *      输入: S="a##c", T="#a#c"   输出:true  解释:S和T都会变成"c"
     *      输入: S="a#c", T="b"       输出:false
     * @param S
     * @param T
     * @return
     */
    public boolean backspaceCompare(String S, String T) {
        return build(S).equals(build(T));
    }

    public String build(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i ++) {
            char ch = str.charAt(i);
            if (ch != '#') {
                sb.append(ch);
            } else {
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 划分字母区间
     * 字符串S由小写字母组成。我们要把这个字符串划分为尽可能多的片段，同一个字母最多出现在一个片段中。
     * 返回一个表示每个字符串片段长度的列表。
     * 示例
     *      输入： S = "ababcbacadefegdehijhklij"
     *      输出：[9,7,8]
     * 解释：
     *      划分结果为 "ababcbaca", "defegde", "hijhklij"。
     * 每个字母最多出现在一个片段中。
     * 像 "ababcbacadefegde", "hijhklij" 的划分是错误的，因为划分的片段数较少。
     * @param S
     * @return
     */
    public List<Integer> partitionLabels(String S) {
        // 记录每个字符最后一次出现的下标
        int[] last = new int[26];
        int length = S.length();
        for (int i = 0; i < length; i++) {
            last[S.charAt(i) - 'a'] = i;
        }
        List<Integer> partition = new ArrayList<Integer>();
        int start = 0;
        int end = 0;
        for (int i = 0; i < length; i++) {
            // end和lastEnd对比
            end = Math.max(end, last[S.charAt(i) - 'a']);
            if (i == end) {
                partition.add(end - start + 1);
                start = end + 1;
            }
        }
        return partition;
    }

    /**
     * 按摩师
     * 一个有名的按摩师会收到源源不断的预约请求，每个预约都可以选择接或者不接。
     * 在每次预约服务之间要有休息时间，因此她不能接收相邻的预约。给定一个预约的请求序列，
     * 替按摩师找到最优的预约集合(总预约时间最长)，返回总的分钟数。
     * 示例1：
     *  输入:[1,2,3,1] 输出:4 选择1号预约和3号预约，总时长=1+3=4
     *  输入:[2,7,9,3,1] 输出:12 选择1号 3号 5号预约，总时长=2+9+1=12
     *  输入:[2,1,4,5,3,1,1,3] 输出:12 选择1号 3号 5号和8号预约，总时长=2+4+3+3=12
     * @param nums
     * @return
     */
    public int massage(int[] nums) {

        return -1;
    }

    public int massageV1(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int dp0 = 0;
        int dp1 = nums[0];
        for (int i = 1; i < n; ++i) {
            // 计算 dp[i][0]
            int tdp0 = Math.max(dp0, dp1);
            // 计算 dp[i][1]
            int tdp1 = dp0 + nums[i];
            // 用 dp[i][0] 更新 dp_0
            dp0 = tdp0;
            // 用 dp[i][1] 更新 dp_1
            dp1 = tdp1;
        }
        return Math.max(dp0, dp1);
    }
}
