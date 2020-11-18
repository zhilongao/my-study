package com.example.algorithm.linkedlist;

import java.util.HashSet;
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
     * 返回倒数第 k 个节点
     * 实现一种算法，找出单向链表中倒数第k个节点。返回该节点的值
     * 注意：本题相对原题稍作改动
     * 示例：
     * 输入： 1->2->3->4->5 和 k = 2
     * 输出： 4
     * 说明：
     *     给定的 k 保证是有效的。
     * @param head
     * @param k
     * @return
     */
    public int kthToLast(ListNode head, int k) {
        // 遍历两次链表吗
        ListNode first = head;
        ListNode second = head;
        while (k -- > 0) {
            first = first.next;
        }
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        return second.val;
    }

    /**
     * 删除中间节点
     * 实现一种算法，删除单向链表中间的某个节点（即不是第一个或最后一个节点），假定你只能访问该节点。
     * 示例：
     *  输入：单向链表a->b->c->d->e->f中的节点c
     *  结果：不返回任何数据，但该链表变为a->b->d->e->f
     * @param node
     */
    public void deleteNode(ListNode node) {
        //思路：将下一个结点的值赋给当前节点，当前节点的下一个结点为下下一个结点。
        node.val = node.next.val;
        node.next = node.next.next;
    }

    /**
     *  删除链表的节点
     *  给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。
     *  返回删除后的链表的头节点。
     *  注意：此题对比原题有改动
     * 示例 1:
     *      输入: head = [4,5,1,9], val = 5
     *      输出: [4,1,9]
     * 解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
     * 示例 2:
     *      输入: head = [4,5,1,9], val = 1
     * 输出: [4,5,9]
     * 解释: 给定你链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.
     *  
     *
     * 说明：
     *
     * 题目保证链表中节点的值互不相同
     * 若使用 C 或 C++ 语言，你不需要 free 或 delete 被删除的节点
     * @param head
     * @param val
     * @return
     */
    public ListNode deleteNode(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        if (head.val == val) {
            return head.next;
        }
        ListNode temp = head;
        while (temp.next != null) {
            if (temp.next.val == val) {
                temp.next = temp.next.next;
                break;
            }
            temp = temp.next;
        }
        return head;
    }

    /**
     * 两个链表的第一个公共节点
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode whereA = headA;
        ListNode whereB = headB;
        int lenA = 0;
        int lenB = 0;
        while (whereA != null) {
            lenA++;
            whereA = whereA.next;
        }
        while (whereB != null) {
            lenB++;
            whereB = whereB.next;
        }
        //将指针移动到可能出现匹配的位置
        whereA = headA;
        whereB = headB;
        if (lenA >= lenB) {
            for (int val = lenA - lenB; val > 0; val--) {
                whereA = whereA.next;
            }
        } else {
            for (int val = lenB - lenA; val > 0; val--) {
                whereB = whereB.next;
            }
        }
        //一一进行匹配
        int min=Math.min(lenA,lenB);
        while (min--!=0){
            if(whereA==whereB)return whereA;
            whereA=whereA.next;
            whereB=whereB.next;
        }
        return null;
    }

    /**
     * 反转链表
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = temp;
        }
        return pre;
    }

    /**
     * 编写代码，移除未排序链表中的重复节点。保留最开始出现的节点。
     * 示例1:
     *      输入：[1, 2, 3, 3, 2, 1]
     *      输出：[1, 2, 3]
     * 示例2:
     *      输入：[1, 1, 1, 1, 2]
     *      输出：[1, 2]
     * 提示：
     *      链表长度在[0, 20000]范围内。
     *      链表元素在[0, 20000]范围内。
     * 进阶：
     *      如果不得使用临时缓冲区，该怎么解决？
     * @param head
     * @return
     */
    public ListNode removeDuplicateNodes(ListNode head) {
        ListNode ob = head;
        while (ob != null) {
            ListNode oc = ob;
            while (oc.next != null) {
                if (oc.next.val == oc.val) {
                    oc.next = oc.next.next;
                } else {
                    oc = oc.next;
                }
            }
            ob = ob.next;
        }
        return head;
    }
}
