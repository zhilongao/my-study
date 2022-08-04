package com.example.algorithm.tree;

import com.example.algorithm.linkedlist.ListNode;

import java.util.*;

public class App7 {
    public static void main(String[] args) {
        App7 app = new App7();
        int[] nums = new int[]{1,2,3,4,5};

        ListNode head1 = new ListNode(1);
        ListNode head12 = new ListNode(2);
        ListNode head13 = new ListNode(3);
        ListNode head14 = new ListNode(4);
        ListNode head15 = new ListNode(5);
        head1.next = head12;
        head12.next = head13;
        head13.next = head14;
        head14.next = head15;

        int[][] nums01 = new int[][]{{2,3,4},{5,6,7},{8,9,10},{11,12,13},{14,15,16}};
        app.spiralOrder(nums01);
    }

    public ArrayList<Integer> spiralOrder(int[][] matrix) {
        // 1 2 3
        // 4 5 6
        // 7 8 9
        // 确定四个边界值
        int topRow = 0;
        int bottomRow = matrix.length - 1;
        int leftCol = 0;
        int rightCol = matrix[0].length - 1;
        ArrayList<Integer> list = new ArrayList<Integer>();
        while (topRow <= bottomRow && leftCol <= rightCol) {
            // 上
            for (int i = leftCol; i <= rightCol; i++) {
                list.add(matrix[topRow][i]);
            }
            topRow++;
            if (topRow > bottomRow) {
                break;
            }
            // 右
            for (int i = topRow; i <= bottomRow; i++) {
                list.add(matrix[i][rightCol]);
            }
            rightCol--;
            if (rightCol < leftCol) {
                break;
            }
            // 下
            for (int i = rightCol; i >= leftCol; i--) {
                list.add(matrix[bottomRow][i]);
            }
            bottomRow--;
            if (bottomRow < topRow) {
                break;
            }
            // 左
            for (int i = bottomRow; i >= topRow; i--) {
                list.add(matrix[i][leftCol]);
            }
            leftCol++;
            if (leftCol > rightCol) {
                break;
            }
        }
        return list;
    }



}
