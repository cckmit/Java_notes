package thread_app;

/**
 * @author cat
 * @description
 * @date 2022/4/11 下午9:36
 */


// 1, 为什么不直接调用了run方法，而是调用start启动线程。
// 直接调用run方法会当成普通方法执行，此时相当于还是单线程执行。
// 只有调用start方法才是启动一个新的线程执行。


public class ThreadDemo1 {

    public static void main(String[] args) {
        // 3, 创建新线程对象，调用start()方法
        Thread myThread = new MyThread();
        // 通知CPU以线程的方式启动run()方法
        myThread.start();

        for (int i = 0; i < 5; i++) {
            System.out.println("主线程执行输出+++" + i);
        }
    }

}

/**
 * 1,定义一个线程类继承Thread
 */
class MyThread extends Thread{
    /**
     * 2,编写run方法，里面是定义线程以后要干啥
     */
    @Override
    public void run() {
        //super.run();
        for (int i = 0; i < 5; i++) {
            System.out.println("子线程执行输出..." + i);
        }
    }
}



