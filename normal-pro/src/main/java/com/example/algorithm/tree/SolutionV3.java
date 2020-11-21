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

    public static void main(String[] args) {
        int[] inorder = {8, 5, 1, 7, 10, 12};
        int[] sort = Arrays.copyOf(inorder, inorder.length);
        Arrays.sort(sort);
        for (int i = 0; i < sort.length; i++) {
            System.err.print(sort[i] + "\t");
        }
    }

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

}
