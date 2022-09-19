package com.example.algorithm;

import java.util.Arrays;
import java.util.TreeSet;

public class App3 {

    // 我我我又修改了这个文件的内容啦哈哈哈

    // 我修改这个文件的内容
    public static void main(String[] args) {
        App3 app = new App3();
        int[] nums = new int[]{-2,1,-3,4,-1,2,1,-5,4};
        int[] ints = app.maxSubArrayV3(nums);
        for (int num : ints) {
            System.err.print(num + " ");
        }

        System.err.println();
        int[][] nums1 = new int[][]{{-1, 0}, {0, -1}, {3, 2}};
        System.err.println(nums1.length);
        System.err.println(nums1[0].length);

        System.err.println("====================================");
        int[] nums2 = new int[]{1,2,3,4,5,6}; // 1,3,
        int[] ints1 = app.preSum(nums2);
        for (int n : ints1) {
            System.err.print(n + " ");
        }

    }

    // 求一维数组的前缀和
    public int[] preSum(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = dp[i - 1] + nums[i];
        }
        return dp;
    }




    public int maxSubArray(int[] nums) {
        int cur = nums[0];
        int max = cur;
        for (int num : nums) {
            cur = Math.max(cur, 0) + num;
            max = Math.max(max, cur);
        }
        return max;
    }

    public int maxSubArrayV2(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 1; i < nums.length; i ++) {
            dp[i] = Math.max(dp[i-1] + nums[i], nums[i]);
            max = Math.max(max, dp[i]);
        }
        return max;
    }
    // 最大子序列和,记录起始位置坐标
    public int[] maxSubArrayV3(int[] nums) {
        int[] pos = new int[2];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        int begin = 0;
        for (int i = 1; i < nums.length; i ++) {
            if (dp[i-1] + nums[i] > nums[i]) {
                dp[i] = dp[i-1] + nums[i];
            } else {
                dp[i] = nums[i];
                begin = i;// 另起炉灶整
            }
            if (dp[i] > max) {
                max = dp[i];
                pos[0] = begin;
                pos[1] = i;
            }
        }
        int[] res = new int[pos[1] - pos[0] + 1];
        System.arraycopy(nums, pos[0], res, 0, pos[1] - pos[0] + 1);
        System.err.println(max);
        return res;
    }



    // 最大子矩阵
    public int[] getMaxMatrixV2(int[][] matrix) {
        int r = matrix.length;
        int c = matrix[0].length;

        // 求得二维数组的一维前缀和
        int[][] accum = new int[c][r + 1];
        Arrays.fill(accum, 0);
        for (int i = 0; i < r; i++) {//遍历行
            for (int j = 0; j < c; j++) {//遍历列
                accum[j][i + 1] = matrix[i][j] + accum[j][i];
            }
        }
        int mval = Integer.MIN_VALUE;
        int[] ans = new int[]{0,0,0,0};
        for (int sr = 0; sr < r; ++sr) {// 遍历行
            for (int er = sr; er < r; ++ er) {// 从第sr行开始遍历,最后一行结束
                int dp = Integer.MIN_VALUE;
                int mc = 0;
                for (int cc = 0; cc < c; ++cc) {// 列
                    if (dp > 0) {
                        dp += accum[cc][er+1] - accum[cc][sr];
                    } else {
                        mc = cc;
                        dp = accum[cc][er+1] - accum[cc][sr];
                    }
                    if (dp > mval) {
                        mval = dp;
                        ans = new int[]{sr, mc, er, cc};
                    }
                }
            }
        }
        return ans;
    }

    public int[] getMaxMatrix(int[][] matrix) {
        int[] ans = new int[4];
        int n = matrix.length;// 记录多少行
        int m = matrix[0].length;// 记录多少列
        int[] b = new int[m]; // 记录当前i-j行组成大矩阵的每一列的和,将二维转换为一维
        int sum = 0;
        int maxSum = Integer.MAX_VALUE;// 记录最大值
        int bestRl = 0;
        int bestCl = 0;
        for (int i = 0; i < n; i++) {// 以i为上边,从上向下扫描
            for (int t = 0; t < m; t ++) {
                b[t] = 0;
            }
            for (int j = i; j < n; j ++) {
                sum = 0;
                // 求第j行的最大子序列和
                for (int k = 0; k < m; k++) {
                    b[k] += matrix[j][k];
                    // sum是从0开始滴
                    sum = Math.max(sum + b[k], b[k]);
                    if (sum <= 0) {
                        bestCl = i;// 左上角位置(行)
                        bestRl = k;// 左上角位置(列)
                    }
                    if (sum > maxSum) {
                        maxSum = sum;
                        ans[0] = bestRl;
                        ans[1] = bestCl;
                        ans[2] = j;
                        ans[3] = k;
                    }
                }
            }
        }
        return ans;
    }




    public int[] getMaxMatrixV3(int[][] matrix) {
        int[] ans = new int[4];// 记录结果
        int M = matrix.length;
        int N = matrix[0].length;
        int[] b = new int[N]; // 记录状态
        int maxVal = Integer.MIN_VALUE; // 最大值
        int sum;
        int bestRl = 0;//左边界的横坐标和纵坐标
        int bestCl = 0;
        for (int i = 0; i < M; i++) {
            // 清空b数组
            for (int k = 0; k < N; k++) {
                b[k] = 0;
            }
            // 循环j从i开始,边界为M-1
            for (int j = i; j < M; j ++) {
                // 没遍历一行,求一次最大子序列和
                sum = 0;
                for (int k = 0; k < N; k++) {
                    b[k] = matrix[j][k];
                    if (sum > 0) {
                        sum = sum + b[k];
                    } else {
                        sum = b[k];
                        // 更新边界
                        bestRl = i;
                        bestCl = k;
                    }
                    // 更新右边界
                    if (sum > maxVal) {
                        maxVal = sum;
                        ans[0] = bestRl;
                        ans[1] = bestCl;
                        ans[2] = j;
                        ans[3] = k;
                    }
                }
            }
        };
        return ans;
    }



    public int maxSumSubmatrix(int[][] matrix, int k) {
        int ans = Integer.MIN_VALUE;
        int m = matrix.length, n = matrix[0].length;
        for (int i = 0; i < m; ++i) { // 枚举上边界
            int[] sum = new int[n];
            for (int j = i; j < m; ++j) { // 枚举下边界
                for (int c = 0; c < n; ++c) {
                    sum[c] += matrix[j][c]; // 更新每列的元素和
                }
                TreeSet<Integer> sumSet = new TreeSet<Integer>();
                sumSet.add(0);
                int s = 0;
                for (int v : sum) {
                    s += v;
                    Integer ceil = sumSet.ceiling(s - k);
                    if (ceil != null) {
                        ans = Math.max(ans, s - ceil);
                    }
                    sumSet.add(s);
                }
            }
        }
        return ans;
    }


    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int max1 = getMax(nums, 0, nums.length - 2);
        int max2 = getMax(nums, 1, nums.length - 1);
        return Math.max(max1, max2);
    }


    public int getMax(int[] nums, int start, int end) {
        // 滚动数组
       int first = nums[start];
       int second = Math.max(nums[start], nums[start + 1]);
       for (int i = start + 2; i <= end; i++) {
            int temp = second;
            second = Math.max(first + nums[i], second);
            first = temp;
       }
       return second;
    }


    public int maxSizeSlices(int[] slices) {
        // 定义两个数组,分别处理环形数组不带第一个元素和最后一个元素的情况
        int[] slices1 = new int[slices.length - 1];
        int[] slices2 = new int[slices.length - 1];
        System.arraycopy(slices, 1, slices1, 0, slices1.length);// 不考虑第一个元素
        System.arraycopy(slices, 0, slices2, 0, slices2.length);// 不考虑最后一个元素
        int ans1 = calculate(slices1);
        int ans2 = calculate(slices2);
        return Math.max(ans1, ans2);
    }

    public int calculate(int[] slices) {
        int n = slices.length;
        int choose = (n + 1) / 3;//减少了一个元素,所以这块要加1
        int[][] dp = new int[n + 1][choose + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= choose; j++) {
                dp[i][j] = Math.max(dp[i-1][j], (i - 2 >= 0 ? dp[i - 2][j - 1] : 0) + slices[i - 1]);
            }
        }
        return dp[n][choose];
    }




    public int maxProduct(int[] nums) {
        int ret = nums[0];
        int prevMin = nums[0];
        int prevMax = nums[0];
        int temp1 = nums[0];
        int temp2 = nums[0];
        for (int i = 1; i < nums.length; i++) {
            temp1 = prevMin * nums[i];
            temp2 = prevMin * nums[i];
            prevMin = Math.min(Math.min(temp1, temp2), nums[i]);
            prevMax = Math.max(Math.max(temp1, temp2), nums[i]);
            ret = Math.max(prevMax, ret);
        }
        return ret;
    }


    public int maxSubarraySumCircular(int[] nums) {
        // 分类讨论
        // 不使用环:最大连续子数组的玩法


        return 0;
    }

}
