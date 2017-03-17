/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * 
 */
package com.saic.ebiz.market.common.entity.qrcode;

import com.alibaba.fastjson.JSONObject;


/**
 * @author hejian
 * 
 */
public class Qrcode {

	/** 二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久 */
	private String action_name;

	/** 该二维码有效时间，以秒为单位。 最大不超过1800。 */
	private Integer expire_seconds;
	
	/** 二维码详细信息 */
	private ActionInfo action_info;

	/**
	 * @return the action_name
	 */
	public String getAction_name() {
		return action_name;
	}

	/**
	 * @param action_name
	 *            the action_name to set
	 */
	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}

	/**
	 * @return the expire_seconds
	 */
	public Integer getExpire_seconds() {
		return expire_seconds;
	}

	/**
	 * @param expire_seconds
	 *            the expire_seconds to set
	 */
	public void setExpire_seconds(Integer expire_seconds) {
		this.expire_seconds = expire_seconds;
	}

	/**
	 * @return the action_info
	 */
	public ActionInfo getAction_info() {
		return action_info;
	}

	/**
	 * @param action_info
	 *            the action_info to set
	 */
	public void setAction_info(ActionInfo action_info) {
		this.action_info = action_info;
	}
	
	public static void main(String[] args) {
		Qrcode qrcode = new Qrcode();
		qrcode.setAction_name("QR_LIMIT_SCENE");
		ActionInfo actionInfo = new ActionInfo();
		Scene scene = new Scene();
		scene.setScene_str("20153280001");
		actionInfo.setScene(scene);
		qrcode.setAction_info(actionInfo);
		System.out.println(JSONObject.toJSONString(qrcode));
	}
}
