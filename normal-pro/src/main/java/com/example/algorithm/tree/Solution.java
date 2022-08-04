package com.example.algorithm.tree;

public class Solution {
    // 思路:通过hash表和双向链表实现
    // 直接通过双向链表来实现

    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;
        DLinkedNode() {

        }
        DLinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    // 头结点和尾节点
    int capacity;
    int size;
    DLinkedNode head;
    DLinkedNode tail;

    public Solution(int capacity) {
        // write code here
        this.capacity = capacity;
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        // write code here
        // 找到该节点
        DLinkedNode temp = head.next;
        while (temp != tail) {
            if (temp.key == key) {
                // 从链表中清除该节点
                removeNode(temp);
                // 将该链表添加到头部
                addHead(temp);
                // 返回该链表的值
                return temp.value;
            } else {
                temp = temp.next;
            }
        }
        return -1;
    }

    public void set(int key, int value) {
        // write code here
        // 1. 先在链表中查找下是否有该节点
        DLinkedNode temp = head.next;
        boolean update = false;
        while(temp != tail) {
            if (temp.key == key) {
                // 从链表中清除该节点
                removeNode(temp);
                // 将该链表添加到头部
                addHead(temp);
                // 更新该节点的值
                temp.value = value;
                update = true;
                break;
            } else {
                temp = temp.next;
            }
        }
        if (!update) {
            // 新增节点
            size++;
            // 删除一个节点
            if (size > capacity) {
                removeTail();
            }
            // 新增节点
            DLinkedNode newNode = new DLinkedNode(key, value);
            DLinkedNode headNext = head.next;
            head.next = newNode;
            newNode.prev = head;
            newNode.next = headNext;
            headNext.prev = newNode;
        }
    }


    public void removeNode(DLinkedNode temp) {
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        temp.next = null;
        temp.prev = null;
    }

    public void addHead(DLinkedNode temp) {
        DLinkedNode headNext = head.next;
        head.next = temp;
        temp.prev = head;
        temp.next = headNext;
        headNext.prev = temp;
    }

    public void removeTail() {
        DLinkedNode prevNode = tail.prev;
        prevNode.prev.next = tail;
        tail.prev = prevNode.prev;
        prevNode.next = null;
        prevNode.prev = null;
    }

}
