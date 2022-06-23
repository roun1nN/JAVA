import org.junit.Test;

public class DaemonDemo {

    @Test
    public void testDaemon() {
        Thread daemonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("i am alive");
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("finally block");
                    }
                }
            }
        });
        long curTime = System.currentTimeMillis();
        daemonThread.setDaemon(true);
        daemonThread.start();
        // start执行之后再将线程设置为守护线程会报错,会作为欧普通线程继续执行
//        daemonThread.setDaemon(true);
        //确保main线程结束前能给daemonThread能够分到时间片
        try {
            System.out.println("main中休眠前守护进程执行时间:" + (System.currentTimeMillis() - curTime));
            curTime = System.currentTimeMillis();
            Thread.sleep(800);
            System.out.println("main中休眠时间:" + (System.currentTimeMillis() - curTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMainSleep() {
        // main函数执行后也是一个线程，此处测试主线程中执行当前线程休眠，主线程的执行也会休眠
        for (int i = 1; i < 10; i ++) {
            System.out.println("这是第" + i + "次");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
