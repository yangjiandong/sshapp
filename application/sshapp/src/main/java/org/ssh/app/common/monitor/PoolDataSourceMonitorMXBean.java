/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssh.app.common.monitor;

/**
 * This is the interface for JMX Bean that monitors the standalone data source connection pool.
 * For management data source in server side, most of the application servers have been built-in jmx support,
 * so we won't duplicate task here, please refer vendor's document.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface PoolDataSourceMonitorMXBean {

    /**
     * the number of Connections in the pool that are active (or said checked out).
     */
    int getActiveConnections();

    /**
     * the number of Connections in the pool that are currently available for checkout.
     */
    int getIdleConnections();

    /**
     * the total number of Connections in the pool.
     * <p>
     * The invariant idleConnections + activeConnections == totalConnections should always hold.
     * </p>
     * @return
     */
    int getTotalConnections();
}
