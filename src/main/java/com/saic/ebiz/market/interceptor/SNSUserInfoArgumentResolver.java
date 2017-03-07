/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 */
package com.saic.ebiz.market.interceptor;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.saic.ebiz.market.annotation.UserInfoOauth;
import com.saic.ebiz.market.common.entity.authentication.Oauth2Token;
import com.saic.ebiz.market.common.entity.authentication.SNSUserInfo;

/**
 * 
 * 使用HandlerMethodArgumentResolver
 * 不推荐使用WebArgumentResolver，
 * 因为测试发现如果有多个WebArgumentResolver，只有第一个会起作用，其他无效。
 * 
 * @author hejian
 *
 */
public class SNSUserInfoArgumentResolver implements /*WebArgumentResolver,*/ HandlerMethodArgumentResolver {

	/*@Deprecated
	   public Object resolveArgument(MethodParameter param, NativeWebRequest request) throws Exception {
		if (UserInfoOauth.class.isAssignableFrom(param.getParameterType())) {
			return request.getAttribute("SNSUserInfo",  RequestAttributes.SCOPE_REQUEST);
		} 
		
		return null;
	}*/

	/* (non-Javadoc)
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getMethodAnnotation(UserInfoOauth.class) != null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		if(Oauth2Token.class.equals(parameter.getParameterType())){
			return webRequest.getAttribute("Oauth2Token",  RequestAttributes.SCOPE_REQUEST);
		}
		
		if(SNSUserInfo.class.equals(parameter.getParameterType())){
			return webRequest.getAttribute("SNSUserInfo",  RequestAttributes.SCOPE_REQUEST);
		}
		
		return null;
	}
}
