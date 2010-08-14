package org.springside.examples.showcase.cache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

/**
 * 使用ConcurrentHashMap的本地缓存策略.
 * 
 * 基于Google Collection实现:
 * 
 * 1.加大并发锁数量.
 * 2.每个放入的对象在固定时间后过期.
 * 3.当key不存在时, 需要进行较长时间的计算(如访问数据库)时, 能避免并发访问造成的重复计算.
 * 
 * @author calvin
 */
public class MapCacheDemo {

	public static <K, V> ConcurrentMap<K, V> createMapCache(Function<? super K, ? extends V> computingFunction) {
		return new MapMaker().concurrencyLevel(32).expiration(1, TimeUnit.DAYS).makeComputingMap(computingFunction);
	}
}
