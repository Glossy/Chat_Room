package Chat_Client.UI.CustomizedUI;

/**
 * Created by Wu on 2017/5/6.
 * 自定义一个重写过ButtonPress状态的UI类
 * 为了按钮在按压时能显示非默认的浅红色
 */

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

class CustomizedButtonUI extends BasicButtonUI {
    @Override
    public void paintButtonPressed(Graphics g, AbstractButton b){
        g.setColor(new Color(240, 128, 128));//浅红
        g.fillRect(0, 0, b.getWidth(), b.getHeight());//用制定画刷填充矩阵
    }

}