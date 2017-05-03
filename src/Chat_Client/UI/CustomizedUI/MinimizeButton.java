package Chat_Client.UI.CustomizedUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Wu on 2017/5/2.
 */
public class MinimizeButton extends JButton {
    public MinimizeButton(final JFrame jFrame){
        setPreferredSize(new Dimension(40, 30));
        setBackground(Color.DARK_GRAY);
        setText("-");
        setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
        setFocusPainted(false);// 设置不要焦点（文字的边框）
        setBorder(null);
        setForeground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e){
                isEnter = true;
                setBackground(new Color(192, 192, 192));//灰白
            }
            @Override
            public void mouseClicked(MouseEvent e){
                if (isEnter) {
                    jFrame.setExtendedState(JFrame.ICONIFIED);//设置窗体状态
                }
            }
            @Override
            public void mouseExited(MouseEvent e){
                setBackground(Color.DARK_GRAY);
                isEnter = false;
            }
        });

    }
    private boolean isEnter = false;
}
