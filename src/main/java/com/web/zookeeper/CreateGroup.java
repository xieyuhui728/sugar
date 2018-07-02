package com.web.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by xieyuhui on 2018/6/29.
 * 创建一个分组
 */
public class CreateGroup implements Watcher {

    private static final int TIME_OUT = 5000;
    private ZooKeeper zk;
    private CountDownLatch latch = new CountDownLatch(1);


    public void connect(String host) throws IOException, InterruptedException {
        zk = new ZooKeeper(host, TIME_OUT, this);
        latch.await();
    }

    public void create(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        String createPath = zk.create(path, null/*data*/, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Create:" + createPath);
    }

    public void close()throws InterruptedException{
        zk.close();
    }
    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            latch.countDown();
        }
    }

    public static void main(String[] args) throws Exception {
        CreateGroup group = new CreateGroup();
        group.connect("localhost");
        group.create("zoo");
        group.close();
    }
}
