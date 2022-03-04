package com.charles.trace.agent.jar;

import java.lang.instrument.Instrumentation;

public class FoxAgent {
    // jvm启动时加载agent
    public static void premain(String args, Instrumentation instrumentation){
        System.out.println("premain：" + args);
    }

    public static void premain(String agrs) {
        System.err.print(agrs);
    }

    // jvm运行时加载agent
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.err.print("agentmain:" + agentArgs);
    }
    public static void agentmain(String agentArgs) {

    }

}
