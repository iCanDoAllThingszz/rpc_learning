package com.example.day108rpc.rmi;/**
 * @Author:zhoayu
 * @Date:2024/4/6 14:11
 * @Description:com.example.day108rpc.rmi
 * @version:1.0
 */

import com.example.day108rpc.rmi.impl.RMIServerImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @ClassName ClientTest
 * @Description //TODO 
 * @Author zhaoyu
 * @Date 2024/4/6
 */
public class ClientTest {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        RMIServerImpl lookup = (RMIServerImpl)Naming.lookup("rmi://localhost:9999/testRmi");
        //客户端这里像调用自己本地的方法一样调用服务器的方法
        String result = lookup.test1("client");
        System.out.println(result);
        //clientrmi
    }
}

 