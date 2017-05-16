package Chat_Client.MessageProtocol;

/**
 * Created by Wu on 2017/5/7.
 * 添加好友的应答消息
 * type 0x55
 */
public class MsgAddFriendResponse extends MsgHead{
    private byte state;

    public byte getState() {
        return state;
    }
    public void setState(byte state) {
        this.state = state;
    }
}
