

# MyCat笔记-总

[黑马20200918课程](https://www.bilibili.com/video/BV17f4y1D7pm?p=15&spm_id_from=pageDriver)

[MyCat1官方语雀文档](https://www.yuque.com/ccazhw/tuacvk/vttsnv)



| 第一天            | 第二天        | 第三天              | 第四天        |
| ----------------- | ------------- | ------------------- | ------------- |
| MyCat简介         | MyCat分片规则 | MyCat高可用集群搭建 | MyCat综合案例 |
| MyCat入门         | MyCat高级     | MyCat架构剖析       |               |
| MyCat配置文件详解 |               |                     |               |



<hr>



## 1. MyCat简介



### 1.1 MyCat 引入

如今随着互联网的发展，数据的量级也是成指数式的增长，从GB到TB到PB。对数据的各种操作也是愈加的困难，传统的关系性数据库已经无法满足快速查询与插入数据的需求,这个时候NoSQL的出现暂时解决了这一危机。它通过降低数据的安全性，减少对事务的支持，减少对复杂查询的支持，来获取性能上的提升。但是，在有些场合NoSQL一些折衷是无法满足使用场景的，就比如有些使用场景是绝对要有事务与安全指标的。这个时候NoSQL肯定是无法满足的，所以还是需要使用关系性数据库。

如何使用关系型数据库解决海量存储的问题呢？此时就需要做数据库集群，为了提高查询性能将一个数据库的数据分散到不同的数据库中存储，为应对此问题就出现了——MyCat 。

MyCAT的目标是：低成本的将现有的单机数据库和应用平滑迁移到"云"端，解决海量数据存储和业务规模迅速增长情况下的数据存储和访问的瓶颈问题 。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220404183221768.png)



### 1.2 MyCat 历史

**前身Cobar**

Mycat 背后是阿里曾经开源的知名产品——Cobar。Cobar 的核心功能和优势是 MySQL 数据库分片，此产品曾经广为流传，据说最早的发起者对 Mysql 很精通，后来从阿里跳槽了，阿里随后开源的 Cobar，并维持到 2013 年年初，然后，就没有然后了。 Cobar 的思路和实现路径的确不错。基于 Java 开发的，实现了 MySQL 公开的二进制传输协议，巧妙地将自己伪装成一个 MySQL Server，目前市面上绝大多数 MySQL 客户端工具和应用都能兼容。比自己实现一个新的数据库协议要明智的多，因为生态环境在那里摆着。 

**相较cobar的2个优势**

Mycat 是基于 cobar 演变而来，相对于cobar来说 , 有两个显著优势 : 

①. 对 cobar 的代码进行了彻底的重构，Mycat在I/O方面进行了重大改进,将原来的BIO改成了NIO, 并发量有大幅提高 ; 

 ②. 增加了对Order By、Group By、limit等聚合功能的支持，同时兼容绝大多数数据库成为通用的数据库中间件 。

**总的来说**

简单的说，MyCAT就是：一个新颖的数据库中间件产品支持mysql集群，或者 mariadb cluster，提供高可用性数据分片集群。你可以像使用mysql一样使用 mycat 。对于开发人员来说根本感觉不到mycat的存在。



### 1.3 概述

#### 1.3.1 功能介绍

##### 对于DBA来说，可以这么理解Mycat：

Mycat就是MySQL Server，而Mycat后面连接的MySQL Server，就好象是MySQL的存储引擎,如InnoDB，MyISAM等。因此，Mycat本身并不存储数据，数据是在后端的MySQL上存储的，因此数据可靠性以及事务等都是MySQL保证的，简单的说，Mycat就是MySQL最佳伴侣，它在一定程度上让MySQL拥有了能跟Oracle PK的能力。



##### 对于软件工程师来说，可以这么理解Mycat：

Mycat就是一个近似等于MySQL的数据库服务器，你可以用连接MySQL的方式去连接Mycat（除了端口不同，默认的Mycat端口是**8066**而非MySQL的3306，因此需要在连接字符串上增加端口信息）。大多数情况下，可以用你熟悉的对象映射框架使用Mycat，但建议对于分片表，尽量使用基础的SQL语句，因为这样能达到最佳性能，特别是几千万甚至几百亿条记录的情况下。



##### 对于架构师来说，可以这么理解Mycat：

Mycat是一个强大的数据库中间件，不仅仅可以用作读写分离、以及分表分库、容灾备份，而且可以用于多租户应用开发、云平台基础设施、让你的架构具备很强的适应性和灵活性，借助于即将发布的Mycat智能优化模块，系统的数据访问瓶颈和热点一目了然，根据这些统计分析数据，你可以自动或手工调整后端存储，将不同的表映射到不同存储引擎上，而整个应用的代码一行也不用改变。



## 2. MyCat入门





## MyCat配置文件详解





## Linux服务器准备





```bash
阿里云
39.101.189.62
腾讯云1
101.42.229.218
腾讯云2
49.232.132.201
```









## 环境搭建

mysql

```bash
# 卸载原有的mysql
https://blog.csdn.net/qq_40550973/article/details/80721014
find / -name mysql
find / -name mysql|xargs rm -rf;
# 安装老师的版本

## 解决冲突
rpm -qa|grep -i mariadb
yum remove mariadb-libs-5.5.65-1.el7.x86_64

A RANDOM PASSWORD HAS BEEN SET FOR THE MySQL root USER !
You will find that password in '/root/.mysql_secret'.
# The random password set for the root user at Mon Apr  4 13:08:58 2022 (local time): GApYUdcT4Udo69FN

## 启动mysql
https://www.cnblogs.com/maminghao/archive/2010/03/08/1680715.html
```











jdk







mycat









