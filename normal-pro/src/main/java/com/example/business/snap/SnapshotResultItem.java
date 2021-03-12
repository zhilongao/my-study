package com.example.business.snap;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/10 13:53
 * @since v1.0.0001
 */
public class SnapshotResultItem {
    /**
     * 监控点编号
     */
    private String indexCode;
    /**
     * 预置点编号
     */
    private String presetCode;
    /**
     * 返回PTZ值
     */
    private PtzValueItem ptzValue;
    /**
     * 业务参数
     */
    private String extra;
    /**
     * 图片ID
     */
    private String uuid;
    /**
     * 图片URL
     */
    private String picUrl;
    /**
     * 缩略图URL
     */
    private String picUrlThumb;
    /**
     * 图片属性
     */
    private PicAttributeItem picAttribute;
    /**
     * collectTime
     */
    private String collectTime;

}
