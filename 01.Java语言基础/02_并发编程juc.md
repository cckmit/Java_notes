# 写在前面



## 推荐

### 文章



### 视频

黑马



### 书籍

《offer来了》第三章 Java 并发编程



# 1 Java 线程的创建方式



## 继承 Thread 类创建线程

```java
// 1, 为什么不直接调用了run方法，而是调用start启动线程。
// 直接调用run方法会当成普通方法执行，此时相当于还是单线程执行。
// 只有调用start方法才是启动一个新的线程执行。
    
public class ThreadDemo1 {

    public static void main(String[] args) {
        // 3, 实例化新线程对象，调用start()方法启动线程
        Thread myThread = new MyThread();
        // start()方法是一个native方法，通过在操作系统上启动一个新线程，并最终执行run方法来启动一个线程。
        // 通知CPU以线程的方式启动run()方法
        myThread.start();

        for (int i = 0; i < 5; i++) {
            System.out.println("主线程执行输出+++" + i);
        }
    }

}

// Thread类 implements实现了 Runnable接口，并定义了操作线程的一些方法。
// 1,定义一个线程类继承Thread（创建了一个线程）
class MyThread extends Thread{
    
    // 2,重写run方法，里面是定义线程以后要干啥
    @Override
    public void run() {
        //super.run();
        for (int i = 0; i < 5; i++) {
            System.out.println("子线程执行输出..." + i);
        }
    }
}
```

## 实现Runnable接口



## 通过ExecutorService和Callable`<Class>`实现有返回值的线程

```java
public class ThreadDemo33 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建固定大小的线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);
        // 创建有多个返回值的任务列表list
        List<Future> list = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            // 创建Callable任务对象
            MyCallable33 myCallable = new MyCallable33(i);
            // 提交线程，获取Future对象并将其保存到Future List中
            Future future = pool.submit(myCallable);
            list.add(future);
        }
        // 关闭线程池,等待线程执行结束
        pool.shutdownNow();
        // 遍历所有线程的运行结果
        for (Future future : list) {
            System.out.println(future.get().toString());
        }

    }
}

// 1, 定义一个任务类，实现Callable接口，应该申明线程任务执行完毕后返回结果的数据类型
class MyCallable33 implements Callable<String>{
    private final int n;

    public MyCallable33(int n) {
        this.n = n;
    }

    // 2, 重写call()方法（任务方法）
    @Override
    public String call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        // 查看下当前线程的名字和id
        System.out.println(Thread.currentThread().getName() + "&&id: " + Thread.currentThread().getId());
        
        return "子线程执行结果是：" + sum + "";
    }
}
```



## 基于线程池

创建一个线程池并用该线程池提交线程任务。

# 2 线程池的工作原理

程序中，我们会用各种池化技术来缓存付出昂贵代价创建的对象，比如线程池、连接池、内存池。一般是预先创建一些对象放入池中，使用的时候直接取出使用，用完归还以便复用，还会通过一定的策略调整池中缓存对象的数量，实现池的动态伸缩。

由于线程的创建比较昂贵，随意、没有控制地创建大量线程会造成性能问题，因此短平快的任务一般考虑使用线程池来处理，而不是直接创建线程。



## 核心组件（4个）和核心类

- 线程池管理器（用于创建并管理线程池）
- 工作线程（线程池中执行具体任务的线程）
- 任务接口（用于定义工作线程的调度和执行策略）
- 任务队列（存放待处理的任务）



线程池的体系结构

 java.util.concurrent.Executor : 负责线程的使用与调度的根接口

```lua
 |--ExecutorService 子接口：线程池的主要接口
   |--ThreadPoolExecutor 线程池的实现类
   |--ScheduledExecutorService 子接口：负责线程的调度
     |--ScheduledThreadPoolExecutor ：继承 ThreadPoolExecutor， 实现 ScheduledExecutorService
```



ThreadPoolExecutor 是构建线程的核心方法，定义如下

```java
// ThreadPoolExecutor 的构造方法
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) {
   ........................
}
```

参数说明

| 序号 | 参数            | 说明                                             |
| ---- | --------------- | ------------------------------------------------ |
| 1    | corePoolSize    | 核心线程数量                                     |
| 2    | maximumPoolSize | 最大线程数量                                     |
| 3    | keepAliveTime   | 当前线程数大于corePoolSize时，空闲线程的存活时间 |
| 4    | unit            | keepAliveTime的时间单位                          |
| 5    | workQueue       | 任务队列，被提交但尚未被执行的任务存放的地方     |
| 6    | threadFactory   | 线程工厂，用于创建线程                           |
| 7    | handler         | 任务拒绝策略                                     |

### Java 线程池的工作流程



### 线程池的拒绝策略

# 3 5种常用的线程池

Java定义了 `Executor`接口并在该接口中定义了`execute()` 方法（唯一一个方法）用于执行一个线程任务，然后通过 ExecutorService 继承Executor接口并执行具体的线程操作。

ExecutorService接口有多个实现类可用于创建不同的线程池，如下所示是5种常用的线程池。

| 名称                    | 说明                          |
| ----------------------- | ----------------------------- |
| newCachedThreadPool     | 可缓存的线程池                |
| newFixedThreadPool      | 固定大小的线程池              |
| newScheduledThreadPool  | 可做任务调度的线程池          |
| newSingleThreadExecutor | 单个线程的线程池              |
| newWorkStealingPool     | 足够大小的线程池，JDK 1.8新增 |



## newScheduledThreadPool（可定时调度）

newScheduledThreadPool创建了一个可定时调度的线程池，可设置在给定的延迟时间后执行或者定期执行某个线程任务：

```java
public class ScheduledThreadPool {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);

        // 1 创建一个延迟3秒执行的线程
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("delay 3 seconds execute.");
            }
        },3, TimeUnit.SECONDS);
        
        // 2 创建一个延迟1秒执行且每3秒执行一次的线程
        //scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
        //    @Override
        //    public void run() {
        //        System.out.println("delay 1 seconds. repeat execute every 3 seconds");
        //    }
        //}, 1, 3, TimeUnit.SECONDS);

        scheduledThreadPool.shutdown();
    }

}
```





## newSingleThreadExecutor（永远有且只有一个可用的）

保证永远有且只有一个可用的线程，在该线程停止或发生异常时，newSingleThreadExecutor线程池会启动一个新的线程来代替该线程继续执行任务

```java
ExecutorService singleThread = Executors.newSingleThreadExecutor();
```







# 4 线程池使用注意

通过三个生产事故，来看看使用线程池应该注意些什么。

## 线程池的声明需要手动进行

**Java** 中的 **Executors** 类定义了一些快捷的工具方法，来帮助我们快速创建线程池。《阿里巴巴Java开发手册》中提到，禁止使用这些方法来创建线程池，而应该手动 **new ThreadPoolExecutor** 来创建线程池。

这一条规则的背后，是大量血淋淋的生产事故，最典型的就是 **newFixedThreadPool** 和**newCachedThreadPool** ，可能因为资源耗尽导致 **OOM** 问题。

首先，我们来看一下 **newFixedThreadPool** 为什么可能会出现 **OOM** 的问题。

我们写一段测试代码，来初始化一个单线程的 **FixedThreadPool** ，循环1亿次向线程池提交任务，每个任务都会创建一个比较大的字符串然后休眠一小时：

```java
@GetMapping("oom1")
public void oom1() throws InterruptedException {
ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
//打印线程池的信息，稍后我会解释这段代码
printStats(threadPool); 
for (int i = 0; i < 100000000; i++) {
    threadPool.execute(() -> {
        String payload = IntStream.rangeClosed(1, 1000000)
                .mapToObj(__ -> "a")
                .collect(Collectors.joining("")) + UUID.randomUUID().toString();
        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
        }
        log.info(payload);
    });
}
 
threadPool.shutdown();
threadPool.awaitTermination(1, TimeUnit.HOURS);
 
}
```

执行程序后不久，日志中就出现了如下 **OOM** ：

```bash
Exception in thread "http-nio-45678-ClientPoller" java.lang.OutOfMemoryError: GC overhead limit exceeded
```

翻看 **newFixedThreadPool** 方法的源码不难发现，线程池的工作队列直接 **new** 了一个 **LinkedBlockingQueue**，而默认构造方法的 **LinkedBlockingQueue** 是一个 **Integer.MAX_VALUE** 长度的队列，可以认为是无界的：

```java
public static ExecutorService newFixedThreadPool(int nThreads) {
return new ThreadPoolExecutor(nThreads, nThreads,
0L, TimeUnit.MILLISECONDS,
new LinkedBlockingQueue<Runnable>());
}
public class LinkedBlockingQueue<E> extends AbstractQueue<E>
implements BlockingQueue<E>, java.io.Serializable {
...
/**
 * Creates a {@code LinkedBlockingQueue} with a capacity of
 * {@link Integer#MAX_VALUE}.
 */
public LinkedBlockingQueue() {
    this(Integer.MAX_VALUE);
}
 
...
}
```

虽然使用 **newFixedThreadPool** 可以把工作线程控制在固定的数量上，但任务队列是无界的。如果任务较多并且执行较慢的话，队列可能会快速积压，撑爆内存导致OOM。

<br>

我们再把刚才的例子稍微改一下，改为使用 **newCachedThreadPool** 方法来获得线程池。程序运行不久后，同样看到了如下 **OOM** 异常：

```bash
[11:30:30.487] [http-nio-45678-exec-1] [ERROR] [.a.c.c.C.[.[.[/].[dispatcherServlet]:175 ] - Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Handler dispatch failed; nested exception is java.lang.OutOfMemoryError: unable to create new native thread] with root cause
java.lang.OutOfMemoryError: unable to create new native thread
```

从日志中可以看到，这次 **OOM** 的原因是无法创建线程，翻看 **newCachedThreadPool** 的源码可以看到，这种线程池的最大线程数是**Integer.MAX_VALUE**，可以认为是没有上限的，而其工作队列 **SynchronousQueue** 是一个没有存储空间的阻塞队列。这意味着，只要有请求到来，就必须找到一条工作线程来处理，如果当前没有空闲的线程就再创建一条新的。

由于我们的任务需要 **1** 小时才能执行完成，大量的任务进来后会创建大量的线程。我们知道线程是需要分配一定的内存空间作为线程栈的，比如 **1MB**，因此无限制创建线程必然会导致 **OOM**：

```java
public static ExecutorService newCachedThreadPool() {
	return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
}
```

其实，大部分 **Java** 开发同学知道这两种线程池的特性，只是抱有侥幸心理，觉得只是使用线程池做一些轻量级的任务，不可能造成队列积压或开启大量线程。

但，现实往往是残酷的。

我之前就遇到过这么一个事故：用户注册后，我们调用一个外部服务去发送短信，发送短信接口正常时可以在 **100** 毫秒内响应，TPS **100**的注册量，**CachedThreadPool** 能稳定在占用 **10** 个左右线程的情况下满足需求。在某个时间点，外部短信服务不可用了，我们调用这个服务的超时又特别长，比如1分钟，1分钟可能就进来了 **6000** 用户，产生 **6000** 个发送短信的任务，需要 **6000** 个线程，没多久就因为无法创建线程导致了 **OOM**，整个应用程序崩溃。

<br>

因此，我同样不建议使用 **Executors** 提供的两种快捷的线程池，原因如下：

我们需要根据自己的场景、并发情况来评估线程池的几个核心参数，包括核心线程数、最大线程数、线程回收策略、工作队列的类型，以及拒绝策略，确保线程池的工作行为符合需求，一般都需要设置有界的工作队列和可控的线程数。

任何时候，都应该为自定义线程池指定有意义的名称，以方便排查问题。当出现线程数量暴增、线程死锁、线程占用大量CPU、线程执行出现异常等问题时，我们往往会抓取线程栈。此时，有意义的线程名称，就可以方便我们定位问题。

除了建议手动声明线程池以外，我还**建议用一些监控手段来观察线程池的状态**。线程池这个组件往往会表现得任劳任怨、默默无闻，除非是出现了拒绝策略，否则压力再大都不会抛出一个异常。如果我们能提前观察到线程池队列的积压，或者线程数量的快速膨胀，往往可以提早发现并解决问题。



## 线程池线程管理策略详解

在之前的 **Demo** 中，我们用一个 **printStats** 方法实现了最简陋的监控，每秒输出一次线程池的基本内部信息，包括线程数、活跃线程数、完成了多少任务，以及队列中还有多少积压任务等信息：

```java
private void printStats(ThreadPoolExecutor threadPool) {
   Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
        log.info("=========================");
        log.info("Pool Size: {}", threadPool.getPoolSize());
        log.info("Active Threads: {}", threadPool.getActiveCount());
        log.info("Number of Tasks Completed: {}", threadPool.getCompletedTaskCount());
        log.info("Number of Tasks in Queue: {}", threadPool.getQueue().size());
		log.info("=========================");
	}, 0, 1, TimeUnit.SECONDS);
}
```

接下来，我们就利用这个方法来观察一下线程池的基本特性吧。

首先，自定义一个线程池。这个线程池具有 **2** 个核心线程、**5** 个最大线程、使用容量为 **10** 的 **ArrayBlockingQueue** 阻塞队列作为工作队列，使用默认的**AbortPolicy** 拒绝策略，也就是任务添加到线程池失败会抛出 **RejectedExecutionException** 。此外，我们借助了 **Jodd** 类库的 **ThreadFactoryBuilder** 方法来构造一个线程工厂，实现线程池线程的自定义命名。

然后，我们写一段测试代码来观察线程池管理线程的策略。测试代码的逻辑为，每次间隔 **1** 秒向线程池提交任务，循环 **20** 次，每个任务需要 **10** 秒才能执行完成，代码如下：

```java
@GetMapping("right")
public int right() throws InterruptedException {
//使用一个计数器跟踪完成的任务数
AtomicInteger atomicInteger = new AtomicInteger();
//创建一个具有2个核心线程、5个最大线程，使用容量为10的ArrayBlockingQueue阻塞队列作为工作队列的线程池，使用默认的AbortPolicy拒绝策略
ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
2, 5,
5, TimeUnit.SECONDS,
new ArrayBlockingQueue<>(10),
new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").get(),
new ThreadPoolExecutor.AbortPolicy());
printStats(threadPool);
//每隔1秒提交一次，一共提交20次任务
IntStream.rangeClosed(1, 20).forEach(i -> {
    try {
        TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    int id = atomicInteger.incrementAndGet();
    try {
        threadPool.submit(() -> {
            log.info("{} started", id);
            //每个任务耗时10秒
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
            }
            log.info("{} finished", id);
        });
    } catch (Exception ex) {
        //提交出现异常的话，打印出错信息并为计数器减一
        log.error("error submitting task {}", id, ex);
        atomicInteger.decrementAndGet();
    }
});
 
TimeUnit.SECONDS.sleep(60);
return atomicInteger.intValue();
 
}
```

**60** 秒后页面输出了 **17**，有3次提交失败了：

![image-20220601145842933](/Users/cat/Library/Application%20Support/typora-user-images/image-20220601145842933.png)



并且日志中也出现了 **3** 次类似的错误信息：

```bash
[14:24:52.879] [http-nio-45678-exec-1] [ERROR] [.t.c.t.demo1.ThreadPoolOOMController:103 ] - error submitting task 18
java.util.concurrent.RejectedExecutionException: Task java.util.concurrent.FutureTask@163a2dec rejected from java.util.concurrent.ThreadPoolExecutor@18061ad2[Running, pool size = 5, active threads = 5, queued tasks = 10, completed tasks = 2]
```



我们把 **printStats** 方法打印出的日志绘制成图表，得出如下曲线：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220517082642564.png)

至此，我们可以总结出线程池默认的工作行为：

不会初始化 **corePoolSize** 个线程，有任务来了才创建工作线程；

- 当核心线程满了之后不会立即扩容线程池，而是把任务堆积到工作队列中；
- 当工作队列满了后扩容线程池，一直到线程个数达到 **maximumPoolSize** 为止；
- 如果队列已满且达到了最大线程后还有任务进来，按照拒绝策略处理；
- 当线程数大于核心线程数时，线程等待 **keepAliveTime** 后还是没有任务需要处理的话，收缩线程到核心线程数。

了解这个策略，有助于我们根据实际的容量规划需求，为线程池设置合适的初始化参数。当然，我们也可以通过一些手段来改变这些默认工作行为，比如：

- 声明线程池后立即调用 **prestartAllCoreThreads** 方法，来启动所有核心线程；
- 传入 **true** 给 **allowCoreThreadTimeOut** 方法，来让线程池在空闲的时候同样回收核心线程。





# 5 线程的生命周期



线程的生命周期分为新建（New）、就绪（Runnable）、运行（Running）、阻塞（Blocked） 和死亡（Dead）这 5种状态。

在系统运行过程中不断有新的线程被创建，旧的线程在执行完毕后被 清理，线程在排队获取共享资源或者锁时将被阻塞，因此运行中的线程会在就绪、阻塞、运行状态之间来回切换。线程的具体状态转化流程如图所示。



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220601152944877.png)





# 6 线程的基本方法

线程相关的基本方法有wait、notify、notifyAll、sleep、join、yield等，这些方法控制线程的运行，并影响线程的状态变化。

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220601153635190.png)





## 线程等待 wait方法

调用wait方法的线程会进入WAITING状态，只有等到其他线程的通知或被中断后才会返回。

需要注意的是，在调用wait方法后会释放对象的锁，因此wait方法一般被用于同步方法或同步代码块中。

## 线程睡眠 sleep方法

调用sleep方法会导致当前线程休眠。

与wait方法不同的是，sleep方法不会释放当前占有的锁，会导致线程进入TIMED-WATING状态，而wait方法会导致当前线程进入WATING状态。





# 7 Java中的 锁🔐











## 2. 进程与线程

- 进程和线程的概念
- 并行和并发的概念
- 线程基本应用

<hr>



### 进程与线程

### 并行与并发

### 应用











## 3. Java线程

- 创建和运行线程
- 查看线程
- 线程 API
- 线程状态

<hr>

### 3.1 创建和运行线程

#### 方法一，直接使用 Thread

```java
// 创建线程对象
Thread t = new Thread() {
		public void run() {
		// 要执行的任务
 		}
};
// 启动线程
t.start();
```

例如：

```java
// 构造方法的参数是给线程指定名字，推荐
Thread t1 = new Thread("t1") {
		@Override
		// run 方法内实现了要执行的任务
		public void run() {
				log.debug("hello");
 		}
};
t1.start();
```

输出：

```java
19:19:00 [t1] c.ThreadStarter - hello
```





#### 方法二，使用 Runnable 配合 Thread

把【线程】和【任务】（要执行的代码）分开

- Thread 代表线程

- Runnable 可运行的任务（线程要执行的代码）

```java
Runnable runnable = new Runnable() {
    public void run(){
        // 要执行的任务
    }
};
// 创建线程对象
Thread t = new Thread( runnable );
// 启动线程
t.start();
```

例如：

```java
// 创建任务对象
Runnable task2 = new Runnable() {
    @Override
    public void run() {
        log.debug("hello");
     }
};
// 参数1 是任务对象; 参数2 是线程名字，推荐
Thread t2 = new Thread(task2, "t2");
t2.start();
```

输出：

```java
19:19:00 [t2] c.ThreadStarter - hello
```



Java 8 以后可以使用 lambda 精简代码

```java
// 创建任务对象
Runnable task2 = () -> log.debug("hello");
// 参数1 是任务对象; 参数2 是线程名字，推荐
Thread t2 = new Thread(task2, "t2");
t2.start();
```





#### `*`原理之 Thread 与 Runnable 的关系

分析 Thread 的源码，理清它与 Runnable 的关系

- 方法1 是把线程和任务合并在了一起，方法2 是把线程和任务分开了
- 用 Runnable 更容易与线程池等高级 API 配合
- 用 Runnable 让任务类脱离了 Thread 继承体系，更灵活





#### 方法三，FutureTask 配合 Thread

FutureTask 能够接收 Callable 类型的参数，用来处理有返回结果的情况

```java
// 创建任务对象
FutureTask<Integer> task3 = new FutureTask<>(() -> {
    log.debug("hello");
    return 100;
});
// 参数1 是任务对象; 参数2 是线程名字，推荐
new Thread(task3, "t3").start();
// 主线程阻塞，同步等待 task 执行完毕的结果
Integer result = task3.get();
log.debug("结果是:{}", result);
```

输出：

```bash
19:22:27 [t3] c.ThreadStarter - hello
19:22:27 [main] c.ThreadStarter - 结果是:100
```





### 3.2 观察多个线程同时运行

主要是理解
- 交替执行
- 谁先谁后，不由我们控制



### 3.3 查看进程线程的方法

#### windows

- 任务管理器可以查看进程和线程数，也可以用来杀死进程
- tasklist 查看进程
- taskkill 杀死进程

#### linux

- ps -fe 查看所有进程
- ps -fT -p <PID> 查看某个进程（PID）的所有线程
- kill 杀死进程
- top 按大写 H 切换是否显示线程
- top -H -p <PID> 查看某个进程（PID）的所有线程

#### Java

- jps 命令查看所有 Java 进程
- jstack <PID> 查看某个 Java 进程（PID）的所有线程状态
- jconsole 来查看某个 Java 进程中线程的运行情况（图形界面）



jconsole 远程监控配置
- 需要以如下方式运行你的 java 类
```java
java -Djava.rmi.server.hostname=`ip地址` -Dcom.sun.management.jmxremote -
Dcom.sun.management.jmxremote.port=`连接端口` -Dcom.sun.management.jmxremote.ssl=是否安全连接 -
Dcom.sun.management.jmxremote.authenticate=是否认证 java类
```
- 修改 /etc/hosts 文件将 127.0.0.1 映射至主机名

如果要认证访问，还需要做如下步骤
- 复制 jmxremote.password 文件
- 修改 jmxremote.password 和 jmxremote.access 文件的权限为 600 即文件所有者可读写
- 连接时填入 controlRole（用户名），R&D（密码）



### 3.4 原理之线程运行

#### 栈与栈帧

Java Virtual Machine Stacks （Java 虚拟机栈）

我们都知道 JVM 中由堆、栈、方法区所组成，其中栈内存是给谁用的呢？其实就是线程，每个线程启动后，虚拟机就会为其分配一块栈内存。

- 每个栈由多个栈帧（Frame）组成，对应着每次方法调用时所占用的内存
- 每个线程只能有一个活动栈帧，对应着当前正在执行的那个方法



#### 线程上下文切换（Thread Context Switch）

因为以下一些原因导致 cpu 不再执行当前的线程，转而执行另一个线程的代码

- 线程的 cpu 时间片用完
- 垃圾回收
- 有更高优先级的线程需要运行
- 线程自己调用了 sleep、yield、wait、join、park、synchronized、lock 等方法

当 Context Switch 发生时，需要由操作系统保存当前线程的状态，并恢复另一个线程的状态，Java 中对应的概念就是程序计数器（Program Counter Register），它的作用是记住下一条 jvm 指令的执行地址，是线程私有的
- 状态包括程序计数器、虚拟机栈中每个栈帧的信息，如局部变量、操作数栈、返回地址等
- Context Switch 频繁发生会影响性能





### 3.5 常用方法

| 方法名           | static | 功能说明                                                     | 注意                                                         |
| ---------------- | ------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| start()          |        | 启动一个新线程，在新的线程运行 run 方法中的代码              | start 方法只是让线程进入就绪，里面代码不一定立刻运行（CPU 的时间片还没分给它）。每个线程对象的start方法只能调用一次，如果调用了多次会出现IllegalThreadStateException |
| run()            |        | 新线程启动后会调用的方法                                     | 如果在构造 Thread 对象时传递了 Runnable 参数，则线程启动后会调用 Runnable 中的 run 方法，否则默认不执行任何操作。但可以创建 Thread 的子类对象，来覆盖默认行为 |
| join()           |        |                                                              | 等待线程运行结束                                             |
| join(long n)     |        |                                                              | 等待线程运行结束,最多等待 n 毫秒                             |
| getId()          |        | 获取线程长整型的 id                                          | id 唯一                                                      |
| getName()        |        | 获取线程名                                                   |                                                              |
| setName(String)  |        | 修改线程名                                                   |                                                              |
| getPriority()    |        | 获取线程优先级                                               |                                                              |
| setPriority(int) |        | 修改线程优先级                                               | java中规定线程优先级是1~10 的整数，较大的优先级能提高该线程被 CPU 调度的机率 |
| getState()       |        | 获取线程状态                                                 | Java 中线程状态是用 6 个 enum 表示，分别为：NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED |
| isInterrupted()  |        | 判断是否被打断                                               | 不会清除 打断标记                                            |
| isAlive()        |        | 线程是否存活（还没有运行完毕）                               |                                                              |
| interrupt()      |        | 打断线程                                                     | 如果被打断线程正在 sleep，wait，join 会导致被打断的线程抛出 InterruptedException，并清除 打断标记 ；如果打断的正在运行的线程，则会设置 打断标记 ；park 的线程被打断，也会设置 打断标记 |
| interrupted()    | static | 判断当前线程是否被打断                                       | 会清除 打断标记                                              |
| currentThread()  | static | 获取当前正在执行的线程                                       |                                                              |
| sleep(long n)    | static | 让当前执行的线程休眠n毫秒，休眠时让出 cpu 的时间片给其它线程 |                                                              |
| yield()          | static | 提示线程调度器让出当前线程对CPU的使用                        | 主要是为了测试和调试                                         |





### 3.6 start与run

#### 调用run

```java
public static void main(String[] args) {
    Thread t1 = new Thread("t1") {
        @Override
        public void run() {
            log.debug(Thread.currentThread().getName());
            FileReader.read(Constants.MP4_FULL_PATH);
        }
    };
    t1.run();
    log.debug("do other things ...");
}
```

输出

```java
19:39:14 [main] c.TestStart - main
19:39:14 [main] c.FileReader - read [1.mp4] start ...
19:39:18 [main] c.FileReader - read [1.mp4] end ... cost: 4227 ms
19:39:18 [main] c.TestStart - do other things ...
```

程序仍在 main 线程运行， FileReader.read() 方法调用还是同步的



#### 调用start

将上述代码的 t1.run() 改为

```java
t1.start();
```

输出

```java
19:41:30 [main] c.TestStart - do other things ...
19:41:30 [t1] c.TestStart - t1
19:41:30 [t1] c.FileReader - read [1.mp4] start ...
19:41:35 [t1] c.FileReader - read [1.mp4] end ... cost: 4542 ms
```

程序在 t1 线程运行， FileReader.read() 方法调用是异步的



#### 总结

- 直接调用 run 是在主线程中执行了 run，没有启动新的线程

- 使用 start 是启动新的线程，通过新的线程间接执行 run 中的代码



### 3.7 sleep与yield

#### sleep

1. 调用 sleep 会让当前线程从 Running 进入 Timed Waiting 状态（阻塞）
2. 其它线程可以使用 interrupt 方法打断正在睡眠的线程，这时 sleep 方法会抛出 InterruptedException
3. 睡眠结束后的线程未必会立刻得到执行
4. 建议用 TimeUnit 的 sleep 代替 Thread 的 sleep 来获得更好的可读性

#### yield

1. 调用 yield 会让当前线程从 Running 进入 Runnable 就绪状态，然后调度执行其它线程
2. 具体的实现依赖于操作系统的任务调度器



#### 线程优先级

- 线程优先级会提示（hint）调度器优先调度该线程，但它仅仅是一个提示，调度器可以忽略它
- 如果 cpu 比较忙，那么优先级高的线程会获得更多的时间片，但 cpu 闲时，优先级几乎没作用

```java
Runnable task1 = () -> {
    int count = 0;
    for (;;) {
        System.out.println("---->1 " + count++);
     }
};
Runnable task2 = () -> {
    int count = 0;
    for (;;) {
        // Thread.yield();
        System.out.println(" ---->2 " + count++);
     }
};
Thread t1 = new Thread(task1, "t1");
Thread t2 = new Thread(task2, "t2");
// t1.setPriority(Thread.MIN_PRIORITY);
// t2.setPriority(Thread.MAX_PRIORITY);
t1.start();
t2.start();
```







### 3.8 join方法详解

#### 为什么需要 join

下面的代码执行，打印 r 是什么？



```java
static int r = 0;
public static void main(String[] args) throws InterruptedException {
    test1();
}

private static void test1() throws InterruptedException {
    log.debug("开始");
    Thread t1 = new Thread(() -> {
        log.debug("开始");
        sleep(1);
        log.debug("结束");
        r = 10;
    });
    t1.start();
    log.debug("结果为:{}", r);
    log.debug("结束");
}
```



分析
- 因为主线程和线程 t1 是并行执行的，t1 线程需要 1 秒之后才能算出 r=10
- 而主线程一开始就要打印 r 的结果，所以只能打印出 r=0

解决方法
- 用 sleep 行不行？为什么？
- 用 join，加在 t1.start() 之后即可



#### `*`应用之同步（案例1）

以调用方角度来讲，如果

- 需要等待结果返回，才能继续运行就是同步
- 不需要等待结果返回，就能继续运行就是异步



<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220301111701975.png" style="zoom: 33%;" />





#### 等待多个结果

问，下面代码 cost 大约多少秒？

```java
static int r1 = 0;
static int r2 = 0;
public static void main(String[] args) throws InterruptedException {
    test2();
}

private static void test2() throws InterruptedException {
    Thread t1 = new Thread(() -> {
        sleep(1);
        r1 = 10;
     });

    Thread t2 = new Thread(() -> {
        sleep(2);
        r2 = 20;
     });
     
    long start = System.currentTimeMillis();
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    long end = System.currentTimeMillis();
    log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
}
```



分析如下
- 第一个 join：等待 t1 时, t2 并没有停止, 而在运行
- 第二个 join：1s 后, 执行到此, t2 也运行了 1s, 因此也只需再等待 1s

如果颠倒两个 join 呢？
最终都是输出

```java
20:45:43.239 [main] c.TestJoin - r1: 10 r2: 20 cost: 2005
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220301112416524.png)





#### 有时效的 join

等够时间

```java
static int r1 = 0;
static int r2 = 0;
public static void main(String[] args) throws InterruptedException {
    test3();
}
public static void test3() throws InterruptedException {
    Thread t1 = new Thread(() -> {
        sleep(1);
        r1 = 10;
     });
    long start = System.currentTimeMillis();
    t1.start();
    // 线程执行结束会导致 join 结束
    t1.join(1500);
    long end = System.currentTimeMillis();
    log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
}
```

输出：

```java
20:48:01.320 [main] c.TestJoin - r1: 10 r2: 0 cost: 1010
```



没等够时间

```java
static int r1 = 0;
static int r2 = 0;
public static void main(String[] args) throws InterruptedException {
    test3();
}
public static void test3() throws InterruptedException {
    Thread t1 = new Thread(() -> {
        sleep(2);
        r1 = 10;
     });
    long start = System.currentTimeMillis();
    t1.start();
    // 线程执行结束会导致 join 结束
    t1.join(1500);
    long end = System.currentTimeMillis();
    log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
}
```


输出：
```java
20:52:15.623 [main] c.TestJoin - r1: 0 r2: 0 cost: 1502
```



### 3.9 interrupt 方法详解

#### 打断 sleep，wait，join 的线程

这几个方法都会让线程进入阻塞状态
打断 sleep 的线程, 会清空打断状态，以 sleep 为例

```java
private static void test1() throws InterruptedException {
    Thread t1 = new Thread(()->{
        sleep(1);
     }, "t1");
    t1.start();
    sleep(0.5);
    t1.interrupt();
    log.debug(" 打断状态: {}", t1.isInterrupted());
}
```

输出：

```java
java.lang.InterruptedException: sleep interrupted
    at java.lang.Thread.sleep(Native Method)
    at java.lang.Thread.sleep(Thread.java:340)
    at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
    at cn.itcast.n2.util.Sleeper.sleep(Sleeper.java:8)
    at cn.itcast.n4.TestInterrupt.lambda$test1$3(TestInterrupt.java:59)
    at java.lang.Thread.run(Thread.java:745)
21:18:10.374 [main] c.TestInterrupt - 打断状态: false
```



#### 打断正常运行的线程

打断正常运行的线程, 不会清空打断状态

```java
private static void test2() throws InterruptedException {
    Thread t2 = new Thread(()->{
        while(true) {
            Thread current = Thread.currentThread();
            boolean interrupted = current.isInterrupted();
            if(interrupted) {
                log.debug(" 打断状态: {}", interrupted);
                break;
             }
         }
     }, "t2");
    t2.start();
    
    sleep(0.5);
    t2.interrupt();
}
```

输出：

```java
20:57:37.964 [t2] c.TestInterrupt - 打断状态: true
```



#### 打断 park 线程

打断 park 线程, 不会清空打断状态

```java
private static void test3() throws InterruptedException {
    Thread t1 = new Thread(() -> {
        log.debug("park...");
        LockSupport.park();
        log.debug("unpark...");
        log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
     }, "t1");
    t1.start();
    
    sleep(0.5);
    t1.interrupt();
}
```

输出：

```java
21:11:52.795 [t1] c.TestInterrupt - park... 
21:11:53.295 [t1] c.TestInterrupt - unpark... 
21:11:53.295 [t1] c.TestInterrupt - 打断状态：true
```

如果打断标记已经是 true, 则 park 会失效

```java
private static void test4() {
    Thread t1 = new Thread(() -> {
        for (int i = 0; i < 5; i++) {
            log.debug("park...");
            LockSupport.park();
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
         }
     });
    t1.start();
    
    sleep(1);
    t1.interrupt();
}
```

输出：

```java
21:13:48.783 [Thread-0] c.TestInterrupt - park... 
21:13:49.809 [Thread-0] c.TestInterrupt - 打断状态：true 
21:13:49.812 [Thread-0] c.TestInterrupt - park... 
21:13:49.813 [Thread-0] c.TestInterrupt - 打断状态：true 
21:13:49.813 [Thread-0] c.TestInterrupt - park... 
21:13:49.813 [Thread-0] c.TestInterrupt - 打断状态：true 
21:13:49.813 [Thread-0] c.TestInterrupt - park... 
21:13:49.813 [Thread-0] c.TestInterrupt - 打断状态：true 
21:13:49.813 [Thread-0] c.TestInterrupt - park... 
21:13:49.813 [Thread-0] c.TestInterrupt - 打断状态：true
```

> 提示
>
> 可以使用 Thread.interrupted() 清除打断状态



### 3.10 不推荐的方法

还有一些不推荐使用的方法，这些方法已过时，容易破坏同步代码块，造成线程死锁

| 方法名    | static | 功能说明             |
| --------- | ------ | -------------------- |
| stop()    |        | 停止线程运行         |
| suspend() |        | 挂起（暂停）线程运行 |
| resume()  |        | 恢复线程运行         |



### 3.11 主线程与守护线程

默认情况下，Java 进程需要等待所有线程都运行结束，才会结束。有一种特殊的线程叫做守护线程，只要其它非守

护线程运行结束了，即使守护线程的代码没有执行完，也会强制结束。



例：

```java
log.debug("开始运行...");
Thread t1 = new Thread(() -> {
    log.debug("开始运行...");
    sleep(2);
    log.debug("运行结束...");
}, "daemon");
// 设置该线程为守护线程
t1.setDaemon(true);
t1.start();

sleep(1);
log.debug("运行结束...");
```

输出：

```java
08:26:38.123 [main] c.TestDaemon - 开始运行... 
08:26:38.213 [daemon] c.TestDaemon - 开始运行... 
08:26:39.215 [main] c.TestDaemon - 运行结束...
```

> **注意**
>
> - 垃圾回收器线程就是一种守护线程
>
> - Tomcat 中的 Acceptor 和 Poller 线程都是守护线程，所以 Tomcat 接收到 shutdown 命令后，不会等待它们处理完当前请求



### 3.12 五种状态

这是从 **操作系统** 层面来描述的

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220301135718727.png)

【初始状态】仅是在语言层面创建了线程对象，还未与操作系统线程关联

【可运行状态】（就绪状态）指该线程已经被创建（与操作系统线程关联），可以由 CPU 调度执行

【运行状态】指获取了 CPU 时间片运行中的状态
当 CPU 时间片用完，会从【运行状态】转换至【可运行状态】，会导致线程的上下文切换

【阻塞状态】

- 如果调用了阻塞 API，如 BIO 读写文件，这时该线程实际不会用到 CPU，会导致线程上下文切换，进入【阻塞状态】

- 等 BIO 操作完毕，会由操作系统唤醒阻塞的线程，转换至【可运行状态】
- 与【可运行状态】的区别是，对【阻塞状态】的线程来说只要它们一直不唤醒，调度器就一直不会考虑调度它们

【终止状态】表示线程已经执行完毕，生命周期已经结束，不会再转换为其它状态



### 3.13 六种状态

这是从 **Java API** 层面来描述的

根据 Thread.State 枚举，分为六种状态

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220301142504871.png?w=600)



- `NEW` 线程刚被创建，但是还没有调用 start() 方法
- `RUNNABLE `当调用了 start() 方法之后，注意，Java API 层面的 RUNNABLE 状态涵盖了 操作系统 层面的【可运行状态】、【运行状态】和【阻塞状态】（由于 BIO 导致的线程阻塞，在 Java 里无法区分，仍然认为是可运行）
- `BLOCKED` ， `WAITING` ， `TIMED_WAITING` 都是 Java API 层面对【阻塞状态】的细分，后面会在状态转换一节详述
- `TERMINATED` 当线程代码运行结束



<hr>



### 习题









### 本章小结

本章的重点在于掌握

- 线程创建
- 线程重要 api，如 start，run，sleep，join，interrupt 等
- 线程状态
- 应用方面

> 异步调用：主线程执行期间，其它线程异步执行耗时操作
>
> 提高效率：并行计算，缩短运算时间
>
> 同步等待：join
>
> 统筹规划：合理使用线程，得到最优效果

- 原理方面

> 线程运行流程：栈、栈帧、上下文切换、程序计数器
>
> Thread 两种创建方式 的源码

- 模式方面

> 终止模式之两阶段终止





















<hr>

## 4. 共享模型之管程

- 共享问题
- synchronized
- 线程安全分析
- Monitor
- wait/notify
- 线程状态转换
- 活跃性
- Lock

<hr>

### 4.1 共享带来的问题

#### 小故事









#### Java的体现

两个线程对初始值为 0 的静态变量一个做自增，一个做自减，各做 5000 次，结果是 0 吗？

```java
static int counter = 0;

public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
        for (int i = 0; i < 5000; i++) {
            counter++;
         }
     }, "t1");

    Thread t2 = new Thread(() -> {
        for (int i = 0; i < 5000; i++) {
            counter--;
         }
     }, "t2");

    t1.start();
    t2.start();
    t1.join();
    t2.join();
    log.debug("{}",counter);
}
```



#### 问题分析

以上的结果可能是正数、负数、零。为什么呢？因为 Java 中对静态变量的自增，自减并不是原子操作，要彻底理解，必须从字节码来进行分析
例如对于 i++ 而言（i 为静态变量），实际会产生如下的 JVM 字节码指令：

```bash
getstatic i // 获取静态变量i的值
iconst_1 // 准备常量1
iadd // 自增
putstatic i // 将修改后的值存入静态变量i
```

而对应 i-- 也是类似：
```bash
getstatic i // 获取静态变量i的值
iconst_1 // 准备常量1
isub // 自减
putstatic i // 将修改后的值存入静态变量i
```



而 Java 的内存模型如下，完成静态变量的自增，自减需要在主存和工作内存中进行数据交换：







如果是单线程,以上8行代码是顺序执行（不会交错）没有问题：





但多线程下这 8 行代码可能交错运行：
出现负数的情况：





出现正数的情况：















#### 临界区Critical Section

- 一个程序运行多个线程本身是没有问题的
- 问题出在多个线程访问共享资源
>多个线程读共享资源其实也没有问题
>在多个线程对共享资源读写操作时发生指令交错，就会出现问题
- 一段代码块内如果存在对共享资源的多线程读写操作，称这段代码块为临界区



例如，下面代码中的临界区

```java
static int counter = 0;

static void increment() 
// 临界区
{ 
    counter++; 
}

static void decrement() 
// 临界区
{ 
    counter--; 
}
```





#### 竞态条件Race Condition

多个线程在临界区内执行，由于代码的**执行序列不同**而导致结果无法预测，称之为发生了**竞态条件**



<hr>

### 4.2 synchronized 解决方案

#### `*`应用之互斥

为了避免临界区的竞态条件发生，有多种手段可以达到目的。

- 阻塞式的解决方案：synchronized，Lock
- 非阻塞式的解决方案：原子变量



本次课使用阻塞式的解决方案：synchronized，来解决上述问题，即俗称的【对象锁】，它采用互斥的方式让同一时刻至多只有一个

线程能持有【对象锁】，其它线程再想获取这个【对象锁】时就会阻塞住。这样就能保证拥有锁的线程可以安全的执行临界区内的代

码，不用担心线程上下文切换



> 注意
> 虽然 java 中互斥和同步都可以采用 synchronized 关键字来完成，但它们还是有区别的：
>
> - 互斥是保证临界区的竞态条件发生，同一时刻只能有一个线程执行临界区代码
> - 同步是由于线程执行的先后、顺序不同、需要一个线程等待其它线程运行到某个点



#### synchronized

语法
```java
synchronized(对象) // 线程1， 线程2(blocked)
{
    临界区
}
```



解决
```java
static int counter = 0;
static final Object room = new Object();

public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
        for (int i = 0; i < 5000; i++) {
            synchronized (room) {
                counter++;
            }
        }
    }, "t1");

    Thread t2 = new Thread(() -> {
        for (int i = 0; i < 5000; i++) {
            synchronized (room) {
                counter--;
            }
        }
    }, "t2");

    t1.start();
    t2.start();
    t1.join();
    t2.join();
    log.debug("{}",counter);
}
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220301145711239.png)



你可以做这样的类比：

- synchronized(对象) 中的对象，可以想象为一个房间（room），有唯一入口（门）房间只能一次进入一人进行计算，线程 t1，t2 想象成两个人
- 当线程 t1 执行到 synchronized(room) 时就好比 t1 进入了这个房间，并锁住了门拿走了钥匙，在门内执行count++ 代码
- 这时候如果 t2 也运行到了 synchronized(room) 时，它发现门被锁住了，只能在门外等待，发生了上下文切换，阻塞住了
- 这中间即使 t1 的 cpu 时间片不幸用完，被踢出了门外（不要错误理解为锁住了对象就能一直执行下去哦），这时门还是锁住的，t1 仍拿着钥匙，t2 线程还在阻塞状态进不来，只有下次轮到 t1 自己再次获得时间片时才能开门进入
- 当 t1 执行完 synchronized{} 块内的代码，这时候才会从 obj 房间出来并解开门上的锁，唤醒 t2 线程把钥匙给他。t2 线程这时才可以进入 obj 房间，锁住了门拿上钥匙，执行它的 count-- 代码



用图来表示

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220301150125957.png)





#### 思考

synchronized 实际是用对象锁保证了临界区内代码的原子性，临界区内的代码对外是不可分割的，不会被线程切换所打断。

为了加深理解，请思考下面的问题

- 
  如果把 synchronized(obj) 放在 for 循环的外面，如何理解？-- 原子性
- 如果 t1 synchronized(obj1) 而 t2 synchronized(obj2) 会怎样运作？-- 锁对象
- 如果 t1 synchronized(obj) 而 t2 没有加会怎么样？如何理解？-- 锁对象





#### 面向对象改进

把需要保护的共享变量放入一个类

```java
class Room {
    int value = 0;

    public void increment() {
        synchronized (this) {
            value++;
        }
    }

    public void decrement() {
        synchronized (this) {
            value--;
        }
    }

    public int get() {
        synchronized (this) {
            return value;
        }
    }
}


@Slf4j
public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int j = 0; j < 5000; j++) {
                room.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int j = 0; j < 5000; j++) {
                room.decrement();
            }
        }, "t2");
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("count: {}" , room.get());
    }
}
```





### 4.3 方法上的synchronized

```java
class Test{
    public synchronized void test() {

    }
}

等价于

class Test{
    public void test() {
        synchronized(this) {
        
        }
    }
}
```



```java
class Test{
    public synchronized static void test() {

    }
}

等价于

class Test{
    public static void test() {
        synchronized(Test.class) {

        }
    }
}
```



#### 不加 synchronzied 的方法

不加 synchronzied 的方法就好比不遵守规则的人，不去老实排队（好比翻窗户进去的）



#### 所谓的“线程八锁”

其实就是考察 synchronized 锁住的是哪个对象



##### 成员变量和静态变量是否线程安全？

- 如果它们没有共享，则线程安全
- 如果它们被共享了，根据它们的状态是否能够改变，又分两种情况
>如果只有读操作，则线程安全
>如果有读写操作，则这段代码是临界区，需要考虑线程安全



##### 局部变量是否线程安全？
- 局部变量是线程安全的
- 但局部变量引用的对象则未必
> 如果该对象没有逃离方法的作用访问，它是线程安全的
> 如果该对象逃离方法的作用范围，需要考虑线程安全



##### 局部变量线程安全分析

```java
public static void test1() {
    int i = 10; 
    i++; 
}
```

每个线程调用 test1() 方法时局部变量 i，会在每个线程的栈帧内存中被创建多份，因此不存在共享







### 4.4 变量的线程安全分析





### 4.5 习题



### 4.6 Monitor概念







### wait notify



### wait notify的正确姿势



### Park & Unpark



### 重新理解线程状态转换



### 多把锁

### 活跃性

### ReentrantLock

### 本章小结



















## 5. 共享模型之内存

上一章讲解的 Monitor 主要关注的是访问共享变量时，保证临界区代码的原子性

这一章我们进一步深入学习共享变量在多线程间的【可见性】问题与多条指令执行时的【有序性】问题

<hr>









## 6. 共享模型之无锁

## 7. 共享模型之不可变

## 8. 共享模型之工具







## 参考

https://www.bilibili.com/video/BV16J411h7Rd?p=12&spm_id_from=pageDriver