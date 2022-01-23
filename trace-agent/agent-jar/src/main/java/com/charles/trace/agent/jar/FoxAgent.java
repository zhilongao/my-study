package com.charles.trace.agent.jar;

import java.lang.instrument.Instrumentation;

public class FoxAgent {

    public static void premain(String args, Instrumentation instrumentation){
        System.out.println("premain：" + args);
    }
}
