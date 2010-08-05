package org.ssh.app.common.monitor;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * The implementation of {@link MethodInvocationMonitorMXBean} to be exposed as JMX Bean.
 *
 * <p>
 * <b>This class is thread-safe by utilizing the {@literal java.util.concurrent}. </b>
 *
 * </p>
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@ManagedResource(objectName = "org.ssh.app.common.monitor:name=methodInvocationMonitor", description = "A Monitor Bean store the statistics of the method invocation.")
public final class MethodInvocationMonitorMXBeanImpl implements MethodInvocationMonitorMXBean {

    private final Map<String, MethodInvocation> statistics = new ConcurrentHashMap<String, MethodInvocation>();

    @Override
    public Map<String, MethodInvocation> getStatistics() {
        return Collections.unmodifiableMap(statistics);
    }

    @Override
    @ManagedOperation(description = "Output the statistics of all interceptored methods, the execution time in 'ms'")
    public String print() {
        return toString();
    }

    @Override
    public void addStatistic(String name, long executionTime, boolean isSuccess) {
        if (statistics.containsKey(name)) {
            // method already in the map
            statistics.get(name).onExecute(executionTime, isSuccess);
        } else {
            // a new one
            MethodInvocation methodInvocation = new MethodInvocation(name);
            // it is threadsafe because we are using java.util.concurrent type
            statistics.put(name, methodInvocation);

            methodInvocation.onExecute(executionTime, isSuccess);
        }
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        for (MethodInvocation methodInvocationBean : statistics.values()) {
            buffer.append(methodInvocationBean.toString());
            buffer.append(", ");
        }
        buffer.append("}");

        return buffer.toString();
    }
}
