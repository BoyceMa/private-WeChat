/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * 
 */
package com.saic.ebiz.market.common.entity.qrcode;

/**
 * @author hejian
 * 
 */
public class QrcodeFactory {
	
	private QrcodeFactory() {
	}
	
	/**
	 * type int or string
	 * @return 永久二维码
	 */
	public static Qrcode createPermanentQrcode(String sceneValue,String type){
		Qrcode qrcode = new Qrcode();
		qrcode.setAction_name("QR_LIMIT_SCENE");
		ActionInfo actionInfo = new ActionInfo();
		Scene scene = new Scene();
		if(type.equals("int")){
			scene.setScene_id(Integer.valueOf(sceneValue));
		}else if(type.equals("string")){
			scene.setScene_str(sceneValue);
		}else{
			throw new RuntimeException("type 只能为int或者string");
		}
		actionInfo.setScene(scene);
		qrcode.setAction_info(actionInfo);
		return qrcode;
	}
	
	/**
	 * 
	 * @return 临时二维码
	 */
	public static Qrcode createTemporaryQrcode(){
		Qrcode qrcode = new Qrcode();
		qrcode.setAction_name("QR_SCENE");
		ActionInfo actionInfo = new ActionInfo();
		qrcode.setExpire_seconds(1800);
		Scene scene = new Scene();
		scene.setScene_id(8888);
		actionInfo.setScene(scene);
		qrcode.setAction_info(actionInfo);
		return qrcode;
	}
}
