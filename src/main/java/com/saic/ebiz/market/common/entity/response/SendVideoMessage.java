package com.saic.ebiz.market.common.entity.response;

/**
 * 发送视频消息类
 * @author csdwz
 *
 */
public class SendVideoMessage extends BaseSendMessage{
	private static final long serialVersionUID = 4420755048038720912L;
	/**
	 * 内部类  
	 */
	private VideoContent video;
	
	public static class VideoContent{
		/**
		 * 视频消息的标题
		 */
	    private String title;
	    /**
	     * 视频消息的描述
	     */
		private String description;
		/**
		 * 发送的视频的媒体ID
		 */
		private String media_id;
		/**
		 * 发送的视频的媒体ID
		 */
		private String thumb_media_id;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getMedia_id() {
			return media_id;
		}
		public void setMedia_id(String media_id) {
			this.media_id = media_id;
		}
		public String getThumb_media_id() {
			return thumb_media_id;
		}
		public void setThumb_media_id(String thumb_media_id) {
			this.thumb_media_id = thumb_media_id;
		}
		
	}

	public VideoContent getVideo() {
		return video;
	}

	public void setVideo(VideoContent video) {
		this.video = video;
	}
}
