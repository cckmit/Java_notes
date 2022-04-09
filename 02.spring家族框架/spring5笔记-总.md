

## 写在前面

### 认知

- 整个Java体系最核心的框架，没有之一	

- 面试必备 / 热点（从Spring的核心原理，到SpringMVC的执行流程，再到SpringBoot的自动配置）

- 技术、思想提升（大量设计模式的应用和体现）（应用了很多技术）



<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220408211450426.png)





### 总结



#### 容器与bean（01-08）





#### AOP（09-19）









#### Web MVC（20-36）



#### Spring Boot（37-42）







#### 其它（43-49）







### 参考与推荐如下资料





<br>



`spring4`官方文档

https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/spring-framework-reference/html/overview.html

`spring5.2.19` 官方文档

https://docs.spring.io/spring-framework/docs/5.2.19.RELEASE/spring-framework-reference/index.html

<br>

[黑马220323 Spring高级课程](https://www.bilibili.com/video/BV1P44y1N7QG?spm_id_from=333.999.0.0)







<hr>

## 1. 简介（这块内容来自官网）





### 1.1 Framework Modules框架模块

The Spring Framework consists of features organized into about 20 modules. These modules are grouped into `Core Container`, `Data Access/Integration`, `Web`, `AOP (Aspect Oriented Programming)`, `Instrumentation`, `Messaging`, and `Test`, as shown in the following diagram.



​	

**spring4**

> 注：这个图来源于`Spring4`的`Doc`，我在`Spring5`以后的官方Doc中没有找到这个图，而且Spring5的文档的排版跟Spring4的排版有很大的区别。



> 
>
> ![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1648109242400-5b6481d2-d6fc-45ec-9840-c8b33f3a0491.png?w=600)

**spring5**

> ![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1648110566537-f5ec2849-b148-4c00-8d64-bf54697cc0dd.png?w=600)



### 1.2 使用场景



**Table 2.1. Spring Framework Artifacts**

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1648110013126-e5faaf5c-6f36-4029-a29e-40d590219f54.png?w=900)











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

### 容器接口

BeanFactory 能做哪些事

ApplicationContext 有哪些扩展功能

事件解耦







## 09 AOP

AOP的实现原理是什么？

代理。（回答没有错误，但是不太完整）



> AOP的三种实现：（1）代理；（2）编译器aspectj-maven-plugin在编译阶段增强；（3）类加载阶段-javaagent增强
>
> 目的主要开阔视野，AOP增强不止一种。实际开发中还是以代理为主。







### 16 切点匹配

### 常见切点匹配实现

























































