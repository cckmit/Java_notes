



# 一、泛型

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





# 二、锁





synchronized与Lock 的对比
1. Lock是显式锁（手动开启和关闭锁，别忘记关闭锁），synchronized是隐式锁，出了作用域自动释放
2. Lock只有代码块锁，synchronized有代码块锁和方法锁
3. 使用Lock锁，JVM将花费较少的时间来调度线程，性能更好。并且具有更好的扩展性（提供更多的子类）



优先使用顺序：
Lock → 同步代码块（已经进入了方法体，分配了相应资源） → 同步方法（在方法体之外）









# 三、多线程

## 1 概述

线程(thread)是一个程序内部的一条执行路径。

我们之前启动程序执行后，main方法的执行其实就是一条单独的执行路径。

程序中如果只有一条执行路径，那么这个程序就是单线程的程序。

<br>

多线程是指从软硬件上实现多条执行流程的技术。

再例如：消息通信、淘宝、京东系统都离不开多线程技术。

<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411212806859.png)



## 2 多线程的创建

### 方式一：继承Thread类

Java是通过java.lang.Thread 类来代表线程的。 

按照面向对象的思想，Thread类应该提供了实现多线程的方式。

<br>



- 定义一个子类MyThread**继承**线程类java.lang.Thread，重写 `run()` 方法

- 创建MyThread类的对象

- 调用线程对象的 `start()` 方法启动线程（启动后还是执行run方法的）



<br>

优点：编码简单

缺点：线程类已经继承Thread，无法继承其他类，不利于扩展。





### 方式二：实现Runnable接口

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



### 方式三：JDK 5.0新增：实现Callable接口（Callable——FutureTask——Thread）

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





## 3 常用API

Thread常用方法：获取线程名称getName()、设置名称setName()、获取当前线程对象currentThread()。至于Thread类提供的诸如：yield、join、interrupt、不推荐的方法 stop 、守护线程、线程优先级等线程的控制方法，在开发中很少使用，这些方法会在高级篇以及后续需要用到的时候再为大家讲解。



<br>

<hr>

# 四、常用类



## 0 自动装箱和拆箱

装箱：自动将基本类型⽤它们对应的引⽤类型包装起来；

拆箱：自动将包装类型转换为基本数据类型；

[参考](https://www.cnblogs.com/dolphin0520/p/3780005.html)



<hr>

深入剖析Java中的装箱和拆箱

##### （1）什么是装箱？什么是拆箱？

Java为每种基本数据类型都提供了对应的包装器类型，在Java SE  5之前，如果要生成一个数值为10的Integer对象，必须这样进行：

```java
	Integer i = new Integer(10);
```

而在从Java SE5开始就提供了自动装箱的特性，如果要生成一个数值为10的Integer对象，只需要这样就可以了：

```java
Integer i = 10;
```

这个过程中会自动根据数值创建对应的 Integer对象，这就是装箱。

那什么是拆箱呢？顾名思义，跟装箱对应，就是自动将包装器类型转换为基本数据类型：

```java
Integer i = 10;  //装箱
int n = i;   //拆箱
```

<br>

| int（4字节）    | Integer   |
| --------------- | --------- |
| byte（1字节）   | Byte      |
| short（2字节）  | Short     |
| long（8字节）   | Long      |
| float（4字节）  | Float     |
| double（8字节） | Double    |
| char（2字节）   | Character |
| boolean（未定） | Boolean   |



##### （2）装箱和拆箱是如何实现的？

以Interger类为例，在装箱的时候自动调用的是Integer的 **valueOf(int)** 方法。而在拆箱的时候自动调用的是Integer的 **intValue()** 方法。

##### （3）面试中相关的问题

虽然大多数人对装箱和拆箱的概念都清楚，但是在面试和笔试中遇到了与装箱和拆箱的问题却不一定会答得上来。（切身体会）下面列举一些常见的与装箱/拆箱有关的面试题。

1. 下面这段代码的输出结果是什么？

```java
public class Main {
    public static void main(String[] args) {
         
        Integer i1 = 100;
        Integer i2 = 100;
        Integer i3 = 200;
        Integer i4 = 200;
         
        System.out.println(i1==i2);
        System.out.println(i3==i4);
    }
}
```

也许有些朋友会说都会输出false，或者也有朋友会说都会输出true。但是事实上输出结果是：

```bash
true
false
```

为什么会出现这样的结果？输出结果表明i1和i2指向的是同一个对象，而i3和i4指向的是不同的对象。此时只需一看源码便知究竟，下面这段代码是 Integer 的 valueOf() 方法的具体实现：

```java
public static Integer valueOf(int i) {
  if(i >= -128 && i <= IntegerCache.high)
    return IntegerCache.cache[i + 128];
  else
    return new Integer(i);
}
```

而其中IntegerCache类的实现为：

```java
private static class IntegerCache {
  static final int high;
  static final Integer cache[];

  static {
    final int low = -128;

    // high value may be configured by property
    int h = 127;
    if (integerCacheHighPropValue != null) {
      // Use Long.decode here to avoid invoking methods that
      // require Integer's autoboxing cache to be initialized
      int i = Long.decode(integerCacheHighPropValue).intValue();
      i = Math.max(i, 127);
      // Maximum array size is Integer.MAX_VALUE
      h = Math.min(i, Integer.MAX_VALUE - -low);
    }
    high = h;

    cache = new Integer[(high - low) + 1];
    int j = low;
    for(int k = 0; k < cache.length; k++)
      cache[k] = new Integer(j++);
  }

  private IntegerCache() {}
  
}
```

从这2段代码可以看出，在通过valueOf方法创建Integer对象的时候，如果数值在 [-128,127] 之间，便返回指向IntegerCache.cache中已经存在的对象的引用；否则创建一个新的Integer对象。

上面的代码中i1和i2的数值为100，因此会直接从cache中取已经存在的对象，所以i1和i2指向的是同一个对象，而i3和i4则是分别指向不同的对象。

2. 下面这段代码的输出结果是什么？

```java
public class Main {
    public static void main(String[] args) {
         
        Double i1 = 100.0;
        Double i2 = 100.0;
        Double i3 = 200.0;
        Double i4 = 200.0;
         
        System.out.println(i1==i2);
        System.out.println(i3==i4);
    }
}
```

也许有的朋友会认为跟上面一道题目的输出结果相同，但是事实上却不是。实际输出结果为：

```bash
false
false
```

至于具体为什么，读者可以去查看Double类的valueOf的实现。

在这里只解释一下为什么Double类的valueOf方法会采用与Integer类的valueOf方法不同的实现。很简单：**在某个范围内的整型数值的个数是有限的，而浮点数却不是。**

> 注意，Integer、Short、Byte、Character、Long这几个类的valueOf方法的实现是类似的。
>
> Double、Float的valueOf方法的实现是类似的。



<br>

3. 谈谈 Integer i = new Integer(xxx) 和 Integer i =xxx; 这两种方式的区别。

当然，这个题目属于比较宽泛类型的。但是要点一定要答上，我总结一下主要有以下这两点区别：

1）第一种方式不会触发自动装箱的过程；而第二种方式会触发；

2）在执行效率和资源占用上的区别。第二种方式的执行效率和资源占用在一般性情况下要优于第一种情况（注意这并不是绝对的）。









<hr>



## 1 字符串相关的类

### 1 String

**String类**：代表字符串。Java 程序中的所有字符串字面值（如 "abc" ）都作为此类的实例实现。

String是一个final类，代表不可变的字符序列。 

字符串是常量，用双引号引起来表示。它们的值在创建之后不能更改。

 String对象的字符内容是存储在一个 字符数组 **value[]** 中的。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220412105855093.png)



<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220412110454415.png)

```bash
# 特性
常量与常量的拼接结果在常量池。且常量池中不会存在相同内容的常量。
只要其中有一个是变量，结果就在堆中
如果拼接的结果调用intern()方法，返回值就在常量池中
```



### 2 String与基本数据类型、字符数组、字节数组的转换

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







### 3 StringBuffer

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





### 4 StringBuilder

StringBuilder 和 StringBuffer 非常类似，均代表可变的字符序列，而且提供相关功能的方法也一样







### 5 面试题：对比String、StringBuffer、StringBuilder

String(JDK1.0)：**不可变字符序列**

StringBuffer(JDK1.0)：可变字符序列、效率低、**线程安全**

StringBuilder(JDK 5.0)：可变字符序列、效率高、线程不安全

> 注意：作为参数传递的话，方法内部String不会改变其值，StringBuffer和StringBuilder会改变其值。













<hr>

# 五、Java8 新特性

Java 8 是oracle公司于2014年3月发布，可以看成是自Java 5 以来最具革命性的版本。Java 8为Java语言、编译器、类库、开发工具与JVM带来了大量新特性。







## 1 Lambda表达式（核心1）

##### 为什么

Lambda 是一个**匿名函数**，我们可以把 Lambda 表达式理解为是**一段可以传递的代码**（将代码像数据一样进行传递）。

使用它可以写出更简洁、更灵活的代码。作为一种更紧凑的代码风格，使Java的语言表达能力得到了提升。





##### 举例



##### 语法

Lambda 表达式：在Java 8 语言中引入的一种新的语法元素和操作符。这个操作符为 “->” ， 该操作符被称为 **Lambda 操作符**或箭头操作符。它将 Lambda 分为两个部分：

左侧：指定了 Lambda 表达式需要的**参数列表**

右侧：指定了 **Lambda 体**，是抽象方法的实现逻辑，也即Lambda 表达式要执行的功能。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416114704716.png?w=550)

<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416132616512.png)



上述 Lambda 表达式中的参数类型都是由编译器推断得出的。Lambda 表达式中无需指定类型，程序依然可以编译，这是因为 javac 根据程序的上下文，在后台推断出了参数的类型。Lambda 表达式的类型依赖于上下文环境，是由编译器推断出来的。这就是所谓的“类型推断”。





## 2 函数式(Functional)接口

只包含一个抽象方法的接口，称为函数式接口。

在java.util.function包下定义了Java 8 的丰富的函数式接口



Java 内置四大核心函数式接口

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416132906954.png)





## 3 方法引用与构造器引用



方法引用可以看做是Lambda表达式深层次的表达。换句话说，方法引用就是Lambda表达式，也就是函数式接口的一个实例，通过方法的名字来指向一个方法，可以认为是 Lambda表达式的一个语法糖。



格式：使用操作符 `::` 将类(或对象) 与 方法名分隔开来。以下三种主要使用情况：

- 对象::实例方法名
- 类::静态方法名
- 类::实例方法名







## 4 强大的Stream API（核心2）

Java8中有两大最为重要的改变。第一个是 Lambda 表达式；另外一个则是 Stream API。

Stream API ( java.util.stream) 把真正的函数式编程风格引入到Java中。这是目前为止对Java类库最好的补充，因为Stream API可以极大提供Java程序员的生产力，让程序员写出高效率、干净、简洁的代码。





##### Stream 的操作三个步骤

```bash
# 1- 创建 Stream
一个数据源（如：集合、数组），获取一个流
# 2- 中间操作
一个中间操作链，对数据源的数据进行处理
# 3- 终止操作(终端操作) 
一旦执行终止操作，就执行中间操作链，并产生结果。之后，不会再被使用
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416133720717.png)









stream把list转map







## 5 Optional类

到目前为止，臭名昭著的空指针异常是导致Java应用程序失败的最常见原因。

以前，为了解决空指针异常，Google公司著名的Guava项目引入了Optional类，Guava通过使用检查空值的方式来防止代码污染，它鼓励程序员写更干净的代码。受到Google Guava的启发，Optional类已经成为Java 8类库的一部分。



Optional<T> 类(java.util.Optional) 是一个容器类，它可以保存类型T的值，代表这个值存在。或者仅仅保存null，表示这个值不存在。原来用 null 表示一个值不存在，现在 Optional 可以更好的表达这个概念。并且可以避免空指针异常。



Optional提供很多有用的方法，这样我们就不用显式进行空值检测。

##### 创建Optional类对象的方法

| 方法                     | 说明                                |
| ------------------------ | ----------------------------------- |
| Optional.of(T t)         | 创建一个 Optional 实例，t必须非空； |
| Optional.empty()         | 创建一个空的 Optional 实例          |
| Optional.ofNullable(T t) | t可以为null                         |

##### 判断Optional容器中是否包含对象

| 方法                                         | 说明                                                         |
| -------------------------------------------- | ------------------------------------------------------------ |
| boolean isPresent()                          | 判断是否包含对象                                             |
| void ifPresent(Consumer<? super T> consumer) | 如果有值，就执行Consumer接口的实现代码，并且该值会作为参数传给它。 |

##### 获取Optional容器的对象

| 方法                                                   | 说明                                                       |
| ------------------------------------------------------ | ---------------------------------------------------------- |
| T get()                                                | 如果调用对象包含值，返回该值，否则抛异常                   |
| T orElse(T other)                                      | 如果有值则将其返回，否则返回指定的other对象。              |
| T orElseGet(Supplier<? extends T> other)               | 如果有值则将其返回，否则返回由Supplier接口实现提供的对象。 |
| T orElseThrow(Supplier<? extends X> exceptionSupplier) | 如果有值则将其返回，否则抛出由Supplier接口实现提供的异常。 |







```java
@Test
public void test1() {
    Boy b = new Boy("张三");
    Optional<Girl> opt = Optional.ofNullable(b.getGrilFriend());
    // 如果女朋友存在就打印女朋友的信息
    opt.ifPresent(System.out::println);
}
@Test
public void test2() {
    Boy b = new Boy("张三");
    Optional<Girl> opt = Optional.ofNullable(b.getGrilFriend());
    // 如果有女朋友就返回他的女朋友，否则只能欣赏“嫦娥”了
    Girl girl = opt.orElse(new Girl("嫦娥"));
    System.out.println("他的女朋友是：" + girl.getName());
}
```











<hr>

# 六、Collection接口系列集合、Map接口系列集合

## 1 Collection接口方法

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411224715970.png)



Collection 接口是 List、Set 和 Queue 接口的父接口，该接口里定义的方法既可用于操作 Set 集合，也可用于操作 List 和 Queue 集合。



| 方法                                                         | 说明                 |                                            |
| ------------------------------------------------------------ | -------------------- | ------------------------------------------ |
| add(Object obj)、addAll(Collection coll)                     | 添加                 |                                            |
| int size()                                                   | 获取有效元素的个数   |                                            |
| void clear()                                                 | 清空集合             |                                            |
| boolean isEmpty()                                            | 是否是空集合         |                                            |
| boolean contains(Object obj)、boolean containsAll(Collection c) | 是否包含某个元素     | 通过元素的equals方法来判断是否是同一个对象 |
| boolean remove(Object obj)                                   | 删除                 |                                            |
| boolean retainAll(Collection c)                              | 取两个集合的交集     |                                            |
| boolean equals(Object obj)                                   | 集合是否相等         |                                            |
| Object[] toArray()                                           | 转成对象数组         |                                            |
| hashCode()                                                   | 获取集合对象的哈希值 |                                            |
| iterator()                                                   | 遍历                 | 返回迭代器对象，用于集合遍历               |



## 2 Iterator迭代器接口

Iterator对象称为迭代器(设计模式的一种)，主要用于遍历 Collection 集合中的元素。

Collection接口继承了java.lang.Iterable接口，该接口有一个iterator()方法，那么所有实现了Collection接口的集合类都有一个iterator()方法，用以返回一个实现了Iterator接口的对象。 

**Iterator 仅用于遍历集合**，Iterator 本身并不提供承装对象的能力。如果需要创建Iterator 对象，则必须有一个被迭代的集合。

**集合对象每次调用iterator()方法都得到一个全新的迭代器对象**，默认游标都在集合的第一个元素之前。





<hr>

## 3 （一）List接口

##### 概述

鉴于Java中数组用来存储数据的局限性，我们通常使用List替代数组

List集合类中**元素有序、且可重复**，集合中的每个元素都有其对应的顺序索引。

List容器中的元素都对应一个整数型的序号记载其在容器中的位置，可以根据序号存取容器中的元素。

JDK API中List接口的实现类常用的有：ArrayList、LinkedList和Vector。

<br>





 List除了从Collection集合继承的方法外，List 集合里添加了一些根据索引来操作集合元素的方法。

<hr>

##### List实现类之一：ArrayList（数组）

ArrayList 是 List 接口的典型实现类、主要实现类

本质上，ArrayList是对象引用的一个”变长”数组

ArrayList的JDK1.8之前与之后的实现区别？

- JDK1.7：ArrayList像饿汉式，直接创建一个初始容量为10的数组
- JDK1.8：ArrayList像懒汉式，一开始创建一个长度为 0 的数组，当添加第一个元素时再创建一个始容量为 10 的数组



Arrays.asList(…) 方法返回的 List 集合，既不是 ArrayList 实例，也不是Vector 实例。 Arrays.asList(…) 返回值是一个固定长度的 List 集合



##### List实现类之二：LinkedList（双向链表）（插入和删除）

对于频繁的插入或删除元素的操作，建议使用LinkedList类，效率较高

**双向链表**，内部没有声明数组，而是定义了 Node 类型的first和last，用于记录首末元素。同时，定义内部类Node，作为LinkedList中保存数据的基本结构。Node除了保存数据，还定义了两个变量：

- prev变量记录前一个元素的位置

- next变量记录下一个元素的位置



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220414215219900.png?w=550)



##### List 实现类之三：Vector

Vector 是一个古老的集合，JDK1.0就有了。大多数操作与ArrayList相同，区别之处在于Vector是线程安全的。



<br>

##### 面试题

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220415224438975.png)



## 4 （二）set接口

Set接口是Collection的子接口，set接口没有提供额外的方法



**Set系列集合特点**

- 无序：存取顺序不一致
- 不重复：可以去除重复（不允许包含相同的元素，根据 equals() 方法判断两个对象是否相同）
- 无索引：没有带索引的方法，所以不能使用普通for循环遍历，也不能通过索引来获取元素。

<br>

**Set集合实现类特点**

-  HashSet : 无序、不重复、无索引。
- LinkedHashSet：有序、不重复、无索引。
- TreeSet：排序、不重复、无索引。

<br>

Set集合的功能上基本上与Collection的API一致。



<hr>

##### Set实现类之一：HashSet

HashSet 是 Set 接口的典型实现，大多数时候使用 Set 集合时都使用这个实现类。

HashSet 按 **Hash 算法**来存储集合中的元素，因此具有很好的存取、查找、删除性能。 

<br>

具有的特点：

- 不能保证元素的排列顺序

- HashSet 不是线程安全的

- 集合元素可以是 null



HashSet 集合判断两个元素相等的标准：两个对象通过 hashCode() 方法比较相等，并且两个对象的 equals() 方法返回值也相等。

对于存放在Set容器中的对象，对应的类一定要重写equals()和hashCode(Object obj)方法，以实现对象相等规则。即：“相等的对象必须具有相等的散列码”。









##### Set实现类之二：LinkedHashSet





<hr>

## 5 map接口

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220411225519826.png?w=600)



### 概述



Map与Collection并列存在。用于保存具有**映射关系**的数据: key-value

Map 中的 key 和 value 都可以是任何引用类型的数据

Map 中的 key 用Set来存放，**不允许重复**，即同一个 Map 对象所对应的类，须重写hashCode()和equals()方法

常用String类作为Map的“键”

key 和 value 之间存在单向一对一关系，即通过指定的 key 总能找到唯一的、确定的 value

 Map接口的常用实现类：HashMap、TreeMap、LinkedHashMap和Properties。其中，HashMap是 Map 接口使用频率最高的实现类



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416100809318.png)



常用方法

| 方法                                | 说明                                       |      |
| ----------------------------------- | ------------------------------------------ | ---- |
| Object put(Object key,Object value) | 将指定key-value添加到(或修改)当前map对象中 |      |
| void putAll(Map m)                  | 将m中的所有key-value对存放到当前map中      |      |
| Object remove(Object key)           | 移除指定key的key-value对，并返回value      |      |
| void clear()                        | 清空当前map中的所有数据                    |      |
| Object get(Object key)              | 获取指定key对应的value                     |      |
| boolean containsKey(Object key)     | 是否包含指定的key                          |      |
| boolean containsValue(Object value) | 是否包含指定的value                        |      |
| int size()                          | 返回map中key-value对的个数                 |      |
| boolean isEmpty()                   | 判断当前map是否为空                        |      |
| boolean equals(Object obj)          | 判断当前map和参数对象obj是否相等           |      |
| Set keySet()                        | 返回所有key构成的Set集合                   |      |
| Collection values()                 | 返回所有value构成的Collection集合          |      |
| Set entrySet()                      | 返回所有key-value对构成的Set集合           |      |

<br>

<hr>

### Map实现类之一：HashMap

HashMap是 Map 接口**使用频率最高**的实现类。

允许使用null键和null值，与HashSet一样，不保证映射的顺序。

所有的key构成的集合是Set:无序的、不可重复的。所以，key所在的类要重写：equals()和hashCode()

所有的value构成的集合是Collection:无序的、可以重复的。所以，value所在的类要重写：equals()

一个key-value构成一个entry

所有的entry构成的集合是Set:无序的、不可重复的

HashMap **判断两个** **key** **相等的标准**是：两个 key 通过 equals() 方法返回 true，hashCode 值也相等。

HashMap **判断两个** **value相等的标准**是：两个 value 通过 equals() 方法返回 true。

<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416101639515.png)

<br>

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220416101712791.png)





HashMap源码中的重要常量

- DEFAULT_INITIAL_CAPACITY : HashMap的默认容量，16
- MAXIMUM_CAPACITY ： HashMap的最大支持容量，2^30
- DEFAULT_LOAD_FACTOR：HashMap的默认加载因子
- TREEIFY_THRESHOLD：Bucket中链表长度大于该默认值，转化为红黑树
- UNTREEIFY_THRESHOLD：Bucket中红黑树存储的Node小于该默认值，转化为链表
- MIN_TREEIFY_CAPACITY：桶中的Node被树化时最小的hash表容量。（当桶中Node的数量大到需要变红黑树时，若hash表容量小于MIN_TREEIFY_CAPACITY时，此时应执行resize扩容操作这个MIN_TREEIFY_CAPACITY的值至少是TREEIFY_THRESHOLD的4倍。）
- table：存储元素的数组，总是2的n次幂
- entrySet：存储具体元素的集
- size：HashMap中存储的键值对的数量
- modCount：HashMap扩容和结构改变的次数。
- threshold：扩容的临界值，=容量*填充因子
- loadFactor：填充因子





### Map实现类之二：LinkedHashMap

LinkedHashMap 是 HashMap 的子类

在HashMap存储结构的基础上，使用了**一对双向链表**来**记录添加元素的顺序**

与LinkedHashSet类似，LinkedHashMap 可以维护 Map 的迭代顺序：迭代顺序与 Key-Value 对的插入顺序一致





### Map实现类之三：TreeMap

TreeMap存储 Key-Value 对时，需要根据 key-value 对进行排序。TreeMap 可以保证所有的 Key-Value 对处于**有序**状态。

TreeSet底层使用**红黑树**结构存储数据

TreeMap判断**两个key相等的标准**：两个key通过compareTo()方法或者compare()方法返回0。







### Map实现类之四：Hashtable

 Hashtable是个古老的 Map 实现类，JDK1.0就提供了。不同于HashMap，Hashtable是线程安全的。



### Map实现类之五：Properties

Properties 类是 Hashtable 的子类，该对象用于处理属性文件

由于属性文件里的 key、value 都是字符串类型，所以 Properties 里的 key 和 value 都是字符串类型

存取数据时，建议使用 setProperty(String key,String value) 方法和 getProperty(String key) 方法



<br>

## 6 Collections工具类

Collections 是一个操作 Set、List 和 Map 等集合的工具类

Collections 中提供了一系列静态的方法对集合元素进行排序、查询和修改等操作，还提供了对集合对象设置不可变、对集合对象实现同步控制等方法

##### 排序（均为static方法）

| 方法                   | 说明                                                     |
| ---------------------- | -------------------------------------------------------- |
| reverse(List)          | 反转 List 中元素的顺序                                   |
| shuffle(List)          | 对 List 集合元素进行随机排序                             |
| sort(List)             | 根据元素的自然顺序对指定 List 集合元素按升序排序         |
| sort(List，Comparator) | 根据指定的 Comparator 产生的顺序对 List 集合元素进行排序 |
| swap(List，int， int)  | 将指定 list 集合中的 i 处元素和 j 处元素进行交换         |





##### 查找、替换

| 方法                                                        | 说明                                                    |
| ----------------------------------------------------------- | ------------------------------------------------------- |
| Object max/min(Collection)                                  | 根据元素的自然顺序，返回给定集合中的最大/小元素         |
| Object max/min(Collection，Comparator)                      | 根据 Comparator 指定的顺序，返回给定集合中的最大/小元素 |
| int frequency(Collection，Object)                           | 返回指定集合中指定元素的出现次数                        |
| void copy(List dest,List src)                               | 将src中的内容复制到dest中                               |
| boolean replaceAll(List list，Object oldVal，Object newVal) | 使用新值替换 List 对象的所有旧值                        |



##### 同步控制

Collections 类中提供了多个 synchronizedXxx() 方法，该方法可使将指定集合包装成线程同步的集合，从而可以解决多线程并发访问集合时的线程安全问题











<hr>

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





#### Map接口系列集合

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







# 七、final

final 关键字是最终的意思，可以修饰（方法，变量，类）

- 修饰方法：表明该方法是最终方法，**不能被重写**。
- 修饰变量：表示该变量第一次赋值后，**不能再次被赋值(有且仅能被赋值一次)**。
- 修饰类：表明该类是最终类，**不能被继承**。

<br>

final修饰变量的注意

- final修饰的变量是基本类型：那么**变量存储的数据值**不能发生改变。

- final修饰的变量是引用类型：那么**变量存储的地址值**不能发生改变，但是地址指向的对象内容是可以发生变化的。





# 八、static



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220413121449979.png)



## 静态关键字static

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





## static应用知识：代码块

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



## static应用知识：单例设计模式

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





## 面向对象三大特征之二：继承

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





# 九、接口与抽象类







# 十、异常类

## 1 异常概述与异常体系结构（Throwable）

Java程序在执行过程中所发生的异常事件可分为两类：

- **Error**：Java虚拟机无法解决的严重问题。如：JVM系统内部错误、资源耗尽等严重情况。比如：StackOverflowError和OOM。一般不编写针对性的代码进行处理。

- **Exception**: 其它因编程错误或偶然的外在因素导致的一般性问题，可以使用针对性的代码进行处理。

<br>

运行时异常

- 是指编译器不要求强制处置的异常。一般是指编程时的逻辑错误，是程序员应该积极避免其出现的异常。

- **java.lang.RuntimeException**类及它的子类都是运行时异常。

- 对于这类异常，可以不作处理，因为这类异常很普遍，若全处理可能会对程序的可读性和运行效率产生影响。



编译时异常

- 是指编译器要求必须处置的异常。即程序在运行时由于外界因素造成的一般性异常。编译器要求Java程序必须捕获或声明所有编译时异常。

- 对于这类异常，如果程序不处理，可能会带来意想不到的结果。



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220414205421838.png)



## 2 常见异常

-  java.lang.RuntimeException
  - ClassCastException
  - ArrayIndexOutOfBoundsException
  - NullPointerException
  - ArithmeticException
  - NumberFormatException
  - InputMismatchException

-  java.io.IOExeption
  - FileNotFoundException
  - EOFException
  - java.lang.ClassN



- java.lang.ClassNotFoundException
- java.lang.InterruptedException 
- java.io.FileNotFoundException
- java.sql.SQLException



## 3 异常处理机制一：try-catch-finally

Java异常处理机制：Java采用的异常处理机制，是将异常处理的程序代码集中在一起，与正常的程序代码分开，使得程序简洁、优雅，并易于维护。

- 如果一个方法内抛出异常，该异常对象会被抛给调用者方法中处理。如果异常没有在调用者方法中处理，它继续被抛给这个调用方法的上层方法。这个过程将一直继续下去，直到异常被处理。这一过程称为捕获(catch)异常。

- 如果一个异常回到main()方法，并且main()也不处理，则程序运行终止。 

- 程序员通常只能处理Exception，而对Error无能为力。



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220414211030908.png)







## 4 异常处理机制二：throws声明抛出异常



如果一个方法(中的语句执行时)可能生成某种异常，但是并不能确定如何处理这种异常，则此方法应**显示地**声明抛出异常，表明该方法将不对这些异常进行处理，而由该方法的**调用者**负责处理。 

在方法声明中用throws语句可以声明抛出异常的列表，throws后面的异常类型可以是方法中产生的异常类型，也可以是它的父类。



重写方法不能抛出比被重写方法范围更大的异常类型。在多态的情况下， 对methodA()方法的调用-异常的捕获按父类声明的异常处理。



## 5 手动抛出异常



首先要生成异常类对象，然后通过throw语句实现抛出操作(提交给Java运行环境)。

```java
IOException e = new IOException();
throw e;
```





可以抛出的异常必须是Throwable或其子类的实例。下面的语句在编译时将会产生语法错误：

```java
throw new String("want to throw");
```





## 6 用户自定义异常

一般地，用户自定义异常类都是**RuntimeException**的子类。

自定义异常类通常需要编写几个**重载的构造器**。 

自定义异常需要提供**serialVersionUID**

自定义的异常通过**throw抛出**。

自定义异常最重要的是异常类的名字，当异常出现时，可以根据名字判断异常类型。



# 十一、IO流



Java程序中，对于数据的输入/输出操作以“流(stream)” 的方式进行。

java.io包下提供了各种“流”类和接口，用以获取不同种类的数据，并通过标准的方法输入或输出数据。

<br>

分类：

按操作数据单位不同分为：字节流(8 bit)，字符流(16 bit)

按数据流的流向不同分为：输入流，输出流

按流的角色的不同分为：节点流，处理流

| 抽象基类 | 字节流       | 字符流 |
| -------- | ------------ | ------ |
| 输入流   | InputStream  | Reader |
| 输出流   | OutputStream | Writer |



Java的IO流共涉及 40 多个类，实际上非常规则，都是从如下4个抽象基类派生的。

由这四个类派生出来的子类名称都是以其父类名作为子类名后缀。

<br>



| 方法                                   | 说明                                                         |
| -------------------------------------- | ------------------------------------------------------------ |
| int read()                             | 从输入流中读取数据的下一个字节。返回 0 到 255 范围内的 int 字节值。如果因为已经到达流末尾而没有可用的字节，则返回值 -1。 |
| int read(byte[] b)                     | 从此输入流中将最多 b.length 个字节的数据读入一个 byte 数组中。 |
| int read(byte[] b, int off,int len)    | 将输入流中最多 len 个数据字节读入 byte 数组。                |
| public void close() throws IOException | 关闭此输入流并释放与该流关联的所有系统资源。                 |



## 1 节点流（文件流）

读取文件

```bash
# 1.建立一个流对象，将已存在的一个文件加载进流。
FileReader fr = new FileReader(new File(“Test.txt”));
# 2.创建一个临时存放数据的数组。
char[] ch = new char[1024];
# 3.调用流对象的读取方法将流中的数据读入到数组中。
fr.read(ch);
# 4. 关闭资源。
fr.close();
```

<br>

```java
FileReader fr = null;
try {
    fr = new FileReader(new File("c:\\test.txt"));
    char[] buf = new char[1024];
    int len;
    while ((len = fr.read(buf)) != -1) {
    		System.out.print(new String(buf, 0, len));
    }
} catch (IOException e) {
		System.out.println("read-Exception :" + e.getMessage());
} finally {
    if (fr != null) {
        try {
        		fr.close();
        } catch (IOException e) {
        		System.out.println("close-Exception :" + e.getMessage());
        } 
    } 
}
```



写入文件

```bash
# 1.创建流对象，建立数据存放文件
FileWriter fw = new FileWriter(new File(“Test.txt”));
# 2.调用流对象的写入方法，将数据写入流
fw.write(“atguigu-songhongkang”);
# 3.关闭流资源，并将流中的数据清空到文件中。
fw.close();
```

<br>

```java
FileWriter fw = null;
try {
    fw = new FileWriter(new File("Test.txt"));
    fw.write("atguigu-songhongkang");
} catch (IOException e) {
    e.printStackTrace();
} finally {
    if (fw != null){
    		try {
    				fw.close();
    		} catch (IOException e) {
    				e.printStackTrace();
    		} 
    }
}
```





定义文件路径时，可以用“/”或者“\\”。 

在写入一个文件时，如果使用构造器FileOutputStream(file)，则**目录下有同名文件将被覆盖。**如果使用构造器FileOutputStream(file,true)，则目录下的同名文件不会被覆盖，**在文件内容末尾追加内容。**

字节流操作字节，比如：.mp3，.avi，.rmvb，mp4，.jpg，.doc，.ppt



## 2 处理流之一：缓冲流

为了提高数据读写的速度，Java API提供了带缓冲功能的流类，在使用这些流类时，会创建一个内部缓冲区数组，缺省使用8192个字节(8Kb)的缓冲区。

缓冲流要“套接”在相应的节点流之上，根据数据操作单位可以把缓冲流分为：



- BufferedInputStream 和 BufferedOutputStream
- BufferedReader 和 BufferedWriter





## 3 处理流之二：转换流



## 4 处理流之三：标准输入、输出流



## 5 处理流之四：打印流











#### 6 处理流之五：数据流













#### 7 处理流之六：对象流





#### NIO





# 十二、枚举类与注解



类的对象只有有限个，确定的。举例如下：

- 星期：Monday(星期一)、......、Sunday(星期天) 
- 性别：Man(男)、Woman(女) 
- 季节：Spring(春节)......Winter(冬天) 
- 支付方式：Cash（现金）、WeChatPay（微信）、Alipay(支付宝)、BankCard(银行卡)、CreditCard(信用卡) 
- 就职状态：Busy、Free、Vocation、Dimission
- 订单状态：Nonpayment（未付款）、Paid（已付款）、Delivered（已发货）、Return（退货）、Checked（已确认）Fulfilled（已配货）
- 线程状态：创建、就绪、运行、阻塞、死亡

<br>

当需要定义一组常量时，强烈建议使用枚举类！

<br>

JDK1.5之前需要自定义枚举类

JDK 1.5 新增的 enum 关键字用于定义枚举类

```java
class Season{
    private final String SEASONNAME;//季节的名称
    private final String SEASONDESC;//季节的描述
    private Season(String seasonName,String seasonDesc){
    		this.SEASONNAME = seasonName;
    		this.SEASONDESC = seasonDesc; 
    }
    public static final Season SPRING = new Season("春天", "春暖花开");
    public static final Season SUMMER = new Season("夏天", "夏日炎炎");
    public static final Season AUTUMN = new Season("秋天", "秋高气爽");
    public static final Season WINTER = new Season("冬天", "白雪皑皑");
}
```









未来的开发模式都是基于注解的，JPA是基于注解的，Spring2.5以上都是基于注解的，Hibernate3.x以后也是基于注解的，现在的Struts2有一部分也是基于注解的了，注解是一种趋势，一定程度上可以说：**框架 = 注解 + 反射 + 设计模式**。





自定义 Annotation



定义新的 Annotation 类型使用 @interface 关键字



@Retention: 只能用于修饰一个 Annotation 定义, 用于指定该 Annotation 的生命周期, @Rentention 包含一个 RetentionPolicy 类型的成员变量, 使用@Rentention 时必须为该 value 成员变量指定值:

- RetentionPolicy.SOURCE:在源文件中有效（即源文件保留），编译器直接丢弃这种策略的注释
- RetentionPolicy.CLASS:在class文件中有效（即class保留） ， 当运行 Java 程序时, JVM 不会保留注解。 这是默认值
- RetentionPolicy.RUNTIME:在运行时有效（即运行时保留），当运行 Java 程序时, JVM 会保留注释。程序可以通过反射获取该注释。



@Target: 用于修饰 Annotation 定义, 用于指定被修饰的 Annotation 能用于修饰哪些程序元素。 @Target 也包含一个名为 value 的成员变量。











# 十三、Java9&Java10&Java11新特性



自从 2017 年 9 月 21 日 Java 9 正式发布之时，Oracle 就宣布今后会按照每六个月一次的节奏进行更新，在过去的几个月中，我们见证了其兑现了诺言，但万万没想到，苦了大批迎头而上的开发者们。





#### Java9新特性

经过4次跳票，历经曲折的Java 9 终于终于在2017年9月21日发布。 









#### Java10新特性

需要注意的是 Java 9 和 Java 10 都不是 LTS (Long-Term-Support) 版本。和过去的 Java 大版本升级不同，这两个只有半年左右的开发和维护期。而未来的 **Java 11**，也就是 18.9 LTS，才是 Java 8 之后第一个 LTS 版本。









#### Java11新特性

北京时间 2018年9 月 26 日，Oracle 官方宣布 Java 11 正式发布。这是 Java 大版本周期变化后的第一个长期支持版本，非常值得关注。



















