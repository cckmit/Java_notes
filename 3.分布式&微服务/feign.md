
# Netflix Feign or Open Feign？

## 前言
说到HTTP客户端，Java自己源生的就有`java.net`包下的`HttpURLConnection`（虽然不太好用），同时优秀的开源产品更是百花齐放：
- Apache HttpClient
- OkHttp
- Spring的RestTemplate
- Feign

> feign的中文意思：假装，装作，捏造，想象



## 版本声明

## 官网介绍

## 使用实例

## Netflix Feign还是Open Feign？
首先需要明确：

在这个时间节点上，很多人对这“两种”Feign傻傻分不清楚，不知有何区别和联系，本文将给与告知。
首先需要明确：他俩是属于同一个东西， Feign属于Netflix开源的组件。针对于于差异性，下面分这几个方面展开做出对比
1、 GAV坐标差异
1  
feign-cores/artifactId> dependency
寸


 8
cartifactId>feign-cores/artifactId> 
2、官网地址差异：
https://github.com/Netflix/feign和https://github.com/0penFeign/feign。不过现在访问 https：//github.com/Netflix/feign已经被重定向到了后者上。
3、发版历史
. Netfix Feign： 1.0.0发布于2013.6，于2016.7月发布其最后一个版本8.18.0
. Open Feign：首个版本便是9.0.0版，于2016.7月发布，然后一直持续发布到现在（未停止）
从以上3个特点其实你可以很清楚的看到两者的区别和联系，简单的理解： Netflix Feign仅仅只是改名成为了Open Feign而已，然后Open Feign项目在其基础上继续发展至今。
9.0版本之前它叫Nettix Feign， 自9.0版本起它改名叫Open Feign了。
但是请注意，虽然GAV完全变了，但是源码的包名和核心API是没有任何变化的，所以扔具有很好的向下兼容性（并不是100%向下兼容）。




## 总结









