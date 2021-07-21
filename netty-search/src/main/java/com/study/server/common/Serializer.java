package com.study.server.common;

/**
 * 序列化
 */
public interface Serializer {
    /**
     * json序列化
     */
    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java对象转二进制
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制转java对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
