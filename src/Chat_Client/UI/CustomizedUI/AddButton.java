package Chat_Client.UI.CustomizedUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Wu on 2017/5/7.
 */
public class AddButton extends JButton {
    public boolean is_exit = true;

    public AddButton() {
        setPreferredSize(new Dimension(40, 40));
        setBackground(Color.DARK_GRAY);
        setText("+");
        setFont(new Font("Microsoft JhengHei", Font.PLAIN, 40));
        setFocusPainted(false);// 设置不要焦点（文字的边框）
        setBorder(null);
        setForeground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(192, 192, 192));
                is_exit = false;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.darkGray);
                is_exit = true;
            }
        });
    }


}
