package com.example.algorithm.linkedlist;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/18 18:53
 * @since v1.0.0001
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode() {}
    public ListNode(int val) { this.val = val; }
    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

}
