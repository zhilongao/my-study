package com.example.algorithm.tree;

import com.example.algorithm.tree.common.ListNode;
import com.example.algorithm.tree.common.TreeNode;

import java.util.*;

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
}
