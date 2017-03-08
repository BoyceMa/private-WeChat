package com.saic.ebiz.wechat.annotation;
/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 */


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 微信UserInfo授权
 * 
 * @author hejian
 *
 */
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserInfoOauth {
	
	String appId() default "";
	
	String appSecret() default "";
}
