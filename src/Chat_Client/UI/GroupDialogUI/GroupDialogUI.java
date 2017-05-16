package Chat_Client.UI.GroupDialogUI;

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

/**
 * 群聊的窗体界面
 * Created by Wu on 2017/5/16.
 */
public class GroupDialogUI extends JFrame{
    private int x, y;
    private boolean isDraging = false;

    private String groupName;
    private int groupID;
    private JTextArea MsgArea;
    private JTextArea sendArea;

    private JPanel contentPane;
    private JPanel listPane;
    private JPanel panel;//用于显示整体的panel

    public int getGroupID() {return groupID;}
    public void setGroupID(int groupID) {this.groupID = groupID;}

    public GroupDialogUI(String groupName, int groupPic, final int groupID){
        this.groupName = groupName;
        this.groupID = groupID;
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

        setBounds(100, 100, 902, 642);

        panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setLayout(null);
        panel.setBounds(0,0,902,642);

        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        contentPane.setBounds(0,0,653,642);

        listPane = new JPanel();
        listPane.setBackground(Color.DARK_GRAY);
        listPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        listPane.setLayout(null);
        listPane.setBounds(653,0,249,642);

        // 设置自制按钮
        CloseButton eb = new CloseButton(this);
        eb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                GroupDialogHandler.DeleteDialog(groupID);
                dispose();
            }
        });
        int windowWeith = this.getWidth();
        eb.setBounds(windowWeith - 40, 0, 40, 30);
        listPane.add(eb);
        MinimizeButton mb = new MinimizeButton(this);
        mb.setBounds(windowWeith - 80, 0, 40, 30);
        listPane.add(mb);

        JLabel GroupName = new JLabel();
        GroupName.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 30));
        GroupName.setForeground(Color.WHITE);
        GroupName.setBounds(31, 93, 200, 44);
        GroupName.setText("GroupID : " + groupID);
        contentPane.add(GroupName);

        MsgArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(MsgArea);
        MsgArea.setEditable(false);
        MsgArea.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
        MsgArea.setBackground(Color.LIGHT_GRAY);
        scrollPane.setBounds(0, 147, 653, 300);
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
                    GroupDialogHandler.SendMessage(groupID, msg);
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
                PrivateDialogDataBase.dialogDB.remove(String.valueOf(groupID));
                dispose();
            }
        });
        Close.setBounds(398, 440, 165, 40);
        contentPane.add(Close);

        JLabel lbGroupName = new JLabel(groupName);
        lbGroupName.setForeground(Color.WHITE);
        lbGroupName.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 60));
        lbGroupName.setBounds(31, 10, 513, 91);
        contentPane.add(lbGroupName);

        JLabel groupInfo = new JLabel();
        groupInfo.setForeground(Color.WHITE);
        groupInfo.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 35));
        groupInfo.setBounds(653 + 25, 10, 170, 91);
        listPane.add(lbGroupName);

        panel.add(listPane);
        panel.add(contentPane);

        setContentPane(panel);
        setVisible(true);
    }
    // 用于显示对方发送来的消息
    public void ShowMsg(String nickName, String msg) {
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
    public void Shack() throws IOException {
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
