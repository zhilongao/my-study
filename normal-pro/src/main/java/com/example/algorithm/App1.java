package com.example.algorithm;

import java.util.HashMap;
import java.util.Map;

public class App1 {

    public static void main(String[] args) {
        int[] nums1 = {2,7,11,15}; int target1 = 9;
        int[] nums2 = {3,2,4}; int target2 = 6;
        int[] nums3 = {3,3}; int target3 = 6;
        int[] ints1 = twoSumV1(nums1, target1);
        System.err.println(ints1[0] + ":" + ints1[1]);

        int[] ints2 = twoSumV1(nums2, target2);
        System.err.println(ints2[0] + ":" + ints2[1]);

        int[] ints3 = twoSumV1(nums3, target3);
        System.err.println(ints3[0] + ":" + ints3[1]);
    }

    // 给定一个数组和一个目标值,找出数组中两数之和等于目标值的两个数,返回其下标
    // 双层循环查找
    public static int[] twoSumV1(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int agr1 = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                int agr2 = nums[j];
                if (agr1 + agr2 == target) {
                    return new int[]{i, j};
                }
            }
        }
        return result;
    }

    // 使用哈希表查找
    public static int[] twoSumV2(int[] nums, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int searchKey= target - nums[i];
            if (map.containsKey(searchKey)) {
                // 此处不需要加判断下标是否相等,因为map中的元素总比循环慢一位
                // 按照顺序返回
                if (i < map.get(searchKey)) {
                    return new int[]{i, map.get(searchKey)};
                } else {
                    return new int[]{map.get(searchKey), i};
                }
            }
            map.put(nums[i], i);
        }
        return result;
    }
}