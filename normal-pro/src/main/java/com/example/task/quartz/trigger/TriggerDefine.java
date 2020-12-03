package com.example.task.quartz.trigger;

import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CalendarIntervalTrigger;
import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.TimeOfDay;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/3 19:14
 * @since v1.0.0001
 */
public class TriggerDefine {

    public static void main(String[] args) {
        CalendarIntervalTrigger build = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                // 每天执行一次
                .withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInDays(1))
                .build();

        Trigger dailyTimeIntervalTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                        //第天9：00开始
                        .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(9, 0))
                        //16：00 结束
                        .endingDailyAt(TimeOfDay.hourAndMinuteOfDay(16, 0))
                        //周一至周五执行
                        .onDaysOfTheWeek(1,2,3,4,5)
                        //每间隔1小时执行一次
                        .withIntervalInHours(1)
                        //最多重复100次（实际执行100+1次）
                        .withRepeatCount(100))
                .build();
    }
}
