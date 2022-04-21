package com.example.algorithm.str;


import java.util.HashMap;
import java.util.Map;

public class SimpleV4 {

    public static void main(String[] args) {
        SimpleV4 v4 = new SimpleV4();
        // v4.testRotate();
        // v4.testTowSum();
        v4.testIsPalindrome();
    }

    public void testIsPalindrome() {
        int x1 = 1221;
        int x2 = 121;
        int x3 = -10;
        boolean b1 = isPalindrome(x1);
        boolean b2 = isPalindrome(x2);
        boolean b3 = isPalindrome(x3);
        System.err.println(b1);
        System.err.println(b2);
        System.err.println(b3);
    }

    public void testRotate() {
        String s1 = "abcde", goal1 = "cdeab";
        String s2 = "abcde", goal2 = "abced";
        boolean b1 = rotateString(s1, goal1);
        boolean b2 = rotateString(s2, goal2);
        System.err.println(b1);
        System.err.println(b2);
    }

    public void testTowSum() {
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;

        int[] ints1= twoSum(nums1, target1);
        for (int anInt : ints1) {
            System.err.println(anInt);
        }
        int[] nums2 = {3,2,4};
        int target2 = 6;
        int[] ints2 = twoSum(nums2, target2);
        for (int n : ints2) {
            System.err.println(n);
        }

        int[] nums3 = {3,3};
        int target3 = 6;
        int[] ints3 = twoSum(nums3, target3);
        for (int n : ints3) {
            System.err.println(n);
        }
    }

    // 一个字符串旋转之后是否可以变成另外一个字符串
    public boolean rotateString(String s, String goal) {
        int i = 0;
        String compare = s;
        for (;;) {
            if (compare.equals(goal)) {
                return true;
            }
            if (++i == compare.length()) {
                break;
            }
            compare =  compare.substring(1) + compare.charAt(0);
        }
        return false;
    }

    // 两数之和
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int temp = target - nums[i];
            if (map.containsKey(temp)) {
                return new int[]{map.get(temp), i};
            } else {
                map.put(nums[i], i);
            }
        }
        return new int[]{};
    }

    // 回文数
    public boolean isPalindrome(int x) {
        int temp = x;
        StringBuilder sb = new StringBuilder();
        while (temp != 0) {
            int remind = temp % 10;
            temp = temp / 10;
            sb.append(remind);
        }
        return String.valueOf(x).equals(sb.toString());
    }

    // 罗马数字包含以下七种字符:
    // I(1), V(5), X(10), L(50), C(100), D(500), M(1000)
    public int romanToInt(String s) {
        // todo
        //2 II
        //12 XII
        //27 XXVII
        return -1;
    }

    public String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length && num > 0; i++){
            while(num >= values[i]){
                num -= values[i];
                sb.append(symbols[i]);
            }
        }
        return sb.toString();
    }
    // 贪心算法
    // k1:北京 上海 天津
    // k2:广州 北京 深证
    // k3:成都 上海 杭州
    // k4:上海 天津
    // k5:杭州 大连
    

}
