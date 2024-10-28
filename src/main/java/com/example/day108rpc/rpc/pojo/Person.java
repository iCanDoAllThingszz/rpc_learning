package com.example.day108rpc.rpc.pojo;/**
 * @Author:zhoayu
 * @Date:2024/4/6 18:24
 * @Description:com.example.day108rpc.rpc.pojo
 * @version:1.0
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName Person
 * @Description //TODO 
 * @Author zhaoyu
 * @Date 2024/4/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
    private Integer uid;
    private String name;
}

 