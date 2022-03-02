





> 定位：
>
> - 小白
>   - 初步掌握SpringBoot程序的开发流程，能够基于SpringBoot实现基础的SSM框架整合。
> - 初学者
>   - 掌握各种第三方技术与SpringBoot整合的方案。
>   - 积累基于SpringBoot的实战开发经验。
> - 开发者
>   - 提升对Spring和SpringBoot原理的理解层次。
>   - 基于原理理解基础上，实现整合任意技术。



> 技术点：
>
> - 能够创建SpringBoot工程；
> - 基于SpringBoot实现ssm整合；
>
> 
>
> - 能够掌握SpringBoot应用多环境开发；
> - 能够基于Linux系统发布SpringBoot应用；
> - 能够解决线上灵活配置SpringBoot应用的需求；
> - 能够基于SpringBoot整合任意第三方技术；
>
> 
>
> - 掌握SpringBoot内部工作流程；
> - 理解SpringBoot整合第三方技术的原理；
> - 实现自定义开发整合第三方技术的组件；



<hr>



## 基础篇



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302124415188.png)



> SpringBoot是由Pivotal团队提供的全新框架，其设计目的是用来<font color=red>简化</font>Spring应用的<font color=red>初始搭建</font>以及<font color=red>开发过程</font>



> Spring程序缺点
> - 依赖设置繁琐
> - 配置繁琐
>
> SpringBoot程序优点
> - 起步依赖（简化依赖配置）
> - 自动配置（简化常用工程相关配置）
> - 辅助功能（内置服务器，……）



### 1. 四种创建方式：

> 基于Idea创建SpringBoot工程
> 基于官网创建SpringBoot工程
> 基于阿里云创建SpringBoot工程
> 手工创建Maven工程修改为SpringBoot工程



#### 1.1基于IDEA

①：创建新模块，选择Spring Initializr，并配置模块相关基础信息

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302125636716.png)



②：选择当前模块需要使用的技术集

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302125704010.png)



③：开发控制器类

```java
//Rest模式
@RestController
@RequestMapping("/books")
public class BookController {
    @GetMapping
    public String getById(){
        System.out.println("springboot is running...");
        return "springboot is running..."; 
    } 
}
```



④：运行自动生成的Application类

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302125857236.png)



> 最简SpringBoot程序所包含的基础文件：
>
> - pom.xml文件
> - Application类



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302130025852.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302130040514.png)



> Spring程序与SpringBoot程序对比



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302130152848.png)

注：基于idea开发SpringBoot程序需要确保联网且能够加载到程序框架结构。





#### 1.2 基于官网：https://start.spring.io/

> 打开SpringBoot官网，选择Quickstart Your Project
> 创建工程，并保存项目
> 解压项目，通过IDE导入项目



#### 1.3 基于阿里云：https://start.aliyun.com

> 阿里云提供的坐标版本较低，如果需要使用高版本，进入工程后手工切换SpringBoot版本
> 阿里云提供的工程模板与Spring官网提供的工程模板略有不同

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302131047519.png)



#### 1.4 手工创建Maven工程修改为SpringBoot工程

> 1. 创建普通Maven工程
> 2. 继承spring-boot-starter-parent
> 3. 添加依赖spring-boot-starter-web
> 4. 制作引导类Application



### 2. 隐藏指定文件/文件夹

> Idea中隐藏指定文件或指定类型文件
> - Setting → File Types → Ignored Files and Folders
> - 输入要隐藏的文件名，支持*号通配符
> - 回车确认添加



### 3. 初步解析



#### parent

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302151722177.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302152433847.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302152545767.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220302152642782.png)



> 仅定义未使用



> 1. 开发SpringBoot程序要继承spring-boot-starter-parent
> 2. spring-boot-starter-parent中定义了若干个依赖管理
> 3. 继承parent模块可以避免多个依赖使用相同技术时出现依赖版本冲突
> 4. 继承parent的形式也可以采用引入依赖的形式实现效果



#### starter











#### 引导类

#### 内嵌tomcat





























 

## 实用篇之运维实用篇









 

## 实用篇之开发使用篇

 

## 原理篇







































