package Chat_Client.UI.CustomizedUI;

import Chat_Client.DataBase.FriendListInfo;
import Chat_Client.DataBase.PrivateDialogDataBase;
import Chat_Client.UI.CustomizedUI.CustomizedJOptionPane;
import Chat_Client.UI.DialogUI.PrivateDialogHandler;
import Chat_Client.UI.DialogUI.PrivateDialogUI;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Wu on 2017/5/6.
 * 这里把列表中的每一个用户包装成一个JLabel
 * 方便放在JScollPane里面
 */
public class FriendMemberJLabel extends JLabel{

    public JPanel jPanel = new JPanel(); // 模板容器；
    private JLabel lb_nickName = null; // 显示昵称；
    private JLabel lb_IDnum = null; // 显示ID号
    private JLabel lb_State;//显示状态
    private boolean is_exit = true;
    private int MemberIDNum;
    private int pic;
    private String nickname;

    public int getMemberIDNum() {
        return MemberIDNum;
    }
    public void setMemberIDNum(int memberIDNum) {
        MemberIDNum = memberIDNum;
    }
    public int getPic() {
        return pic;
    }
    public void setPic(int pic) {
        this.pic = pic;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public FriendMemberJLabel(int picNum, String nickname, int IDNum, byte state){
        MemberIDNum = IDNum;
        pic = picNum;
        this.nickname = nickname;
        setBackground(Color.darkGray);
        setLayout(null);

        //用户名
        lb_nickName = new JLabel();
        lb_nickName.setForeground(Color.white);
        lb_nickName.setBounds(new Rectangle(70, 10, 95, 20));
        lb_nickName.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 18));
        lb_nickName.setText(nickname);
        add(lb_nickName);

        //账号
        lb_IDnum = new JLabel();
        lb_IDnum.setForeground(Color.WHITE);
        lb_IDnum.setBounds(new Rectangle(70, 38, 150, 20));
        lb_IDnum.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 15));
        lb_IDnum.setText("JKNum:(" + IDNum + ")");
        add(lb_IDnum);

        //状态
        String SState;
        if (state == 0)
            SState = "OnLine";
        else
            SState = "OffLine";
        lb_State = new JLabel();
        lb_State.setText(SState);
        lb_State.setForeground(Color.WHITE);
        lb_State.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 18));
        lb_State.setBounds(new Rectangle(70, 10, 95, 20));
        lb_State.setBounds(180, 10, 95, 20);
        add(lb_State);

        //头像
        JButton UserIcon = new JButton();
        UserIcon.setBorder(null);
        UserIcon.setBounds(10, 10, 50, 50);
        UserIcon.setIcon(new ImageIcon("img/AvatarImg/" + pic + ".jpg"));
        add(UserIcon);

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
                setIcon(new ImageIcon("img/ListImg/memberBGOff.jpg"));
                is_exit = true;
            }
            @Override
            public void mouseClicked(MouseEvent e) {//点击则弹出对话框
                PrivateDialogUI dialog;
                if(PrivateDialogDataBase.dialogDB.containsKey(String.valueOf(IDNum))){
                    dialog = PrivateDialogDataBase.dialogDB.get(String.valueOf(IDNum));
                    dialog.toFront();   //存在窗口则使聊天窗口置顶
                }
                else{//不存在则弹出新窗口并注册
                    dialog = new PrivateDialogUI(nickname,picNum,IDNum);
                    PrivateDialogHandler.RegisterDialog(IDNum, dialog);
                }
            }
        });
    }

    //收到好友信息
    public void receiverMsg(){
        //System.out.println("Have_A_MSG");
        setIcon(new ImageIcon("img/ListImg/memberBGMsg.jpg"));
        try {
            FileInputStream fileau = new  FileInputStream("audio/message.wav");
            AudioStream as= new AudioStream(fileau);
            AudioPlayer.player.start(as);
        } catch (IOException e) {
            e.printStackTrace();
            new CustomizedJOptionPane("Error","Audio play error");
        }
    }

    public void setState(byte state){
        String SState;
        if (state == 0)
            SState = "OnLine";
        else
            SState = "OffLine";
        lb_State.setText(SState);
    }
}
