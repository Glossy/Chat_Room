package Chat_Client.UI.DialogUI;

import Chat_Client.DataBase.FigureProperty;
import Chat_Client.DataBase.PrivateDialogDataBase;
import Chat_Client.UI.CustomizedUI.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;

import static Chat_Client.UI.DialogUI.PrivateDialogHandler.DeleteDialog;
import static Chat_Client.UI.GroupDialogUI.GroupDialogHandler.DeleteDialog;

/**
 * Created by Wu on 2017/5/6.
 * 此类为私聊的窗体界面
 */
public class PrivateDialogUI extends JFrame {
    private int x, y;
    private boolean isDraging = false;

    private String nickName;
    private int friendID;
    private JTextArea MsgArea;
    private JTextArea sendArea;

    private JPanel contentPane;

    public int getFriendID() {return friendID;}
    public void setFriendID(int friendID) {this.friendID = friendID;}

    public PrivateDialogUI(String nickName, int friendPic, final int friendID){
        this.nickName = nickName;
        this.friendID = friendID;
        setUndecorated(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isDraging = true;
                x = e.getX();
                y = e.getY();
            }
            @Override
            public void mouseReleased(MouseEvent e){
                isDraging = false;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(isDraging){
                    int curser_x = getLocation().x;
                    int curser_y = getLocation().y;
                    setLocation(e.getX() + curser_x - x, e.getY() + curser_y - y);
                }
            }
        });

        setBounds(100, 100, 653, 494);
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 设置自制按钮
        CloseButton eb = new CloseButton(this);
        eb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                DeleteDialog(friendID);
                dispose();
            }
        });
        int windowWeith = this.getWidth();
        eb.setBounds(windowWeith - 40, 0, 40, 30);
        contentPane.add(eb);
        MinimizeButton mb = new MinimizeButton(this);
        mb.setBounds(windowWeith - 80, 0, 40, 30);
        contentPane.add(mb);

        JLabel NickName = new JLabel("");
        NickName.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 30));
        NickName.setForeground(Color.WHITE);
        NickName.setBounds(31, 93, 200, 44);
        NickName.setText(nickName);
        contentPane.add(NickName);

        MsgArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(MsgArea);
        MsgArea.setEditable(false);
        MsgArea.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
        MsgArea.setBackground(Color.LIGHT_GRAY);
        scrollPane.setBounds(0, 147, 653, 152);
        scrollPane.getVerticalScrollBar().setUI(new CustomizedScrollBarUI());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 不显示水平滚动条；
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        contentPane.add(scrollPane);

        sendArea = new JTextArea();
        JScrollPane sendscrollPane = new JScrollPane(sendArea);
        sendArea.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
        sendArea.setBackground(Color.LIGHT_GRAY);
        sendscrollPane.getVerticalScrollBar().setUI(new CustomizedScrollBarUI());
        sendscrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 不显示水平滚动条；
        sendscrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        sendscrollPane.setBorder(null);
        sendscrollPane.setBounds(0, 325, 653, 96);
        contentPane.add(sendscrollPane);

        UniversalButton Send = new UniversalButton("Send");
        Send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!sendArea.getText().equals("")) {
                    String msg = sendArea.getText();
                    PrivateDialogHandler.SendMessage(friendID, msg);
                    SendMsg(msg);
                    sendArea.setText("");
                }else {
                    new CustomizedJOptionPane("Warning","Sending text can not be null");
                }
            }
        });
        Send.setBounds(77, 440, 165, 40);
        contentPane.add(Send);

        UniversalButton Close = new UniversalButton("Close");
        Close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PrivateDialogDataBase.dialogDB.remove(String.valueOf(friendID));
                dispose();
            }
        });
        Close.setBounds(398, 440, 165, 40);
        contentPane.add(Close);

        JLabel lblUserInfor = new JLabel("User Info");
        lblUserInfor.setForeground(Color.WHITE);
        lblUserInfor.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 60));
        lblUserInfor.setBounds(31, 10, 513, 91);
        contentPane.add(lblUserInfor);

        setVisible(true);
    }
    // 用于显示对方发送来的消息
    public void ShowMsg(String msg) {
        String text = MsgArea.getText();
        text += nickName + " Says:" + msg + "\r\n";
        MsgArea.setText(text);
    }
    //用于显示已经发送的消息
    public void SendMsg(String msg) {
        String text = MsgArea.getText();
        text += FigureProperty.NickName + " Says:" + msg + "\r\n";
        MsgArea.setText(text);
    }
    //抖动效果
    public void Shack() throws IOException{
        int x = this.getX();
        int y = this.getY();
        FileInputStream fileau= new  FileInputStream("audio/message.wav");
        AudioStream as= new AudioStream(fileau);
        AudioPlayer.player.start(as);
        for (int i = 0; i < 10; i++) {
            if ((i & 1) == 0) {
                x += 3;
                y += 3;
            } else {
                x -= 3;
                y -= 3;
            }
            this.setLocation(x, y);
            try {
                Thread.sleep(50);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }
}

