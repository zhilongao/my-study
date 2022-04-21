import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;

public class test1 {

    public static void main(String[] args) {
        String name1 = "com.kuaidi100.sentapi.third.service.yto.newplatform.YtoNewPlatformService";
        String name2 = "com.kuaidi100.sentapi.third.service.jtexpress.newplaform.JtNewPlatformService";
        int length = 36;
        String res1 = handle(name1, length);
        String res2 = handle(name2, length);
        System.err.println(res1);
        System.err.println(res2);
        BigDecimal b1 = new BigDecimal(0.1);
        BigDecimal b2 = new BigDecimal("0.1");
        System.err.println(b1);
        System.err.println(b2);
    }

    public static String handle(String name, int length) {
        if (name.length() <= length) {
            return name;
        }
        String[] arr = name.split("\\.");
        int index = 0;
        StringBuilder sb = new StringBuilder(name);
        while (sb.length() > length) {
            sb.delete(0, sb.length());
            for (int i = 0; i < arr.length; i++) {
                String temp = arr[i];
                if (i <= index) {
                    sb.append(temp.charAt(0));
                } else {
                    sb.append(temp);
                }
                if (i != arr.length - 1) {
                    sb.append(".");
                }
            }
            index ++;
        }
        return sb.toString();
    }

}
