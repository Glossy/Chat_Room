package Chat_Client.UI.AddGroupUI;

import Chat_Client.Chat_Client;
import Chat_Client.DataBase.FigureProperty;
import Chat_Client.UI.CustomizedUI.CloseButton;
import Chat_Client.UI.CustomizedUI.CustomizedJOptionPane;
import Chat_Client.UI.CustomizedUI.MinimizeButton;
import Chat_Client.UI.CustomizedUI.UniversalButton;
import Chat_Client.UI.MainInterfaceUI.MainInterfaceUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Created by Wu on 2017/5/17.
 * 用于添加群组的窗体
 */
public class AddGroupUI extends JFrame {
    private int x, y;
    private boolean isDraging = false;
    private MainInterfaceUI mainInterfaceUI;

    private JPanel contentPane;
    private JTextField list_name;
    private JTextField Group_ID;
    private JButton newGroup;

    public AddGroupUI(){
//        this.mainInterfaceUI = mainInterfaceUI;
        FigureProperty.addGroupUI = this;

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

        JLabel lbID = new JLabel("群聊ID号");
        lbID.setForeground(Color.WHITE);
        lbID.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        lbID.setBounds(37, 174, 131, 42);
        contentPane.add(lbID);

        Group_ID = new JTextField();
        Group_ID.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        Group_ID.setColumns(10);
        Group_ID.setBounds(186, 174, 245, 40);
        Group_ID.setBorder(null);
        // 只能输入数字
        Group_ID.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (!(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)) {
                    e.consume(); // 消费此次事件
                }
            }
        });
        contentPane.add(Group_ID);

        JLabel lbName = new JLabel("群聊名称");
        lbName.setForeground(Color.WHITE);
        lbName.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        lbName.setBounds(37, 245, 131, 42);
        contentPane.add(lbName);

        list_name = new JTextField();
        list_name.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        list_name.setColumns(10);
        list_name.setBorder(null);
        list_name.setBounds(186, 245, 245, 42);
        contentPane.add(list_name);

        UniversalButton friendRegister = new UniversalButton("添加群聊");
        friendRegister.setBounds(37, 323, 206, 42);
        contentPane.add(friendRegister);
        friendRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if("".equals(Group_ID.getText().toString())){
                        new CustomizedJOptionPane("Error","输入不能为空");
                    }
                    else{
                        //chat_client.sendAddGroup(Integer.parseInt(Group_ID.getText().toString()));
                    }
                }catch (NumberFormatException e1) {
                    e1.printStackTrace();
                } catch (IOException e1){
                    e1.printStackTrace();
                }
            }
        });

        newGroup = new UniversalButton("新建群聊");
        newGroup.setBounds(535 - 206 - 37, 323, 206, 42);
        newGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//向服务器发送新建群聊信息
                int a =
                new CustomizedJOptionPane("Caution",String.format("新建的群号为%d",a);
            }
        });
        contentPane.add(newGroup);

        JLabel lblRegisterNewUser = new JLabel("Add Group");
        lblRegisterNewUser.setForeground(Color.WHITE);
        lblRegisterNewUser.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 57));
        lblRegisterNewUser.setBackground(Color.WHITE);
        lblRegisterNewUser.setBounds(37, 60, 494, 80);
        contentPane.add(lblRegisterNewUser);

        JLabel label_1 = new JLabel("Chat Room");
        label_1.setForeground(Color.WHITE);
        label_1.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 23));
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
            new CustomizedJOptionPane("Error","该群不存在");
        } else if (result == 2) {
            new CustomizedJOptionPane("Error","已在此群聊中");
        } else {
            new CustomizedJOptionPane("Error","添加失败");
        }
    }
}
