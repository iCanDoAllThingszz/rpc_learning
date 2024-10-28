package com.example.day108rpc.pojo;/**
 * @Author:zhoayu
 * @Date:2024/4/5 19:43
 * @Description:com.example.day108rpc.pojo
 * @version:1.0
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName User
 * @Description //TODO 
 * @Author zhaoyu
 * @Date 2024/4/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer uid;
    private String uname;
    private String gender;
}
 