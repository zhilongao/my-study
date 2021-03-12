package com.example.algorithm.str;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleV2 {


    public static void main(String[] args) {
        int[] nums1 = {1,2,2,1};
        int[] nums2 = {2,2};
        // 1 1 2 2
        // 2 2
        int[] nums3 = {4,9,5};
        int[] nums4 = {9,4,9,8,4};
        // 4 5 9
        // 4 4 8 9 9
        int[] res1 = intersection(nums1, nums2);
        int[] res2 = intersection(nums3, nums4);
        System.err.println();
    }

    public static int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int len1 = nums1.length;
        int len2 = nums2.length;
        int index1 = 0;
        int index2 = 0;
        Set<Integer> sets = new HashSet<>();
        while (index1 < len1 && index2 < len2) {
            if (nums1[index1] == nums2[index2]) {
                sets.add(nums1[index1]);
                index1++;
                index2++;
            } else if (nums1[index1] < nums2[index2]) {
                index1 ++;
            } else {
                index2 ++;
            }
        }
        int[] res = new int[sets.size()];
        int index = 0;
        for (Integer temp : sets) {
            res[index++] = temp;
        }
        return res;
    }

}
