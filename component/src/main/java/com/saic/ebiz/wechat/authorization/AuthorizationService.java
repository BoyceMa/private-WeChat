/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * 
 */
package com.saic.ebiz.wechat.authorization;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.saic.ebiz.wechat.util.CommonUtil;
import com.saic.ebiz.wechat.util.PropertiesUtil;

/**
 * 参考
 * http://mp.weixin.qq.com/wiki/index.php?title=%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF
 * 
 * @author hejian
 *
 */
@Service
public class AuthorizationService {
	
	private static Logger logger = LoggerFactory.getLogger(AuthorizationService.class); 

    /**
     * 微信应用唯一ID
     * 车享购服务号 wxc2c9c0c1d5115808
     * 测试账号        wx867e1eccd949be40
     * 
     */
    @Value("${ebiz.wap.web.appId:}")
    protected String appId;
    
    @Value("${ebiz.wap.web.appSecret:}")
    protected String appSecret;
    
	/** 授权链接url */
	private static String authorization_url = PropertiesUtil.HTTPS_AUTHORIZATION;
	private static String access_token_url = PropertiesUtil.HTTPS_ACCESS_TOKEN;
	private static String refresh_token_url = PropertiesUtil.HTTPS_REFRESH_TOKEN;
	private static String userinfo_url = PropertiesUtil.HTTPS_USERINFO_URL;
	
	/**
	 * 构建授权链接url
	 * @param appId
	 * @param redirect_uri
	 * @param scope @see {@link Scope}
	 * @return
	 */
	public String buildAuthorizationLink(String appId, String redirect_uri, Scope scope) {
		logger.info("buildAuthorizationLink => appId : {} , redirect_uri : {} , scope : {} ", appId , redirect_uri, scope);
		String result = authorization_url.replace("APPID", appId).replace("REDIRECT_URI", CommonUtil.encodeURL(redirect_uri))
						.replace("SCOPE", scope.name());
		logger.info("buildAuthorizationLink => 返回 ：" + result); 
		return result;
	}

	public boolean isWeiXinBrowser(HttpServletRequest request) {
	    boolean isWeiXinBrowser =false;
	    String userAgent = request.getHeader("User-Agent") + "";
        if (StringUtils.isNotBlank(userAgent)
                && userAgent.toLowerCase().contains("micromessenger")) {
            isWeiXinBrowser = true;
        }
        
        return isWeiXinBrowser;
	}

	/**
	 * 
	 *   一句话功能简述 <br>
	 * 〈获取微信用户基础信息〉
	 *
	 * @param code	微信code
	 * @param scope 作用域
	 * @return    属性参数
	 *  SNSUserInfo    返回信息 
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public SNSUserInfo getSNSUserInfo(String code,Scope scope) {
			SNSUserInfo snsUserInfo = null;
			if ((code != null && !"".equals(code))) {
				Oauth2Token oauth2Token = this.getOauth2Token(
						appId, appSecret, code);
				if(oauth2Token!=null){
					if (Scope.snsapi_userinfo==scope) {
						snsUserInfo = this.getUserInfo(oauth2Token.getAccessToken(), oauth2Token.getOpenId());
					}
					if(snsUserInfo==null){
						snsUserInfo=new SNSUserInfo();
						snsUserInfo.setOpenId(oauth2Token.getOpenId());
					}
				}
			}
		logger.info("zcm--getWxCheckInfo--code:+"+code+"--scope:"+scope+"--" + JSON.toJSONString(snsUserInfo));
		return snsUserInfo;
	}
	
	/**
	 * 获取网页授权凭证
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public Oauth2Token getOauth2Token(String appId,String appSecret,String code){
		Oauth2Token oauth2Token = null;
		String requestUrl = access_token_url.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);
		logger.info("getOauth2Token => ###### requestUrl : " + requestUrl);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if(null != jsonObject){
			try{
				oauth2Token = new Oauth2Token();
				oauth2Token.setAccessToken(jsonObject.getString("access_token"));
				oauth2Token.setExpiresIn(jsonObject.getInt("expires_in"));
				oauth2Token.setRefreshToken(jsonObject.getString("refresh_token"));
				oauth2Token.setOpenId(jsonObject.getString("openid"));
				oauth2Token.setScope(jsonObject.getString("scope"));
			}catch(Exception e){
				oauth2Token = null;
				int errCode = jsonObject.getInt("errcode");
				String errMsg = jsonObject.getString("errmsg");
				logger.error("获取网页授权凭证失败 errCode : {} , errmsg : {} ", errCode,errMsg);
			}
		}
		logger.info("getOauth2Token => 返回 ：" + com.alibaba.fastjson.JSONObject.toJSONString(oauth2Token)); 
		return oauth2Token;
	}
	
	/**
	 * 刷新网页授权凭证
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public Oauth2Token refreshOauth2Token(String appId,String refreshToken){
		logger.info("refreshOauth2Token appId : {} , refreshToken : {} ", appId, refreshToken);
		Oauth2Token oauth2Token = null;
		String requestUrl = refresh_token_url.replace("APPID", appId).replace("REFRESH_TOKEN", refreshToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if(null != jsonObject){
			try{
				oauth2Token = new Oauth2Token();
				oauth2Token.setAccessToken(jsonObject.getString("access_token"));
				oauth2Token.setExpiresIn(jsonObject.getInt("expires_in"));
				oauth2Token.setRefreshToken(jsonObject.getString("refresh_token"));
				oauth2Token.setOpenId(jsonObject.getString("openid"));
				oauth2Token.setScope(jsonObject.getString("scope"));
			}catch(Exception e){
				oauth2Token = null;
				int errCode = jsonObject.getInt("errcode");
				String errMsg = jsonObject.getString("errmsg");
				logger.error("刷新网页授权凭证失败 errCode : {} , errmsg : {} ", errCode,errMsg);
			}
		}
		logger.info("refreshOauth2Token => 返回 ：" + com.alibaba.fastjson.JSONObject.toJSONString(oauth2Token)); 
		return oauth2Token;
	}

	/**
	 * 通过网页授权获取用户信息
	 * @param accessToken 授权凭证
	 * @param openId 用户唯一标识
	 * @see SNSUserInfo
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public SNSUserInfo getUserInfo(String accessToken,String openId){
		logger.info("getUserInfo accessToken : {} , openId : {} ", accessToken, openId);
		SNSUserInfo user = null;
		String requestUrl = userinfo_url.replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if(null != jsonObject){
//			logger.info("授权返回JSONObject 数据 : " + com.meidusa.fastjson.JSONObject.toJSONString(jsonObject));
			logger.info("授权返回JSONObject 数据 : Key : {}, values : {} " ,com.alibaba.fastjson.JSONObject.toJSONString(jsonObject.keySet()), 
					com.alibaba.fastjson.JSONObject.toJSONString(jsonObject.values()));
			try{
				user = new SNSUserInfo();
				user.setOpenId(jsonObject.getString("openid"));
				user.setNickname(jsonObject.getString("nickname"));
				user.setSex(jsonObject.getInt("sex"));
				user.setProvince(jsonObject.getString("province"));
				user.setCity(jsonObject.getString("city"));
				user.setCountry(jsonObject.getString("country"));
				user.setHeadimgurl(jsonObject.getString("headimgurl"));
				user.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
			}catch(Exception e){
				logger.error("网页授权获取用户信息失败 " + e.getMessage());
				user = null;
				int errCode = jsonObject.getInt("errcode");
				String errMsg = jsonObject.getString("errmsg");
				logger.error("网页授权获取用户信息失败 errCode : {} , errmsg : {} ", errCode,errMsg);
			}
		}
		logger.info("getUserInfo => 返回  : " + com.alibaba.fastjson.JSONObject.toJSONString(user));
		return user;		
	}
	/**
	 * 应用授权作用域：<br/>
	 * snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），<br/>
	 * snsapi_userinfo	（弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）,
	 * login_snsapi_base	（微信授权登录车享会员url，默认授权通过openid登录），<br/>
	 * login_snsapi_userinfo	（微信授权登录车享会员url，显示授权通过openid拿到昵称、性别灯，登录），<br/>
	 * */
	public static enum Scope {
		snsapi_base, snsapi_userinfo;
	}
	
}
