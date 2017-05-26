package Chat_Client.UI.MainInterfaceUI;

import Chat_Client.DataBase.FigureProperty;
import Chat_Client.DataBase.FriendListInfo;
import Chat_Client.DataBase.GroupListInfo;
import Chat_Client.UI.AddFriendUI.AddFriendUI;
import Chat_Client.UI.CustomizedUI.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Created by Wu on 2017/5/6.
 * 此类为登陆后的主界面
 */
public class MainInterfaceUI extends JFrame{
    private boolean isAdding = false;
    private ListPane list;//好友列表
    private ListPane groupList; //群聊列表
    private int xx, yy;
    private boolean isDraging = false;
    private MainInterfaceUI mainInterfaceUI;
    private JScrollPane scrollPane;
    private JPanel panel;
    private FriendListInfo user;
    private JPanel contentPane;
    private GroupListInfo groupListInfo;

    public MainInterfaceUI(){
        mainInterfaceUI = this;
        FigureProperty.mainInterfaceUI = this;
        setBackground(Color.DARK_GRAY);
        try{
            user = FigureProperty.cc.getlist();
            groupListInfo = FigureProperty.cc.getGroupList();
        }catch (IOException e){
            e.printStackTrace();
        }

        FigureProperty.IDNum = user.getIDNum();
        FigureProperty.NickName = user.getNickName();

        setUndecorated(true);
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                isDraging = true;
                xx = e.getX();
                yy = e.getY();
            }

            public void mouseReleased(MouseEvent e) {
                isDraging = false;
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (isDraging) {
                    int left = getLocation().x;
                    int top = getLocation().y;
                    setLocation(left + e.getX() - xx, top + e.getY() - yy);
                }
            }
        });

        setBounds(100, 100, 300, 700);
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        setResizable(false);
        contentPane.setLayout(null);

        // 设置自制按钮
        ExitButton eb = new ExitButton();
        int windowWeith = this.getWidth();
        eb.setBounds(windowWeith  - 40, 0, 40, 30);
        contentPane.add(eb);
        MinimizeButton mb = new MinimizeButton(this);
        mb.setBounds(windowWeith - 80, 0, 40, 30);
        contentPane.add(mb);

        JPanel OwnInfo = new JPanel();//存储个人信息的JPanel
        OwnInfo.setBounds(15, 15, 272, 161);
        OwnInfo.setBackground(Color.DARK_GRAY);
        OwnInfo.setPreferredSize(new Dimension(200, 150));
        contentPane.add(OwnInfo);
        OwnInfo.setLayout(null);

        JLabel lblWelcome = new JLabel("Welcome");
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 50));
        lblWelcome.setBounds(1, 29, 226, 59);
        OwnInfo.add(lblWelcome);

        JLabel UserInfo = new JLabel(user.getNickName());
        UserInfo.setForeground(Color.WHITE);
        UserInfo.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 35));
        UserInfo.setBounds(1, 86, 202, 47);
        OwnInfo.add(UserInfo);

        JLabel lblTest = new JLabel("ID number: " + user.getIDNum());
        lblTest.setForeground(Color.WHITE);
        lblTest.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 17));
        lblTest.setBounds(1, 134, 137, 27);
        OwnInfo.add(lblTest);

        JLabel lblChatRoom = new JLabel("Chat Room");
        lblChatRoom.setForeground(Color.WHITE);
        lblChatRoom.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 20));
        lblChatRoom.setBounds(1, 10, 137, 17);
        OwnInfo.add(lblChatRoom);

        JLabel lblContacts = new JLabel("CONTACTS");
        lblContacts.setForeground(Color.WHITE);
        lblContacts.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 40));
        lblContacts.setBounds(15, 175, 226, 59);
        contentPane.add(lblContacts);

        panel = new JPanel();//好友列表及底部栏容器
        panel.setBackground(Color.DARK_GRAY);
        panel.setBounds(15, 244, 272, 450);
        panel.setBorder(null);
        contentPane.add(panel);
        panel.setLayout(null);

        JPanel listcontainer = new JPanel();//用于放置群聊列表和好友列表的JPanel
        listcontainer.setBackground(Color.DARK_GRAY);
        listcontainer.setBounds(0,0,272,420);
        listcontainer.setLayout(new BoxLayout(listcontainer,BoxLayout.Y_AXIS));

        list = new ListPane(user);
        groupList = new ListPane()
        scrollPane = new JScrollPane(list);
        FigureProperty.list = list;//设置list
        scrollPane.getVerticalScrollBar().setUI(new CustomizedScrollBarUI());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 不显示水平滚动条；
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.setBounds(0, 0, 272, 420);
        scrollPane.setBackground(Color.darkGray);
        panel.add(scrollPane);

        AddButton button = new AddButton();
        button.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 36));
        button.setBounds(236, 186,40, 40);
        contentPane.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(isAdding != true){
                    isAdding = true;
                    AddFriendUI afu = new AddFriendUI(mainInterfaceUI, FigureProperty.cc);
                }
            }
        });
        setVisible(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();//获取本机参数的工具包
        Dimension screenSize = toolkit.getScreenSize(); // 获取屏幕的尺寸
        int screenWidth = screenSize.width; // 获取屏幕的宽
        int screenHeight = screenSize.height; // 获取屏幕的高
        int height = this.getHeight();
        setLocation(screenWidth * 3 / 4, (screenHeight - height) / 2);

        FigureProperty.cc.start();//从服务器不断获取信息
    }



    public boolean isAdding() {
        return isAdding;
    }
    public void setAdding(boolean isAdding) {
        this.isAdding = isAdding;
    }

}
