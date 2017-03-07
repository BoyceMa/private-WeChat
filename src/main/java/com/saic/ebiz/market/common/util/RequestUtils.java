/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 */
package com.saic.ebiz.market.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * @author hejian
 *
 */
public class RequestUtils {

	/***
	 * 获取特定cookie的value
	 * @param request web请求对象
	 * @param cookieKey cookieKey
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request,String cookieKey){
		Cookie[] cookies = request.getCookies();
		String cookieValue = "";
		if(!StringUtils.isBlank(cookieKey)){
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if (cookieKey.equals(name)) {
					cookieValue = cookie.getValue();
					break;
				}
			}
		}
		return cookieValue;
	}
	
	/***
	 * 获取请求的完整url路径 比如http://car.chexiang.com/detail.htm?spuId=2222
	 * @param request
	 * @param needQueryString 是否包含get请求的参数
	 * @return
	 */
	public static String getFullRequestUrl(HttpServletRequest request, boolean needQueryString){
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(request.getRequestURL().toString());
		if(needQueryString){
			if(request.getQueryString() != null && request.getQueryString().length() > 0){
				urlBuilder.append("?").append(request.getQueryString());
			}
		}
		return urlBuilder.toString();
	}
	
}