package Chat_Client.DataBase;

/**
 * Created by Wu on 2017/5/6.
 * 好友列表
 */
public class FriendListInfo extends UserInfo {
    private byte[][] friendState;

    public byte[][] getFriendState() {
        return friendState;
    }

    public void setFriendState(byte[][] friendState) {
        this.friendState = friendState;
    }

}

