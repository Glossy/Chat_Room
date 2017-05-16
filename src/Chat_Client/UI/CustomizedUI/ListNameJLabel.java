package Chat_Client.UI.CustomizedUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Wu on 2017/5/6.
 * 将分组包装成JLable
 */
public class ListNameJLabel extends JLabel{
    private JLabel lb_Name;
    private boolean is_click = false;

    public ListNameJLabel(String name, JLabel[] friends, int type) {

        setIcon(new ImageIcon("img/ListImg/ListOff.jpg"));
        setSize(new Dimension(272, 50));

        setBackground(Color.green);
        lb_Name = new JLabel();
        lb_Name.setForeground(Color.WHITE);
        lb_Name.setBounds(new Rectangle(20, 10, 95, 20));
        lb_Name.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 18));
        lb_Name.setText(name);

        if (type == 0) {//若为好友列表
            for (int i = 0; i < friends.length; i++) {
                friends[i].setVisible(false);
            }
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    if (is_click) {
                        setIcon(new ImageIcon("img/ListImg/ListOn.jpg"));
                    } else {
                        setIcon(new ImageIcon("img/ListImg/ListOff.jpg"));
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (is_click) {
                        setIcon(new ImageIcon("img/ListImg/ListOnEnter.jpg"));
                    } else {
                        setIcon(new ImageIcon("img/ListImg/ListOffEnter.jpg"));
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    is_click = !is_click;
                    if (is_click) {
                        for (int i = 0; i < friends.length; i++) {
                            friends[i].setVisible(true);//显示分组好友
                        }
                        setIcon(new ImageIcon("img/ListImg/ListOnEnter.jpg"));
                    } else {
                        for (int i = 0; i < friends.length; i++) {
                            friends[i].setVisible(false);
                        }
                        setIcon(new ImageIcon("img/ListImg/ListOffEnter.jpg"));
                    }
                }
            });
        }else{//若是群聊列表
            for(;;){

            }
        }
    }
}
