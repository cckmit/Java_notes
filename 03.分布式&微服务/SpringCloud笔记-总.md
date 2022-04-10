---
title: SpringCloud_2黑马
date: 2022-01-01 19:43:15
tags:

---

> 黑马程序员

- [常用总结与问题解决](#常用总结与问题解决)
  - [常用总结](#常用总结)
  - [问题解决](#问题解决)
- [服务框架学习路线](#服务框架学习路线)
- [springcloud实用篇一](#springcloud实用篇一)
  - [一、认识微服务](#一认识微服务)
    - [服务框架演变](#服务框架演变)
    - [微服务技术对比](#微服务技术对比)
    - [SpringCloud](#springcloud)
  - [二、微服务拆分案例](#二微服务拆分案例)
    - [服务拆分](#服务拆分)
    - [案例cloud-demo](#案例cloud-demo)
    - [概念：提供者与消费者](#概念提供者与消费者)
  - [三、eureka注册中心](#三eureka注册中心)
    - [1、远程调用的问题（一堆问题）](#1远程调用的问题一堆问题)
    - [2、eureka原理](#2eureka原理)
    - [3、搭建EurekaServer注册中心](#3搭建eurekaserver注册中心)
    - [4、服务注册](#4服务注册)
    - [5、服务发现](#5服务发现)
  - [四、Ribbon负载均衡原理](#四ribbon负载均衡原理)
    - [负载均衡原理](#负载均衡原理)
    - [负载均衡策略](#负载均衡策略)
    - [懒加载](#懒加载)
  - [五、nacos注册中心](#五nacos注册中心)
    - [1、认识和安装](#1认识和安装)
    - [2、快速入门](#2快速入门)
    - [3、服务分级存储模型](#3服务分级存储模型)
    - [4、环境隔离namespace](#4环境隔离namespace)
      - [如何修改一个服务的命名空间：](#如何修改一个服务的命名空间)
      - [总结](#总结)
      - [临时实例和非临时实例](#临时实例和非临时实例)
- [springcloud实用篇二](#springcloud实用篇二)
  - [一、Nacos配置管理](#一nacos配置管理)
    - [1、统一配置管理](#1统一配置管理)
      - [Nacos实现配置管理](#nacos实现配置管理)
      - [微服务配置拉取](#微服务配置拉取)
    - [2、配置热更新](#2配置热更新)
    - [3、配置共享](#3配置共享)
    - [4、搭建Nacos集群](#4搭建nacos集群)
      - [搭建集群的基本步骤：](#搭建集群的基本步骤)
  - [二、Feign远程调用（HTTP客户端Feign）](#二feign远程调用http客户端feign)
    - [1、Feign替代RestTemplate](#1feign替代resttemplate)
      - [1.1 RestTemplate方式调用存在的问题](#11-resttemplate方式调用存在的问题)
      - [1.2 Feign的介绍](#12-feign的介绍)
      - [1.3 定义和使用Feign客户端](#13-定义和使用feign客户端)
    - [2、自定义配置](#2自定义配置)
      - [配置Feign日志有两种方式：](#配置feign日志有两种方式)
    - [3、Figen使用优化（性能调优）](#3figen使用优化性能调优)
      - [连接池配置](#连接池配置)
    - [4、最佳实践](#4最佳实践)
      - [Feign的最佳实践](#feign的最佳实践)
      - [实现Feign最佳实践](#实现feign最佳实践)
  - [三、Gateway服务网关](#三gateway服务网关)
    - [1、为什么需要网关](#1为什么需要网关)
    - [2、gateway快速入门](#2gateway快速入门)
      - [搭建服务网关](#搭建服务网关)
    - [3、断言工厂](#3断言工厂)
    - [4、全局过滤器](#4全局过滤器)
      - [路由过滤器 GatewayFilter](#路由过滤器-gatewayfilter)
      - [过滤器工厂 GatewayFilterFactory](#过滤器工厂-gatewayfilterfactory)
      - [案例：给所有进入userservice的请求添加一个请求头：Truth=itcast is freaking awesome!](#案例给所有进入userservice的请求添加一个请求头truthitcast-is-freaking-awesome)
      - [全局过滤器 GlobalFilter](#全局过滤器-globalfilter)
      - [案例：定义全局过滤器，拦截并判断用户身份](#案例定义全局过滤器拦截并判断用户身份)
      - [过滤器执行顺序](#过滤器执行顺序)
    - [5、跨域问题处理](#5跨域问题处理)





# 写在前面

[SpringCloud+RabbitMQ+Docker+Redis+搜索+分布式_黑马20210811](https://www.bilibili.com/video/BV1LQ4y127n4?p=3)





# 常用总结与问题解决

## 常用总结
```lua
项目布局  
cloud_demo -- 父工程：定义版本    　　   
　　├── eureka-server -- 注册中心
　　├── feign-api -- 远程调用接口定义
　　├── gateway -- 网关配置
　　├── order-service -- 订单服务
　　├── user-service -- 用户服务
　　└── pom.xml -- 父pom　
```

<br>



> 注册中心eureka：

服务消费者该如何获取服务提供者的地址信息？

果有多个服务提供者，消费者该如何选择？

消费者如何得知服务提供者的健康状态？

> 负载均衡：




> 配置中心：




> feign远程调用：  


基于RestTemplate发起的http请求实现远程调用，只要知道对方的ip、端口、接口路径、请求参数即可。
一个服务既可以是提供者，也可以是消费者。

> Gateway服务网关：




## 问题解决



# 服务框架学习路线

**完整微服务技术栈：微服务技术 + 持续集成**

> 微服务并不是等于springcloud技术
>
> 消息队列：异步通信大大提高服务的并发：秒杀等高并发场景利用
>
> 系统监控链路追踪：实时监控每一个服务节点的运行状态、CPU负载、内存的占用等等情况，一旦出现任何的问题，可以直接定位到某一具体的方法栈信息，快速定位异常所在。
>
> 自动化部署：工具Jenkins，Docker打包形成镜像

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211211113438830.png)



**需要学习的微服务知识**

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/%E5%BE%AE%E6%9C%8D%E5%8A%A11.png)





![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211211120632882.png)



**学习路径**

![](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211120655865.png)

<br>

**分层次教学**

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211211122543190.png)

<br>

**知识层次**

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/%E5%AD%A6%E4%B9%A0%E5%B1%82%E6%AC%A1.png)





# springcloud实用篇一

> 注册中心、负载均衡器

<br>

## 一、认识微服务

### 服务框架演变

**1、单体架构：将业务的所有功能集中在一个项目中开发，打成一个包部署。**

**2、分布式架构：根据业务功能对系统进行拆分，每个业务模块作为独立项目开发，称为一个服务。**

**3、微服务是一种经过良好架构设计的分布式架构方案，微服务架构特征：**



> 单一职责：微服务拆分粒度更小，每一个服务都对应唯一的业务能力，做到单一职责，避免重复业务开发
>
> 面向服务：微服务对外暴露业务接口
>
> 自治：团队独立、技术独立、数据独立、部署独立
>
> 隔离性强：服务调用做好隔离、容错、降级，避免出现级联问题

### 微服务技术对比

微服务这种方案需要技术框架来落地，全球的互联网公司都在积极尝试自己的微服务落地技术。在国内最知名的就是SpringCloud和阿里巴巴的Dubbo。

![image-20211211125555526](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211125555526.png)





### SpringCloud

SpringCloud是目前国内使用最广泛的微服务框架。官网地址：https://spring.io/projects/spring-cloud。

SpringCloud集成了各种微服务功能组件，并基于SpringBoot实现了这些**组件的自动装配**，从而提供了良好的开箱即用体验：

![image-20211211130417169](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211130417169.png)



<br>

**SpringCloud与SpringBoot的版本兼容关系如下：**

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211130745877.png" alt="image-20211211130745877" style="zoom: 33%;" />



## 二、微服务拆分案例

### 服务拆分

父工程：定义版本

> **注意事项：**
>
> 单一职责：不同微服务，不要重复开发相同业务
>
> 数据独立：不要访问其它微服务的数据库
>
> 面向服务：将自己的业务暴露为接口，供其它微服务调用

### 案例cloud-demo

**微服务远程调用--查询订单**

**1）注册RestTemplate**

```java
@MapperScan("cn.itcast.order.mapper")
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    /**
     * 创建RestTemplate并注入Spring容器
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

**2）服务远程调用RestTemplate（url中使用硬编码的方式）**

修改order-service中的OrderService的queryOrderById方法：

```java
@Service
public class OrderService {
		@Autowired
    private RestTemplate restTemplate;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.利用RestTemplate发起http请求，查询用户
        // 2.1.url路径
        String url = "http://localhost:8081/user/" + order.getUserId();
        // 2.2.发送http请求，实现远程调用
        User user = restTemplate.getForObject(url, User.class);
        // 3.封装user到Order
        order.setUser(user);
        // 4.返回
        return order;
    }
}
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211211162822962.png)

基于RestTemplate发起的http请求实现远程调用

http请求做远程调用是与语言无关的调用，只要知道对方的ip、端口、接口路径、请求参数即可。

### 概念：提供者与消费者

服务提供者：一次业务中，被其它微服务调用的服务。（提供接口给其它微服务）
服务消费者：一次业务中，调用其它微服务的服务。（调用其它微服务提供的接口）

![image-20211211164700331](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211164700331.png)



<br>

服务A调用服务B，服务B调用服务C，那么服务B是什么角色？

**一个服务既可以是提供者，也可以是消费者。**



## 三、eureka注册中心

### 1、远程调用的问题（一堆问题）

服务消费者该如何获取服务提供者的地址信息？

>服务提供者启动时向eureka注册自己的信息
>
>eureka保存这些信息
>
>消费者根据服务名称向eureka拉取提供者信息

如果有多个服务提供者，消费者该如何选择？

>服务消费者利用负载均衡算法，从服务列表中挑选一个

消费者如何得知服务提供者的健康状态？

>服务提供者会每隔30秒向EurekaServer发送心跳请求，报告健康状态
>
>eureka会更新记录服务列表信息，心跳不正常会被剔除
>
>消费者就可以拉取到最新的信息



### 2、eureka原理

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211211165752560.png" alt="image-20211211165752560" style="zoom: 40%;" />



### 3、搭建EurekaServer注册中心

创建项目，引入spring-cloud-starter-netflix-eureka-server的依赖

```xml
<!--eureka服务端-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

编写启动类，添加`@EnableEurekaServer`注解

```java
@EnableEurekaServer
```

添加application.yml文件，编写下面的配置：

```yaml
server:
  port: 10086 # 服务端口
spring:
  application:
    name: eurekaserver # eureka的服务名称
eureka:
  client:
    service-url:  # eureka的地址信息
      defaultZone: http://127.0.0.1:10086/eureka
```



### 4、服务注册

引入`spring-cloud-starter-netflix-eureka-client`的依赖

```xml
<!--eureka客户端依赖-->
<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

在application.yml文件，编写下面的配置：

```yaml
spring:
  application:
    name: userservice
eureka:
  client:
    service-url:  # eureka的地址信息
      defaultZone: http://127.0.0.1:10086/eureka
```

另外，我们可以将user-service多次启动， 模拟多实例部署，但为了避免端口冲突，需要修改端口设置：

![image-20211211172410982](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211172410982.png)

### 5、服务发现

在order-service完成服务拉取

服务拉取是**基于服务名称获取服务列表**，**然后再对服务列表做负载均衡**

修改OrderService的代码，修改访问的url路径，用服务名代替ip、端口：

```java
String url = "http://userservice/user/" + order.getUserId();
```

在order-service项目的启动类OrderApplication中的RestTemplate添加负载均衡注解：**@LoadBalanced**

```java
@Bean
@LoadBalanced
public RestTemplate restTemplate() {
	return new RestTemplate();
}
```



## 四、Ribbon负载均衡原理

### 负载均衡原理

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211211174139422.png)



### 负载均衡策略（IRule接口）

Ribbon的负载均衡规则是一个叫做`IRule` 的接口来定义的，每一个子接口都是一种规则：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211211174332772.png?w=600)



![](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211174904487.png)

**通过定义IRule实现可以修改负载均衡规则，有两种方式：**

代码方式：在order-service中的OrderApplication类中，定义一个新的IRule：（全局配置）

```java
@Bean
public IRule randomRule() {
  return new RandomRule();
}
```

配置文件方式：在order-service的application.yml文件中，添加新的配置也可以修改规则：（针对某个微服务而言）

```yaml
userservice:
  ribbon:
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule# 负载均衡规则 
```



### 懒加载

Ribbon默认是采用懒加载，即第一次访问时才会去创建LoadBalanceClient，请求时间会很长。

而饥饿加载则会在项目启动时创建，降低第一次访问的耗时，通过下面配置开启饥饿加载：

```yaml
ribbon:
  eager-load:
    enabled: true # 开启饥饿加载
    clients: # 指定饥饿加载的服务名称
      - userservice
```



<br>

### 总结：Ribbon负载均衡规则、自定义、饥饿加载

**Ribbon负载均衡规则**

- 规则接口是IRule
- 默认实现是ZoneAvoidanceRule，根据zone选择服务列表，然后轮询

**负载均衡自定义方式**

- 代码方式：配置灵活，但修改时需要重新打包发布
- 配置方式：直观，方便，无需重新打包发布，但是无法做全局配置

**饥饿加载**

- 开启饥饿加载
- 指定饥饿加载的微服务名称



## 五、nacos注册中心

### 1、认识和安装

Nacos是阿里巴巴的产品，现在是SpringCloud中的一个组件。相比Eureka功能更加丰富，在国内受欢迎程度较高。

![image-20211211182315838](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211182315838.png)



### 2、快速入门

在cloud-demo父工程中添加spring-cloud-alilbaba的管理依赖：

```xml
<!--nacos的管理依赖-->
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-alibaba-dependencies</artifactId>
  <version>2.2.5.RELEASE</version>
  <type>pom</type>
  <scope>import</scope>
</dependency>
```

注释掉order-service和user-service中原有的eureka依赖。
添加nacos的客户端依赖：

```xml
<!-- nacos客户端依赖包 -->
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

修改user-service&order-service中的application.yml文件，注释eureka地址，添加nacos地址：

```yaml
spring:
  cloud:
    nacos:
      server-addr: localhost:8848 # nacos服务地址
```

启动并测试：

![image-20211211191456264](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211191456264.png)

### 3、服务分级存储模型

![image-20211211194746398](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211194746398.png)

**3.1 服务跨集群调用问题**

> 服务调用尽可能选择本地集群的服务，跨集群调用延迟较高
>
> 本地集群不可访问时，再去访问其它集群

**3.2 服务集群属性**

修改application.yml，添加如下内容：

![image-20211211231019257](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211231019257.png)



在Nacos控制台可以看到集群变化：（我们修改user-service集群属性配置，达到下面的效果：）

![image-20211211231239040](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211231239040.png)

**3.3 总结：**

- Nacos服务分级存储模型
  - 一级是服务，例如userservice
  - 二级是集群，例如杭州或上海
  - 三级是实例，例如杭州机房的某台部署了userservice的服务器

<br>

- 如何设置实例的集群属性
  - 修改application.yml文件，添加 `spring.cloud.nacos.discovery.cluster-name `属性即可



**3.4 根据集群负载均衡**

> 我们希望优先选择HZ集群（8001和8002）
>
> 进行如下设置：

![](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211231610325.png)

> 1）修改order-service中的application.yml，设置集群为HZ：
>
> <img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211231905497.png" alt="image-20211211231905497" style="zoom:33%;" />
>
> 2）然后在order-service中设置负载均衡的IRule为**NacosRule**，这个规则**优先会寻找与自己同集群的服务**：
>
> NacosRule负载均衡策略：先本地，然后本地内随机
>
> <img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211231930721.png" alt="image-20211211231930721" style="zoom: 33%;" />

<br>

跨集群访问的警告信息：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211211232440450.png)



 **3.5 根据权重负载均衡**

实际部署中会出现的场景：服务器设备性能有差异，部分实例所在机器性能较好，另一些较差，我们希望性能好的机器承担更多的用户请求

Nacos提供了权重配置来控制访问频率，权重越大则访问频率越高



**实践：**

在Nacos控制台可以设置实例的权重值，首先选中实例后面的编辑按钮

![image-20211211232908311](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211232908311.png)

将权重设置为0.1，测试可以发现8081被访问到的频率大大降低

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211232935847.png" alt="image-20211211232935847" style="zoom: 33%;" />



<br>

**实例的权重控制**

- Nacos控制台可以设置实例的权重值，0~1之间
- 同集群内的多个实例，权重越高被访问的频率越高
- 权重设置为<font color=red>0</font>则完全不会被访问





### 4、环境隔离namespace



#### 如何修改一个服务的命名空间：

**1）在Nacos控制台可以创建namespace，用来隔离不同环境**

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211233943719.png" alt="image-20211211233943719" style="zoom:33%;" />

**2）然后填写一个新的命名空间信息：**

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211234013400.png" alt="image-20211211234013400" style="zoom:33%;" />

**3）保存后会在控制台看到这个命名空间的id：**

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211234038201.png" alt="image-20211211234038201" style="zoom:33%;" />

**4）修改order-service的application.yml，添加namespace：**

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/cloud_order?useSSL=false
    username: root
    password: 1
    driver-class-name: com.mysql.jdbc.Driver
  application:
    name: orderservice
  cloud:
    nacos:
      server-addr: localhost:8848 # nacos服务地址
      discovery:
        cluster-name: SH # 上海
        namespace: 4d6ce343-9e1b-44df-a90f-2cf2b6b3d177 # dev环境   命名空间，填ID
```

**5）重启order-service后，再来查看控制台：**

![image-20211211234615252](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211234615252.png)

**6）此时访问order-service，因为namespace不同，会导致找不到userservice，控制台会报错：**

![image-20211211234632036](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211234632036.png)



#### 总结Nacos环境隔离

- 1）每个namespace都有唯一id
- 2）服务设置namespace时要写id而不是名称
- 3）不同namespace下的服务互相不可见



#### 临时实例和非临时实例

**好好理解**

> 服务注册到Nacos时，可以选择注册为临时或非临时实例，通过下面的配置来设置：
>
> 临时实例宕机时，会从nacos的服务列表中剔除，而非临时实例则不会

```yaml
spring:
  cloud:
    nacos:
      discovery:
        ephemeral: false # 是否是临时实例
```



![image-20211211235219524](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211211235219524.png)



**Nacos与eureka的共同点**

> 都支持服务注册和服务拉取
>
> 都支持服务提供者心跳方式做健康检测

**Nacos与Eureka的区别**

> Nacos支持服务端主动检测提供者状态：临时实例采用心跳模式，非临时实例采用主动检测模式
>
> 临时实例心跳不正常会被剔除，非临时实例则不会被剔除
>
> Nacos支持服务列表变更的消息推送模式，服务列表更新更及时
>
> Nacos集群默认采用AP方式（强调可用性），当集群中存在非临时实例时，采用CP模式；Eureka采用AP方式



# springcloud实用篇二

## 一、Nacos配置管理

### 1、统一配置管理



<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211212093955308.png" alt="image-20211212093955308" style="zoom:33%;" />

#### Nacos实现配置管理

在Nacos中添加配置信息：

![](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211212094350600.png)

在弹出表单中填写配置信息：

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211212094447628.png" alt="" style="zoom:33%;" />

#### 微服务配置拉取

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211212094829855.png)



（1）引入Nacos的配置管理客户端依赖：

```xml
<!--nacos的配置管理依赖-->
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```

（2）在userservice中的resource目录添加一个`bootstrap.yml`文件，这个文件是引导文件，优先级高于application.yml：

```yaml
spring:
  application:
    name: userservice
  profiles:
    active: dev # 环境
  cloud:
    nacos:
      server-addr: localhost:8848 # nacos地址
      config:
        file-extension: yaml # 文件后缀名
```

（3）我们在user-service中将pattern.dateformat这个属性注入到UserController中做测试：

```java
@RestController
@RequestMapping("/user")
public class UserController {
		
  	// 注入nacos中的配置属性
    @Value("${pattern.dateformat}")
    private String dateformat;
		
  	// 编写controller，通过日期格式化器来格式化现在时间并返回
    @GetMapping("now")
    public String now(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(properties.getDateformat()));
    }
}
```



**将配置交给Nacos管理的步骤**

- 在Nacos中添加配置文件

- 在微服务中引入nacos的config依赖

- 在微服务中添加bootstrap.yml，配置nacos地址、当前环境、服务名称、文件后缀名。这些决定了程序启动时去nacos读取哪个文件



### 2、配置热更新

Nacos中的配置文件变更后，微服务无需重启就可以感知。不过需要通过下面两种配置实现：

<br>

方式一：在@Value注入的变量所在类上添加注解**@RefreshScope**

```java
@Slf4j
@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @Value("${pattern.dateformat}")
    private String dateformat;
```

方式二：使用**@ConfigurationProperties**注解（推荐）

```java
@Data
@Component
@ConfigurationProperties(prefix = "pattern")
public class PatternProperties {
    private String dateformat;
    private String envSharedValue;
    private String name;
}
```

> Nacos配置更改后，微服务可以实现热更新，方式：
>
> - 通过@Value注解注入，结合@RefreshScope来刷新
> - 通过@ConfigurationProperties注入，自动刷新
>
> 注意事项：
>
> - 不是所有的配置都适合放到配置中心，维护起来比较麻烦
> - 建议将一些关键参数，需要运行时调整的参数放到nacos配置中心，一般都是自定义配置



### 3、多环境配置共享



**微服务启动时会从nacos读取多个配置文件：**

- [spring.application.name]-[spring.profiles.active].yaml，例如：userservice-dev.yaml，环境配置
- [spring.application.name].yaml，例如：userservice.yaml，默认配置，多环境共享

<br>

无论profile如何变化，[spring.application.name].yaml这个文件一定会加载，因此多环境共享配置可以写入这个文件

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211212131127779.png" alt="image-20211212131127779" style="zoom:50%;" />



**多种配置的优先级：[服务名]-[环境].yaml >[服务名].yaml > 本地配置**

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211212131225553.png" alt="image-20211212131225553" style="zoom:50%;" />







不同微服务之间可以共享配置文件，通过下面的两种方式来指定：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211212131623849.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211212131641085.png)



多种配置的优先级：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220410155723694.png)



```bash
# 微服务默认读取的配置文件：
		[服务名]-[spring.profile.active].yaml，默认配置
		[服务名].yaml，多环境共享
# 不同微服务共享的配置文件：
		通过shared-configs指定
		通过extension-configs指定
# 优先级：
		环境配置 >服务名.yaml > extension-config > extension-configs > shared-configs > 本地配置
```





### 4、搭建Nacos集群



<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211212132653512.png" alt="image-20211212132653512" style="zoom:33%;" />



#### 搭建集群的基本步骤

- 搭建数据库，初始化数据库表结构
- 下载nacos安装包
- 配置nacos
- 启动nacos集群
- nginx反向代理



**1）初始化数据库**

Nacos默认数据存储在内嵌数据库Derby中，不属于生产可用的数据库。

官方推荐的最佳实践是使用带有主从的高可用数据库集群，主从模式的高可用数据库可以参考**传智教育**的后续高手课程。

这里我们以单点的数据库为例来讲解。

首先新建一个数据库，命名为nacos，而后导入下面的SQL：

```sql
CREATE TABLE `config_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) DEFAULT NULL,
  `content` longtext NOT NULL COMMENT 'content',
  `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COMMENT 'source user',
  `src_ip` varchar(50) DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) DEFAULT NULL,
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) DEFAULT NULL,
  `c_use` varchar(64) DEFAULT NULL,
  `effect` varchar(64) DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `c_schema` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info';

/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = config_info_aggr   */
/******************************************/
CREATE TABLE `config_info_aggr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) NOT NULL COMMENT 'datum_id',
  `content` longtext NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) DEFAULT NULL,
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='增加租户字段';


/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = config_info_beta   */
/******************************************/
CREATE TABLE `config_info_beta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
  `content` longtext NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COMMENT 'source user',
  `src_ip` varchar(50) DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_beta';

/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = config_info_tag   */
/******************************************/
CREATE TABLE `config_info_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
  `content` longtext NOT NULL COMMENT 'content',
  `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COMMENT 'source user',
  `src_ip` varchar(50) DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_tag';

/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = config_tags_relation   */
/******************************************/
CREATE TABLE `config_tags_relation` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_tag_relation';

/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = group_capacity   */
/******************************************/
CREATE TABLE `group_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='集群、各Group容量信息表';

/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = his_config_info   */
/******************************************/
CREATE TABLE `his_config_info` (
  `id` bigint(64) unsigned NOT NULL,
  `nid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) NOT NULL,
  `group_id` varchar(128) NOT NULL,
  `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
  `content` longtext NOT NULL,
  `md5` varchar(32) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text,
  `src_ip` varchar(50) DEFAULT NULL,
  `op_type` char(10) DEFAULT NULL,
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='多租户改造';


/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = tenant_capacity   */
/******************************************/
CREATE TABLE `tenant_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户容量信息表';


CREATE TABLE `tenant_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) default '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) default '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tenant_info';

CREATE TABLE `users` (
	`username` varchar(50) NOT NULL PRIMARY KEY,
	`password` varchar(500) NOT NULL,
	`enabled` boolean NOT NULL
);

CREATE TABLE `roles` (
	`username` varchar(50) NOT NULL,
	`role` varchar(50) NOT NULL,
	UNIQUE INDEX `idx_user_role` (`username` ASC, `role` ASC) USING BTREE
);

CREATE TABLE `permissions` (
    `role` varchar(50) NOT NULL,
    `resource` varchar(255) NOT NULL,
    `action` varchar(8) NOT NULL,
    UNIQUE INDEX `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
);

INSERT INTO users (username, password, enabled) VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', TRUE);

INSERT INTO roles (username, role) VALUES ('nacos', 'ROLE_ADMIN');
```



**2）下载nacos**

https://github.com/alibaba/nacos/tags，可以选择任意版本下载。本例中采用1.4.1版本。

**3）配置nacos**

解压 （bin：启动脚本  conf：配置文件）

进入nacos的conf目录，修改配置文件cluster.conf.example，重命名为 `cluster.conf`：

然后添加内容：

```
127.0.0.1:8845
127.0.0.1.8846
127.0.0.1.8847
```

然后修改application.properties文件，添加数据库配置

```properties
spring.datasource.platform=mysql

db.num=1

db.url.0=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
db.user.0=root
db.password.0=123456
```



**4）启动**

将nacos文件夹复制三份，分别命名为：nacos1、nacos2、nacos3

然后分别修改三个文件夹中的application.properties

nacos1:

```properties
server.port=8845
```

nacos2:

```properties
server.port=8846
```

nacos3:

```properties
server.port=8847
```



然后分别启动三个nacos节点：

```
startup.cmd
```



**5）nginx反向代理**

nginx安装包解压

修改conf/nginx.conf文件，配置如下：

```conf
upstream nacos-cluster {
    server 127.0.0.1:8845;
	  server 127.0.0.1:8846;
	  server 127.0.0.1:8847;
}

server {
    listen       80;
    server_name  localhost;

    location /nacos {
        proxy_pass http://nacos-cluster;
    }
}
```



## 二、Feign远程调用（HTTP客户端Feign）

```java
// vaule和name 其实是一个属性
public @interface FeignClient {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
  	......
}

@FeignClient(name="${feign.name.element2:element-server2}")
public interface IBaseDataFeignClient extends IBaseDataFeignController {
}
```



### 1、Feign替代RestTemplate

#### 1.1 RestTemplate方式调用存在的问题

```java
String url = "http://userservice/user/" + order.getUserId();
User user = restTemplate.getForObject(url, User.class);
```

代码可读性差，编程体验不统一；参数复杂URL难以维护



#### 1.2 Feign的介绍

官方地址：https://github.com/OpenFeign/feign

<br>

Feign是一个声明式的http客户端，其作用就是帮助我们优雅的实现http请求的发送，解决上面提到的问题。

> 理解声明式：如spring的声明式事务，对谁加事务规则定义好，spring来实现事务。
>
> feign接口定义：发http请求的信息写出来就可以

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211213101049566.png" alt="image-20211213101049566" style="zoom:33%;" />



#### 1.3 定义和使用Feign客户端

引入依赖

```xml
<!--feign客户端依赖-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

在order-service的启动类添加注解开启Feign的功能：`@EnableFeignClients`

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211213110316047.png" alt="image-20211213110316047" style="zoom: 33%;" />

编写Feign客户端

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211213111042682.png)



### 2、自定义配置

#### 配置Feign日志有两种方式：

**方式一：配置文件方式**

![](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211213225807846.png)

<br>

**方式二：java代码方式，需要先声明一个Bean**

![](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211213225944483.png)



### 3、Figen使用优化（性能调优）

**Feign底层的客户端实现：**

- URLConnection：默认实现，不支持连接池
- Apache HttpClient ：支持连接池
- OKHttp：支持连接池



**因此优化Feign的性能主要包括：**

- 使用连接池代替默认的URLConnection
- 日志级别，最好用 `basic` 或 `none`

<br>

#### 连接池配置（以HttpClient为例）

引入feign-httpClient依赖

```xml
<!--引入HttpClient依赖-->
<dependency>
  <groupId>io.github.openfeign</groupId>
  <artifactId>feign-httpclient</artifactId>
</dependency>
```

配置文件开启httpClient功能，设置连接池参数

```yaml
feign:
  client:
    config:
      default: # default全局的配置
        loggerLevel: BASIC # 日志级别，BASIC就是基本的请求和响应信息 
  httpclient:
    enabled: true # 支持HttpClient的开关
    max-connections: 200 # 最大连接数
    max-connections-per-route: 50 # 单个路径的最大连接数
```



> **Feign的优化：**
>
> **1）日志级别尽量用basic**
>
> **2）使用HttpClient或OKHttp代替URLConnection**
>
> **引入feign-httpClient依赖**
>
> **配置文件开启httpClient功能，设置连接池参数**



### 4、最佳实践（我做过的内容）

#### Feign的最佳实践

> 所谓继承：让controller和FeignClient继承同一接口
>
> 所谓抽取：将FeignClient、POJO、Feign的默认配置都定义到一个项目中，供所有消费者使用



<br>

方式一（继承）：给消费者的FeignClient和提供者的controller定义统一的父接口作为标准。



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211213232445655.png)



方式二（抽取）：将FeignClient抽取为独立模块，并且把接口有关的POJO、默认的Feign配置都放到这个模块中，提供给所有消费者使用

![image-20211213232535909](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211213232535909.png)



#### 实现Feign最佳实践

**实现最佳实践方式二的步骤如下：**

（1）首先创建一个module，命名为feign-api，然后引入feign的starter依赖

（2）将order-service中编写的UserClient、User、DefaultFeignConfiguration都复制到feign-api项目中

（3）在order-service中引入feign-api的依赖

（4）修改order-service中的所有与上述三个组件有关的import部分，改成导入feign-api中的包

（5）重启测试

<br>

**当定义的FeignClient不在SpringBootApplication的扫描包范围时，这些FeignClient无法使用。有两种方式解决：**

![](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211213233439656.png)



## 三、Gateway服务网关

### 1、为什么需要网关

![](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211213234142351.png)

**在SpringCloud中网关的实现包括两种：zuul和gateway**



> Zuul是基于Servlet的实现，属于阻塞式编程。
>
> 而**SpringCloudGateway**则是基于Spring5中提供的WebFlux，属于**响应式编程**的实现，具备更好的性能。



### 2、gateway快速入门

#### 搭建服务网关

1）创建新的module，引入SpringCloudGateway的依赖和nacos的服务发现依赖：

```xml
<!--nacos服务注册发现依赖-->
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<!--网关gateway依赖-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

2）编写路由配置及nacos地址

```yaml
server:
  port: 10010
spring:
  application:
    name: gateway
  cloud:
    nacos:
      server-addr: nacos:8848 # nacos地址
    gateway:
      routes: # 网关路由配置
        - id: user-service # 路由id，自定义，必须唯一
          uri: lb://userservice # 路由的目标地址  lb就是负载均衡，后面跟服务名称
          predicates: # 路由断言，也就是判断请求是否符合路由规则的条件
            - Path=/user/** # 路径断言，这个是按照路径匹配，判断路径是否是以/user开头，如果是则符合
        - id: order-service
          uri: lb://orderservice
          predicates:
            - Path=/order/**
```



<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211214000039112.png" alt="image-20211214000039112" style="zoom: 43%;" />

<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211214000341355.png)

```bash
# 网关搭建步骤：
创建项目，引入nacos服务发现和gateway依赖
配置application.yml，包括服务基本信息、nacos地址、路由
# 路由配置包括：
路由id：路由的唯一标示
路由目标（uri）：路由的目标地址，http代表固定地址，lb代表根据服务名负载均衡
路由断言（predicates）：判断路由的规则，
路由过滤器（filters）：对请求或响应做处理
```



### 3、断言工厂

**predicates：路由断言，判断请求是否符合要求，符合则转发到路由目的地**

> 我们在配置文件中写的断言规则只是字符串，这些字符串会被Predicate Factory读取并处理，转变为路由判断的条件
>
> 例如Path=/user/**是按照路径匹配，这个规则是由
>
> org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory类来处理的
>
> 像这样的断言工厂在SpringCloudGateway还有十几个



**Spring提供了11种基本的Predicate工厂：要用哪个去官方查一查文档，看怎么写**

https://docs.spring.io/spring-cloud-gateway/docs/3.1.1-SNAPSHOT/reference/html/#gateway-request-predicates-factories

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211214001242768.png)

> PredicateFactory的作用是什么？
>
> - 读取用户定义的断言条件，对请求做出判断
>
> Path=/user/**是什么含义？
>
> - 路径是以/user开头的就认为是符合的	

<br>

这一块参考官网进行配置就可以：

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: after_route
        uri: https://example.org
        predicates:
        - After=2017-01-20T17:42:47.789-07:00[America/Denver]
```



### 4、全局过滤器

#### 路由过滤器 GatewayFilter

**GatewayFilter是网关中提供的一种过滤器，可以对进入网关的请求和微服务返回的响应做处理：**

![](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214002520861.png)

<br>

#### 过滤器工厂 GatewayFilterFactory

**Spring提供了31种不同的路由过滤器工厂。例如：**

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214002728752.png" alt="image-20211214002728752" style="zoom: 50%;" />



#### 案例：给所有进入userservice的请求添加一个请求头：Truth=itcast is freaking awesome!

实现方式：在gateway中修改application.yml文件，给userservice的路由添加过滤器：

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214003315444.png" alt="image-20211214003315444" style="zoom: 33%;" />



**默认过滤器：如果要对所有的路由都生效，则可以将过滤器工厂写到default下。格式如下：**

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211214003458750.png)



> **过滤器的作用是什么？**
>
> - 对路由的请求或响应做加工处理，比如添加请求头
>
> - 配置在路由下的过滤器只对当前路由的请求生效
>
> **default Filters的作用是什么？**
>
> - 对所有路由都生效的过滤器



#### 全局过滤器 GlobalFilter（实现 `GlobalFilter`接口）

> 全局过滤器的作用也是处理一切进入网关的请求和微服务响应，与GatewayFilter的作用一样。
>
> 区别在于GatewayFilter通过配置定义，处理逻辑是固定的。而GlobalFilter的逻辑需要自己写代码实现。
>
> 定义方式是实现 `GlobalFilter`接口。



![](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214005424921.png)



#### 案例：定义全局过滤器，拦截并判断用户身份

> 需求：定义全局过滤器，拦截请求，判断请求的参数是否满足下面条件：
>
> - 参数中是否有authorization，
>
> - authorization参数值是否为admin
>
> 如果同时满足则放行，否则拦截



自定义类，实现GlobalFilter接口，添加@Order注解：

```java
package cn.itcast.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// @Order(-1)
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> params = request.getQueryParams();
        // 2.获取参数中的 authorization 参数
        String auth = params.getFirst("authorization");
        // 3.判断参数值是否等于 admin
        if ("admin".equals(auth)) {
            // 4.是，放行
            return chain.filter(exchange);
        }
        // 5.否，拦截
        // 5.1.设置状态码
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        // 5.2.拦截请求
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
```

<br>

![](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214010241143.png)



> **全局过滤器的作用是什么？**
>
> **对所有路由都生效的过滤器，并且可以自定义处理逻辑**
>
> **实现全局过滤器的步骤？**
>
> - 实现GlobalFilter接口
> - 添加@Order注解或实现Ordered接口
> - 编写处理逻辑



#### 过滤器执行顺序

请求进入网关会碰到三类过滤器：当前路由的过滤器、DefaultFilter、GlobalFilter

**请求路由后，会将当前路由过滤器和DefaultFilter、GlobalFilter，合并到一个过滤器链（集合）中（设计模式之适配器模式），排序后依次执行每个过滤器**

![image-20211214010849607](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214010849607.png)



每一个过滤器都必须指定一个int类型的order值，**order值越小，优先级越高，执行顺序越靠前。**

<br>

GlobalFilter通过实现Ordered接口，或者添加@Order注解来指定order值，由我们自己指定

路由过滤器和defaultFilter的order由Spring指定，默认是按照声明顺序从1递增。

当过滤器的order值一样时，会按照 **defaultFilter > 路由过滤器 > GlobalFilter**的顺序执行。



> **可以参考下面几个类的源码来查看：**

![image-20211214011320668](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214011320668.png)

> 路由过滤器、defaultFilter、全局过滤器的执行顺序？
>
> - order值越小，优先级越高
>
> - 当order值一样时，顺序是defaultFilter最先，然后是局部的路由过滤器，最后是全局过滤器



### 5、跨域问题处理

跨域：域名不一致就是跨域，主要包括：

- 域名不同： www.taobao.com 和 www.taobao.org 和 www.jd.com 和 miaosha.jd.com

- 域名相同，端口不同：localhost:8080和localhost8081

<br>

跨域问题：浏览器禁止请求的发起者与服务端发生跨域ajax请求，请求被浏览器拦截的问题

解决方案：CORS

<br>

网关处理跨域采用的同样是CORS方案，并且只需要简单配置即可实现：

![image-20211214012404184](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214012404184.png)









