package com.example.thread;

public class T02_Disorder {

    private static int x = 0, y = 0;

    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (;;){
            i++;
            x = 0; y = 0;
            a = 0; b = 0;
            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                }
            });
            Thread two = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            one.start();
            two.start();
            one.join();
            two.join();
            // 若是没有指令重排序的话，不会出现x==0&&y==0的情况
            // 第1248036次(0,0)
            String result = "第" + i + "次(" + x + "," + y + ")";
            if (x == 0 && y == 0) {
                System.err.println(result);
                break;
            } else {

            }
        }
    }
}
