/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * 
 */
package com.saic.ebiz.market.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.saic.ebiz.market.common.constant.Constants;
import com.saic.ebiz.market.common.entity.authentication.Oauth2Token;
import com.saic.ebiz.market.common.entity.authentication.SNSUserInfo;
import com.saic.ebiz.market.service.AuthorizationService;

/**
 * @author hejian
 * 
 */
@RestController
public class AuthorizationController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private AuthorizationService authorizationService;

	private String appId = Constants.APP_ID;
	
	private String appSecret = Constants.APP_SECRET;
	
	public LoadingCache<String, SNSUserInfo> cache = null;
	
	public ConcurrentMap<String, Oauth2Token> tokenCache = new ConcurrentHashMap<String,Oauth2Token>();
	public ConcurrentMap<String, SNSUserInfo> userInfoCache = new ConcurrentHashMap<String,SNSUserInfo>();
	
	/**
	 * 
	 */
	public AuthorizationController() {
		cache = CacheBuilder.newBuilder()
				// 设置并发级别为200，并发级别是指可以同时写缓存的线程数
				.concurrencyLevel(200)
				// 设置写缓存后1分钟过期
				.expireAfterWrite(2, TimeUnit.MINUTES).initialCapacity(10).maximumSize(100)
				// 设置要统计缓存的命中率
				.recordStats()
				// 设置缓存的移除通知
				.removalListener(new RemovalListener<String, SNSUserInfo>() {
					@Override
					public void onRemoval(RemovalNotification<String, SNSUserInfo> notification) {
						log.info(notification.getKey() + " was removed, cause by " + notification.getCause());
					}
				}).build(new CacheLoader<String, SNSUserInfo>() {
					// build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
					@Override
					public SNSUserInfo load(String appIdSecret) throws Exception {
						return userInfoCache.get(appIdSecret);
					}
				});
	}
	
	/**
	 * 微信授权的回调地址
	 * @param request
	 * @return
	 */
	@RequestMapping("/oauth")
	@ResponseBody
	public Oauth2Token oauth(HttpServletRequest request) {
		log.info("返回的参数 : " + JSONObject.toJSONString(request.getParameterMap()));
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		
		//微信浏览器判断网址，重复访问，Cache。只是做demo用，生产不可这样使用。
		if(StringUtils.isNotBlank(code)){
			if(tokenCache.containsKey(code)){
				return tokenCache.get(code);
			}
		}
		
		log.info("授权返回代码 code : {}, state : {} ", code, state);
		Oauth2Token oauth2Token = authorizationService.getOauth2Token(appId, appSecret, code);
		log.info("返回Oauth2Token : " + JSONObject.toJSONString(oauth2Token));
		String openId = oauth2Token.getOpenId();
		
		log.info("AuthorizationController => openId : " + openId);
		tokenCache.put(code, oauth2Token);
		return oauth2Token;
	}
	
	@RequestMapping("/oauth/sns")
	@ResponseBody
	public SNSUserInfo oauthSns(HttpServletRequest request) throws ExecutionException {
		log.info("返回的参数 : " + JSONObject.toJSONString(request.getParameterMap()));
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		
		//微信浏览器判断网址，重复访问，Cache。只是做demo用，生产不可这样使用。
		if(StringUtils.isNotBlank(code)){
			if(userInfoCache.containsKey(code)){
				return userInfoCache.get(code);
			}
		}
		
		log.info("授权返回代码 code : {}, state : {} ", code, state);
		Oauth2Token oauth2Token = authorizationService.getOauth2Token(appId, appSecret, code);
		log.info("返回Oauth2Token : " + JSONObject.toJSONString(oauth2Token));
		String openId = oauth2Token.getOpenId();
		log.info("AuthorizationController => openId : " + openId);
		SNSUserInfo snsUserInfo = this.authorizationService.getUserInfo(oauth2Token.getAccessToken(), oauth2Token.getOpenId());
		cache.put(code, snsUserInfo);
		userInfoCache.put(code, snsUserInfo);
		return snsUserInfo;
	}
	
	@RequestMapping("/oauth/router")
	public void router(HttpServletRequest request, HttpServletResponse response) throws ExecutionException, IOException {
		log.info("返回的参数 : " + JSONObject.toJSONString(request.getParameterMap()));
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		
		if(StringUtils.isNotBlank(state)){
			response.sendRedirect(RedisClient.get(state) + "?code=" + code);
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String encodeString = URLEncoder.encode("何剑","UTF-8");
		System.out.println("encode : " + encodeString);
		System.out.println("decode : " + URLDecoder.decode(encodeString, "UTF-8"));
		encodeString = Base64.encodeBase64String("何剑".getBytes());
		System.out.println(encodeString);
		System.out.println(new String(Base64.decodeBase64(encodeString),"UTF-8"));
		
		String str = "aHR0cDovL3BtZi5jaGV4aWFuZy5jb20vdGVzdC9hdXRoLmh0bQ====";
		str = str.substring(0, str.length() - 2);
		System.out.println(new String(Base64.decodeBase64(str),"UTF-8"));
		
	}
}
