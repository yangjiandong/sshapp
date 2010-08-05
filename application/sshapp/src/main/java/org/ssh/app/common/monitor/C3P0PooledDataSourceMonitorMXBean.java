/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssh.app.common.monitor;

import com.mchange.v2.c3p0.PooledDataSource;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * C3P0 <a href="http://www.mchange.com/projects/c3p0/apidocs/com/mchange/v2/c3p0/PooledDataSource.html">PooledDataSource</a>
 * Bean implements the monitor interface {@link PoolDataSourceMonitorMXBean}.
 * <p>
 * If the data-source set up in spring application context is not {@literal C3P0}, the value for these JMX attributes
 * will be '0', the default value of the declaration type.
 * </p>
 * It intends to be exposed as JMX Bean.
 * <p>
 * <b>This class is immutable class</b>
 * </p>
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@ManagedResource(objectName = "org.ssh.app.common.monitor:name=c3P0PooledDataSourceMonitor", description = "C3P0 PooledDataSource Monitor Bean")
public final class C3P0PooledDataSourceMonitorMXBean implements PoolDataSourceMonitorMXBean {

    private static Log log = LogFactory.getLog(C3P0PooledDataSourceMonitorMXBean.class);

    private final PooledDataSource pooledDataSource;

    public C3P0PooledDataSourceMonitorMXBean(PooledDataSource pooledDataSource) {
        this.pooledDataSource = pooledDataSource;
    }

    @Override
    @ManagedAttribute(description = "the number of Connections in the pool that are active (or said checked out)")
    public int getActiveConnections() {
        int activeConnections = Integer.MIN_VALUE;
        try {
            activeConnections = pooledDataSource.getNumBusyConnectionsDefaultUser();
        } catch (SQLException ex) {
            log.error("Failure to get ActiveConnections from C3P0", ex);
        }

        return activeConnections;
    }

    @Override
    @ManagedAttribute(description = "the number of Connections in the pool that are currently available for checkout")
    public int getIdleConnections() {
        int idleConnections = Integer.MIN_VALUE;

        try {
            idleConnections = pooledDataSource.getNumIdleConnectionsDefaultUser();
        } catch (SQLException ex) {
            log.error("Failure to get idleConnections from C3P0", ex);
        }

        return idleConnections;
    }

    @Override
    @ManagedAttribute(description = "the total number of Connections in the pool: The invariant idleConnections + activeConnections == totalConnections should always hold.")
    public int getTotalConnections() {
        int totalConnections = Integer.MIN_VALUE;

        try {
            totalConnections = pooledDataSource.getNumConnectionsDefaultUser();
        } catch (SQLException ex) {
            log.error("Failure to get totalConnections from C3P0", ex);
        }

        return totalConnections;
    }

    /**
     * In fact, this method should be {@literal synchronized}, but it is immutable.
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("C3P0PooledDataSourceMonitor[")
        .append("activeConnections=").append(getActiveConnections()).append(", ")
        .append("idleConnections=").append(getIdleConnections()).append(", ")
        .append("totalConnections=").append(getTotalConnections())
        .append("]");

        return buffer.toString();
    }
}
