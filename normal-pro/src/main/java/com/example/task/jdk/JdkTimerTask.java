package com.example.task.jdk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/3 19:26
 * @since v1.0.0001
 */
public class JdkTimerTask extends TimerTask {
    @Override
    public void run() {
        Date executeTime = new Date(this.scheduledExecutionTime());
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(executeTime);
        System.out.println("任务执行了：" + dateStr);
    }
}
