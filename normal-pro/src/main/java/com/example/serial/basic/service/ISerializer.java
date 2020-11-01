package com.example.serial.basic.service;

public interface ISerializer {

    <T> byte[] serialize(T object);

    <T> T deserialize(byte[] data);
}
