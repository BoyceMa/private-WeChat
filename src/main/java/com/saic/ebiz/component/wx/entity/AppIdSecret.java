/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 */
package com.saic.ebiz.component.wx.entity;

/**
 * @author hejian
 * 
 */
public class AppIdSecret {
	/** 微信服务号AppID(应用ID) */
	private String appId;

	/** 微信服务号AppSecret(应用密钥) */
	private String appSecret;

	/**
 * 
 */
	public AppIdSecret() {
	}

	/**
	 * @param appId
	 * @param appSecret
	 */
	public AppIdSecret(String appId, String appSecret) {
		super();
		this.appId = appId;
		this.appSecret = appSecret;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the appSecret
	 */
	public String getAppSecret() {
		return appSecret;
	}

	/**
	 * @param appSecret
	 *            the appSecret to set
	 */
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppIdSecret [appId=" + appId + ", appSecret=" + appSecret + "]";
	}

}
