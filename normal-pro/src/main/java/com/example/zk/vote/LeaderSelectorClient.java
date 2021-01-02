package com.example.zk.vote;

import com.example.zk.util.CuratorUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.io.Closeable;

public class LeaderSelectorClient extends LeaderSelectorListenerAdapter implements Closeable {
    // 当前进程的名称
    private String name;

    // leader选举的api
    private LeaderSelector leaderSelector;

    // leaderPath
    private static final String path = "/leader";

    // 防止进程退出
    private CountDownLatch latch = new CountDownLatch(1);


    public LeaderSelectorClient() {

    }

    public LeaderSelectorClient(String name) {
        this.name = name;
    }

    public void start(){
        //开始竞争leader
        leaderSelector.start();
    }

    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        // 如果进入当前的方法，意味着当前的进程获得了锁。获得锁以后，这个方法会被回调
        // 这个方法执行结束之后，表示释放leader权限
        System.err.println(name + " 现在是leader了");
        latch.await();
    }

    @Override
    public void close() throws IOException {
        leaderSelector.close();
    }

    public LeaderSelector getLeaderSelector() {
        return leaderSelector;
    }

    public void setLeaderSelector(LeaderSelector leaderSelector) {
        this.leaderSelector = leaderSelector;
    }

    public static void main(String[] args) throws IOException {
        String processNo = UUID.randomUUID().toString();
        String processName = "进程:" + processNo;
        CuratorFramework framework = CuratorUtil.curatorFramework();
        framework.start();
        LeaderSelectorClient leaderSelectorClient = new LeaderSelectorClient(processName);
        LeaderSelector leaderSelector = new LeaderSelector(framework,path, leaderSelectorClient);
        leaderSelectorClient.setLeaderSelector(leaderSelector);
        //开始选举
        leaderSelectorClient.start();
        System.in.read();
    }
}
