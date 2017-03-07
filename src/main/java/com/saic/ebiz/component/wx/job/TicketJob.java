/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 */
package com.saic.ebiz.component.wx.job;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.saic.ebiz.component.wx.entity.AppIdSecret;
import com.saic.ebiz.component.wx.entity.JsapiTicket;
import com.saic.ebiz.component.wx.util.CommonUtil;
import com.saic.ebiz.market.common.constant.Constants;

/**
 * @author hejian
 * 
 */
@Component
public class TicketJob implements InitializingBean, DisposableBean {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public LoadingCache<AppIdSecret, String> ticketCache = null;

	private AppIdSecret appIdSecret = new AppIdSecret(Constants.APP_ID, Constants.APP_SECRET);
	
	@Autowired
	private AccessTokenJob accessTokenJob;

	/**
	 * @return the accessTokenJob
	 */
	public AccessTokenJob getAccessTokenJob() {
		return accessTokenJob;
	}

	/**
	 * @param accessTokenJob the accessTokenJob to set
	 */
	public void setAccessTokenJob(AccessTokenJob accessTokenJob) {
		this.accessTokenJob = accessTokenJob;
	}

	public TicketJob() {
		logger.info("init");
		ticketCache = CacheBuilder.newBuilder()
				// 设置并发级别为200，并发级别是指可以同时写缓存的线程数
				.concurrencyLevel(200)
				// 设置写缓存后1分钟过期
				.expireAfterWrite(90, TimeUnit.MINUTES).initialCapacity(10)
				.maximumSize(100)
				// 设置要统计缓存的命中率
				.recordStats()
				// 设置缓存的移除通知
				.removalListener(new RemovalListener<AppIdSecret, String>() {
					@Override
					public void onRemoval(
							RemovalNotification<AppIdSecret, String> notification) {
						logger.info(notification.getKey()
								+ " was removed, cause by "
								+ notification.getCause());
					}
				}).build(new CacheLoader<AppIdSecret, String>() {
					// build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
					@Override
					public String load(AppIdSecret appIdSecret)
							throws Exception {
						String accessToken = accessTokenJob.getAccessToken(appIdSecret);
						JsapiTicket ticket = CommonUtil.getJsapiTicket(accessToken);
						return ticket.getTicket();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
	}

	public String getTicket() {
		try {
			return ticketCache.get(appIdSecret);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		final AccessTokenJob accessTokenJob = new AccessTokenJob();
		System.out.println(accessTokenJob.getAccessToken(null));
		TicketJob ticketJob = new TicketJob();
		ticketJob.setAccessTokenJob(accessTokenJob);
		System.out.println(ticketJob.getTicket());
	}
}
