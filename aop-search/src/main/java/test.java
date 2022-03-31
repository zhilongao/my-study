import java.security.MessageDigest;

public class test {
    public static void main(String[] args) {
//        String arg = args[0];
        String s1 = "{\"taskId\":\"106CB2358A3944AAE3EFAC12DE868BDF\",\"orderId\":\"17640573\",\"cancelMsg\":\"王大大测试\"}" + System.currentTimeMillis() + "BxvSsBfB970" + "91ae9646dfbf46f1bca64a3e6b68d8d1";

        System.out.println(s1);
        String s2 = md5(s1, true);
        System.out.println(s2);
//        JSONObject jsonObject = JSONObject.parseObject(s1);
//        System.out.println(jsonObject);
    }

    public static String md5(String source, boolean toUpperCase) {
//        source = source + salt;

        StringBuilder sb = new StringBuilder(32);

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(source.getBytes("utf-8"));

            for (int i = 0; i < array.length; i++) {
                if(toUpperCase) {
                    sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
                } else {
                    sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
                }
            }
        } catch (Exception e) {
//            log.error("Can not encode the string '" + source + "' to MD5!", e);
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}