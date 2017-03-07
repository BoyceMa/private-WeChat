/**
 *
 * Copyright (C), 2013-2013, 上海汽车集团股份有限公司
 * FileName : CommonUtil.java
 * Author : 何剑
 * Date : 2014年9月22日
 * 
 */
package com.saic.ebiz.component.wx.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saic.ebiz.component.wx.NumberConstants;
import com.saic.ebiz.component.wx.SaicX509TrustManager;
import com.saic.ebiz.component.wx.entity.JsapiTicket;
import com.saic.ebiz.component.wx.entity.Token;


/**
 *  @author hejian
 *
 *  @date 2014年9月22日
 */
public class CommonUtil {
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
    
    /**
     * 发起https请求并获取结果
     * 
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     * @param outputStr
     *            提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl,String requestMethod,String outputStr){
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new SaicX509TrustManager() };
//          SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            SSLContext sslContext = SSLContext.getInstance("TLS", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            logger.error("connection timed out cause by " + ce.getMessage());
        } catch (Exception e) {
            logger.error("https request error : " + e.getMessage());
        }
        return jsonObject;
    }
    
    /**
     * 获取access_token
     * 
     * @return
     */
    public static Token getAccessToken(String appid, String appsecret) {
        Token accessToken = null;

        String requestUrl = NumberConstants.TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
        logger.info("获取access_token的请求url:{}", requestUrl);
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new Token();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        
        //没有获取到token,重试一次
        if(accessToken == null) {
            try {
                Thread.sleep(NumberConstants.THREAD_SLEEP_5_SECONDS);
            
            }catch(InterruptedException ie) {
                logger.error("重试获取token sleep error", ie.fillInStackTrace());
            }
            
            jsonObject = httpsRequest(requestUrl, "GET", null);
            
            // 如果请求成功
            if (null != jsonObject) {
                try {
                    accessToken = new Token();
                    accessToken.setToken(jsonObject.getString("access_token"));
                    accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
                } catch (JSONException e) {
                    accessToken = null;
                    // 获取token失败
                    logger.error("重试获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                }
            }
        }
        
        return accessToken;
    }
    
    public static JsapiTicket getJsapiTicket(String accessToken){
    	Token token = new Token();
    	token.setToken(accessToken);
    	return getJsapiTicket(token);
    }
    
    
    public static JsapiTicket getJsapiTicket(Token accessToken){
        logger.info("Token : {}", accessToken);
        
        if(accessToken == null || accessToken.getToken() == null) {
            return null;
        }
        
    	JsapiTicket jsapiTicket = null;

		String requestUrl = NumberConstants.TICKET_URL.replace("ACCESS_TOKEN", accessToken.getToken());
		logger.info("获取ticket的请求url:{}", requestUrl);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
        if (null != jsonObject) {
            try {
            	jsapiTicket = new JsapiTicket();
            	jsapiTicket.setErrcode(jsonObject.getString("errcode"));
            	jsapiTicket.setErrmsg(jsonObject.getString("errmsg"));
            	jsapiTicket.setTicket(jsonObject.getString("ticket"));
            	jsapiTicket.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
            	jsapiTicket = null;
                // 获取token失败
                logger.error("获取ticket失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
    	
    	
    	//没有获取到jsapiTicket,重试一次
    	if(jsapiTicket == null) {
    	    try {
                Thread.sleep(NumberConstants.THREAD_SLEEP_5_SECONDS);
            
            }catch(InterruptedException ie) {
                logger.error("重试获取jsapiTicket sleep error", ie.fillInStackTrace());
            }

            jsonObject = httpsRequest(requestUrl, "GET", null);
            // 如果请求成功
            if (null != jsonObject) {
                try {
                    jsapiTicket = new JsapiTicket();
                    jsapiTicket.setErrcode(jsonObject.getString("errcode"));
                    jsapiTicket.setErrmsg(jsonObject.getString("errmsg"));
                    jsapiTicket.setTicket(jsonObject.getString("ticket"));
                    jsapiTicket.setExpiresIn(jsonObject.getInt("expires_in"));
                } catch (JSONException e) {
                    jsapiTicket = null;
                    // 获取token失败
                    logger.error("重试获取ticket失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                }
            }
    	}
    	
    	return jsapiTicket;
    }
    
    /**
     * URL编码 utf-8
     * @param url 原始url
     * @return	编码后的url
     */
    public static String encodeURL(String url) {
    	try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return url;
    }
    
}
