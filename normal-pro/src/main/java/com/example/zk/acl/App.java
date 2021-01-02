package com.example.zk.acl;

public class App {

    public static void main(String[] args) {
        // AclOperate.aclOperateV1();
        try {
            AclOperate.aclOperateV2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
