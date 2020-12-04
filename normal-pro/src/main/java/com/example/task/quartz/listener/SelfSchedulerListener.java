package com.example.task.quartz.listener;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/4 20:43
 * @since v1.0.0001
 */
public class SelfSchedulerListener implements SchedulerListener {
    @Override
    public void jobScheduled(Trigger trigger) {
        String jobName = trigger.getJobKey().getName();
        System.err.println(jobName + " has been scheduled");
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {

    }

    @Override
    public void triggerFinalized(Trigger trigger) {

    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {

    }

    @Override
    public void triggersPaused(String s) {

    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {

    }

    @Override
    public void triggersResumed(String s) {

    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        String jobName = jobDetail.getKey().getName();
        System.err.println(jobName + " has been added");
    }

    @Override
    public void jobDeleted(JobKey jobKey) {

    }

    @Override
    public void jobPaused(JobKey jobKey) {

    }

    @Override
    public void jobsPaused(String s) {

    }

    @Override
    public void jobResumed(JobKey jobKey) {

    }

    @Override
    public void jobsResumed(String s) {

    }

    @Override
    public void schedulerError(String s, SchedulerException e) {

    }

    @Override
    public void schedulerInStandbyMode() {

    }

    @Override
    public void schedulerStarted() {
        System.err.println("SelfSchedulerListener schedulerStarted");
    }

    @Override
    public void schedulerStarting() {
        System.err.println("SelfSchedulerListener schedulerStarting");
    }

    @Override
    public void schedulerShutdown() {

    }

    @Override
    public void schedulerShuttingdown() {

    }

    @Override
    public void schedulingDataCleared() {

    }
}
