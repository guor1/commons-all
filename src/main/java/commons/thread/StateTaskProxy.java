package commons.thread;

import commons.utils.DateUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 有状态的任务代理
 *
 * @author guor
 */
@Slf4j
@Getter
public class StateTaskProxy implements Runnable {
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 完成时间
     */
    private Date completedTime;

    private final Runnable realTask;

    public StateTaskProxy(Runnable realTask) {
        this.realTask = realTask;
    }

    /**
     * 定义线程执行流程，记录完成时间
     */
    @Override
    public void run() {
        this.beginTime = DateUtils.now();
        try {
            this.realTask.run();
        } finally {
            this.completedTime = DateUtils.now();
            log.info("执行耗时:{}", this.getElapsedTime());
        }
    }

    /**
     * 任务已执行时间（ms）
     *
     * @return 返回任务已执行时间，单位毫秒
     */
    public long getElapsedTime() {
        if (getCompletedTime() != null) {
            return getCompletedTime().getTime() - getBeginTime().getTime();
        } else {
            return DateUtils.now().getTime() - getBeginTime().getTime();
        }
    }

    /**
     * 线程是否执行完成
     *
     * @return 完成返回true，否则返回false
     */
    public boolean isCompleted() {
        return getCompletedTime() != null;
    }
}
