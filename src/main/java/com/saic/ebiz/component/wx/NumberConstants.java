package com.saic.ebiz.component.wx;


public class NumberConstants {
	
	/** (单位分钟)ticket在失效的20分钟前，重新获取ticket */
	public final static long BEFORE_EXPIRY_20_MINUTES = 20*60;
	
	/** (单位毫秒)线程睡眠1分钟 */
	public final static long THREAD_SLEEP_ONE_MINUTE = 60*1000; 
	
	/** (单位毫秒)线程睡眠1分钟 */
    public final static long THREAD_SLEEP_5_SECONDS = 5*1000; 
	
	public final static int REDIS_EXPIRE_TEN_MINUTES = 60 * 10; 
	
	public final static int REDIS_EXPIRE_HALF_HOUR = 60 * 30; 
	
	public final static int REDIS_EXPIRE_ONE_HOUR = 60 * 60; 
	
	public final static int REDIS_EXPIRE_ONE_AND_HALF_HOUR = 60 * 90;
	
	/** redis ttl未找到返回-2 */
	public final static long NOT_FOUND = -2;
	
	/** redis ttl永不过期-1 */
	public final static long NEVER_EXPIRY = -1;
	
	/** 分布式锁setNX */
	public final static String PREFIX_TOKEN_LOCK = "AccessToken_Lock_";
	
	public final static String PREFIX_TICKET_LOCK = "Ticket_Lock_";
	
	public final static String PREFIX_TOKEN_TICKET_LOCK = "TokenTicket_Lock_";
	
	public final static String TOKEN_TICKET_LOCK_VALUE = "lock";
	
	/** 分布式锁超时时间 : 2分钟 */
	public final static int LOCK_TIMEOUT = 2*60;
	
	/** 分布式锁超时key */
	public final static String PREFIX_TOKEN_TIMEOUT = "AccessToken_Lock_Timeout_";
	
	public final static String PREFIX_TICKET_TIMEOUT= "Ticket_Lock_Timeout_";
	
	public final static String PREFIX_REDIS_ACCESS_TOKEN = "AccessToken_"; 
	
	public final static String PREFIX_REDIS_TICKET = "Ticket_";
	
	public final static String REDIS_CACHE_VALUE = "REDIS_CACHE_VALUE";
	
	/** 获取access_token的接口地址URL（GET） 限100000（次/天） */
    public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    
    public final static String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    public final static String QRCODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
    
    public static final String REDIS_NAME_SPACE = "promotion_wx";
	
}
