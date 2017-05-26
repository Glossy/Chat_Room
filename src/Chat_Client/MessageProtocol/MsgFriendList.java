package Chat_Client.MessageProtocol;

/**
 * Created by Wu on 2017/5/7.
 * type 0x03
 */
public class MsgFriendList extends MsgHead {

    private String UserName;
    private int pic;
    private byte listCount;
    private String listName[];
    private byte friendNum[]; //每组好友数
    private int friendID[][];
    private int friendPic[][];
    private String nickName[][];    //好友名称
    private byte friendState[][];


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public byte getListCount() {
        return listCount;
    }

    public void setListCount(byte listCount) {
        this.listCount = listCount;
    }

    public String[] getListName() {
        return listName;
    }

    public void setListName(String[] listName) {
        this.listName = listName;
    }

    public byte[] getFriendNum() {
        return friendNum;
    }

    public void setFriendNum(byte[] friendNum) {
        this.friendNum = friendNum;
    }

    public int[][] getFriendID() {
        return friendID;
    }

    public void setFriendID(int[][] friendID) {
        this.friendID = friendID;
    }

    public int[][] getFriendPic() {
        return friendPic;
    }

    public void setFriendPic(int[][] friendPic) {
        this.friendPic = friendPic;
    }

    public String[][] getNickName() {
        return nickName;
    }

    public void setNickName(String[][] nickName) {
        this.nickName = nickName;
    }

    public byte[][] getFriendState() {
        return friendState;
    }

    public void setFriendState(byte[][] friendState) {
        this.friendState = friendState;
    }
}
