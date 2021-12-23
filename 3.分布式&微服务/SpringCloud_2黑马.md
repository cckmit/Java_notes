

# 黑马程序员

## 服务框架学习路线

**完整微服务技术栈：微服务技术 + 持续集成**

> 微服务并不是等于springcloud技术
>
> 消息队列：异步通信大大提高服务的并发：秒杀等高并发场景利用
>
> 系统监控链路追踪：实时监控每一个服务节点的运行状态、CPU负载、内存的占用等等情况，一旦出现任何的问题，可以直接定位到某一具体的方法栈信息，快速定位异常所在。
>
> 自动化部署：工具Jenkins，Docker打包形成镜像

![image-20211211113438830](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211113438830.png)



**需要学习的微服务知识**

![微服务1](https://gitee.com/code0002/blog-img/raw/master/img/微服务1.png)



![image-20211211120632882](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211120632882.png)



**学习路径**

![image-20211211120655865](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211120655865.png)

**分层次教学**

![image-20211211122543190](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211122543190.png)

**知识层次**

![学习层次](https://gitee.com/code0002/blog-img/raw/master/img/学习层次.png)



## springcloud实用篇一

> 注册中心、负载均衡器

### 一、认识微服务

#### 服务框架演变

**1、单体架构：将业务的所有功能集中在一个项目中开发，打成一个包部署。**
**2、分布式架构：根据业务功能对系统进行拆分，每个业务模块作为独立项目开发，称为一个服务。**
**3、微服务是一种经过良好架构设计的分布式架构方案，微服务架构特征：**

> 单一职责：微服务拆分粒度更小，每一个服务都对应唯一的业务能力，做到单一职责，避免重复业务开发
> 面向服务：微服务对外暴露业务接口
> 自治：团队独立、技术独立、数据独立、部署独立
> 隔离性强：服务调用做好隔离、容错、降级，避免出现级联问题

#### 微服务技术对比

微服务这种方案需要技术框架来落地，全球的互联网公司都在积极尝试自己的微服务落地技术。在国内最知名的就是SpringCloud和阿里巴巴的Dubbo。

![image-20211211125555526](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211125555526.png)





#### SpringCloud<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211211130153930.png" alt="image-20211211130153930" style="zoom:5%;" />

SpringCloud是目前国内使用最广泛的微服务框架。官网地址：https://spring.io/projects/spring-cloud。
SpringCloud集成了各种微服务功能组件，并基于SpringBoot实现了这些**组件的自动装配**，从而提供了良好的开箱即用体验：

![image-20211211130417169](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211130417169.png)

**SpringCloud与SpringBoot的版本兼容关系如下：**

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211211130745877.png" alt="image-20211211130745877" style="zoom: 33%;" />



### 二、微服务拆分案例

#### 服务拆分

父工程：定义版本

> **注意事项：**
>
> 单一职责：不同微服务，不要重复开发相同业务
> 数据独立：不要访问其它微服务的数据库
> 面向服务：将自己的业务暴露为接口，供其它微服务调用

#### 案例cloud-demo

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



![image-20211211162822962](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211162822962.png)

基于RestTemplate发起的http请求实现远程调用
http请求做远程调用是与语言无关的调用，只要知道对方的ip、端口、接口路径、请求参数即可。

#### 概念：提供者与消费者

服务提供者：一次业务中，被其它微服务调用的服务。（提供接口给其它微服务）
服务消费者：一次业务中，调用其它微服务的服务。（调用其它微服务提供的接口）

![image-20211211164700331](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211164700331.png)



服务A调用服务B，服务B调用服务C，那么服务B是什么角色？

一个服务既可以是提供者，也可以是消费者。

### 三、erreka注册中心

#### 远程调用的问题（一堆问题）

服务消费者该如何获取服务提供者的地址信息？

>服务提供者启动时向eureka注册自己的信息
>eureka保存这些信息
>消费者根据服务名称向eureka拉取提供者信息

如果有多个服务提供者，消费者该如何选择？

>服务消费者利用负载均衡算法，从服务列表中挑选一个

消费者如何得知服务提供者的健康状态？

>服务提供者会每隔30秒向EurekaServer发送心跳请求，报告健康状态
>eureka会更新记录服务列表信息，心跳不正常会被剔除
>消费者就可以拉取到最新的信息



#### eureka原理

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211211165752560.png" alt="image-20211211165752560" style="zoom: 40%;" />



#### 搭建EurekaServer注册中心

创建项目，引入spring-cloud-starter-netflix-eureka-server的依赖

```xml
<!--eureka服务端-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

编写启动类，添加@EnableEurekaServer注解

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



#### 服务注册

引入spring-cloud-starter-netflix-eureka-client的依赖

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

![image-20211211172410982](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211172410982.png)

#### 服务发现

在order-service完成服务拉取

服务拉取是基于服务名称获取服务列表，然后再对服务列表做负载均衡

修改OrderService的代码，修改访问的url路径，用服务名代替ip、端口：

```java
String url = "http://userservice/user/" + order.getUserId();
```

在order-service项目的启动类OrderApplication中的RestTemplate添加负载均衡注解：@LoadBalanced

```java
@Bean
@LoadBalanced
public RestTemplate restTemplate() {
	return new RestTemplate();
}
```



### 四、Ribbon负载均衡原理

#### 负载均衡原理

![image-20211211174139422](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211174139422.png)



#### 负载均衡策略

Ribbon的负载均衡规则是一个叫做IRule的接口来定义的，每一个子接口都是一种规则：

![image-20211211174332772](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211174332772.png)



![image-20211211174904487](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211174904487.png)

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



#### 懒加载

Ribbon默认是采用懒加载，即第一次访问时才会去创建LoadBalanceClient，请求时间会很长。
而饥饿加载则会在项目启动时创建，降低第一次访问的耗时，通过下面配置开启饥饿加载：

```yaml
ribbon:
  eager-load:
    enabled: true # 开启饥饿加载
    clients: # 指定饥饿加载的服务名称
      - userservice
```

> Ribbon负载均衡规则
> 		规则接口是IRule
> 		默认实现是ZoneAvoidanceRule，根据zone选择服务列表，然后轮询
> 负载均衡自定义方式
> 		代码方式：配置灵活，但修改时需要重新打包发布
> 		配置方式：直观，方便，无需重新打包发布，但是无法做全局配置
> 饥饿加载
> 		开启饥饿加载
> 		指定饥饿加载的微服务名称

### 五、nacos注册中心

#### 1、认识和安装

Nacos是阿里巴巴的产品，现在是SpringCloud中的一个组件。相比Eureka功能更加丰富，在国内受欢迎程度较高。

![image-20211211182315838](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211182315838.png)



#### 2、快速入门

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

![image-20211211191456264](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211191456264.png)

#### 3、服务分级存储模型

![image-20211211194746398](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211194746398.png)

**3.1 服务跨集群调用问题**

> 服务调用尽可能选择本地集群的服务，跨集群调用延迟较高
> 本地集群不可访问时，再去访问其它集群

**3.2 服务集群属性**

修改application.yml，添加如下内容：

![image-20211211231019257](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211231019257.png)



在Nacos控制台可以看到集群变化：（我们修改user-service集群属性配置，达到下面的效果：）

![image-20211211231239040](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211231239040.png)

**3.3 总结：**

Nacos服务分级存储模型
		一级是服务，例如userservice
		二级是集群，例如杭州或上海
		三级是实例，例如杭州机房的某台部署了userservice的服务器
如何设置实例的集群属性
		修改application.yml文件，添加spring.cloud.nacos.discovery.cluster-name属性即可



**3.4 根据集群负载均衡**

> 我们希望优先选择HZ集群（8001和8002）
>
> 进行如下设置：

![image-20211211231610325](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211231610325.png)

> 1）修改order-service中的application.yml，设置集群为HZ：
>
> <img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211211231905497.png" alt="image-20211211231905497" style="zoom:33%;" />
>
> 2）然后在order-service中设置负载均衡的IRule为**NacosRule**，这个规则**优先会寻找与自己同集群的服务**：
>
> NacosRule负载均衡策略：先本地，然后本地内随机
>
> <img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211211231930721.png" alt="image-20211211231930721" style="zoom: 33%;" />



跨集群访问的警告信息：

![image-20211211232440450](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211232440450.png)



 **3.5 根据权重负载均衡**

实际部署中会出现的场景：服务器设备性能有差异，部分实例所在机器性能较好，另一些较差，我们希望性能好的机器承担更多的用户请求

Nacos提供了权重配置来控制访问频率，权重越大则访问频率越高



**实践：**

在Nacos控制台可以设置实例的权重值，首先选中实例后面的编辑按钮

![image-20211211232908311](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211232908311.png)

将权重设置为0.1，测试可以发现8081被访问到的频率大大降低

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211211232935847.png" alt="image-20211211232935847" style="zoom: 33%;" />



> 实例的权重控制
> 		Nacos控制台可以设置实例的权重值，0~1之间
> 		同集群内的多个实例，权重越高被访问的频率越高
> 		权重设置为<font color=red>0</font>则完全不会被访问

#### 4、环境隔离namespace



##### 如何修改一个服务的命名空间：

**1）在Nacos控制台可以创建namespace，用来隔离不同环境**

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211211233943719.png" alt="image-20211211233943719" style="zoom:33%;" />

**2）然后填写一个新的命名空间信息：**

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211211234013400.png" alt="image-20211211234013400" style="zoom:33%;" />

**3）保存后会在控制台看到这个命名空间的id：**

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211211234038201.png" alt="image-20211211234038201" style="zoom:33%;" />

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

![image-20211211234615252](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211234615252.png)

**6）此时访问order-service，因为namespace不同，会导致找不到userservice，控制台会报错：**

![image-20211211234632036](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211234632036.png)



##### 总结

Nacos环境隔离
1）每个namespace都有唯一id
2）服务设置namespace时要写id而不是名称
3）不同namespace下的服务互相不可见



##### 临时实例和非临时实例

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



![image-20211211235219524](https://gitee.com/code0002/blog-img/raw/master/img/image-20211211235219524.png)



**Nacos与eureka的共同点**

> 都支持服务注册和服务拉取
> 都支持服务提供者心跳方式做健康检测

**Nacos与Eureka的区别**

> Nacos支持服务端主动检测提供者状态：临时实例采用心跳模式，非临时实例采用主动检测模式
> 临时实例心跳不正常会被剔除，非临时实例则不会被剔除
> Nacos支持服务列表变更的消息推送模式，服务列表更新更及时
> Nacos集群默认采用AP方式，当集群中存在非临时实例时，采用CP模式；Eureka采用AP方式



## springcloud实用篇二

### 一、Nacos配置管理

#### 1、统一配置管理



<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211212093955308.png" alt="image-20211212093955308" style="zoom:33%;" />

##### Nacos实现配置管理

在Nacos中添加配置信息：

![image-20211212094350600](https://gitee.com/code0002/blog-img/raw/master/img/image-20211212094350600.png)

在弹出表单中填写配置信息：

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211212094447628.png" alt="image-20211212094447628" style="zoom:33%;" />

##### 微服务配置拉取

![image-20211212094829855](https://gitee.com/code0002/blog-img/raw/master/img/image-20211212094829855.png)



（1）引入Nacos的配置管理客户端依赖：

```xml
<!--nacos的配置管理依赖-->
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```

（2）在userservice中的resource目录添加一个bootstrap.yml文件，这个文件是引导文件，优先级高于application.yml：

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
在Nacos中添加配置文件
在微服务中引入nacos的config依赖
在微服务中添加bootstrap.yml，配置nacos地址、当前环境、服务名称、文件后缀名。这些决定了程序启动时去nacos读取哪个文件



#### 2、配置热更新

Nacos中的配置文件变更后，微服务无需重启就可以感知。不过需要通过下面两种配置实现：

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
> 		通过@Value注解注入，结合@RefreshScope来刷新
> 		通过@ConfigurationProperties注入，自动刷新
> 注意事项：
> 		不是所有的配置都适合放到配置中心，维护起来比较麻烦
> 		建议将一些关键参数，需要运行时调整的参数放到nacos配置中心，一般都是自定义配置

#### 3、配置共享

多环境配置共享

**微服务启动时会从nacos读取多个配置文件：**
		[spring.application.name]-[spring.profiles.active].yaml，例如：userservice-dev.yaml
		[spring.application.name].yaml，例如：userservice.yaml
无论profile如何变化，[spring.application.name].yaml这个文件一定会加载，因此多环境共享配置可以写入这个文件

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211212131127779.png" alt="image-20211212131127779" style="zoom:50%;" />



**多种配置的优先级：**

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211212131225553.png" alt="image-20211212131225553" style="zoom:50%;" />



> 微服务会从nacos读取的配置文件：
> [服务名]-[spring.profile.active].yaml，环境配置
> [服务名].yaml，默认配置，多环境共享
> 优先级：
> [服务名]-[环境].yaml >[服务名].yaml > 本地配置



不同微服务之间可以共享配置文件，通过下面的两种方式来指定：

![image-20211212131623849](https://gitee.com/code0002/blog-img/raw/master/img/image-20211212131623849.png)



![image-20211212131641085](https://gitee.com/code0002/blog-img/raw/master/img/image-20211212131641085.png)



![image-20211212131834352](https://gitee.com/code0002/blog-img/raw/master/img/image-20211212131834352.png)



> 微服务默认读取的配置文件：
> 		[服务名]-[spring.profile.active].yaml，默认配置
> 		[服务名].yaml，多环境共享
> 不同微服务共享的配置文件：
> 		通过shared-configs指定
> 		通过extension-configs指定
> 优先级：
> 		环境配置 >服务名.yaml > extension-config > extension-configs > shared-configs > 本地配置



#### 4、搭建Nacos集群



<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211212132653512.png" alt="image-20211212132653512" style="zoom:33%;" />



##### 搭建集群的基本步骤：

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

解压 					bin：启动脚本  conf：配置文件

进入nacos的conf目录，修改配置文件cluster.conf.example，重命名为cluster.conf：

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
db.password.0=123
```



**4）启动**

将nacos文件夹复制三份，分别命名为：nacos1、nacos2、nacos3

然后分别修改三个文件夹中的application.properties，

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

```nginx
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



### 二、Feign远程调用（HTTP客户端Feign）

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



#### 1、Feign替代RestTemplate

##### 1.1 RestTemplate方式调用存在的问题

```java
String url = "http://userservice/user/" + order.getUserId();
User user = restTemplate.getForObject(url, User.class);
```

代码可读性差，编程体验不统一；参数复杂URL难以维护



##### 1.2 Feign的介绍

官方地址：https://github.com/OpenFeign/feign
Feign是一个声明式的http客户端，其作用就是帮助我们优雅的实现http请求的发送，解决上面提到的问题。

> 理解声明式：如spring的声明式事务，对谁加事务规则定义好，spring来实现事务。
>
> feign接口定义：发http请求的信息写出来就可以

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211213101049566.png" alt="image-20211213101049566" style="zoom:33%;" />



##### 1.3 定义和使用Feign客户端

引入依赖

```xml
<!--feign客户端依赖-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

在order-service的启动类添加注解开启Feign的功能：@EnableFeignClients

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211213110316047.png" alt="image-20211213110316047" style="zoom: 33%;" />

编写Feign客户端

![image-20211213111042682](https://gitee.com/code0002/blog-img/raw/master/img/image-20211213111042682.png)



> **Feign的使用步骤：**
> 引入依赖
> 添加@EnableFeignClients注解
> 编写FeignClient接口
> 使用FeignClient中定义的方法代替RestTemplate



#### 2、自定义配置

##### 配置Feign日志有两种方式：

**方式一：配置文件方式**

![image-20211213225807846](https://gitee.com/code0002/blog-img/raw/master/img/image-20211213225807846.png)

**方式二：java代码方式，需要先声明一个Bean：**

![image-20211213225944483](https://gitee.com/code0002/blog-img/raw/master/img/image-20211213225944483.png)



#### 3、Figen使用优化（性能调优）

**Feign底层的客户端实现：**
		URLConnection：默认实现，不支持连接池
		Apache HttpClient ：支持连接池
		OKHttp：支持连接池

**因此优化Feign的性能主要包括：**
（1）使用连接池代替默认的URLConnection
（2）日志级别，最好用`basic`或`none`



##### 连接池配置

Feign添加HttpClient的支持：

引入依赖：

```xml
<!--引入HttpClient依赖-->
<dependency>
  <groupId>io.github.openfeign</groupId>
  <artifactId>feign-httpclient</artifactId>
</dependency>
```

配置连接池：

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
> **1）日志级别尽量用basic**
> **2）使用HttpClient或OKHttp代替URLConnection**
> 		    **引入feign-httpClient依赖**
> 		    **配置文件开启httpClient功能，设置连接池参数**



#### 4、最佳实践

##### Feign的最佳实践

> 所谓继承：让controller和FeignClient继承同一接口
> 所谓抽取：将FeignClient、POJO、Feign的默认配置都定义到一个项目中，供所有消费者使用

方式一（继承）：给消费者的FeignClient和提供者的controller定义统一的父接口作为标准。

![image-20211213232445655](https://gitee.com/code0002/blog-img/raw/master/img/image-20211213232445655.png)



方式二（抽取）：将FeignClient抽取为独立模块，并且把接口有关的POJO、默认的Feign配置都放到这个模块中，提供给所有消费者使用

![image-20211213232535909](https://gitee.com/code0002/blog-img/raw/master/img/image-20211213232535909.png)



##### 实现Feign最佳实践

**实现最佳实践方式二的步骤如下：**
（1）首先创建一个module，命名为feign-api，然后引入feign的starter依赖
（2）将order-service中编写的UserClient、User、DefaultFeignConfiguration都复制到feign-api项目中
（3）在order-service中引入feign-api的依赖
（4）修改order-service中的所有与上述三个组件有关的import部分，改成导入feign-api中的包
（5）重启测试



**当定义的FeignClient不在SpringBootApplication的扫描包范围时，这些FeignClient无法使用。有两种方式解决：**

![image-20211213233439656](https://gitee.com/code0002/blog-img/raw/master/img/image-20211213233439656.png)



### 三、Gateway服务网关

#### 1、为什么需要网关

![image-20211213234142351](https://gitee.com/code0002/blog-img/raw/master/img/image-20211213234142351.png)

**在SpringCloud中网关的实现包括两种：**
		**gateway**
		**zuul**

> Zuul是基于Servlet的实现，属于阻塞式编程。而**SpringCloudGateway**则是基于Spring5中提供的WebFlux，属于**响应式编程**的实现，具备更好的性能。



#### 2、gateway快速入门

##### 搭建服务网关

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



<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211214000039112.png" alt="image-20211214000039112" style="zoom: 43%;" />



![image-20211214000341355](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214000341355.png)

> 网关搭建步骤：
> 		创建项目，引入nacos服务发现和gateway依赖
> 		配置application.yml，包括服务基本信息、nacos地址、路由
> 路由配置包括：
> 		路由id：路由的唯一标示
> 		路由目标（uri）：路由的目标地址，http代表固定地址，lb代表根据服务名负载均衡
> 		路由断言（predicates）：判断路由的规则，
> 		路由过滤器（filters）：对请求或响应做处理



#### 3、断言工厂

**predicates：路由断言，判断请求是否符合要求，符合则转发到路由目的地**

> 我们在配置文件中写的断言规则只是字符串，这些字符串会被Predicate Factory读取并处理，转变为路由判断的条件
> 例如Path=/user/**是按照路径匹配，这个规则是由org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory类来处理的
> 像这样的断言工厂在SpringCloudGateway还有十几个



**Spring提供了11种基本的Predicate工厂：要用哪个去官方查一查文档，看怎么写**

https://docs.spring.io/spring-cloud-gateway/docs/3.1.1-SNAPSHOT/reference/html/#gateway-request-predicates-factories

![image-20211214001242768](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214001242768.png)

> PredicateFactory的作用是什么？
> 		读取用户定义的断言条件，对请求做出判断
> Path=/user/**是什么含义？
> 		路径是以/user开头的就认为是符合的	



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

#### 4、全局过滤器

##### 路由过滤器 GatewayFilter

**GatewayFilter是网关中提供的一种过滤器，可以对进入网关的请求和微服务返回的响应做处理：**

![image-20211214002520861](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214002520861.png)

##### 过滤器工厂 GatewayFilterFactory

**Spring提供了31种不同的路由过滤器工厂。例如：**

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211214002728752.png" alt="image-20211214002728752" style="zoom: 50%;" />



##### 案例：给所有进入userservice的请求添加一个请求头：Truth=itcast is freaking awesome!

实现方式：在gateway中修改application.yml文件，给userservice的路由添加过滤器：

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211214003315444.png" alt="image-20211214003315444" style="zoom: 33%;" />



**默认过滤器：如果要对所有的路由都生效，则可以将过滤器工厂写到default下。格式如下：**

<img src="/Users/cat/Library/Application%2520Support/typora-user-images/image-20211214003458750.png" alt="image-20211214003458750" style="zoom:50%;" />



> **过滤器的作用是什么？**
> 		**对路由的请求或响应做加工处理，比如添加请求头**
> 		**配置在路由下的过滤器只对当前路由的请求生效**
> **defaultFilters的作用是什么？**
> 		**对所有路由都生效的过滤器**



##### 全局过滤器 GlobalFilter

> 全局过滤器的作用也是处理一切进入网关的请求和微服务响应，与GatewayFilter的作用一样。
> 区别在于GatewayFilter通过配置定义，处理逻辑是固定的。而GlobalFilter的逻辑需要自己写代码实现。
> 定义方式是实现GlobalFilter接口。

![image-20211214005424921](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214005424921.png)



##### 案例：定义全局过滤器，拦截并判断用户身份

> 需求：定义全局过滤器，拦截请求，判断请求的参数是否满足下面条件：
> 		参数中是否有authorization，
> 		authorization参数值是否为admin
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

![image-20211214010241143](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214010241143.png)



> **全局过滤器的作用是什么？**
> 		**对所有路由都生效的过滤器，并且可以自定义处理逻辑**
> **实现全局过滤器的步骤？**
> 		**实现GlobalFilter接口**
> 		**添加@Order注解或实现Ordered接口**
> 		**编写处理逻辑**



##### 过滤器执行顺序

请求进入网关会碰到三类过滤器：当前路由的过滤器、DefaultFilter、GlobalFilter
请求路由后，会将当前路由过滤器和DefaultFilter、GlobalFilter，合并到一个过滤器链（集合）中（设计模式之适配器模式），排序后依次执行每个过滤器

![image-20211214010849607](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214010849607.png)



每一个过滤器都必须指定一个int类型的order值，**order值越小，优先级越高，执行顺序越靠前。**
GlobalFilter通过实现Ordered接口，或者添加@Order注解来指定order值，由我们自己指定
路由过滤器和defaultFilter的order由Spring指定，默认是按照声明顺序从1递增。
当过滤器的order值一样时，会按照 **defaultFilter > 路由过滤器 > GlobalFilter**的顺序执行。



> **可以参考下面几个类的源码来查看：**

![image-20211214011320668](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214011320668.png)

> 路由过滤器、defaultFilter、全局过滤器的执行顺序？
> 		order值越小，优先级越高
> 		当order值一样时，顺序是defaultFilter最先，然后是局部的路由过滤器，最后是全局过滤器



#### 5、跨域问题处理

跨域：域名不一致就是跨域，主要包括：
		域名不同： www.taobao.com 和 www.taobao.org 和 www.jd.com 和 miaosha.jd.com
		域名相同，端口不同：localhost:8080和localhost8081
跨域问题：浏览器禁止请求的发起者与服务端发生跨域ajax请求，请求被浏览器拦截的问题
解决方案：CORS



网关处理跨域采用的同样是CORS方案，并且只需要简单配置即可实现：

![image-20211214012404184](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214012404184.png)



## Docker



## 

```sh
# 概念和命令总结一下
镜像：将应⽤程序及其依赖、环境、配置打包在⼀起
容器：镜像运⾏起来就是容器，⼀个镜像可以运⾏多个容器
Docker结构：CS架构。服务端：接收命令或远程请求，操作镜像或容器。客户端：发送命令或者请求到Docker服务端。
数据卷：容器数据管理
数据卷挂载：基于数据卷，基于目录直接挂载
DockerFile自定义镜像：docker build -t javaweb:2.0 .
Docker-Compose：快速部署分布式应用，无需一个个微服务去构建镜像和部署。
镜像仓库：使⽤DockerCompose部署带有图象界⾯的DockerRegistry
重新tar本地镜像：docker tar nginx:latest 172.16.126.5:8080/nginx:1.0
推送镜像：docker push 172.16.126.5:8080/nginx:1.0
拉取镜像：docker pull 172.16.126.5:8080/nginx:1.0

# 启停docker
systemctl start docker # 启动docker服务
systemctl stop docker # 停⽌docker服务
systemctl restart docker # 重启docker服务
docker -v # 查看版本

# 镜像操作命令
# 帮助文档
docker --help
docker images --help
docker pull nginx # 拉取镜像（不指定版本就是最新版本）
docker images  # 查看拉取到的镜像
docker save # 导出镜像到磁盘
docker load # 加载镜像
docker rmi # 删除镜像

# 容器操作命令
# 去docker hub查看Nginx的容器运⾏命令
docker run --name containerName -p 80:80 -d nginx # 创建并运⾏⼀个Nginx容器
docker logs -f mn # 持续日志输出
docker ps # 查看容器状态
docker exec -it mn bash # 进入容器  exec命令可以进⼊容器修改⽂件，但是在容器内修改⽂件是不推荐的
docker run --name redis -p 6379:6379 -d redis redis-server --appendonly yes # 创建并运⾏⼀个redis容器，并且⽀持数据持久化

# 数据卷（容器数据管理）
docker volume create html # 创建数据卷
docker volume ls # 查看所有数据卷
docker volume inspect html # 查看数据卷详细信息卷（包括所在宿主机的位置）
docker volume prune # 移除未使用的数据卷
# 挂载文件或目录到容器中：docker run的命令中通过 -v 参数
# 方式一：基于数据卷
docker run --name mn -v html /usr/share/nginx/html -p 80:80 -d nginx # 创建容器并挂载数据卷到容器内的html目录
# 方式二：基于目录直接挂载
# 好好理解这条命令
docker run \
	--name mysql \
	-e MYSQL_ROOT_PASSWORD=1 \
	-p 3306:3306 \
	-v /tmp/mysql/conf/hmy.cnf:/etc/mysql/conf.d/hmy.cnf \
	-v /tmp/mysql/data:/var/lib/mysql \
	-d \
	mysql:5.7.25

# DockerFile自定义镜像
# 案例：基于Ubuntu镜像构建⼀个新镜像，运⾏⼀个java项⽬
docker build -t javaweb:1.0 .    # docker-demo.jar、jdk8.tar.gz、Dockerfile三个文件/目录
如上：已成功构建镜像并部署到docker上去

# 案例：基于java:8-alpine镜像，将⼀个Java项⽬构建为镜像（基于一个已有镜像构建，更为简化）
docker build -t javaweb:2.0 .

# Docker-Compose：实际⽣产环境下，微服务的数量可是⾮常多的，集群部署的⼿段。帮助我们快速部署分布式应用，无需一个个微服务去构建镜像和部署。
# 案例：将之前学习的cloud-demo微服务集群利⽤DockerCompose部署
# gateway、order-service、user-service三个服务，mysql和nacos
# 一般先部署nacos，再部署其它
# 准备的文件：
gateway：Dockerfile、app.jar
order-service：Dockerfile、app.jar
user-service：Dockerfile、app.jar
mysql：将mysql容器挂载到这两个上面，数据和配置都有了
	 data
	 		hmy.cnf
	 conf
	 		数据库表
docker-compose.yml
内容如下 
------------------------------------------------------
version: "3.2"

services:
  nacos:
    image: nacos/nacos-server
    environment:
      MODE: standalone
    ports:
      - "8848:8848"
  mysql:	# 端口不对外暴露
    image: mysql:5.7.25
    environment:
      MYSQL_ROOT_PASSWORD: 123
    volumes:   # 配置数据卷挂载 应该挂载到我们准备好的目录上即data和conf  $PWD当前目录
      - "$PWD/mysql/data:/var/lib/mysql"
      - "$PWD/mysql/conf:/etc/mysql/conf.d/"
  userservice:
    build: ./user-service
  orderservice:
    build: ./order-service
  gateway:
    build: ./gateway
    ports:
      - "10010:10010" # 网关暴露的端口：整个微服务的入口
------------------------------------------------------
# 服务间使用服务名互相访问
# 命令
docker-compose up -d
docker-compose restart gateway userservice orderservice  # 重启服务[服务启动顺序的问题，一般先部署nacos]
docker-compose logs -f userservice # 查看某个服务的日志

关键还是写docker-compose文件，在里面把每个容器的运行方式都写好，就可以实现一键部署
```



### 一、初识Docker

#### 1、什么是Docker

**项目部署的问题**

大型项目组件较多，运行环境也较为复杂，部署时会碰到一些问题：
依赖关系复杂，容易出现兼容性问题
开发、测试、生产环境有差异



**Docker如何解决依赖的兼容问题的？**

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211214013817441.png" alt="image-20211214013817441" style="zoom: 33%;" />

不同环境的操作系统不同，Docker如何解决？我们先来了解下操作系统结构

> 内核与硬件交互，提供操作硬件的指令
> 系统应用封装内核指令为函数，便于程序员调用
>
> 用户程序基于系统函数库实现功能

Ubuntu和CentOS都是基于Linux内核，只是系统应用不同，提供的函数库有差异

![WechatIMG612](https://gitee.com/code0002/blog-img/raw/master/img/WechatIMG612.png)



**Docker如何解决不同系统环境的问题？**

Docker将用户程序与所需要调用的系统(比如Ubuntu)函数库一起打包

Docker运行到不同操作系统时，直接基于打包的库函数，借助于操作系统的Linux内核来运行



Docker如何解决大型项目依赖关系复杂，不同组件依赖的兼容性问题？
		Docker允许开发中将应用、依赖、函数库、配置一起打包，形成可移植镜像
		Docker应用运行在容器中，使用沙箱机制，相互隔离

Docker如何解决开发、测试、生产环境有差异的问题
  	  Docker镜像中包含完整运行环境，包括系统函数库，仅依赖系统的Linux内核，因此可以在任意Linux操作系统上运行



> **Docker是一个快速交付应用、运行应用的技术：**
> 		**可以将程序及其依赖、运行环境一起打包为一个镜像，可以迁移到任意Linux操作系统**
> 		**运行时利用沙箱机制形成隔离容器，各个应用互不干扰**
> 		**启动、移除都可以通过一行命令完成，方便快捷**

#### 2、Docker和虚拟机的区别

虚拟机（virtual machine）是在操作系统中模拟硬件设备，然后运行另一个操作系统，比如在 Windows 系统里面运行 Ubuntu 系统，这样就可以运行任意的Ubuntu应用了。

![image-20211214015419915](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214015419915.png)

> **Docker和虚拟机的差异：**
> 		**docker是一个系统进程；虚拟机是在操作系统中的操作系统**
> 		**docker体积小、启动速度快、性能好；虚拟机体积大、启动速度慢、性能一般**



#### 3、Docker架构

##### 镜像和容器

**镜像（Image）：**Docker将应用程序及其所需的依赖、函数库、环境、配置等文件打包在一起，称为镜像。
**容器（Container）：**镜像中的应用程序运行后形成的进程就是容器，只是Docker会给容器做隔离，对外不可见。

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211214123821346.png" alt="image-20211214123821346" style="zoom: 33%;" />



##### Docker和DockerHub

DockerHub：DockerHub是一个Docker镜像的托管平台。这样的平台称为Docker Registry。
国内也有类似于DockerHub 的公开服务，比如 网易云镜像服务、阿里云镜像库等。

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211214124015659.png" alt="image-20211214124015659" style="zoom:33%;" />



##### docker架构

**Docker是一个CS架构的程序，由两部分组成：**
**服务端(server)：Docker守护进程，负责处理Docker指令，管理镜像、容器等**
**客户端(client)：通过命令或RestAPI向Docker服务端发送指令。可以在本地或远程向服务端发送指令。**

![image-20211214124457505](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214124457505.png)



> 镜像：
> 		将应用程序及其依赖、环境、配置打包在一起
> 容器：
> 		镜像运行起来就是容器，一个镜像可以运行多个容器
> Docker结构：
> 		服务端：接收命令或远程请求，操作镜像或容器
> 		客户端：发送命令或者请求到Docker服务端
> DockerHub：
> 		一个镜像托管的服务器，类似的还有阿里云镜像服务，统称为DockerRegistry



#### 4、安装Docker

企业部署一般都是采用Linux操作系统，而其中又数CentOS发行版占比最多，因此我们在CentOS下安装Docker。参考课前资料中的文档。

Docker 分为 CE 和 EE 两大版本。CE 即社区版（免费，支持周期 7 个月），EE 即企业版，强调安全，付费使用，支持周期 24 个月。
Docker CE 分为 `stable` `test` 和 `nightly` 三个更新频道。
官方网站上有各种环境下的 [安装指南](https://docs.docker.com/install/)，这里主要介绍 Docker CE 在 CentOS上的安装。

##### 1.CentOS安装Docker

> Docker CE 支持 64 位版本 CentOS 7，并且要求内核版本不低于 3.10， CentOS 7 满足最低内核的要求，所以我们在CentOS 7安装Docker。

**卸载（可选）**

如果之前安装过旧版本的Docker，可以使用下面命令卸载：

```
yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-selinux \
                  docker-engine-selinux \
                  docker-engine \
                  docker-ce
```

![image-20211214145853175](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214145853175.png)



**安装Docker**

首先需要大家虚拟机联网，安装yum工具

```bash
yum install -y yum-utils \
           device-mapper-persistent-data \
           lvm2 --skip-broken
```

![image-20211214150645950](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214150645950.png)

然后更新本地镜像源：（直接复制粘贴）

```bash
# 设置docker镜像源
yum-config-manager \
    --add-repo \
    https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
    
sed -i 's/download.docker.com/mirrors.aliyun.com\/docker-ce/g' /etc/yum.repos.d/docker-ce.repo

yum makecache fast
```

![image-20211214150818679](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214150818679.png)

<font color=red>然后输入命令：</font>

```bash
yum install -y docker-ce
```

docker-ce为社区免费版本。稍等片刻，docker即可安装成功。

![image-20211214151418402](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214151418402.png)

![image-20211214151446667](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214151446667.png)





**启动Docker**

Docker应用需要用到各种端口，逐一去修改防火墙设置。非常麻烦，因此建议大家直接关闭防火墙！

启动docker前，一定要关闭防火墙后！！
启动docker前，一定要关闭防火墙后！！
启动docker前，一定要关闭防火墙后！！



```sh
# 关闭
systemctl stop firewalld
# 禁止开机启动防火墙
systemctl disable firewalld
```



通过命令启动docker：

```sh
systemctl start docker  # 启动docker服务

systemctl stop docker  # 停止docker服务

systemctl restart docker  # 重启docker服务
```



> 启动成功

![image-20211214151925745](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214151925745.png)



然后输入命令，可以查看docker版本：

```bash
docker -v
```

![image-20211214152122014](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214152122014.png)

**配置镜像加速**

docker官方镜像仓库网速较差，我们需要设置国内镜像服务：
参考阿里云的镜像加速文档：https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors

> 针对Docker客户端版本大于 1.10.0 的用户
> 您可以通过修改daemon配置文件/etc/docker/daemon.json来使用加速器

```sh
sudo mkdir -p /etc/docker

sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://v5kumamv.mirror.aliyuncs.com"]
}
EOF

sudo systemctl daemon-reload

sudo systemctl restart docker
```

![image-20211214152835303](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214152835303.png)



##### 2.CentOS7安装DockerCompose









### 二、Docker的基本操作

#### 1、镜像操作

##### 镜像相关命令

镜像名称一般分两部分组成：[repository]:[tag]。
在没有指定tag时，默认是latest，代表最新版本的镜像

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211214153317751.png" alt="image-20211214153317751" style="zoom:25%;" />



##### 镜像操作命令

![image-20211214153517303](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214153517303.png)

>会看帮助文档：
>
>docker --help
>
>docker images --help



##### 案例：从DockerHub中拉取一个nginx镜像并查看

首先去镜像仓库搜索nginx镜像，比如DockerHub:

https://registry.hub.docker.com/search?q=nginx&type=image

![image-20211214154158866](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214154158866.png)

根据查看到的镜像名称，拉取自己需要的镜像，通过命令：docker pull nginx

```sh
docker pull nginx
```

![image-20211214154509820](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214154509820.png)

通过命令：docker images 查看拉取到的镜像

![image-20211214154732316](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214154732316.png)

>以上熟悉了拉取和查看命令
>
>下面尝试保存和加载命令

**利用docker save将nginx镜像导出磁盘，然后再通过load加载回来**

步骤一：利用docker xx --help命令查看docker save和docker load的语法

步骤二：使用docker save导出镜像到磁盘 

![image-20211214155519832](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214155519832.png)

步骤三：使用docker load加载镜像

> 镜像删除、查看、加载

![image-20211214160105010](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214160105010.png)



> 镜像操作有哪些？
> docker images
> docker rmi
> docker pull
> docker push
> docker save 
> docker load



**去DockerHub搜索并拉取一个Redis镜像**

```
去DockerHub搜索Redis镜像
查看Redis镜像的名称和版本
利用docker pull命令拉取镜像
利用docker save命令将 redis:latest打包为一个redis.tar包
利用docker rmi 删除本地的redis:latest
利用docker load 重新加载 redis.tar文件
```



#### 2、容器操作

![image-20211214161316687](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214161316687.png)

##### 案例：创建运行一个Nginx容器

去docker hub查看Nginx的容器运行命令

```sh
docker run --name containerName -p 80:80 -d nginx
```

> 命令解读：
> 		docker run ：创建并运行一个容器
> 		--name : 给容器起一个名字，比如叫做mn
> 		-p ：将宿主机端口与容器端口映射，冒号左侧是宿主机端口，右侧是容器端口
> 		-d：后台运行容器
> 		nginx：镜像名称，例如nginx

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211214162310530.png" alt="image-20211214162310530" style="zoom:33%;" />

![image-20211214162517371](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214162517371.png)

![image-20211214162907310](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214162907310.png)

>查看日志

![image-20211214163001699](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214163001699.png)

> 持续日志输出

![image-20211214163226926](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214163226926.png)



> docker run命令的常见参数有哪些？
> 		--name：指定容器名称
> 		-p：指定端口映射
> 		-d：让容器后台运行
> 查看容器日志的命令：
> 		docker logs
> 		添加 -f 参数可以持续查看日志
> 查看容器状态：
> 		docker ps



##### 案例：进入Nginx容器，修改HTML文件内容，添加“传智教育欢迎您”

步骤一：进入容器。进入我们刚刚创建的nginx容器的命令为：

```sh
docker exec -it mn bash 
```

> 命令解读：
> docker exec ：进入容器内部，执行一个命令
> -it : 给当前进入的容器创建一个标准输入、输出终端，允许我们与容器交互
> mn ：要进入的容器的名称
> bash：进入容器后执行的命令，bash是一个linux终端交互命令

步骤二：进入nginx的HTML所在目录 /usr/share/nginx/html

```bash
cd /usr/share/nginx/html
```

步骤三：修改index.html的内容

```bash
sed -i 's#Welcome to nginx#Welcome to nginx!!!传智教育欢迎您#g' index.html
sed -i 's#<head>#<head><meta charset="utf-8">#g' index.html
```

![image-20211214165429798](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214165429798.png)



> 查看容器状态：
> 		docker ps 
> 		添加-a参数查看所有状态的容器
> 删除容器：
> 		docker rm
> 		不能删除运行中的容器，除非添加 -f 参数
> 进入容器：
> 		命令是docker exec -it [容器名] [要执行的命令]
> 		exec命令可以进入容器修改文件，但是在容器内修改文件是不推荐的

![image-20211214170109661](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214170109661.png)



##### 练习：创建并运行一个redis容器，并且支持数据持久化

步骤一：到DockerHub搜索Redis镜像
步骤二：查看Redis镜像文档中的帮助信息
步骤三：利用docker run 命令运行一个Redis容器

```bash
docker run --name redis -p 6379:6379 -d redis redis-server --appendonly yes
```

![image-20211214173315655](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214173315655.png)



访问它，打开一个Redis的客户端

![image-20211214180611822](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214180611822.png)



> 事实上，快捷命令直接进入：

```bash
docker execi -t mrr edis-cli
```



##### 练习：进入redis容器，并执行redis-cli客户端命令，存入num=666

![image-20211214180732083](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214180732083.png)



#### 3、数据卷（容器数据管理）

容器与数据耦合的问题

![image-20211214211434187](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214211434187.png)

数据卷（volume）是一个虚拟目录，指向宿主机文件系统中的某个目录。

![image-20211214211815944](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214211815944.png)



##### 操作数据卷

基本语法如下：

```bash
docker volume [COMMAND]
```

> docker volume命令是数据卷操作，根据命令后跟随的command来确定下一步的操作：
> 		create	创建一个volume
> 		inspect	显示一个或多个volume的信息
> 		ls		列出所有的volume
> 		prune		删除未使用的volume
> 		rm		删除一个或多个指定的volume



##### 案例：创建一个数据卷，并查看数据卷在宿主机的目录位置

创建数据卷

```sh
docker volume create html
```



查看所有数据卷

```sh
docker volume ls
```



查看数据卷详细信息卷（包括所在宿主机的位置）

```sh
docker volume inspect html
```

![image-20211214213456120](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214213456120.png)

> 数据卷的作用：
> 		将容器与数据分离，解耦合，方便操作容器内数据，保证数据安全
> 数据卷操作：
> 		docker volume create
> 		docker volume ls
> 		docker volume inspect
> 		docker volume rm
> 		docker volume prune



##### 挂载数据卷

我们在创建容器时，可以通过 -v 参数来挂载一个数据卷到某个容器目录

![image-20211214214121382](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214214121382.png)

##### 案例：创建一个nginx容器，修改容器内的html目录内的index.html内容

需求说明：上个案例中，我们进入nginx容器内部，已经知道nginx的html目录所在位置/usr/share/nginx/html ，我们需要把这个目录挂载到html这个数据卷上，方便操作其中的内容。
提示：运行容器时使用 -v 参数挂载数据卷

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211214220924303.png" alt="image-20211214220924303" style="zoom: 33% float:left;" />



![image-20211214220312195](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214220312195.png)

![image-20211214220214267](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214220214267.png)

![image-20211214220340170](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214220340170.png)

> 数据卷挂载方式：
> 		-v volumeName: /targetContainerPath
> 		如果容器运行时volume不存在，会自动被创建出来



##### 案例：创建并运行一个MySQL容器，将宿主机目录直接挂载到容器

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211214221907832.png" alt="image-20211214221907832" style="zoom:50%;" />

> 值得挂载的内容就2个：数据data和配置文件conf
>
> 我们的配置覆盖容器内的配置

```bash
# 好好理解这条命令
docker run \
	--name mysql \
	-e MYSQL_ROOT_PASSWORD=1 \
	-p 3306:3306 \
	-v /tmp/mysql/conf/hmy.cnf:/etc/mysql/conf.d/hmy.cnf \
	-v /tmp/mysql/data:/var/lib/mysql \
	-d \
	mysql:5.7.25
```



![image-20211214224314094](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214224314094.png)

![image-20211214224343930](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214224343930.png)



```sh
# 命令太多了，在这里总结一下




# 加载镜像
docker load -i mysql.tar

# 查看镜像
docker images

# 创建文件夹下的目录
mkdir -p mysql/data
mkdir -p mysql/conf
```



> 已经学习了两种数据导入（挂载）的方式：
>
> 一种是基于数据卷，一种是基于目录直接挂载；
>
> 一种是自动化但是隐藏了细节，一种是细节自己实现但是没有自动化；

![image-20211214224825040](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214224825040.png)



docker run的命令中通过 -v 参数挂载文件或目录到容器中：
		-v volume名称:容器内目录
		-v 宿主机文件:容器内文件
		-v 宿主机目录:容器内目录
数据卷挂载与目录直接挂载的区别：
		数据卷挂载耦合度低，由docker来管理目录，但是目录较深，不好找
		目录挂载耦合度高，需要我们自己管理目录，不过目录容易寻找查看



### 三、DockerFile自定义镜像

> 已经学习：如何去拉取一个镜像，如何基于镜像去创建并运行容器；
>
> 自己写的微服务代码，官方不可能帮我们制作镜像，自己的微服务自己制作镜像。

#### 1、镜像结构

镜像是将应用程序及其需要的系统函数库、环境、配置、依赖打包而成。

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211214225525656.png" alt="image-20211214225525656" style="zoom:25%;" />

> 底层函数库、环境配置、依赖安装、应用安装、应用配置
>
> 我们应该从基础开始，逐层构建



<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211214230046084.png" alt="image-20211214230046084" style="zoom: 33%;" />

> 镜像是分层结构，每一层称为一个Layer
> 		BaseImage层：包含基本的系统函数库、环境变量、文件系统
> 		Entrypoint：入口，是镜像中应用启动的命令
> 		其它：在BaseImage基础上添加依赖、安装程序、完成整个应用的安装和配置

#### 2、DockerFile语法

`Dockerfile`就是一个文本文件，其中包含一个个的指令(Instruction)，用指令来说明要执行什么操作来构建镜像。每一个指令都会形成一层Layer。

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211214230303095.png" alt="image-20211214230303095" style="zoom:33%;" />

更新详细语法说明，请参考官网文档： https://docs.docker.com/engine/reference/builder

##### [自定义镜像-Dockerfile]案例：基于Ubuntu镜像构建一个新镜像，运行一个java项目

步骤1：新建一个空文件夹docker-demo
步骤2：拷贝课前资料中的docker-demo.jar文件到docker-demo这个目录
步骤3：拷贝课前资料中的jdk8.tar.gz文件到docker-demo这个目录
步骤4：拷贝课前资料提供的Dockerfile到docker-demo这个目录
步骤5：进入docker-demo
步骤6：运行命令：

```bash
docker build -t javaweb:1.0 .
```

最后访问 http://192.168.150.101:8090/hello/count，其中的ip改成你的虚拟机ip



![image-20211214231412970](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214231412970.png)

![image-20211214231444454](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214231444454.png)



![image-20211214231903459](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214231903459.png)

> 如上：已成功构建镜像并部署到docker上去



```sh
# 指定基础镜像
FROM ubuntu:16.04
# 配置环境变量，JDK的安装目录
ENV JAVA_DIR=/usr/local

# 拷贝jdk和java项目的包
COPY ./jdk8.tar.gz $JAVA_DIR/
COPY ./docker-demo.jar /tmp/app.jar

# 安装JDK
RUN cd $JAVA_DIR \
 && tar -xf ./jdk8.tar.gz \
 && mv ./jdk1.8.0_144 ./java8

# 配置环境变量
ENV JAVA_HOME=$JAVA_DIR/java8
ENV PATH=$PATH:$JAVA_HOME/bin

# 暴露端口
EXPOSE 8090
# 入口，java项目的启动命令
ENTRYPOINT java -jar /tmp/app.jar
```

> COPY ./docker-demo.jar /tmp/app.jar
>
> 上面这行命令才是真正来构建java项目的，其它上面的命令都是来安装JDK，如果之后又有一个微服务要来构建，会有重复的步骤。
>
> 所以，可以把前n层提前构建好做一个镜像放在那里，以后都在这个基础上去构建，会方便很多，这就是分层的好处。
>
> 事实上，java:8-alpine镜像已经帮我们做好了。

##### 案例：基于java:8-alpine镜像，将一个Java项目构建为镜像

可以将上面Dockerfile中的命令简化如下

```sh
# 指定基础镜像
FROM java:8-alpine

COPY ./docker-demo.jar /tmp/app.jar

# 暴露端口
EXPOSE 8090
# 入口，java项目的启动命令
ENTRYPOINT java -jar /tmp/app.jar
```



![image-20211214232947091](https://gitee.com/code0002/blog-img/raw/master/img/image-20211214232947091.png)

> 只需要4步就可以完成。



> Dockerfile的本质是一个文件，通过指令描述镜像的构建过程
> Dockerfile的第一行必须是FROM，从一个基础镜像来构建
> 基础镜像可以是基本操作系统，如Ubuntu。也可以是其他人制作好的镜像，例如：java:8-alpine



#### 3、构建Java项目（如上构建的javaweb1.0和javaweb2.0）



### 四、Docker-Compose

> 实际生产环境下，微服务的数量可是非常多的，集群部署的手段。

#### 1、初识Docker-Compose

##### 介绍

![image-20211215012032022](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215012032022.png)

DockerCompose的详细语法参考官网：https://docs.docker.com/compose/compose-file/

##### 安装

1）下载

```bash
# 安装
curl -L https://github.com/docker/compose/releases/download/1.23.1/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
```

可以使用课前资料提供的docker-compose文件，上传到`/usr/local/bin/`目录也可以。

2）修改文件权限：变绿色，可执行

```bash
# 修改权限
chmod +x /usr/local/bin/docker-compose
```

3）Bash自动补全命令

```bash
# 补全命令
curl -L https://raw.githubusercontent.com/docker/compose/1.29.1/contrib/completion/bash/docker-compose > /etc/bash_completion.d/docker-compose
```

如果这里出现错误，需要修改自己的hosts文件：

```bash
echo "199.232.68.133 raw.githubusercontent.com" >> /etc/hosts
```

> DockerCompose有什么作用？
> 帮助我们快速部署分布式应用，无需一个个微服务去构建镜像和部署。



#### 2、部署微服务集群

##### 案例：将之前学习的cloud-demo微服务集群利用DockerCompose部署

<img src="https://gitee.com/code0002/blog-img/raw/master/img/image-20211215023346516.png" alt="image-20211215023346516" style="zoom: 33%;" />



![image-20211215023529096](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215023529096.png)

查看日志：docker-compose logs -f userservice

![image-20211215023715085](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215023715085.png)



测试：

![image-20211215024005558](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215024005558.png)



![image-20211215024156027](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215024156027.png)



### 五、Docker镜像仓库

#### 1、搭建私有镜像仓库

##### 常见镜像仓库服务

**镜像仓库（ Docker Registry ）有公共的和私有的两种形式：**

> 公共仓库：例如Docker官方的 Docker Hub，国内也有一些云服务商提供类似于 Docker Hub 的公开服务，比如 网易云镜像服务、DaoCloud 镜像服务、阿里云镜像服务等。
> 除了使用公开仓库外，用户还可以在本地搭建私有 Docker Registry。企业自己的镜像最好是采用私有Docker Registry来实现。



##### 搭建

> 搭建镜像仓库可以基于Docker官方提供的DockerRegistry来实现。官网地址：https://hub.docker.com/_/registry

##### 1）搭建简化版镜像仓库

Docker官方的Docker Registry是一个基础版本的Docker镜像仓库，具备仓库管理的完整功能，但是没有图形化界面。

搭建方式比较简单，命令如下：

```sh
docker run -d \
    --restart=always \
    --name registry	\
    -p 5000:5000 \
    -v registry-data:/var/lib/registry \
    registry
```



命令中挂载了一个数据卷registry-data到容器内的/var/lib/registry 目录，这是私有镜像库存放数据的目录。

访问http://YourIp:5000/v2/_catalog 可以查看当前私有镜像服务中包含的镜像

##### 2）搭建带有图形化界面版本

使用DockerCompose部署带有图象界面的DockerRegistry，命令如下：

```yaml
version: '3.0'
services:
  registry:
    image: registry
    volumes:
      - ./registry-data:/var/lib/registry
  ui:
    image: joxit/docker-registry-ui:static
    ports:
      - 8080:80
    environment:
      - REGISTRY_TITLE=传智教育私有仓库
      - REGISTRY_URL=http://registry:5000
    depends_on:
      - registry
```



##### 3）配置Docker信任地址

我们的私服采用的是http协议，默认不被Docker信任，所以需要做一个配置：

```sh
# 打开要修改的文件
vi /etc/docker/daemon.json
# 添加内容：
"insecure-registries":["http://192.168.150.101:8080"]
# 重加载
systemctl daemon-reload
# 重启docker
systemctl restart docker
```

![image-20211215030300597](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215030300597.png)

![image-20211215030444085](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215030444085.png)



![image-20211215030515158](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215030515158.png)



#### 2、向镜像仓库推送镜像

#### 3、从镜像仓库拉取镜像

##### 在私有镜像仓库推送或拉取镜像

![image-20211215030715398](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215030715398.png)



```
# 查看现有镜像



```

推送：

![image-20211215031243981](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215031243981.png)



![image-20211215031312728](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215031312728.png)



![image-20211215031418472](https://gitee.com/code0002/blog-img/raw/master/img/image-20211215031418472.png)

> 推送本地镜像到仓库前都必须重命名(docker tag)镜像，以镜像仓库地址为前缀
> 镜像仓库推送前需要把仓库地址配置到docker服务的daemon.json文件中，被docker信任
> 推送使用docker push命令
> 拉取使用docker pull命令





