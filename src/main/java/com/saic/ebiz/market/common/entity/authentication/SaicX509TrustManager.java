/**
 *
 * Copyright (C), 2013-2013, 上海汽车集团股份有限公司
 * FileName : Token.java
 * Author : 何剑
 * Date : 2014年9月22日
 * 
 */
package com.saic.ebiz.market.common.entity.authentication;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 证书信任管理器（用于https请求）
 * 
 */
public class SaicX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
