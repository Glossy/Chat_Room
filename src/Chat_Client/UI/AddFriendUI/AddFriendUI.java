package Chat_Client.UI.AddFriendUI;

import Chat_Client.Chat_Client;
import Chat_Client.DataBase.FigureProperty;
import Chat_Client.UI.CustomizedUI.CloseButton;
import Chat_Client.UI.CustomizedUI.CustomizedJOptionPane;
import Chat_Client.UI.CustomizedUI.MinimizeButton;
import Chat_Client.UI.CustomizedUI.UniversalButton;
import Chat_Client.UI.MainInterfaceUI.MainInterfaceUI;
import sun.applet.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Created by Wu on 2017/5/7.
 * 加好友时的界面
 */
public class AddFriendUI extends JFrame{
    private int x, y;
    private boolean isDraging = false;
    private MainInterfaceUI mainInterfaceUI;

    private JPanel contentPane;
    private JTextField list_name;
    private JTextField Friend_ID;

    public AddFriendUI(MainInterfaceUI mainInterfaceUI, Chat_Client chat_client){
        this.mainInterfaceUI = mainInterfaceUI;
        FigureProperty.addFriendUI = this;

        setUndecorated(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isDraging = true;
                x = e.getX();
                y = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDraging = false;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(isDraging){
                    int cursor_x = e.getX();
                    int cursor_y = e.getY();
                    setLocation(getLocation().x - x + cursor_x,getLocation().y - y + cursor_y);
                }
            }
        });
        setBounds(100, 100, 535, 418);
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);

        // 设置自制按钮
        CloseButton eb = new CloseButton(this);
        eb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                mainInterfaceUI.setAdding(false);
                dispose();
            }
        });
        int windowWeith = this.getWidth();
        eb.setBounds(windowWeith - 4 - 40, 0, 40, 30);
        contentPane.add(eb);
        MinimizeButton mb = new MinimizeButton(this);
        mb.setBounds(windowWeith - 4 - 80, 0, 40, 30);
        contentPane.add(mb);

        JLabel lbID = new JLabel("好友ID号");
        lbID.setForeground(Color.WHITE);
        lbID.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        lbID.setBounds(37, 167, 131, 42);
        contentPane.add(lbID);

        JLabel lbList = new JLabel("选择列表");
        lbList.setForeground(Color.WHITE);
        lbList.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        lbList.setBounds(37, 212, 131, 42);
        contentPane.add(lbList);

        list_name = new JTextField();
        list_name.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        list_name.setColumns(10);
        list_name.setBorder(null);
        list_name.setBounds(176, 216, 219, 35);
        contentPane.add(list_name);

        Friend_ID = new JTextField();
        Friend_ID.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        Friend_ID.setColumns(10);
        Friend_ID.setBounds(176, 173, 219, 35);
        Friend_ID.setBorder(null);
        // 只能输入数字
        Friend_ID.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (!(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)) {
                    e.consume(); // 消费此次事件
                }
            }
        });
        contentPane.add(Friend_ID);

        UniversalButton friendRegister = new UniversalButton("添加好友");
        friendRegister.setBounds(37, 323, 206, 42);
        contentPane.add(friendRegister);
        friendRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if("".equals(Friend_ID.getText().toString())&&"".equals(list_name.getText().toString())){
                        new CustomizedJOptionPane("Error","输入不能为空");
                    }
                    else if (Integer.parseInt(Friend_ID.getText().toString())==FigureProperty.IDNum){
                        new CustomizedJOptionPane("Error","好友不能是自己");
                    }
                    else{
                        chat_client.SendaddFriend(Integer.parseInt(Friend_ID.getText().toString()), list_name.getText().toString());
                    }
                }catch (NumberFormatException e1){
                    e1.printStackTrace();
                }catch (IOException e1){
                    e1.printStackTrace();
                }
            }
        });

        JLabel lblRegisterNewUser = new JLabel("Add Friend");
        lblRegisterNewUser.setForeground(Color.WHITE);
        lblRegisterNewUser.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 57));
        lblRegisterNewUser.setBackground(Color.WHITE);
        lblRegisterNewUser.setBounds(37, 60, 494, 80);
        contentPane.add(lblRegisterNewUser);

        JLabel label_1 = new JLabel("Chat Room");
        label_1.setForeground(Color.WHITE);
        label_1.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 20));
        label_1.setBounds(37, 20, 165, 35);
        contentPane.add(label_1);

        setResizable(false);
        setVisible(true);
    }
    public void showResult(int result) {//显示注册结果
        System.out.println("To show Result" + result);
        if (result == 0) {
            new CustomizedJOptionPane("Message","添加成功");
            FigureProperty.mainInterfaceUI.setAdding(false);
            this.dispose();
        } else if (result == 1){
            new CustomizedJOptionPane("Error","查无此人");
        } else if (result == 2) {
            new CustomizedJOptionPane("Error","已有此好友");
        } else {
            new CustomizedJOptionPane("Error","添加失败");
        }
    }
}
