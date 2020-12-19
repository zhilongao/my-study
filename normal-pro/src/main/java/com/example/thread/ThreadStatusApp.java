package com.example.thread;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/15 11:15
 * @since v1.0.0001
 */
public class ThreadStatusApp {

    public static String CONFIG_PATTERN = "\\[(\\w|\\||\\,)+\\]";

    public static void main(String[] args) {
        String configStr = "[OrigCustomerMapper|BpCustomerMapper][idCard|prcid,sex|gender,houseId|houseInfo,position|companyDuty]";
        Pattern pattern = Pattern.compile(CONFIG_PATTERN);
        Matcher matcher = pattern.matcher(configStr);
        matcher.find();
        String mapperStr = matcher.group(0).replace("[", "").replace("]", "");
        System.err.println(mapperStr);
        String[] split = mapperStr.split("\\|");
        String origMapper = new StringBuilder(split[0].substring(0, 1).toLowerCase()).append(split[0].substring(1))
                .toString();
        String dstMapper = new StringBuilder(split[1].substring(0, 1).toLowerCase()).append(split[1].substring(1))
                .toString();
        System.err.println(origMapper);
        System.err.println(dstMapper);
        String substring = split[1].substring(0, split[1].length() - 6);
        System.err.println(substring);

        matcher.find();
        String columnStr = matcher.group(0).replace("[", "").replace("]", "");
        System.err.println(columnStr);

        Map<String, String> columnMap = new HashMap<String, String>();
        if (StringUtils.isNotBlank(columnStr)) {
            String[] columnArr = columnStr.split(",");
            for (String column : columnArr) {
                String[] splitA = column.split("\\|");
                columnMap.put(splitA[0], splitA[1]);
            }
        }
        for (Map.Entry entry : columnMap.entrySet()) {
            System.err.println(entry.getKey() + "--" + entry.getValue());
        }
    }
}
