package com.saic.ebiz.market.common.entity.response;

import java.util.List;

/**
 * 发送图文消息类
 * @author csdwz
 *
 */
public class SendNewsMessage extends BaseSendMessage{

	private static final long serialVersionUID = -1926459590184903558L;
	/**
	 * 内部类  图文消息 
	 */
	private NewsContent news;
	
	public static class NewsContent{
		/**
		 *  图文消息
		 */
		private List<Article> articles;

		public List<Article> getArticles() {
			return articles;
		}

		public void setArticles(List<Article> articles) {
			this.articles = articles;
		}
	}

	public NewsContent getNews() {
		return news;
	}

	public void setNews(NewsContent news) {
		this.news = news;
	}
	
}
