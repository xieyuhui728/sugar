package com.web.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by xieyuhui on 2018/6/29.
 * 连接zookeeper服务
 */
public class ConnectWatcher implements Watcher {

    private static final int TIME_OUT = 5000;
    protected ZooKeeper zk;
    private CountDownLatch latch = new CountDownLatch(1);


    public void connect(String host) throws IOException, InterruptedException {
        zk = new ZooKeeper(host, TIME_OUT, this);
        latch.await();
    }

    public void close() throws InterruptedException {
        zk.close();
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            latch.countDown();
        }
    }
}
