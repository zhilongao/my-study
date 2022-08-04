package com.example.algorithm.tree;


import com.example.algorithm.linkedlist.ListNode;
import com.example.algorithm.tree.common.TreeNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class App10 {
    public static void main(String[] args) throws Exception {
        App10 app = new App10();
        int a = app.totalNQueens(4);
        System.err.println(a);

    }


    int result = 0;

    public int totalNQueens(int n) {
        // 初始化n*n的数组
        String[][] board = new String[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = ".";
            }
        }
        // 递归+回溯算法搜索答案
        backtrack(board, 0);
        return result;
    }

    public void backtrack(String[][] board, int row) {
        if (row == board.length) {
            result++;
            return;
        }
        int cols = board[0].length;
        for (int i = 0; i < cols; i++) {
            if (!check(board, row, i)) {
                continue;
            }
            // 修改状态
            board[row][i] = "Q";
            // 递归
            backtrack(board, row + 1);
            // 回溯
            board[row][i] = ".";
        }
    }

    public boolean check(String[][] board, int row, int col) {
        int rows = board.length;
        int cols = board[0].length;
        // 检查左上角
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j].equals("Q")) {
                return false;
            }
        }
        // 检查右上角
        for (int i = row - 1, j = col + 1; i >= 0 && j < cols; i--, j++) {
            if (board[i][j].equals("Q")) {
                return false;
            }
        }
        // 上边
        for (int i = 0; i < row; i++) {
            if (board[i][col].equals("Q")) {
                return false;
            }
        }
        return true;
    }









}