package com.web.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by xieyuhui on 2018/6/29.
 * 获取组下的节点
 */
public class ListGroup extends ConnectWatcher {

    public void list(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        try {
            List<String> children = zk.getChildren(path, false);
            if (CollectionUtils.isEmpty(children)) {
                System.out.println(String.format("No memberName in group %s\n" + groupName));
                System.exit(1);
            }
            for (String childName : children) {
                System.out.println(childName);
            }
        } catch (KeeperException e) {
            System.out.println(String.format("Group %s does not exists\n" , groupName));
        }

    }

    public static void main(String[] args) throws Exception {
        ListGroup listGroup = new ListGroup();
        listGroup.connect("localhost");
        listGroup.list("zoo");
        listGroup.close();
    }
}
