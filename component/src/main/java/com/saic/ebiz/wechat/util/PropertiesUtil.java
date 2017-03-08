package com.saic.ebiz.wechat.util;

import java.util.ResourceBundle;

import org.springframework.stereotype.Component;


/****
 * 
 * Copyright (C), 2014-1-20, 上汽电商有限公司<br/>
 * 
 */
@Component
public class PropertiesUtil {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("weixin");

	/****
	 * 微信通用接口凭证
	 */
	public static final String TOKEN_URL = getKey("https_token_url");

	/****
	 * 微信ticket
	 */
	public static final String TICKET_URL = getKey("https_ticket_url");

	/**
	 * 1 用户同意授权，获取code
	 */
	public static final String HTTPS_AUTHORIZATION = getKey("https_authorize_url");
	
	/**
	 * 2 换取网页授权
	 */
	public static final String HTTPS_ACCESS_TOKEN = getKey("https_access_token_url");

	/**
	 * 4 微信获取用户基本信息
	 */
	public static final String HTTPS_USERINFO_URL = getKey("https_userinfo_url");
	
	/**
	 * 3 刷新access_token
	 */
	public static final String HTTPS_REFRESH_TOKEN = getKey("https_refresh_token_url");

	
	/**
	 * 
	 * @param key weixin.properties文件key
	 * @return
	 */
	public static String getKey(String key){
		return bundle.getString(key);
	}
	
	public static void main(String[] args) {
		System.out.println(TOKEN_URL);
		System.out.println(TICKET_URL);
		System.out.println(HTTPS_AUTHORIZATION);
		System.out.println(HTTPS_ACCESS_TOKEN);
		System.out.println(HTTPS_REFRESH_TOKEN);
		System.out.println(HTTPS_USERINFO_URL);
	}
}
