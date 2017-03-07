/*
 * Copyright (C), 2013-2014, 上海汽车集团股份有限公司
 */
package com.saic.ebiz.market.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 获取客户端真实ip<br>
 * .
 * 
 * @author chenliang
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class AccessClientIpUtil {
    
    /**
     * 功能描述: 解析客户端ip<br>
     * .
     * 
     * @param request the request
     * @return ip
     */
    private static String parseIpAddr(HttpServletRequest request) {
        String ipAddress = StringUtils.EMPTY;

        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
    
    /**
     * 功能描述: 获取客户端ip<br>
     * .
     * 
     * @param request the request
     * @return ip或null
     */
    public static String getClientIp(HttpServletRequest request) {
        String ipAddress = parseIpAddr(request);
        // ***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(",")).trim();
            }
        }
        return ipAddress;
    }
}
