package Chat_Client.UI.CustomizedUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Wu on 2017/5/3.
 */
public class UniversalButton extends JButton{
    public UniversalButton(String s){
        setBounds(37, 285, 165, 40);
        setBackground(new Color(0x696969));
        setForeground(Color.WHITE);
        setBorder(null);
        setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        setPreferredSize(new Dimension(170, 40));
        setText(s);
        setFocusPainted(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(0x878787));
            }
            @Override
            public void mouseExited(MouseEvent e1){
                setBackground(new Color(0x696969));
            }
        });
    }
}
