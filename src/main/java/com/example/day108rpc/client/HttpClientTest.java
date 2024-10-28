package com.example.day108rpc.client;/**
 * @Author:zhoayu
 * @Date:2024/4/5 19:04
 * @Description:com.example.day108rpc.client
 * @version:1.0
 */

import com.example.day108rpc.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GetTest
 * @Description //TODO 
 * @Author zhaoyu
 * @Date 2024/4/5
 */
public class HttpClientTest {
    
    @Test
    public void testGetRequest() throws URISyntaxException, IOException {
        //1.创建http工具,用于发送请求,解析响应
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2.请求路径
        URIBuilder uriBuilder = new URIBuilder("http://localhost:8080/test1");
        uriBuilder.addParameter("param","111");

        //3.创建http get请求对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        //4.创建一个响应对象
        CloseableHttpResponse response = httpClient.execute(httpGet);

        //5.获取响应
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity,"UTF-8");
        System.out.println(responseString);
        //打印:111测试HttpServer

        //6.释放资源
        response.close();
        httpClient.close();
    }

    @Test
    public void TestPostRequest() throws URISyntaxException, IOException {
        //1.创建http客户端实例
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2.声明uri路径即参数
        URIBuilder uriBuilder = new URIBuilder("http://localhost:8080/test1");
        uriBuilder.addParameter("param","222");

        //3.构建post请求对象
        HttpPost httpPost = new HttpPost(uriBuilder.build());
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("param","333"));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        //4.获取响应
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(responseString);
        //打印:222,333测试HttpServer

        //5.释放资源
        response.close();
        httpClient.close();
    }

    @Test
    public void testPostObject() throws URISyntaxException, IOException {
        //1.创建http客户端实例
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2.声明uri路径即参数
        URIBuilder uriBuilder = new URIBuilder("http://localhost:8080/test2");
        uriBuilder.addParameter("uid","100");

        //3.构建post请求对象
        HttpPost httpPost = new HttpPost(uriBuilder.build());
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("uname","zhangsan"));
        nameValuePairs.add(new BasicNameValuePair("gender","male"));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        //4.获取响应
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");

        //5.将服务器返回的json字符串转换为对象
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(responseString, User.class);
        System.out.println(user);
        //打印:User(uid=100, uname=zhangsan, gender=male)
        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);
        //打印:{"uid":100,"uname":"zhangsan","gender":"male"}

        //6.释放资源
        response.close();
        httpClient.close();
    }

    @Test
    public void testCollections() throws URISyntaxException, IOException {
        //1.创建http客户端实例
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2.声明uri路径即参数
        URIBuilder uriBuilder = new URIBuilder("http://localhost:8080/test3");
        uriBuilder.addParameter("uid","100");

        //3.构建post请求对象
        HttpPost httpPost = new HttpPost(uriBuilder.build());
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("uname","zhangsan"));
        nameValuePairs.add(new BasicNameValuePair("gender","male"));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        //4.获取响应
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");

        //5.将服务器返回的json字符串转换为集合
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class,User.class);

        List<User> users = objectMapper.readValue(responseString, javaType);
        System.out.println(users);
        //[User(uid=100, uname=zhangsan, gender=male), User(uid=100, uname=zhangsan, gender=male)]

        //6.释放资源
        response.close();
        httpClient.close();
    }


}

 