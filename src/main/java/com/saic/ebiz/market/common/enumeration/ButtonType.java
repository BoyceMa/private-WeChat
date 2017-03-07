/**
 *
 * Copyright (C), 2013-2013, 上海汽车集团股份有限公司
 * FileName : MessageType.java
 * Author : 何剑
 * Date : 2014年9月19日
 * 
 */
package com.saic.ebiz.market.common.enumeration;

/**
 * @author hejian
 * 
 */
public enum ButtonType {
    CLICK("click"), VIEW("view"), LOCATION("location_select"),SCAN_CODE("scancode_push"), PHOTO_ALBUM("pic_photo_or_album");

    private String code;

    private ButtonType(String code) {
        this.code = code;
    }

    /**
     * 
     * Get the code
     * 
     * @return the code
     */
    public String code() {
        return code;
    }

    /**
     * 
     * Set the code
     * 
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return this.code;
    }

}
