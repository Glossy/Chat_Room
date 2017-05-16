package Chat_Client.DataBase;

/**
 * Created by Wu on 2017/5/7.
 * 群组列表
 */
public class GroupListInfo extends UserInfo {

    private byte isFriend[][];

    public byte[][] getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(byte[][] isFriend) {
        this.isFriend = isFriend;
    }

}
