

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

```
IP 地址列表 : 
	39.101.189.62
	101.42.229.218
	49.232.132.201
```



| IP             | 环境         |
| -------------- | ------------ |
| 39.101.189.62  | MySQL、MyCat |
| 101.42.229.218 | MySQL        |
| 49.232.132.201 | MySQL        |



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













<hr>



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









