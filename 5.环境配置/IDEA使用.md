---
title: IDEA使用
abbrlink: 91c5
date: 2022-02-25 09:09:34
tags:
---







## 常用快捷键

```bash
# 替换
command + r

# 全局搜索（太好用了叭！！！）
command + shift + f
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220226165529372.png)









## 类和方法的注释



### 新建文件时生成模板注释

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220225173221679.png)



### 方法注释模板

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220225174526490.png)



```bash
**
 * @description $description$
 * @author Lemonade 
 $param$
 * @updateTime $date$ $TIME$ $return$
 */
```



内容变量如下：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220225174622297.png)



```bash
groovyScript("def result=''; def params=\"${_1}\".replaceAll('[\\\\[|\\\\]|\\\\s]', '').split(',').toList(); for(i = 0; i < params.size(); i++) {result+='* @param: ' + params[i] + ((i < params.size() - 1) ? '\\n ' : '')};return result", methodParameters())
```



```bash
groovyScript("def result=''; def data=\"${_1}\"; def stop=false; if(data==null || data=='null' || data=='' || data=='void' ) { stop=true; }; if(!stop) { result += '\\r\\n' + ' * @return: ' + data; }; return result;", methodReturnType())
```



## 插件

### 无限试用30天 ide-eval-reset

```bash
https://plugins.zhile.io/files/ide-eval-resetter-2.1.6.zip
```









## springboot项目启动问题（时隔一年，终于解决）

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220303092821111.png)



> 参考：https://blog.csdn.net/qq_44695727/article/details/106296294



> - 找到报错的仓库位置，复制一份，解压一份
>
> - 打开MANIFEST.MF文件，删除Class-Path（这个指向的就是找不到的那几个），再打包回jar
>
> 打jar命令：$ jar cvf jaxb-impl-2.1.13.jar .
>
> - 替换本地仓库原来的jar包



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220303095046901.png)



















