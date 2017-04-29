/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 */
package com.saic.ebiz.component.wx.job;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

//import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONPObject;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.saic.ebiz.component.wx.entity.AppIdSecret;
import com.saic.ebiz.component.wx.entity.Token;
import com.saic.ebiz.component.wx.util.CommonUtil;
import com.saic.ebiz.market.common.constant.Constants;

/**
 * @author hejian
 * 
 */
@Component
public class AccessTokenJob implements InitializingBean, DisposableBean {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public LoadingCache<AppIdSecret, String> accessTokenCache = null;
	
	private AppIdSecret appIdSecret = new AppIdSecret(Constants.APP_ID, Constants.APP_SECRET);
	
	public AccessTokenJob() {
		logger.info("init");
		accessTokenCache = CacheBuilder.newBuilder()
				// 设置并发级别为200，并发级别是指可以同时写缓存的线程数
				.concurrencyLevel(200)
				// 设置写缓存后1分钟过期
				.expireAfterWrite(90, TimeUnit.MINUTES).initialCapacity(10).maximumSize(100)
				// 设置要统计缓存的命中率
				.recordStats()
				// 设置缓存的移除通知
				.removalListener(new RemovalListener<AppIdSecret, String>() {
					@Override
					public void onRemoval(RemovalNotification<AppIdSecret, String> notification) {
						logger.info(notification.getKey() + " was removed, cause by " + notification.getCause());
					}
				}).build(new CacheLoader<AppIdSecret, String>() {
					// build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
					@Override
					public String load(AppIdSecret appIdSecret) throws Exception {
						Token token = CommonUtil.getAccessToken(appIdSecret.getAppId(), appIdSecret.getAppSecret());
						return token.getToken();
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
	}
	
	public String getAccessToken(AppIdSecret instance) {
		if(instance == null){
			instance = appIdSecret;
		}
		try {
			return accessTokenCache.get(instance);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	public static void main(String[] args) throws ExecutionException {
		System.out.println("".split(" |,").length);
//		System.out.println(CollectionUtils.sizeIsEmpty("".split(" |,")));
//		System.out.println(JSONPObject.toJSONString("".split(" |,")));
		
		System.out.println(new AccessTokenJob().getAccessToken(null));
	}
}


