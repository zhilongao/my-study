package com.charles.trace.bytebuddy.client;

public class ByteBuddyClient {
    public static void main(String[] args) {
        HelloService service = new HelloService();
        service.say();
        service.say2();
    }
}
