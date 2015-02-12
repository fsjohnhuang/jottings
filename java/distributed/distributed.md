﻿## 分布式方式
1. 基于消息
2. 基于远程调用（RPC）

## 广播
基于UDP/IP扩展出来的多播协议，一份数据在网络上进行传输。<br/>
数据接收方和发送方均要加入多播组（多播组地址在224.0.0.0~239.255.255.255之间）<br/>
````
InetAddress groupAddress = InetAddress.getByName("244.1.1.1");
MulticastSocket server = new MulticastSocket(port);
// 通过server.leaveGroup(多播地址)可推出多播组
server.joinGroup(groupAddress);

MulticastSocket client = new MulticastSocket(port);
client.joinGropu(groupAddress);

// 后面就是UDP/IP的通信代码
````

## Mina基于消息方式通信的开源框架
基于NIO，同时支持TCP/IP和UDP/IP协议。<br/>
1. 组成部分<br/>
`IoConnector`
`IoAcceptor`
`IoHandler`
`IoSession`
2. 采用Filter Chain方式进行消息的处理、发送和接收等工作。<br/>

## 远程调用
主要技术有RMI和WebService。<br/>

## 分布式
1. 内容<Br/>
跨进程通信、集群、负载均衡、分布式缓存、分布式文件系统等。<br/>
2. 分布式所带来的问题<Br/>
服务多级调用带来的延时、调试/跟踪困难、更高的安全/监测要求、现有应用移植、Qos的支持、高可用和高度可伸缩、多版本和依赖管理<br/>
3. SOA标准SCA(Service Component Architecture)<br/>
  由于SOA仅仅是一种思想，而非实现方法指导。因此IBM、Oracle等巨头就联合起来制定SCA实现规范。<br/>

