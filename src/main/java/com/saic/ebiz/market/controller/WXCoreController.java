/******
 * 
 * 
 * Copyright (C), 2014-1-20, 上汽电商有限公司<br/>
 * 
 * 微信用户控制类
 * 
 * @author 何剑
 * @version 1.0.0
 * @date 2014年9月17日
 */
package com.saic.ebiz.market.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.saic.ebiz.market.common.constant.Constants;
import com.saic.ebiz.market.common.util.MessageUtil;
import com.saic.ebiz.market.common.util.SignUtil;
import com.saic.ebiz.market.service.CoreService;


@Controller
@RequestMapping("/wxcore")
public class WXCoreController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CoreService coreService;
	
//	@Resource
//	private UserInfoService userInfoService;

	/*****
	 * 确认请求来自微信服务器
	 * 
	 * @param signature
	 *            微信加密签名
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机数
	 * @param echostr
	 *            随机字符串
	 * @return
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/server/0", method = RequestMethod.GET)
	public void doGetMethod(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 微信加密签名
		String signature = request.getParameter(Constants.SIGNATURE);
		// 时间戳
		String timestamp = request.getParameter(Constants.TIMESTAMP);
		// 随机数
		String nonce = request.getParameter(Constants.NONCE);
		// 随机字符串
		String echostr = request.getParameter(Constants.ECHOSTR);
		
		this.logger.info("signature : {} - timestamp : {} - nonce : {} - echostr : {}", new Object[]{signature,timestamp,nonce,echostr});
		PrintWriter out = response.getWriter();
		if (signature != null && timestamp != null && nonce != null) {
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				out.print(echostr);
				out.flush();
			}
		}
		out.close();
	}

	/**
	 * 处理微信服务器发来的消息
	 */
	@ResponseBody
	@RequestMapping(value = "/server/0", method = RequestMethod.POST)
	public void doPostMethod(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");// 设置浏览器以UTF-8打开
		// 调用核心业务类接收消息、处理消息
		String respMessage = "response Message";
		// 响应消息
		PrintWriter out = null;
		try {
            Map<String,String> map = MessageUtil.parseXml(request);
            logger.info("-=-=-=-=-=-=-=-=- map \n {} \n -=-=-=-=-=-=-=-=-",JSONObject.toJSONString(map));
            out = response.getWriter();
            respMessage = coreService.processRequest(request,map);
            out.print(respMessage);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(out != null){
                out.close();
            }
        }
		
		logger.info("-=-=-=-=-=-=-=-=- response Message: \n {} \n -=-=-=-=-=-=-=-=-",respMessage);
	}

}
