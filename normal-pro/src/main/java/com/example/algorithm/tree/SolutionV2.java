package com.example.algorithm.tree;

import com.example.algorithm.tree.common.TreeNode;

import java.util.Stack;


public class SolutionV2 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(6);
        TreeNode node2_7 = new TreeNode(7);
        TreeNode node2_8 = new TreeNode(8);
        root.left = node2_7;
        root.right = node2_8;

        TreeNode node3_2 = new TreeNode(2);
        TreeNode node3_7 = new TreeNode(7);
        TreeNode node3_1 = new TreeNode(1);
        TreeNode node3_3 = new TreeNode(3);
        node2_7.left = node3_2;
        node2_7.right = node3_7;
        node2_8.left = node3_1;
        node2_8.right = node3_3;

        TreeNode node4_9 = new TreeNode(9);
        TreeNode node4_1 = new TreeNode(1);
        TreeNode node4_4 = new TreeNode(4);
        TreeNode node4_5 = new TreeNode(5);
        node3_2.left = node4_9;
        node3_7.left = node4_1;
        node3_7.right = node4_4;
        node3_3.right = node4_5;

        SolutionV2 v2 = new SolutionV2();

    }

    /**
     * 累加树
     * @param root
     * @return
     */
    int sum = 0;
    public TreeNode convertBST(TreeNode root) {
        if (root != null) {
            convertBST(root.right);
            sum += root.val;
            root.val = sum;
            convertBST(root.left);
        }
        return root;
    }

}
