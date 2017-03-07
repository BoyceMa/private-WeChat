/**
 *
 * Copyright (C), 2013-2013, 上海汽车集团股份有限公司
 * FileName : CoreService.java
 * Author : 何剑
 * Date : 2014年9月21日
 * 
 */
package com.saic.ebiz.market.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.meidusa.toolkit.common.util.Base64;
import com.saic.ebiz.market.common.constant.Constants;
import com.saic.ebiz.market.common.entity.response.TextMessageReponse;
import com.saic.ebiz.market.common.util.ImageUtil;
import com.saic.ebiz.market.common.util.MessageUtil;

/**
 * @author hejian
 * @date 2014年9月21日
 */
@Component
public class CoreService {
	// 日志
	private Logger logger = LoggerFactory.getLogger(CoreService.class);

	@Value("${ebiz.wap.web.domain:}")
	private String domain;

	@Resource
	private AuthorizationService authorizationService;
	
	@Resource
	private ImageUtil imageUtil;

	/**
	 * sit
	 * 	  mgo.sit.chexiang.com
	 *    本地测试192.168.26.141
	 * pre
	 * 	  mgo.pre.chexiang.com
	 * pro
	 *    mgo.chexiang.com
	 */
	@Value("${ebiz.wap.web.redirectHost:}")
	private String redirectHost;
	
	/**
	 * 微信应用唯一ID
	 * 车享购服务号 wxc2c9c0c1d5115808
	 * 测试账号        wx867e1eccd949be40
	 * 
	 */
	@Value("${ebiz.wap.web.appId:}")
	private String appId;
	
	/**
	 * 
	 * @Title:处理微信发来的请求
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request,
			Map<String, String> requestMap) {
		// xml模板内容
		String respMessage = null;
		String respContent = "";
		TextMessageReponse textMessage = new TextMessageReponse();
		try {
			// xml请求解析
			// Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get(Constants.FROM_USER_NAME);
			// 公众帐号
			String toUserName = requestMap.get(Constants.TO_USER_NAME);
			// 消息类型
			String msgType = requestMap.get(Constants.MSG_TYPE);

			logger.info("接受请求  { 发送方账号 fromUserName(openId) : " + fromUserName + ", 公众帐号 toUserName : " + toUserName + " {},消息类型 msgType :" + msgType);

			// 回复文本消息
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			    //获取当前用户的openId
                Object obj = requestMap.get("Content");
                respContent = obj.toString();
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				 respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 视频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				respContent = "您发送的是视频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get(Constants.REQUEST_PARAMETER_EVENT);
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "您好，欢迎关注车享新车服务号。回复1查看车享新车，回复2查看品牌馆活动车型。如有任何疑问可输入您的问题，并以“？”结尾。";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
					logger.info("openid : {} 取消关注 ", fromUserName);
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
					logger.info("扫描事件");
					//情景二维码扫描
					//免费送车
					String eventKey = requestMap.get("EventKey");
					logger.info("eventKey : " + eventKey);
					respContent = eventKey;
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
					logger.info("地理事件");
					respContent = "地理事件";
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					logger.info("Click事件");
					respContent = "Click事件";
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
					logger.info("点击菜单点击事件");
					respContent = "点击菜单点击事件";
					//该消息不会回复给用户
				}
			}
		} catch (Exception e) {
			logger.error("---消息回复异常--：" + e.getMessage());
//			respContent = e.getMessage();
		}
		logger.info("textMessage.setContent(respContent)  : " + respContent);
		textMessage.setContent(respContent);
		respMessage = MessageUtil.messageToXml(textMessage);
		return respMessage;
	}
	
	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String content = Base64.encode("328活动，还有呢？".getBytes());
		content = URLEncoder.encode(content,"UTF-8");
		System.out.println(content);
		content = "MzI45rS75Yqo77yM6L+Y5pyJ5ZGi77yf";
		content = URLDecoder.decode(content,"UTF-8");
		System.out.println(content);
//		System.out.println(new String(Base64.decode(content)));
	}
}

