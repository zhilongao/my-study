package com.example.algorithm.tree;

import com.example.algorithm.linkedlist.ListNode;
import com.example.algorithm.tree.common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class App9 {
    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node12 = new ListNode(2);
        ListNode node13 = new ListNode(3);
        ListNode node14 = new ListNode(4);
        ListNode node15 = new ListNode(5);

        node1.next = node12;
        node12.next = node13;
        node13.next = node14;
        node14.next = node15;

        App9 app9 = new App9();


    }


}
