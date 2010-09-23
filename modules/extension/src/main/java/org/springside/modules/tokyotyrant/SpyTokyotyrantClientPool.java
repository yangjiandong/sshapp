/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SpyTokyotyrantClientPool.java 1211 2010-09-10 16:20:45Z calvinxiu $
 */
package org.springside.modules.tokyotyrant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.spy.memcached.AddrUtil;
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
 * 对SpyMemcached Client的二次封装.
 * 
 * 1.建立SpyMemcached Client池,负责初始化与关闭.
 * 2.提供常用的Get/GetBulk/Set/Delete/Incr/Decr函数的封装.
 * 
 * 未提供封装的函数可直接调用getClient()取出Spy的原版MemcachedClient来使用.
 * 
 * @author calvin
 */
public class SpyTokyotyrantClientPool implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(SpyTokyotyrantClientPool.class);

	private List<SpyTokyotyrantClient> clientPool;

	private int poolSize = 1;

	private Random random = new Random();

	private String memcachedNodes = "localhost:11211";

	private boolean isConsistentHashing = false;

	private long operationTimeout = 1000; //default value in Spy is 1000ms

	@Override
	public void afterPropertiesSet() throws Exception {
		clientPool = new ArrayList<SpyTokyotyrantClient>(poolSize);

		for (int i = 0; i < poolSize; i++) {
			ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();

			cfb.setFailureMode(FailureMode.Redistribute);
			cfb.setDaemon(true);
			cfb.setProtocol(Protocol.TEXT);

			if (isConsistentHashing) {
				cfb.setLocatorType(Locator.CONSISTENT);
				cfb.setHashAlg(HashAlgorithm.KETAMA_HASH);
			}

			cfb.setOpTimeout(operationTimeout);

			try {
				MemcachedClient spyClient = new MemcachedClient(cfb.build(), AddrUtil.getAddresses(memcachedNodes));
				clientPool.add(new SpyTokyotyrantClient(spyClient));
			} catch (IOException e) {
				logger.error("MemcachedClient initilization error: ", e);
				throw e;
			}
		}
	}

	@Override
	public void destroy() throws Exception {
		for (SpyTokyotyrantClient spyClient : clientPool) {
			if (spyClient != null) {
				spyClient.getMemcachedClient().shutdown();
			}
		}
	}

	/**
	 * 随机取出Pool中的SpyMemcached Client.
	 */
	public SpyTokyotyrantClient getClient() {
		return clientPool.get(random.nextInt(poolSize));
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	/**
	 *  支持多节点, 以","分割.
	 *  eg. "localhost:11211,localhost:11212"
	 */
	public void setMemcachedNodes(String memcachedNodes) {
		this.memcachedNodes = memcachedNodes;
	}

	public void setConsistentHashing(boolean isConsistentHashing) {
		this.isConsistentHashing = isConsistentHashing;
	}

	public void setOperationTimeout(long operationTimeout) {
		this.operationTimeout = operationTimeout;
	}
}