/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: JmemcachedServer.java 1222 2010-09-14 16:44:57Z calvinxiu $
 */
package org.springside.modules.memcached;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.spy.memcached.AddrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thimbleware.jmemcached.Cache;
import com.thimbleware.jmemcached.MemCacheDaemon;
import com.thimbleware.jmemcached.storage.hash.LRUCacheStorageDelegate;

/**
 * JMemcached的封装, 主要用于功能测试.
 * 注意JMemcached对二进制协议支持不好, 尽量使用文本协议.
 * 
 * @author calvin
 */
public class JmemcachedServer {

	private static Logger logger = LoggerFactory.getLogger(JmemcachedServer.class);

	private MemCacheDaemon jmemcached;

	private String serverUrl = "localhost:11211";

	private int maxItems = 1024;
	private long maxBytes = 1024 * 2048;
	private long ceilingSize = 2048;

	@PostConstruct
	public void start() throws Exception {

		logger.info("Initializing JMemcached Daemon");

		LRUCacheStorageDelegate cacheStorage = new LRUCacheStorageDelegate(maxItems, maxBytes, ceilingSize);

		jmemcached = new MemCacheDaemon();
		jmemcached.setCache(new Cache(cacheStorage));
		jmemcached.setAddr(AddrUtil.getAddresses(serverUrl).get(0));
		jmemcached.setBinary(false);
		jmemcached.start();
		logger.info("Initialized JMemcached Daemon");
	}

	@PreDestroy
	public void stop() throws Exception {
		logger.info("Shutting down Jmemcached Daemon");
		jmemcached.stop();
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

	public void setMaxBytes(long maxBytes) {
		this.maxBytes = maxBytes;
	}

	public void setCeilingSize(long ceilingSize) {
		this.ceilingSize = ceilingSize;
	}
}
