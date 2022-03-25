



## 一、Spring 整体

### 什么是 Spring Framework？





### Spring Framework 中有多少个模块，它们分别是什么？

### 使用 Spring 框架能带来哪些好处？

### Spring 框架中都用到了哪些设计模式？









## 二、Spring IoC

### 什么是 Spring IoC 容器？

### 什么是依赖注入？

### IoC 和 DI 有什么区别？

### 可以通过多少种方式完成依赖注入？

### Spring 中有多少种 IoC 容器？

Spring 提供了两种( 不是“个” ) IoC 容器，分别是 BeanFactory、ApplicationContext 。

**BeanFactory**

> BeanFactory 在 `spring-beans` 项目提供。

BeanFactory ，就像一个包含 Bean 集合的工厂类。它会在客户端要求时实例化 Bean 对象。



**ApplicationContext**

> ApplicationContext 在 `spring-context` 项目提供。

ApplicationContext 接口扩展了 BeanFactory 接口，它在 BeanFactory 基础上提供了一些额外的功能。内置如下功能：

- MessageSource ：管理 message ，实现国际化等功能。
- ApplicationEventPublisher ：事件发布。
- ResourcePatternResolver ：多资源加载。
- EnvironmentCapable ：系统 Environment（profile + Properties）相关。
- Lifecycle ：管理生命周期。
- Closable ：关闭，释放资源
- InitializingBean：自定义初始化。
- BeanNameAware：设置 beanName 的 Aware 接口。



另外，ApplicationContext 会自动初始化非懒加载的 Bean 对象们。

详细的内容，感兴趣的胖友，可以看看 [《【死磕 Spring】—— ApplicationContext 相关接口架构分析》](http://svip.iocoder.cn/Spring/ApplicationContext/) 一文。源码之前无秘密。简单总结下 BeanFactory 与 ApplicationContext 两者的差异：



> 可能很多胖友没看过源码，所以会比较难。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220325150047943.png)



另外，BeanFactory 也被称为**低级**容器，而 ApplicationContext 被称为**高级**容器。



### 请介绍下常用的 BeanFactory 容器？

BeanFactory 最常用的是 XmlBeanFactory 。它可以根据 XML 文件中定义的内容，创建相应的 Bean。



### 请介绍下常用的 ApplicationContext 容器？

以下是三种较常见的 ApplicationContext 实现方式：

- 1、ClassPathXmlApplicationContext ：从 ClassPath 的 XML 配置文件中读取上下文，并生成上下文定义。应用程序上下文从程序环境变量中取得。示例代码如下：

  ```
  ApplicationContext context = new ClassPathXmlApplicationContext(“bean.xml”);
  ```

- 2、FileSystemXmlApplicationContext ：由文件系统中的XML配置文件读取上下文。示例代码如下：

  ```
  ApplicationContext context = new FileSystemXmlApplicationContext(“bean.xml”);
  ```

- 3、XmlWebApplicationContext ：由 Web 应用的XML文件读取上下文。例如我们在 Spring MVC 使用的情况。

当然，目前我们更多的是使用 Spring Boot 为主，所以使用的是第四种 ApplicationContext 容器，ConfigServletWebServerApplicationContext。







### 列举一些 IoC 的一些好处？

- 它将最小化应用程序中的代码量。
- 它以最小的影响和最少的侵入机制促进松耦合。
- 它支持即时的实例化和延迟加载 Bean 对象。
- 它将使您的应用程序易于测试，因为它不需要单元测试用例中的任何单例或 JNDI 查找机制。







### 简述 Spring IoC 的实现机制？

简单来说，Spring 中的 IoC 的实现原理，就是**工厂模式**加**反射机制**。代码如下：

```java
interface Fruit {

     public abstract void eat();
     
}
class Apple implements Fruit {

    public void eat(){
        System.out.println("Apple");
    }
    
}
class Orange implements Fruit {
    public void eat(){
        System.out.println("Orange");
    }
}

class Factory {

    public static Fruit getInstance(String className) {
        Fruit f = null;
        try {
            f = (Fruit) Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
    
}

class Client {

    public static void main(String[] args) {
        Fruit f = Factory.getInstance("io.github.dunwu.spring.Apple");
        if(f != null){
            f.eat();
        }
    }
    
}
```

- Fruit 接口，有 Apple 和 Orange 两个实现类。
- Factory 工厂，通过反射机制，创建 `className` 对应的 Fruit 对象。
- Client 通过 Factory 工厂，获得对应的 Fruit 对象。
- 😈 实际情况下，Spring IoC 比这个复杂很多很多，例如单例 Bean 对象，Bean 的属性注入，相互依赖的 Bean 的处理，以及等等。



在基友 [《面试问烂的 Spring IoC 过程》](http://www.iocoder.cn/Fight/Interview-poorly-asked-Spring-IOC-process-1/) 的文章中，把 Spring IoC 相关的内容，讲的非常不错。



### Spring 框架中有哪些不同类型的事件？

Spring 的 ApplicationContext 提供了支持事件和代码中监听器的功能。

我们可以创建 Bean 用来监听在 ApplicationContext 中发布的事件。如果一个 Bean 实现了 ApplicationListener 接口，当一个ApplicationEvent 被发布以后，Bean 会自动被通知。示例代码如下：

```java
public class AllApplicationEventListener implements ApplicationListener<ApplicationEvent> {  
    
    @Override  
    public void onApplicationEvent(ApplicationEvent applicationEvent) {  
        // process event  
    }
    
}
```



Spring 提供了以下五种标准的事件：

1. 上下文更新事件（ContextRefreshedEvent）：该事件会在ApplicationContext 被初始化或者更新时发布。也可以在调用ConfigurableApplicationContext 接口中的 <font color=red>`#refresh()` </font>方法时被触发。
2. 上下文开始事件（ContextStartedEvent）：当容器调用ConfigurableApplicationContext 的 `#start()` 方法开始/重新开始容器时触发该事件。
3. 上下文停止事件（ContextStoppedEvent）：当容器调用 ConfigurableApplicationContext 的 `#stop()` 方法停止容器时触发该事件。
4. 上下文关闭事件（ContextClosedEvent）：当ApplicationContext 被关闭时触发该事件。容器被关闭时，其管理的所有单例 Bean 都被销毁。
5. 请求处理事件（RequestHandledEvent）：在 We b应用中，当一个HTTP 请求（request）结束触发该事件。















## 三、Spring Bean

### 1. 什么是 Spring Bean ？

- Bean 由 Spring IoC 容器实例化，配置，装配和管理。
- Bean 是基于用户提供给 IoC 容器的配置元数据 Bean Definition 创建。



这个问题，胖友可以在回过头看 [「什么是 Spring IoC 容器？」](http://svip.iocoder.cn/Spring/Interview/#) 问题，相互对照。

### 2. Spring 有哪些配置方式

单纯从 Spring Framework 提供的方式，一共有三种：



- 1、XML 配置文件。

Bean 所需的依赖项和服务在 XML 格式的配置文件中指定。这些配置文件通常包含许多 bean 定义和特定于应用程序的配置选项。它们通常以 bean 标签开头。例如：



- 2、注解配置。

您可以通过在相关的类，方法或字段声明上使用注解，将 Bean 配置为组件类本身，而不是使用 XML 来描述 Bean 装配。默认情况下，Spring 容器中未打开注解装配。因此，您需要在使用它之前在 Spring 配置文件中启用它。例如：

```xml
<beans>
<context:annotation-config/>
<!-- bean definitions go here -->
</beans>
```

- 3、Java Config 配置。

Spring 的 Java 配置是通过使用 @Bean 和 @Configuration 来实现。

- `@Bean` 注解扮演与 `<bean />` 元素相同的角色。
- `@Configuration` 类允许通过简单地调用同一个类中的其他 `@Bean` 方法来定义 Bean 间依赖关系。

```java
@Configuration
public class StudentConfig {
    
    @Bean
    public StudentBean myStudent() {
        return new StudentBean();
    }
    
}
```



目前主要使用 **Java Config** 配置为主。当然，三种配置方式是可以混合使用的。例如说：

- Dubbo 服务的配置，喜欢使用 XML 。
- Spring MVC 请求的配置，喜欢使用 `@RequestMapping` 注解。
- Spring MVC 拦截器的配置，喜欢 Java Config 配置。

<hr>

另外，现在已经是 Spring Boot 的天下，所以更加是 **Java Config** 配置为主。





### 3. Spring 支持几种 Bean Scope ？

> 这个是一个比较小众的题目，简单了解即可。



Spring Bean 支持 5 种 Scope ，分别如下：

- Singleton - 每个 Spring IoC 容器仅有一个单 Bean 实例。**默认**
- Prototype - 每次请求都会产生一个新的实例。
- Request - 每一次 HTTP 请求都会产生一个新的 Bean 实例，并且该 Bean 仅在当前 HTTP 请求内有效。
- Session - 每一个的 Session 都会产生一个新的 Bean 实例，同时该 Bean 仅在当前 HTTP Session 内有效。
- Application - 每一个 Web Application 都会产生一个新的 Bean ，同时该 Bean 仅在当前 Web Application 内有效。

> 另外，网络上很多文章说有 Global-session 级别，它是 Portlet 模块独有，目前已经废弃，在 Spring5 中是找不到的。



仅当用户使用支持 Web 的 ApplicationContext 时，**最后三个才可用**。

再补充一点，开发者是可以**自定义** Bean Scope ，具体可参见 [《Spring（10）—— Bean 作用范围（二）—— 自定义 Scope》](https://blog.csdn.net/elim168/article/details/75581670) 。

不错呢，还是那句话，这个题目简单了解下即可，实际常用的只有 Singleton 和 Prototype 两种级别，甚至说，只有 Singleton 级别。😈



### 4. Spring Bean 在容器的生命周期是什么样的？

> 这是一个比较高级的 Spring 的面试题，非常常见，并且答对比较加分。当然，如果实际真正弄懂，需要对 Spring Bean 的源码，有比较好的理解，所以 [《精尽 Spring 源码》](http://svip.iocoder.cn/categories/Spring/) 系列，该读还是读吧。





> 要注意下面每段话，进行加粗的地方。



Spring Bean 的**初始化**流程如下：

- 实例化 Bean 对象

  - Spring 容器根据配置中的 Bean Definition(定义)中**实例化** Bean 对象。

    > Bean Definition 可以通过 XML，Java 注解或 Java Config 代码提供。

  - Spring 使用依赖注入**填充**所有属性，如 Bean 中所定义的配置。

- Aware 相关的属性，注入到 Bean 对象

  - 如果 Bean 实现 **BeanNameAware** 接口，则工厂通过传递 Bean 的 beanName 来调用 `#setBeanName(String name)` 方法。
  - 如果 Bean 实现 **BeanFactoryAware** 接口，工厂通过传递自身的实例来调用 `#setBeanFactory(BeanFactory beanFactory)` 方法。

- 调用相应的方法，进一步初始化 Bean 对象

  - 如果存在与 Bean 关联的任何 **BeanPostProcessor** 们，则调用 `#preProcessBeforeInitialization(Object bean, String beanName)` 方法。
  - 如果 Bean 实现 **InitializingBean** 接口，则会调用 `#afterPropertiesSet()` 方法。
  - 如果为 Bean 指定了 **init** 方法（例如 `<bean />` 的 `init-method` 属性），那么将调用该方法。
  - 如果存在与 Bean 关联的任何 **BeanPostProcessor** 们，则将调用 `#postProcessAfterInitialization(Object bean, String beanName)` 方法。





### 5. 什么是 Spring 的内部 bean？





### 6. 什么是 Spring 装配？





### 7. 解释什么叫延迟加载？

### 8. Spring 框架中的单例 Bean 是线程安全的么？

### 9. Spring Bean 怎么解决循环依赖的问题？







## 四、Spring 注解

什么是基于注解的容器配置？
如何在 Spring 中启动注解装配？
@Component, @Controller, @Repository, @Service 有何区别？
@Required 注解有什么用？
@Autowired 注解有什么用？
@Qualifier 注解有什么用？





## 五、Spring AOP

什么是 AOP ？
什么是 Aspect ？
什么是 JoinPoint ?
什么是 PointCut ？
关于 JoinPoint 和 PointCut 的区别
什么是 Advice ？
什么是 Target ？
AOP 有哪些实现方式？
Spring AOP and AspectJ AOP 有什么区别？
什么是编织（Weaving）？
Spring 如何使用 AOP 切面？







## 六、Spring Transaction

什么是事务？
事务的特性指的是？
列举 Spring 支持的事务管理类型？
Spring 事务如何和不同的数据持久层框架做集成？
为什么在 Spring 事务中不能切换数据源？
@Transactional 注解有哪些属性？如何使用？
什么是事务的隔离级别？分成哪些隔离级别？
什么是事务的传播级别？分成哪些传播级别？
什么是事务的超时属性？
什么是事务的只读属性？
什么是事务的回滚规则？
简单介绍 TransactionStatus ？

### 使用 Spring 事务有什么优点？

1. 通过 PlatformTransactionManager ，为不同的数据层持久框架提供统一的 API ，无需关心到底是原生 JDBC、Spring JDBC、JPA、Hibernate 还是 MyBatis 。
2. 通过使用声明式事务，使业务代码和事务管理的逻辑分离，更加清晰。

从倾向上来说，比较喜欢**注解** + 声明式事务。





## 七、Spring Data Access

> 这块的问题，感觉面试问的不多，至少我很少问。哈哈哈。就当做下了解，万一问了呢。



### Spring 支持哪些 ORM 框架？

- Hibernate
- JPA
- MyBatis
- [JDO](https://docs.spring.io/spring/docs/3.0.0.M4/reference/html/ch13s04.html)
- [OJB](https://db.apache.org/ojb/docu/howtos/howto-use-spring.html)



可能会有胖友说，不是应该还有 Spring JDBC 吗。注意，Spring JDBC 不是 ORM 框架。



### 在 Spring 框架中如何更有效地使用 JDBC ？

Spring 提供了 Spring JDBC 框架，方便我们使用 JDBC 。

对于开发者，只需要使用 `JdbcTemplate` 类，它提供了很多便利的方法解决诸如把数据库数据转变成基本数据类型或对象，执行写好的或可调用的数据库操作语句，提供自定义的数据错误处理。

没有使用过的胖友，可以看看 [《Spring JDBC 访问关系型数据库》](https://www.tianmaying.com/tutorial/spring-jdbc-data-accessing) 文章。



### Spring 数据数据访问层有哪些异常？

通过使用 Spring 数据数据访问层，它统一了各个数据持久层框架的不同异常，统一进行提供 

`org.springframework.dao.DataAccessException` 异常及其子类。如下图所示：







### 使用 Spring 访问 Hibernate 的方法有哪些？

我们可以通过两种方式使用 Spring 访问 Hibernate：

- 使用 Hibernate 模板和回调进行控制反转。
- 扩展 HibernateDAOSupport 并应用 AOP 拦截器节点。



当然，我们可以再来看一道 [《JPA 规范与 ORM 框架之间的关系是怎样的呢？》](https://www.cnblogs.com/xiaoheike/p/5150553.html) 。这个问题，我倒是问过面试的候选人，哈哈哈哈。



## 八、最后

参考与推荐如下文章：

<hr>



- Java 架构 [《Spring 面试题》](https://yq.aliyun.com/articles/669702)
- 永顺 [《彻底征服 Spring AOP 之理论篇》](https://segmentfault.com/a/1190000007469968)
- 陌上桑花开花 [《Java 面试题集（七）– Spring常见面试问题》](https://blog.csdn.net/u014079773/article/details/52453002)
- 一人浅醉 [《Spring 的 @Transactional 注解详细用法》](https://www.cnblogs.com/yepei/p/4716112.html)
- dalaoyang [《Spring 面试题》](https://juejin.im/post/5b065000f265da0de45235e6)























