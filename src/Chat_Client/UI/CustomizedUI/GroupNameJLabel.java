package Chat_Client.UI.CustomizedUI;

import Chat_Client.DataBase.GroupDialogDataBase;
import Chat_Client.DataBase.GroupListInfo;
import Chat_Client.DataBase.PrivateDialogDataBase;
import Chat_Client.UI.DialogUI.PrivateDialogHandler;
import Chat_Client.UI.DialogUI.PrivateDialogUI;
import Chat_Client.UI.GroupDialogUI.GroupDialogHandler;
import Chat_Client.UI.GroupDialogUI.GroupDialogUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Wu on 2017/5/17.
 * 用于在主面板中将群聊标签化
 */
public class GroupNameJLabel extends JLabel {
    public JPanel jPanel = new JPanel(); // 模板容器
    private JLabel lb_groupName = null; // 显示昵称
    private JLabel lb_IDnum = null; // 显示ID号
    private JLabel lb_grouperNum;//显示人数
    private boolean is_exit;
    public GroupNameJLabel(String GroupName, int GroupID, int GrouperNum){
        setBackground(Color.darkGray);
        setLayout(null);

        lb_groupName = new JLabel();
        lb_groupName.setForeground(Color.white);
        lb_groupName.setBounds(new Rectangle(10, 10, 95, 20));
        lb_groupName.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 20));
        lb_groupName.setText(GroupName);
        add(lb_groupName);

        lb_IDnum = new JLabel();
        lb_IDnum.setForeground(Color.WHITE);
        lb_IDnum.setBounds(new Rectangle(10, 38, 150, 20));
        lb_IDnum.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 18));
        lb_IDnum.setText("GroupID:(" + GroupID + ")");
        add(lb_IDnum);

        lb_grouperNum = new JLabel();
        lb_grouperNum.setText("Member Num: " + String.valueOf(GrouperNum));
        lb_grouperNum.setForeground(Color.WHITE);
        lb_grouperNum.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 17));
        lb_grouperNum.setBounds(new Rectangle(55, 10, 95, 20));
        lb_grouperNum.setBounds(130, 10, 150, 20);
        add(lb_grouperNum);

        setIcon(new ImageIcon("img/ListImg/memberBGOff.jpg"));//默认显示未选中状态下的背景
        setSize(new Dimension(272, 70));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(is_exit){
                    setIcon(new ImageIcon("img/ListImg/memberBGOff.jpg"));
                }
                else{
                    setIcon(new ImageIcon("img/ListImg/memberBGOn.jpg"));
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                is_exit = false;
                setIcon(new ImageIcon("img/ListImg/memberBGOn.jpg"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(new ImageIcon("img/ListImg/memberBGOff.jpg"));    is_exit = true;
            }
            @Override
            public void mouseClicked(MouseEvent e) {//点击则弹出对话框
                GroupDialogUI dialog;
                if(GroupDialogDataBase.dialogDB.containsKey(String.valueOf(GroupID))){
                    dialog = GroupDialogDataBase.dialogDB.get(String.valueOf(GroupID));
                    dialog.toFront();   //存在窗口则使聊天窗口置顶
                }
                else{//不存在则弹出新窗口并注册
                    dialog = new GroupDialogUI(GroupName,GroupID);
                    GroupDialogHandler.RegisterDialog(GroupID, dialog);
                }
            }
        });
    }
}
