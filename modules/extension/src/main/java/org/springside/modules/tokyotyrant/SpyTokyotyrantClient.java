/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SpyTokyotyrantClient.java 1211 2010-09-10 16:20:45Z calvinxiu $
 */
package org.springside.modules.tokyotyrant;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SpyMemcached Client针对Tokyotyrant的二次封装,提供常用的Get/GetBulk/Set/Delete/Incr/Decr函数的封装,屏蔽TT不支持的功能.
 * 
 * 未提供封装的函数可直接调用getClient()取出Spy的原版MemcachedClient来使用.
 * 
 * @author calvin
 */
public class SpyTokyotyrantClient {

	private static Logger logger = LoggerFactory.getLogger(SpyTokyotyrantClient.class);

	private MemcachedClient client;

	public SpyTokyotyrantClient(MemcachedClient client) {
		this.client = client;
	}

	public MemcachedClient getMemcachedClient() {
		return client;
	}

	/**
	 * Get方法, 转换结果类型并屏蔽异常,仅返回Null.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		try {
			return (T) client.get(key);
		} catch (RuntimeException e) {
			logger.warn("Get from memcached server fail,key is" + key, e);
			return null;
		}
	}

	/**
	 * GetBulk方法, 转换结果类型并屏蔽异常.
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<String, T> getBulk(String... keys) {
		try {
			return (Map<String, T>) client.getBulk(keys);
		} catch (RuntimeException e) {
			logger.warn("Get from memcached server fail,keys are" + Arrays.toString(keys), e);
			return null;
		}
	}

	/**
	 * GetBulk方法, 转换结果类型并屏蔽异常.
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<String, T> getBulk(Collection<String> keys) {
		try {
			return (Map<String, T>) client.getBulk(keys);
		} catch (RuntimeException e) {
			logger.warn("Get from memcached server fail,keys are" + keys, e);
			return null;
		}
	}

	/**
	 * Set方法.
	 */
	public Future<Boolean> set(String key, Object value) {
		return client.set(key, 0, value);
	}

	/**
	 * Delete方法.	 
	 */
	public Future<Boolean> delete(String key) {
		return client.delete(key);
	}

	/**
	 * Incr方法.
	 */
	public long incr(String key, int by, long defaultValue) {
		return client.incr(key, by, defaultValue);
	}

	/**
	 * 异步Incr方法, 不支持默认值, 若key不存在返回-1.
	 */
	public Future<Long> asyncIncr(String key, int by) {
		return client.asyncIncr(key, by);
	}

	/**
	 * Decr方法.
	 */
	public long decr(String key, int by, long defaultValue) {
		return client.decr(key, by, defaultValue);
	}

	/**
	 * 异步Decr方法, 不支持默认值, 若key不存在返回-1.
	 */
	public Future<Long> asyncDecr(String key, int by) {
		return client.asyncDecr(key, by);
	}
}