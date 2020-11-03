package com.example.algorithm.tree;

import com.example.algorithm.tree.common.Node;
import com.example.algorithm.tree.common.TreeNode;

import java.util.*;

public class SolutionV2 {

    /**
     * 给你一颗所有节点为非负值的二叉搜索树，请你计算树中任意两节点的差的绝对值的最小值。
     * 示例
     *  输入：
     *          1
     *           \
     *            3
     *           /
     *          2
     *   输出：1
     * 解释：
     *  最小绝对值差为1，其中2和1的差的绝对值为1(或者2和3)
     * 提示：
     *  树中至少有2个节点
     *
     * @param root
     * @return
     */
    int pre;
    int ans;
    public int getMinimumDifference(TreeNode root) {
        ans = Integer.MAX_VALUE;
        pre = -1;
        dfs(root);
        return ans;
    }

    public void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        dfs(node.left);
        if (pre == -1) {
            pre = node.val;
        } else {
            ans = Math.min(ans, node.val - pre);
            pre = node.val;
        }
        dfs(node.right);
    }

    /**
     * 给定一个n叉树，返回其节点值的后续遍历。
     * 例如：给定一个三叉树
     *              1
     *            / \  \
     *           3  2   4
     *         /  \
     *        5    6
     * 返回其后续遍历：[5,6,3,2,4,1]
     * 说明：递归法很简单，你可以使用迭代法完成此题吗。
     * @param root
     * @return
     */
    List<Integer> list;
    public List<Integer> postorder(Node root) {
        list = new ArrayList<>();
        order(root);
        return list;
    }

    public void order(Node root) {
        if (root == null) {
            return;
        }
        for (Node node : root.children) {
            order(node);
        }
        list.add(root.val);
    }

    /**
     * 实现一个函数，检查二叉树是否平衡。在这个问题中，平衡树的定义如下：任意一个节点，其两颗子树的高度差不超过1。
     * 示例1：
     *  给定二叉树[3,9,20,null,null,15,7]
     *              3
     *            /   \
     *           9    20
     *               / \
     *              15  7
     *  返回true
     *  示例2：
     *   给定二叉树[1,2,2,3,3,null,null,4,4]
     *                      1
     *                    /   \
     *                   2     2
     *                 /  \
     *               3     3
     *             / \
     *            4  4
     *  返回false。
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        return dfsV1(root);
    }

    public boolean dfsV1(TreeNode node) {
        if (node == null) {
            return true;
        }
        if (Math.abs(depthNode(node.left) - depthNode(node.right)) > 1) {
            return false;
        }
        return dfsV1(node.left) && dfsV1(node.right);
    }

    private int depthNode(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return Math.max(depthNode(node.left), depthNode(node.right)) + 1;
    }

}
