# Docker

- [Docker](#docker)
  - [常用总结与问题解决](#常用总结与问题解决)
  - [一、初识Docker](#一初识docker)
    - [1、什么是Docker](#1什么是docker)
    - [2、Docker和虚拟机的区别](#2docker和虚拟机的区别)
    - [3、Docker架构](#3docker架构)
      - [镜像和容器](#镜像和容器)
      - [Docker和DockerHub](#docker和dockerhub)
      - [docker架构](#docker架构)
    - [4、安装Docker](#4安装docker)
      - [1.CentOS安装Docker](#1centos安装docker)
      - [2.CentOS7安装DockerCompose](#2centos7安装dockercompose)
  - [二、Docker的基本操作](#二docker的基本操作)
    - [1、镜像操作](#1镜像操作)
      - [镜像相关命令](#镜像相关命令)
      - [镜像操作命令](#镜像操作命令)
      - [案例：从DockerHub中拉取一个nginx镜像并查看](#案例从dockerhub中拉取一个nginx镜像并查看)
    - [2、容器操作](#2容器操作)
      - [案例：创建运行一个Nginx容器](#案例创建运行一个nginx容器)
      - [案例：进入Nginx容器，修改HTML文件内容，添加“传智教育欢迎您”](#案例进入nginx容器修改html文件内容添加传智教育欢迎您)
      - [练习：创建并运行一个redis容器，并且支持数据持久化](#练习创建并运行一个redis容器并且支持数据持久化)
      - [练习：进入redis容器，并执行redis-cli客户端命令，存入num=666](#练习进入redis容器并执行redis-cli客户端命令存入num666)
      - [3、数据卷（容器数据管理）](#3数据卷容器数据管理)
      - [操作数据卷](#操作数据卷)
      - [案例：创建一个数据卷，并查看数据卷在宿主机的目录位置](#案例创建一个数据卷并查看数据卷在宿主机的目录位置)
      - [挂载数据卷](#挂载数据卷)
      - [案例：创建一个nginx容器，修改容器内的html目录内的index.html内容](#案例创建一个nginx容器修改容器内的html目录内的indexhtml内容)
      - [案例：创建并运行一个MySQL容器，将宿主机目录直接挂载到容器](#案例创建并运行一个mysql容器将宿主机目录直接挂载到容器)
  - [三、DockerFile自定义镜像](#三dockerfile自定义镜像)
    - [1、镜像结构](#1镜像结构)
    - [2、DockerFile语法](#2dockerfile语法)
      - [[自定义镜像-Dockerfile]案例：基于Ubuntu镜像构建一个新镜像，运行一个java项目](#自定义镜像-dockerfile案例基于ubuntu镜像构建一个新镜像运行一个java项目)
      - [案例：基于java:8-alpine镜像，将一个Java项目构建为镜像](#案例基于java8-alpine镜像将一个java项目构建为镜像)
    - [3、构建Java项目（如上构建的javaweb1.0和javaweb2.0）](#3构建java项目如上构建的javaweb10和javaweb20)
  - [四、Docker-Compose](#四docker-compose)
    - [1、初识Docker-Compose](#1初识docker-compose)
      - [介绍](#介绍)
      - [安装](#安装)
    - [2、部署微服务集群](#2部署微服务集群)
      - [案例：将之前学习的cloud-demo微服务集群利用DockerCompose部署](#案例将之前学习的cloud-demo微服务集群利用dockercompose部署)
  - [五、Docker镜像仓库](#五docker镜像仓库)
    - [1、搭建私有镜像仓库](#1搭建私有镜像仓库)
      - [常见镜像仓库服务](#常见镜像仓库服务)
      - [搭建](#搭建)
      - [1）搭建简化版镜像仓库](#1搭建简化版镜像仓库)
      - [2）搭建带有图形化界面版本](#2搭建带有图形化界面版本)
      - [3）配置Docker信任地址](#3配置docker信任地址)
    - [2、向镜像仓库推送镜像](#2向镜像仓库推送镜像)
    - [3、从镜像仓库拉取镜像](#3从镜像仓库拉取镜像)
      - [在私有镜像仓库推送或拉取镜像](#在私有镜像仓库推送或拉取镜像)


## 常用总结与问题解决

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



## 一、初识Docker

### 1、什么是Docker

**项目部署的问题**

大型项目组件较多，运行环境也较为复杂，部署时会碰到一些问题：
依赖关系复杂，容易出现兼容性问题
开发、测试、生产环境有差异



**Docker如何解决依赖的兼容问题的？**

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214013817441.png" alt="image-20211214013817441" style="zoom: 33%;" />

不同环境的操作系统不同，Docker如何解决？我们先来了解下操作系统结构

> 内核与硬件交互，提供操作硬件的指令
> 系统应用封装内核指令为函数，便于程序员调用
>
> 用户程序基于系统函数库实现功能

Ubuntu和CentOS都是基于Linux内核，只是系统应用不同，提供的函数库有差异

![WechatIMG612](https://gitee.com/lemonade19/blog-img/raw/master/img/WechatIMG612.png)



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

### 2、Docker和虚拟机的区别

虚拟机（virtual machine）是在操作系统中模拟硬件设备，然后运行另一个操作系统，比如在 Windows 系统里面运行 Ubuntu 系统，这样就可以运行任意的Ubuntu应用了。

![image-20211214015419915](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214015419915.png)

> **Docker和虚拟机的差异：**
> 		**docker是一个系统进程；虚拟机是在操作系统中的操作系统**
> 		**docker体积小、启动速度快、性能好；虚拟机体积大、启动速度慢、性能一般**



### 3、Docker架构

#### 镜像和容器

**镜像（Image）：**Docker将应用程序及其所需的依赖、函数库、环境、配置等文件打包在一起，称为镜像。
**容器（Container）：**镜像中的应用程序运行后形成的进程就是容器，只是Docker会给容器做隔离，对外不可见。

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214123821346.png" alt="image-20211214123821346" style="zoom: 33%;" />



#### Docker和DockerHub

DockerHub：DockerHub是一个Docker镜像的托管平台。这样的平台称为Docker Registry。
国内也有类似于DockerHub 的公开服务，比如 网易云镜像服务、阿里云镜像库等。

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214124015659.png" alt="image-20211214124015659" style="zoom:33%;" />



#### docker架构

**Docker是一个CS架构的程序，由两部分组成：**
**服务端(server)：Docker守护进程，负责处理Docker指令，管理镜像、容器等**
**客户端(client)：通过命令或RestAPI向Docker服务端发送指令。可以在本地或远程向服务端发送指令。**

![image-20211214124457505](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214124457505.png)



> 镜像：
> 		将应用程序及其依赖、环境、配置打包在一起
> 容器：
> 		镜像运行起来就是容器，一个镜像可以运行多个容器
> Docker结构：
> 		服务端：接收命令或远程请求，操作镜像或容器
> 		客户端：发送命令或者请求到Docker服务端
> DockerHub：
> 		一个镜像托管的服务器，类似的还有阿里云镜像服务，统称为DockerRegistry



### 4、安装Docker

企业部署一般都是采用Linux操作系统，而其中又数CentOS发行版占比最多，因此我们在CentOS下安装Docker。参考课前资料中的文档。

Docker 分为 CE 和 EE 两大版本。CE 即社区版（免费，支持周期 7 个月），EE 即企业版，强调安全，付费使用，支持周期 24 个月。
Docker CE 分为 `stable` `test` 和 `nightly` 三个更新频道。
官方网站上有各种环境下的 [安装指南](https://docs.docker.com/install/)，这里主要介绍 Docker CE 在 CentOS上的安装。

#### 1.CentOS安装Docker

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

![image-20211214145853175](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214145853175.png)



**安装Docker**

首先需要大家虚拟机联网，安装yum工具

```bash
yum install -y yum-utils \
           device-mapper-persistent-data \
           lvm2 --skip-broken
```

![image-20211214150645950](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214150645950.png)

然后更新本地镜像源：（直接复制粘贴）

```bash
# 设置docker镜像源
yum-config-manager \
    --add-repo \
    https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
    
sed -i 's/download.docker.com/mirrors.aliyun.com\/docker-ce/g' /etc/yum.repos.d/docker-ce.repo

yum makecache fast
```

![image-20211214150818679](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214150818679.png)

<font color=red>然后输入命令：</font>

```bash
yum install -y docker-ce
```

docker-ce为社区免费版本。稍等片刻，docker即可安装成功。

![image-20211214151418402](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214151418402.png)

![image-20211214151446667](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214151446667.png)





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

![image-20211214151925745](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214151925745.png)



然后输入命令，可以查看docker版本：

```bash
docker -v
```

![image-20211214152122014](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214152122014.png)

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

![image-20211214152835303](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214152835303.png)



#### 2.CentOS7安装DockerCompose









## 二、Docker的基本操作

### 1、镜像操作

#### 镜像相关命令

镜像名称一般分两部分组成：[repository]:[tag]。
在没有指定tag时，默认是latest，代表最新版本的镜像

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214153317751.png" alt="image-20211214153317751" style="zoom:25%;" />



#### 镜像操作命令

![image-20211214153517303](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214153517303.png)

>会看帮助文档：
>
>docker --help
>
>docker images --help



#### 案例：从DockerHub中拉取一个nginx镜像并查看

首先去镜像仓库搜索nginx镜像，比如DockerHub:

https://registry.hub.docker.com/search?q=nginx&type=image

![image-20211214154158866](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214154158866.png)

根据查看到的镜像名称，拉取自己需要的镜像，通过命令：docker pull nginx

```sh
docker pull nginx
```

![image-20211214154509820](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214154509820.png)

通过命令：docker images 查看拉取到的镜像

![image-20211214154732316](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214154732316.png)

>以上熟悉了拉取和查看命令
>
>下面尝试保存和加载命令

**利用docker save将nginx镜像导出磁盘，然后再通过load加载回来**

步骤一：利用docker xx --help命令查看docker save和docker load的语法

步骤二：使用docker save导出镜像到磁盘 

![image-20211214155519832](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214155519832.png)

步骤三：使用docker load加载镜像

> 镜像删除、查看、加载

![image-20211214160105010](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214160105010.png)



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



### 2、容器操作

![image-20211214161316687](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214161316687.png)

#### 案例：创建运行一个Nginx容器

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

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214162310530.png" alt="image-20211214162310530" style="zoom:33%;" />

![image-20211214162517371](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214162517371.png)

![image-20211214162907310](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214162907310.png)

>查看日志

![image-20211214163001699](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214163001699.png)

> 持续日志输出

![image-20211214163226926](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214163226926.png)



> docker run命令的常见参数有哪些？
> 		--name：指定容器名称
> 		-p：指定端口映射
> 		-d：让容器后台运行
> 查看容器日志的命令：
> 		docker logs
> 		添加 -f 参数可以持续查看日志
> 查看容器状态：
> 		docker ps



#### 案例：进入Nginx容器，修改HTML文件内容，添加“传智教育欢迎您”

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

![image-20211214165429798](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214165429798.png)



> 查看容器状态：
> 		docker ps 
> 		添加-a参数查看所有状态的容器
> 删除容器：
> 		docker rm
> 		不能删除运行中的容器，除非添加 -f 参数
> 进入容器：
> 		命令是docker exec -it [容器名] [要执行的命令]
> 		exec命令可以进入容器修改文件，但是在容器内修改文件是不推荐的

![image-20211214170109661](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214170109661.png)



#### 练习：创建并运行一个redis容器，并且支持数据持久化

步骤一：到DockerHub搜索Redis镜像
步骤二：查看Redis镜像文档中的帮助信息
步骤三：利用docker run 命令运行一个Redis容器

```bash
docker run --name redis -p 6379:6379 -d redis redis-server --appendonly yes
```

![image-20211214173315655](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214173315655.png)



访问它，打开一个Redis的客户端

![image-20211214180611822](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214180611822.png)



> 事实上，快捷命令直接进入：

```bash
docker execi -t mrr edis-cli
```



#### 练习：进入redis容器，并执行redis-cli客户端命令，存入num=666

![image-20211214180732083](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214180732083.png)



#### 3、数据卷（容器数据管理）

容器与数据耦合的问题

![image-20211214211434187](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214211434187.png)

数据卷（volume）是一个虚拟目录，指向宿主机文件系统中的某个目录。

![image-20211214211815944](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214211815944.png)



#### 操作数据卷

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



#### 案例：创建一个数据卷，并查看数据卷在宿主机的目录位置

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

![image-20211214213456120](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214213456120.png)

> 数据卷的作用：
> 		将容器与数据分离，解耦合，方便操作容器内数据，保证数据安全
> 数据卷操作：
> 		docker volume create
> 		docker volume ls
> 		docker volume inspect
> 		docker volume rm
> 		docker volume prune



#### 挂载数据卷

我们在创建容器时，可以通过 -v 参数来挂载一个数据卷到某个容器目录

![image-20211214214121382](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214214121382.png)

#### 案例：创建一个nginx容器，修改容器内的html目录内的index.html内容

需求说明：上个案例中，我们进入nginx容器内部，已经知道nginx的html目录所在位置/usr/share/nginx/html ，我们需要把这个目录挂载到html这个数据卷上，方便操作其中的内容。
提示：运行容器时使用 -v 参数挂载数据卷

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214220924303.png" alt="image-20211214220924303" style="zoom: 33% float:left;" />



![image-20211214220312195](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214220312195.png)

![image-20211214220214267](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214220214267.png)

![image-20211214220340170](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214220340170.png)

> 数据卷挂载方式：
> 		-v volumeName: /targetContainerPath
> 		如果容器运行时volume不存在，会自动被创建出来



#### 案例：创建并运行一个MySQL容器，将宿主机目录直接挂载到容器

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214221907832.png" alt="image-20211214221907832" style="zoom:50%;" />

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



![image-20211214224314094](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214224314094.png)

![image-20211214224343930](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214224343930.png)



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

![image-20211214224825040](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214224825040.png)



docker run的命令中通过 -v 参数挂载文件或目录到容器中：
		-v volume名称:容器内目录
		-v 宿主机文件:容器内文件
		-v 宿主机目录:容器内目录
数据卷挂载与目录直接挂载的区别：
		数据卷挂载耦合度低，由docker来管理目录，但是目录较深，不好找
		目录挂载耦合度高，需要我们自己管理目录，不过目录容易寻找查看



## 三、DockerFile自定义镜像

> 已经学习：如何去拉取一个镜像，如何基于镜像去创建并运行容器；
>
> 自己写的微服务代码，官方不可能帮我们制作镜像，自己的微服务自己制作镜像。

### 1、镜像结构

镜像是将应用程序及其需要的系统函数库、环境、配置、依赖打包而成。

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214225525656.png" alt="image-20211214225525656" style="zoom:25%;" />

> 底层函数库、环境配置、依赖安装、应用安装、应用配置
>
> 我们应该从基础开始，逐层构建



<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214230046084.png" alt="image-20211214230046084" style="zoom: 33%;" />

> 镜像是分层结构，每一层称为一个Layer
> 		BaseImage层：包含基本的系统函数库、环境变量、文件系统
> 		Entrypoint：入口，是镜像中应用启动的命令
> 		其它：在BaseImage基础上添加依赖、安装程序、完成整个应用的安装和配置

### 2、DockerFile语法

`Dockerfile`就是一个文本文件，其中包含一个个的指令(Instruction)，用指令来说明要执行什么操作来构建镜像。每一个指令都会形成一层Layer。

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214230303095.png" alt="image-20211214230303095" style="zoom:33%;" />

更新详细语法说明，请参考官网文档： https://docs.docker.com/engine/reference/builder

#### [自定义镜像-Dockerfile]案例：基于Ubuntu镜像构建一个新镜像，运行一个java项目

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



![image-20211214231412970](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214231412970.png)

![image-20211214231444454](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214231444454.png)



![image-20211214231903459](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214231903459.png)

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

#### 案例：基于java:8-alpine镜像，将一个Java项目构建为镜像

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



![image-20211214232947091](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211214232947091.png)

> 只需要4步就可以完成。



> Dockerfile的本质是一个文件，通过指令描述镜像的构建过程
> Dockerfile的第一行必须是FROM，从一个基础镜像来构建
> 基础镜像可以是基本操作系统，如Ubuntu。也可以是其他人制作好的镜像，例如：java:8-alpine



### 3、构建Java项目（如上构建的javaweb1.0和javaweb2.0）



## 四、Docker-Compose

> 实际生产环境下，微服务的数量可是非常多的，集群部署的手段。

### 1、初识Docker-Compose

#### 介绍

![image-20211215012032022](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211215012032022.png)

DockerCompose的详细语法参考官网：https://docs.docker.com/compose/compose-file/

#### 安装

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



### 2、部署微服务集群

#### 案例：将之前学习的cloud-demo微服务集群利用DockerCompose部署

<img src="https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211215023346516.png" alt="image-20211215023346516" style="zoom: 33%;" />



![image-20211215023529096](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211215023529096.png)

查看日志：docker-compose logs -f userservice

![image-20211215023715085](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211215023715085.png)



测试：

![image-20211215024005558](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211215024005558.png)



![image-20211215024156027](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211215024156027.png)



## 五、Docker镜像仓库

### 1、搭建私有镜像仓库

#### 常见镜像仓库服务

**镜像仓库（ Docker Registry ）有公共的和私有的两种形式：**

> 公共仓库：例如Docker官方的 Docker Hub，国内也有一些云服务商提供类似于 Docker Hub 的公开服务，比如 网易云镜像服务、DaoCloud 镜像服务、阿里云镜像服务等。
> 除了使用公开仓库外，用户还可以在本地搭建私有 Docker Registry。企业自己的镜像最好是采用私有Docker Registry来实现。



#### 搭建

> 搭建镜像仓库可以基于Docker官方提供的DockerRegistry来实现。官网地址：https://hub.docker.com/_/registry

#### 1）搭建简化版镜像仓库

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

#### 2）搭建带有图形化界面版本

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



#### 3）配置Docker信任地址

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

![image-20211215030300597](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211215030300597.png)

![image-20211215030444085](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211215030444085.png)



![image-20211215030515158](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211215030515158.png)



### 2、向镜像仓库推送镜像

### 3、从镜像仓库拉取镜像

#### 在私有镜像仓库推送或拉取镜像

![image-20211215030715398](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211215030715398.png)



```
# 查看现有镜像



```

推送：

![image-20211215031243981](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211215031243981.png)



![image-20211215031312728](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211215031312728.png)



![image-20211215031418472](https://gitee.com/lemonade19/blog-img/raw/master/img/image-20211215031418472.png)

> 推送本地镜像到仓库前都必须重命名(docker tag)镜像，以镜像仓库地址为前缀
> 镜像仓库推送前需要把仓库地址配置到docker服务的daemon.json文件中，被docker信任
> 推送使用docker push命令
> 拉取使用docker pull命令

