package commons.thread;

import commons.thread.impl.FluidThreadPoolExecutorImpl;

import java.util.concurrent.ThreadFactory;

/**
 * @author guorui1
 */
public class FluidThreadPoolFactory {

    public static FluidThreadPoolExecutor newThreadPool(int corePoolSize) {
        return new FluidThreadPoolExecutorImpl(corePoolSize);
    }

    public static FluidThreadPoolExecutor newThreadPool(int corePoolSize, ThreadFactory threadFactory) {
        return new FluidThreadPoolExecutorImpl(corePoolSize, threadFactory);
    }
}
