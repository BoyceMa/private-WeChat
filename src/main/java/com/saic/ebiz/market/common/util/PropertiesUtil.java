package com.saic.ebiz.market.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/****
 * 
 * Copyright (C), 2014-1-20, 上汽电商有限公司<br/>
 * 
 */
@Component
public class PropertiesUtil {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("weixin");

	/**
	 * 本地属性对象
	 */
	public static final Properties URLPROPS = new Properties();

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(PropertiesUtil.class);

	static {
		InputStream is = PropertiesUtil.class
				.getResourceAsStream("/conf.properties");
		try {
			URLPROPS.load(is);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static String getValue(String key) {
		return URLPROPS.getProperty(key);
	}

	/**
	 * 
	 * @param key weixin.properties文件key
	 * @return
	 */
	public static String getKey(String key){
		return bundle.getString(key);
	}
	
	/*****
	 * signature
	 */
	public static final String SIGNATURE = getKey("signature");

	/****
	 * 微信通用接口凭证
	 */
	public static final String HTTPS_TOKEN_URL = getKey("https_token_url");

	/****
	 * 微信上传多媒体文件
	 */
	public static final String HTTP_UPLOAD_URL = getKey("http_upload_url");

	/****
	 * 微信下载多媒体文件
	 */
	public static final String HTTP_DOWNLOAD_URL = getKey("http_download_url");

	/****
	 * 微信发送客服消息
	 */
	public static final String HTTPS_SENDMSG_URL = getKey("https_sendmsg_url");

	/****
	 * 微信自定义菜单创建
	 */
	public static final String HTTPS_CREATEMENU_URL = getKey("https_createmenu_url");

	/****
	 * 微信自定义菜单查询
	 */
	public static final String HTTPS_SEARCHMENU_URL = getKey("https_searchmenu_url");

	/****
	 * 微信自定义菜单删除
	 */
	public static final String HTTPS_DELETEMENU_URL = getKey("https_deletemenu_url");

	/****
	 * 微信创建分组
	 */
	public static final String HTTPS_CREATEGROUPS_URL = getKey("https_creategroups_url");

	/****
	 * 微信查询所有分组
	 */
	public static final String HTTPS_SEARCHGROUPS_URL = getKey("https_searchgroups_url");

	/****
	 * 微信查询用户所在分组
	 */
	public static final String HTTPS_SEARCHGROUPSUSER_URL = getKey("https_searchgroupsuser_url");

	/****
	 * 微信修改分组名
	 */
	public static final String HTTPS_UPDATEGROUPS_URL = getKey("https_updategroups_url");

	/****
	 * 微信移动用户分组
	 */
	public static final String HTTPS_UPDATEGROUPSUSER_URL = getKey("https_updategroupsuser_url");

	/****
	 * 微信获取用户基本信息
	 */
	public static final String HTTPS_USERINFO_URL = getKey("https_userinfo_url");

	/****
	 * 微信获取用户列表
	 */
	public static final String HTTPS_USERLIST_URL = getKey("https_userList_url");

	/*****
	 * 下载 二维码
	 */

	public static final String HTTPS_SHOWQRCODE = getKey("https_showqrcode");

	/********
	 * 生成二维码
	 */

	public static final String HTTPS_CREATEQRCODE = getKey("https_create");
	
	/**
	 * 用户同意授权，获取code
	 */
	public static final String HTTPS_AUTHORIZATION = getKey("https_authorize_url");
	
	/**
	 * 换取网页授权
	 */
	public static final String HTTPS_ACCESS_TOKEN = getKey("https_access_token_url");
	
	/**
	 * 刷新access_token
	 */
	public static final String HTTPS_REFRESH_TOKEN = getKey("https_refresh_token_url");
	
	/**
	 * 拉取用户信息(需scope为 snsapi_userinfo)
	 */
	public static final String HTTPS_SNS_USER_INFO = getKey("https.userinfo.url");
	
	/**
	 * 模板
	 */
	public static final String TEMPLATE = getKey("template");
	
	/**
	 * 登录url
	 */
//	public static final String LOGIN_URL = getKey("LOGIN_URL");
	/**
	 * 图文群发每日数据
	 */
	public static final String HTTPS_DATACUBE_GETARTICLESUMMARY= getKey("https_datacube_getarticlesummary_url");
	
	/**
	 * 图文统计数据
	 */
	public static final String HTTPS_DATACUBE_GETUSERREAD= getKey("https_datacube_getuserread_url");
}
