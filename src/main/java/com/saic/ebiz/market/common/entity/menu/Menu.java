/**
 *
 * Copyright (C), 2013-2013, 上海汽车集团股份有限公司
 * FileName : Menu.java
 * Author : 何剑
 * Date : 2014年9月21日
 * 
 */
package com.saic.ebiz.market.common.entity.menu;

import java.util.Arrays;

/**
 * 菜单
 * 
 * @author hejian
 *
 *  @date 2014年9月21日
 */
public class Menu {
    /** 一级菜单数组，个数应为1~3个 */
    private Button[] button;

    /**
     *
     *	Get the button
     *
     * @return the button
     */
    public Button[] getButton() {
        return button;
    }

    /**
     *
     *	Set the button
     *
     * @param button the button to set
     */
    public void setButton(Button[] button) {
    	if(button != null){
    		this.button = Arrays.copyOf(button, button.length);
    	}
    }
}
