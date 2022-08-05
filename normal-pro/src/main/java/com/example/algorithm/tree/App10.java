package com.example.algorithm.tree;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class App10 {

    public static void main(String[] args) throws IOException{
        App10 app = new App10();
        int n = 3;
        int[][] ints = app.generateMatrix(n);
        System.err.println(ints);
    }

    public int[][] generateMatrix(int n) {
        int loop = 0;
        int val = 1;
        int[][] result = new int[n][n];
        int top = 0;
        int bottom = n - 1;
        int left = 0;
        int right = n - 1;
        while (loop < n / 2) {
            loop++;
            for (int i = left; i <= right -1; i++) {
                result[top][i] = val++;
            }
            for (int i = top; i <= bottom - 1; i++) {
                result[i][right] = val++;
            }
            for (int i = right; i >= left + 1; i--) {
                result[bottom][i] = val++;
            }
            for (int i = bottom; i >= top + 1; i--) {
                result[left][i] = val++;
            }
            left++;
            top++;
            right--;
            bottom--;
        }
        if (n % 2 == 1) {
            result[top][left] = val;
        }
        return result;
    }


}