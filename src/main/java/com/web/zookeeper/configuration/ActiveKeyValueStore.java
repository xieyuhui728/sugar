package com.web.zookeeper.configuration;

import com.web.zookeeper.ConnectWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;

/**
 * Created by xieyuhui on 2018/7/2.
 */
public class ActiveKeyValueStore extends ConnectWatcher {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    public void write(String path, String value) throws KeeperException, InterruptedException {
        //判断znode是否存在
        Stat stat = zk.exists(path, false);
        if (stat == null) {
            //新增
            zk.create(path, value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            //更新覆盖
            zk.setData(path, value.getBytes(CHARSET), -1);
        }
    }

    public String read(String path, Watcher watcher) throws KeeperException, InterruptedException {
        byte[] data = zk.getData(path, watcher, null);
        return new String(data, CHARSET);
    }
}
