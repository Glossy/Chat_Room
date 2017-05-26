package Chat_Client;

import Chat_Client.DataBase.FigureProperty;
import Chat_Client.UI.CustomizedUI.FriendMemberJLabel;
import Chat_Client.UI.CustomizedUI.GroupNameJLabel;
import Chat_Client.UI.CustomizedUI.ListNameJLabel;

import javax.swing.*;

/**
 * Created by Wu on 2017/5/17.
 */
public class test extends JFrame {
    public static void main(String args[]){
        new test();
    }
    public test(){
        JPanel jPanel = new JPanel();
        setBounds(100,100,900,900);
//        FriendMemberJLabel friendMemberJLabel = new FriendMemberJLabel(2,"d",123,(byte) 0);
//        //friendMemberJLabel.setBounds(0,0,40,15);
//        //jPanel.add(friendMemberJLabel);
//
//        FriendMemberJLabel friendMemberJLabel1 = new FriendMemberJLabel(2,"d",123,(byte) 0);
//        //friendMemberJLabel1.setBounds(0,0,40,15);
//        jPanel.add(friendMemberJLabel1);
//
//        JLabel s[] = {friendMemberJLabel,friendMemberJLabel1};
//        ListNameJLabel listNameJLabel = new ListNameJLabel("bn",s,0);

        GroupNameJLabel groupNameJLabel = new GroupNameJLabel("s",123,65);
        FriendMemberJLabel friendMemberJLabel = new FriendMemberJLabel(2,"s",123,(byte)0);
        jPanel.add(groupNameJLabel);
        jPanel.add(friendMemberJLabel);
        add(jPanel);
        setVisible(true);
    }
}
