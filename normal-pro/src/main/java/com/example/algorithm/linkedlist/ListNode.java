package com.example.algorithm.linkedlist;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/18 18:53
 * @since v1.0.0001
 */
public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

}
