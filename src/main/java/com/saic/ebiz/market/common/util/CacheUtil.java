/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * 
 */
package com.saic.ebiz.market.common.util;

/**
 * @author hejian
 *
 */
public class CacheUtil {
	/** 订单有效期 60*60 秒 . */
	public static final int ORDER_VALID_TIME = 60*60;
	
	/** Redis 缓存的Key组成*/
	private static final String CACHE_KEY_PREFIX = "ms:go:interval:";
	
	/** 冒号分隔符  */
	public static final String COLON = ":";
	
	/**
	 * 获取常规车缓冲的Key
	 * 
	 * @param  key Key
	 * @return	缓存的key值
	 * 
	 */
	public static String getOrderCacheKey(Object key){
		return CACHE_KEY_PREFIX + key;
	}
	
	public static void main(String[] args) {
		System.out.println("OrderCacheKey : " + getOrderCacheKey("222"));
	}
}
