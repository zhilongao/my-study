package com.example.thread;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/19 11:23
 * @since v1.0.0001
 */
public class T02_Fork_Join {

    // ForkJoinPool线程池

    public static void main(String[] args) {
        long[] array = new long[20000];
        for (int i = 0; i < array.length; i++) {
            array[i] = random();
        }
        long start1 = System.currentTimeMillis();
        long expectedSum = 0;
        for (int i = 0; i < array.length; i++) {
            expectedSum += array[i];
        }
        long start2 = System.currentTimeMillis();
        System.err.println("Expected sum: " + expectedSum);
        System.err.println("normal consume compute time " + (start2 - start1) + "ms.");
        ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = ForkJoinPool.commonPool().invoke(task);
        long endTime = System.currentTimeMillis();
        System.err.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
    }

    static class SumTask extends RecursiveTask<Long> {
        static final int THRESHOLD = 500;
        long[] array;
        int start;
        int end;

        SumTask(long[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                // 如果任务足够的小，直接计算
                long sum = 0L;
                for (int i = start; i < end; i ++) {
                    sum += array[i];
                    // 故意放慢计算速度:
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return sum;
            }
            // 任务太大，一分为2
            int middle = (end + start) / 2;
            System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
            SumTask sumTask1 = new SumTask(this.array, start, middle);
            SumTask sumTask2 = new SumTask(this.array, middle, end);
            invokeAll(sumTask1, sumTask2);
            Long sum1 = sumTask1.join();
            Long sum2 = sumTask2.join();
            Long result = sum1 + sum2;
            System.out.println("result = " + sum1 + " + " + sum2 + " ==> " + result);
            return result;
        }
    }



    static Random random = new Random(0);

    static long random() {
        return random.nextInt(10000);
    }
}
