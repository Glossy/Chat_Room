package Chat_Client.UI.CustomizedUI;

import Chat_Client.DataBase.FriendListInfo;
import Chat_Client.DataBase.GroupListInfo;
import Chat_Client.DataBase.UserInfo;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Wu on 2017/5/6.
 * 显示好友列表或者群列表的Panel
 * 构造函数需要传入好友列表或者群列表
 */
public class ListPane extends JPanel {
    private FriendListInfo list;
    private FriendMemberJLabel user[][];
    private byte listCount;//多少组
    private byte[] friendNumber;//每组多少人
    private byte[][] friendState;//好友状态

    public ListPane(FriendListInfo friendList){
        super();
        this.list = friendList;
        initialize();
    }

    public ListPane(GroupListInfo groupListInfo){
    }
    private void initialize(){
        setBackground(Color.darkGray);
        listCount = list.getListCount();
        String[] listName = list.getListName();
        friendNumber = list.getFriendCount();
        int[][] friendID = list.getFriendID();
        int[][] friendyPic = list.getFriendPic();
        String[][] friendNickName = list.getFriendNickName();
        friendState = list.getFriendState();
        ListNameJLabel[] list = new ListNameJLabel[listCount];
        user = new FriendMemberJLabel[listCount][];
        int i, j;
        for (i = 0; i < listCount; i++) {
            user[i] = new FriendMemberJLabel[friendNumber[i]];
            for (j = 0; j < friendNumber[i]; j++) {
                int pic = friendyPic[i][j];
                int num = friendID[i][j];
                String name = friendNickName[i][j];
                byte State = friendState[i][j];
                user[i][j] = new FriendMemberJLabel(pic, name, num, State);
            }
            list[i] = new ListNameJLabel(listName[i], user[i],0);//分组名
            System.out.println(list[i].getText());
            this.add(list[i]);
            for (j = 0; j < friendNumber[i]; j++) {
                this.add(user[i][j]);
            }
        }
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setSize(272, 450);
        this.setLocation(0, 0);
    }

    public UserInfo findUserByID(int IDNum){
        UserInfo result = new UserInfo();
        for(int i = 0;i<listCount;i++){
            for(int j = 0; j<friendNumber[i];j++){
                if(user[i][j].getMemberIDNum() == IDNum){
                    result.setIDNum(IDNum);
                    result.setNickName(user[i][j].getNickname());
                    result.setPic(user[i][j].getPic());
                    break;
                }
            }
        }
        return result;
    }

    public void HaveMemberMsg(int IDNum){//检查list好友中是否有新消息
        for(int i = 0;i<listCount;i++){
            for(int j = 0; j<friendNumber[i];j++){
                if(user[i][j].getMemberIDNum() == IDNum){
                    user[i][j].receiverMsg();
                }
            }
        }
    }

    public void Refresh_List(FriendListInfo new_list){
        byte new_listCount = new_list.getListCount();
        byte[] new_friendCount = new_list.getFriendCount();
        byte[][] state = new_list.getFriendState();
        boolean has_new_member = false;
        boolean has_new_list = false;
        if(new_listCount == listCount){
            for(int i = 0; i< listCount;i++){
                if(new_friendCount[i] != friendNumber[i]){
                    has_new_member = true;
                    break;
                }
                for(int j = 0;j < friendNumber[i];j++){
                    user[i][j].setState(state[i][j]);
                }
            }
        }
        else{
            has_new_list = true;
        }
        if(has_new_member || has_new_list){
            this.removeAll();
            list = new_list;
            initialize();

        }
    }
}
