package com.study.server.common;

import com.alibaba.fastjson.JSON;

/**
 * json序列化方式
 */
public class JSONSerializer implements Serializer {

    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
