package org.springside.modules.memcached;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对SpyMemcached Client的二次封装,提供常用的Get/GetBulk/Set/Delete/Incr/Decr函数的封装.
 * 
 * 未提供封装的函数可直接调用getClient()取出Spy的原版MemcachedClient来使用.
 * 
 * @author calvin
 */
public class SpyMemcachedClient {

	private static Logger logger = LoggerFactory.getLogger(SpyMemcachedClient.class);

	private MemcachedClient client;

	public SpyMemcachedClient(MemcachedClient client) {
		this.client = client;
	}

	public MemcachedClient getClient() {
		return client;
	}

	/**
	 * Get方法, 转换结果类型并屏蔽异常.
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
	public Future<Boolean> set(String key, int expiredTime, Object value) {
		return client.set(key, expiredTime, value);
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
	 * Decr方法.
	 */
	public long decr(String key, int by, long defaultValue) {
		return client.decr(key, by, defaultValue);
	}
}