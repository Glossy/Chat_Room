package Chat_Client.UI.LoginUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Chat_Client.Chat_Client;
import Chat_Client.DataBase.FigureProperty;
import Chat_Client.UI.CustomizedUI.CustomizedJOptionPane;
import Chat_Client.UI.MainInterfaceUI.MainInterfaceUI;
import Chat_Client.UI.RegisterUI.RegisterUI;

/**
 * Created by Wu on 2017/5/2.
 * 用于监听登录界面
 */
public class LoginAction implements ActionListener {
    private JTextField userid_field;// Login界面的ID号
    private JPasswordField password_field;// Login界面的密码
    private Chat_Client chat_client; //LoginUI传过来的用于连接服务器的ChatClient
    private boolean is_Registering = false;// 这个参数用于判断是否已经打开了注册界面
    public static JFrame LoginJF;//登陆界面以及注册界面的JF 用于关闭窗口

    /**
     * Invoked when an action occurs.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();//获取事件源
        int user_id;
        String password;
        //若按下的按键为登陆
        if(jButton.getText().equals("Login")){
            try{
                user_id = Integer.valueOf(userid_field.getText());
                password = new String(password_field.getPassword());
                if (password.equals("")) {
                    new CustomizedJOptionPane("Error","密码不为空");
                }
                else {
                    int result = chat_client.Login(user_id, password);
                    if (result == 0) {//若密码正确
                        FigureProperty.cc = chat_client;
                        new MainInterfaceUI();
                        LoginJF.dispose();
                    } else if (result == 1) {//若密码错误
                        new CustomizedJOptionPane( "Error","用户名或密码错误");
                        password_field.setText("");
                    } else if (result == 2) {
                        new CustomizedJOptionPane( "Error","该用户已经登陆");
                        password_field.setText("");
                        userid_field.setText("");
                    } else if(result == 3){
                        new CustomizedJOptionPane( "Error","发生未知错误，请稍后重试");
                        password_field.setText("");
                        userid_field.setText("");
                    }
                }
            } catch (NumberFormatException e1){//若输入ID号为空
                new CustomizedJOptionPane( "Error","ID不能为空");
            }
        }
        if(jButton.getText().equals("Register")){//
            if(!is_Registering){
                new RegisterUI(this,chat_client);
                is_Registering = true;
            }
        }
    }

    public Chat_Client getChat_client(){return chat_client;}
    public void setChat_client(Chat_Client c){chat_client = c;}

    public JTextField getUsername() {
        return userid_field;
    }
    public void setUsername(JTextField username) {
        this.userid_field = username;
    }

    public JPasswordField getPassword() {
        return password_field;
    }
    public void setPassword(JPasswordField password) {
        this.password_field = password;
    }

    public boolean isIs_Registering() {
        return is_Registering;
    }
    public void setIs_Registering(boolean is_Registering) {
        this.is_Registering = is_Registering;
    }

}
