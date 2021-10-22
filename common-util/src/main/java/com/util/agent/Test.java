package com.util.agent;

import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

public class Test {
    public static void main(String[] args) {
        String agent = "Macintosh";
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
        // 操作系统信息
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 设备类型
        DeviceType deviceType = operatingSystem.getDeviceType();
        //是否PC
        if("COMPUTER".equals(deviceType)) {
            System.err.println("111");
        } else {
            System.err.println("222");
        }
    }
}
