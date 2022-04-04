



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









