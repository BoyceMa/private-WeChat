/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 */
package com.saic.ebiz.market.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hejian
 *
 */
public class RedisClient {

	private static Map<String, String> cache = new ConcurrentHashMap<String, String>();
	
	public static void put(String key, String value){
		cache.put(key, value);
	}
	
	public static String get(String key){
		return cache.get(key);
	}
}
