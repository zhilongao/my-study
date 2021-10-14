package com.util.serial;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.util.serial.SerialClient.*;

public class ClientApp {
    public static void main(String[] args) {
        ClientApp app = new ClientApp();
        SerialClient<WrapperCls> client = new SerialClient<WrapperCls>();
        int size = 10000;
        List<WrapperCls> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            WrapperCls obj = new WrapperCls();
            obj.setAge(i);
            obj.setName("test" + i);
            list.add(obj);
        }
        List<SerialMethod> methods = new ArrayList<>();
        methods.add(new SerialMethod(SERIAL_TYPE_STREAM));
        methods.add(new SerialMethod(SERIAL_TYPE_JACKSON));
        methods.add(new SerialMethod(SERIAL_TYPE_FASTJSON));
        methods.add(new SerialMethod(SERIAL_TYPE_HESSIAN));
        methods.add(new SerialMethod(SERIAL_TYPE_PROTO));
        for (SerialMethod m : methods) {
            client.setSerialType(m.getType());
            long start = System.currentTimeMillis();
            List<byte[]> results = app.testSerialize(client, list);
            long end1 = System.currentTimeMillis();
            List<WrapperCls> wrapperCls = app.testDeserialization(client, results);
            long end2 = System.currentTimeMillis();
            m.setSerializeTime(end1 - start);
            m.setDeserializationTime(end2 - end1);
        }
        methods.sort(new Comparator<SerialMethod>() {
            @Override
            public int compare(SerialMethod o1, SerialMethod o2) {
                return (int) (o1.getSerializeTime() - o2.getSerializeTime());
            }
        });
        System.err.println("------------序列化排名------------");
        for (SerialMethod m : methods) {
            System.err.println(m.getType() + "   \t\t\t耗时:" + m.getSerializeTime());
        }
        System.err.println("\n------------反序列化排名------------");
        methods.sort(new Comparator<SerialMethod>() {
            @Override
            public int compare(SerialMethod o1, SerialMethod o2) {
                return (int) (o1.getDeserializationTime() - o2.getDeserializationTime());
            }
        });
        for (SerialMethod m : methods) {
            System.err.println(m.getType() + "   \t\t\t耗时:" + m.getDeserializationTime());
        }

    }

    private List<byte[]> testSerialize(SerialClient<WrapperCls> client, List<WrapperCls> list) {
        List<byte[]> results = new ArrayList<>();
        for (WrapperCls cls : list) {
            byte[] result = client.serialize(cls);
            results.add(result);
        }
        return results;
    }

    private List<WrapperCls> testDeserialization(SerialClient<WrapperCls> client, List<byte[]> list) {
        List<WrapperCls> results = new ArrayList<>();
        for (byte[] b : list) {
            WrapperCls obj = client.deserialization(b, WrapperCls.class);
            results.add(obj);
        }
        return results;
    }


    public static class SerialMethod {
        public SerialMethod(String type) {
            this.type = type;
        }
        /**
         * 序列化类型
         */
        private String type;
        /**
         * 序列化耗时
         */
        private long serializeTime;
        /**
         * 反序列化耗时
         */
        private long deserializationTime;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getSerializeTime() {
            return serializeTime;
        }

        public void setSerializeTime(long serializeTime) {
            this.serializeTime = serializeTime;
        }

        public long getDeserializationTime() {
            return deserializationTime;
        }

        public void setDeserializationTime(long deserializationTime) {
            this.deserializationTime = deserializationTime;
        }
    }


    public static class WrapperCls implements Serializable {

        @Protobuf(fieldType = FieldType.INT32, required = true, order = 1)
        private int age;

        @Protobuf(fieldType = FieldType.STRING, required = true, order = 2)
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
