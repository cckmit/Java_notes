



# 基础篇

## 前面

MySQL 是一款非常流行的数据库管理系统

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220330133909080.png?w=600)









### 面试题



```bash
-- 基础篇
什么是事务,以及事务的四大特性? 
事务的隔离级别有哪些,MySQL默认是哪个? 
内连接与左外连接的区别是什么? 

-- 进阶篇
常用的存储引擎？InnoDB与MyISAM的区别？
MySQL默认InnoDB引擎的索引是什么数据结构? 
如何查看MySQL的执行计划? 
索引失效的情况有哪些? 
什么是回表查询? 
什么是MVCC?

-- 运维篇
MySQL主从复制的原理是什么? 
主从复制之后的读写分离如何实现? 
数据库的分库分表如何实现?
```



### 课程规划

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220330134008703.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220330133948392.png)



- 初级工程师
- 中级工程师
- 高级工程师



## 1. 概述（介绍、下载、安装）

### 介绍

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220330225654238.png)



**主流的关系型数据库管理系统**

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220330230208197.png)



### 下载和安装

```bash
-- 版本
社区版（MySQL Community Server）：免费，MySQL不提供任何技术支持
商业版（MySQL Enterprise Edition）：收费，可以试用30天，官方提供技术支持

本课程采用的是MySQL的最新社区版（MySQL Community Server 8.0.26）。
```



```bash
-- 下载（MSI Installer）
https://dev.mysql.com/downloads/windows/installer/8.0.html
https://downloads.mysql.com/archives/community/
-- 安装

```



```bash
-- 启动、停止(mysql80是注册到系统服务的服务名称)
net start mysql80
net stop mysql80
-- 连接
（1）MySQL提供的客户端命令行工具
（2）系统自带的命令行工具执行指令（使用这种方式时，需要配置PATH环境变量。）
mysql [-h 127.0.0.1][-p 3306] -u root -p
```



**关系模型**

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220330232509495.png)





## 2. SQL（操作MySQL的语言）（核心部分）

关键字建议使用大写

SQL分类

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220330233114823.png)



### DDL（库表字段定义）

- 库操作

```sql
-- 查询所有数据库
show databases;
-- 使用
use 数据库名;

-- 查询当前数据库
select database();
-- 创建
create database [if not exists] 数据库名 [default charset 字符集] [collate 排序规则]; 
-- 删除
drop database [if exists] 数据库名;
```



- 操作

```sql
-- 查询当前数据库所有表
show tables;
-- 查询表结构
desc 表名;
-- 查询指定表的建表语句
show create table 表名;

-- 创建表
create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    username     varchar(256)                       null comment '用户名',
    userAccount  varchar(256)                       null comment '登录账号',
    avatarUrl    varchar(256)                       null comment '头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       null comment '密码',
    phone        varchar(128)                       null comment '电话',
    email        varchar(128)                       null comment '邮箱',
    userStatus   int      default 1                 not null comment '状态 0 - 正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDeleted    tinyint  default 0                 not null comment '是否逻辑删除',
    userRole     int      default 0                 null comment '角色 0-普通用户 1-管理员'
)
    comment '用户表';

-- 添加字段
alter table 表名 add 字段名 类型（长度）[comment 注释][约束]
// 为emp表增加一个新的字段”昵称”为nickname，类型为varchar(20)
alter table emp add nickname varchar(20) comment '昵称';

-- 修改数据类型
alter table 表名 modify 字段名 新数据类型(长度);
-- 修改字段名和字段类型
alter table 表名 change 旧字段名 新字段名 类型(长度)[comment 注释][约束];
// 将emp表的nickname字段修改为username，类型为varchar(30)
alter table emp change nickname username varchar(30) comment '昵称';

-- 删除字段
alter table 表名 drop 字段名;
-- 修改表名
alter table 表名 rename to 新表名;
-- 删除表
drop table [if exists] 表名;
-- 删除指定表，并重新创建该表
truncate table 表名;
// 注意：在删除表时，表中的全部数据也会被删除。
```



MySQL中的数据类型有很多，主要分为三类：数值类型、字符串类型、日期时间类型。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331110917846.png?w=600)



![image-20220331110934767](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331110934767.png)



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331110956441.png)



```sql
根据需求创建表(设计合理的数据类型、长度)	

设计一张员工信息表，要求如下：
1. 编号（纯数字）
2. 员工工号 (字符串类型，长度不超过10位)
3. 员工姓名（字符串类型，长度不超过10位）
4. 性别（男/女，存储一个汉字）
5. 年龄（正常人年龄，不可能存储负数）
6. 身份证号（二代身份证号均为18位，身份证中有X这样的字符）
7. 入职时间（取值年月日即可）
```



**MySQL图形化界面**

![image-20220331112151427](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331112151427.png)



### DML（表的增删改）

- 添加数据（INSERT） 
- 修改数据（UPDATE） 
- 删除数据（DELETE）



```sql
insert into 表名 (字段名1,字段名2,...) values (值1,值2,...);
insert into 表名 values (值1,值2,...);

-- 批量添加
insert into 表名 (字段名1,字段名2,...) values (值1,值2,...),(值1,值2,...),(值1,值2,...);
insert into 表名 values (值1,值2,...),(值1,值2,...),(值1,值2,...);
// 字符串和日期型数据应该包含在引号中。

-- 修改数据
update 表名 set 字段名1=值1,字段名2=值2,...[where 条件];
// 注意：修改语句的条件可以有，也可以没有，如果没有条件，则会修改整张表的所有数据。

-- 删除数据
delete from 表名 [where 条件];
// 注意：DELETE 语句的条件可以有，也可以没有，如果没有条件，则会删除整张表的所有数据。
```



### DQL（表的查询）

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331113108224.png)



```sql
-- 基本查询
select 字段1 [as 别名1],字段2 [as 别名2],字段3 [as 别名3],... from 表名;
select * from 表名;
select distinct 字段列表 from 表名;
// 注意 : * 号代表查询所有字段，在实际开发中尽量少用（不直观、影响效率）。

-- 条件查询
select 字段列表 from 表名 where 条件列表;
```

![image-20220331113523084](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331113523084.png)



```sql
-- 聚合函数：将一列数据作为一个整体，进行纵向计算 。
count: 统计数量
max: 最大值
min: 最小值
avg: 平均值
sum: 求和

select 聚合函数 (字段列表) from 表名;
// 注意 : null值不参与所有聚合函数运算。

-- 分组查询 group by
select 字段列表 from 表名 [where 条件] group by 分组字段名 [having 分组后过滤条件];	
// where与having区别
// 执行时机不同：where是分组之前进行过滤，不满足where条件，不参与分组；而having是分组之后对结果进行过滤。
// 判断条件不同：where不能对聚合函数进行判断，而having可以。
// 执行顺序: where > 聚合函数 > having 。
// 分组之后，查询的字段一般为聚合函数和分组字段，查询其他字段无任何意义。
```



```sql
-- 排序查询 order by
select 字段列表 from 表名 order by 字段1 排序方式1, 字段2 排序方式2;
// ASC：升序 （默认值）
// DESC：降序
// 注意：如果是多字段排序，当第一个字段值相同时，才会根据第二个字段进行排序。
-- 分页查询 limit
select 字段列表 from 表名 limit 起始索引 查询记录数;
// 起始索引从0开始，起始索引 = （查询页码 - 1）* 每页显示记录数。
// 分页查询是数据库的方言，不同的数据库有不同的实现，MySQL中是LIMIT。
// 如果查询的是第一页数据，起始索引可以省略，直接简写为 limit 10。
```



```sql
// 练习
1. 查询年龄为20,21,22,23岁的员工信息。
2. 查询性别为 男 ，并且年龄在 20-40 岁(含)以内的姓名为三个字的员工。
3. 统计员工表中, 年龄小于60岁的 , 男性员工和女性员工的人数。
4. 查询所有年龄小于等于35岁员工的姓名和年龄，并对查询结果按年龄升序排序，如果年龄相同按入职时间降序排序。
5. 查询性别为男，且年龄在20-40 岁(含)以内的前5个员工信息，对查询的结果按年龄升序排序，年龄相同按入职时间升序排序。
```



执行顺序

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331114556898.png)





### DCL（访问权限控制）

#### 用户管理

```sql
-- 查询用户
use mysql;
select * from user;

-- 创建用户
create user '用户名'@'主机名' identified by '密码';

-- 修改用户密码
alter user '用户名'@'主机名' identified with mysql_native_password by '新密码'; 

-- 删除用户
drop user '用户名'@'主机名'
// 主机名可以使用 % 通配。
// 这类SQL开发人员操作的比较少，主要是DBA（ Database Administrator 数据库管理员）使用。
```



#### 权限控制

MySQL中定义了很多种权限，但是常用的就以下几种：

| 权限                | 说明               |
| ------------------- | ------------------ |
| ALL, ALL PRIVILEGES | 所有权限           |
| SELECT              | 查询数据           |
| INSERT              | 插入数据           |
| UPDATE              | 修改数据           |
| DELETE              | 删除数据           |
| ALTER               | 修改表             |
| DROP                | 删除数据库/表/视图 |
| CREATE              | 创建数据库/表      |



```sql
-- 查询权限
show grants for '用户名'@'主机名';
-- 授予权限
grant 权限列表 on 数据库名.表名 to '用户名'@'主机名';
-- 撤销权限
revoke 权限列表 on 数据库名.表名 from '用户名'@'主机名';
// 多个权限之间，使用逗号分隔
// 授权时， 数据库名和表名可以使用 * 进行通配，代表所有。
```





## 3. 函数（功能强大的内置函数、其应用场景）

是指一段可以直接被另一段程序调用的程序或代码。

### 字符串函数

| 函数                       | 功能                                                         |
| -------------------------- | ------------------------------------------------------------ |
| concat (S1,S2,...Sn)       | 字符串拼接，将 S1，S2 ，...Sn 拼接成一个字符串               |
| lower(str)                 | 将字符串 str 全部转为小写                                    |
| upder(str)                 | 将字符串 str 全部转为大写                                    |
| lpad(str, n ,pad)          | 左填充，用字符串 pad 对 str 的左边进行填充，达到 n 个字符串长度 |
| Rpad(str, n ,pad)          | 左填充，用字符串 pad 对 str 的右边进行填充，达到 n 个字符串长度 |
| trim(str)                  | 去掉字符串头部和尾部的空格                                   |
| substring(str, start, len) | 返回字符串 str  从 start 位置起的 len 个长度的字符串         |



```sql
select 函数(参数);

// 练习：由于业务需求变更，企业员工的工号，统一为5位数，目前不足5位数的全部在前面补0。比如： 1号员
工的工号应该为00001。
```



### 数值函数

| 函数       | 功能                                   |
| ---------- | -------------------------------------- |
| ceil(x)    | 向上取整                               |
| floor(x)   | 向下取整                               |
| mod(x/y)   | 返回 x/y 的模                          |
| rand()     | 返回 0~1 内的随机数                    |
| rand(x, y) | 求参数 x 的四舍五入的值，保留 y 位小数 |



```sql
//通过数据库的函数，生成一个六位数的随机验证码。

```





### 日期函数

| 函数                               | 功能                                                |
| ---------------------------------- | --------------------------------------------------- |
| curdate()                          | 返回当前日期                                        |
| curtime()                          | 返回当前时间                                        |
| now()                              | 返回当前日期和时间                                  |
| yesr(date)                         | 获取指定 date 的年份                                |
| month(date)                        | 获取指定 date 的月份                                |
| day(date)                          | 获取指定 date 的日期                                |
| date_add(date, interval expr type) | 返回一个日期/时间值加上一个时间间隔 expr 后的时间值 |
| datediff(date1, date1)             | 返回起始时间 date1 和 结束时间 date2 之间的天数     |



```sql
// 查询所有员工的入职天数，并根据入职天数倒序排序。	
```





### 流程函数

流程函数也是很常用的一类函数，可以在SQL语句中实现条件筛选，从而提高语句的效率。

| 函数                                                      | 功能                                                         |
| --------------------------------------------------------- | ------------------------------------------------------------ |
| if(value, t, f)                                           | 如果 value 为 true，则返回t ，否则返回f                      |
| ifnull(value1, value2)                                    | 如果 value 不为空，返回value1 ，否则返回value2               |
| case when [val1] then [res1] ... else [default] end       | 如果 val1 为true ，返回res1 ， 否则返回 default 默认值       |
| case [expr] when [val1] then [res1]... else [default] end | 如果 expr 的值等于val1 ，返回res1 ， 否则返回 default 默认值 |



```sql
统计班级各个学员的成绩，展示的规则如下：
• >= 85，展示优秀
• >= 60，展示及格
• 否则，展示不及格
```



```sql
1. 字符串函数
concat lower upper lpad rpad trim sunstring
2. 数值函数
ceil floor mod rand round
3. 日期函数
curdate curtime now year month day date_add adtediff 
4. 流程函数
if ifnull case[...]when...then...else...end
```





## 4. 约束（保证数据的完整性和正确性）

### 概述

1. 概念：约束是作用于表中字段上的规则，用于限制存储在表中的数据。

2. 目的：保证数据库中数据的正确、有效性和完整性。

3. 分类：

| 约束                       | 描述                                                     | 关键字      |
| -------------------------- | -------------------------------------------------------- | ----------- |
| 非空约束                   | 限制该字段的数据不能为null                               | not null    |
| 唯一约束                   | 保证该字段的所有数据都是唯一、不重复的                   | unique      |
| 主键约束                   | 主键是一行数据的唯一标识，要求非空且唯一                 | primary key |
| 默认约束                   | 保存数据时，如果未指定该字段的值，则采用默认值           | default     |
| 检查约束（8.0.16版本之后） | 保证字段值满足某一个条件                                 | check       |
| 外键约束                   | 用来让两张表的数据之间建立连接，保证数据的一致性和完整性 | foreign key |

注意：约束是作用于表中字段上的，可以在创建表/修改表的时候添加约束。



> 案例

| 字段名 | 字段含义   | 字段类型    | 约束条件                  |
| ------ | ---------- | ----------- | ------------------------- |
| id     | ID唯一标识 | int         | 主键，并且自动增长        |
| name   | 姓名       | varchar(10) | 不为空，并且唯一          |
| age    | 年龄       | int         | 大于 ，并且小于等于120    |
| status | 状态       | char        | 如果没有指定该值，默认为1 |
| gender | 性别       | char        | 无                        |



```sql
create table tb_user(
  id int auto_increment primary key comment 'ID唯一标识',
  name varchar(10) not null unique comment '姓名',
  age int check(age>0&&age<=120) comment '年龄',
  status char(1) default('1') comment '状态',
  gender char(1) comment '性别'
);
```



### 外键约束

外键用来让两张表的数据之间建立连接，从而保证数据的一致性和完整性。



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331131724487.png)

注意：目前上述的两张表，在数据库层面，并未建立外键关联，所以是无法保证数据的一致性和完整性的。



```sql
-- 添加外键
create table 表名(
    字段名  数据类型
  ...
  
  [constraint][外键名称] foreign key (外键字段名) references 主表(主表列名);
)
alter table 表名 add constraint 外键名称 foreign key(外键字段名) references 主表(主表列名);

-- 删除外键
alter table 表名 drop foreign key 外键名称;
```



- 删除/更新行为

| 行为        | 说明                                                         |
| ----------- | ------------------------------------------------------------ |
| no action   | 当在父表中删除/更新对应记录时，首先检查该记录是否有对应外键，如果有则不允许删除/更新。 (与restrict 一致) |
| restrict    | 当在父表中删除/更新对应记录时，首先检查该记录是否有对应外键，如果有则不允许删除/更新。 (与 no action一致) |
| cascade     | 当在父表中删除/更新对应记录时，首先检查该记录是否有对应外键，如果有，则也删除/更新外键在子表中的记录。 |
| set null    | 当在父表中删除对应记录时，首先检查该记录是否有对应外键，如果有则设置子表中该外键值为null（这就要求该外键允许取null）。 |
| set default | 父表有变更时，子表将外键列设置成一个默认的值 (Innodb不支持)  |



```sql
alter table 表名 add constraint 外键名称 foreign key（外键字段） references 主表名( 主表字段名)  on update cascade on default cascade;
```





## 5. 多表查询（结合具体的案例）

### 多表关系

项目开发中，在进行数据库表结构设计时，会根据业务需求及业务模块之间的关系，分析并设计表结构，由于业务之间相互关联，所

以各个表结构之间也存在着各种联系，基本上分为三种：

- 一对多（多对一）

案例: 部门 与 员工的关系

关系: 一个部门对应多个员工，一个员工对应一个部门

实现: 在多的一方建立外键，指向一的一方的主键

![image-20220331134259260](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331134259260.png)



- 多对多

案例: 学生 与 课程的关系

关系: 一个学生可以选修多门课程，一门课程也可以供多个学生选择

实现: 建立第三张中间表，中间表至少包含两个外键，分别关联两方主键

![image-20220331134402725](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331134402725.png)



- 一对一

案例: 用户 与 用户详情的关系

关系: 一对一关系，多用于单表拆分，将一张表的基础字段放在一张表中，其他详情字段放在另一张表中，以提升操作效率

实现: 在任意一方加入外键，关联另外一方的主键，并且设置外键为唯一的(UNIQUE)

![image-20220331134527199](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331134527199.png)



### 多表查询概述

概述: 指从多张表中查询数据

笛卡尔积: 笛卡尔乘积是指在数学中，两个集合A集合 和 B集合的所有组合情况。(在多表查询时，需要消除无效的笛卡尔积)

#### 连接查询

内连接：相当于查询A、B交集部分数据

左外连接：查询左表所有数据，以及两张表交集部分数据

右外连接：查询右表所有数据，以及两张表交集部分数据

自连接：当前表与自身的连接查询，自连接必须使用表别名

#### 子查询

交集

### 内连接

```sql
-- 隐式内连接
  select 字段列表 from 表1,表2 where 条件...;
  -- 隐式内连接
  select 字段列表 from 表1 [inner] join 表2 on 连接条件...;
// 内连接查询的是两张表交集的部分
```



### 外连接

```sql
-- 左外连接: 相当于查询表1(左表)的所有数据 包含 表1和表2交集部分的数据
select 字段列表 from 表1 left[outer] join 表2 on 条件...;
-- 左外连接
select 字段列表 from 表1 right[outer] join 表2 on 条件...;
```



### 自连接

```sql
-- 自连接查询，可以是内连接查询，也可以是外连接查询。
select 字段列表 from 表A 别名A join 表B 别名B on 条件...;

-- 联合查询-union , union all：对于union查询，就是把多次查询的结果合并起来，形成一个新的查询结果集。
select 字段列表 from 表A...
union[all]
select 字段列表 from 表B...
// 对于联合查询的多张表的列数必须保持一致，字段类型也需要保持一致。
// union all 会将全部的数据直接合并在一起，union 会对合并之后的数据去重。
```



### 子查询

概念：SQL语句中嵌套SELECT语句，称为嵌套查询，又称子查询。

```sql
select * from t1 where column1 = (select column1 from t2);
// 子查询外部的语句可以是INSERT / UPDATE / DELETE / SELECT 的任何一个。
```

根据子查询结果不同，分为：

- 标量子查询（子查询结果为单个值：数字、字符串、日期等）（ 常用的操作符：= <> > >= < <= 	）
- 列子查询(子查询结果为一列) （常用的操作符：IN 、NOT IN 、 ANY 、SOME 、 ALL）
- 行子查询(子查询结果为一行) （常用的操作符：= 、<> 、IN 、NOT IN）
- 表子查询(子查询结果为多行多列)）（常用的操作符：IN）



根据子查询位置，分为：WHERE之后 、FROM之后、SELECT 之后。



### 多表查询案例

```bash
1. 查询员工的姓名、年龄、职位、部门信息。
2. 查询年龄小于30岁的员工姓名、年龄、职位、部门信息。
3. 查询拥有员工的部门ID、部门名称。
4. 查询所有年龄大于40岁的员工, 及其归属的部门名称; 如果员工没有分配部门, 也需要展示出来。
5. 查询所有员工的工资等级。
6. 查询 "研发部" 所有员工的信息及工资等级。
7. 查询 "研发部" 员工的平均工资。
8. 查询工资比 "灭绝" 高的员工信息。
9. 查询比平均薪资高的员工信息。
10. 查询低于本部门平均工资的员工信息。
11. 查询所有的部门信息, 并统计部门的员工人数。
12. 查询所有学生的选课情况, 展示出学生名称, 学号, 课程名称
```



### 总结

多表关系

- 一对多：在多的一方设置外键，关联一的一方的主键

- 多对多：建立中间表，中间表包含两个外键，关联两张表的主键

- 一对一：用于表结构拆分，在其中任何一方设置外键unique ，关联另一方的主键



多表查询

- 内连接

```sql
select 字段列表 from 表1,表2 where 条件...;

select 字段列表 from 表1 [inner] join 表2 on 连接条件...;
```

- 外连接

```sql
select 字段列表 from 表1 left[outer] join 表2 on 条件...;
```

- 自连接

```sql
select 字段列表 from 表A 别名A join 表B 别名B on 条件...;
```

- 子查询

```sql
标量子查询、列子查询、行子查询、表子查询
```





## 6. 事务（保证数据的安全性）

### 事务简介

事务 是一组操作的集合，它是一个不可分割的工作单位，事务会把所有的操作作为一个整体一起向系统提交或撤销操作

请求，即这些操作要么同时成功，要么同时失败。

**默认MySQL的事务是自动提交的，也就是说，当执行一条DML语句，MySQL会立即隐式的提交事务。**



### 事务操作

```sql
-- 查看/设置事务提交方式
select @@autocommit;
set @@autocommit=0;

-- 开启事务
start transaction 或 begin;
-- 提交事务
commit;
-- 回滚事务
rollback;
```



### 事务四大特性AIDC

- 原子性（Atomicity）：事务是不可分割的最小操作单元，要么全部成功，要么全部失败。
- 一致性（Consistency）：事务完成时，必须使所有的数据都保持一致状态。
- 隔离性（Isolation）：数据库系统提供的隔离机制，保证事务在不受外部并发操作影响的独立环境下运行。
-  持久性（Durability）：事务一旦提交或回滚，它对数据库中的数据的改变就是永久的。



### 并发事务问题

| 问题       | 描述                                                         |
| ---------- | ------------------------------------------------------------ |
| 脏读       | 一个事务读到另外一个事务还没有提交的数据。                   |
| 不可重复读 | 一个事务先后读取同一条记录，但两次读取的数据不同，称之为不可重复读。 |
| 幻读       | 一个事务按照条件查询数据时，没有对应的数据行，但是在插入数据时，又发现这行数据已经存在，好像出现了 |





### 事务隔离级别

| 隔离级别                | 脏读 | 不可重复读 | 幻读 |
| ----------------------- | ---- | ---------- | ---- |
| Read uncommitted        | √    | √          | √    |
| Read committed          | ×    | √          | √    |
| Repeatable Read（默认） | ×    | ×          | √    |
| Serializable            | ×    | ×          | ×    |



```sql
-- 查看事务隔离级别
select @@transaction_isolation;

-- 设置事务隔离级别
set [session | global] transaction isolation level [Read uncommitted | Read committed | Repeatable Read | Serializable]
```



**注意：事务隔离级别越高，数据越安全，但是性能越低。**





### 总结

1. 事务简介

事务是一组操作的集合，这组操作，要么全部执行成功，要么全部执行失败。

2. 事务操作

```sql
-- 开启事务
start transaction 或 begin;
-- 提交事务
commit;
-- 回滚事务
rollback;
```



3. 事务四大特性

原子性 Atomicity 、一致性 Consistency 、隔离性 Isolation 、持久性 Durability 

4. 并发事务问题

脏读、不可重复读、幻读

5. 事务隔离级别

Read uncommitted | Read committed | Repeatable Read | Serializable







# 进阶篇

## 7. 存储引擎

### MySQL体系结构

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331144414498.png)



```bash
-- 连接层
最上层是一些客户端和链接服务，主要完成一些类似于连接处理、授权认证、及相关的安全方案。服务器也会为安全接入的每个客户
端验证它所具有的操作权限。

-- 服务层
第二层架构主要完成大多数的核心服务功能，如SQL接口，并完成缓存的查询，SQL的分析和优化，部分内置函数的执行。所有跨存
储引擎的功能也在这一层实现，如 过程、函数等。

-- 引擎层
存储引擎真正的负责了MySQL中数据的存储和提取，服务器通过API和存储引擎进行通信。不同的存储引擎具有不同的功能，这样我
们可以根据自己的需要，来选取合适的存储引擎。

-- 存储层
主要是将数据存储在文件系统之上，并完成与存储引擎的交互。

```



### 存储引擎简介

存储引擎就是存储数据、建立索引、更新/查询数据等技术的实现方式 。存储引擎是基于表的，而不是基于库的，所以存储引擎也可被

称为表类型。



1. 在创建表时，指定存储引擎

```sql
create table 表名(
		字段1  字段1类型 [comment 字段1注释],
  	...
		字段n  字段n类型 [comment 字段n注释]
) engine=innodb [comment 表注释];
```



2. 查看当前数据库支持的存储引擎

```sql
show engines;
```



### 存储引擎特点（事务、外键、行级锁）

#### InnoDB

介绍：InnoDB是一种兼顾高可靠性和高性能的通用存储引擎，在 MySQL 5.5 之后，InnoDB是默认的 MySQL 存储引擎。

特点：

- DML操作遵循ACID模型，支持事务 ；

- 行级锁 ，提高并发访问性能；

- 支持外键 FOREIGN KEY约束，保证数据的完整性和正确性；

文件：

xxx.ibd：xxx代表的是表名，innoDB引擎的每张表都会对应这样一个表空间文件，存储该表的表结构（frm、sdi）、数据和索引。

参数：innodb_file_per_table

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331145439040.png)





#### MyISAM

介绍：MyISAM是MySQL早期的默认存储引擎。

特点：

- 不支持事务，不支持外键

- 支持表锁，不支持行锁

- 访问速度快

文件：

xxx.sdi：存储表结构信息

xxx.MYD: 存储数据

xxx.MYI: 存储索引





#### Memory

介绍：Memory引擎的表数据是存储在内存中的，由于受到硬件问题、或断电问题的影响，只能将这些表作为临时表或缓存使用。

特点：

- 内存存放

- hash索引（默认）

文件

xxx.sdi：存储表结构信息



![image-20220331145726518](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331145726518.png)



### 存储引擎选择

在选择存储引擎时，应该根据应用系统的特点选择合适的存储引擎。对于复杂的应用系统，还可以根据实际情况选择多种存储引擎进行组合。



➢ InnoDB : 是Mysql的默认存储引擎，支持事务、外键。如果应用对事务的完整性有比较高的要求，在并发条件下要求数据的一致

性，数据操作除了插入和查询之外，还包含很多的更新、删除操作，那么InnoDB存储引擎是比较合适的选择。

➢ MyISAM ： 如果应用是以读操作和插入操作为主，只有很少的更新和删除操作，并且对事务的完整性、并发性要求不是很高，那

么选择这个存储引擎是非常合适的。

➢ MEMORY：将所有数据保存在内存中，访问速度快，通常用于临时表及缓存。MEMORY的缺陷就是对表的大小有限制，太大的表

无法缓存在内存中，而且无法保障数据的安全性。







## 8. 索引index

### 索引概述

索引`index`是帮助MySQL高效获取数据的数据结构（有序）。在数据之外，数据库系统还维护着满足特定查找算法的数据结构，这

些数据结构以某种方式引用（指向）数据， 这样就可以在这些数据结构上实现高级查找算法，这种数据结构就是索引。

> B + Tree
>
> 红黑树
>
> 二叉树
>
> B - Tree



![image-20220331150549880](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331150549880.png)

备注：上述二叉树索引结构的只是一个示意图，并不是真实的索引结构。



优点：

- 提高数据检索的效率，降低数据库的IO成本。

- 通过索引列对数据进行排序，降低数据排序的成本，降低CPU的消耗。

缺点：

- 索引列也是要占用空间的。
- 索引大大提高了查询效率，同时却也降低更新表的速度，如对表进行INSERT、UPDATE、DELETE时，效率降低。



### 索引结构

我们平常所说的索引，如果没有特别指明，都是指B+树结构组织的索引。

| 索引          | InnoDB          | MyISAM | Memory |
| ------------- | --------------- | ------ | ------ |
| B+Tree索引    | 支持            | 支持   | 支持   |
| Hash索引      | 不支持          | 不支持 | 支持   |
| R-tree索引    | 不支持          | 支持   | 不支持 |
| Full-text索引 | 5.6版本之后支持 | 支持   | 不支持 |



#### 二叉树

![image-20220331151215402](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331151215402.png)

二叉树缺点：顺序插入时，会形成一个链表，查询性能大大降低。 大数据量情况下，层级较深，检索速度慢。

红黑树：大数据量情况下，层级较深，检索速度慢。



#### B-Tree（多路平衡查找树）

以一颗最大度数（max-degree）为5(5阶)的b-tree为例(每个节点最多存储4个key，5个指针)：

![image-20220331151317568](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331151317568.png)

> 知识小贴士: 树的度数指的是一个节点的子节点个数。



插入 100 65 169 368 900 556 780 35 215 1200 234 888 158 90 1000 88 120 268 250 数据为例。

![image-20220331151432119](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331151432119.png)



> 具体动态变化的过程可以参考网站: https://www.cs.usfca.edu/~galles/visualization/BTree.html





#### B+Tree

以一颗最大度数（max-degree）为4（4阶）的b+tree为例：

![image-20220331151621423](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331151621423.png)



插入 100 65 169 368 900 556 780 35 215 1200 234 888 158 90 1000 88 120 268 250 数据为例。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331151738181.png)

相对于B-Tree区别: 

①. 所有的数据都会出现在叶子节点

②. 叶子节点形成一个单向链表



MySQL索引数据结构对经典的B+Tree进行了优化。在原B+Tree的基础上，增加一个指向相邻叶子节点的链表指针，就形成了带有顺

序指针的B+Tree，提高区间访问的性能。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331151850397.png)



#### Hash

哈希索引就是采用一定的hash算法，将键值换算成新的hash值，映射到对应的槽位上，然后存储在hash表中。

如果两个(或多个)键值，映射到一个相同的槽位上，他们就产生了hash冲突（也称为hash碰撞），可以通过链表来解决。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331152030663.png?w=600)

Hash索引特点：

- Hash索引只能用于对等比较(=，in)，不支持范围查询（between，>，< ，...）
- 无法利用索引完成排序操作
- 查询效率高，通常只需要一次检索就可以了，效率通常要高于B+tree索引



<hr>

存储引擎支持：

在MySQL中，支持hash索引的是Memory引擎，而InnoDB中具有自适应hash功能，hash索引是存储引擎根据B+Tree索引在指定条件

下自动构建的。



**为什么InnoDB存储引擎选择使用B+tree索引结构?（即和另外3种进行对比）** 

- 相对于二叉树，层级更少，搜索效率高；
- 对于B-tree，无论是叶子节点还是非叶子节点，都会保存数据，这样导致一页中存储的键值减少，指针跟着减少，要同样保存大量数据，只能增加树的高度，导致性能降低；
- 相对Hash索引，B+tree支持范围匹配及排序操作；



<hr>

### 索引分类

| 分类     | 含义                                                 | 特点                     | 关键字   |
| -------- | ---------------------------------------------------- | ------------------------ | -------- |
| 主键索引 | 针对于表中主键创建的索引                             | 默认自动创建，只能有一个 | primary  |
| 唯一索引 | 避免同一个表中某数据列中的值重复                     | 可以有多个               | unique   |
| 常规索引 | 快速定位特定数据                                     | 可以有多个               |          |
| 全文索引 | 全文索引查找的是文本中的关键词，而不是比较索引中的值 | 可以有多个               | fulltext |



**在InnoDB存储引擎中，根据索引的存储形式，又可以分为以下两种：**

| 分类                      | 含义                                                       | 特点                    |
| ------------------------- | ---------------------------------------------------------- | ----------------------- |
| 聚集索引(Clustered Index) | 将数据存储与索引放到了一块，索引结构的叶子节点保存了行数据 | **必须有,而且只有一个** |
| 二级索引(Secondary Index) | 将数据与索引分开存储，索引结构的叶子节点关联的是对应的主键 | 可以存在多个            |



聚集索引选取规则: 

- 如果存在主键，主键索引就是聚集索引。
- 如果不存在主键，将使用第一个唯一（UNIQUE）索引作为聚集索引。
- 如果表没有主键，或没有合适的唯一索引，则InnoDB会自动生成一个rowid作为隐藏的聚集索引。







主键构建的主键索引就是聚集索引；叶子结点挂的数据就是这一行的数据。

如果要针对name字段再建立一个索引，因为聚集索引只会有一个，所以name字段建的索引称为二级索引。叶子结点下面挂的是name

值对应的这一行的的主键值

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331154544445.png)

> 第七分钟



```sql
select * from user where name = 'arm';
-- 不会走聚集索引，会走二级索引
-- A比L小，到Geek，再到Arm，拿到10.
-- 要获取所有数据，再根据聚集索引查找。这一过程称为回表查询：先走二级索引找到对应的主键值，再根据主键值到聚集索引中拿到这一行的行数据。
```





聚集索引和二级索引的结构一定要理解，这个在SQL优化中是非常重要的。很多SQL优化策略的底层原理。

看2道思考题：

1. 一下SQL语句，哪个执行效率高？为什么？（1）

```sql
-- id为主键，name字段创建的有索引
select * from user where id =10;
select * from user where name = 'Arm';
```

2. InnoDB主键索引的B+tree高度为多高呢?

假设: 一行数据大小为1k，一页中可以存储16行这样的数据。InnoDB的指针占用6个字节的空间，主键即使为bigint，占用字节数为8。

```bash
高度为2：
n * 8 + (n + 1) * 6 = 16*1024 , 算出n约为 1170 
1171* 16 = 18736
高度为3：
1171 * 1171 * 16 = 21939856

2千多万条记录，B+树高度也才为3。效率很高。
```





<hr>

### 索引语法

```sql
-- 创建索引
create [unique | fulltext] index index_name on table_name(index_col_name,...);

-- 查看索引
show index from table_name;

-- 删除索引
drop index index_name on table_name;
```



案例：按照下列的需求，完成索引的创建

```sql
-- 1. name字段为姓名字段，该字段的值可能会重复，为该字段创建索引。
-- 2. phone手机号字段的值，是非空，且唯一的，为该字段创建唯一索引。
-- 3. 为profession、age、status创建联合索引。
-- 4. 为email建立合适的索引来提升查询效率。

create index idx_user_name on tb_user(name);
create unique index idx_user_phone on tb_user(phone);
create index idx_user_profession_age_status on tb_user(profession,age,status);
create index idx_user_email on tb_user(email);

-- 删除索引
drop index idx_user_email on tb_user;
```





### SQL性能分析

#### SQL执行频率（查询为主还是增删改为主？）

MySQL 客户端连接成功后，通过 `show [session|global] status` 命令可以提供服务器状态信息。通过如下指令，可以查看当前

数据库的INSERT、UPDATE、DELETE、SELECT的访问频次：

```sql
show global status like 'Com_______';
```



#### 慢查询日志（优化哪些select语句）（定位执行效率低的SQL）

慢查询日志记录了所有执行时间超过指定参数（long_query_time，单位：秒，默认10秒）的所有SQL语句的日志。

MySQL的慢查询日志默认没有开启，需要在MySQL的配置文件（/etc/my.cnf）中配置如下信息：

```properties
# 查看当前慢查询日志开关
show variables like 'slow_query_log';
# 开启MySQL慢查询日志开关
slow_query_log=1
# 设置慢日志的时间为 2秒， 语句执行时间超过 2秒，就会视为慢查询，记录慢查询日志
long_query_time=2
```

配置完毕之后，通过以下指令重新启动MySQL服务器进行测试，查看慢日志文件中记录的信息 /var/lib/mysql/localhost-slow.log。

> https://blog.csdn.net/weixin_40675010/article/details/104256171



```sql
# mac开启慢查询日志: https://www.cnblogs.com/woods1815/p/11829659.html
mysql> set global slow_query_log='on';
SET GLOBAL long_query_time = 3;  # 这里需要注意下，long_query_time参数设置后需要下次会话后才生效，当前会话查询还是原来的数值
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331184122293.png)



#### profile详情（每一条sql的耗时，以及耗时在哪一个阶段）

show profiles 能够在做SQL优化时帮助我们了解时间都耗费到哪里去了。通过have_profiling参数，能够看到当前MySQL是否支持

profile操作：

```sql
select @@have_profiling;
```

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331184659785.png)





默认profiling是关闭的，可以通过set语句在session/global级别（会话/全局级别）开启profiling：

```sql
set profiling=1;
```



执行一系列的业务SQL的操作，然后通过如下指令查看指令的执行耗时：

```sql
-- 查看每一条 SQL 的耗时基本情况
show profiles;
-- 查看指定query_id的 SQL语句各个阶段的耗时情况
show profile for query query_id;
-- 查看指定query_id的 SQL语句cpu的使用情况
show profile cpu for query query_id;
```



以上三种方式从执行时间的角度出发。并不能真正评判一条sql的性能。

<hr>



#### explain执行计划（重要）

EXPLAIN 或者 DESC命令获取 MySQL 如何执行 SELECT 语句的信息，包括在 SELECT 语句执行过程中表如何连接和连接的顺序。

```sql
-- 直接在select语句之前加上关键字explain / desc
explain select 字段列表 from 表名 where 条件;
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331185441717.png)

 

**EXPLAIN 执行计划各字段含义：（重点字段是type、possible_key、key、key_len）**

- Id：select查询的序列号，表示查询中执行select子句或者是操作表的顺序(id相同，执行顺序从上到下；id不同，值越大，越先执行)。 

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331190207655.png)

- select_type：表示 SELECT 的类型，常见的取值有 SIMPLE（简单表，即不使用表连接或者子查询）、PRIMARY（主查询，即外

  层的查询）、UNION（UNION 中的第二个或者后面的查询语句）、SUBQUERY（SELECT/WHERE之后包含了子查询）等

- **type**：表示连接类型，性能由好到差的连接类型为NULL、system、const（主键或唯一索引）、eq_ref、ref（非唯一性索引）、range、 index、all （全表扫描，性能低）。 （尽量往前优化！）

- **possible_key**：显示可能应用在这张表上的索引，一个或多个。

- **Key**：实际使用的索引，如果为NULL，则没有使用索引。

- **Key_len**：表示索引中使用的字节数， 该值为索引字段最大可能长度，并非实际使用长度，在不损失精确性的前提下， 长度越短越好 。
- rows：MySQL认为必须要执行查询的行数，在innodb引擎的表中，是一个估计值，可能并不总是准确的。
- filtered：表示返回结果的行数占需读取行数的百分比， filtered 的值越大越好。



### 索引使用（如何正确使用）

#### 验证索引效率（索引对查询效率提升极大）



```sql
-- 在未建立索引之前，执行如下SQL语句，查看SQL的耗时。-------------------21s
select * from tb_sku where id = 1;
select * from tb_sku where id = 1\G;
select * from tb_sku where sn = '100000003145001';
-- 针对字段创建索引(1千万数据构建B+树，耗时1分钟11秒)
create index idx_sku_sn on tb_sku(sn);

-- 然后再次执行相同的SQL语句，再次查看SQL的耗时。-------------------0.01s
select * from tb_sku where sn = '100000003145001';
```



> 想到面试题：sql优化之正确使用索引

#### 1-最左前缀法则

如果索引了多列（联合索引），要遵守最左前缀法则（查询从索引的最左列开始，并且不跳过索引中的列。）。

如果跳跃某一列，索引将部分失效(后面的字段索引失效)。

最左边的索引要存在，存在位置没有关系。

```sql
explain select * from tb_user where profession = '软件工程' and age = 31 and status = '0';（符合最左前缀法则）
explain select * from tb_user where profession = '软件工程' and age = 31;（符合最左前缀法则）
explain select * from tb_user where profession = '软件工程';（符合最左前缀法则）
explain select * from tb_user where age = 31 and status = '0';（跳过了左边的列profession，不符合，索引失效，全表扫描）
explain select * from tb_user where status = '0';（同上）
explain select * from tb_user where profession = '软件工程' and status = '0';（索引status部分失效）
```



#### 范围查询（业务允许的话，尽量使用>=）

联合索引中，出现范围查询(>,<)，范围查询右侧的列索引失效

```sql
explain select * from tb_user where profession = '软件工程' and age > 31 and status = '0';（status部分索引失效）
explain select * from tb_user where profession = '软件工程' and age >= 31 and status = '0';（索引未失效）
```



#### 索引列运算

不要在索引列上进行运算操作， 索引将失效。

```sql
-- 查询手机号最后2位是15
explain select * from tb_user where substring(phone,10,2) = '15';（索引失效，type=ALL，全表扫描）
```



#### 字符串不加引号

字符串类型字段使用时，不加引号， 索引将失效。

```sql
explain select * from tb_user where profession = '软件工程' and age = 31 and status = '0';
explain select * from tb_user where phone = 17799990015;
```



#### 模糊查询

如果仅仅是尾部模糊匹配，索引不会失效。如果是头部模糊匹配，索引失效。

```sql
explain select * from tb_user where profession like '软件%';
explain select * from tb_user where profession like '%工程';
explain select * from tb_user where profession like '%工';
```



#### or连接的条件

用or分割开的条件， 如果or前的条件中的列有索引，而后面的列中没有索引，那么涉及的索引都不会被用到。

```sql
explain select * from tb_user where id = 10 or age = 23;
explain select * from tb_user where phone = '17799990015' or age = 23;
```



由于age没有索引，所以即使id、phone有索引，索引也会失效。所以需要针对于age也要建立索引。



#### 数据分布影响（is null和is not null是否走索引取决于表中数据的分布）

如果MySQL评估使用索引比全表更慢，则不使用索引。

```sql
select * from tb_user where phone >= '17799990015';
select * from tb_user where phone >= '17799990015';
```



#### SQL提示（告诉MySQL用哪个索引）

SQL提示，是优化数据库的一个重要手段，简单来说，就是在SQL语句中加入一些人为的提示来达到优化操作的目的。



```sql
-- use index：
explain select * from tb_user use index(idx_user_pro) where profession = '软件工程'; 
-- ignore index：
explain select * from tb_user ignore index(idx_user_pro) where profession = '软件工程'; 
-- force index：
explain select * from tb_user force index(idx_user_pro) where profession = '软件工程'; 
```



#### 覆盖索引

尽量使用覆盖索引（查询使用了索引，并且需要返回的列，在该索引中已经全部能够找到），减少` select * `。（因为 * 很容易出现回表扫描）

```sql
explain select id,profession from tb_user where profession = '软件工程' and age = 31 and status = '0';
explain select id,profession,age,status from tb_user where profession = '软件工程' and age = 31 and status = '0';

explain select id,profession,age,status,name from tb_user where profession = '软件工程' and age = 31 and status = '0';
explain select * from tb_user where profession = '软件工程' and age = 31 and status = '0';
```

知识小贴士：

- using index condition ：查找使用了索引，但是需要回表查询数据

- using where; using index ：查找使用了索引，但是需要的数据都在索引列中能找到，所以不需要回表查询数据





![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220331203949131.png)



```sql
-- 面试题思考
-- 一张表, 有四个字段(id, username, password, status), 由于数据量大, 需要对以下SQL语句进行优化, 该如何进行才是最优方案:
select id, username, password from tb_user where username = 'itcast';
-- 建立username, password的联合索引
```





#### 前缀索引（降低索引的体积，只针对前缀建立索引）

当字段类型为字符串（varchar，text等）时，有时候需要索引很长的字符串，这会让索引变得很大，查询时，浪费大量的磁盘IO， 影

响查询效率。此时可以只将字符串的一部分前缀，建立索引，这样可以大大节约索引空间，从而提高索引效率。



```sql
create index idx_xxx on table_name(column(n));
-- 前缀长度: 可以根据索引的选择性来决定，而选择性是指不重复的索引值（基数）和数据表的记录总数的比值，索引选择性越高则查询效率越高， 唯一索引的选择性是1，这是最好的索引选择性，性能也是最好的。
select count(distinct email)/count(*) from tb_user;
select count(distinct substring(email,1,5)/count(*) from tb_user);
```







<hr>

### 索引设计原则







## 9. SQL优化



## 10. 视图/存储过程/触发器



## 11. 锁

## 12. InnoDB引擎

## 13. MySQL管理



# 运维篇

## 14. 日志



## 15. 主从复制



## 16. 分库分表



## 17. 读写分离





















