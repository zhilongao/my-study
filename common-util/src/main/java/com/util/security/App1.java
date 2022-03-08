package com.util.security;

public class App1 {

    public static void main(String[] args) {
        String token = "PWYJNwX4Zb1gUMYOEUhwHfIAssdGQTlZUAO8eN2crtU";
        SecurityUtilV3 inst = SecurityUtilV3.getInst();
        String desString = inst.getDesString(token);
        System.err.println(desString);
    }
}
