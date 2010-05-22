/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: QueuesHolder.java 1050 2010-04-17 15:10:56Z calvinxiu $
 */
package org.springside.modules.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.google.common.collect.MapMaker;

/**
 * BlockingQueue Map的持有者.
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
@ManagedResource(objectName = QueuesHolder.QUEUEHOLDER_MBEAN_NAME, description = "Queues Holder Bean")
public class QueuesHolder {
	/**
	 * QueueManager注册的名称.
	 */
	public static final String QUEUEHOLDER_MBEAN_NAME = "SpringSide:type=QueueManagement,name=queueHolder";

	private static ConcurrentMap<String, BlockingQueue> queueMap = new MapMaker().concurrencyLevel(32).makeMap();//消息队列
	private static int queueSize = Integer.MAX_VALUE;

	/**
	 * 设置每个队列的最大长度, 默认为Integer最大值, 设置时不改变已创建队列的最大长度.
	 */
	public void setQueueSize(int queueSize) {
		QueuesHolder.queueSize = queueSize; //NOSONAR
	}

	/**
	 * 根据queueName获得消息队列的静态函数.
	 * 如消息队列还不存在, 会自动进行创建.
	 */
	public static <T> BlockingQueue<T> getQueue(String queueName) {
		BlockingQueue queue = queueMap.get(queueName);

		if (queue == null) {
			queue = new LinkedBlockingQueue(queueSize);
			queueMap.put(queueName, queue);
		}

		return queue;
	}

	/**
	 * 根据queueName获得消息队列中未处理消息的数量,支持基于JMX查询.
	 */
	@ManagedOperation(description = "Get message count in queue")
	@ManagedOperationParameters( { @ManagedOperationParameter(name = "queueName", description = "Queue name") })
	public static int getQueueLength(String queueName) {
		return getQueue(queueName).size();
	}

}