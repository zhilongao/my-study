package com.example.redis.type;

import com.example.redis.util.JedisUtil;
import org.apache.commons.io.FileUtils;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/23 15:33
 * @since v1.0.0001
 */
public class ByteType {
    public static void main(String[] args) throws IOException {
        System.err.println(Charset.defaultCharset());
        File file = new File("D:\\work\\10001.png");
        byte[] bytes = FileUtils.readFileToByteArray(file);
        System.err.println(bytes);
        String str = new String(bytes);
        System.err.println(str);
        System.err.println(str.length());
        Jedis jedis = JedisUtil.getInstance().getJedis();
        jedis.set("image:key", str);
    }
}
