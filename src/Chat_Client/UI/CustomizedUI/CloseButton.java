package Chat_Client.UI.CustomizedUI;

import Chat_Client.DataBase.PrivateDialogDataBase;
import Chat_Client.UI.DialogUI.PrivateDialogUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Wu on 2017/5/3.
 */
public class CloseButton extends JButton {
    public CloseButton(){
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

    public CloseButton(final PrivateDialogUI dialog) {
        setUI(new CustomizedButtonUI());
        setPreferredSize(new Dimension(40, 30));
        setBackground(Color.DARK_GRAY);
        setText("×");
        setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
        setFocusPainted(false);// 设置不要焦点（文字的边框）
        setBorder(null);
        setForeground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!isExit){
                    PrivateDialogDataBase.dialogDB.remove(String.valueOf(dialog.getFriendID()));
                    dialog.dispose();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.DARK_GRAY);
                isExit = true;
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(176, 23, 31));
                isExit = false;
            }
        });
    }
    private boolean isExit = true;
}
