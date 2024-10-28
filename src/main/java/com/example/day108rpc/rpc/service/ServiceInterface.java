package com.example.day108rpc.rpc.service;

import com.example.day108rpc.rpc.pojo.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @Author:zhoayu
 * @Date:2024/4/6 18:25
 * @Description:com.example.day108rpc.rpc.service
 * @version:1.0
 */
public interface ServiceInterface extends Remote {
    //在接口中定义RPC方法

    //1.构造Person对象
    public Person constructUser(Integer uid,String name) throws RemoteException;

    //2.修改Person对象
    public Person modifyUser(Person person) throws RemoteException;

}
