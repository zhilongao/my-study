package com.example.algorithm.tree;

import com.example.algorithm.tree.common.ListNode;
import com.example.algorithm.tree.common.TreeNode;

import java.util.*;

public class SolutionV3 {
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
     * 删点成林
     * 给出二叉树的根节点root,树上每个节点都有一个不同的值。
     * 如果节点值在to_delete中出现，我们就把该节点从树上删去，最后得到一个森林（一些不相交的树构成的集合）
     * 返回森林中的每棵树。你可以按任意顺序组织答案。
     * 示例
     *                          1
     *                        /   \
     *                       2     3
     *                     /  \   /\
     *                    4   5  6  7
     * 输入：root=[1,2,3,4,5,6,7]  to_delet=[3,5]
     * 输出：[[1,2,null,4], [6], [7]]
     * 提示：
     *     树中的节点数最大为1000。
     *     每个节点都有一个介于1到1000之间的值，且各不相同。
     *     to_delete.length <= 1000
     *     to_delete包含一些从1到1000，各不相同的值。
     * @param root
     * @param to_delete
     * @return
     */
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {

        return null;
    }
}
