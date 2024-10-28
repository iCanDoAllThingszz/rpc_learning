package com.example.day108rpc.controller;/**
 * @Author:zhoayu
 * @Date:2024/4/5 17:53
 * @Description:com.example.day108rpc.controller
 * @version:1.0
 */

import com.example.day108rpc.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HttpServerClientTest
 * @Description //TODO 
 * @Author zhaoyu
 * @Date 2024/4/5
 */
@Controller
public class HttpServerClientTest {
    @RequestMapping("test1")
    @ResponseBody
    public String test1(String param){
        return param + "测试HttpServer";
    }

    @RequestMapping("test2")
    @ResponseBody
    public User test2(User user){
        return user;
    }

    @RequestMapping("test3")
    @ResponseBody
    public List<User> test3(User user1,User user2){
        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        return users;
    }

    @RequestMapping("test4")
    @ResponseBody
    @CrossOrigin
    public List<User> test4(@RequestBody List<User> list){
        System.out.println(list);
        return list;
    }

}
