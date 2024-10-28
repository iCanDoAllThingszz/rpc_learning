package com.example.day108rpc.rpc.service;/**
 * @Author:zhoayu
 * @Date:2024/4/6 18:30
 * @Description:com.example.day108rpc.rpc.service
 * @version:1.0
 */

import com.example.day108rpc.rpc.service.impl.ServiceImpl2;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @ClassName ServiceProvider
 * @Description //TODO 
 * @Author zhaoyu
 * @Date 2024/4/6
 */
public class ServiceProvider {
    public static void main(String[] args) throws IOException, AlreadyBoundException, InterruptedException, KeeperException {
        ServiceInterface service = new ServiceImpl2();

        LocateRegistry.createRegistry(9999);

        String url = "rmi://localhost:9999/test";

        Naming.bind(url,service);

        //将url发布到zookeeper中,这样的好处是当服务器监听的rpc端口号或者url变了的时候,客户端不用写死跟着也改
        //也就是说,服务端的url变化对rpc是无感知的 因为客户端获取url是通过zookeeper
        //服务端->发布url到zookeeper 客户端<-订阅节点内容 通过zookeeper获得url
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 1000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("zookeeper已连接");
            }
        });

        //判断下zookeeper中是否已经有/rpc节点
        if(zooKeeper.exists("/rpc",false)!=null){
            //version=-1:忽略版本限制
            zooKeeper.setData("/rpc",url.getBytes(),-1);
        }else{
            zooKeeper.create("/rpc",url.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        //[zk: localhost:2181(CONNECTED) 12] get -s /rpc
        //rmi://localhost:9999/test
        //cZxid = 0x2c
        //ctime = Sat Apr 06 18:42:58 CST 2024
        //mZxid = 0x2c
        //mtime = Sat Apr 06 18:42:58 CST 2024
        //pZxid = 0x2c
        //cversion = 0
        //dataVersion = 0
        //aclVersion = 0
        //ephemeralOwner = 0x0
        //dataLength = 25
        //numChildren = 0
    }
}

 