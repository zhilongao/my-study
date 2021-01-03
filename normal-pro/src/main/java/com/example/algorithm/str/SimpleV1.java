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

    // 1374. 生成每种字符都是奇数个的字符串
    // 给你一个整数n，请你返回一个含n个字符的字符串，其中每种字符在该字符串中都恰好出现奇数次 。
    // 返回的字符串必须只含小写英文字母。如果存在多个满足题目要求的字符串，则返回其中任意一个即可。
    // 示例 1：
    //  输入：n = 4
    //  输出："pppz"
    //  解释："pppz" 是一个满足题目要求的字符串，因为 'p' 出现 3 次，且 'z' 出现 1 次。当然，还有很多其他字符串也满足题目要求，比如："ohhh" 和 "love"。
    //示例 2：
    //输入：n = 2
    //输出："xy"
    //解释："xy" 是一个满足题目要求的字符串，因为 'x' 和 'y' 各出现 1 次。当然，还有很多其他字符串也满足题目要求，比如："ag" 和 "ur"。
    //示例 3：
    //
    //输入：n = 7
    //输出："holasss"
    public String generateTheString(int n) {
        StringBuilder bf = new StringBuilder();
        if(n % 2 == 0) {
            int temp = n / 2;
            if (temp % 2 == 0) {
                temp --;
            }
            for (int i = 0; i < temp; i ++) {
                bf.append("A");
            }
            for (int i = temp; i < n; i++) {
                bf.append("B");
            }
        } else {
            for (int i = 0; i < n; i++) {
                bf.append("a");
            }
        }
        return bf.toString();
    }

    // 1309. 解码字母到整数映射
    // 给你一个字符串s，它由数字（'0'-'9'）和 '#' 组成。我们希望按下述规则将s映射为一些小写英文字符：
    // 字符（'a' - 'i'）分别用（'1' - '9'）表示。
    // 字符（'j' - 'z'）分别用（'10#' - '26#'）表示。 
    // 返回映射之后形成的新字符串。
    // 题目数据保证映射始终唯一。
    // 示例 1：
    //  输入：s = "10#11#12"
    //  输出："jkab"
    //  解释："j" -> "10#" , "k" -> "11#" , "a" -> "1" , "b" -> "2".
    // 示例 2：
    //  输入：s = "1326#"
    //  输出："acz"
    //示例 3：
    //  输入：s = "25#"
    //  输出："y"
    //示例 4：
    //输入：s = "12345678910#11#12#13#14#15#16#17#18#19#20#21#22#23#24#25#26#"
    //输出："abcdefghijklmnopqrstuvwxyz"
    public String freqAlphabets(String s) {
        StringBuilder bf = new StringBuilder();
        char[] map = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (i + 2 < s.length() && s.charAt(i + 1) == '#') {
                s.charAt(i);
            }
        }
        return "";
    }

}
