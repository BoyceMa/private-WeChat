package com.saic.ebiz.wechat.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.saic.ebiz.carmall.service.api.IRedisService;
import com.saic.ebiz.wechat.annotation.BaseOauth;
import com.saic.ebiz.wechat.annotation.UserInfoOauth;
import com.saic.ebiz.wechat.authorization.AuthorizationService;
import com.saic.ebiz.wechat.authorization.AuthorizationService.Scope;
import com.saic.ebiz.wechat.authorization.Oauth2Token;
import com.saic.ebiz.wechat.authorization.SNSUserInfo;
import com.saic.ebiz.wechat.util.BrowserUtil;
import com.saic.ebiz.wechat.util.CommonUtil;
import com.saic.ebiz.wechat.util.MD5Util;
import com.saic.ebiz.wechat.util.PropertiesUtil;
import com.saic.ebiz.wechat.util.RequestUtils;


public class OauthInterceptor implements HandlerInterceptor{

	final static Logger logger = LoggerFactory.getLogger(OauthInterceptor.class);
	
	private static final int ONE_WEEK = 7 * 24 * 3600;

	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private IRedisService redisService;

	@Value("${saic.framework.web.common.cxh.wechat.cxxc.appid:}")
	private String appId; 
	
	@Value("${saic.framework.web.common.cxh.wechat.cxxc.appsecret:}")
	private String appSecret;
	
	@Value("${ebiz.wechat.web.redirectUrl:}")
	private String redirectUrl;
	
	private static final String OAUTH_URL = PropertiesUtil.HTTPS_AUTHORIZATION;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug(RequestUtils.getFullRequestUrl(request, true));
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
		boolean baseOauth = false;
		boolean userInfoOauth = false;
		if(methodParameters != null){
			for(MethodParameter mp : methodParameters){
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
			}
		}
		
		//判断是否是微信浏览器
		if(BrowserUtil.isWeixinBrowser(request) && (userInfoOauth || baseOauth) ){
			//微信授权码，约定是code
			final String code = request.getParameter("code");
			if(StringUtils.isNoneEmpty(code) && codeIsValid(code)){
				//通过code获取授权信息
				if(baseOauth){
					Oauth2Token oauth2Token = authorizationService.getOauth2Token(appId, appSecret, code);
					this.redisService.setex(getRedisCodeKey(code), ONE_WEEK, code);
					request.setAttribute("Oauth2Token", oauth2Token);
				}else if(userInfoOauth){
					Oauth2Token oauth2Token = authorizationService.getOauth2Token(appId, appSecret, code);
					this.redisService.setex(getRedisCodeKey(code), ONE_WEEK, code);
					SNSUserInfo snsUserInfo = this.authorizationService.getUserInfo(oauth2Token.getAccessToken(), oauth2Token.getOpenId());
					request.setAttribute("Oauth2Token", oauth2Token);
					request.setAttribute("SNSUserInfo", snsUserInfo);
				}
				return true;
			}else{
				boolean flag = true;
				String currentUrl = RequestUtils.getFullRequestUrlRemovePameter(request, "code");
				final String state = MD5Util.getMD5(currentUrl);
				redisService.setex(state, ONE_WEEK, currentUrl);
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
	
	/**
	 * 判断code是否有效，用户刷新后code失效。
	 * @param code
	 * @return
	 * true  有效
	 * false 无效
	 */
	private boolean codeIsValid(String code){
		//如果redis中有code说明已经访问过，失效。
		String redisCode = this.redisService.get(getRedisCodeKey(code));
		return StringUtils.isBlank(redisCode);
	}
	
	private String getRedisCodeKey(String code){
		return this.appId + code;
	}
	
	private static String buildUrl(String url,String appId, Scope scope, String state){
		return OAUTH_URL.replace("APPID", appId).replace("REDIRECT_URI", CommonUtil.encodeURL(url))
				.replace("SCOPE", scope.name()).replace("STATE", state);
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception {
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
	
}
