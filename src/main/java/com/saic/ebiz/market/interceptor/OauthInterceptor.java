package com.saic.ebiz.market.interceptor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.saic.ebiz.component.wx.util.CommonUtil;
import com.saic.ebiz.market.annotation.BaseOauth;
import com.saic.ebiz.market.annotation.UserInfoOauth;
import com.saic.ebiz.market.common.constant.Constants;
import com.saic.ebiz.market.common.constant.Scope;
import com.saic.ebiz.market.common.entity.authentication.Oauth2Token;
import com.saic.ebiz.market.common.entity.authentication.SNSUserInfo;
import com.saic.ebiz.market.common.util.BrowserUtil;
import com.saic.ebiz.market.common.util.MD5Util;
import com.saic.ebiz.market.common.util.RequestUtils;
import com.saic.ebiz.market.controller.RedisClient;
import com.saic.ebiz.market.service.AuthorizationService;

public class OauthInterceptor implements HandlerInterceptor{

	final static Logger logger = LoggerFactory.getLogger(OauthInterceptor.class);
	
	@Resource
	private AuthorizationService authorizationService;

	private String appId = Constants.APP_ID;
	
	private String appSecret = Constants.APP_SECRET;
	
	private String redirectUrl = "http://tojaoomy.ngrok.cc/oauth/router";
	
	private static final String OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	
	public ConcurrentMap<String, Oauth2Token> tokenCache = new ConcurrentHashMap<String,Oauth2Token>();
	public ConcurrentMap<String, SNSUserInfo> userInfoCache = new ConcurrentHashMap<String,SNSUserInfo>();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info(RequestUtils.getFullRequestUrl(request, true));
		//判断是否是微信浏览器
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
		boolean baseOauth = false;
		boolean userInfoOauth = false;
		if(methodParameters != null){
			for(MethodParameter mp : methodParameters){
//				Annotation[] annotations = mp.getMethodAnnotations();
				BaseOauth oauth = mp.getMethodAnnotation(BaseOauth.class);
				if(oauth != null){
					baseOauth = true;
					if(StringUtils.isNoneBlank(oauth.appId())){
						this.appId = oauth.appId();
					}
					if(StringUtils.isNoneBlank(oauth.appSecret())){
						this.appSecret = oauth.appSecret();
					}
				}
				
				UserInfoOauth oauthSns = mp.getMethodAnnotation(UserInfoOauth.class);{
					if(oauthSns != null){
						userInfoOauth = true;
						if(StringUtils.isNoneBlank(oauthSns.appId())){
							this.appId = oauthSns.appId();
						}
						if(StringUtils.isNoneBlank(oauthSns.appSecret())){
							this.appSecret = oauthSns.appSecret();
						}
					}
				}
				/*if(mp.hasParameterAnnotation(BaseOauth.class)){
					baseOauth = true;
				}
				if(mp.hasParameterAnnotation(UserInfoOauth.class)){
					userInfoOauth = true;
				}*/
			}
		}
		if(BrowserUtil.isWeixinBrowser(request) && (userInfoOauth || baseOauth) ){
			//微信授权码，约定是code
			final String code = request.getParameter("code");
			if(StringUtils.isNoneEmpty(code)){
				//通过code获取授权信息
				if(baseOauth){
					Oauth2Token oauth2Token = null;
					if(tokenCache.containsKey(code)){
						oauth2Token = tokenCache.get(code);
					}else{
						oauth2Token = authorizationService.getOauth2Token(appId, appSecret, code);
						tokenCache.put(code, oauth2Token);
					}
					request.setAttribute("Oauth2Token", oauth2Token);
				}else if(userInfoOauth){
					Oauth2Token oauth2Token = null;
					if(tokenCache.containsKey(code)){
						oauth2Token = tokenCache.get(code);
					}else{
						oauth2Token = authorizationService.getOauth2Token(appId, appSecret, code);
						tokenCache.put(code, oauth2Token);
					}
					
					SNSUserInfo snsUserInfo = null;
					if(userInfoCache.containsKey(code)){
						snsUserInfo = userInfoCache.get(code);
					}else{
						snsUserInfo = this.authorizationService.getUserInfo(oauth2Token.getAccessToken(), oauth2Token.getOpenId());
						userInfoCache.put(code, snsUserInfo);
					}
					request.setAttribute("Oauth2Token", oauth2Token);
					request.setAttribute("SNSUserInfo", snsUserInfo);
				}
				return true;
			}else{
				boolean flag = true;
				final String currentUrl = request.getRequestURL().toString();
				final String state = MD5Util.getMD5(currentUrl);
				RedisClient.put(state, currentUrl);
				if(baseOauth){
					response.sendRedirect(buildUrl(redirectUrl, appId, Scope.snsapi_base, state));
					flag = false;
				} else if(userInfoOauth){
					response.sendRedirect(buildUrl(redirectUrl, appId, Scope.snsapi_userinfo, state));
					flag = false;
				}
				return flag;
			}
		}
		return true;
	}
	
	private static String buildUrl(String url,String appId, Scope scope, String state){
		return OAUTH_URL.replace("APPID", appId).replace("REDIRECT_URI", CommonUtil.encodeURL(url))
				.replace("SCOPE", scope.name()).replace("STATE", state);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}

}
