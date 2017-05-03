package Chat_Client.UI.RegisterUI;

import Chat_Client.Chat_Client;
import Chat_Client.UI.CustomizedUI.CloseButton;
import Chat_Client.UI.CustomizedUI.MinimizeButton;
import Chat_Client.UI.CustomizedUI.UniversalButton;
import Chat_Client.UI.LoginUI.LoginAction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Wu on 2017/5/3.
 */
public class RegisterUI extends JFrame{
    private int xx, yy;
    private boolean isDragged = false;

    private JPanel contentPane;
    private JTextField password;
    private JTextField nickname;
    private JFrame jFrame = this;

    public RegisterUI(LoginAction loginAction, Chat_Client chat_client){
        setUndecorated(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isDragged = true;
                xx = getX();
                yy = getY();
            }
            @Override
            public void mouseReleased(MouseEvent e1){
                isDragged = false;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(isDragged){
                    int x = getLocation().x;
                    int y = getLocation().y;
                    setLocation(e.getX() + x - xx, e.getY() + y - yy);
                }
            }
        });

        setBounds(100, 100, 535, 418);
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(contentPane);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel labelNickname = new JLabel("Nickname");
        labelNickname.setForeground(Color.WHITE);
        labelNickname.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        labelNickname.setBounds(37, 167, 131, 42);
        contentPane.add(labelNickname);

        JLabel labelPassword = new JLabel("PassWord");
        labelPassword.setForeground(Color.WHITE);
        labelPassword.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        labelPassword.setBounds(37, 212, 131, 42);
        contentPane.add(labelPassword);

        password = new JTextField();
        password.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        password.setColumns(10);
        password.setBorder(null);
        password.setBounds(176, 219, 219, 35);
        contentPane.add(password);

        // 设置自制按钮
        CloseButton eb = new CloseButton();
        eb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                loginAction.setIs_Registering(false);
                dispose();
            }
        });
        int windowWeith = this.getWidth();
        eb.setBounds(windowWeith - 40, 0, 40, 30);
        contentPane.add(eb);

        MinimizeButton mb = new MinimizeButton(this);
        mb.setBounds(windowWeith - 80, 0, 40, 30);
        contentPane.add(mb);

        // 只能输入数字和字母
        password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)
                        || (keyChar >= KeyEvent.VK_A && keyChar <= KeyEvent.VK_Z)
                        || (keyChar >= 'a' && keyChar <= 'z')){
                }else {
                    System.out.println("非法输入");
                    e.consume();        //将此次事件消费掉
                }
            }
        });

        nickname = new JTextField();
        nickname.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        nickname.setBounds(176, 173, 219, 35);
        nickname.setBorder(null);
        contentPane.add(nickname);

        JLabel lbljk = new JLabel(
                "*提示：当注册成功，将返回唯一的JK码，请妥善保管。");
        lbljk.setForeground(Color.WHITE);
        lbljk.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        lbljk.setBounds(37, 268, 372, 28);
        contentPane.add(lbljk);

       UniversalButton buttonRegister = new UniversalButton("Register Now");
       buttonRegister.setBounds(37, 328, 206, 42);
       contentPane.add(buttonRegister);
       buttonRegister.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if(chat_client.Register(nickname.getText(),password.getText())){
                   //JOptionPane.showMessageDialog(null, "注册失败", "Error", JOptionPane.ERROR_MESSAGE);
               }
               else {
                   loginAction.setIs_Registering(false);
                   jFrame.dispose();
               }
           }
       });

        JLabel lblRegisterNewUser = new JLabel("Register New User");
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
}
