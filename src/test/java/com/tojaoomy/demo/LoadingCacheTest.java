/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 */
package com.tojaoomy.demo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * @author hejian
 *
 */
public class LoadingCacheTest {

	/**
	 * @param args
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		LoadingCache<String, String> cache = null;
		cache = CacheBuilder.newBuilder()
				// 设置并发级别为200，并发级别是指可以同时写缓存的线程数
				.concurrencyLevel(200)
				// 设置写缓存后1分钟过期
				.expireAfterWrite(1, TimeUnit.SECONDS).initialCapacity(10).maximumSize(100)
				// 设置要统计缓存的命中率
				.recordStats()
				// 设置缓存的移除通知
				.removalListener(new RemovalListener<String, String>() {
					@Override
					public void onRemoval(RemovalNotification<String, String> notification) {
						System.out.println(notification.getKey() + " was removed, cause by " + notification.getCause());
					}
				}).build(new CacheLoader<String, String>() {
					// build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
					@Override
					public String load(String appIdSecret) throws Exception {
						return "";
					}
				});
		
		cache.put("key1", "value1");
		System.out.println(cache.get("key1"));
		Thread.sleep(2000);
		System.out.println(cache.get("key1"));
	}

}
