
package com.saic.ebiz.market.common.entity.menu.manager;

import com.saic.ebiz.market.common.entity.menu.Button;
import com.saic.ebiz.market.common.entity.menu.Menu;
import com.saic.ebiz.market.common.entity.menu.ViewButton;
import com.saic.ebiz.market.common.util.MenuUtil;

/**
 * @author hejian
 * 
 * 车享新车服务号菜单
 * @date 2014年9月22日
 */
public class SaicMenuManager {
    
    public static Menu getMenuNew(String env){
    	//菜单1
        ViewButton btn1 = new ViewButton();
        btn1.setName("74 折购车");
        btn1.setUrl("http://c."+env+".com/promotion/47.htm?channel=zc_weixintwxinche_xtj45_20170214_h5");
        
        //菜单2
        ViewButton btn2 = new ViewButton();
        btn2.setName("品牌旗舰店");
        btn2.setUrl("http://c."+env+".com/promotion/brands/home.htm");
        
        //菜单3
        ViewButton btn3 = new ViewButton();
        btn3.setName("我的");
        btn3.setUrl("http://member."+env+".com/m/index.htm");
        
        Menu menu = new Menu();
        menu.setButton(new Button[]{btn1,btn2,btn3});
        
        return menu;
    }

    public static void main(String[] args) {
        
        String env = "chexiangsit";
        
        String token = "VnrYi7tb8Ih_476FeJehLcBFoA5mRwaaxfned20Hu6Rg4x3KkNn-hC5YEbGdPNUARgOZx2SoJmREExMuqQ_htcvnvG3Kcj2IRXTuMgYIPs008qSqTySS_pXSj45kYRwENYUiAIAYJF";
        if (token != null) {  
            int result = MenuUtil.createMenu(getMenuNew(env), token);
            if (result == 0) {
                System.out.println("创建菜单成功");
            } else {
                System.out.println("创建菜单失败");
            }
        }
    }
}
