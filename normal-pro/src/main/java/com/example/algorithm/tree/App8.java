package com.example.algorithm.tree;

public class App8 {
    public static void main(String[] args) {
        Solution solution = new Solution(2);
        solution.set(1, 1);
        solution.set(2, 2);
        solution.get(1);
        solution.set(3, 3);
        solution.get(2);
        solution.set(4, 4);
        solution.get(1);
        solution.get(3);
        solution.get(4);
    }
}
