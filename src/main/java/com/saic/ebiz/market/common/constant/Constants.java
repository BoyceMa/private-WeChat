/**
 *
 * Copyright (C), 2013-2013, 上海汽车集团股份有限公司
 * FileName : JupiterConstants.java
 * Author : 何剑
 * Date : 2014年9月19日
 * 
 */
package com.saic.ebiz.market.common.constant;


/**
 * @author hejian
 * 
 * @see http
 *      ://mp.weixin.qq.com/wiki/index.php?title=%E9%AA%8C%E8%AF%81%E6%B6%88%
 *      E6%81%AF%E7%9C%9F%E5%AE%9E%E6%80%A7
 */
public class Constants {

	public final static String APP_ID = "wx38a0b3a7cc7761f9";
	
	public final static String APP_SECRET = "04a4ea410735b9a134d41ed29ce64699";
	
	/** 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。 */
	public static final String SIGNATURE = "signature";

	/** 时间戳 */
	public static final String TIMESTAMP = "timestamp";

	/** 随机数 */
	public static final String NONCE = "nonce";

	/** 随机字符串 */
	public static final String ECHOSTR = "echostr";

	/** 微信服务器最大等待时间 */
	public static final int INTERNAL = 5;

	/** 发送方帐号（open_id） */
	public static final String FROM_USER_NAME = "FromUserName";

	/** 公众帐号 */
	public static final String TO_USER_NAME = "ToUserName";

	/** 消息类型 */
	public static final String MSG_TYPE = "MsgType";

	/** 事件 */
	public static final String REQUEST_PARAMETER_EVENT = "Event";

	/** 事件Value */
	public static final String REQUEST_PARAMETER_EVENT_KEY = "EventKey";
	
	/** MDM User Source 车享购来源号 与彭姚商定60 */
	public static final int MDM_USER_SAIC_TYPE= 60;

	/** 订单有效期 一个小时 = 60*60 seconds . */
	public static final int ORDER_VALID_TIME = 60 * 60;

	/** 订单已存在的code */
	public static final String ORDER_EXISTS_CODE = "-1";

	// /////////////////////// 微信授权相关常量字段 ///////////////////////////
	// https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect

	public static final String URL_PARAMETER_KEY_APPID = "appid";
	public static final String URL_PARAMETER_VALUE_APPID = "APPID";
	
	public static final String URL_PARAMETER_KEY_REDIRECT_URI = "redirect_uri";
	public static final String URL_PARAMETER_VALUE_REDIRECT_URI = "REDIRECT_URI";
	
	public static final String URL_PARAMETER_KEY_RESPONSE_TYPE = "RESPONSE_TYPE";
	public static final String URL_PARAMETER_VALUE_RESPONSE_TYPE = "code";
	
	public static final String URL_PARAMETER_KEY_SCOPE = "scope";
	public static final String URL_PARAMETER_VALUE_SCOPE = "SCOPE";
	
	public static final String URL_PARAMETER_KEY_STATE = "state";
	public static final String URL_PARAMETER_VALUE_STATE = "STATE";
	
	 public static final String REDIS_NAME_SPACE = "promotion_wx";
	 
	public static final int REDIS_EXPIRE_10_MIN = 60*10;//10分钟
	
	public static final int REDIS_EXPIRE_60_MIN = 60*60;//10分钟
	
	public final static String DOMAIN = "http://mgo.chexiang.com";
}
