package org.springside.modules.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A very simple profiler to log the time elapsed performing some tasks. This
 * implementation is not thread-safe.
 *
 */
public class TimeProfiler {

    private Logger logger;
    private long start = 0;
    private String name;

    public TimeProfiler(Logger logger) {
        this.logger = logger;
    }

    public TimeProfiler(Class clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    /**
     * Use the default Sonar logger
     */
    public TimeProfiler() {
        this.logger = LoggerFactory.getLogger("org.sill.INFO");
    }

    public TimeProfiler start(String name) {
        this.name = name;
        this.start = System.currentTimeMillis();
        logger.info(name + "...");
        return this;
    }

    public TimeProfiler setLogger(Logger logger) {
        this.logger = logger;
        return this;
    }

    public Logger getLogger() {
        return logger;
    }

    public TimeProfiler stop() {
        if (start > 0) {
            logger.info("{} done: {} ms", name,
                    (System.currentTimeMillis() - start));
        }
        start = 0;
        return this;
    }
}