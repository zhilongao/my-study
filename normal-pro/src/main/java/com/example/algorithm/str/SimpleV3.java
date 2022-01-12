package com.example.algorithm.str;

import java.util.Stack;

public class SimpleV3 {

    public static void main(String[] args) {
        SimpleV3 v3 = new SimpleV3();
        String s1 = "(1+(2*3)+((8)/4))+1"; int a1 = 3; //(()(()))
        String s2 = "(1)+((2))+(((3)))"; int a2 = 3;   //()(())((()))
        String s3 = "1+(2*3)/(2-1)"; int a3 = 1;       //()()
        String s4 = "1"; int a4 = 0;
        System.err.println(v3.maxDepth(s1));
        System.err.println(v3.maxDepth(s2));
        System.err.println(v3.maxDepth(s3));
        System.err.println(v3.maxDepth(s4));
    }

    // 字符串的有效括号最大深度
    public int maxDepth(String s) {
        int max = 0;
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        int pop = 0;
        for (char c : chars) {
            if ('(' == c) {
                max = Math.max(max, pop);
                pop = 0;
                stack.push(c);
            } else if (')' == c) {
                stack.pop();
                pop = pop + 1;
            }
        }
        max = Math.max(max, pop);
        return max;
    }
}
