package com.example.day108rpc.rmi;/**
 * @Author:zhoayu
 * @Date:2024/4/6 14:02
 * @Description:com.example.day108rpc.rmi
 * @version:1.0
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @ClassName RMIServer
 * @Description //TODO 
 * @Author zhaoyu
 * @Date 2024/4/6
 */
public interface RMIServer extends Remote {

    public String test1(String  param) throws RemoteException;

}
 