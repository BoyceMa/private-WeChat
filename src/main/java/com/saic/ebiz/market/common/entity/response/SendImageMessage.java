package com.saic.ebiz.market.common.entity.response;
/**
 * 发送图片消息类
 * @author csdwz
 *
 */
public class SendImageMessage extends BaseSendMessage{
	private static final long serialVersionUID = 3915923658279718871L;
	/**
	 * 内部类  
	 */
	private ImageContent image;
	
	public static class ImageContent{
		/**
		 * 发送的图片的媒体ID
		 */
		private String media_id;

		public String getMedia_id() {
			return media_id;
		}

		public void setMedia_id(String media_id) {
			this.media_id = media_id;
		}
	}

	public ImageContent getImage() {
		return image;
	}

	public void setImage(ImageContent image) {
		this.image = image;
	}
}
