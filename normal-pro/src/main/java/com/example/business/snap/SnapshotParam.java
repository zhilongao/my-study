package com.example.business.snap;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/10 14:05
 * @since v1.0.0001
 */
public class SnapshotParam {
    /**
     *监控点编号
     */
    private String indexCode;
    /**
     * 预置点编号，字符串为-1 表示当前视野；支持传入单个预置点编号
     */
    private String presetCode;
    /**
     * 存储标记，0表示临时存储， 1表示永久存储；
     */
    private String storageTag;
    /**
     * 详细使用方式，请查看消 息中间件数据对接章节
     */
    private String mqKey;
    /**
     * 组件ID，表示请求来源
     */
    private String comId;
    /**
     * 用于透传给应用的业务参数
     */
    private String extra;

}
