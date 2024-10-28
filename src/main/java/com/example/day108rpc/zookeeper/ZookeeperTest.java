package com.example.day108rpc.zookeeper;
/**
 * @Author:zhoayu
 * @Date:2024/4/6 17:44
 * @Description:com.example.day108rpc.zookeeper
 * @version:1.0
 */

import org.apache.zookeeper.*;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName ZookeeperTest
 * @Description //TODO 
 * @Author zhaoyu
 * @Date 2024/4/6
 */
public class ZookeeperTest {
    //测试向zookeeper中写入内容
   @Test
    public void zookeeperTest1() throws IOException, InterruptedException, KeeperException {
       //参数1:zookeeper的ip地址+端口号
       //参数2:访问超时时间
       //参数3:连接上zookeeper后的 Watcher回调函数
       ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 1000, new Watcher() {
           @Override
           public void process(WatchedEvent watchedEvent) {
               System.out.println("zookeeper已连接");
           }
       });

       //通过创建出来的zookeeper对象操纵zookeeper
       //参数1:创建的节点
       //参数2:节点内容
       //参数3:权限
       //参数4:创建的节点是临时or永久 带不带序号
       //参数5:回调函数
       String content = zooKeeper.create("/zookeeperTest","test111".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
       //[zk: localhost:2181(CONNECTED) 0] ls /
       //[demo2, zookeeper, zookeeperTest0000000002]
       //[zk: localhost:2181(CONNECTED) 1] get /zookeeperTest0000000002
       //test111
       System.out.println(content);
       ///zookeeperTest0000000002

       //第二次执行时 有序号的节点被创建了:
       //[zk: localhost:2181(CONNECTED) 5] ls /
       //[demo2, zookeeper, zookeeperTest0000000002, zookeeperTest0000000003]
       //[zk: localhost:2181(CONNECTED) 6] get /zookeeperTest0000000003
       //test111

       ///zookeeperTest0000000003
   }

   //从zookeeper中获取内容
   @Test
    public void zookeeperTest2() throws IOException, InterruptedException, KeeperException {
       //参数1:zookeeper的ip地址+端口号
       //参数2:访问超时时间
       //参数3:连接上zookeeper后的 Watcher回调函数
       ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 1000, new Watcher() {
           @Override
           public void process(WatchedEvent watchedEvent) {
               System.out.println("zookeeper已连接");
           }
       });

       //1.获取zookeeper节点
       List<String> nodes = zooKeeper.getChildren("/", new Watcher() {
           @Override
           public void process(WatchedEvent watchedEvent) {
               System.out.println("该路径下一旦有节点变更 此回调方法执行");
           }
       });

       System.out.println(nodes);
       //[zookeeper, zookeeperTest0000000004, zookeeperTest0000000002, demo2, zookeeperTest0000000003]

       //2.获取zookeeper节点存储的内容
       for (String node : nodes){
           //不要回调了
           String result = new String(zooKeeper.getData("/" + node, false, null));
           System.out.println(result);
           //test111
           //watcher
           //demo2new
       }

       //3.测试上面的zookeeper回调方法
       Thread thread = new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   //当create_mode为PERSISTENT并且该路径下已有要创建的节点时,重复创建会报错KeeperException$NodeExistsException
                   zooKeeper.create("/TestWatcher","watcher".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
               } catch (KeeperException e) {
                   throw new RuntimeException(e);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
       });

       thread.start();

       thread.join();
       //[zk: localhost:2181(CONNECTED) 9] ls /
       //[TestWatcher, demo2, zookeeper, zookeeperTest0000000002, zookeeperTest0000000003, zookeeperTest0000000004]
       //该路径下一旦有节点变更 此回调方法执行
   }
}

 