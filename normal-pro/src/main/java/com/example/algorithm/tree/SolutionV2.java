package com.example.algorithm.tree;

import com.example.algorithm.tree.common.TreeNode;

import java.util.*;


public class SolutionV2 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        TreeNode node2_1 = new TreeNode(1);
        TreeNode node2_2 = new TreeNode(6);
        root.left = node2_1;
        root.right = node2_2;

        TreeNode node3_1 = new TreeNode(0);
        TreeNode node3_2 = new TreeNode(2);
        TreeNode node3_3 = new TreeNode(5);
        TreeNode node3_4 = new TreeNode(7);
        node2_1.left = node3_1;
        node2_1.right = node3_2;
        node2_2.left = node3_3;
        node2_2.right = node3_4;

        TreeNode node4_1 = new TreeNode(3);
        TreeNode node4_2 = new TreeNode(8);

        node3_2.right = node4_1;
        node3_4.right = node4_2;


        SolutionV2 v2 = new SolutionV2();
    }

    // 使用广度优先的算法来实现
    List<List<Integer>> list = new ArrayList<>();
    Map<TreeNode, TreeNode> map = new HashMap<>();
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> valQueue = new LinkedList<>();
        nodeQueue.offer(root);
        valQueue.offer(0);
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            int val = valQueue.poll();
            int sum = val + node.val;
            if (node.left == null && node.right == null && sum == targetSum) {
                getPath(node);
            } else {
                if (node.left != null) {
                    nodeQueue.offer(node.left);
                    valQueue.offer(sum);
                    map.put(node.left, node);
                }
                if (node.right != null) {
                    nodeQueue.offer(node.right);
                    valQueue.offer(sum);
                    map.put(node.right, node);
                }
            }
        }
        return list;
    }

    private void getPath(TreeNode node) {
        List<Integer> temp = new ArrayList<>();
        while (node != null) {
            temp.add(node.val);
            node = map.get(node);
        }
        Collections.reverse(temp);
        list.add(new ArrayList<>(temp));
    }


}
