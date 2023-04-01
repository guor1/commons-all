package commons.thread.impl;

import commons.thread.FluidThread;
import commons.thread.FluidThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author guorui1
 */
@Slf4j
public class FluidThreadPoolExecutorImpl implements FluidThreadPoolExecutor {

    private final DelayQueue<FluidThread> dq = new DelayQueue<>();

    private final int corePoolSize;

    private final ReentrantLock mainLock = new ReentrantLock();

    private final AtomicLong sequencer = new AtomicLong(0);

    private final ThreadFactory threadFactory;

    private static final int NONE_DELAY = 0;

    public FluidThreadPoolExecutorImpl(int corePoolSize) {
        this(corePoolSize, Executors.defaultThreadFactory());
    }

    public FluidThreadPoolExecutorImpl(int corePoolSize, ThreadFactory threadFactory) {
        this.corePoolSize = corePoolSize;
        this.threadFactory = threadFactory;
    }

    @Override
    public void scheduleWithFixedDelay(Runnable command, long delay, TimeUnit unit) {
        FluidThread scheduleTask = decorateTask(command, delay, unit);
        delayedExecute(scheduleTask);
    }

    @Override
    public void remove(Runnable command) {
        dq.remove(decorateTask(command, NONE_DELAY, TimeUnit.MILLISECONDS));
    }

    private void delayedExecute(FluidThread task) {
        dq.add(task);
        ensureStart();
    }

    private synchronized void ensureStart() {
        int wc = sequencer.intValue();
        if (wc < corePoolSize) {
            addWorker();
        }
    }

    private void addWorker() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            threadFactory.newThread(new Worker()).start();
        } finally {
            mainLock.unlock();
        }
        sequencer.incrementAndGet();
    }

    protected FluidThread decorateTask(Runnable command, long delay, TimeUnit unit) {
        return new FluidThread(command, delay, unit);
    }

    class Worker implements Runnable {

        @Override
        public void run() {
            //noinspection InfiniteLoopStatement
            while (true) {
                try {
                    FluidThread task = getTask();
                    task.run();
                    task.reset();
                    dq.add(task);
                } catch (InterruptedException e) {
                    log.error("线程中断", e);
                }
            }
        }

        private FluidThread getTask() throws InterruptedException {
            return dq.take();
        }
    }
}
