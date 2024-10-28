package com.example.day108rpc.Day01;/**
 * @Author:zhoayu
 * @Date:2024/4/5 15:17
 * @Description:com.example.day108rpc.Day01
 * @version:1.0
 */

/**
 * @ClassName Test
 * @Description //TODO 
 * @Author zhaoyu
 * @Date 2024/4/5
 */
public class Test {
    //课时1.学习目标
    //主要内容:
    //1.项目结构变化(掌握)
    //2.RPC简介(掌握)
    //3.RMI实现RPC(掌握)
    //4.HttpClient实现RPC(了解)
    //5.Zookeeper安装(掌握)
    //6.Zookeeper客户端常用命令(掌握)
    //7.向Zookeeper中注册内容(掌握)
    //8.从Zookeeper中发现内容(掌握)
    //9.手写RPC框架(掌握)
    //现在学习RPC,后期学Dubbo、Thrift等RPC框架就会变得容易一些

    //课时2.项目架构变化
    //1.单体架构
    //单体架构就是一个项目里面就包含了这个项目中的所有代码,一个项目搞定所有功能
    //客户端请求域名->DNS服务器返回ip地址->请求对应ip的服务->服务响应

    //单体项目:
    //在单体架构项目中,团队是通过包(package)来区分每个模块
    //eg:com.zy.utils,com.zy.common,com.zy.分层包...
    //优点:部署简单、维护方便、成本低
    //缺点:当项目规模大、用户访问频率高、并发量大、数据量大时,会大大降低程序执行效率 甚至出现服务器宕机的情况
    //适用项目:传统M端管理项目,小型互联网项目

    //分布式架构:
    //分布式架构会把一个项目按照对应不同功能的模块切分成多个项目,每个项目分别部署到不同的服务器上,用户请求某个模块的功能,请求就打到那个服务器上
    //rpc就主要用于整体项目内不同的服务之间通讯
    //比如:京东的项目(jd.com)下细分了若干个模块 有下单(buy.jd.com)、有购物车(cart.jd.com)、有商品清单(item.jd.com)
    //我们请求哪个模块的功能,比如购物、注册等,请求就打到对应服务的服务器上。我们可以针对服务的请求量的大小为其多设置一些服务器
    //eg:给下单服务5台服务器,给注册服务1台服务器
    //整体项目->微服务项目(按模块切分)
    //单个微服务项目水平拓展

    //可以看到,微服务项目实现了解耦,整个服务更加灵活了

    //分布式架构的代码结构:
    //项目1:
    //  --com.zy.xxx
    //      --controller
    //      --service
    //      --mapper
    //      --...
    //项目2:
    //  --com.zy.xxx
    //      --controller
    //      --service
    //      --mapper
    //      --...
    //每一个服务都是一个完整的小项目,组合在一起成为一个整体的大项目

    //优点:
    //1.增大了系统的可用性,减少了因单点故障导致整个应用不可用的概率(比如某个服务的服务器全部挂掉了,不影响其他和它不想干的模块的正常使用。如果情况稍好点,一个服务的某台服务器挂掉了,那基本啥都不影响)
    //2.增加可重用性,因为服务之间是模块化部署的,所以相同功能的服务直接重用就行了
    //3.增加可扩展性,需要增加新的模块时,只需要开发新的服务 整合进原项目即可
    //4.增加每个模块的负载能力,因为每个模块都是一个项目,所以每个模块的负载能力更强,如果哪个模块需要更多的服务器,针对这个模块水平扩展即可

    //缺点:
    //1.成本更高,不同的服务一般部署在不同的服务器上,需要将服务拆分由多个团队维护
    //2.架构更加复杂
    //3.整体响应时间变长,一些业务的一次完整请求要经过多个服务通信后才能给出结果
    //4.吞吐量更大 吞吐量=请求数/秒。比如单体项目的吞吐量为10次/s的话,换成微服务架构,由于模块之间通信也算请求 所以整体的吞吐量就更大了

    //那么问题就来了,既然整个项目被拆分成了多个服务,那么各个模块之间用什么通信呢?
    //可以使用http协议,也可以使用rpc协议,那么要用哪个呢?
    //我们选择的是RPC协议,因为相比http协议,它更适合项目内部通信一点

    //课时3.RPC简介
    //RPC(Remote Procedure Call)远程过程调用协议
    //ps:我们经常把类的属性称为字段,把类的方法称为过程,这就能理解其实远程过程调用=远程方法调用

    //RPC协议规定允许互联网中的一台主机程序调用另一台主机程序时,这个过程对程序员是透明的,比如A程序调用了B程序中的功能或方法时,A只知道B的入参和出参,具体的B的实现A是不用关心的
    //让程序员调用其他服务的方法就像调用自己本地的方法一样简单
    //比如所有的购物软件去调微信/支付宝的接口

    //RPC是上层协议,具体的实现可以基于TCP协议(传输层协议),也可以基于HTTP协议(应用层协议)。一般我们说的RPC都是基于RPC的具体实现框架,比如:Dubbo、Thrift
    //从广义上来说其实只要是满足网络中通讯调用的都可以称为是RPC,甚至HTTP协议都可以说是RPC的具体实现

    //课时4.RPC和HTTP对比
    //1.具体实现
    //RPC:底层实现可以是TPC协议 也可以是HTTP协议(事实上HTTP协议就是由TPC协议实现的),甚至可以自己写一个协议,只要项目内的服务都能遵从这个协议 完成数据的传输,能够根据这个协议定义清除消息的边界 解决粘包的问题即可
    //HTTP:基于HTTP协议,底层实现是TCP协议

    //2.效率
    //RPC:减少了很多无用的报文内容,使得报文体积更小
    //HTTP:如果是HTTP1.1,报文中很多内容都是无用的。如果是HTTP2.0 那和RPC相比相差不大,论性能甚至好过一些RPC框架,但是http2.0是2015年才出的,此时很多企业的项目内已经在用rpc协议 不好改了
    //此外http协议比rpc还少一些服务治理等功能(比如注册中心,负载均衡等)

    //3.应用场景
    //RPC:面向的是一个完整项目内的各微服务之间的通信。项目内的各模块只要遵从rpc协议,不管它底层是由http或者是tpc协议实现的,甚至是自定义实现的都可以,只要项目内的各模块在解析消息时都遵从这个协议就好了
    //http:面向的是互联网中与各项目的通信。比如我访问baidu,访问jingdong,访问taobo,他们都必须遵从http协议,我才能用一套请求http://xxx.xxx.com访问到他们,否则访问百度用一套协议,访问京东用一套协议 整个就乱了

    //4.连接方式
    //RPC:支持长连接(反复连接时复用同一个连接),内部实现一般会用连接池管理连接
    //HTTP:支持长连接(默认),也支持短连接,内部实现也一般会用连接池管理连接

    //5.性能
    //RPC:可以支持多种序列化/反序列化方式,比如protobuf、json、thrift,其中protobuf序列化后的数据体积比json更小,而且序列化/反序列化的速度比json更快
    //一条消息数据,protobuf序列化后的大小是json的10分之一,xml格式的20分之一,是二进制序列化的10分之一
    //HTTP:HTTP协议传输数据主要基于json,序列化和反序列化的效率更低

    //java常用的序列化框架的选型与对比:https://blog.csdn.net/qq_31515997/article/details/136446705

    //6.注册中心
    //RPC:一般RPC框架都带有注册中心 eg:A服务->注册中心->B服务 B服务的ip地址变了不要紧,注册中心统一管理各服务的位置,A服务还是去找注册中心 注册中心再去请求新位置的b服务即可
    //HTTP:都是直连 eg:A服务->B服务 b服务的ip地址变了的话 a服务就不能再通过原ip地址访问到b服务了

    //7.负载均衡
    //RPC:绝大多数RPC框架都自带负载均衡测量,比如A服务调用B服务,B服务有5台服务器,请求打到哪台服务器上呢 RPC框架会有自带的负载均衡策略决定
    //HTTP:需要借助第三方工具 如:nginx,nginx根据设置的负载均衡策略决定把http请求打到哪台服务器上

    //总结:http请求是客户端请求服务的入口,rpc服务是微服务之间内部通讯的手段

    //8.综合结论:
    //RPC框架一般都带有丰富的服务治理功能,并且底层的序列化/反序列化方法也更轻量(数据体积更小,序列化/反序列化速度更快),更适合企业内部服务之间的接口调用
    //HTTP协议由于其通用性,更适合客户端和多平台之间的相互调用

    //课时5.使用HTTPClient实现RPC
    //HttpClient简介:
    //在JDK的java.net包下提供了HttpClient 用于实现http访问的基本功能
    //简而言之就是HttpClient可以实现使用Java代码完成标准的HTTP请求和响应

    //1.新建controller
    //->com.example.day108rpc.controller.HttpServerClientTest

    //2.通过HttpClient编写客户端
    //添加httpclient依赖 ->pom.xml
    //Http Get请求 ->com.example.day108rpc.client.HttpClientTest#testGetRequest
    //Http Post请求 ->com.example.day108rpc.client.HttpClientTest#testPostRequest

    //服务器的返回值是对象:使用json返回对象
    //->com.example.day108rpc.controller.HttpServerClientTest
    //->com.example.day108rpc.client.HttpClientTest#testPostObject

    //服务器的返回值是集合:
    //->com.example.day108rpc.controller.HttpServerClientTest
    //->com.example.day108rpc.client.HttpClientTest#testCollections

    //课时6.使用ajax发送json参数
    //->com.example.day108rpc.controller.HttpServerClientTest#test4
    //->static/html/ajaxTest.html

    //课时7.ajax跨域请求
    //ajax在一个服务器上 自己调自己没有实际意义(同源策略:ajax会默认请求本机器本端口的本服务下的资源),rpc是服务器之间的通信 所以我们要实现ajax的跨域 即不同服务器之间的通信
    //跨域:要请求的资源所在的协议、ip、端口和请求方所在的协议、ip、端口中只要有一个不同就是跨域请求
    //同源策略:浏览器默认只允许ajax访问同源(协议、ip、端口都相同)的内容
    //解决同源策略的方法:在服务器的被访问的Controller上添加@CrossOrigin注解即可,表示该Controller允许外部的跨域请求访问。
    //本质上是在响应头中设置了 Access-Control-Allow-Origin:* 表示任何请求都被允许访问此controller

    //跨域是什么以及如何解决跨域问题：https://zhuanlan.zhihu.com/p/688008398
    //浏览器跨域问题:https://blog.csdn.net/weixin_39510828/article/details/118994285

    //->static/html/ajaxTest2.html

    //课时8.RMI简介
    //RMI(Remote Method Invocation)远程方法调用
    //RMI是JDK1.2推出的功能,它可以实现在一个java应用中像调用本地方法一样调用另一个服务器中的java应用中的方法
    //RMI是java语言的远程调用,无法实现跨语言

    //RMI执行流程
    //Server端通过rebind()方法将本地方法和监听的ip&端口绑定(bind)起来,注册到RMI Registry中
    //Client通过lookup()方法到RMI Registry中进行需要调用方法的查找,找到对应方法的ip地址&port后,直接请求服务器的port
    //服务器的port一旦被访问,就会执行和该port绑定的方法

    //RMI Registry(注册表)是放置所有服务器对象的命名空间。每次服务端创建一个对象时,他都会使用bind()或rebind()方法注册该对象
    //客户端想调用远程方法,需要先用lookup()方法到Registry注册表中找到对应方法的ip地址和端口号 然后直接访问

    //API介绍:
    //1.Remote
    //java.rmi.Remote定义了此接口为远程调用接口,服务端的接口需要继承此接口 接口下的方法才能供别人远程调用

    //2.RemoteException
    //java.rmi.RemoteException
    //在服务器端继承了Remote接口的接口下,对于希望被远程调用的方法,必须抛出此异常

    //3.UnicastRemoteObject
    //java.rmi.server.UnicastRemoteObject
    //此类实现了Remote接口和Serializable接口
    //自定义接口实现类除了要实现自定义接口,还需要继承此类

    //4.LocateRegistry
    //java.rmi.registry.LocateRegistry
    //可以通过LocateRegistry在本机上创建Registry,通过特定的端口就可以访问到这个Registry,主要用于向Registry中写内容

    //5.Naming
    //java.rmi.Naming
    //调用方通过Naming获取到指定的远程方法

    //课时9.RMI代码实现
    //这里为了方便 我们把客户端代码和服务器代码都写在了一个项目里,实际生产环境中它们肯定是在两个服务乃至两台机器上的

    //需要被远程调用的方法
    //->com.example.day108rpc.rmi.RMIServer
    //->com.example.day108rpc.rmi.impl.RMIServerImpl

    //服务端
    //->com.example.day108rpc.rmi.ServerTest

    //客户端
    //->com.example.day108rpc.rmi.ClientTest

    //课时10.Zookeeper简介及安装
    //Zookeeper是一款分布式管理软件,经常被用作注册中心(依赖于zookeeper的发布/订阅功能)、配置文件中心、分布式锁配置、集群管理等

    //zookeeper被我们放在了~/Desktop/soft下
    //tar -zxvf apache-zookeeper-3.8.4-bin.tar
    //新建data目录 修改zookeeper配置文件zoo.cfg的配置项 让其data目录指向我们新建的data目录

    //ps:zookeeper监听的端口号是2181

    //启动zookeeper命令:
    //./zkServer.sh start

    //查看zookeeper状态:
    //(base) zhaoyu@MBP-W12T7NRJFQ-1830 bin % ./zkServer.sh status
    //ZooKeeper JMX enabled by default
    //Using config: /Users/zhaoyu/Desktop/soft/apache-zookeeper-3.8.4-bin/bin/../conf/zoo.cfg
    //Client port found: 2181. Client address: localhost. Client SSL: false.
    //Mode: standalone

    //standalone:zookeeper单机版

    //课时11.zookeeper客户端常用命令
    //1.进入zookeeper客户端
    //./zkCli.sh

    //2.ls(针对节点)
    //查看指定路径下的节点
    //-s 展示详细信息
    //-R 展示当前目录及子目录下的所有内容
    //eg:
    //[zk: localhost:2181(CONNECTED) 5] ls /
    //[zookeeper]
    //根目录下有一个节点[zookeeper]

    //3.create
    //create path [data]
    //在指定path下创建节点 并添加内容(可选)
    //eg:
    //[zk: localhost:2181(CONNECTED) 7] create /demo
    //Created /demo
    //[zk: localhost:2181(CONNECTED) 8] ls /
    //[demo, zookeeper]

    //[zk: localhost:2181(CONNECTED) 9] create /demo2 demo2Test
    //Created /demo2
    //[zk: localhost:2181(CONNECTED) 12] ls -s /demo2
    //[] demo2下没有节点 所以这里为[]
    //cZxid = 0x3
    //ctime = Sat Apr 06 14:40:54 CST 2024
    //mZxid = 0x3
    //mtime = Sat Apr 06 14:40:54 CST 2024
    //pZxid = 0x3
    //cversion = 0
    //dataVersion = 0
    //aclVersion = 0
    //ephemeralOwner = 0x0
    //dataLength = 9
    //numChildren = 0
    //[zk: localhost:2181(CONNECTED) 13] get /demo2
    //demo2Test

    //4.get(针对节点存储的内容)
    //get [-s] path
    //获取指定节点存储的内容
    //[zk: localhost:2181(CONNECTED) 15] get /demo
    //null
    //[zk: localhost:2181(CONNECTED) 16] get /demo2
    //demo2Test
    //[zk: localhost:2181(CONNECTED) 17] get -s /demo2
    //demo2Test /demo2存储的内容为demo2Test
    //cZxid = 0x3
    //ctime = Sat Apr 06 14:40:54 CST 2024
    //mZxid = 0x3
    //mtime = Sat Apr 06 14:40:54 CST 2024
    //pZxid = 0x3
    //cversion = 0
    //dataVersion = 0
    //aclVersion = 0
    //ephemeralOwner = 0x0 该节点是否是临时节点,临时节点的生命周期同创建它的客户端的会话生命周期一致,如果该节点不是ephemeral节点 则ephemeralOwner=0
    //dataLength = 9
    //numChildren = 0 子节点数量

    //5.set(针对节点内容)
    //set path content
    //向指定节点放数据
    //[zk: localhost:2181(CONNECTED) 18] set /demo2 demo2new
    //[zk: localhost:2181(CONNECTED) 19] get -s /demo2
    //demo2new
    //cZxid = 0x3
    //ctime = Sat Apr 06 14:40:54 CST 2024
    //mZxid = 0x4
    //mtime = Sat Apr 06 14:47:40 CST 2024
    //pZxid = 0x3
    //cversion = 0
    //dataVersion = 1
    //aclVersion = 0
    //ephemeralOwner = 0x0
    //dataLength = 8
    //numChildren = 0

    //6.delete(针对节点)
    //delete path
    //删除指定节点
    //[zk: localhost:2181(CONNECTED) 20] delete /demo
    //[zk: localhost:2181(CONNECTED) 21] ls /
    //[demo2, zookeeper]

    //课时12.java代码操作Zookeeper
    //1.添加依赖
    //->pom.xml

    //2.操纵zookeeper
    //->com.example.day108rpc.zookeeper.ZookeeperTest

    //课时13.手写RPC框架
    //为什么要学习操作zookeeper呢,因为zookeeper存储数据、回调函数的特性,很适合做RPC框架中的注册中心

    //接下来我们就使用Zookeeper作为注册中心,RMI作为连接技术,手写一个简易版的RPC框架
    //ps:正常调用方和服务方的代码应该放在两个项目里,这里为了方便我们还是先放在一个项目里

    //整体流程:
    //调用方从注册中心获取信息后访问服务方,获得服务方绑定对应url的对象后,调用该对象的方法

    //整体项目分为以下几个模块:
    //1.pojo:service中需要的实体类
    //2.service:包含serviceImpl具体的rpc方法和服务器代码
    //3.serviceImpl:主要为rpc方法的具体逻辑
    //4.consumer:远程调用服务端方法的使用方

    //1.pojo类 rpc方法涉及到的实体类
    //->com.example.day108rpc.rpc.pojo.Person

    //2.service 定义rpc方法的接口及其实现类
    //->com.example.day108rpc.rpc.service.ServiceInterface
    //->com.example.day108rpc.rpc.service.impl.ServiceImpl2
    //服务提供者provider注册rpc方法,提供服务
    //->com.example.day108rpc.rpc.service.ServiceProvider

    //3.consumer 服务调用方
    //->com.example.day108rpc.rpc.client.ClientTest2
}
