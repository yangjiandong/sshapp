/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SpyMemcachedClient.java 1222 2010-09-14 16:44:57Z calvinxiu $
 */
package org.springside.modules.memcached;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.HashAlgorithm;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 对SpyMemcached Client的二次封装,提供常用的Get/GetBulk/Set/Delete/Incr/Decr函数的封装.
 * 
 * 未提供封装的函数可直接调用getClient()取出Spy的原版MemcachedClient来使用.
 * 
 * @author calvin
 */
public class SpyMemcachedClient implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(SpyMemcachedClient.class);

	private MemcachedClient memcachedClient;

	// 配置项 //
	private String memcachedNodes = "localhost:11211";

	private boolean isBinaryProtocol = false;

	private boolean isConsistentHashing = true;

	private long operationTimeout = 1000; //default value in Spy is 1000ms

	// 初始,关闭函数 //
	@Override
	public void afterPropertiesSet() throws Exception {
		ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();

		cfb.setFailureMode(FailureMode.Redistribute);
		cfb.setDaemon(true);
		cfb.setProtocol(isBinaryProtocol ? Protocol.BINARY : Protocol.TEXT);

		if (isConsistentHashing) {
			cfb.setLocatorType(Locator.CONSISTENT);
			cfb.setHashAlg(HashAlgorithm.KETAMA_HASH);
		}

		cfb.setOpTimeout(operationTimeout);

		try {
			memcachedClient = new MemcachedClient(cfb.build(), AddrUtil.getAddresses(memcachedNodes));
		} catch (IOException e) {
			logger.error("MemcachedClient initilization error: ", e);
			throw e;
		}
	}

	@Override
	public void destroy() throws Exception {
		if (memcachedClient != null) {
			memcachedClient.shutdown();
		}
	}

	// Memcached访问函数  //

	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	/**
	 * Get方法, 转换结果类型并屏蔽异常,仅返回Null.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		try {
			return (T) memcachedClient.get(key);
		} catch (RuntimeException e) {
			logger.warn("Get from memcached server fail,key is " + key, e);
			return null;
		}
	}

	/**
	 * GetBulk方法, 转换结果类型并屏蔽异常.
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<String, T> getBulk(String... keys) {
		try {
			return (Map<String, T>) memcachedClient.getBulk(keys);
		} catch (RuntimeException e) {
			logger.warn("Get from memcached server fail,keys are " + Arrays.toString(keys), e);
			return null;
		}
	}

	/**
	 * GetBulk方法, 转换结果类型并屏蔽异常.
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<String, T> getBulk(Collection<String> keys) {
		try {
			return (Map<String, T>) memcachedClient.getBulk(keys);
		} catch (RuntimeException e) {
			logger.warn("Get from memcached server fail,keys are " + keys, e);
			return null;
		}
	}

	/**
	 * Set方法.
	 */
	public Future<Boolean> set(String key, int expiredTime, Object value) {
		return memcachedClient.set(key, expiredTime, value);
	}

	/**
	 * Delete方法.	 
	 */
	public Future<Boolean> delete(String key) {
		return memcachedClient.delete(key);
	}

	/**
	 * 配合Check and Set的Get方法,转换结果类型并屏蔽异常.
	 */
	@SuppressWarnings("unchecked")
	public <T> CASValue<T> gets(String key) {
		try {
			return (CASValue<T>) memcachedClient.gets(key);
		} catch (RuntimeException e) {
			logger.warn("Get from memcached server fail,key is" + key, e);
			return null;
		}
	}

	/**
	 * Check and Set方法.
	 */
	public CASResponse cas(String key, long casId, Object value) {
		return memcachedClient.cas(key, casId, value);
	}

	/**
	 * Incr方法.
	 */
	public long incr(String key, int by, long defaultValue) {
		return memcachedClient.incr(key, by, defaultValue);
	}

	/**
	 * Decr方法.
	 */
	public long decr(String key, int by, long defaultValue) {
		return memcachedClient.decr(key, by, defaultValue);
	}

	/**
	 * 异步Incr方法, 不支持默认值, 若key不存在返回-1.
	 */
	public Future<Long> asyncIncr(String key, int by) {
		return memcachedClient.asyncIncr(key, by);
	}

	/**
	 * 异步Decr方法, 不支持默认值, 若key不存在返回-1.
	 */
	public Future<Long> asyncDecr(String key, int by) {
		return memcachedClient.asyncDecr(key, by);
	}

	// 配置方法 //
	/**
	 *  支持多节点, 以","分割.
	 *  eg. "localhost:11211,localhost:11212"
	 */
	public void setMemcachedNodes(String memcachedNodes) {
		this.memcachedNodes = memcachedNodes;
	}

	public void setBinaryProtocol(boolean isBinaryProtocol) {
		this.isBinaryProtocol = isBinaryProtocol;
	}

	public void setConsistentHashing(boolean isConsistentHashing) {
		this.isConsistentHashing = isConsistentHashing;
	}

	public void setOperationTimeout(long operationTimeout) {
		this.operationTimeout = operationTimeout;
	}

}