package Chat_Client.UI.CustomizedUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * Created by Wu on 2017/5/6.
 * 重写ScrollBarUI
 */
public class CustomizedScrollBarUI extends BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
        // 滑道背景色
        trackColor = Color.darkGray;
    }
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.translate(thumbBounds.x, thumbBounds.y);//Translates the origin of the graphics context to the point (x, y) in the current coordinate system
        g.setColor(Color.GRAY);// 设置边框颜色
        g.drawRoundRect(5, 0, 6, thumbBounds.height-1, 5, 5); // 画一个圆角矩形
        // 消除锯齿
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//着色微调
        g2.addRenderingHints(rh);
        // 半透明
        //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        // 设置填充颜色
        g2.setPaint(new GradientPaint(c.getWidth() / 2, 1, Color.GRAY, c.getWidth() / 2, c.getHeight(), Color.GRAY));
        // 填充圆角矩形
        g2.fillRoundRect(5, 0, 6, thumbBounds.height-1, 5, 5);
    }
    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        return button;
    }
    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        return button;
    }
}
