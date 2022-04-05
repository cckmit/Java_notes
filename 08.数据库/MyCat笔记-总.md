

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

##### 另外

当前是个大数据的时代，但究竟怎样规模的数据适合数据库系统呢？对此，国外有一个数据库领域的权威人士说了一个结论：千亿以下的数据规模仍然是数据库领域的专长，而Hadoop等这种系统，更适合的是千亿以上的规模。所以，Mycat适合1000亿条以下的单表规模，如果你的数据超过了这个规模，请投靠Mycat Plus吧！



#### 1.3.2 原理

Mycat的原理并不复杂，复杂的是代码，如果代码也不复杂，那么早就成为一个传说了。

Mycat的原理中最重要的一个动词是“拦截”，它拦截了用户发送过来的SQL语句，首先对SQL语句做了一些特定的分析：如分片分析、路由分析、读写分离分析、缓存分析等，然后将此SQL发往后端的真实数据库，并将返回的结果做适当的处理，最终再返回给用户。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220404190250678.png)



上述图片里，Orders表被分为三个分片datanode（简称dn)，这三个分片是分布在两台MySQL Server上(DataHost)。即datanode=database@datahost方式，因此你可以用一台到N台服务器来分片，分片规则为（sharding rule)典型的字符串枚举分片规则，一个规则的定义是分片字段（sharding column)+分片函数(rule function)，这里的分片字段为prov而分片函数为字符串枚举方式。

当Mycat收到一个SQL时，会先解析这个SQL，查找涉及到的表，然后看此表的定义，如果有分片规则，则获取到SQL里分片字段的值，并匹配分片函数，得到该SQL对应的分片列表，然后将SQL发往这些分片去执行，最后收集和处理所有分片返回的结果数据，并输出到客户端。以select * from Orders where prov=?语句为例，查到prov=wuhan，按照分片函数，wuhan返回dn1，于是SQL就发给了MySQL1，去取DB1上的查询结果，并返回给用户。

如果上述SQL改为select * from Orders where prov in (‘wuhan’,‘beijing’)，那么，SQL就会发给MySQL1与MySQL2去执行，然后结果集合并后输出给用户。但通常业务中我们的SQL会有Order By 以及Limit翻页语法，此时就涉及到结果集在Mycat端的二次处理，这部分的代码也比较复杂，而最复杂的则属两个表的Join问题，为此，Mycat提出了创新性的ER分片、全局表、HBT（Human Brain Tech)人工智能的Catlet、以及结合Storm/Spark引擎等十八般武艺的解决办法，从而成为目前业界最强大的方案，这就是开源的力量！





#### 1.3.3 应用场景

Mycat发展到现在，适用的场景已经很丰富，而且不断有新用户给出新的创新性的方案，以下是几个典型的应用场景：

·   单纯的读写分离，此时配置最为简单，支持读写分离，主从切换；

·   分表分库，对于超过1000万的表进行分片，最大支持1000亿的单表分片；

·   多租户应用，每个应用一个库，但应用程序只连接Mycat，从而不改造程序本身，实现多租户化；

·   报表系统，借助于Mycat的分表能力，处理大规模报表的统计；

·   替代Hbase，分析大数据；

·   作为海量数据实时查询的一种简单有效方案，比如100亿条频繁查询的记录需要在3秒内查询出来结果，除了基于主键的查询，还可能存在范围查询或其他属性查询，此时Mycat可能是最简单有效的选择。



#### 1.3.4 优势

**1). 性能可靠稳定**

基于阿里开源的Cobar产品而研发，Cobar的稳定性、可靠性、优秀的架构和性能以及众多成熟的使用案例使得MYCAT一开始就拥有一个很好的起点，站在巨人的肩膀上，我们能看到更远。业界优秀的开源项目和创新思路被广泛融入到MYCAT的基因中，使得MYCAT在很多方面都领先于目前其他一些同类的开源项目，甚至超越某些商业产品。

**2). 强大的技术团队**

MyCat 现在由一支强大的技术团队维护 , 吸引和聚集了一大批业内大数据和云计算方面的资深工程师、架构师、DBA，优秀的团队保障了MyCat的稳定高效运行。而且MyCat不依托于任何商业公司，而且得到大批开源爱好者的支持。

**3). 体系完善**

MyCat已经形成了一系列的周边产品,比较有名的是 Mycat-web、Mycat-NIO、Mycat-Balance等,已经形成了一个比较完整的解决方案,而不仅仅是一个中间件。

**4). 社区活跃**

与MyCat数据库中间件类似的产品还有 TDDL、Amoeba、Cobar 。

①. TDDL（Taobao Distributed Data Layer）不同于其它几款产品，并非独立的中间件，只能算作中间层，是以Jar包方式提供给应用调用 ，属于JDBC Shard的思想 。

②. Amoeba是作为一个真正的独立中间件提供服务,应用去连接Amoeba操作MySQL集群，就像操作单个MySQL一样。Amoeba算中间件中的早期产品,后端还在使用JDBC Driver。

③. Cobar是在Amoeba基础上进化的版本，一个显著变化是把后端JDBC Driver改为原生的MySQL通信协议层。

④. MyCat又是在Cobar基础上发展的版本, 性能优良, 功能强大, 社区活跃 。









## 2. MyCat入门

### 2.1 环境搭建

#### JDK1.8

```bash
A. 上传JDK的安装包到Linux的root目录下
	
B. 解压压缩包 , 到 /usr/share 目录下
	tar -zxvf jdk-8u181-linux-x64.tar.gz -C /usr/share/

C. 配置PATH环境变量 , 在该配置文件(/etc/profile)的最后加入如下配置
	export JAVA_HOME=/usr/share/jdk1.8.0_181
	export PATH=$PATH:$JAVA_HOME/bin
```

#### MySQL

##### 安装MySQL

```bash
A). 卸载 centos 中预安装的 mysql
	
	rpm -qa | grep -i mysql
	
	rpm -e mysql-libs-5.1.71-1.el6.x86_64 --nodeps
	
B). 上传 mysql 的安装包
	
	alt + p -------> put  E:/test/MySQL-5.6.22-1.el6.i686.rpm-bundle.tar

C). 解压 mysql 的安装包 
	
	mkdir mysql
	
	tar -xvf MySQL-5.6.22-1.el6.i686.rpm-bundle.tar -C /root/mysql
	
D). 安装依赖包 
	
	yum -y install libaio.so.1 libgcc_s.so.1 libstdc++.so.6 libncurses.so.5 --setopt=protected_multilib=false
	
	yum  update libstdc++-4.4.7-4.el6.x86_64
	
E). 安装 mysql-client
	
	rpm -ivh MySQL-client-5.6.22-1.el6.i686.rpm
	
F). 安装 mysql-server
	
	rpm -ivh MySQL-server-5.6.22-1.el6.i686.rpm
```





##### 启动停止MySQL

```bash
service mysql start

service mysql stop

service mysql status

service mysql restart
```





##### 登陆MySQL

```bash
mysql 安装完成之后, 会自动生成一个随机的密码, 并且保存在一个密码文件中 : /root/.mysql_secret

mysql -u root -p 

登录之后, 修改密码 :

set password = password('123456');

授权远程访问 : 

grant all privileges on *.* to 'root' @'%' identified by '123456';
flush privileges;
```

授权远程访问之后 , 就可以通过sqlYog来连接Linux上的MySQL , 但是记得关闭Linux上的防火墙(或者配置防火墙)。

云服务器开放端口就行。



#### MyCat

```bash
1). 上传MyCat的压缩包
alt + p --------> put D:/Mycat-server-1.6.7.3-release-20190927161129-linux.tar.gz

2). 解压MyCat的压缩包
tar -zxvf Mycat-server-1.6.7.3-release-20190927161129-linux.tar.gz -C /usr/local	
```







### 2.2 核心概念



#### 2.2.1 分片

简单来说，就是指通过某种特定的条件，将我们存放在同一个数据库中的数据分散存放到多个数据库（主机）上面，以达到分散单台设备负载的效果。 



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220405151431172.png)



虚线以上是逻辑结构图, 虚线以下是物理结构图。



#### 2.2.2 逻辑库(schema)

MyCat是一个数据库中间件，通常对实际应用来说，并不需要知道中间件的存在，业务开发人员只需要知道数据库的概念，所以数据库中间件可以被看做是一个或多个数据库集群构成的逻辑库。





#### 2.2.3 逻辑表（table）

既然有逻辑库，那么就会有逻辑表，分布式数据库中，对应用来说，读写数据的表就是逻辑表。逻辑表，可以是数据切分后，分布在一个或多个分片库中，也可以不做数据切分，不分片，只有一个表构成。



1). 分片表

是指那些原有的很大数据的表，需要切分到多个数据库的表，这样，每个分片都有一部分数据，所有分片构成了完整的数据。 总而言之就是需要进行分片的表。如 ：tb_order 表是一个分片表, 数据按照规则被切分到dn1、dn2两个节点。



2). 非分片表

一个数据库中并不是所有的表都很大，某些表是可以不用进行切分的，非分片是相对分片表来说的，就是那些不需要进行数据切分的表。如： tb_city是非分片表 , 数据只存于其中的一个节点 dn1 上。



3). ER表

关系型数据库是基于实体关系模型(Entity Relationship Model)的, MyCat中的ER表便来源于此。 MyCat提出了基于ER关系的数据分片策略 , 字表的记录与其所关联的父表的记录存放在同一个数据分片中, 通过表分组(Table Group)保证数据关联查询不会跨库操作。



4). 全局表

在一个大型的项目中,会存在一部分字典表(码表) , 在其中存储的是项目中的一些基础的数据 , 而这些基础的数据 , 数据量都不大 , 在各个业务表中可能都存在关联 。当业务表由于数据量大而分片后 ， 业务表与附属的数据字典表之间的关联查询就变成了比较棘手的问题 ， 在MyCat中可以通过数据冗余来解决这类表的关联查询 ， 即所有分片都复制这一份数据（数据字典表），因此可以把这些冗余数据的表定义为全局表。



#### 2.2.4 分片节点(dataNode)

数据切分后，一个大表被分到不同的分片数据库上面，每个表分片所在的数据库就是分片节点（dataNode）。



#### 2.2.5 节点主机(dataHost)

数据切分后，每个分片节点（dataNode）不一定都会独占一台机器，同一机器上面可以有多个分片数据库，这样一个或多个分片节点（dataNode）所在的机器就是节点主机（dataHost）,为了规避单节点主机并发数限制，尽量将读写压力高的分片节点（dataNode）均衡的放在不同的节点主机（dataHost）。



#### 2.2.6 分片规则(rule)

前面讲了数据切分，一个大表被分成若干个分片表，就需要一定的规则，这样按照某种业务规则把数据分到某个分片的规则就是分片规则，数据切分选择合适的分片规则非常重要，将极大的避免后续数据处理的难度。







### 2.3 分片配置测试

#### 2.3.1 需求

由于 TB_TEST 表中数据量很大, 现在需要对 TB_TEST 表进行数据分片, 分为三个数据节点 , 每一个节点主机位于不同的服务器上, 具体的结构 ,参考下图 : 





#### 2.3.2 环境准备

准备三台虚拟机 , 且安装好MySQL , 并配置好 : 

```bash
IP 地址列表 : 
	39.101.189.62
	101.42.229.218
	49.232.132.201
```



| IP             | 环境         | 备注   |
| -------------- | ------------ | ------ |
| 39.101.189.62  | MySQL、MyCat | 阿里云 |
| 101.42.229.218 | MySQL        | 腾讯云 |
| 49.232.132.201 | MySQL        | 腾讯云 |



#### 2.3.3 配置 schema.xml

schema.xml 作为MyCat中重要的配置文件之一，管理着MyCat的逻辑库、逻辑表以及对应的分片规则、DataNode以及DataSource。弄懂这些配置，是正确使用MyCat的前提。这里就一层层对该文件进行解析。

| 属性     | 含义                                                         |
| -------- | ------------------------------------------------------------ |
| schema   | 标签用于定义MyCat实例中的逻辑库                              |
| table    | 标签定义了MyCat中的逻辑表, rule用于指定分片规则，auto-sharding-long的分片规则是按ID值的范围进行分片 1-5000000 为第1片  5000001-10000000 为第2片....  具体设置我们会在第四节中讲解。 |
| dataNode | 标签定义了MyCat中的数据节点，也就是我们通常说所的数据分片。  |
| dataHost | 标签在mycat逻辑库中也是作为最底层的标签存在，直接定义了具体的数据库实例、读写分离配置和心跳语句。 |



在服务器上创建3个数据库，命名为 db1

修改schema.xml如下：

```xml
<?xml version="1.0"?>
<!DOCTYPE mycat:schema SYSTEM "schema.dtd">
<mycat:schema xmlns:mycat="http://io.mycat/">

	<schema name="ITCAST" checkSQLschema="true" sqlMaxLimit="100">
		<table name="TB_TEST" dataNode="dn1,dn2,dn3" rule="auto-sharding-long" />
	</schema>
	

	<dataNode name="dn1" dataHost="localhost1" database="db1" />
	<dataNode name="dn2" dataHost="localhost2" database="db1" />
	<dataNode name="dn3" dataHost="localhost3" database="db1" />
	
	
	<dataHost name="localhost1" maxCon="1000" minCon="10" balance="0"
			  writeType="0" dbType="mysql" dbDriver="native" switchType="1"  slaveThreshold="100">
		<heartbeat>select user()</heartbeat>
		<writeHost host="hostM1" url="39.101.189.62:3306" user="root" password="123456"></writeHost>
	</dataHost>
	
	<dataHost name="localhost2" maxCon="1000" minCon="10" balance="0"
			  writeType="0" dbType="mysql" dbDriver="native" switchType="1"  slaveThreshold="100">
		<heartbeat>select user()</heartbeat>
		<writeHost host="hostM1" url="101.42.229.218:3306" user="root" password="123456"></writeHost>
	</dataHost>
	
	<dataHost name="localhost3" maxCon="1000" minCon="10" balance="0"
			  writeType="0" dbType="mysql" dbDriver="native" switchType="1"  slaveThreshold="100">
		<heartbeat>select user()</heartbeat>
		<writeHost host="hostM1" url="49.232.132.201:3306" user="root" password="123456"></writeHost>
	</dataHost>
	
	
</mycat:schema>
```



#### 2.3.4 配置 server.xml

server.xml几乎保存了所有mycat需要的系统配置信息。最常用的是在此配置用户名、密码及权限。在system中添加UTF-8字符集设置，否则存储中文会出现问号

```xml
<property name="charset">utf8</property>
```

修改user的设置 ,  我们这里为 ITCAST 设置了两个用户 : 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- - - Licensed under the Apache License, Version 2.0 (the "License"); 
	- you may not use this file except in compliance with the License. - You 
	may obtain a copy of the License at - - http://www.apache.org/licenses/LICENSE-2.0 
	- - Unless required by applicable law or agreed to in writing, software - 
	distributed under the License is distributed on an "AS IS" BASIS, - WITHOUT 
	WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. - See the 
	License for the specific language governing permissions and - limitations 
	under the License. -->
<!DOCTYPE mycat:server SYSTEM "server.dtd">
<mycat:server xmlns:mycat="http://io.mycat/">
	<system>
	<property name="charset">utf8</property>
		
	<property name="nonePasswordLogin">0</property> <!-- 0为需要密码登陆、1为不需要密码登陆 ,默认为0，设置为1则需要指定默认账户-->
	<property name="useHandshakeV10">1</property>
	<property name="useSqlStat">0</property>  <!-- 1为开启实时统计、0为关闭 -->
	<property name="useGlobleTableCheck">0</property>  <!-- 1为开启全加班一致性检测、0为关闭 -->
		<property name="sqlExecuteTimeout">300</property>  <!-- SQL 执行超时 单位:秒-->
		<property name="sequnceHandlerType">2</property>
		<!--<property name="sequnceHandlerPattern">(?:(\s*next\s+value\s+for\s*MYCATSEQ_(\w+))(,|\)|\s)*)+</property>-->
		<!--必须带有MYCATSEQ_或者 mycatseq_进入序列匹配流程 注意MYCATSEQ_有空格的情况-->
		<property name="sequnceHandlerPattern">(?:(\s*next\s+value\s+for\s*MYCATSEQ_(\w+))(,|\)|\s)*)+</property>
	<property name="subqueryRelationshipCheck">false</property> <!-- 子查询中存在关联查询的情况下,检查关联字段中是否有分片字段 .默认 false -->
      <!--  <property name="useCompression">1</property>--> <!--1为开启mysql压缩协议-->
        <!--  <property name="fakeMySQLVersion">5.6.20</property>--> <!--设置模拟的MySQL版本号-->
	<!-- <property name="processorBufferChunk">40960</property> -->
	<!-- 
	<property name="processors">1</property> 
	<property name="processorExecutor">32</property> 
	 -->
        <!--默认为type 0: DirectByteBufferPool | type 1 ByteBufferArena | type 2 NettyBufferPool -->
		<property name="processorBufferPoolType">0</property>
		<!--默认是65535 64K 用于sql解析时最大文本长度 -->
		<!--<property name="maxStringLiteralLength">65535</property>-->
		<!--<property name="sequnceHandlerType">0</property>-->
		<!--<property name="backSocketNoDelay">1</property>-->
		<!--<property name="frontSocketNoDelay">1</property>-->
		<!--<property name="processorExecutor">16</property>-->
		<!--
			<property name="serverPort">8066</property> <property name="managerPort">9066</property> 
			<property name="idleTimeout">300000</property> <property name="bindIp">0.0.0.0</property>
			<property name="dataNodeIdleCheckPeriod">300000</property> 5 * 60 * 1000L; //连接空闲检查
			<property name="frontWriteQueueSize">4096</property> <property name="processors">32</property> -->
		<!--分布式事务开关，0为不过滤分布式事务，1为过滤分布式事务（如果分布式事务内只涉及全局表，则不过滤），2为不过滤分布式事务,但是记录分布式事务日志-->
		<property name="handleDistributedTransactions">0</property>
		
			<!--
			off heap for merge/order/group/limit      1开启   0关闭
		-->
		<property name="useOffHeapForMerge">0</property>

		<!--
			单位为m
		-->
        <property name="memoryPageSize">64k</property>

		<!--
			单位为k
		-->
		<property name="spillsFileBufferSize">1k</property>

		<property name="useStreamOutput">0</property>

		<!--
			单位为m
		-->
		<property name="systemReserveMemorySize">384m</property>


		<!--是否采用zookeeper协调切换  -->
		<property name="useZKSwitch">false</property>

		<!-- XA Recovery Log日志路径 -->
		<!--<property name="XARecoveryLogBaseDir">./</property>-->

		<!-- XA Recovery Log日志名称 -->
		<!--<property name="XARecoveryLogBaseName">tmlog</property>-->
		<!--如果为 true的话 严格遵守隔离级别,不会在仅仅只有select语句的时候在事务中切换连接-->
		<property name="strictTxIsolation">false</property>
		
		<property name="useZKSwitch">true</property>
		
	</system>
	
	<!-- 全局SQL防火墙设置 -->
	<!--白名单可以使用通配符%或着*-->
	<!--例如<host host="127.0.0.*" user="root"/>-->
	<!--例如<host host="127.0.*" user="root"/>-->
	<!--例如<host host="127.*" user="root"/>-->
	<!--例如<host host="1*7.*" user="root"/>-->
	<!--这些配置情况下对于127.0.0.1都能以root账户登录-->
	<!--
	<firewall>
	   <whitehost>
	      <host host="1*7.0.0.*" user="root"/>
	   </whitehost>
       <blacklist check="false">
       </blacklist>
	</firewall>
	-->

	<user name="root" defaultAccount="true">
		<property name="password">123456</property>
		<property name="schemas">ITCAST</property>
		
		<!-- 表级 DML 权限设置 -->
		<!-- 		
		<privileges check="false">
			<schema name="TESTDB" dml="0110" >
				<table name="tb01" dml="0000"></table>
				<table name="tb02" dml="1111"></table>
			</schema>
		</privileges>		
		 -->
	</user>

	<user name="user">
		<property name="password">123456</property>
		<property name="schemas">ITCAST</property>
		<property name="readOnly">true</property>
	</user>

</mycat:server>
```





#### 2.3.5 启动MyCat

启动:

```
bin/mycat start
bin/mycat stop
bin/mycat status
```



连接端口号  8066 

通过命令行连接：

```sql
mysql -h 127.0.0.1 -P 8066 -u root -p

```

通过Navicat等工具连接：

注意端口输入3306





#### 2.3.6 MyCat分片测试

进入mycat ，执行下列语句创建一个表

```sql
CREATE TABLE TB_TEST (
  id BIGINT(20) NOT NULL,
  title VARCHAR(100) NOT NULL ,
  PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 ;
```



我们再查看MySQL的3个库，发现表都自动创建好啦。好神奇。



接下来是插入表数据，注意，在写 INSERT 语句时一定要写把字段列表写出来，否则会出现下列错误提示：

错误代码： 1064

partition table, insert must provide ColumnList

我们试着插入一些数据：

```sql
INSERT INTO TB_TEST(ID,TITLE) VALUES(1,'goods1');
INSERT INTO TB_TEST(ID,TITLE) VALUES(2,'goods2');
INSERT INTO TB_TEST(ID,TITLE) VALUES(3,'goods3');
```

我们会发现这些数据被写入到第一个节点中了，那什么时候数据会写到第二个节点中呢？

我们插入下面的数据就可以插入第二个节点了

```sql
INSERT INTO TB_TEST(ID,TITLE) VALUES(5000001,'goods5000001');
```

因为我们采用的分片规则是每节点存储500万条数据，所以当ID大于5000000则会存储到第二个节点上。



#### 2.3.7 说明

上面我们采取的分片规则为`rule="auto-sharding-long"`

在`rule.xml`文件中我们找到此分片规则的定义为：

```bash
# autopartition-long.txt文件内容如下

# range start-end ,data node index
# K=1000,M=10000.
0-500M=0
500M-1000M=1
1000M-1500M=2
```

即每一个分片结点存储500w数据。





<hr>


## 3. MyCat配置文件详解

### 3.1 server.xml

#### 3.1.1 system 标签

| 属性                      | 取值       | 含义                                                         |
| ------------------------- | ---------- | ------------------------------------------------------------ |
| charset                   | utf8       | 设置Mycat的字符集, 字符集需要与MySQL的字符集保持一致         |
| nonePasswordLogin         | 0,1        | 0为需要密码登陆、1为不需要密码登陆 ,默认为0，设置为1则需要指定默认账户 |
| useHandshakeV10           | 0,1        | 使用该选项主要的目的是为了能够兼容高版本的jdbc驱动, 是否采用HandshakeV10Packet来与client进行通信, 1:是, 0:否 |
| useSqlStat                | 0,1        | 开启SQL实时统计, 1 为开启 , 0 为关闭 ;<br />开启之后, MyCat会自动统计SQL语句的执行情况 ;<br />mysql -h 127.0.0.1 -P 9066 -u root -p<br />查看MyCat执行的SQL, 执行效率比较低的SQL , SQL的整体执行情况、读写比例等 ;<br />show @@sql ; show @@sql.slow ; show @@sql.sum ; |
| useGlobleTableCheck       | 0,1        | 是否开启全局表的一致性检测。1为开启 ，0为关闭 。             |
| sqlExecuteTimeout         | 1000       | SQL语句执行的超时时间 , 单位为 s ;                           |
| sequnceHandlerType        | 0,1,2      | 用来指定Mycat全局序列类型，0 为本地文件，1 为数据库方式，2 为时间戳列方式，默认使用本地文件方式，文件方式主要用于测试 |
| sequnceHandlerPattern     | 正则表达式 | 必须带有MYCATSEQ_或者 mycatseq_进入序列匹配流程 注意MYCATSEQ_有空格的情况 |
| subqueryRelationshipCheck | true,false | 子查询中存在关联查询的情况下,检查关联字段中是否有分片字段 .默认 false |
| useCompression            | 0,1        | 开启mysql压缩协议 , 0 : 关闭, 1 : 开启                       |
| fakeMySQLVersion          | 5.5,5.6    | 设置模拟的MySQL版本号                                        |
| defaultSqlParser          |            | 由于MyCat的最初版本使用了FoundationDB的SQL解析器, 在MyCat1.3后增加了Druid解析器, 所以要设置defaultSqlParser属性来指定默认的解析器; 解析器有两个 : druidparser 和 fdbparser, 在MyCat1.4之后,默认是druidparser, fdbparser已经废除了 |
| processors                | 1,2....    | 指定系统可用的线程数量, 默认值为CPU核心 x 每个核心运行线程数量; processors 会影响processorBufferPool, processorBufferLocalPercent, processorExecutor属性, 所有, 在性能调优时, 可以适当地修改processors值 |
| processorBufferChunk      |            | 指定每次分配Socket Direct Buffer默认值为4096字节, 也会影响BufferPool长度, 如果一次性获取字节过多而导致buffer不够用, 则会出现警告, 可以调大该值 |
| processorExecutor         |            | 指定NIOProcessor上共享 businessExecutor固定线程池的大小; MyCat把异步任务交给 businessExecutor线程池中, 在新版本的MyCat中这个连接池使用频次不高, 可以适当地把该值调小 |
| packetHeaderSize          |            | 指定MySQL协议中的报文头长度, 默认4个字节                     |
| maxPacketSize             |            | 指定MySQL协议可以携带的数据最大大小, 默认值为16M             |
| idleTimeout               | 30         | 指定连接的空闲时间的超时长度;如果超时,将关闭资源并回收, 默认30分钟 |
| txIsolation               | 1,2,3,4    | 初始化前端连接的事务隔离级别,默认为 REPEATED_READ , 对应数字为3<br />READ_UNCOMMITED=1;<br />READ_COMMITTED=2;<br />REPEATED_READ=3;<br />SERIALIZABLE=4; |
| sqlExecuteTimeout         | 300        | 执行SQL的超时时间, 如果SQL语句执行超时,将关闭连接; 默认300秒; |
| serverPort                | 8066       | 定义MyCat的使用端口, 默认8066                                |
| managerPort               | 9066       | 定义MyCat的管理端口, 默认9066                                |



#### 3.1.2 user 标签

```xml
<user name="root" defaultAccount="true">
    <property name="password">123456</property>
    <property name="schemas">ITCAST</property>
    <property name="readOnly">true</property>
    <property name="benchmark">1000</property>
    <property name="usingDecrypt">0</property>
    
    <!-- 表级 DML 权限设置 -->
    <!-- 		
    <privileges check="false">
        <schema name="TESTDB" dml="0110" >
            <table name="tb01" dml="0000"></table>
            <table name="tb02" dml="1111"></table>
        </schema>
    </privileges>		
    -->
</user>
```

user标签主要用于定义登录MyCat的用户和权限 :

1). \<user name="root" defaultAccount="true"> : name 属性用于声明用户名 ;

2). \<property name="password">123456\</property> : 指定该用户名访问MyCat的密码 ;

3). \<property name="schemas">ITCAST\</property> : 能够访问的逻辑库, 多个的话, 使用 "," 分割

4). \<property name="readOnly">true\</property> : 是否只读

5). \<property name="benchmark">11111\</property> : 指定前端的整体连接数量 , 0 或不设置表示不限制 

6). \<property name="usingDecrypt">0\</property> : 是否对密码加密默认 0 否 , 1 是

```
java -cp Mycat-server-1.6.7.3-release.jar io.mycat.util.DecryptUtil 0:root:123456
```

7). \<privileges check="false">

A. 对用户的 schema 及 下级的 table 进行精细化的 DML 权限控制; 

B. privileges 节点中的 check 属性是用 于标识是否开启 DML 权限检查， 默认 false 标识不检查，当然 privileges 节点不配置，等同 check=false, 由于 Mycat 一个用户的 schemas 属性可配置多个 schema ，所以 privileges 的下级节点 schema 节点同样 可配置多个，对多库多表进行细粒度的 DML 权限控制;

C. 权限修饰符四位数字(0000 - 1111)，对应的操作是 IUSD ( 增，改，查，删 )。同时配置了库跟表的权限，就近原则。以表权限为准。



#### 3.1.3 firewall 标签

firewall标签用来定义防火墙；firewall下whitehost标签用来定义 IP白名单 ，blacklist用来定义 SQL黑名单。

```xml
<firewall>
    <!-- 白名单配置 -->
    <whitehost>
        <host user="root" host="127.0.0.1"></host>
    </whitehost>
    <!-- 黑名单配置 -->
    <blacklist check="true">
        <property name="selelctAllow">false</property>
    </blacklist>
</firewall>
```

黑名单拦截明细配置:

| 配置项                      | 缺省值 | 描述                                                         |
| --------------------------- | ------ | ------------------------------------------------------------ |
| selelctAllow                | true   | 是否允许执行 SELECT 语句                                     |
| selectAllColumnAllow        | true   | 是否允许执行 SELECT * FROM T 这样的语句。如果设置为 false，不允许执行 select * from t，但可以select * from (select id, name from t) a。这个选项是防御程序通过调用 select * 获得数据表的结构信息。 |
| selectIntoAllow             | true   | SELECT 查询中是否允许 INTO 字句                              |
| deleteAllow                 | true   | 是否允许执行 DELETE 语句                                     |
| updateAllow                 | true   | 是否允许执行 UPDATE 语句                                     |
| insertAllow                 | true   | 是否允许执行 INSERT 语句                                     |
| replaceAllow                | true   | 是否允许执行 REPLACE 语句                                    |
| mergeAllow                  | true   | 是否允许执行 MERGE 语句，这个只在 Oracle 中有用              |
| callAllow                   | true   | 是否允许通过 jdbc 的 call 语法调用存储过程                   |
| setAllow                    | true   | 是否允许使用 SET 语法                                        |
| truncateAllow               | true   | truncate 语句是危险，缺省打开，若需要自行关闭                |
| createTableAllow            | true   | 是否允许创建表                                               |
| alterTableAllow             | true   | 是否允许执行 Alter Table 语句                                |
| dropTableAllow              | true   | 是否允许修改表                                               |
| commentAllow                | false  | 是否允许语句中存在注释，Oracle 的用户不用担心，Wall 能够识别 hints和注释的区别 |
| noneBaseStatementAllow      | false  | 是否允许非以上基本语句的其他语句，缺省关闭，通过这个选项就能够屏蔽 DDL。 |
| multiStatementAllow         | false  | 是否允许一次执行多条语句，缺省关闭                           |
| useAllow                    | true   | 是否允许执行 mysql 的 use 语句，缺省打开                     |
| describeAllow               | true   | 是否允许执行 mysql 的 describe 语句，缺省打开                |
| showAllow                   | true   | 是否允许执行 mysql 的 show 语句，缺省打开                    |
| commitAllow                 | true   | 是否允许执行 commit 操作                                     |
| rollbackAllow               | true   | 是否允许执行 roll back 操作                                  |
| 拦截配置－永真条件          |        |                                                              |
| selectWhereAlwayTrueCheck   | true   | 检查 SELECT 语句的 WHERE 子句是否是一个永真条件              |
| selectHavingAlwayTrueCheck  | true   | 检查 SELECT 语句的 HAVING 子句是否是一个永真条件             |
| deleteWhereAlwayTrueCheck   | true   | 检查 DELETE 语句的 WHERE 子句是否是一个永真条件              |
| deleteWhereNoneCheck        | false  | 检查 DELETE 语句是否无 where 条件，这是有风险的，但不是 SQL 注入类型的风险 |
| updateWhereAlayTrueCheck    | true   | 检查 UPDATE 语句的 WHERE 子句是否是一个永真条件              |
| updateWhereNoneCheck        | false  | 检查 UPDATE 语句是否无 where 条件，这是有风险的，但不是SQL 注入类型的风险 |
| conditionAndAlwayTrueAllow  | false  | 检查查询条件(WHERE/HAVING 子句)中是否包含 AND 永真条件       |
| conditionAndAlwayFalseAllow | false  | 检查查询条件(WHERE/HAVING 子句)中是否包含 AND 永假条件       |
| conditionLikeTrueAllow      | true   | 检查查询条件(WHERE/HAVING 子句)中是否包含 LIKE 永真条件      |
| 其他拦截配置                |        |                                                              |
| selectIntoOutfileAllow      | false  | SELECT ... INTO OUTFILE 是否允许，这个是 mysql 注入攻击的常见手段，缺省是禁止的 |
| selectUnionCheck            | true   | 检测 SELECT UNION                                            |
| selectMinusCheck            | true   | 检测 SELECT MINUS                                            |
| selectExceptCheck           | true   | 检测 SELECT EXCEPT                                           |
| selectIntersectCheck        | true   | 检测 SELECT INTERSECT                                        |
| mustParameterized           | false  | 是否必须参数化，如果为 True，则不允许类似 WHERE ID = 1 这种不参数化的 SQL |
| strictSyntaxCheck           | true   | 是否进行严格的语法检测，Druid SQL Parser 在某些场景不能覆盖所有的SQL 语法，出现解析 SQL 出错，可以临时把这个选项设置为 false，同时把 SQL 反馈给 Druid 的开发者。 |
| conditionOpXorAllow         | false  | 查询条件中是否允许有 XOR 条件。XOR 不常用，很难判断永真或者永假，缺省不允许。 |
| conditionOpBitwseAllow      | true   | 查询条件中是否允许有"&"、"~"、"\|"、"^"运算符。              |
| conditionDoubleConstAllow   | false  | 查询条件中是否允许连续两个常量运算表达式                     |
| minusAllow                  | true   | 是否允许 SELECT * FROM A MINUS SELECT * FROM B 这样的语句    |
| intersectAllow              | true   | 是否允许 SELECT * FROM A INTERSECT SELECT * FROM B 这样的语句 |
| constArithmeticAllow        | true   | 拦截常量运算的条件，比如说 WHERE FID = 3 - 1，其中"3 - 1"是常量运算表达式。 |
| limitZeroAllow              | false  | 是否允许 limit 0 这样的语句                                  |
| 禁用对象检测配置            |        |                                                              |
| tableCheck                  | true   | 检测是否使用了禁用的表                                       |
| schemaCheck                 | true   | 检测是否使用了禁用的 Schema                                  |
| functionCheck               | true   | 检测是否使用了禁用的函数                                     |
| objectCheck                 | true   | 检测是否使用了“禁用对对象”                                   |
| variantCheck                | true   | 检测是否使用了“禁用的变量”                                   |
| readOnlyTables              | 空     | 指定的表只读，不能够在 SELECT INTO、DELETE、UPDATE、INSERT、MERGE 中作为"被修改表"出现 |



### 3.2 schema.xml

schema.xml 作为MyCat中最重要的配置文件之一 , 涵盖了MyCat的逻辑库 、 表 、 分片规则、分片节点及数据源的配置。

#### 3.2.1 schema 标签

```xml
<schema name="ITCAST" checkSQLschema="false" sqlMaxLimit="100">
	<table name="TB_TEST" dataNode="dn1,dn2,dn3" rule="auto-sharding-long" />
</schema>
```

schema 标签用于定义 MyCat实例中的逻辑库 , 一个MyCat实例中, 可以有多个逻辑库 , 可以通过 schema 标签来划分不同的逻辑库。MyCat中的逻辑库的概念 ， 等同于MySQL中的database概念 , 需要操作某个逻辑库下的表时, 也需要切换逻辑库:

```
use ITCAST;
```



##### 3.2.1.1 属性

schema 标签的属性如下 : 

1). name

指定逻辑库的库名 , 可以自己定义任何字符串 ;



2). checkSQLschema

取值为 true / false ;

如果设置为true时 , 如果我们执行的语句为 "select * from ITCAST.TB_TEST;" , 则MyCat会自动把schema字符去掉, 把SQL语句修改为 "select * from TB_TEST;" 可以避免SQL发送到后端数据库执行时, 报table不存在的异常 。

不过当我们在编写SQL语句时, 指定了一个不存在schema, MyCat是不会帮我们自动去除的 ,这个时候数据库就会报错, 所以在编写SQL语句时,最好不要加逻辑库的库名, 直接查询表即可。



3). sqlMaxLimit

当该属性设置为某个数值时,每次执行的SQL语句如果没有加上limit语句, MyCat也会自动在limit语句后面加上对应的数值 。也就是说， 如果设置了该值为100，则执行 select * from TB_TEST 与 select * from TB_TEST limit 100 是相同的效果 。

所以在正常的使用中, 建立设置该值 , 这样就可以避免每次有过多的数据返回。



##### 3.2.1.2 子标签table

table 标签定义了MyCat中逻辑库schema下的逻辑表 , 所有需要拆分的表都需要在table标签中定义 。

```xml
<table name="TB_TEST" dataNode="dn1,dn2,dn3" rule="auto-sharding-long" />
```

属性如下 ： 

![1574954845578](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1574954845578.png)



1). name 

定义逻辑表的表名 , 在该逻辑库下必须唯一。



2). dataNode

定义的逻辑表所属的dataNode , 该属性需要与dataNode标签中的name属性的值对应。 如果一张表拆分的数据，存储在多个数据节点上，多个节点的名称使用","分隔 。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1574955059453.png)



3). rule

该属性用于指定逻辑表的分片规则的名字, 规则的名字是在rule.xml文件中定义的, 必须与tableRule标签中name属性对应。

 ![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1574955534319.png)



4). ruleRequired

该属性用于指定表是否绑定分片规则, 如果配置为true, 但是没有具体的rule, 程序会报错。



5). primaryKey

逻辑表对应真实表的主键

如: 分片规则是使用主键进行分片, 使用主键进行查询时, 就会发送查询语句到配置的所有的datanode上; 如果使用该属性配置真实表的主键, 那么MyCat会缓存主键与具体datanode的信息, 再次使用主键查询就不会进行广播式查询了, 而是直接将SQL发送给具体的datanode。



6). type

该属性定义了逻辑表的类型，目前逻辑表只有全局表和普通表。

全局表：type的值是 global , 代表 全局表 。

普通表：无



7). autoIncrement

mysql对非自增长主键，使用last_insert_id() 是不会返回结果的，只会返回0。所以，只有定义了自增长主键的表，才可以用last_insert_id()返回主键值。
mycat提供了自增长主键功能，但是对应的mysql节点上数据表，没有auto_increment,那么在mycat层调用last_insert_id()也是不会返回结果的。

如果使用这个功能， 则最好配合数据库模式的全局序列。使用 autoIncrement="true" 指定该表使用自增长主键,这样MyCat才不会抛出 "分片键找不到" 的异常。 autoIncrement的默认值为 false。



8). needAddLimit

指定表是否需要自动在每个语句的后面加上limit限制, 默认为true。



#### 3.2.2 dataNode 标签

```xml
<dataNode name="dn1" dataHost="host1" database="db1" />
```

dataNode标签中定义了MyCat中的数据节点, 也就是我们通常说的数据分片。一个dataNode标签就是一个独立的数据分片。



具体的属性 ： 

| 属性     | 含义                 | 描述                                                         |
| -------- | -------------------- | ------------------------------------------------------------ |
| name     | 数据节点的名称       | 需要唯一 ; 在table标签中会引用这个名字, 标识表与分片的对应关系 |
| dataHost | 数据库实例主机名称   | 引用自 dataHost 标签中name属性                               |
| database | 定义分片所属的数据库 |                                                              |



#### 3.2.3 dataHost 标签

```xml
<dataHost name="host1" maxCon="1000" minCon="10" balance="0"
          writeType="0" dbType="mysql" dbDriver="native" switchType="1"  slaveThreshold="100">
    <heartbeat>select user()</heartbeat>
    <writeHost host="hostM1" url="192.168.192.147:3306" user="root" password="itcast"></writeHost>
</dataHost>	
```

该标签在MyCat逻辑库中作为底层标签存在, 直接定义了具体的数据库实例、读写分离、心跳语句。

##### 3.2.3.1 属性

| 属性       | 含义           | 描述                                                         |
| ---------- | -------------- | ------------------------------------------------------------ |
| name       | 数据节点名称   | 唯一标识， 供上层标签使用                                    |
| maxCon     | 最大连接数     | 内部的writeHost、readHost都会使用这个属性                    |
| minCon     | 最小连接数     | 内部的writeHost、readHost初始化连接池的大小                  |
| balance    | 负载均衡类型   | 取值0,1,2,3 ; 后面章节会详细介绍;                            |
| writeType  | 写操作分发方式 | 0 : 写操作都转发到第1台writeHost, writeHost1挂了, 会切换到writeHost2上; <br />1 : 所有的写操作都随机地发送到配置的writeHost上 ; |
| dbType     | 后端数据库类型 | mysql, mongodb , oracle                                      |
| dbDriver   | 数据库驱动     | 指定连接后端数据库的驱动,目前可选值有 native和JDBC。native执行的是二进制的MySQL协议，可以使用MySQL和MariaDB。其他类型数据库需要使用JDBC（需要在MyCat/lib目录下加入驱动jar） |
| switchType | 数据库切换策略 | 取值 -1,1,2,3 ; 后面章节会详细介绍;                          |



##### 3.2.3.2 子标签heartbeat

配置MyCat与后端数据库的心跳，用于检测后端数据库的状态。heartbeat用于配置心跳检查语句。例如 ： MySQL中可以使用 select user(), Oracle中可以使用 select 1 from dual等。



##### 3.2.3.3 子标签writeHost、readHost

指定后端数据库的相关配置， 用于实例化后端连接池。 writeHost指定写实例， readHost指定读实例。

在一个dataHost中可以定义多个writeHost和readHost。但是，如果writeHost指定的后端数据库宕机， 那么这个writeHost绑定的所有readHost也将不可用。



属性：

| 属性名       | 含义               | 取值                                                         |
| ------------ | ------------------ | ------------------------------------------------------------ |
| host         | 实例主机标识       | 对于writeHost一般使用 *M1；对于readHost，一般使用 *S1；      |
| url          | 后端数据库连接地址 | 如果是native，一般为 ip:port ; 如果是JDBC, 一般为jdbc:mysql://ip:port/ |
| user         | 数据库用户名       | root                                                         |
| password     | 数据库密码         | itcast                                                       |
| weight       | 权重               | 在readHost中作为读节点权重                                   |
| usingDecrypt | 密码加密           | 默认 0 否 , 1 是                                             |



### 3.3 rule.xml

rule.xml中定义所有拆分表的规则, 在使用过程中可以灵活的使用分片算法, 或者对同一个分片算法使用不同的参数, 它让分片过程可配置化。

#### 3.3.1 tableRule标签

```xml
<tableRule name="auto-sharding-long">
    <rule>
        <columns>id</columns>
        <algorithm>rang-long</algorithm>
    </rule>
</tableRule>
```

A. name : 指定分片算法的名称

B. rule : 定义分片算法的具体内容 

C. columns : 指定对应的表中用于分片的列名

D. algorithm : 对应function中指定的算法名称



#### 3.3.2 Function标签

```xml
<function name="rang-long" class="io.mycat.route.function.AutoPartitionByLong">
	<property name="mapFile">autopartition-long.txt</property>
</function>
```

A. name : 指定算法名称, 该文件中唯一 

B. class : 指定算法的具体类

C. property : 根据算法的要求执行 



### 3.4 sequence 配置文件

在分库分表的情况下 , 原有的自增主键已无法满足在集群中全局唯一的主键 ,因此, MyCat中提供了全局sequence来实现主键 , 并保证全局唯一。那么在MyCat的配置文件 sequence_conf.properties 中就配置的是序列的相关配置。

主要包含以下几种形式：

1). 本地文件方式

2). 数据库方式

3). 本地时间戳方式

4). 其他方式

5). 自增长主键







## 4. MyCat分片

### 4.1 垂直拆分

#### 4.1.1 概述（想象购物快递的流程被垂直拆分）

![image-20220405155132442](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220405155132442.png)





一种是按照不同的表（或者Schema）来切分到不同的数据库（主机）之上，这种切分可以称之为数据的垂直（纵向）切分。



#### 4.1.2 案例场景

![1575725341210](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1575725341210.png)



在业务系统中, 有以下表结构 ,但是由于用户与订单每天都会产生大量的数据, 单台服务器的数据存储及处理能力是有限的, 可以对数据库表进行拆分, 原有的数据库表: 

![1575726526012](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1575726526012.png)



### 4.2 水平拆分













### 4.3 分片规则







































<hr>





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









