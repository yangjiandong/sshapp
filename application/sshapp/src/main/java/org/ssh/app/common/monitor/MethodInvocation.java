/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssh.app.common.monitor;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An POJO contains the method invocation information.
 *
 * <p>
 * <b>This class is thread-safe by utilizing the {@literal java.util.concurrent}. </b>
 *
 * </p>
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class MethodInvocation implements Serializable {

    private static Log log = LogFactory.getLog(MethodInvocation.class);
    private AtomicLong count = new AtomicLong(0L);
    private AtomicLong avgTime = new AtomicLong(0L);
    private AtomicLong minTime = new AtomicLong(Long.MAX_VALUE);
    private AtomicLong maxTime = new AtomicLong(0L);
    private AtomicLong exceptionCount = new AtomicLong(0L);
    private final String name;

    public MethodInvocation(String name) {
        this.name = name;
    }

    public long getCount() {
        return count.longValue();
    }

    public long getAvgTime() {
        return avgTime.longValue();
    }

    public long getMinTime() {
        return minTime.longValue();
    }

    public long getMaxTime() {
        return maxTime.longValue();
    }

    public long getExceptionCount() {
        return exceptionCount.longValue();
    }

    /**
     * this is the name of this monitor, usually, it is the mingled name from the method and its enclosing class.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * When the pre-setup method for the {@link MethodInvocationInterceptor} is executed, this method will be called.
     * @param executionTime how long the method to finish
     * @param isSuccess if the method invocation was successful
     */
    public void onExecute(long executionTime, boolean isSuccess) {
        try {
            // (avgTime * count + executionTime) / (count + 1);
            this.avgTime = new AtomicLong((avgTime.longValue() * count.longValue() + executionTime) / (count.longValue() + 1));
            this.count.incrementAndGet();
            if (this.minTime.longValue() > executionTime) {
                this.minTime.set(executionTime);
            }
            if (this.maxTime.longValue() < executionTime) {
                this.maxTime.set(executionTime);
            }

            if (isSuccess == false) {
                this.exceptionCount.incrementAndGet();
            }
        } catch (Throwable t) {
            // in case of any issues, such as overflow, we should reset all the values.
            log.error("MethodInvocationMonitor: onExecute with error : " + t.getMessage());
            reset();
        }
    }

    private void reset() {
        this.count = new AtomicLong(0L);
        this.maxTime = new AtomicLong(0L);
        this.minTime = new AtomicLong(Long.MAX_VALUE);
        this.avgTime = new AtomicLong(0L);
        this.exceptionCount = new AtomicLong(0L);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(name)
                .append("[")
                .append("count=").append(count).append(", ")
                .append("avgTime=").append(avgTime).append(",")
                .append("minTime=").append(minTime.longValue() == Long.MAX_VALUE ? 0 : minTime).append(", ")
                .append("maxTime=").append(maxTime).append(",")
                .append("exceptionCount=").append(exceptionCount).append(", ")
                .append("]");

        return buffer.toString();
    }
}
