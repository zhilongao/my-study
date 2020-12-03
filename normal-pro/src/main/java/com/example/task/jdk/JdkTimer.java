package com.example.task.jdk;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/3 19:25
 * @since v1.0.0001
 */
public class JdkTimer {
    /**
     * 采用jdk的java.util.Timer和java.util.TimerTask来实现任务的调度
     */
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new JdkTimerTask();
        timer.schedule(task, 5000L, 1000L);
    }
}
