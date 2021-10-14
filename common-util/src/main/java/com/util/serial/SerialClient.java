package com.util.serial;

import com.alibaba.fastjson.JSON;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class SerialClient<T> {

    ObjectMapper mapper = new ObjectMapper();

    public static final String SERIAL_TYPE_STREAM = "stream";

    public static final String SERIAL_TYPE_JACKSON = "jackson";

    public static final String SERIAL_TYPE_FASTJSON = "fastjson";

    public static final String SERIAL_TYPE_HESSIAN = "hessian";

    public static final String SERIAL_TYPE_PROTO = "proto";

    public String serialType = SERIAL_TYPE_STREAM;

    public void setSerialType(String serialType) {
        this.serialType = serialType;
    }

    public String getSerialType() {
        return this.serialType;
    }


    public byte[] serialize(T object) {
        String serialType = this.serialType;
        if (serialType.equals(SERIAL_TYPE_STREAM)) {
            return serializeByStream(object);
        } else if (serialType.equals(SERIAL_TYPE_JACKSON)) {
            return serializeByJackson(object);
        } else if (serialType.equals(SERIAL_TYPE_FASTJSON)) {
            return serialByFastJson(object);
        } else if (serialType.equals(SERIAL_TYPE_HESSIAN)) {
            return serialByHessian(object);
        } else if (serialType.equals(SERIAL_TYPE_PROTO)) {
            return serialByProto(object);
        } else {
            return serializeByStream(object);
        }
    }

    public T deserialization(byte[] data, Class<T> cls) {
        String serialType = this.serialType;
        if (serialType.equals(SERIAL_TYPE_STREAM)) {
            return deserializationByStream(data);
        } else if (serialType.equals(SERIAL_TYPE_JACKSON)) {
            return deserializationByJackson(data, cls);
        } else if (serialType.equals(SERIAL_TYPE_FASTJSON)) {
            return deserializationByFastJson(data, cls);
        } else if (serialType.equals(SERIAL_TYPE_HESSIAN)) {
            return deserializationByHessian(data);
        } else if (serialType.equals(SERIAL_TYPE_PROTO)) {
            return deserializationByProto(data, cls);
        } else {
            return deserializationByStream(data);
        }
    }

    /**
     * 基于流的序列化方式
     * @param object object
     * @return byte[]
     */
    public byte[] serializeByStream(T object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 基于jackson的序列化方式
     * @param object object
     * @return byte[]
     */
    public byte[] serializeByJackson(T object) {
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 基于fastjson的序列化方式
     * @param object object
     * @return byte[]
     */
    public byte[] serialByFastJson(T object) {
        return JSON.toJSONBytes(object);
    }

    /**
     * 基于protobuf的序列化方式
     * @param object object
     * @return byte[]
     */
    public byte[] serialByHessian(T object) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(os);
        try {
            output.writeObject(object);
            output.flush();
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 基于proto的序列化方式
     * @param object object
     * @return byte
     */
    public byte[] serialByProto(T object) {
        Codec<T> codec = (Codec<T>)ProtobufProxy.create(object.getClass());
        try {
            return codec.encode(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 基于流的反序列化方式
     * @param data data
     * @return T
     */
    public T deserializationByStream(byte[] data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 基于jackson的反序列化方式
     * @param data data
     * @param cls cls
     * @return T
     */
    public T deserializationByJackson(byte[] data, Class<T> cls) {
        try {
            return mapper.readValue(data, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 基于fast json的反序列化方式
     * @param data data
     * @param cls cls
     * @return T
     */
    public T deserializationByFastJson(byte[] data, Class<T> cls) {
        return (T) JSON.parseObject(data, cls);
    }

    /**
     * 基于hessian的反序列化
     * @param data data
     * @return T
     */
    public T deserializationByHessian(byte[] data) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        Hessian2Input input = new Hessian2Input(bis);
        try {
            return (T) input.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 基于proto的发序列化方式
     * @param data data
     * @param cls cls
     * @return T
     */
    public T deserializationByProto(byte[] data, Class<T> cls) {
        Codec<T> codec = ProtobufProxy.create(cls, false);
        try {
            return codec.decode(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
