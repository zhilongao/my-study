package com.example.algorithm.tree;

import com.example.algorithm.tree.common.TreeNode;

import java.util.*;

public class Solution {

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
    public int diameterOfBinaryTree(TreeNode root) {
        // todo 先做到这里吧，今天
        return 0;
    }

}
