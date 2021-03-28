package com.example.algorithm.tree;

import com.example.algorithm.tree.common.ListNode;
import com.example.algorithm.tree.common.TreeNode;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SolutionV3 {

    public static void main(String[] args) {
        SolutionV3 v3 = new SolutionV3();
        int[] nums1 = new int[]{1,3,5};
        v3.permute(nums1);
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(nums, -1, path, list);
        return list;
    }

    public void dfs(int[] nums, int index, List<Integer> path, List<List<Integer>> list) {
        if (path.size() == nums.length) {
            return;
        }
        if (index != -1) {
            path.add(nums[index]);
        }
        if (path.size() == nums.length) {
            list.add(new ArrayList<>(path));
            System.err.println(path);
        }
        // 循环真正开始的地方
        for (int i = 0; i < nums.length; i ++) {
            dfs(nums, i, path, list);
        }
        if (index != -1) {
            // 回溯
            path.remove(path.size() - 1);
        }
    }

}
