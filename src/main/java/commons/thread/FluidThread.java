package commons.thread;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author guorui1
 */
public class FluidThread implements Runnable, Delayed {

    private final Runnable command;

    private final long delay;

    private long start;

    public FluidThread(Runnable command, long delay, TimeUnit unit) {
        this.command = command;
        this.delay = TimeUnit.MILLISECONDS.convert(delay, unit);
        this.start = System.currentTimeMillis();
    }

    public long getDelay() {
        return delay;
    }

    public long getStart() {
        return start;
    }

    @Override
    public void run() {
        this.command.run();
    }

    @Override
    public int compareTo(Delayed o) {
        if (!(o instanceof FluidThread)) {
            return -1;
        }
        FluidThread oo = (FluidThread) o;
        long d = (waitTime() - oo.waitTime());
        return (d == 0) ? (getDelay() < oo.getDelay() ? -1 : 1) : ((d < 0) ? -1 : 1);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(waitTime(), TimeUnit.MILLISECONDS);
    }

    private long waitTime() {
        return getStart() + TimeUnit.MILLISECONDS.convert(getDelay(), TimeUnit.MILLISECONDS) - System.currentTimeMillis();
    }

    public void reset() {
        this.start = System.currentTimeMillis();
    }

    @Override
    public int hashCode() {
        return command.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FluidThread)) {
            return false;
        }
        FluidThread task = (FluidThread) obj;
        return task.hashCode() == this.hashCode();
    }
}
