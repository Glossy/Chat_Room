package Chat_Client.UI.CustomizedUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Wu on 2017/5/2.
 */
public class ExitButton extends JButton {
    public ExitButton(){
        setUI(new CustomizedButtonUI());    //设置自定义UI
        setPreferredSize(new Dimension(40,30));//封装了构建高度和宽度的类
        setBackground(Color.DARK_GRAY);
        this.setText("×");
        setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
        setFocusPainted(false);         // 设置不要焦点（文字的边框）
        setForeground(Color.WHITE);     //文字前色
        setBorder(null);
        UIManager.put("Button select",Color.RED);

        addMouseListener(new MouseAdapter() {
/**重写mousePressed并不能改变按钮按压下的颜色*/
//            @Override
//            public  void mousePressed(MouseEvent e){
//                setBackground(new Color(240, 128, 128));   //浅红
//            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(176, 23, 31)); //暗红
                isExit = false;
            }
            @Override
            public void mouseExited(MouseEvent e){
                isExit = true;
                setBackground(Color.DARK_GRAY);
            }
            @Override
            public void mouseReleased(MouseEvent e){
                if(!isExit){
                    System.exit(0);
                }
            }
        });
    }
    private boolean isExit = true;
}
