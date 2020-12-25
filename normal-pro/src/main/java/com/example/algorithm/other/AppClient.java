package com.example.algorithm.other;

import java.util.HashSet;
import java.util.Set;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/25 15:18
 * @since v1.0.0001
 */
public class AppClient {
    // 给你一个由不同字符组成的字符串allowed和一个字符串数组words。
    // 如果一个字符串的每一个字符都在allowed中，就称这个字符串是一致字符串 。
    // 请你返回words数组中一致字符串的数目。

    // 示例 1：
    // 输入： allowed="ab", words=["ad", "bd", "aaab", "baa", "badab"]
    // 输出：2

    // 示例 2：
    // 输入：allowed = "abc", words = ["a","b","c","ab","ac","bc","abc"]
    // 输出：7

    // 示例 3：
    // 输入：allowed = "cad", words = ["cc","acd","b","ba","bac","bad","ac","d"]
    // 输出：4

    public static void main(String[] args) {
        AppClient client = new AppClient();
        String allowed1 = "ab";    String[] words1 = new String[]{"ad", "bd", "aaab", "baa", "badab"};
        String allowed2 = "abc";   String[] words2 = new String[]{"a","b","c","ab","ac","bc","abc"};
        String allowed3 = "cad";   String[] words3 = new String[]{"cc","acd","b","ba","bac","bad","ac","d"};
        int count1 = client.countConsistentStringV2(allowed1, words1);
        int count2 = client.countConsistentStringV2(allowed2, words2);
        int count3 = client.countConsistentStringV2(allowed3, words3);
        System.err.println(count1);
        System.err.println(count2);
        System.err.println(count3);
    }


    public int countConsistentStringV2(String allowed, String[] words) {
        int count = 0;
        for (int i = 0; i < words.length; i ++) {
            boolean flag = true;
            for (int j = 0; j < words[i].length(); j++) {
                if (allowed.indexOf(words[i].charAt(j)) == -1) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                count++;
            }
        }
        return count;
    }

    public int countConsistentStringsV1(String allowed, String[] words) {
        int count = 0;
        Set<Character> sets = new HashSet<>();
        for (int i = 0; i < allowed.length(); i++) {
            sets.add(allowed.charAt(i));
        }
        for (String word : words) {
            boolean contains = true;
            for (int i = 0; i < word.length(); i++) {
                if (!sets.contains(word.charAt(i))) {
                    contains = false;
                    break;
                }
            }
            if (contains) {
                count++;
            }
        }
        return count;
    }


    public String reverseLeftWordsV3(String s, int n) {
        String suffix = s.substring(0, n);
        String prefix = s.substring(n, s.length());
        return prefix + suffix;
    }

    public String reverseLeftWordsV1(String s, int n) {
        StringBuilder builder1 = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        for (int i = 0; i < n; i++) {
            builder1.append(s.charAt(i));
        }
        for (int i = n; i < s.length(); i++) {
            builder2.append(s.charAt(i));
        }
        builder2.append(builder1);
        return builder2.toString();
    }

    public String reverseLeftWordsV2(String s, int n) {
        StringBuilder builder1 = new StringBuilder();
        for (int i = 0; i < n; i++) {
            builder1.append(s.charAt(i));
        }
        String substring = s.substring(n, s.length());
        return substring += builder1.toString();
    }
}
