package Chat_Server.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Wu on 2017/5/8.
 */
public class UserInfo {
    private int IDNum;
    private String nickName;
    private int avatar;//头像

    UserInfo(ResultSet userResult) throws SQLException {
        IDNum = userResult.getInt("user_id");
        nickName = userResult.getString("nickname");
        avatar = userResult.getInt("avatar");
    }

    private byte collectionCount;//
    private String ListName[];//
    private byte[] friendCount;//
    private int friendID[][];//
    private int friendPic[][];//
    private String friendName[][];//

    public byte getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(byte listCount) {
        this.collectionCount = listCount;
    }

    public String[] getListName() {
        return ListName;
    }

    public void setListName(String[] listName) {
        ListName = listName;
    }

    public byte[] getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(byte[] friendCount) {
        this.friendCount = friendCount;
    }

    public int[][] getFriendID() {
        return friendID;
    }

    public void setFriendID(int[][] friendID) {
        this.friendID = friendID;
    }

    public String[][] getFriendName() {
        return friendName;
    }

    public void setFriendName(String[][] friendName) {
        this.friendName = friendName;
    }

    public int getIDNum() {
        return IDNum;
    }

    public void setIDNum(int IDNum) {
        IDNum = IDNum;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nick) {
        nickName = nick;
    }

    public boolean equals(UserInfo compare) {//比较ID
        if (compare.getIDNum() == IDNum/* && compare.getPassWord().equals(passWord)*/) {
            return true;
        }
        return false;
    }

    public int[][] getFriendPic() {
        return friendPic;
    }

    public void setFriendPic(int friendpic[][]) {
        this.friendPic = friendpic;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
