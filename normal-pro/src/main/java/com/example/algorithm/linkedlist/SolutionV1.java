package com.example.algorithm.linkedlist;

import com.alibaba.fastjson.JSONObject;
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
     * 反转链表II
     * 反转从位置m到n的链表。请使用一趟扫描完成反转。
     * 说明:
     *  1 <= m <= n <= 链表长度
     *  1->2->3->4->5->null  m=2 n=4
     *  1->4->3->2->5->null
     * @param head
     * @param m
     * @param n
     * @return
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
       if (head == null || head.next == null) {
           return head;
       }
       // 找到开始反转的节点和反转节点的前一个节点
       ListNode newHead = new ListNode(1);
       newHead.next = head;
       ListNode before = null;
       ListNode after = null;
       ListNode curr = newHead;
       for (int i = 1; i <= m - 1; i ++) {
           curr = curr.next;
       }
       before = curr;
       curr = curr.next;
       before.next = null;
       // 开始反转链表啦
        ListNode prev = null;
        ListNode temp = null;
        for(int i = m; i <= n; i ++) {
            if (curr == null) {
                break;
            }
            if (i == n) {
                after = curr;
            }
            temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        // 剩余元素的处理
        before.next = prev;
        while (prev.next != null) {
            prev = prev.next;
        }
        prev.next = after;
        return newHead.next;
    }




}
