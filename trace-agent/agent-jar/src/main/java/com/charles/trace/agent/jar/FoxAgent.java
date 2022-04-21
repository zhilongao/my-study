package com.charles.trace.agent.jar;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class FoxAgent {
    // jvm启动时加载agent
    public static void premain(String args, Instrumentation instrumentation) {
        long start = System.currentTimeMillis();
        System.err.println("主函数运行,time:" + start);
        System.out.println("主函数运行,参数:" + args);
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                // only print className
                System.err.println(className);
                return classfileBuffer;
            }
        }, false);
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
