package com.study.job.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 任务调度类
 */
@Data
public class SysJob implements Serializable {
    private static final long serialVersionUID = 3746569356086283114L;
    /**
     * 任务id
     */
    private Integer id;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务组别
     */
    private String jobGroup;
    /**
     * 任务表达式
     */
    private String jobCron;
    /**
     * 类路径
     */
    private String jobClassPath;
    /**
     * 任务状态 0:停用 1:启用
     */
    private Integer jobStatus;
    /**
     * 任务状态描述
     */
    private String jobStatusStr;
    /**
     * 任务具体描述
     */
    private String jobDescribe;
    /**
     * 传递map参数
     */
    private String jobDataMap;

}
