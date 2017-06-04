package Chat_Client.UI.CustomizedUI;

import Chat_Client.DataBase.*;
import Chat_Client.UI.AddGroupUI.AddGroupUI;
import Chat_Client.UI.GroupDialogUI.GroupDialogHandler;
import Chat_Client.UI.GroupDialogUI.GroupDialogUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Wu on 2017/5/6.
 * 显示好友列表或者群列表的Panel
 * 构造函数需要传入好友列表或者群列表
 */
public class ListPane extends JPanel {
    private FriendListInfo list;
    private GroupListInfo groupListInfo;
    private FriendMemberJLabel user[][];
    private byte listCount;//多少组
    private byte[] friendNumber;//每组多少人
    private byte[][] friendState;//好友状态
    private byte[][] isFriend; //若传入群列表判断是否为好友

    public ListPane(FriendListInfo friendList, GroupListInfo groupListInfo){
        super();
        this.list = friendList;
        this.groupListInfo = groupListInfo;
        setBackground(Color.darkGray);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setSize(272, 450);
        this.setLocation(0, 0);
        initialize();
        groupInitialize();
        this.setVisible(true);
    }

    private void initialize(){
        //setBackground(Color.darkGray);
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
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        this.setSize(272, 450);
//        this.setLocation(0, 0);
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

    /**
     * 此方法为GroupDialogHandler 调用群聊窗体时获取发送人姓名参数
     * @param ID
     * @return
     */
    public String findNameByID(int ID){
        String name;
        for(int i = 0; i < groupListInfo.getGroupCount(); i++){
            for(int j = 0; j < groupListInfo.getGrouperCount()[i]; j++){
                if(groupListInfo.getGrouperID()[i][j] == ID){
                    name = groupListInfo.getGrouperNickName()[i][j];
                    return name;
                }
            }
        }
        return null;
    }

    /**
     * 该方法从一个人的群聊信息数组中返回一个特定群聊的信息
     * 返回的特定群聊信息只继承了UserInfo中的IDNum和NickName
     * @param GroupID
     * @return
     */
    public GroupListInfo findGroupByID(int GroupID){
        GroupListInfo result = new GroupListInfo();
        for(int i = 0; i < groupListInfo.getGroupCount(); i++){
            if(GroupID == groupListInfo.getGroupID()[i]){
                result.setIDNum(groupListInfo.getIDNum());
                result.setNickName(groupListInfo.getNickName());
                result.setGGrouperID(groupListInfo.getGrouperID()[i]);
                result.setGGrouperNickName(groupListInfo.getGrouperNickName()[i]);
                result.setGGroupID(groupListInfo.getGroupID()[i]);
                result.setGGroupName(groupListInfo.getGroupName()[i]);
                result.setGGrouperState(groupListInfo.getState()[i]);
                result.setGIsFriend(groupListInfo.getIsFriend()[i]);
                break;
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
    public void Refresh_List(GroupListInfo new_list) {
        byte new_groupCount = new_list.getGroupCount();

        boolean has_new_list = false;
        if (new_groupCount == groupListInfo.getGroupCount()) {
            for (int j = 0; j < new_groupCount; j++) {
                if (new_list.getGroupID()[j] != groupListInfo.getGroupID()[j]) {
                    has_new_list = true;
                    break;
                }
            }
        } else {
            has_new_list = true;
        }
        if (has_new_list) {
            this.removeAll();
            groupListInfo = new_list;
            groupInitialize();
        }
    }

    public void groupInitialize(){
//        setBackground(Color.darkGray);

        JLabel groupNameJLabel[] = new JLabel[groupListInfo.getGroupCount() + 1]; //加入一个加群组的JLabel 多加入了一个添加群组的label
        int i;
        for (i = 0; i < groupListInfo.getGroupCount(); i++) {
            groupNameJLabel[i] = new GroupNameJLabel(groupListInfo.getGroupName()[i],groupListInfo.getGroupID()[i],groupListInfo.getGrouperCount()[i]);
            }

        ListNameJLabel listName = new ListNameJLabel("我的群组",groupNameJLabel,1);
        this.add(listName);
        for(i = 0; i < groupListInfo.getGroupCount(); i++){
            this.add(groupNameJLabel[i]);
        }

        JLabel plus = new JLabel();
        plus.setBackground(Color.DARK_GRAY);
        plus.setForeground(Color.white);
        plus.setText("              +");
        plus.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 30));
        //plus.setIcon(new ImageIcon("img/ListImg/memberBGOff.jpg"));//默认显示未选中状态下的背景
        plus.setSize(new Dimension(272, 70));
        plus.addMouseListener(new MouseAdapter() {
            boolean is_exit = false;
            @Override
            public void mouseReleased(MouseEvent e) {
                if(is_exit){
                    plus.setIcon(new ImageIcon("img/ListImg/memberBGOff.jpg"));
                }
                else{
                    plus.setIcon(new ImageIcon("img/ListImg/memberBGOn.jpg"));
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                is_exit = false;
                plus.setIcon(new ImageIcon("img/ListImg/memberBGOn.jpg"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                plus.setIcon(new ImageIcon("img/ListImg/memberBGOff.jpg"));
                is_exit = true;
            }
            @Override
            public void mouseClicked(MouseEvent e) {//点击则弹出对话框
                //new AddGroupUI(FigureProperty.mainInterfaceUI,FigureProperty.cc);

            }
        });
        this.add(plus);
        groupNameJLabel[groupListInfo.getGroupCount()] = plus;
        plus.setVisible(false);
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        this.setSize(272, 450);
//        this.setLocation(0, 0);
    }

}
