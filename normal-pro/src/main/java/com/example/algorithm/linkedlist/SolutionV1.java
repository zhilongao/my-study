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

    /**
     * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
     * @param head
     * @param k
     * @return
     */
    public ListNode sortList(ListNode head) {
        // 头节点或者头节点的下一个节点为null，直接返回
        if (head == null || head.next == null) {
            return head;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode tmp = slow.next;
        slow.next = null;
        ListNode left = sortList(head);
        ListNode right = sortList(tmp);
        ListNode h = new ListNode(0);
        ListNode res = h;
        while (left != null && right != null) {
            if (left.val < right.val) {
                h.next = left;
                left = left.next;
            } else {
                h.next = right;
                right = right.next;
            }
            h = h.next;
        }
        h.next = left != null ? left : right;
        return res.next;
    }

    /**
     * 旋转链表
     * 给定一个链表，旋转链表，将链表每个节点向右移动k个位置，其中k是非负数。
     * 示例 1:
     *      输入: 1->2->3->4->5->NULL, k = 2
     *      输出: 4->5->1->2->3->NULL
     * 解释:
     *      向右旋转 1 步: 5->1->2->3->4->NULL
     *      向右旋转 2 步: 4->5->1->2->3->NULL
     * 示例 2:
     *      输入: 0->1->2->NULL, k = 4
     *      输出: 2->0->1->NULL
     * 解释:
     *      向右旋转 1 步: 2->0->1->NULL
     *      向右旋转 2 步: 1->2->0->NULL
     *      向右旋转 3 步: 0->1->2->NULL
     *      向右旋转 4 步: 2->0->1->NULL
     * @param head
     * @param k
     * @return
     */
    // 说是循环旋转，但其实本质上是将尾部向前数第K个元素作为头，原来的头接到原来的尾上
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || k == 0) {
            return head;
        }
        ListNode cursor = head;
        ListNode tail = null;
        int length = 1;
        while(cursor.next != null) {
            cursor = cursor.next;
            length ++;
        }
        tail = cursor;

        // 得到循环的次数
        int loop = length - (k % length);
        //改成循环链表
        cursor.next = head;
        //指向头结点
        cursor = head;
        //开始循环
        for(int i = 0; i < loop; i++) {
            cursor = cursor.next;
            tail = tail.next;
        }
        //改成单链表
        tail.next = null;
        //返回当前头
        return cursor;
    }

    public ListNode rotateRightV1(ListNode head, int k) {
        if (head == null || k == 0) {
            return head;
        }
        ListNode cursor = head;
        ListNode tail = null;
        int length = 1;
        while (cursor.next != null) {
            cursor = cursor.next;
            length ++;
        }
        tail = cursor;
        // 将链表构造成为环形链表
        cursor.next = head;
        cursor = head;
        int loop = length - (k % length);
        for (int i = 0; i < loop; i++) {
            cursor = cursor.next;
            tail = tail.next;
        }
        tail.next = null;
        return cursor;
    }

    /**
     * 两两交换链表中的节点
     * 给定一个链表，两两交换相邻的节点，返回交换后的链表
     * 你不能够只是单纯的修改节点内部的值，而是要真是节点的交换。
     * 示例1： 1->2->3->4  2->1->4->3
     * 示例2： head = [] []
     * 示例3: head = [1] [1]
     * 链表中节点的数目在1-100内
     * 0 <= node.val <= 100
     * @param node
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = head.next;
        head.next = swapPairs(newHead.next);
        newHead.next = head;
        return newHead;
    }

    /**
     * 重排链表
     * 给定一个链表L  L0->L1->...->Ln-1->Ln
     * 将其重新排序后变为 L0->Ln->L1->Ln-1->L2->Ln-2
     * 你不能够只是单纯的修改链表的值，而是需要修改真实的节点
     * 示例1：给定链表 1->2->3->4  重新排序 1->4->2->3
     * 示例2: 给定链表 1->2->3->4->5 重新排序 1->5->2->4->3
     *
     *  因为链表不支持下标访问，所以我们无法随机访问链表中任意位置的元素。
     *  因此比较容易想到的一个方法是，我们利用线性表存储该链表，然后利用线性表可以下标访问的特点，直接按照顺序访问指定的元素，重建该链表即可。
     * @param head
     */
    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        List<ListNode> list = new ArrayList<>();
        ListNode temp = head;
        while (head != null) {
            list.add(temp);
            temp = temp.next;
        }
        int i = 0;
        int j = list.size() - 1;
        while (i < j) {
            list.get(i).next = list.get(j);
            i ++;
            if (i == j) {
                break;
            }
            list.get(j).next = list.get(i);
            j --;
        }
        list.get(i).next = null;
    }

    /**
     * 分割链表
     * 编写程序以x为基准分割链表，使得所有小于x的节点排在大于或等于x的节点之前。
     * 如果链表中包含x,x只需出现小于x的元素之后(如下所示)。
     * 分割元素x只需处于右半部分即可，其不需要被置于左右两部分之间。
     * 输入: head = 3->5->8->5->10->2->1, x = 5
     * 输出: 3->1->2->10->5->5->8
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        // 定义两个链表
        ListNode leftHead = new ListNode(-1);
        ListNode rightHead = new ListNode(-1);
        ListNode left = leftHead;
        ListNode right = rightHead;
        while (head != null) {
            if (head.val < x) {
                left.next = head;
                left = left.next;
            } else {
                right.next = head;
                right = right.next;
            }
            head = head.next;
        }
        left.next = rightHead.next;
        return leftHead.next;
    }

    public ListNode partitionV1(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }
        //左链表虚拟头节点
        ListNode leftOne = new ListNode(-1);
        ListNode left = leftOne;
        //右链表虚拟头节点
        ListNode rightOne = new ListNode(-1);
        ListNode right = rightOne;
        //因为有断开操作,所以需要记录下一个遍历的节点
        ListNode next;
        while (head != null) {
            next = head.next;
            //值小于x的节点都接在左链表,其他接在右链表上
            if (head.val < x) {
                left.next = head;
                left = left.next;
            } else {
                right.next = head;
                right = right.next;
            }
            //断开操作
            head.next = null;
            head = next;
        }
        //拼接操作 左链表尾节点指向右链表头节点
        left.next = rightOne.next;
        return leftOne.next;
    }

    /**
     * 分隔链表
     * 给定一个头节点为root的链表，编写一个函数以将链表分隔为k个连续的部分。
     * 每部分的长度应该尽可能的相等；任意两部分的长度差不能超过1，也就是说可能有些部分为null。
     * 这k个部分应该按照在链表中出现的顺序进行输出，并且排在前面的部分的长度应该大于或等于后面的长度。
     * 返回一个符合上述规则的链表的列表。
     * 1->2->3->4 k=5  [[1] [2] [3] [4] null]
     * @param root
     * @param k
     * @return
     */
    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode curr = root;
        int n = 0;
        while (curr != null) {
            n ++;
            curr = curr.next;
        }
        int width = n / k;
        int rem = n % k;
        ListNode[] ans = new ListNode[k];
        curr = root;
        for (int i = 0; i < k; i++) {
            ListNode head = new ListNode(0);
            ListNode write = head;
            for (int j = 0; j < width + (i < rem ? 1 : 0); j ++) {
                write = write.next = new ListNode(curr.val);
                if (curr != null) {
                    curr = curr.next;
                }
            }
            ans[i] = head.next;
        }
        return ans;
    }

    public ListNode[] splitListToPartsV2(ListNode root, int k) {
        ListNode cur = root;
        int N = 0;
        while (cur != null) {
            cur = cur.next;
            N++;
        }

        int width = N / k, rem = N % k;

        ListNode[] ans = new ListNode[k];
        cur = root;
        for (int i = 0; i < k; ++i) {
            ListNode head = cur;
            for (int j = 0; j < width + (i < rem ? 1 : 0) - 1; ++j) {
                if (cur != null) cur = cur.next;
            }
            if (cur != null) {
                ListNode prev = cur;
                cur = cur.next;
                prev.next = null;
            }
            ans[i] = head;
        }
        return ans;
    }

    /**
     * 环路检测，并找出环路的头节点
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            //快慢指针，快指针每次走两步，慢指针每次走一步
            fast = fast.next.next;
            slow = slow.next;
            //先判断是否有环，
            if (slow == fast) {
                //确定有环之后才能找环的入口
                while (head != slow) {
                    //两相遇指针，一个从头结点开始，
                    //一个从相遇点开始每次走一步，直到
                    //再次相遇为止
                    head = head.next;
                    slow = slow.next;
                }
                return slow;
            }
        }
        return null;
    }

    /**
     * 链表求和
     *    给定两个用链表表示的整数，每个节点包含一个数位。
     *    这些数位是反向存放的，也就是个位排在链表首部。
     *    编写函数对这两个整数求和，并用链表形式返回结果。
     * 示例：
     *      输入：(7->1->6) + (5->9->2)，即617 + 295
     * 输出：2->1->9，即912
     * 进阶：思考一下，假设这些数位是正向存放的，又该如何解决呢?
     * 示例：
     *      输入：(6 -> 1 -> 7) + (2 -> 9 -> 5)，即617 + 295
     *      输出：9 -> 1 -> 2，即912
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null;
        ListNode trim = head;
        int rem = 0;
        while (l1 != null || l2 != null || rem != 0) {
            int a = l1 != null ? l1.val : 0;
            int b = l2 != null ? l2.val : 0;
            int ans = a + b + rem;
            rem = ans / 10;
            ans = ans % 10;
            if (trim == null) {
                trim = new ListNode(ans);
            } else {
                trim.next = new ListNode(ans);
                trim = trim.next;
            }
            l1 = l1 != null ? l1.next : null;
            l2 = l2 != null ? l2.next : null;
        }
        return head;
    }
}
