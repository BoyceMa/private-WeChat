/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 * 
 */
package com.saic.ebiz.market.common.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author hejian
 *
 */
@Component
public class ImageUtil {
	
    @Value("${ebiz.wap.web.domain:}")
	private String domain;
	
	@Value("resources/images/") 
	private String imageBase;
	
	public static final String DEFAULT_RESOLUTION = "300x200";
	
	/**
	 * 
	 * @param image 图片的名称(包括路径，比如：logo/xxx.jpg)
	 * @return 图片的完整路径
	 */
	public String getImageUrl(String image){
	    System.out.println("------------------------load1-------------------------domainvalue:"+domain);
	    if(domain!=null&&StringUtils.isBlank(domain)){
	        domain="http://mgo.pre.chexiang.com/)";
	    }
	    System.out.println("------------------------load2-----------------------domainvalue:"+domain);
		return domain + imageBase + image;
	}
	
	/**
	 * 
	 * @param resolution 图片分辨率的大小 如300X200
	 * @param imageUrl 图片的url
	 * @return
	 */
	public static String getStaticImageUrl(String resolution,String imageUrl){
		return "/" + resolution + "/" + imageUrl;
	}
}
