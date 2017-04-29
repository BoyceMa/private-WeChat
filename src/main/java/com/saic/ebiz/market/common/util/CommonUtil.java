/**
 *
 * Copyright (C), 2013-2013, 上海汽车集团股份有限公司
 * FileName : CommonUtil.java
 * Author : 何剑
 * Date : 2014年9月22日
 * 
 */
package com.saic.ebiz.market.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saic.ebiz.market.common.entity.authentication.SaicX509TrustManager;
import com.saic.ebiz.market.common.entity.authentication.Token;
import com.saic.ebiz.market.common.entity.qrcode.Qrcode;
import com.saic.ebiz.market.common.entity.response.SendTextMessage;
import com.saic.ebiz.market.common.entity.response.SendTextMessage.TextContent;
import com.saic.ebiz.market.common.enumeration.MessageType;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;


/**
 *  @author hejian
 *
 *  @date 2014年9月22日
 */
public class CommonUtil {
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
    
    /** 获取access_token的接口地址URL（GET） 限200（次/天） */
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    
    public final static String ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    public final static String QRCODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
    
    private static String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=AT";
    
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
     * @deprecated
     */
    public static Token getAccessToken(String appid, String appsecret) {
        Token accessToken = null;

        String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
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
        return accessToken;
    }

    public static JSONObject generatePermanentQrcode(String appid,String appsecret,Qrcode qrcode,String token){
    	String requestUrl = QRCODE_URL + token;
    	return httpsRequest(requestUrl,"POST",com.alibaba.fastjson.JSONObject.toJSONString(qrcode));
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
    /**
     * URL解码 utf-8
     * @param url 原始url
     * @return	编码后的url
     */
    public static String decodeURL(String url) {
    	try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return url;
    }
    
    public static void sendMessage(String accessToken,String openid, String text){
		requestUrl = requestUrl.replace("AT", accessToken);
		String jsonMessage = com.alibaba.fastjson.JSONObject.toJSONString(buildObject(openid, text));
		CommonUtil.httpsRequest(requestUrl, "POST", jsonMessage);
	}
    
    private static Object buildObject(String openid,String text){
		SendTextMessage message = new SendTextMessage();
		message.setTouser(openid);
		message.setMsgtype(MessageType.TEXT.code());
		TextContent tc = new SendTextMessage.TextContent();
		tc.setContent(text);
		message.setText(tc);
		return message;
	}
    
//    public static String getAccessToken(String urlString){
//		StringBuilder builder = new StringBuilder();
//		try {
//			URL url = new URL(urlString);
//			URLConnection openConnection = url.openConnection();
//			openConnection.setDoOutput(true);
//			InputStream inputStream = openConnection.getInputStream();
//			BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(inputStream));
//			String data = null;
//			while((data = bufferedInputStream.readLine()) != null){
//				builder.append(data);
//			}
////			builder.setLength(builder.length() - 1);
//			inputStream.close();
//			bufferedInputStream.close();
//		} catch (MalformedURLException e) {
//			logger.error("解析URL失败",e);
//		} catch (IOException e) {
//			logger.error("IOException", e);
//		}
//		return builder.toString();
//	}
    
    public static void main(String[] args) {
//    	Token token = getAccessToken(WXconstant.APPID, WXconstant.APPSECRET);
//    	System.out.println(com.meidusa.fastjson.JSONObject.toJSONString(getJsapiTicket(token)));
//    	JSONObject json = generatePermanentQrcode(WXconstant.APPID, WXconstant.APPSECRET, QrcodeFactory.createPermanentQrcode("1","int"));
//    	System.out.println(com.meidusa.fastjson.JSONObject.toJSONString(json));
//    	System.out.println(json.getString("url"));
//    	System.out.println(json.getString("ticket"));
//    	//获取带参数的二维码需要encode ticket
//    	System.out.println(encodeURL(json.getString("ticket")));
	}
}
