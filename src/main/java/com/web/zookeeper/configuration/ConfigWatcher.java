package com.web.zookeeper.configuration;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.IOException;

/**
 * Created by xieyuhui on 2018/7/2.
 * 观察者
 */
public class ConfigWatcher implements Watcher {
    private ActiveKeyValueStore store;

    public ConfigWatcher(String hosts) throws IOException, InterruptedException {
        store = new ActiveKeyValueStore();
        store.connect(hosts);
    }

    public void displayConfig() throws KeeperException, InterruptedException {
        String value = store.read(ConfigUpdater.PATH, this);
        System.out.println(String.format("Read %s as %s\n", ConfigUpdater.PATH, value));
    }

    @Override
    public void process(WatchedEvent event) {
        //监听node改变事件
        if (event.getType() == Event.EventType.NodeDataChanged) {
            try {
                displayConfig();
            } catch (KeeperException e) {
                System.out.println(String.format("KeeperException：%s.Exiting \n", e));
            } catch (InterruptedException e) {
                System.out.printf(String.format("InterruptedException：%s.Exiting \n", e));
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ConfigWatcher configWatcher = new ConfigWatcher("localhost");
        configWatcher.displayConfig();
        Thread.sleep(Long.MAX_VALUE);
    }
}
