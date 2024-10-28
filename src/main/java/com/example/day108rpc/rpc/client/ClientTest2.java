package com.example.day108rpc.rpc.client;/**
 * @Author:zhoayu
 * @Date:2024/4/6 18:48
 * @Description:com.example.day108rpc.rpc.client
 * @version:1.0
 */

import com.example.day108rpc.rpc.pojo.Person;
import com.example.day108rpc.rpc.service.ServiceInterface;
import com.example.day108rpc.rpc.service.impl.ServiceImpl2;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

/**
 * @ClassName ClientTest2
 * @Description //TODO 
 * @Author zhaoyu
 * @Date 2024/4/6
 */
public class ClientTest2 {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException, NotBoundException {
        //订阅Zookeeper的/rpc节点,获取访问rpc方法的url
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 1000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("zookeeper已连接");
            }
        });

        if (zooKeeper.exists("/rpc",false)!=null){
            String url = new String(zooKeeper.getData("/rpc", false, null));
            ServiceInterface serviceImpl2 = (ServiceInterface) Naming.lookup(url);
            serviceImpl2.constructUser(100,"zhangsan");
            serviceImpl2.modifyUser(new Person(100,"lisi"));
        }
    }
}

 