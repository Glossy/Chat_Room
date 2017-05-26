package Chat_Client.MessageProtocol;

/**
 * Created by Wu on 2017/5/18.
 * 加群聊消息体
 * type 0x07
 */
public class MsgAddGroup extends MsgHead {

    private int addGroupID;

    public int getAddGroupID() {
        return addGroupID;
    }

    public void setAddGroupID(int addGroupID) {
        this.addGroupID = addGroupID;
    }
}
