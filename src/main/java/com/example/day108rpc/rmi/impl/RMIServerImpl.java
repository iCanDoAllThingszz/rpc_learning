package com.example.day108rpc.rmi.impl;/**
 * @Author:zhoayu
 * @Date:2024/4/6 14:04
 * @Description:com.example.day108rpc.rmi.impl
 * @version:1.0
 */

import com.example.day108rpc.rmi.RMIServer;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

/**
 * @ClassName RMIServerImpl
 * @Description //TODO 
 * @Author zhaoyu
 * @Date 2024/4/6
 */
public class RMIServerImpl extends UnicastRemoteObject implements RMIServer {

    public RMIServerImpl() throws RemoteException {
    }

    public RMIServerImpl(int port) throws RemoteException {
        super(port);
    }

    public RMIServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public String test1(String param) throws RemoteException {
        return param + "rmi";
    }
}

 