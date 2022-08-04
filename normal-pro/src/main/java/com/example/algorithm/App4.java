package com.example.algorithm;

import java.util.Arrays;

public class App4 {


    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5};
        NumArray app = new NumArray(nums);
        int i = app.sumRange(0, 2);
        System.err.println(i);

    }

    // 二维数组的前缀和
    class NumMatrix {

        int[][] dp;
        public NumMatrix(int[][] matrix) {
            int n = matrix.length;
            if (n > 0) {
                int m = matrix[0].length;
                dp = new int[n][m + 1];
                for (int i = 0; i < n; i ++) {
                    for (int j = 0; j < m; j++) {
                        dp[i][j + 1] = dp[i][j] + matrix[i][j];
                    }
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            int sum = 0;
            for (int i = row1; i <= row2 ; i++) {
                sum += dp[i][col2 + 1] - dp[i][col1];
            }
            return sum;
        }
    }


    // 一维数组的前缀和
    public static class NumArray {

        int[] dp;

        public NumArray(int[] nums) {
            dp = new int[nums.length + 1];
            Arrays.fill(dp, 0);
            dp[0] = nums[0];
            for (int i = 1; i <= nums.length; i++) {
                dp[i] = dp[i - 1] + nums[i - 1];
            }
        }

        public int sumRange(int left, int right) {
            return dp[++right] - dp[left];
        }
    }

}
