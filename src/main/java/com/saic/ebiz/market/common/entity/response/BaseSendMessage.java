package com.saic.ebiz.market.common.entity.response;

import java.io.Serializable;

public class BaseSendMessage implements Serializable{

	private static final long serialVersionUID = -256499723304644097L;
	/**
	 * 普通用户openid
	 */
	private String touser;
	/**
	 * 消息类型
	 */
	private String msgtype;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
}
