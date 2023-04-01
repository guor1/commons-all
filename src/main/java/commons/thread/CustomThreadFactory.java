package commons.thread;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂，可以指定线程名称前缀
 *
 * @author guorui1
 */
public class CustomThreadFactory implements ThreadFactory {

    private final String threadNamePrefix;

    private final AtomicInteger threadCount = new AtomicInteger(0);

    @Override
    public Thread newThread(@Nonnull Runnable r) {
        return new Thread(r, nextThreadName());
    }

    private String nextThreadName() {
        return threadNamePrefix + this.threadCount.incrementAndGet();
    }

    public CustomThreadFactory(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public CustomThreadFactory() {
        this("global-");
    }
}
