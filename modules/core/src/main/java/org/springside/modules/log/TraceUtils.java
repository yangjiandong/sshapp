/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: TraceUtils.java 1594 2011-05-11 14:22:29Z calvinxiu $ 
 */
package org.springside.modules.log;

import org.apache.log4j.MDC;
import org.springside.modules.utils.IdUtils;

/**
 * 系统运行时打印方便调试与追踪信息的工具类.
 * 
 * 使用MDC存储traceID, 一次trace中所有日志都自动带有该ID,
 * 可以方便的用grep命令在日志文件中提取该trace的所有日志.
 * 
 * 需要在log4j.properties中将ConversionPattern添加%X{traceId},如:
 * log4j.appender.stdout.layout.ConversionPattern=%d [%c] %X{traceId}-%m%n
 * 
 * @author calvin
 */
public abstract class TraceUtils {

	public static final String TRACE_ID_KEY = "traceId";

	/**
	 * 开始Trace, 默认生成本次Trace的ID(8字符长)并放入MDC.
	 */
	public static void beginTrace() {
		String traceId = IdUtils.randomBase62();
		MDC.put(TRACE_ID_KEY, traceId);
	}

	/**
	 * 结束一次Trace, 清除traceId.
	 */
	public static void endTrace() {
		MDC.remove(TRACE_ID_KEY);
	}
}
