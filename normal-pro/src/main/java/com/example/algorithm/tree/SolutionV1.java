package com.example.algorithm.tree;

import com.example.algorithm.tree.common.TreeNode;

import java.util.*;

public class SolutionV1 {

    /**
     * 给你二叉搜索树的根节点root,同时给定最小边界low和最大边界high。通过修剪二叉搜索树，使得所有的节点值在[low,high]中。
     * 修剪树不应该改变保留在树中的元素的相对结构(即，如果没有被移除，原有的父代子代关系都应当保留)。可以证明，存在唯一的答案。
     * 所以结果应当返回修剪好的二叉搜索树的新的根节点。注意：根节点可能会根据给定的边界发生改变。
     * 示例1：
     *              1               1
     *            /   \               \
     *           0     2                2
     *  输入 root=[1, 0, 2], low=1, high=2
     *  输出 [1, null, 2]
     *  示例2:
     *                      3                          3
     *                    /   \                       /
     *                   0     4                     2
     *                   \                          /
     *                    2                        1
     *                   /
     *                  1
     *  输入 root = [3, 0, 4, null, 2, null, null, 1], low=1,high=3
     *  输出 [3, 2, null, 1]
     *  示例3:
     *  输入: root=[1] low=1, high=2
     *  输出: [1]
     *  示例4:
     *  输入: root=[1, null, 2], low=1,high=3
     *  输出：[1, null, 2]
     *  示例5:
     *  输入:root=[1, null, 2], low=2,high=4
     *  输出:[2]
     *  提示：
     *      树中节点数在范围[1, 10^4]内
     *      0 <= Node.val <= 10^4
     *      树中每个节点的值都是唯一的
     *      题目数据保证输入是一颗有效的二叉搜索树
     *      0 <= low <= high <= 10^4
     * @param root
     * @param low
     * @param high
     * @return
     */
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) {
            return root;
        }
        if (root.val > high) {
            return trimBST(root.left, low, high);
        }
        if (root.val < low) {
            return trimBST(root.right, low, high);
        }
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);
        return root;
    }

    /**
     * 在二叉树中，根节点位于深度0处，每个深度为k的节点的子节点位于深度k+1处。
     * 如果二叉树的两个节点深度相同，但父节点不同，则它们是一对堂兄弟节点。
     * 我们给出了具有唯一值的二叉树的根节点root,以及树中两个不同节点的值x和y。
     * 只有与值x和y对应的节点是唐兄弟节点时，才返回true。否则返回false。
     * 示例1
     *              1
     *            /   \
     *           2     3
     *          /
     *         4
     * 输入：root=[1, 2, 3, 4] x=4, y=3
     * 输出: false
     * 示例2
     *                  1
     *                /   \
     *               2     3
     *               \      \
     *                4      5
     * 输入: root=[1,2,3,null,4,null,5] x=5 y=4
     * 输出：true
     * 示例3
     *                      1
     *                    /   \
     *                   2     3
     *                   \
     *                   4
     * 输入: root=[1,2,3,null,4], x=2, y=3
     * 输出: false
     * 提示：
     *      二叉树的节点数介于2到100之间
     *      每个节点的值都是唯一的，范围为1到100的整数
     * @param root
     * @param x
     * @param y
     * @return
     */
    Map<Integer, Integer> depth;
    Map<Integer, TreeNode> parent;
    public boolean isCousins(TreeNode root, int x, int y) {
        depth = new HashMap<>();
        parent = new HashMap<>();
        dfs(root, null);
        return (depth.get(x) == depth.get(y) && parent.get(x) != parent.get(y));
    }

    private void dfs(TreeNode node, TreeNode par) {
        if (node != null) {
            depth.put(node.val, par != null ? depth.get(par.val) + 1 : 0);
            parent.put(node.val, par);
            dfs(node.left, node);
            dfs(node.right, node);
        }
    }

    /**
     * 给定一个二叉搜索树和一个目标结果，如果BST中存在两个元素且他们的和等于给定的目标结果，则返回true。
     * 案例1
     * 输入：
     *                  5
     *                /   \
     *               3     6
     *             /  \     \
     *            2   4      7
     * target=9  输出:true
     * 案例2
     * 输入:
     *                  5
     *                /  \
     *               3    6
     *             /  \    \
     *            2   4     7
     * target=28 输出:false
     * @param root
     * @param k
     * @return
     */
    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>();
        return find(root, k, set);
    }


    private boolean find(TreeNode root, int k, Set<Integer> set) {
        if (root == null) {
            return false;
        }
        if (set.contains(k - root.val)) {
            return true;
        }
        set.add(root.val);
        return find(root.left, k, set) || find(root.right, k, set);
    }

    /**
     * 给定一颗二叉树，你需要计算它的直径长度。一颗二叉树的直径长度是任意两个节点路径长度中的最大值。
     * 这条路径可能穿过也可能不穿过根节点。
     * 示例：
     * 给定二叉树
     *                  1
     *                /   \
     *               2     3
     *             /  \
     *            4    5
     * 返回3，它的长度是路径[4,2,1,3]或者[5,2,1,3]
     * 注意：两节点之间的路径长度是以他们之间边的数目表示。
     * @param root
     * @return
     */
    int ans;
    public int diameterOfBinaryTree(TreeNode root) {
        ans = 1;
        depth(root);
        return ans - 1;
    }

    public int depth(TreeNode node) {
        // 访问到空节点了，返回0
        if (node == null) {
            return 0;
        }
        // 左儿子为根的子树的深度
        int L = depth(node.left);
        // 右儿子为根的子树的深度
        int R = depth(node.right);
        // 计算即L+R+1 并更新ans
        ans = Math.max(ans, L + R + 1);
        // 返回该节点为根的子树的深度
        return Math.max(L, R) + 1;
    }


    /**
     * 最小高度树
     * 给定一个有序整数数组，元素各不相同且按照升序排列。
     * 编写一个算法，创建一个高度最小的二叉树。
     * 示例：给定有序数组 [-10, -3, 0, 5, 9]
     * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
     *           0
     *          / \
     *        -3   9
     *        /   /
     *      -10  5
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0) {
            return null;
        }
        TreeNode node = new TreeNode(nums[nums.length / 2]);
        node.left = sortedArrayToBST(Arrays.copyOfRange(nums, 0, nums.length / 2));
        node.right = sortedArrayToBST(Arrays.copyOfRange(nums, nums.length / 2 + 1, nums.length));
        return node;
    }

    /**
     * 给定二叉搜索树(BST)的根节点和一个值。你需要在BST中找到值为给定值的节点。
     * 返回以该节点为根的子树。如果不存在，返回null。
     * 例如，给定二叉搜索树和值2
     *              4
     *            /   \
     *          2      7
     *         / \
     *        1   3
     * 你应该返回如下子树
     *          2
     *        /  \
     *       1    3
     * 在上述示例中，如果要找的值是 5，但因为没有节点值为 5，我们应该返回 NUL
     * @param root
     * @param val
     * @return
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null || root.val == val) {
            return root;
        }
        if (val < root.val) {
            return searchBST(root.left, val);
        } else {
            return searchBST(root.right, val);
        }
    }

    /**
     * 二叉搜索树的范围和
     * 给定二叉搜索树的根节点root,返回L和R(含)之间的所有节点的值的和。
     * 二叉搜索树保证具有唯一的值。
     * 例如：
     *      输入：root=[10, 5, 15, 3, 7, null, 18], L=7, R=15
     *                          10
     *                        /   \
     *                      5      15
     *                    / \       \
     *                   3   7       18
     *      输出：32
     * 示例 2：
     *      输入：root = [10,5,15,3,7,13,18,1,null,6], L = 6, R = 10
     *                          10
     *                        /   \
     *                       5     15
     *                     /  \   /  \
     *                    3   7  13  18
     *                  /    /
     *                 1    6
     * 输出：23
     * 提示：
     *  树中的结点数量最多为 10000 个。
     *  最终的答案保证小于 2^31。
     * @param root
     * @param L
     * @param R
     * @return
     */

    // 我们对树进行深度优先搜索，对于当前节点 node，如果 node.val 小于等于 L，那么只需要继续搜索它的右子树；如果 node.val 大于等于 R，那么只需要继续搜索它的左子树；
    // 如果 node.val 在区间 (L, R) 中，则需要搜索它的所有子树。
    //我们在代码中用递归和迭代的方法分别实现了深度优先搜索。
    int ans1;
    public int rangeSumBST(TreeNode root, int L, int R) {
        ans = 0;
        dfs(root, L, R);
        return ans1;
    }

    public void dfs(TreeNode node, int L, int R) {
        if (node == null) {
            return;
        }
        if (L <= node.val && node.val <= R) {
            dfs(node.left, L, R);
            ans += node.val;
            dfs(node.right, L, R);
        }
        if (node.val > R) {
            dfs(node.left, L, R);
        }
        if (node.val < L) {
            dfs(node.right, L, R);
        }

    }


    /**
     * 给定一个非空特殊的二叉树，每个节点都是正数，并且每个节点的子节点数量只能为2或0。
     * 若是一个节点有两个子节点的话，那该节点的值等于两个子节点中较小的一个。
     * 更加正式的说：root.val = min(root.left.val, root.right.val)总是成立。
     * 给出这样的一颗二叉树，你需要输出所有节点中第二小的值，如果第二小的值不存在，输出-1。
     * 实例：
     *                      2
     *                    /   \
     *                   2     5
     *                        / \
     *                       5  7
     * 输入：root=[2,2,5,null,null,5,7]
     * 输出：5
     * 说明：最小值是2，第二小值是5
     *
     * 示例：
     *                      2
     *                    /  \
     *                   2   2
     * 输入：root=[2, 2, 2]
     * 输出：-1
     * 说明：最小值是2，但是不存在第二小的值
     *
     * 说明：
     *      树中节点数目在[1, 25]中。
     *      1 <= Node.val <= 231 - 1
     *      对于树中每个节点 root.val == min(root.left.val, root.right.val)
     * @param root
     * @return
     */
    public int findSecondMinimumValue(TreeNode root) {
        // todo
        return -1;
    }

    private int help(TreeNode root, int min) {
        // todo
        return -1;
    }


}
