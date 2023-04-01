package commons.thread;

import java.util.concurrent.*;

/**
 * @author guor
 */
public class GlobalExecutor {

    private static final int MAX_TASKS_NUMBER = Integer.max(Runtime.getRuntime().availableProcessors() / 2, 2);

    private static final ScheduledExecutorService COMMON_EXECUTOR = new ScheduledThreadPoolExecutor(MAX_TASKS_NUMBER, new CustomThreadFactory());

    public static void runWithoutThread(Runnable command) {
        command.run();
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return COMMON_EXECUTOR.submit(task);
    }

    public static void execute(Runnable command) {
        COMMON_EXECUTOR.execute(command);
    }

    public static ScheduledFuture<?> schedule(Runnable command, long delayMs) {
        return COMMON_EXECUTOR.schedule(command, delayMs, TimeUnit.MILLISECONDS);
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delayMs) {
        return COMMON_EXECUTOR.schedule(callable, delayMs, TimeUnit.MILLISECONDS);
    }

    public static ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelayMs, long delayMs) {
        return COMMON_EXECUTOR.scheduleWithFixedDelay(command, initialDelayMs, delayMs, TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period) {
        return COMMON_EXECUTOR.scheduleWithFixedDelay(command, initialDelay, period, TimeUnit.MILLISECONDS);
    }
}
