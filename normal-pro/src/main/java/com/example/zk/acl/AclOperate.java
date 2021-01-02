package com.example.zk.acl;

import com.example.zk.util.CuratorUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

public class AclOperate {


    public static void aclOperateV2() throws Exception {
        CuratorFramework framework = CuratorUtil.curatorFramework();
        framework.start();
        List<ACL> list = new ArrayList<>();
        ACL acl = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE,
                new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin")));
        list.add(acl);
        framework.setACL().withACL(ZooDefs.Ids.CREATOR_ALL_ACL).forPath("/temp");
    }

    public static void aclOperateV1() {
        CuratorFramework framework = CuratorUtil.curatorFramework();
        framework.start();
        List<ACL> list = new ArrayList<>();
        try {
            ACL acl = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE,
                    new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin")));
            list.add(acl);
            framework.create().withMode(CreateMode.PERSISTENT).withACL(list).forPath("/auth");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
