/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.examples.showcase.queue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.util.Assert;

/**
 * 采用定时批量读取Queue中消息策略的Consumer.
 */
public abstract class PeriodConsumer extends QueueConsumer {

	protected int batchSize = 0;
	protected int period = 0;

	/**
	* 批量定时读取消息的队列大小.
	*/
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * 批量定时读取的时间间隔,单位为毫秒.
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	@PostConstruct
	public void checkSetting() {
		Assert.isTrue(batchSize > 0);
		Assert.isTrue(period > 0);
	}

	/**
	 * 线程执行函数,定期批量获取消息并调用processMessageList()处理.
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				List list = new ArrayList(batchSize);
				queue.drainTo(list, batchSize);
				processMessageList(list);
				if (!Thread.currentThread().isInterrupted()) {
					Thread.sleep(period);
				}
			}
		} catch (InterruptedException e) {
			// Ignore.
		} finally {
			//退出线程前调用清理函数.
			clean();
		}
	}

	/**
	 * 批量消息处理函数.
	 */
	protected abstract void processMessageList(List<?> messageList);

	/**
	 * 退出清理函数.
	 */
	protected abstract void clean();
}
