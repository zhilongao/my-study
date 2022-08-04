package com.example.algorithm.tree;

import java.util.*;

public class App5 {
    public static void main(String[] args) {
        App5 app = new App5();
        int[] arr1 = new int[]{1,2,3,4,5,6,7,8};
        int[] arr2 = new int[]{1,3,7,11,12,14,18};
        int res1 = app.lenLongestFibSubseq(arr1);
        int res2 = app.lenLongestFibSubseq(arr2);
        System.err.println(res1);
        System.err.println(res2);
    }

    public int lenLongestFibSubseq(int[] arr) {
        int maxLen = 0;
        Set<Integer> set = new HashSet<>();
        for (int temp : arr) {
            set.add(temp);
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j ++) {
                int pre = arr[i];
                int cur = arr[j];
                int sum = pre + cur;
                int len = 2;
                while (set.contains(sum)) {
                    pre = cur;
                    cur = sum;
                    sum = pre + cur;
                    len++;
                    maxLen = Math.max(len, maxLen);
                }
            }
        }
        return maxLen >= 3 ? maxLen : 0;
    }

    public int lenLongestFibSubseqV2(int[] arr) {
        if (arr.length < 3) {
            return 0;
        }
        int ans = 0;
        int len = arr.length;
        Map<Integer, Integer> index = new HashMap<Integer, Integer>();
        for (int i = 0; i < arr.length; i++) {
            index.put(arr[i], i);
        }
        int[][] dp = new int[len][len];
        for (int i = 0; i < len; i++) {
            // j是i前面的元素,从i-1开始
            for (int j = i - 1; j >=0; j--) {
                if (arr[j] * 2 <= arr[i]) {
                    continue;
                }
                int k = index.getOrDefault(arr[i] - arr[j], -1);
                if (k != -1) {
                    dp[j][i] = Math.max(dp[k][j] + 1, 3);
                }
                ans = Math.max(dp[j][i], ans);
            }
        }
        return ans;
    }


    public int longestArithSeqLength(int[] nums) {
        int len = nums.length;
        int[][] dp = new int[len][1001];
        int manLen = 0;
        for (int k = 1; k < len; k ++) {
            for (int j = 0; j < k; j++) {
                int d = nums[k] - nums[j] + 500;
                dp[k][d] = dp[j][d] + 1;
                manLen = Math.max(manLen, dp[k][d]);
            }
        }
        return manLen + 1;
    }

    // 0 3 6 9
    public int longestArithSeqLengthV2(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = 2;
            }
        }
        Map<Integer, Integer> map = new HashMap<>();
        int ans = 0;
        for(int i = 0; i < n; i++) {// i j k
            for (int j = i + 1; j < n; j++) {
                int target = 2 * nums[i] - nums[j];
                if (map.containsKey(target)) {
                    dp[i][j] = dp[map.get(target)][i] + 1;
                    ans = Math.max(ans, dp[i][j]);
                }
            }
            map.put(nums[i], i);
        }
        return ans + 2;
    }





}
