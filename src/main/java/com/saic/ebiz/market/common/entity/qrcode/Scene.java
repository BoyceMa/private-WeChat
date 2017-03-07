/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * 
 */
package com.saic.ebiz.market.common.entity.qrcode;

/**
 * 二维码场景
 * 
 * @author hejian
 *
 */
public class Scene {
	/** 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）  */
	private Integer scene_id;

	/** 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段  */
	private String scene_str;
	/**
	 * @return the scene_id
	 */
	public Integer getScene_id() {
		return scene_id;
	}

	/**
	 * @param scene_id the scene_id to set
	 */
	public void setScene_id(Integer scene_id) {
		this.scene_id = scene_id;
	}

	/**
	 * @return the scene_str
	 */
	public String getScene_str() {
		return scene_str;
	}

	/**
	 * @param scene_str the scene_str to set
	 */
	public void setScene_str(String scene_str) {
		this.scene_str = scene_str;
	}
	
}
