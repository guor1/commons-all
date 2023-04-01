package commons.thread;

import java.util.concurrent.TimeUnit;

/**
 * 一个支持阻塞的线程池，线程都是延迟执行，只有到了该执行的时刻才会被调用 支持从线程池中删除线程
 *
 * @author guor
 */
public interface FluidThreadPoolExecutor {

    /**
     * 向线程池中，提交一个线程，按照固定的延迟周期执行
     *
     * @param command 线程对象
     * @param delay   延迟周期
     * @param unit    时间单位
     */
    void scheduleWithFixedDelay(Runnable command, long delay, TimeUnit unit);

    /**
     * 从线程池中，删除一个线程对象
     *
     * @param command 被删除的对象
     */
    void remove(Runnable command);
}
