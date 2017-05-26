package Chat_Client.MessageProtocol;

/**
 * Created by Wu on 2017/5/18.
 * 加群聊的回复消息
 * type 0x77
 */
public class MsgAddGroupResponse extends MsgHead {
    private byte state;

    public byte getState() {
        return state;
    }
    public void setState(byte state) {
        this.state = state;
    }
}
