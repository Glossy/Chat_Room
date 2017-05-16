package Chat_Client.MessageProtocol;

/**
 * Created by Wu on 2017/5/7.
 * 添加好友消息
 * type 0x05
 */
public class MsgAddFriend extends MsgHead {
    private int add_ID;
    private String list_name;

    public int getAdd_ID() {
        return add_ID;
    }

    public void setAdd_ID(int add_ID) {
        this.add_ID = add_ID;
    }

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }
}
