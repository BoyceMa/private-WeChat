/**
 *
 * Copyright (C), 2013-2013, 上海汽车集团股份有限公司
 * FileName : MenuManager.java
 * Author : 何剑
 * Date : 2014年9月22日
 * 
 */
package com.saic.ebiz.market.common.entity.menu.manager;

import com.saic.ebiz.component.wx.entity.Token;
import com.saic.ebiz.component.wx.util.CommonUtil;
import com.saic.ebiz.market.common.constant.Constants;
import com.saic.ebiz.market.common.entity.menu.Button;
import com.saic.ebiz.market.common.entity.menu.ClickButon;
import com.saic.ebiz.market.common.entity.menu.ComplexButton;
import com.saic.ebiz.market.common.entity.menu.LocationButton;
import com.saic.ebiz.market.common.entity.menu.Menu;
import com.saic.ebiz.market.common.entity.menu.PhotoAlbumButton;
import com.saic.ebiz.market.common.entity.menu.ScanCodePushButton;
import com.saic.ebiz.market.common.entity.menu.ViewButton;
import com.saic.ebiz.market.common.util.MenuUtil;

/**
 * @author hejian
 *
 *  @date 2014年9月22日
 */
public class MenuManager {
    public static Menu getMenu(){
        ClickButon musicButton = new ClickButon();
        musicButton.setName("今日歌曲");
        musicButton.setKey("V1001_TODAY_MUSIC");
        
        ViewButton searchButton = new ViewButton();
        searchButton.setName("搜索");
        searchButton.setUrl("https://www.baidu.com");
        
        LocationButton locationButton = new LocationButton();
        locationButton.setName("地理位置");
        locationButton.setKey("rselfmenu");
        
        ComplexButton menu1 = new ComplexButton();
        menu1.setName("菜单1");
        menu1.setSub_button(new Button[]{musicButton, searchButton, locationButton});
        
        ////////////////////////////////////////////
        
        ScanCodePushButton scanButton = new ScanCodePushButton();
        scanButton.setName("扫码");
        scanButton.setKey("scan_btn");
        
        PhotoAlbumButton photoAlbumButton = new PhotoAlbumButton();
        photoAlbumButton.setName("照片");
        photoAlbumButton.setKey("picture");
        
        ComplexButton menu2 = new ComplexButton();
        menu2.setName("精彩活动");
        menu2.setSub_button(new Button[]{scanButton, photoAlbumButton});
        
        Menu menu = new Menu();
        menu.setButton(new Button[]{menu1,menu2});

        return menu;
//    	return SaicMenuManager.getMenuNew("pre");
    }
    
    public static void main(String[] args) {
        //到redistribute中查对应的值, key:
        //key: promotion_wx.AccessToken_{appId}
//        String token = "";
        Token token = CommonUtil.getAccessToken(Constants.APP_ID, Constants.APP_SECRET);
        if(token != null){
            int result = MenuUtil.createMenu(getMenu(), token.getToken());
            if(result == 0){
                System.out.println("创建菜单成功");
            }else{
                System.out.println("创建菜单失败");
            }
        }
    }
}
