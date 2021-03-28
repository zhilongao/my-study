package com.example.algorithm.linkedlist;

import java.util.*;

public class App1 {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String message = scanner.next();
        String result = sortMessage(message);
        System.out.println(result);
    }

    public static String sortMessage(String message) {
        // 1. 切割字符串
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        List<String> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            // 字母
            if (c - 'a' >= 0 && c - 'a' <= 25) {
                if (sb2.length() > 0) {
                    list2.add(Integer.parseInt(sb2.toString()));
                    sb2.setLength(0);
                }
                sb1.append(c);
            // 数字
            } else {
                if (sb1.length() > 0) {
                    list1.add(sb1.toString());
                    sb1.setLength(0);
                }
                sb2.append(c);
            }
        }
        if (sb1.length() > 0) {
            list1.add(sb1.toString());
        }
        if (sb2.length() > 0) {
            list2.add(Integer.parseInt(sb2.toString()));
        }
        // 排序啊
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            String value = list1.get(i);
            Integer num = list2.get(i);
            itemList.add(new Item(value, num));
        }
        list1.clear();
        list1 = null;
        list2.clear();
        list2 = null;
        Collections.sort(itemList, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if ((int)o1.getNum() != o2.getNum()) {
                   return o1.getNum() - o2.getNum();
                }
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        // 拼接
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            String value = item.getValue();
            Integer num = item.getNum();
            while (num > 0) {
                sb.append(value);
                num--;
            }
        }
        return sb.toString();
    }

    public static class Item {
        private String value;
        private Integer num;

        public Item(String value, Integer num) {
            this.value = value;
            this.num = num;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }
}
