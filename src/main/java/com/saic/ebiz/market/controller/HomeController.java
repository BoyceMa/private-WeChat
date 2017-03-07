/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * 
 */
package com.saic.ebiz.market.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.saic.ebiz.component.wx.job.AccessTokenJob;
import com.saic.ebiz.component.wx.job.TicketJob;
import com.saic.ebiz.market.annotation.BaseOauth;
import com.saic.ebiz.market.annotation.UserInfoOauth;
import com.saic.ebiz.market.common.constant.Constants;
import com.saic.ebiz.market.common.entity.authentication.Oauth2Token;
import com.saic.ebiz.market.common.entity.authentication.SNSUserInfo;

/**
 * @author hejian
 * 
 */
@RestController
public class HomeController {

	@Autowired
	private AccessTokenJob accessTokenJob;
	
	@Autowired
	private TicketJob ticketJob;
	
	@RequestMapping("/index")
	@BaseOauth(appId = Constants.APP_ID, appSecret = Constants.APP_SECRET)
	public Oauth2Token home(HttpServletRequest request, Oauth2Token oauth2Token) {
		return oauth2Token;
	}
	
	@RequestMapping("/snsInfo")
	@UserInfoOauth
	public SNSUserInfo index(HttpServletRequest request, Oauth2Token oauth2Token, SNSUserInfo sNSUserInfo) {
		System.out.println(JSON.toJSONString(oauth2Token));
		return sNSUserInfo;
	}
	
	@RequestMapping("token")
	public String token(){
		return accessTokenJob.getAccessToken(null);
	}
	
	@RequestMapping("ticket")
	public String ticket(){
		return ticketJob.getTicket();
	}
}
