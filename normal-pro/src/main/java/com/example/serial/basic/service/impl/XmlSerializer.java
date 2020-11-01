package com.example.serial.basic.service.impl;

import com.example.serial.basic.service.ISerializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * xml的序列化方式
 */
public class XmlSerializer implements ISerializer {

    XStream stream = new XStream(new DomDriver());

    @Override
    public <T> byte[] serialize(T object) {
        return stream.toXML(object).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data) {
        return (T)stream.fromXML(new String(data));
    }
}
