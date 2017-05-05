package Chat_Client.UI.LoginUI;

import Chat_Client.UI.CustomizedUI.*;
import Chat_Client.Chat_Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Wu on 2017/5/1.
 * LoginUI
 * 客户端启动登陆界面
 * 客户端程序从这里开始
 * 利用ChatClient连接服务器
 * 利用LoginAction监听按键
 */
public class LoginUI extends JFrame{
    /**
     * 客户端登陆(启动界面)
     */
    public LoginUI(){
        // 设置无标题栏
        setUndecorated(true);
        //设置窗口拖动监听器
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cursor_x = e.getX();
                cursor_y = e.getY();
                isDragged = true;
            }
            @Override
            public void mouseReleased(MouseEvent e){
                isDragged = false;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(isDragged){
                    int x = getLocation().x;
                    int y = getLocation().y;
                    //窗口新的坐标位置  = 移动前坐标位置+（鼠标指针当前坐标-鼠标按下时指针的位置）
                    setLocation(e.getX() + x - cursor_x,e.getY() + y - cursor_y);
                }
            }
        });
        setBounds(100, 100, 439, 369);
        //设置在屏幕中央
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        setResizable(false);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblUserName = new JLabel("IDNumber");
        lblUserName.setForeground(Color.WHITE);
        lblUserName.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        lblUserName.setBounds(37, 170, 126, 27);
        contentPane.add(lblUserName);

        // 设置自制按钮
        ExitButton exitButton = new ExitButton();
        int windowWeith = this.getWidth();
        exitButton.setBounds(windowWeith - 40, 0, 40, 30);
        contentPane.add(exitButton);

        MinimizeButton minimizeButton = new MinimizeButton(this);
        minimizeButton.setBounds(windowWeith - 80, 0, 40, 30);
        contentPane.add(minimizeButton);

        JLabel lblPassword = new JLabel("PassWord");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        lblPassword.setBounds(37, 215, 126, 27);
        contentPane.add(lblPassword);

        //	设置监听器，用于监听按键事件
        LoginAction loginAction = new LoginAction();

        jTextField = new JTextField();
        jTextField.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        jTextField.setBounds(170, 167, 219, 35);
        jTextField.setBorder(null);
        contentPane.add(jTextField);
        loginAction.setUsername(jTextField);

        // 限制只能输入数字
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keychar = e.getKeyChar();
                if(keychar >= KeyEvent.VK_0 && keychar <= KeyEvent.VK_9){
                }
                else{
                    System.out.println("非法输入");
                    e.consume();
                }
            }
        });

        JLabel lbLoginSystem = new JLabel("Login System");
        lbLoginSystem.setForeground(Color.WHITE);
        lbLoginSystem.setBackground(Color.WHITE);
        lbLoginSystem.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 58));
        lbLoginSystem.setBounds(37, 60, 357, 80);
        contentPane.add(lbLoginSystem);

        UniversalButton Login = new UniversalButton("Login");
        Login.setBounds(225, 285, 170, 40);
        contentPane.add(Login);
        Login.setFocusPainted(false);
        Login.addActionListener(loginAction);

        passwordField = new JPasswordField();
        passwordField.setBounds(170, 212, 219, 35);
        passwordField.setBorder(null);
        passwordField.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 30));
        contentPane.add(passwordField);
        loginAction.setPassword(passwordField);

        UniversalButton register = new UniversalButton("Register");
        register.setBounds(37, 285, 170, 40);
        contentPane.add(register);
        register.addActionListener(loginAction);

        JLabel label = new JLabel("Chat Room");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 20));
        label.setBounds(37, 20, 165, 35);
        contentPane.add(label);

        setVisible(true);

        //利用ChatClient连接服务器
        Chat_Client chat_client = new Chat_Client("localhost", 9090);
        if (!chat_client.ConnectServer()) {
//
            JOptionPane.showMessageDialog(null, "无法连接服务器", "错误", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        loginAction.setChat_client(chat_client);
    }
    private boolean isDragged = false;
    private int cursor_x, cursor_y;
    private JPanel contentPane;
    private JTextField jTextField;
    private JPasswordField passwordField;

}
