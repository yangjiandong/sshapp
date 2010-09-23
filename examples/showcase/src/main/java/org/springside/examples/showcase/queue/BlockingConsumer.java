/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.examples.showcase.queue;

/**
 * 采用即时阻塞读取Queue中消息策略的Consumer.
 */
public abstract class BlockingConsumer extends QueueConsumer {

	/**
	 * 线程执行函数,阻塞获取消息并调用processMessage()进行处理.
	 */
	public void run() {
		//循环阻塞获取消息直到线程被中断.
		try {
			while (!Thread.currentThread().isInterrupted()) {
				Object message = queue.take();
				processMessage(message);
			}
		} catch (InterruptedException e) {
			// Ignore.
		} finally {
			//退出线程前调用清理函数.
			clean();
		}
	}

	/**
	 * 消息处理函数.
	 */
	protected abstract void processMessage(Object message);

	/**
	 * 退出清理函数.
	 */
	protected abstract void clean();
}
