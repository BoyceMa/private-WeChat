/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 */
package com.saic.ebiz.wechat.util;

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
	
	public static String getFullRequestUrlRemovePameter(HttpServletRequest request, String parameterKey){
		return removePameter(getFullRequestUrl(request, true), parameterKey);
	}
	
	public static String getFullRequestUrlRemovePameter(String url, String parameterKey){
		return removePameter(url, parameterKey);
	}
	
	private static String removePameter(String url, String parameter){
		StringBuilder builder = new StringBuilder(url);
		int startIndex = url.indexOf(parameter + "=");
		if(startIndex != -1){
			int endIndex = url.indexOf("&", startIndex);
			if(endIndex == -1){
				startIndex = startIndex - 1;
				endIndex = url.length();
			}
			builder.replace(startIndex, endIndex + 1, "");
		}
		return builder.toString();
	}
	
	public static void main(String[] args) {
		String url = "http://localhost/luckDraw/oauth.htm?test1=1&code=051QujuJ09HzW32cfrwJ0EepuJ0Qujuj&test2=2&code=3";
		System.out.println(removePameter(url, "code"));
	}
	
}