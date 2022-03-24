



`**spring4**`官方文档

https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/spring-framework-reference/html/overview.html

`**spring5.2.19**` 官方文档

https://docs.spring.io/spring-framework/docs/5.2.19.RELEASE/spring-framework-reference/index.html





## 1. 简介





### Framework Modules框架模块

The Spring Framework consists of features organized into about 20 modules. These modules are grouped into `**Core Container**`, `**Data Access/Integration**`, `**Web**`, `**AOP (Aspect Oriented Programming)**`, `**Instrumentation**`, `**Messaging**`, and `**Test**`, as shown in the following diagram.





#### Figure 2.1. Overview of the Spring Framework

Ps:这个图来源于`**Spring4**`的`**Doc**`，我在`**Spring5**`以后的官方Doc中没有找到这个图，而且Spring5的文档的排版跟Spring4的排版有很大的区别。



![img](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1648109242400-5b6481d2-d6fc-45ec-9840-c8b33f3a0491.png)



![img](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1648110566537-f5ec2849-b148-4c00-8d64-bf54697cc0dd.png)



### 使用场景



#### **Table 2.1. Spring Framework Artifacts**

![img](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1648110013126-e5faaf5c-6f36-4029-a29e-40d590219f54.png)











## 2. 快速入门



![img](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1648111789567-632fdff0-fbb9-4c81-bc0b-c9034ea71350.png)



![img](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/1648111736784-d8be2836-5c4d-4eb7-b74e-28cdd3d83745.png)



`**spring**`程序开发步骤：

① 导入 Spring 开发的基本包坐标

② 编写 Dao 接口和实现类

③ 创建 Spring 核心配置文件

④ 在 Spring 配置文件中配置 UserDaoImpl

⑤ 使用 Spring 的 API 获得 `**Bean**` 实例





```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context</artifactId>
  <version>5.0.5.RELEASE</version>
</dependency>
```



```xml
<bean id="userDao" class="com.itheima.dao.impl.UserDaoImpl"></bean>
```



```java
@Test
public void test1(){
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    UserDao userDao = (UserDao) applicationContext.getBean("userDao");
    userDao.save();
}
```



## 3. 配置文件



### bean标签基本配置、范围配置、生命周期配置









### bean实例化3种方式







### bean依赖注入











## 4. 相关Api





### ApplicationContext的继承体系和实现类





实现

- `**ClassPathXmlApplicationContext**`

它是从类的根路径下加载配置文件 推荐使用这种



- FileSystemXmlApplicationContext

它是从磁盘路径上加载配置文件，配置文件可以在磁盘的任意位置。



- AnnotationConfigApplicationContext

当使用注解配置容器对象时，需要使用此类来创建 spring 容器。它用来读取注解。





### getBean()方法使用

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

其中，当参数的数据类型是字符串时，表示根据Bean的`**id**`从容器中获得Bean实例，返回是`**Object**`，需要强转。

当参数的数据类型是`**Class**`类型时，表示根据类型从容器中匹配Bean实例，当容器中相同类型的Bean有多个时，则此方法会报错。



```java
ApplicationContext applicationContext = newClassPathXmlApplicationContext("applicationContext.xml");
UserService userService1 = (UserService) applicationContext.getBean("userService");
UserService userService2 = applicationContext.getBean(UserService.class);
```



### spring的重点API



```java
ApplicationContext app = new ClasspathXmlApplicationContext("xml文件")
app.getBean("id")
app.getBean(Class)
```



