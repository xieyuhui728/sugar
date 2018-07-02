package com.web.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

/**
 * Created by xieyuhui on 2018/6/29.
 * 加入组
 */
public class JoinGroup extends ConnectWatcher {


    public void join(String groupName, String memberName) throws KeeperException, InterruptedException {
        String path = "/" + groupName + "/" + memberName;
        String createPath = zk.create(path, null/*data*/, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Create:" + createPath);
    }

    public static void main(String[] args) throws Exception{
        JoinGroup joinGroup = new JoinGroup();
        joinGroup.connect("localhost");
        joinGroup.join("zoo","duck");
        Thread.sleep(Long.MAX_VALUE);
    }
}
