package com.example.algorithm.str;

import org.springframework.util.StringUtils;

import java.util.Stack;

public class SimpleV3 {

    public static void main(String[] args) {
        SimpleV3 v3 = new SimpleV3();
        /*
        String s1 = "(1+(2*3)+((8)/4))+1"; int a1 = 3; //(()(()))
        String s2 = "(1)+((2))+(((3)))"; int a2 = 3;   //()(())((()))
        String s3 = "1+(2*3)/(2-1)"; int a3 = 1;       //()()
        String s4 = "1"; int a4 = 0;
        System.err.println(v3.maxDepth(s1));
        System.err.println(v3.maxDepth(s2));
        System.err.println(v3.maxDepth(s3));
        System.err.println(v3.maxDepth(s4));
        */
        // haystack = "hello", needle = "ll"  2
        // haystack = "aaaaa", needle = "bba" -1
        // haystack = "", needle = ""  0
        // 0 <= haystack.length, needle.length <= 5 * 104
        // haystack 和 needle 仅由小写英文字符组成

        System.err.println(v3.strStr("hello", "ll"));
        System.err.println(v3.strStr("aaaaa", "bba"));
        System.err.println(v3.strStr("", ""));

    }


    // 字符串的有效括号最大深度
    public int maxDepth(String s) {
        int pos = 0;
        int max = 0;
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if ('(' == c) {
                max = Math.max(max, pos);
                stack.push('(');
                pos = 0;
            } else if (')' == c) {
                stack.pop();
                pos = pos + 1;
            }
        }
        max = Math.max(pos, max);
        return max;
    }

    public int strStr(String haystack, String needle) {
        if (null == needle || "".equals(needle)) {
            return 0;
        }
        boolean search = false;
        int slow = 0;
        while (slow < haystack.length()) {
            int fast = 0;
            for (int i = slow; i < haystack.length(); i++) {
                // 遇到不同的,立马进入下一轮循环
                if (haystack.charAt(i) != needle.charAt(fast)) {
                    break;
                }
                // fast跑到这里,证明已经找到了index
                if (fast == needle.length() - 1) {
                    search = true;
                    break;
                }
                fast ++;
            }
            if (search) {
                break;
            }
            slow ++;
        }
        if (search) {
            return slow;
        } else {
            return -1;
        }
    }

    // 在9876543210520中寻找520
    // {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
    // M=字符集的总个数=10
    // 520转换成下面的值
    // (5的映射值*M + 2的映射值) *M + 0的映射值 = (5*10 +2) * 10 + 0 = 520

    // 5 5* 10^2  500
    // 2 2* 10^1  20
    // 0 0* 10^0  0

    // 987
    // 9 9* 10^2  900
    // 8 8* 10^1  80
    // 7 7* 10^0  7
}
