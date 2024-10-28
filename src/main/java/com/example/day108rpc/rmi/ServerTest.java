package com.example.day108rpc.rmi;/**
 * @Author:zhoayu
 * @Date:2024/4/6 14:07
 * @Description:com.example.day108rpc.rmi
 * @version:1.0
 */

import com.example.day108rpc.rmi.impl.RMIServerImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @ClassName ServerTest
 * @Description //TODO 
 * @Author zhaoyu
 * @Date 2024/4/6
 */
public class ServerTest {
    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
        //1.创建接口实例
        RMIServer rmiServer = new RMIServerImpl();

        //2.创建注册表
        LocateRegistry.createRegistry(9999);

        //3.绑定服务,将创建出来的实现类发布到服务中
        //这里用的是rmi协议
        Naming.bind("rmi://localhost:9999/testRmi",rmiServer);

        System.out.println("服务器启动成功");
    }
}
 