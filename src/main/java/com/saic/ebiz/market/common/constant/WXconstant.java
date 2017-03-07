package com.saic.ebiz.market.common.constant;


public class WXconstant {
	
	public final static String WXACOUNT = "gh_faf119fca328";//车享购
	
	//token有效期
	public final static int TOKENTIME =   7200; 
	
	//token换取openid超时时间30
	public final static int REDISTIME =   1800;
	
    //    第三方用户唯一凭证  
//    public final static String APPID =  "wx11171b264f347e84";//车享购
//    public final static String APPSECRET =  "b1dbeb044767a2808e79943530714066";//车享购
	
	
//	 //    第三方用户唯一凭证  
//    public final static String APPID =  "wx2a8a303fe5cb81a7";//车享购
//    public final static String APPSECRET =  "28468ea1ab4c40b1fb48b2de6a555826";//车享购

//  PRE
//    public final static String APPID =  "wxabc8f55d4f328c11";//车享购
//    public final static String APPSECRET =  "3c00c069f6c2673ed5f457121f030207";//车享购
//	SIT(michael)
//    public final static String APPID =  "wx867e1eccd949be40";//车享购
//    public final static String APPSECRET =  "569db832b89eb72f009d2c4bcd981859";//车享购
//    
    
//	  第三方用户唯一凭证  正式环境
	public final static String APPID =  "wxc2c9c0c1d5115808";//车享购
	public final static String APPSECRET =  "bdf9bedfd1e36dc2797cddc02847cb88";//车享购

//	
	public final static String REDIS_WX_TOKEN_KEY="REDIS_WX_TOKEN_KEY";
	//redis 请求时间5s
	public final static long REDIS_CACHE_TOKEN_EXPIRE_TIME = 5;
	
	
	
	public static final String REDIS_CACHE_TOKEN_VALUE = "REDIS_CACHE_TOKEN_VALUE";
	
	//token类别: 0 - 微信token  
	public static final int REDIS_TOKEN_TYPE = 0;
	
	//线程睡眠时间
	public static final int SED_RESULT_TIME = 3000;
	
	//释放锁时间
    public static final int  RELEASE_LOCK_TIME = 1000;
}
