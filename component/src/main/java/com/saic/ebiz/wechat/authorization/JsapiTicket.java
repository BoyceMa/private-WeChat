/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 */
package com.saic.ebiz.wechat.authorization;

/**
 * jsapi凭证对象
 * 
 * @author hejian
 * 
 */
public class JsapiTicket {
	/** 错误码 */
	private String errcode;
	
	/** 错误信息 */
	private String errmsg;
	
	/** ticket凭证 */
	private String ticket;
	
	/** 过期时间 */
	private int expiresIn;

	/**
	 * @return the errcode
	 */
	public String getErrcode() {
		return errcode;
	}

	/**
	 * @param errcode
	 *            the errcode to set
	 */
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	/**
	 * @return the errmsg
	 */
	public String getErrmsg() {
		return errmsg;
	}

	/**
	 * @param errmsg
	 *            the errmsg to set
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	/**
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * @param ticket
	 *            the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * @return the expires_in
	 */
	public int getExpiresIn() {
		return expiresIn;
	}

	/**
	 * @param expires_in
	 *            the expires_in to set
	 */
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "JsapiTicket [errcode=" + errcode + ", errmsg=" + errmsg + ", ticket=" + ticket + ", expiresIn="
                + expiresIn + "]";
    }
	
	
}
