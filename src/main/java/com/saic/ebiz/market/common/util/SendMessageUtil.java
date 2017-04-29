package com.saic.ebiz.market.common.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.saic.ebiz.market.common.entity.response.Article;
import com.saic.ebiz.market.common.entity.response.SendImageMessage;
import com.saic.ebiz.market.common.entity.response.SendImageMessage.ImageContent;
import com.saic.ebiz.market.common.entity.response.SendMusicMessage;
import com.saic.ebiz.market.common.entity.response.SendMusicMessage.MusicContent;
import com.saic.ebiz.market.common.entity.response.SendNewsMessage;
import com.saic.ebiz.market.common.entity.response.SendNewsMessage.NewsContent;
import com.saic.ebiz.market.common.entity.response.SendTextMessage;
import com.saic.ebiz.market.common.entity.response.SendTextMessage.TextContent;
import com.saic.ebiz.market.common.entity.response.SendVideoMessage;
import com.saic.ebiz.market.common.entity.response.SendVideoMessage.VideoContent;
import com.saic.ebiz.market.common.entity.response.SendVoiceMessage;
import com.saic.ebiz.market.common.entity.response.SendVoiceMessage.VoiceContent;
import com.saic.ebiz.market.common.enumeration.MessageType;

/**
 * 返回消息工具类
 * @author csdwz
 */
public class SendMessageUtil {

	 private static Logger logger = LoggerFactory.getLogger(SendMessageUtil.class);
	
	/**
	 * 请求路径
	 */
	private static String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=AT";
	
	/**
	 * 发送消息
	 * @param appid
	 * @param appsecret
	 * @param message 具体消息实体
	 * @return
	 */
	public static boolean sendMessage(String appid, String appsecret, Object message, String accessToken){
		/**
		 * 消息发送状态 true:成功  false:失败
		 */
		boolean flag = false;
		
		//Token accessToken = CommonUtil.getAccessToken(appid, appsecret);
		if(null != accessToken){
			logger.info("发送用户消息工具类textMessage获取到的token:{}",accessToken);
			requestUrl = requestUrl.replace("AT", accessToken);
			
			/**
			 * 将消息转换为json数据
			 */
			String jsonMessage = JSONObject.toJSONString(message);
			
			/**
			 * 发送消息
			 */
			CommonUtil.httpsRequest(requestUrl, "POST", jsonMessage);
			
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 发送文本消息
	 * @param openid  用户openid
	 * @param appid   
	 * @param appsecret
	 * @param text  文本消息内容
	 * @return
	 */
	public static boolean sendTextMessage(String openid,String appid, String appsecret, String text, String accessToken){
		
		/**
		 * 创建消息
		 */
		SendTextMessage rtm = new SendTextMessage();
		
		rtm.setTouser(openid);
		rtm.setMsgtype(MessageType.TEXT.code());
		/**
		 * 消息内容
		 */
		TextContent tc = new SendTextMessage.TextContent();
		tc.setContent(text);
		rtm.setText(tc);
		
		/**
		 * 发送消息
		 */
		boolean sendMessageFlag = sendMessage(appid, appsecret, rtm,accessToken);
		
		return sendMessageFlag;
	}
	
	/**
	 * 发送图片消息
	 * @param openid
	 * @param appid
	 * @param appsecret
	 * @param mediaid 图片的media_id
	 * @return
	 */
	public static boolean sendImageMessage(String openid, String appid, String appsecret, String media_id,String accessToken){
		
		/**
		 * 创建消息
		 */
		SendImageMessage sim = new SendImageMessage();
		
		sim.setTouser(openid);
		sim.setMsgtype(MessageType.IMAGE.code());
		/**
		 * 消息内容
		 */
		ImageContent ic = new SendImageMessage.ImageContent();
		ic.setMedia_id(media_id);
		sim.setImage(ic);
		
		/**
		 * 发送消息
		 */
		boolean sendMessageFlag = sendMessage(appid, appsecret, sim,accessToken);
		
		return sendMessageFlag;
	}
	
	/**
	 * 发送语音消息
	 * @param openid
	 * @param appid
	 * @param appsecret
	 * @param media_id 发送的语音的媒体ID
	 * @return
	 */
	public static boolean sendVoiceMessage(String openid, String appid, String appsecret, String media_id,String accessToken){
		
		/**
		 * 创建消息
		 */
		SendVoiceMessage svm = new SendVoiceMessage();
		
		svm.setTouser(openid);
		svm.setMsgtype(MessageType.VOICE.code());
		/**
		 * 消息内容
		 */
		VoiceContent vc = new SendVoiceMessage.VoiceContent();
		vc.setMedia_id(media_id);
		svm.setVoice(vc);
		
		/**
		 * 发送消息
		 */
		boolean sendMessageFlag = sendMessage(appid, appsecret, svm,accessToken);
		
		return sendMessageFlag;
	}
	
	/**
	 * 发送视频消息
	 * @param openid
	 * @param appid
	 * @param appsecret
	 * @param title 视频消息的标题
	 * @param description 视频消息的描述
	 * @param thumb_media_id 缩略图的媒体ID
	 * @param media_id 发送的视频的媒体ID
	 * @return
	 */
	public static boolean sendVedioMessage(String openid, String appid, String appsecret, String title, String description, String thumb_media_id, String media_id,String accessToken ){
		
		/**
		 * 创建消息
		 */
		SendVideoMessage svm = new SendVideoMessage();
		
		svm.setTouser(openid);
		svm.setMsgtype(MessageType.VIDEO.code());
		/**
		 * 消息内容
		 */
		VideoContent vc = new SendVideoMessage.VideoContent();
		vc.setTitle(title);
		vc.setDescription(description);
		vc.setThumb_media_id(thumb_media_id);
		vc.setMedia_id(media_id);
		
		/**
		 * 发送消息
		 */
		boolean sendMessageFlag = sendMessage(appid, appsecret, svm,accessToken);
		
		return sendMessageFlag;
	}
	
	/**
	 * 发送音乐消息
	 * @param openid
	 * @param appid
	 * @param appsecret
	 * @param title 音乐标题
	 * @param description 音乐描述
	 * @param musicurl 音乐链接
	 * @param hqmusicurl 高品质音乐链接，wifi环境优先使用该链接播放音乐
	 * @param thumb_media_id 缩略图的媒体ID
	 * @return
	 */
	public static boolean sendMusicMessage(String openid, String appid, String appsecret, String title, String description, String musicurl, String hqmusicurl, String thumb_media_id,String accessToken){
		
		/**
		 * 创建消息
		 */
		SendMusicMessage smm = new SendMusicMessage();
		
		smm.setTouser(openid);
		smm.setMsgtype(MessageType.MUSIC.code());
		/**
		 * 消息内容
		 */
		MusicContent mc = new SendMusicMessage.MusicContent();
		mc.setTitle(title);
		mc.setDescription(description);
		mc.setMusicurl(musicurl);
		mc.setHqmusicurl(hqmusicurl);
		mc.setThumb_media_id(thumb_media_id);
		
		/**
		 * 发送消息
		 */
		boolean sendMessageFlag = sendMessage(appid, appsecret, smm,accessToken);
		
		return sendMessageFlag;
	}
	
	/**
	 * 发送用户图文消息
	 * @param openid
	 * @param appid
	 * @param appsecret
	 * @param articles  图文消息实体
	 * @return
	 */
	public static boolean sendNewsMessage(String openid, String appid, String appsecret, List<Article> articles,String accessToken){
		
		/**
		 * 创建消息
		 */
		SendNewsMessage snm = new SendNewsMessage();
		
		snm.setTouser(openid);
		snm.setMsgtype(MessageType.NEWS.code());
		/**
		 * 消息内容
		 */
		NewsContent nc = new SendNewsMessage.NewsContent();
		nc.setArticles(articles);
		
		/**
		 * 发送消息
		 */
		boolean sendMessageFlag = sendMessage(appid, appsecret, snm,accessToken);
		
		return sendMessageFlag;
	}
}
