import org.junit.Test;

import java.util.concurrent.*;

public class ThreadTest {
    @Test
    public void runThread() {
        /*ThreadObject1 th1 = new ThreadObject1("首个线程");
        ThreadObject2 th2 = new ThreadObject2("次个线程");
        th1.start();
        th2.start();
        th1.interrupt();
        th2.interrupt();*/
        // 使用线程池通过工厂模式创建线程
        ThreadPoolExecutor thExecutor = new ThreadPoolExecutor(4, 10, 1000, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        ThreadFactory thFactory = thExecutor.getThreadFactory();
        Thread th3 = thFactory.newThread(new Runnable() {

            int count = 0;

            @Override
            public void run() {
                for (; count < 10; count ++) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("这是第三个线程，此时count=" + count);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread th4 = thFactory.newThread(new Runnable() {

            int count = 0;

            @Override
            public void run() {
                for (; count < 10; count ++) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("这是第四个线程，此时count=" + count);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("第四个线程final");
                    }
                    System.out.println("这是第四个线程，此时count=" + count);
                }
            }
        });
        th3.start();
        th4.setDaemon(true);
        th4.start();
        try {
            System.out.println("第三个线程优先级为:" + th3.getPriority() + ",第四个线程优先级为:" + th4.getPriority());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 线程join(),暂时不会用
    @Test
    public void testJoin() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(4, 10, 1000, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        ThreadFactory threadFactory = pool.getThreadFactory();
        Thread th1 = threadFactory.newThread(new ThreadObject1());
        Thread th2 = threadFactory.newThread(new ThreadObject2());
        try {
            th2.join(1000);
            th2.start();
            th1.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



class ThreadObject1 extends Thread{

    public ThreadObject1(String name) {
        super(name);
    }

    public ThreadObject1() {}

    @Override
    public void start() {
        System.out.println("线程一启动:" + this.getName());
        super.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i ++) {
            System.out.println("这是第一个线程:" + currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ThreadObject2 extends Thread{

    public ThreadObject2(){}

    public ThreadObject2(String name) {
        super(name);
    }

    @Override
    public void start() {
        System.out.println("线程二启动:" + this.getName());
        super.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i ++) {
            System.out.println("这是第二个线程:" + this.getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
