package com.example.day108rpc.rpc.service.impl;/**
 * @Author:zhoayu
 * @Date:2024/4/6 18:26
 * @Description:com.example.day108rpc.rpc.service.impl
 * @version:1.0
 */

import com.example.day108rpc.rpc.pojo.Person;
import com.example.day108rpc.rpc.service.ServiceInterface;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

/**
 * @ClassName ServiceImpl2
 * @Description //TODO 
 * @Author zhaoyu
 * @Date 2024/4/6
 */
public class ServiceImpl2 extends UnicastRemoteObject implements ServiceInterface {
    public ServiceImpl2() throws RemoteException {
    }

    public ServiceImpl2(int port) throws RemoteException {
        super(port);
    }

    public ServiceImpl2(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    //rpc方法的具体实现
    @Override
    public Person constructUser(Integer uid, String name) {
        return new Person(uid,name);
    }

    @Override
    public Person modifyUser(Person person) {
        person.setUid(person.getUid()+1);
        return person;
    }
}

 