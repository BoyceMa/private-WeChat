package com.saic.ebiz.market.common.entity.response;

/**
 * 发送文本消息类
 * @author csdwz
 *
 */
public class SendTextMessage extends BaseSendMessage{
	private static final long serialVersionUID = -696426386261501950L;
	/**
	 * 内部类  
	 */
	private TextContent text;
	
	public static class TextContent{
		/**
		 * 消息内容
		 */
		private String content;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

	public TextContent getText() {
		return text;
	}

	public void setText(TextContent text) {
		this.text = text;
	}
}
