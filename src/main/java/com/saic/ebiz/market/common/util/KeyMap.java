/**
 *
 * Copyright (C), 2013-2013, 上海汽车集团股份有限公司
 * FileName : KeyMap.java
 * Author : 何剑
 * Date : 2014年9月21日
 * 
 */
package com.saic.ebiz.market.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hejian
 *
 *  @date 2014年9月21日
 */
public class KeyMap {
    
    public static Map<String,String> keyMap = new HashMap<String, String>();
    
    /** 半价买车 */
    public static final String HALF_BUY_CAR = "half_buy_car";
    
    /** 询价购车 */
    public static final String INQUIRY_BUY_CAR = "inquiry_buy_car";
    
    /** 一口价 */
    public static final String SOLID_PRICE = "solid_price";
    
    /** 第一年盛典  */
    public static final String FIRST_YEAR_CEREMONY = "first_year_ceremony";
    
    /** 我的询价 KEY */
    public static final String CUSTOMER_PRICE = "customer_price";
    
    /** 我的订单 KEY */
    public static final String CUSTOMER_ORDER = "customer_order";
    
    /** 我的积分 KEY */
    public static final String CUSTOMER_POINTS = "customer_points";
    
//    /** 我的保养 KEY */
//    public static final String MAINTENANCE = "maintenance";
    
//    /** 车查询 KEY */
//    public static final String CAR_QUERY = "car_query";
    
    /** 在线咨询 KEY */
    public static final String SERVICE_ONLINE = "service_online";
    
    /** 客服电话 KEY */
    public static final String SERVICE_PHONE = "service_phone";
    
    /** 328主会场 KEY */
    public static final String PROMOTION_328 = "promotion_328";
    /** 微博活动 KEY */
    public static final String PROMOTION_WEIBO = "promotion_weibo";
    /** 微信活动 KEY */
    public static final String PROMOTION_WEIXIN = "promotion_weixin";
    
    public static final String CLUB = "club";
    
    static{
        keyMap.put(HALF_BUY_CAR, HALF_BUY_CAR);
        keyMap.put(INQUIRY_BUY_CAR, INQUIRY_BUY_CAR);
        keyMap.put(SOLID_PRICE, SOLID_PRICE);
        keyMap.put(CUSTOMER_PRICE, CUSTOMER_PRICE);
        keyMap.put(CUSTOMER_ORDER, CUSTOMER_ORDER);
        keyMap.put(CUSTOMER_POINTS, CUSTOMER_POINTS);
        keyMap.put(SERVICE_ONLINE, SERVICE_ONLINE);
        keyMap.put(SERVICE_PHONE, SERVICE_PHONE);
        keyMap.put(PROMOTION_328, PROMOTION_328);
        keyMap.put(PROMOTION_WEIBO, PROMOTION_WEIBO);
        keyMap.put(PROMOTION_WEIXIN, PROMOTION_WEIXIN);
        keyMap.put(CLUB, CLUB);
    }
}
