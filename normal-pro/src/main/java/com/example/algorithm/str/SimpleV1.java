package com.example.algorithm.str;

import java.util.*;

public class SimpleV1 {
    // 检查两个字符串数组是否相等
    // 给你两个字符串数组word1和word2。如果两个数组表示的字符串相同，返回true；否则 返回false 。
    // 数组表示的字符串是由数组中的所有元素按顺序连接形成的字符串。

    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        StringBuilder b1 = new StringBuilder();
        StringBuilder b2 = new StringBuilder();

        int i = 0;
        for (;;) {
            if (i == word1.length || i == word2.length) {
                break;
            }
            b1.append(word1[i]);
            b2.append(word2[i]);
            i++;
        }
        if (i == word1.length) {
            for (int j = i; j < word2.length; j++) {
                b2.append(word2[j]);
            }
        }
        if (i == word2.length) {
            for (int j = 0; j < word1.length; j++) {
                b1.append(word1[j]);
            }
        }
        return b1.toString().equals(b2.toString());
    }

    // 判断字符串的两半是否相似
    // 给你一个偶数长度的字符串 s 。将其拆分成长度相同的两半，前一半为 a ，后一半为 b 。
    // 两个字符串 相似 的前提是它们都含有相同数目的元音（'a'，'e'，'i'，'o'，'u'，'A'，'E'，'I'，'O'，'U'）。注意，s 可能同时含有大写和小写字母。
    // 如果 a 和 b 相似，返回 true ；否则，返回 false
    public boolean halvesAreAlike(String s) {
        boolean[] c = new boolean[128];
        c['A'] = c['E'] = c['I'] = c['O'] = c['U'] = c['a'] = c['e'] = c['i'] = c['o'] = c['u'] = true;
        char[] chars = s.toCharArray();
        int index = 0;
        int limit = s.length() / 2;
        int index1 = 0;
        int index2 = limit;
        int num1 = 0;
        int num2 = 0;
        for(;;) {
            if (index == limit) {
                break;
            }
            if (c[chars[index1]]) {
                num1++;
            }
            if (c[chars[index2]]) {
                num2++;
            }
            index1++;
            index2++;
            index++;
        }
        return num1 == num2;
    }

}
