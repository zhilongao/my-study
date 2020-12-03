package com.example.algorithm.linkedlist;

import com.alibaba.fastjson.JSONObject;
import com.example.algorithm.tree.common.TreeNode;
import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/18 9:49
 * @since v1.0.0001
 */
public class SolutionV1 {

    /**
     * 排序链表
     * 给你链表的头节点head，请将其按升序排列并返回排序后的链表。
     * 进阶：
     *      可以在o(n, logn)时间复杂度和常数级空间复杂度下，对链表进行排序吗?
     * 1. 4->2->1->3      1->2->3->4
     * 2. -1->5->3->4->0  -1->0->3->4->5
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode lastSorted = head;
        ListNode curr = head.next;
        while (curr != null) {
            if (lastSorted.val <= curr.val) {
                lastSorted = lastSorted.next;
            } else {
                ListNode prev = dummyHead;
                while (prev.next.val <= curr.val) {
                    prev = prev.next;
                }
                lastSorted.next = curr.next;
                curr.next = prev.next;
                prev.next = curr;
            }
            curr = lastSorted.next;
        }
        return dummyHead.next;
    }

    public static int[] insertSort(int[] arr) {
        int index = 0;
        for(int i = 0; i < arr.length; i ++) {
            index = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[index]) {
                    index = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 3, 6, 3, 7, 11, 2, 4, 9, 0};
        insertSort(arr1);
        for (int i = 0; i < arr1.length; i++) {
            System.err.print(arr1[i] + "\t");
        }
    }
}
