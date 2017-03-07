package com.saic.ebiz.market.common.entity.response;
/**
 * 发送语音消息类
 * @author csdwz
 *
 */
public class SendVoiceMessage extends BaseSendMessage{
	private static final long serialVersionUID = -6368409299461049341L;
	/**
	 * 内部类  
	 */
	private VoiceContent voice;
	
	public static class VoiceContent{
		/**
		 * 发送的语音的媒体ID
		 */
		private String media_id;

		public String getMedia_id() {
			return media_id;
		}

		public void setMedia_id(String media_id) {
			this.media_id = media_id;
		}
	}

	public VoiceContent getVoice() {
		return voice;
	}

	public void setVoice(VoiceContent voice) {
		this.voice = voice;
	}

}
