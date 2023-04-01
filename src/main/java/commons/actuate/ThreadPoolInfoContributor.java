package commons.actuate;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池监控端点
 * <pre>
 *     public InfoContributor infoContributor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
 *         return new ThreadPoolInfoContributor(threadPoolTaskExecutor);
 *     }
 * </pre>
 *
 * @author guorui1
 */
public class ThreadPoolInfoContributor implements InfoContributor {
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public ThreadPoolInfoContributor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Override
    public void contribute(Info.Builder builder) {
        //当前线程数
        builder.withDetail("poolSize", threadPoolTaskExecutor.getPoolSize());
        //核心线程数
        builder.withDetail("corePoolSize", threadPoolTaskExecutor.getCorePoolSize());
        //最大线程数
        builder.withDetail("maxPoolSize", threadPoolTaskExecutor.getMaxPoolSize());
        //任务队列容量
        builder.withDetail("queueCapacity", threadPoolTaskExecutor.getQueueCapacity());
        //任务队列长度
        builder.withDetail("queueSize", threadPoolTaskExecutor.getQueueSize());
        //当前线程数
        builder.withDetail("activeCount", threadPoolTaskExecutor.getActiveCount());
        //线程KeepAlive时长
        builder.withDetail("keepAliveSeconds", threadPoolTaskExecutor.getKeepAliveSeconds());
        //所有线程数
        builder.withDetail("allActiveCount", Thread.activeCount());
    }
}
