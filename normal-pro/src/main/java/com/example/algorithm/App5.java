package com.example.algorithm;

import java.util.*;

public class App5 {
    public static void main(String[] args) {
        App5 app = new App5();
        int[] nums1 = {1, 1, 1, 2, 2, 3}; int k1 = 3;
        int[] nums2 = {1, 1, 2, 2, 3}; int k2 = 3;
        int[] nums3 = {3,3, 2, 2, 1}; int k3 = 0;
        int[] nums4 = {}; int k4 = 2;
        Set<Integer> res1 = app.getFrontK(nums1, k1);
        Set<Integer> res2 = app.getFrontK(nums2, k2);
        Set<Integer> res3 = app.getFrontK(nums3, k3);
        Set<Integer> res4 = app.getFrontK(nums4, k4);
        app.print(res1);
        app.print(res2);
        app.print(res3);
        app.print(res4);
    }

    /*
    给定一个整数数组 nums 和一个整数 k ，请返回其中出现频率前 k 高的元素。可以按 任意顺序 返回答案。
    示例：
    输入: nums = [1,1,1,2,2,3], k = 2
    输出: [1,2]
     */
    public Set<Integer> getFrontK(int[] nums, int k) {
        Set<Integer> result = new HashSet<>();
        if (nums.length < k) {
            return result;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int key : nums) {
            map.put(key, map.getOrDefault(key, 0) + 1);
        }
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        for (int i = 0; i < k; i++) {
            result.add(list.get(i).getKey());
        }
        return result;
    }

    public void print(Set<Integer> list) {
        for (int temp : list) {
            System.out.print(temp);
        }
        System.out.println();
    }
}
