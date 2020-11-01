package com.example.serial.basic;

import com.example.serial.basic.entity.UserProto;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoMainApp {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        UserProto.User user = UserProto.User.newBuilder().setName("jack11").setAge(22).setStatus(-300).build();
        ByteString bytes = user.toByteString();
        System.err.println("length:" + bytes.size());
        for (byte bt : bytes.toByteArray()) {
            System.err.print(bt + " ");
        }
        System.err.println();
        UserProto.User userRec = UserProto.User.parseFrom(bytes);
        System.err.println("-------------------------");
        System.err.println(userRec);
    }

}
