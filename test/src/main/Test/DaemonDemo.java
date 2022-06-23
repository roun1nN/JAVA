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
}
