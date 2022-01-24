package com.charles.trace.instrument.client;

public class InstrumentClient {
    public static void main(String[] args) {
        HelloService service = new HelloService();
        service.say();
        service.say2();
    }
}
