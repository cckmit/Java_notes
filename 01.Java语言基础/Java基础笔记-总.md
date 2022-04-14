

















### 一、泛型

#### 概述

- 泛型：是 JDK5 中引入的特性，可以在编译阶段约束操作的数据类型，并进行检查。

- 泛型的格式：<数据类型>; 注意：泛型只能支持**引用**数据类型。

- 集合体系的全部接口和实现类都是支持泛型的使用的。

<br>

#### 好处

- **统一数据类型**。

- 把运行时期的**问题提前到了编译期间**，避免了强制类型转换可能出现的异常，因为编译阶段类型就能确定下来。



#### 可以定义的地方

- 类后面——>泛型类

- 方法申明上——>泛型方法

- 接口后面——>泛型接口



<br>

#### 自定义泛型类

定义类时同时定义了泛型的类就是泛型类。格式：

```java
// 修饰符 class 类名<泛型变量>{  }
// 范例
public class MyArrayList<T> {  }
```



 此处泛型变量T可以随便写为任意标识，常见的如E、T、K、V等。



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411194649340.png?w=550)



作用：编译阶段可以指定数据类型，类似于集合的作用。

泛型类的原理：把出现泛型变量的地方全部替换成传输的真实数据类型。



#### 自定义泛型方法

定义方法时同时定义了泛型的方法就是泛型方法。

格式：修饰符 <泛型变量> 方法返回值 方法名称(形参列表){}

```java
// 范例： 
public <T> void show(T t) {  }
```

作用：方法中可以使用泛型接收一切实际类型的参数，方法更具备通用性。



#### 自定义泛型接口

使用了泛型定义的接口就是泛型接口。

泛型接口的格式：修饰符 interface 接口名称<泛型变量>{}

```java
// 范例： 
public interface Data<E>{}
```

作用：泛型接口可以让实现类选择当前功能需要操作的数据类型

泛型接口原理：实现类可以在实现接口的时候传入自己操作的数据类型，这样重写的方法都将是针对于该类型的操作。







#### 泛型通配符?、上下限

? 可以在 使用泛型 的时候代表一切类型。

E T K V 是在定义泛型的时候使用的。



```bash
# 案例：开发一个极品飞车的游戏，所有的汽车都能一起参与比赛。

```

<br>

```java
package genericity_limit;

import java.util.ArrayList;

/**
 * @author cat
 * @description
 * @date 2022/4/11 下午9:00
 */

// https://www.bilibili.com/video/BV1Cv411372m?p=132
// 黑马讲的很不错，理解了泛型的概述和优势、自定义泛型类/泛型方法/泛型接口、泛型通配符以及上下限（4月11日面试考题）
public class GenericDemo {
    public static void main(String[] args) {
        ArrayList<BMW> bmws = new ArrayList<>();
        bmws.add(new BMW());
        bmws.add(new BMW());
        bmws.add(new BMW());
        go(bmws);

        ArrayList<BENZ> benzs = new ArrayList<>();
        benzs.add(new BENZ());
        benzs.add(new BENZ());
        benzs.add(new BENZ());
        go(benzs);

        // 不符合，狗不应该能够比赛
        //ArrayList<genericity_limit.Dog> dogs = new ArrayList<>();
        //dogs.add(new genericity_limit.Dog());
        //dogs.add(new genericity_limit.Dog());
        //dogs.add(new genericity_limit.Dog());
        //go(dogs);
    }

    /**
     * 所有车比赛
     * @param cars
     */
    // 虽然BMW和BENZ都继承了Car, 但是ArrayList<genericity_limit.BMW>和ArrayList<genericity_limit.BENZ>与ArrayList<genericity_limit.Car>没有关系的！
    // (1)类有继承关系，但是父子类各自的集合没有关系

    //public static void go(ArrayList<genericity_limit.Car> cars){
    // (2)使用泛型可以，但是狗不应该能够比赛，所以下面我们使用泛型的上下限
            // ? extends genericity_limit.Car: ?必须是Car或者其子类   泛型上限
            // ? super genericity_limit.Car ： ?必须是Car或者其父类   泛型下限
    public static void go(ArrayList<? extends Car> cars){
    //
    //public static void go(ArrayList<genericity_limit.Car> cars){

    }


}

// -------------------------------------
// 不应该能比赛的狗
class Dog{

}


class BENZ extends Car{

}

class BMW extends Car{

}

class Car{

}
```





### 二、锁





synchronized与Lock 的对比
1. Lock是显式锁（手动开启和关闭锁，别忘记关闭锁），synchronized是隐式锁，出了作用域自动释放
2. Lock只有代码块锁，synchronized有代码块锁和方法锁
3. 使用Lock锁，JVM将花费较少的时间来调度线程，性能更好。并且具有更好的扩展性（提供更多的子类）



优先使用顺序：
Lock → 同步代码块（已经进入了方法体，分配了相应资源） → 同步方法（在方法体之外）









### 三、多线程

#### 概述

线程(thread)是一个程序内部的一条执行路径。

我们之前启动程序执行后，main方法的执行其实就是一条单独的执行路径。

程序中如果只有一条执行路径，那么这个程序就是单线程的程序。

<br>

多线程是指从软硬件上实现多条执行流程的技术。

再例如：消息通信、淘宝、京东系统都离不开多线程技术。

<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411212806859.png)



#### 多线程的创建

##### 方式一：继承Thread类

Java是通过java.lang.Thread 类来代表线程的。 

按照面向对象的思想，Thread类应该提供了实现多线程的方式。

<br>



- 定义一个子类MyThread**继承**线程类java.lang.Thread，重写 `run()` 方法

- 创建MyThread类的对象

- 调用线程对象的 `start()` 方法启动线程（启动后还是执行run方法的）



<br>

优点：编码简单

缺点：线程类已经继承Thread，无法继承其他类，不利于扩展。





##### 方式二：实现Runnable接口

- 定义一个线程任务类MyRunnable**实现**`Runnable` 接口，重写run()方法

- 创建MyRunnable任务对象

- 把MyRunnable任务对象交给Thread处理

- 调用线程对象的 `start()` 方法启动线程

<br>

Thread的构造器

| 构造器                                        | 说明                                         |
| --------------------------------------------- | -------------------------------------------- |
| public Thread(String name)                    | 可以为当前线程指定名称                       |
| public Thread(Runnable target)                | 封装Runnable对象成为线程对象                 |
| public Thread(Runnable target ，String name ) | 封装Runnable对象成为线程对象，并指定线程名称 |

<br>

优点：线程任务类只是实现接口，可以继续继承类和实现接口，扩展性强。

缺点：编程多一层对象包装，如果线程有执行结果是不可以直接返回的。



```java
package thread_app;

public class ThreadDemo22 {
    public static void main(String[] args) {

        // 实现Runnable接口(匿名内部类形式)
        Runnable target = new Runnable(){
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("子线程1执行输出..." + i);
                }
            }
        };

        Thread t = new Thread(target);
        t.start();

        // ------------------------------------------
        new Thread(new Runnable(){
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("子线程2执行输出..." + i);
                }
            }
        }).start();

        // ------------------------------------------lambda
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("子线程3执行输出..." + i);
            }
        }).start();



        for (int i = 0; i < 10; i++) {
            System.out.println("主线程执行输出..." + i);
        }
    }
}
```



##### 方式三：JDK 5.0新增：实现Callable接口（Callable——FutureTask——Thread）

前2种线程创建方式都存在一个问题：

- 他们重写的run方法均不能直接返回结果。

- 不适合需要返回线程执行结果的业务场景。

<br>

怎么解决这个问题呢？

- JDK 5.0提供了Callable和FutureTask来实现。
- **这种方式的优点是：可以得到线程执行的结果。**

<hr>

实现步骤

- 得到任务对象
  - 定义类实现Callable接口，重写call方法，封装要做的事情。
  - 用FutureTask把Callable对象封装成线程任务对象。

- 把线程任务对象交给Thread处理。
- 调用Thread的start方法启动线程，执行任务
- 线程执行完毕后、通过FutureTask的get方法去获取任务执行的结果。



```java
package thread_app;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author cat
 * @description
 * @date 2022/4/11 下午10:22
 */
public class ThreadDemo3 {
    public static void main(String[] args) {
        // 3, 创建Callable任务对象
        Callable<String> myCallable = new MyCallable(100);
        // 4, 把Callable任务对象  交给FutureTask 任务对象
        // FutureTask对象的作用1是：是Runnable的对象（实现了Runnable接口），可以交给Thread了
        // FutureTask对象的作用2是：线程执行完毕之后通过调用其get方法得到线程执行完成的结果
        FutureTask<String> futureTask = new FutureTask<>(myCallable);
        // 5, 交给线程处理
        Thread thread = new Thread(futureTask);
        thread.start();

        // 获取返回值
        try {
            // 如果futureTask任务没有执行完，这里的代码会等待，直到线程1跑完才提取结果
            String s = futureTask.get();
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

// 1, 定义一个任务类，实现Callable接口，应该申明线程任务执行完毕后返回结果的数据类型
class MyCallable implements Callable<String>{
    private final int n;

    public MyCallable(int n) {
        this.n = n;
    }

    // 2, 重写call()方法（任务方法）
    @Override
    public String call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return "子线程执行结果是：" + sum + "";
    }
}

```





| 方法名称                           | 说明                                 |
| ---------------------------------- | ------------------------------------ |
| public FutureTask<>(Callable call) | 把Callable对象封装成FutureTask对象。 |
| public V get() throws Exception    | 获取线程执行call方法返回的结果。     |



优点：

线程任务类只是实现接口，可以继续继承类和实现接口，扩展性强。

可以在线程执行完毕后去获取线程执行的结果。

缺点：

编码复杂一点。



<hr>

对比下3种方式

| 方式             | 优点                                                         | 缺点                                                   |
| ---------------- | ------------------------------------------------------------ | ------------------------------------------------------ |
| 继承Thread类     | 编程比较简单，可以直接使用Thread类中的方法                   | 扩展性较差，不能再继承其他的类，不能返回线程执行的结果 |
| 实现Runnable接口 | 扩展性强，实现该接口的同时还可以继承其他的类。               | 编程相对复杂，不能返回线程执行的结果                   |
| 实现Callable接口 | 扩展性强，实现该接口的同时还可以继承其他的类。可以得到线程执行的结果 | 编程相对复杂                                           |





#### 常用API

Thread常用方法：获取线程名称getName()、设置名称setName()、获取当前线程对象currentThread()。至于Thread类提供的诸如：yield、join、interrupt、不推荐的方法 stop 、守护线程、线程优先级等线程的控制方法，在开发中很少使用，这些方法会在高级篇以及后续需要用到的时候再为大家讲解。





### 四、常用类

#### 1 字符串相关的类

##### 1 String

**String类**：代表字符串。Java 程序中的所有字符串字面值（如 "abc" ）都作为此类的实例实现。

String是一个final类，代表不可变的字符序列。 

字符串是常量，用双引号引起来表示。它们的值在创建之后不能更改。

 String对象的字符内容是存储在一个**字符数组value[]**中的。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220412105855093.png)



<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220412110454415.png)

```bash
# 特性
常量与常量的拼接结果在常量池。且常量池中不会存在相同内容的常量。
只要其中有一个是变量，结果就在堆中
如果拼接的结果调用intern()方法，返回值就在常量池中
```



##### 2 String与基本数据类型、字符数组、字节数组的转换

```bash
# 字符串——>基本数据类型、包装类
Integer包装类的public static int parseInt(String s)：可以将由“数字”字符组成的字符串转换为整型。
类似地,使用java.lang包中的Byte、Short、Long、Float、Double类调相应的类方法可以将由“数字”字符组成的字符串，转化为相应的基本数据类型。
# 基本数据类型、包装类——>字符串
调用String类的public String valueOf(int n)可将int型转换为字符串
相应的valueOf(byte b)、valueOf(long l)、valueOf(float f)、valueOf(double d)、valueOf(boolean b)可由参数的相应类型到字符串的转换

# 字符数组——>字符串
String 类的构造器：String(char[]) 和 String(char[]，int offset，intlength) 分别用字符数组中的全部字符和部分字符创建字符串对象。
# 字符串——>字符数组
public char[] toCharArray()：将字符串中的全部字符存放在一个字符数组中的方法。
public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)：提供了将指定索引范围内的字符串存放到数组中的方法。

# 字节数组——>字符串
# 字符串——>字节数组
```







##### 3 StringBuffer

java.lang.StringBuffer代表**可变的字符序列**，JDK1.0中声明，可以对字符串内容进行增删，此时不会产生新的对象。

很多方法与String相同。

作为参数传递时，方法内部可以改变值。 



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220412111806833.png)

StringBuffer类不同于String，其对象必须使用构造器生成。有三个构造器：

- StringBuffer()：初始容量为16的字符串缓冲区
- StringBuffer(int size)：构造指定容量的字符串缓冲区
- StringBuffer(String str)：将内容初始化为指定字符串内容

<br>



| 方法                                                 | 说明                                         |
| ---------------------------------------------------- | -------------------------------------------- |
| StringBuffer append(xxx)                             | 提供了很多的append()方法，用于进行字符串拼接 |
| StringBuffer delete(int start,int end)               | 删除指定位置的内容                           |
| StringBuffer replace(int start, int end, String str) | 把[start,end)位置替换为str                   |
| StringBuffer insert(int offset, xxx)                 | 在指定位置插入xxx                            |
| StringBuffer reverse()                               | 把当前字符序列逆转                           |
|                                                      |                                              |





##### 4 StringBuilder

StringBuilder 和 StringBuffer 非常类似，均代表可变的字符序列，而且提供相关功能的方法也一样







##### 5 面试题：对比String、StringBuffer、StringBuilder

String(JDK1.0)：**不可变字符序列**

StringBuffer(JDK1.0)：可变字符序列、效率低、**线程安全**

StringBuilder(JDK 5.0)：可变字符序列、效率高、线程不安全

> 注意：作为参数传递的话，方法内部String不会改变其值，StringBuffer和StringBuilder会改变其值。















### 五、Java8 新特性





stream把list转map



### 六、Set系列集合、Map集合体系



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411224715970.png)



**Set系列集合特点**

- 无序：存取顺序不一致
- 不重复：可以去除重复
- 无索引：没有带索引的方法，所以不能使用普通for循环遍历，也不能通过索引来获取元素。

<br>



**Set集合实现类特点**

-  HashSet : 无序、不重复、无索引。
- LinkedHashSet：有序、不重复、无索引。
- TreeSet：排序、不重复、无索引。

<br>

Set集合的功能上基本上与Collection的API一致。



#### Set系列集合

##### HashSet元素无序的底层原理：哈希表

##### HashSet元素去重复的底层原理

##### 实现类：LinkedHashSet

##### 实现类：TreeSet





#### Collection体系的特点、使用场景总结

如果希望元素可以重复，又有索引，索引查询要快？

- 用ArrayList集合，基于数组的。（用的最多）

如果希望元素可以重复，又有索引，增删首尾操作快？

- 用LinkedList集合，基于链表的。

如果希望增删改查都快，但是元素不重复、无序、无索引。

- 用HashSet集合，基于哈希表的。

如果希望增删改查都快，但是元素不重复、有序、无索引。

- 用LinkedHashSet集合，基于哈希表和双链表。

如果要对对象进行排序。

- 用TreeSet集合，基于红黑树。后续也可以用List集合实现排序。





#### 补充知识：可变参数

可变参数在方法内部本质上就是一个数组。



#### 补充知识：集合工具类Collections

java.utils.Collections:是集合工具类

**作用：Collections并不属于集合，是用来操作集合的工具类。**





#### Collection体系的综合案例





#### Map集合体系

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411225519826.png)



使用最多的Map集合是HashMap。

重点掌握HashMap , LinkedHashMap , TreeMap。其他的后续理解。



得益于JDK 8开始的新技术Lambda表达式，提供了一种更简单、更直接的遍历集合的方式。



##### Map集合的遍历方式三：Lambda

Map结合Lambda遍历的API

| 方法名称                                                     | 说明                  |
| ------------------------------------------------------------ | --------------------- |
| default void forEach(BiConsumer<? super K, ? super V> action) | 结合lambda遍历Map集合 |





#### 补充知识：集合的嵌套







### 七、final

final 关键字是最终的意思，可以修饰（方法，变量，类）

- 修饰方法：表明该方法是最终方法，**不能被重写**。
- 修饰变量：表示该变量第一次赋值后，**不能再次被赋值(有且仅能被赋值一次)**。
- 修饰类：表明该类是最终类，**不能被继承**。

<br>

final修饰变量的注意

- final修饰的变量是基本类型：那么**变量存储的数据值**不能发生改变。

- final修饰的变量是引用类型：那么**变量存储的地址值**不能发生改变，但是地址指向的对象内容是可以发生变化的。





### 八、static



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220413121449979.png)



#### 静态关键字static

static是静态的意思，可以修饰**成员变量**和**成员方法**。

static修饰成员变量表示该成员变量只在内存中**只存储一份**，可以**被共享访问、修改**。

<br>



##### 成员变量可以分为2类

静态成员变量（有static修饰，**属于类，内存中加载一次**）: 常表示如在线人数信息、等**需要被共享的信息，可以被共享访问**。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220413122148800.png)

实例成员变量（无static修饰，存在于每个对象中）：常表示姓名name、年龄age、等**属于每个对象的信息**。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220413122217452.png)





##### static修饰成员变量的内存原理

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220413122449532.png)



##### 修饰成员方法的基本用法和内存原理

静态成员方法（有static修饰，属于类），建议用类名访问，也可以用对象访问。

实例成员方法（无static修饰，属于对象），只能用对象触发访问。



> 表示对象自己的行为的，且方法中需要访问实例成员的，则该方法必须申明成实例方法。
>
> 如果该方法是以执行一个共用功能为目的，则可以申明成静态方法。

<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220413123218178.png)





##### static实际应用案例：定义工具类

工具类中定义的都是一些静态方法，每个方法都是以完成一个共用的功能为目的。

工具类的好处：一是调用方便，二是提高了代码复用（一次编写，处处可用）

<br>

建议将工具类的**构造器进行私有**，工具类无需创建对象。

建议里面**都是静态方法**，直接用类名访问即可。





#### static应用知识：代码块

##### 代码块的分类、作用

代码块是类的5大成分之一（成员变量、构造器，方法，代码块，内部类），定义在类中方法外。

在Java类下，使用 { } 括起来的代码被称为代码块 。

<br>

静态代码块：

- 格式：static{}
- 特点：需要通过static关键字修饰，随着类的加载而加载，并且自动触发、只执行一次
- 使用场景：在类加载的时候做一些静态数据初始化的操作，以便后续使用。



构造代码块（了解，用的少）

- 格式：{}
- 特点：每次创建对象，调用构造器执行时，都会执行该代码块中的代码，并且在构造器执行前执行
- 使用场景：初始化实例资源。





##### 静态代码块的应用案例

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220413124244859.png)







**如果要在启动系统时对数据进行初始化。建议使用静态代码块完成数据的初始化操作，代码优雅。**



#### static应用知识：单例设计模式

开发中经常遇到一些问题，一个问题通常有n种解法的，但其中肯定有一种解法是最优的，这个最优的解法被人总结出来了，称之为设计

模式。

设计模式有20多种，对应20多种软件开发中会遇到的问题，学设计模式主要是学2点：

- 第一：这种模式用来解决什么问题。

- 第二：遇到这种问题了，该模式是怎么写的，他是如何解决这个问题的。

<br>

所谓单例模式：

- 可以保证系统中，应用该模式的这个类永远只有一个实例，即一个类永远只能创建一个对象。
- 例如任务管理器对象我们只需要一个就可以解决问题了，这样可以节省内存空间。



##### 单例实现之饿汉单例设计模式

在用类获取对象的时候，对象已经提前为你创建好了。

- 定义一个类，把构造器私有。
- 定义一个静态变量存储一个对象。

```java
/** a、定义一个单例类 */
public class SingleInstance {
  /** c.定义一个静态变量存储一个对象即可 :属于类，与类一起加载一次 */
  public static SingleInstance instance = new SingleInstance ();
  
  /** b.单例必须私有构造器*/
  private SingleInstance (){
    System.out.println("创建了一个对象");
  }
}

```



##### 单例实现之懒汉单例设计模式

在真正需要该对象的时候，才去创建一个对象(延迟加载对象)。

- 定义一个类，把构造器私有。
- 定义一个静态变量存储一个对象。
- 提供一个返回单例对象的方法。



```java
/** 定义一个单例类 */
class SingleInstance{
  /** 定义一个静态变量存储一个对象即可 :属于类，与类一起加载一次 */
  public static SingleInstance instance ; // null
  /** 单例必须私有构造器*/
  private SingleInstance(){
    
  }
  /** 必须提供一个方法返回一个单例对象  */
  public static SingleInstance getInstance(){
    ...
    return ...;
  }
}

```





#### 面向对象三大特征之二：继承

##### 概述与好处

Java中提供一个关键字extends，用这个关键字，我们可以让一个类和另一个类建立起父子关系。

```java
// Student称为子类（派生类），People称为父类(基类 或超类)。
public class Student extends People {}

```



当子类继承父类后，就可以直接使用父类公共的属性和方法了。因此，用好这个技术可以很好的我们**提高代码的复用性。**

Java中子类更强大。



##### 继承的特点

子类可以继承父类的属性和行为，但是子类不能继承父类的构造器。

Java是单继承模式：一个类只能继承一个直接父类。Java不支持多继承、但是支持多层继承。

Java中所有的类都是Object类的子类。



##### 继承后：成员变量、成员方法的访问特点

就近原则，子类有找子类、子类没有找父类、父类没有就报错！

如果子父类中，出现了重名的成员，会优先使用子类的，此时如果一定要在子类中使用父类的怎么办？

```java
格式：super.父类成员变量/父类成员方法
```





##### 继承后：子类构造器的特点

子类中所有的构造器默认都会先访问父类中无参的构造器，再执行自己。

```bash
# 为什么？
子类在初始化的时候，有可能会使用到父类中的数据，如果父类没有完成初始化，子类将无法使用父类的数据。
子类初始化之前，一定要调用父类构造器先完成父类数据空间的初始化。
# 怎么调用父类构造器的？
子类构造器的第一行语句默认都是：super()，不写也存在。

```





### 九、接口与抽象类



























