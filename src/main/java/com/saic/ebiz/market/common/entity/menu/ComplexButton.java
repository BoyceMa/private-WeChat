/**
 *
 * Copyright (C), 2013-2013, 上海汽车集团股份有限公司
 * FileName : ComplexButton.java
 * Author : 何剑
 * Date : 2014年9月21日
 * 
 */
package com.saic.ebiz.market.common.entity.menu;

import java.util.Arrays;

/**
 * 复合类型的按钮
 * 
 * @author hejian
 *
 *  @date 2014年9月21日
 */
public class ComplexButton extends Button {
    /** 二级菜单数组，个数应为1~5个 */
    private Button[] sub_button;

    /**
     *
     *	Get the sub_button
     *
     * @return the sub_button
     */
    public Button[] getSub_button() {
        return sub_button;
    }

    /**
     *
     *	Set the sub_button
     *
     * @param sub_button the sub_button to set
     */
    public void setSub_button(Button[] subButton) {
    	if(subButton != null){
    		this.sub_button = Arrays.copyOf(subButton, subButton.length);
    	}
    }
}
