package com.example.redis.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/21 17:19
 * @since v1.0.0001
 */
public class LruCache {
    /**
     * 以key-val形式存储缓存
     */
    private Map<String, LruNode> map;

    /**
     * 链表的容量
     */
    private int capacity;

    /**
     * 头节点
     */
    private LruNode head;

    /**
     * 尾节点
     */
    private LruNode tail;

    public void set(String key, Object value) {
        LruNode node = map.get(key);
        if (node != null) {
            node = map.get(key);
            node.value = value;

        }
    }

    public Object get(String key) {
        LruNode node = map.get(key);
        if (node != null) {
            // 将刚操作的元素放到head
            remove(node, false);
            setHead(node);
            return node.value;
        }
        return null;
    }


    private void setHead(LruNode node) {
        // 先从链表中删除该元素
        if (head != null) {
            node.next = head;
            head.prev = node;
        }
        head = node;
        if (tail == null) {
            tail = node;
        }
    }

    private void remove(LruNode node, boolean flag) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        node.next = null;
        node.prev = null;
        if (flag) {
            map.remove(node.key);
        }
    }

    public LruCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<String, LruNode>();
    }

    /**
     * 链表中的节点，双链表
     */
    class LruNode {
        String key;
        Object value;
        LruNode prev;
        LruNode next;
        public LruNode(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}
