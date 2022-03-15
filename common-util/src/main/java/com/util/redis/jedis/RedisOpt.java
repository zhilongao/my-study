package com.util.redis.jedis;

/**
 * redis客户端
 */
public class RedisOpt {

    private SimpleProtocol protocol;

    public RedisOpt(String host, int port) {
        this.protocol = new SimpleProtocol(host, port);
    }

    /**
     * set操作
     * @param key key
     * @param value value
     * @return 操作结果
     */
    public String set(String key, String value) {
        String cmd = "set " + key + " " + value + "\r\n";
        String execute = execute(cmd);
        return handleMoved(cmd, execute);
    }

    /**
     * get操作
     * @param key key
     * @return value
     */
    public String get(String key) {
        String cmd = "get " + key + "\r\n";
        String result = execute(cmd);
        result = handleMoved(cmd, result);
        return parse(result);
    }

    public String handleMoved(String cmd, String result) {
        if (result.contains("-MOVED")) {
            String[] s = result.split(" ");
            String[] split = s[2].split(":");
            String host = split[0];
            String port = split[1].trim();
            System.err.printf("redis connect moved, original host:%s, port:%s\n", protocol.getHost(), protocol.getPort());
            System.err.printf("redis connect moved, moved host:%s, port:%s\n", host, port);
            protocol.setHost(host);
            protocol.setPort(Integer.parseInt(port));
            return execute(cmd).trim();
        } else {
            return result.trim();
        }
    }

    public String parse(String result) {
        String[] split = result.split("\n");
        if (split.length == 1) {
            return "";
        } else {
            return split[1].trim();
        }
    }

    public String execute(String cmd) {
        String result = "";
        try {
            result = protocol.execute(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
