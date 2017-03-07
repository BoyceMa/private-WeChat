/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 */
package com.saic.ebiz.component.wx.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.saic.ebiz.component.wx.job.TicketJob;

/**
 * @author hejian
 *
 */
@Component
public class JsSDKSign {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private TicketJob ticketJob;
    
    /***
     * 
     * @param appId 微信服务号应用ID
     * @param url 要访问页面的url eg. http://mgo.chexiang.com/account/login.html
     * 		且从url的#号后面截断(如果有)
     * @return
     */
    public Map<String, String> sign(String appId, String url) {
    	Map<String, String> result = null;
    	//String jsapi_ticket = springRedisClient.get(Constants.PREFIX_REDIS_TICKET + appId,Constants.REDIS_NAME_SPACE,null);
    	String jsapi_ticket = ticketJob.getTicket();
    	if(jsapi_ticket != null){
    		result = this.sign(jsapi_ticket, appId, url);
    	}
		return result;
	}
    /***
     * 
     * @param jsapi_ticket 签名的票据
     * @param appId 应用服务号ID
     * @param url 要访问页面的url
     * @return
     */
	
	public Map<String, String> sign(String jsapi_ticket,String appId, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
//		String nonce_str = "82693e11-b9bc-448e-892f-f5289f46cd0f";
//		String timestamp = "1419835025";
		String data;
		String signature = "";
		//截断#
		int index = url.indexOf("#");
		if(index > 0){
			url = url.substring(0,index);
		}

		// 注意这里参数名必须全部小写，且必须有序
		data = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;
		logger.info("需要签名的数据串 : " + data);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(data.getBytes("UTF-8"));
			signature = byteToStr(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}

		ret.put("url", url);
		ret.put("appId", appId);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < byteArray.length; i++) {
			buffer.append(byteToHexStr(byteArray[i]));
		}
		return buffer.toString();
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis()/1000);
	}
}
