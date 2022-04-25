package com.charles.dubbo.test.po;

public class PathNode {
    /**
     * 接口名称
     */
    private String interfaceName;
    /**
     * 方法列表
     */
    private String methods;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }
}
