import java.util.concurrent.*;

public class ThreadTest {
    public static void main(String[] args) {
        ThreadObject1 th1 = new ThreadObject1("首个线程");
        ThreadObject2 th2 = new ThreadObject2("次个线程");
        th1.start();
        th2.start();
        th1.interrupt();
        th2.interrupt();
        ThreadPoolExecutor thExecutor = new ThreadPoolExecutor(4, 10, 1000, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        ThreadFactory thFactory = thExecutor.getThreadFactory();
    }
}



class ThreadObject1 extends Thread{

    public ThreadObject1(String name) {
        super(name);
    }

    @Override
    public void start() {
        System.out.println("线程一启动:" + this.getName());
        super.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i ++) {
            System.out.println("这是一个线程:" + currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ThreadObject2 extends Thread{

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
        for (int i = 0; i < 100; i ++) {
            System.out.println("这是二个线程" + this.getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
