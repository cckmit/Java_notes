

# 零 写在前面

## 1 认知

- 整个Java体系最核心的框架，没有之一	

- 面试必备 / 热点（从Spring的核心原理，到SpringMVC的执行流程，再到SpringBoot的自动配置）

- 技术、思想提升（大量设计模式的应用和体现）（应用了很多技术）



<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220408211450426.png)





## 2 总结

### 容器与bean（01-08）

### AOP（09-19）

### Web MVC（20-36）

### Spring Boot（37-42）

### 其它（43-49）



## 3 参考与推荐如下资料

### 推荐1：黑马最新课程

[黑马程序员20220420最新SSM框架教程_Spring+SpringMVC+Maven高级+SpringBoot+MyBatisPlus企业实用开发技术](https://www.bilibili.com/video/BV1Fi4y1S7ix?spm_id_from=333.999.0.0)



[黑马220323 Spring高级课程](https://www.bilibili.com/video/BV1P44y1N7QG?spm_id_from=333.999.0.0)

### 推荐2：官方文档

`spring4`官方文档

https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/spring-framework-reference/html/overview.html

`spring5.2.19` 官方文档

https://docs.spring.io/spring-framework/docs/5.2.19.RELEASE/spring-framework-reference/index.html

<br>

### 推荐3：极客时间丁雪丰

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416172241777.png)





<hr>





# 一、Spring相关概念

## 1 介绍

### 1.1 为什么学

- Spring技术是JavaEE开发必备技能，企业开发技术选型命中率>90%

- 专业角度
  - **简化开发**，降低企业级开发的复杂性
  - **框架整合**，高效整合其它技术，提高企业级应用开发与运行效率

### 1.2 学什么

Spring框架主要的优势是在简化开发和框架整合上

```bash
# 简化开发
- IOC
- AOP
	- 事务处理（Spring中AOP的具体应用，可以简化项目中的事务管理，也是Spring技术中的一大亮点。事务处理更简单更高效更强大。）

# 框架整合（Spring在框架整合这块已经做到了极致，它可以整合市面上几乎所有主流框架）
- MyBatis
- MyBatis-plus
- Struts
- Struts2
- Hibernate

# 综上，主要分为4部分
(1)IOC
(2)整合Mybatis(IOC的具体应用)
(3)AOP
(4)声明式事务(AOP的具体应用)
```

### 1.3 怎么学

```bash
# 学习Spring框架设计思想
对于Spring来说，它能迅速占领全球市场，不只是说它的某个功能比较强大，更重要是在它的思想上。
# 学习基础操作，思考操作与思想间的联系
掌握了Spring的设计思想，然后就需要通过一些基础操作来思考操作与思想之间的关联关系
# 学习案例，熟练应用操作的同时，体会思想
会了基础操作后，就需要通过大量案例来熟练掌握框架的具体应用，加深对设计思想的理解。
```



## 2 Spring家族和Spring发展史

官网：https://spring.io/

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220426185049133.png)

重点关注Spring Framework、SpringBoot和 SpringCloud :

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220426185151752.png)



```bash
# Spring Framework:Spring框架，是Spring中最早最核心的技术，也是所有其他技术的基础。
Spring最早出现的技术，本笔记的内容。
Spring全家桶中的地位：其它所有的技术都是依赖它执行的，是一个底层的框架，是一个设计型的框架，具有举足轻重的地位。
# SpringBoot:Spring是来简化开发，而SpringBoot是来帮助Spring在简化的基础上能更快速进行开发。
简化开发的基础上加速开发，提高开发速度。
# SpringCloud:这个是用来做分布式之微服务架构的相关开发。
分布式开发技术

# 还有很多其他的技术，也比较流行，如SpringData,SpringSecurity等，这些都可以被应用在我们的项目中。
# 我们今天所学习的Spring其实指的是Spring Framework。
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220426190240474.png?w=550)





```bash
# Spring1.0是[ 纯配置文件] 开发
# Spring2.0为了简化开发[ 引入了注解开发 ]，此时是配置文件加注解的开发方式
# Spring3.0已经可以进行[ 纯注解开发 ]，使开发效率大幅提升，我们的课程会以注解开发为主
# Spring4.0根据JDK的版本升级对个别API进行了调整
# Spring5.0已经全面支持JDK8，现在Spring最新的是5系列所以建议大家把JDK安装成1.8版
```



## 3 Spring系统架构

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220426190817874.png)

Spring Framework的5版本目前没有最新的架构图，而最新的是4版本，所以接下来主要研究的是4的架构图

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416173559517.png)



```bash
# Core Container核心层
核心容器，这个模块是Spring最核心的模块，其他的都需要依赖该模块

# AOP层
AOP:面向切面编程（设计概念），它依赖核心层容器，目的是在不改变原有代码的前提下对其进行功能增强
Aspects:AOP是思想,Aspects是对AOP思想的具体实现

Spring：Aspects已经做的非常好了，告诉大家Aspects比我做得好，建议大家使用Aspects。
所以后面开发的时候，大家发现，除了要导AOP的坐标，还要导Aspects的坐标。

# 数据层
Data Access:数据访问，Spring全家桶中有对数据访问的具体实现技术
Data Integration:数据集成，Spring支持整合其他的数据层解决方案，比如Mybatis
Transactions:事务，Spring中事务管理是Spring AOP的一个具体实现，也是后期学习的重点内容

事务这一块做了非常大的突破，提供了效率非常高的事务控制方案。

# web层
这一层的内容将在SpringMVC框架具体学习

# test层
Spring主要整合了Junit来完成单元测试和集成测试

架构图讲究上层依赖下层，如AOP依赖核心容器的执行
```





![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220426192735551.png)



## 4 Spring核心概念

目前项目中的问题：
![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220426193133324.png)



针对这个问题，该如何解决呢?

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220426193253425.png)



## 一起认识Spring家族的主要成员

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416173330880.png)

Spring，始于框架，但不限于框架

（1）Spring Framework 

（2）Spring相关项⽬

（3）整个Spring家族 https://spring.io/projects



### Spring Framework

The Spring Framework consists of features organized into about 20 modules. These modules are grouped into `Core Container`, `Data Access/Integration`, `Web`, `AOP (Aspect Oriented Programming)`, `Instrumentation`, `Messaging`, and `Test`, as shown in the following diagram.









<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1648110566537-f5ec2849-b148-4c00-8d64-bf54697cc0dd.png)



<br>

使用场景

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1648110013126-e5faaf5c-6f36-4029-a29e-40d590219f54.png)



### Spring Boot

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416173750815.png)





### Spring Cloud

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416173709755.png)





## 跟着Spring了解技术趋势

### 看看 Spring 5.x 的改变暗示了什么

| 改动点          | 改变的意义                                               | 一些思考                       |
| --------------- | -------------------------------------------------------- | ------------------------------ |
| Java 8+、Kotlin | 语⾔⻋轮滚滚向前                                         | 还在⽤低版本的 Java 我该怎么办 |
| WebFlux         | 异步编程模式的崛起                                       | 全⾯落地尚需时⽇               |
| 去掉了很多⽀持  | Portlet 过时了、Velocity 不维护了、JasperReport 不流⾏了 | 库有千千万，我该怎么选？       |



### Spring Boot 和 Spring Cloud 的出现是必然的

• 开箱即⽤

• 与⽣态圈的深度整合

• 注重运维

• Cloud Native的⼤⽅向

• 最佳实践不嫌多，固化到系统实现中才是王道



## 编写你的第一个Spring程序

原始的包3.1k

实际使用的包17M（打包时把所有的依赖都放在了jar包里面，可执行的jar包）

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416183502426.png)



# 二、JDBC必知必会



## 配置单数据源











## 配置多数据源



## 那些好⽤的连接池 HikariCP

为什么快

- 字节码级别优化（很多⽅法通过 **JavaAssist** ⽣成）

- ⼤量⼩改进

  - ⽤ FastStatementList 代替 ArrayList

  - ⽆锁集合 ConcurrentBag

  - 代理类的优化（⽐如，⽤ invokestatic 代替了 invokevirtual）





常用配置

- spring.datasource.hikari.maximumPoolSize=10
- spring.datasource.hikari.minimumIdle=10
- spring.datasource.hikari.idleTimeout=600000
- spring.datasource.hikari.connectionTimeout=30000
- spring.datasource.hikari.maxLifetime=1800000



其他配置详⻅ **HikariCP** 官⽹：https://github.com/brettwooldridge/HikariCP







## 那些好⽤的连接池 Alibaba Druid

“Druid连接池是阿⾥巴巴开源的数据库连接池项⽬。Druid连接池为监控⽽⽣，内置强⼤的监控功能，监控特性不影响性能。功能强⼤，能防SQL注⼊，内置Logging能诊断Hack应⽤⾏为。”

–Alibaba Druid 官⽅介绍



经过阿⾥巴巴各⼤系统的考验，值得信赖

实⽤的功能

- 详细的监控（真的是全⾯）
- ExceptionSorter，针对主流数据库的返回码都有⽀持
- SQL 防注⼊
- 内置加密配置
- 众多扩展点，⽅便进⾏定制



### 配置方式

直接配置 **DruidDataSource** 

通过 **druid-spring-boot-starter**

- spring.datasource.druid.*

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416192920763.png)





### Druid Filter

⽤于定制连接池操作的各种环节

可以继承 FilterEventAdapter 以便⽅便地实现 Filter

修改 META-INF/druid-fifilter.properties 增加 Filter 配置

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416193259873.png)



## 通过 Spring JDBC 访问数据库

### Spring 的 JDBC 操作类

spring-jdbc

- core，JdbcTemplate 等相关核⼼接⼝和类
- datasource，数据源相关的辅助类
- object，将基本的 JDBC 操作封装成对象
- support，错误码等其他辅助⼯具



### 常⽤的 Bean 注解

通过注解定义 Bean 

- @Component
- @Repository
- @Service
- @Controller
  - @RestController



### 简单的 JDBC 操作

JdbcTemplate 

- query
- queryForObject
- queryForList
- update
- execute



### SQL 批处理



JdbcTemplate 

- batchUpdate
  - BatchPreparedStatementSetter

NamedParameterJdbcTemplate 

- batchUpdate
  - SqlParameterSourceUtils.createBatch





## Spring的事务抽象

⼀致的事务模型

- JDBC/Hibernate/myBatis
- DataSource/JTA



### 事务抽象的核⼼接⼝

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416200545077.png)



### 事务传播特性

| 传播性                    | 值   | 描述                                 |
| ------------------------- | ---- | ------------------------------------ |
| PROPAGATION_REQUIRED      | 0    | 当前有事务就⽤当前的，没有就⽤新的   |
| PROPAGATION_SUPPORTS      | 1    | 事务可有可⽆，不是必须的             |
| PROPAGATION_MANDATORY     | 2    | 当前⼀定要有事务，不然就抛异常       |
| PROPAGATION_REQUIRES_NEW  | 3    | ⽆论是否有事务，都起个新的事务       |
| PROPAGATION_NOT_SUPPORTED | 4    | 不⽀持事务，按⾮事务⽅式运⾏         |
| PROPAGATION_NEVER         | 5    | 不⽀持事务，如果有事务则抛异常       |
| PROPAGATION_NESTED        | 6    | 当前有事务就在当前事务⾥再起⼀个事务 |







### 事务隔离特性

| 隔离性                     | 值   | 脏读 | 不可重复读 | 幻读 |
| -------------------------- | ---- | ---- | ---------- | ---- |
| ISOLATION_READ_UNCOMMITTED | 1    | ✅    | ✅          | ✅    |
| ISOLATION_READ_COMMITTED   | 2    | ❎    | ✅          | ✅    |
| ISOLATION_REPEATABLE_READ  | 3    | ❎    | ❎          | ✅    |
| ISOLATION_SERIALIZABLE     | 4    | ❎    | ❎          | ❎    |



### 编程式事务









### 声明式事务





### 基于注解的配置⽅式

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416201112453.png)











<hr>



## 分割

## 分割

## 分割



## 2. 快速入门



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1648111789567-632fdff0-fbb9-4c81-bc0b-c9034ea71350.png)





![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1648111736784-d8be2836-5c4d-4eb7-b74e-28cdd3d83745.png)



`spring`程序开发步骤：

① 导入 Spring 开发的基本包坐标

② 编写 Dao 接口和实现类

③ 创建 Spring 核心配置文件

④ 在 Spring 配置文件中配置 UserDaoImpl

⑤ 使用 Spring 的 API 获得 `Bean` 实例



<hr>



**spring开发的基本包坐标**

```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context</artifactId>
  <version>5.0.5.RELEASE</version>
</dependency>
```



**编写Dao接口和实现类**

```java
public interface UserDao {
    public void save();
}

public class UserDaoImpl implements UserDao {
    @Override
    public void save() {
        System.out.println("UserDao save method running....");
    }
}
```



**spring配置文件**

```xml
<bean id="userDao" class="com.itheima.dao.impl.UserDaoImpl"></bean>
```



**测试使用spring的API获取`bean`实例**

```java
@Test
public void test1(){
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    UserDao userDao = (UserDao) applicationContext.getBean("userDao");
    userDao.save();
}
```



## 3. 配置文件



### 3.1 bean标签基本配置

用于配置对象交由Spring 来创建。

默认情况下它调用的是类中的无参构造函数，如果没有无参构造函数则不能创建成功。

基本属性：

- id：Bean实例在Spring容器中的唯一标识
- class：Bean的全限定名称

### 3.2 范围配置

| 取值范围       | 说明                                                         | 实例化个数 | 实例化时机                                     | 生命周期                                                     |
| -------------- | ------------------------------------------------------------ | ---------- | ---------------------------------------------- | ------------------------------------------------------------ |
| singleton      | 默认值，单例的                                               | 1          | 当Spring核心文件被加载时，实例化配置的Bean实例 | - 对象创建：当应用加载，创建容器时，对象就被创建了 <br/>- 对象运行：只要容器在，对象一直活着<br/>- 对象销毁：当应用卸载，销毁容器时，对象就被销毁了 |
| prototype      | 多例的                                                       | 多个       | 当调用getBean()方法时实例化Bean                | - 对象创建：当使用对象时，创建新的对象实例<br/>- 对象运行：只要对象在使用中，就一直活着<br/>- 对象销毁：当对象长时间不用时，被 Java 的垃圾回收器回收了 |
| request        | WEB 项目中，Spring 创建一个 Bean 的对象，将对象存入到 request 域中 |            |                                                |                                                              |
| session        | WEB 项目中，Spring 创建一个 Bean 的对象，将对象存入到 session 域中 |            |                                                |                                                              |
| global session | WEB 项目中，应用在 Portlet 环境，如果没有 Portlet 环境那么globalSession 相当于 session |            |                                                |                                                              |

### 3.3 生命周期配置

- init-method：指定类中的初始化方法名称
- destroy-method：指定类中销毁方法名称



### 3.4 bean实例化3种方式

> 无参构造方法实例化、工厂静态方法实例化、工程实例方法实例化

#### 无参构造方法实例化

它会根据默认无参构造方法来创建类对象，如果bean中没有默认无参构造函数，将会创建失败

```xml
<bean id="userDao" class="com.itheima.dao.impl.UserDaoImpl"/>
```



#### 工厂静态方法实例化

工厂的静态方法返回Bean实例

```java
public class StaticFactoryBean {
	public static UserDao createUserDao(){
		return new UserDaoImpl();
	}
}
```



```xml
<bean id="userDao" class="com.itheima.factory.StaticFactoryBean" factory-method="createUserDao" />
```



#### 工程实例方法实例化

工厂的非静态方法返回Bean实例

```java
public class DynamicFactoryBean {
	public UserDao createUserDao(){
		return new UserDaoImpl();
	}
}
```



```xml
<bean id="factoryBean" class="com.itheima.factory.DynamicFactoryBean"/>
<bean id="userDao" factory-bean="factoryBean" factory-method="createUserDao"/>
```



### 3.5 bean依赖注入

① 创建 UserService，UserService 内部再调用 UserDao的save() 方法

```java
public class UserServiceImpl implements UserService {
	@Override
	public void save() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDao userDao = (UserDao) applicationContext.getBean("userDao");
		userDao.save();
	}
}
```

② 将 UserServiceImpl 的创建权交给 Spring

```xml
<bean id="userService" class="com.itheima.service.impl.UserServiceImpl"/>
```

③ 从 Spring 容器中获得 UserService 进行操作

```java
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
UserService userService = (UserService) applicationContext.getBean("userService");
userService.save();
```



**分析一下：**

目前UserService实例和UserDao实例都存在与Spring容器中，当前的做法是在容器外部获得UserService实例和UserDao实例，然后在程序中进行结合。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220324201032850.png)



因为UserService和UserDao都在Spring容器中，而最终程序直接使用的是UserService，所以可以在Spring容器中，将UserDao设置到UserService内部。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220324201232572.png)



#### 概念 

依赖注入（Dependency Injection）：它是 Spring 框架核心 **IOC** 的具体实现。

在编写程序时，通过控制反转，把对象的创建交给了 Spring，但是代码中不可能出现没有依赖的情况。

IOC 解耦只是降低他们的依赖关系，但不会消除。例如：业务层仍会调用持久层的方法。

那这种业务层和持久层的依赖关系，在使用 Spring 之后，就让 Spring 来维护了。简单的说，就是坐等框架把持久层对象传入业务层，而不用我们自己去获取。







> 怎么将UserDao怎样注入到UserService内部呢？

#### 依赖注入方式1——构造方法

创建有参构造

```java
public class UserServiceImpl implements UserService {
		@Override
		public void save() {
				ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
				UserDao userDao = (UserDao) applicationContext.getBean("userDao");
				userDao.save();
		}
}
```

配置Spring容器调用有参构造时进行注入

```xml
<bean id="userDao" class="com.itheima.dao.impl.UserDaoImpl"/>

<bean id="userService" class="com.itheima.service.impl.UserServiceImpl">
		<constructor-arg name="userDao" ref="userDao"></constructor-arg>
</bean>
```







#### 依赖注入方式2——set方法

在UserServiceImpl中添加setUserDao方法

```java
public class UserServiceImpl implements UserService {
		private UserDao userDao;
		public void setUserDao(UserDao userDao) {
				this.userDao = userDao;
		}
  
		@Override
		public void save() {
				userDao.save();
		}
}
```



配置Spring容器调用 `set` 方法进行注入

```xml
<bean id="userDao" class="com.itheima.dao.impl.UserDaoImpl"/>

<bean id="userService" class="com.itheima.service.impl.UserServiceImpl">
		<property name="userDao" ref="userDao"/>
</bean>
```



P命名空间注入本质也是set方法注入，但比起上述的set方法注入更加方便，主要体现在配置文件中，如下：

```xml
首先，需要引入P命名空间：
xmlns:p="http://www.springframework.org/schema/p"
其次，需要修改注入方式
<bean id="userService" class="com.itheima.service.impl.UserServiceImpl" p:userDao-ref="userDao"/>
```



#### 依赖注入的数据类型

上面的操作，都是注入的引用Bean，除了对象的引用可以注入，普通数据类型，集合等都可以在容器中进行注入。

> 可注入的三种数据类型
>
> - 普通数据类型
> - 引用数据类型
> - 集合数据类型
>
> 其中引用数据类型，此处就不再赘述了，之前的操作都是对UserDao对象的引用进行注入的，下面将以set方法注入为
> 例，演示普通数据类型和集合数据类型的注入。



普通数据类型的注入

```java
public class UserDaoImpl implements UserDao {
		private String company;
		private int age;
		public void setCompany(String company) {
				this.company = company;
		}
  
		public void setAge(int age) {
				this.age = age;
		}
  	
		public void save() {
				System.out.println(company+"==="+age);
				System.out.println("UserDao save method running....");
		}
}
```



```xml
<bean id="userDao" class="com.itheima.dao.impl.UserDaoImpl">
		<property name="company" value="传智播客"></property>
		<property name="age" value="15"></property>
</bean>
```



集合数据类型（List<String>）的注入

```java
public class UserDaoImpl implements UserDao {
		private List<String> strList;
		public void setStrList(List<String> strList) {
				this.strList = strList;
		}
  
		public void save() {
				System.out.println(strList);
				System.out.println("UserDao save method running....");
		}
}
```



```xml
<bean id="userDao" class="com.itheima.dao.impl.UserDaoImpl">
    <property name="strList">
        <list>
            <value>aaa</value>
            <value>bbb</value>
            <value>ccc</value>
        </list>
    </property>
</bean>
```



集合数据类型（ Map<String,User> ）的注入

```java
public class UserDaoImpl implements UserDao {
		private Map<String,User> userMap;
		public void setUserMap(Map<String, User> userMap) {
				this.userMap = userMap;
		}
  
		public void save() {
				System.out.println(userMap);
				System.out.println("UserDao save method running....");
		}
}
```



```xml
<bean id="u1" class="com.itheima.domain.User"/>
<bean id="u2" class="com.itheima.domain.User"/>
<bean id="userDao" class="com.itheima.dao.impl.UserDaoImpl">
		<property name="userMap">
    		<map>
        		<entry key="user1" value-ref="u1"/>
        		<entry key="user2" value-ref="u2"/>
    		</map>
		</property>
</bean>
```



集合数据类型（Properties）的注入

```java
public class UserDaoImpl implements UserDao {
		private Properties properties;
		public void setProperties(Properties properties) {
				this.properties = properties;
		}
  
		public void save() {
				System.out.println(properties);
				System.out.println("UserDao save method running....");
		}
}
```



```xml
<bean id="userDao" class="com.itheima.dao.impl.UserDaoImpl">
    <property name="properties">
        <props>
            <prop key="p1">aaa</prop>
            <prop key="p2">bbb</prop>
            <prop key="p3">ccc</prop>
        </props>
    </property>
</bean>
```



### 3.6 引入其它配置文件（分模块开发）

实际开发中，Spring的配置内容非常多，这就导致Spring配置很繁杂且体积很大。

所以，可以将部分配置拆解到其他配置文件中，而在Spring主配置文件通过`import`标签进行加载。

```xml
<import resource="applicationContext-xxx.xml"/>
```



### 3.7 spring的重点配置

```xml
<bean>标签
    id属性:在容器中Bean实例的唯一标识，不允许重复
    class属性:要实例化的Bean的全限定名
    scope属性:Bean的作用范围，常用是Singleton(默认)和prototype
    <property>标签：属性注入
        name属性：属性名称
        value属性：注入的普通属性值
        ref属性：注入的对象引用值
        <list>标签
        <map>标签
    <properties>标签
    <constructor-arg>标签
<import>标签:导入其他的Spring的分文件
```













## 4. 相关Api

### 4.1 ApplicationContext的继承体系和实现类

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220324225117696.png)



- `ClassPathXmlApplicationContext`

它是从类的根路径下加载配置文件 推荐使用这种

- FileSystemXmlApplicationContext

它是从磁盘路径上加载配置文件，配置文件可以在磁盘的任意位置。

- AnnotationConfigApplicationContext

当使用注解配置容器对象时，需要使用此类来创建 spring 容器。它用来读取注解。



### 4.2 getBean()方法使用

```java
public Object getBean(String name) throws BeansException {
    assertBeanFactoryActive();
    return getBeanFactory().getBean(name);
}

public <T> T getBean(Class<T> requiredType) throws BeansException {
    assertBeanFactoryActive();
    return getBeanFactory().getBean(requiredType);
}
```

其中，当参数的数据类型是字符串时，表示根据Bean的`id`从容器中获得Bean实例，返回是`Object`，需要强转。

当参数的数据类型是`Class`类型时，表示根据类型从容器中匹配Bean实例，当容器中相同类型的Bean有多个时，则此方法会报错。



```java
ApplicationContext applicationContext = newClassPathXmlApplicationContext("applicationContext.xml");
UserService userService1 = (UserService) applicationContext.getBean("userService");
UserService userService2 = applicationContext.getBean(UserService.class);
```



### 4.3 spring的重点API



```java
ApplicationContext app = new ClasspathXmlApplicationContext("xml文件")
app.getBean("id")
app.getBean(Class)
```





## 5. spring配置数据源

### 5.1 数据源（连接池）的作用和手动创建

- 数据源(连接池)是提高程序性能如出现的
- 事先实例化数据源，初始化部分连接资源
- 使用连接资源时从数据源中获取
- 使用完毕后将连接资源归还给数据源



常见的数据源(连接池)：DBCP、C3P0、BoneCP、Druid等



<hr>

数据源的开发步骤：

① 导入数据源的坐标和数据库驱动坐标

导入c3p0和druid的坐标

导入mysql数据库驱动坐标

```xml
<!-- C3P0连接池 -->
<dependency>
    <groupId>c3p0</groupId>
    <artifactId>c3p0</artifactId>
    <version>0.9.1.2</version>
</dependency>
<!-- Druid连接池 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.10</version>
</dependency>
<!-- mysql驱动 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.39</version>
</dependency>
```



② 创建数据源对象

创建C3P0连接池

```java
@Test
public void testC3P0() throws Exception {
    //创建数据源
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    //设置数据库连接参数
    dataSource.setDriverClass("com.mysql.jdbc.Driver");
    dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
    dataSource.setUser("root");
    dataSource.setPassword("root");
    //获得连接对象
    Connection connection = dataSource.getConnection();
    System.out.println(connection);
}
```



创建Druid连接池

```java
@Test
public void testDruid() throws Exception {
    //创建数据源
    DruidDataSource dataSource = new DruidDataSource();
    //设置数据库连接参数
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/test");
    dataSource.setUsername("root");
    dataSource.setPassword("root");
    //获得连接对象
    Connection connection = dataSource.getConnection();
    System.out.println(connection);
}
```



③ 设置数据源的基本连接数据

提取jdbc.properties配置文件

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/test
jdbc.username=root
jdbc.password=root
```



④ 使用数据源获取连接资源和归还连接资源

读取jdbc.properties配置文件创建连接池

```java
@Test
public void testC3P0ByProperties() throws Exception {
    //加载类路径下的jdbc.properties
    ResourceBundle rb = ResourceBundle.getBundle("jdbc");
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    dataSource.setDriverClass(rb.getString("jdbc.driver"));
    dataSource.setJdbcUrl(rb.getString("jdbc.url"));
    dataSource.setUser(rb.getString("jdbc.username"));
    dataSource.setPassword(rb.getString("jdbc.password"));
    Connection connection = dataSource.getConnection();
    System.out.println(connection);
}
```



### 5.2 spring配置数据源

可以将DataSource的创建权交由Spring容器去完成

- DataSource有无参构造方法，而Spring默认就是通过无参构造方法实例化对象的
- DataSource要想使用需要通过set方法设置数据库连接信息，而Spring可以通过set方法进行字符串注入



```xml
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
    <property name="driverClass" value="com.mysql.jdbc.Driver"/>
    <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/test"/>
    <property name="user" value="root"/>
    <property name="password" value="root"/>
</bean>
```



测试从容器当中获取数据源

```java
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
Connection connection = dataSource.getConnection();
System.out.println(connection);
```



### 5.3 抽取jdbc配置文件

applicationContext.xml加载jdbc.properties配置文件获得连接信息。



首先，需要引入context命名空间和约束路径：

命名空间：
`xmlns:context="http://www.springframework.org/schema/context"`

约束路径：
`http://www.springframework.org/schema/context`
`http://www.springframework.org/schema/context/spring-context.xsd`



```xml
<context:property-placeholder location="classpath:jdbc.properties"/>
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
    <property name="driverClass" value="${jdbc.driver}"/>
    <property name="jdbcUrl" value="${jdbc.url}"/>
    <property name="user" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</bean>
```



Spring容器加载properties文件

```properties
<context:property-placeholder location="xx.properties"/>
<property name="" value="${key}"/>
```







## 6. spring注解开发

Spring是轻代码而重配置的框架，配置比较繁重，影响开发效率，所以注解开发是一种趋势，注解代替xml配置文件可以简化配置，提高开发效率。

### 6.1 原始注解

Spring原始注解主要是替代<Bean>的配置

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220324233506790.png)



注意：使用注解进行开发时，需要在`applicationContext.xml`中配置组件扫描，作用是指定哪个包及其子包下的Bean
需要进行扫描以便识别使用注解配置的类、字段和方法。

```xml
<!--注解的组件扫描-->
<context:component-scan base-package="com.itheima"></context:component-scan>
```







### 6.2 新注解

使用上面的注解还不能全部替代xml配置文件，还需要使用注解替代的配置如下：

- 非自定义的Bean的配置：<bean>
- 加载properties文件的配置：<context:property-placeholder>
- 组件扫描的配置：<context:component-scan>
- 引入其他文件：<import>



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220324233923630.png)









## 7. spring整合Junit

Spring集成Junit步骤

① 导入spring集成Junit的坐标

② 使用@Runwith注解替换原来的运行期

```java
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringJunitTest {
  
}
```





③ 使用@ContextConfiguration指定配置文件或配置类

```java
@RunWith(SpringJUnit4ClassRunner.class)
//加载spring核心配置文件
//@ContextConfiguration(value = {"classpath:applicationContext.xml"})
//加载spring核心配置类
@ContextConfiguration(classes = {SpringConfiguration.class})
public class SpringJunitTest {
}
```





④ 使用@Autowired注入需要测试的对象

⑤ 创建测试方法进行测试













<hr>

## 08 容器与Bean

### 01）容器接口





BeanFactory 能做哪些事

ApplicationContext 有哪些扩展功能

事件解耦







## 09 AOP

AOP的实现原理是什么？

代理。（回答没有错误，但是不太完整）



> AOP的三种实现：（1）代理；（2）编译器aspectj-maven-plugin在编译阶段增强；（3）类加载阶段-javaagent增强
>
> 目的主要开阔视野，AOP增强不止一种。实际开发中还是以代理为主。



## 10 WEB









## 11 Boot









## 12 其它









### 16 切点匹配

### 常见切点匹配实现

























































