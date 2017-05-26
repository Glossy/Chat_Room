package Chat_Client.DataBase;

/**
 * Created by Wu on 2017/5/6.
 * 此类为用户的基本信息
 */
public class UserInfo {
    private int IDNum;// 保存该用户的ID号
    private String NickName;// 保存该用户的昵称
    private String PassWord;// 保存该用户的密码
    private int Picture;//用户的头像号

    //好友信息
    private byte listCount;// 保存有多少组好友
    private String ListName[];// 保存每个分组的名称
    private byte[] friendCount;// 每组有多少个人
    private int [][] friendID;// 每个好友的ID号
    private int friendPic[][];//好友头像
    private String friendNickName[][];// 每个好友的昵称

    //群组信息
    private byte groupCount;//保存有多少组群聊
    private int groupID[];
    private String groupName[];//群聊名称
    private byte[] grouperCount;//群聊中人数
    private int grouperID[][]; //群聊人中的ID号
    private String grouperNickName[][]; //群聊中每个人的昵称
    private byte isFriend[][]; //群聊中每个人是否为自己的好友
    private byte state[][];//状态

    public byte[][] getState() {
        return state;
    }

    public void setState(byte[][] state) {
        this.state = state;
    }

    public byte getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(byte groupCount) {
        this.groupCount = groupCount;
    }

    public int[] getGroupID() {
        return groupID;
    }

    public void setGroupID(int[] groupID) {
        this.groupID = groupID;
    }

    public String[] getGroupName() {
        return groupName;
    }

    public void setGroupName(String[] groupName) {
        this.groupName = groupName;
    }

    public byte[] getGrouperCount() {
        return grouperCount;
    }

    public void setGrouperCount(byte[] grouperCount) {
        this.grouperCount = grouperCount;
    }

    public int[][] getGrouperID() {
        return grouperID;
    }

    public void setGrouperID(int[][] grouperID) {
        this.grouperID = grouperID;
    }

    public String[][] getGrouperNickName() {
        return grouperNickName;
    }

    public void setGrouperNickName(String[][] grouperNickName) {
        this.grouperNickName = grouperNickName;
    }

    public byte[][] getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(byte[][] isFriend) {
        this.isFriend = isFriend;
    }

    public byte getListCount() {return listCount;}
    public void setListCount(byte listCount) {this.listCount = listCount;}

    public String[] getListName() {return ListName;}
    public void setListName(String[] listName) {ListName = listName;}

    public byte[] getFriendCount() {return friendCount;}
    public void setFriendCount(byte[] bodyCount) {this.friendCount = bodyCount;}

    public int[][] getFriendID() {return friendID;}
    public void setFriendID(int[][] bodyNum) {this.friendID = bodyNum;}

    public String[][] getFriendNickName() {return friendNickName;}
    public void setFriendNickName(String[][] nickName) {this.friendNickName = nickName;}

    public int getIDNum() {return IDNum;}
    public void setIDNum(int IDNum) {this.IDNum = IDNum;}

    public String getNickName() {return NickName;}
    public void setNickName(String nickName) {NickName = nickName;}

    public String getPassWord() {return PassWord;}
    public void setPassWord(String passWord) {PassWord = passWord;}

    public int getPic() {return Picture;}
    public void setPic(int pic) {Picture = pic;}

    public int[][] getFriendPic() {return friendPic;}
    public void setFriendPic(int bodypic[][]) {this.friendPic = bodypic;}

    public boolean equals(UserInfo compare) {
        if (compare.getIDNum() == IDNum && compare.getPassWord().equals(PassWord)) {
            return true;
        }
        return false;
    }
}
