package com.web.zookeeper;

import org.apache.zookeeper.KeeperException;

import java.util.List;

/**
 * Created by xieyuhui on 2018/6/29.
 * 删除分组
 */
public class DeleteGroup extends ConnectWatcher {

    public void delete(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        try {
            List<String> children = zk.getChildren(path, false);
            //先删除分组下的子节点
            for (String child : children) {
                zk.delete(path + "/" + child, -1);
            }
            zk.delete(path, -1);
        }catch (KeeperException e){
            System.out.println(String.format("Group %s does not exists\n" , groupName));
        }
    }

    public static void main(String[] args) throws Exception{
        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connect("localhost");
        deleteGroup.delete("zoo");
        deleteGroup.close();
    }
}
