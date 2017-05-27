package Chat_Client;

import Chat_Client.DataBase.FigureProperty;
import Chat_Client.DataBase.FriendListInfo;
import Chat_Client.DataBase.GroupListInfo;
import Chat_Client.UI.CustomizedUI.FriendMemberJLabel;
import Chat_Client.UI.CustomizedUI.GroupNameJLabel;
import Chat_Client.UI.CustomizedUI.ListNameJLabel;
import Chat_Client.UI.CustomizedUI.ListPane;
import Chat_Client.UI.LoginUI.LoginAction;
import Chat_Client.UI.RegisterUI.RegisterUI;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

/**
 * Created by Wu on 2017/5/17.
 */
public class test extends JFrame {
    public static void main (String args[]){
        try {
            new test();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public test() throws Exception{
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
        setLayout(null);
        byte isFriend[] = {0 , 1, 1};
        byte grouperState[] = {0, 1, 1};
        int grouperID[] = {1, 2, 3};
        String[] groupName = {"A","B","C"};
        int[] groupID = {1 , 2, 3};
        byte[] grouperCount = {1, 2, 3};

        String nick[] = {"b","c","d"};
        GroupListInfo groupListInfo = new GroupListInfo();
        groupListInfo.setGGroupName("Group C");
        groupListInfo.setGGroupID(123);
        groupListInfo.setGIsFriend(isFriend);
        groupListInfo.setGGrouperState(grouperState);
        groupListInfo.setGGrouperID(grouperID);
        groupListInfo.setGGrouperNickName(nick);
        groupListInfo.setGroupCount((byte)3);
        groupListInfo.setGroupName(groupName);
        groupListInfo.setGroupID(groupID);
        groupListInfo.setGrouperCount(grouperCount);

//        ListPane listPane = new ListPane(groupListInfo);

        int[][] friendid = {{1,2}};
        String[] listname = {"a"};
        String[][] friendname = {{"s","a"}};
        int[][] friendPic = {{1,2}};
        byte[][] state = {{1,1}};
        byte[] friendCount = {2};

        FriendListInfo friendListInfo = new FriendListInfo();
        friendListInfo.setFriendID(friendid);
        friendListInfo.setListName(listname);
        friendListInfo.setFriendNickName(friendname);
        friendListInfo.setFriendPic(friendPic);
        friendListInfo.setFriendState(state);
        friendListInfo.setListCount((byte) 1);
        friendListInfo.setFriendCount(friendCount);


        ListPane friendlist = new ListPane(friendListInfo,groupListInfo);


        add(friendlist);
//        GroupNameJLabel groupNameJLabel = new GroupNameJLabel("s",123,65);
//        GroupNameJLabel groupNameJLabel1 = new GroupNameJLabel("d",23,20);
//        FriendMemberJLabel friendMemberJLabel = new FriendMemberJLabel(2,"s",123,(byte)0);
//        jPanel.add(groupNameJLabel);
//        jPanel.add(friendMemberJLabel);
//        add(jPanel);
        setVisible(true);

        //new RegisterUI(new LoginAction(), new Chat_Client("localhost",9090));
    }
}
