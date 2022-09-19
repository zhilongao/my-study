package com.example.algorithm;


public class App2 {
    public static void main(String[] args) {
        int x1 = 9807600;
        int x2 = 980760001;
        System.err.println(reverse(x1));
        System.err.println(reverse(x2));
    }

    // 修改其它文件的内容

    // 我也修改了这个文件的内容啦 啊哈哈滴滴滴


    /**
     * 整数的反转
     * @param x 整数
     * @return 反转之后的数
     */
    public static int reverse(int x) {
        StringBuilder sb = new StringBuilder();
        int r  = 0;
        while ( x != 0) {
            r = x % 10;
            x = x / 10;
            sb.append(r);
        }
        return Integer.parseInt(sb.toString());
    }
}

