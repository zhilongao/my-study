package com.example.business.snap;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/10 13:56
 * @since v1.0.0001
 */
public class PtzValueItem {
    /**
     * P参数（水平参数），浮点型，范围：0 – 36.000 备注： 不同设备会有 差异，具体根据能力集获取
     */
    private String pValue;
    /**
     * T参数（垂直参数），浮点型，范围：90.000–270.000 备注：不同设备会有差异，具体根据能力集获取
     */
    private String tValue;
    /**
     * Z参数（变倍参数），浮点型，范围：0-100000 备注：不同设备会有差异，具体根据能力集获取
     */
    private String zValue;
}
