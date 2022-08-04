package com.example.algorithm.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class App6 {
    public static void main(String[] args) {
        App6 app = new App6();
        String s1 = "(()";
        String s2 = ")()())";
        System.err.println(app.longestValidParentheses(s1));
        System.err.println(app.longestValidParentheses(s2));
    }

    public int longestValidParentheses(String s) {
        if (s.length() < 2) {
            return 0;
        }
        int n = s.length();
        int maxLen = 0;
        for (int i = 0; i < n; i ++) {
            for (int j = i + 2; j < n; j += 2) {
                if (isValid(s.substring(i, j))) {
                    maxLen = Math.max(maxLen, j - i);
                }
            }
        }
        return maxLen;
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push('(');
            } else {
                if (stack.isEmpty()) {
                    return false;
                } else {
                    stack.pop();
                }
            }
        }
        return stack.isEmpty();
    }

}
