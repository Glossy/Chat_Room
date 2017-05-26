package Chat_Client.MessageProtocol;

/**
 * 群聊成员的消息体
 * Created by Wu on 2017/5/18.
 * type 0x66
 */
public class MsgGroupList extends MsgHead {
    private byte groupCount;//保存有多少组群聊
    private int groupID[];  //群聊组ID
    private String groupName[];//群聊名称
    private byte[] grouperCount;//群聊中人数
    private int grouperID[][]; //群聊人中的ID号
    private String grouperNickName[][]; //群聊中每个人的昵称
    private byte isFriend[][]; //群聊中每个人是否为自己的好友
    private byte grouperstate[][]; //是否在线

    public byte[][] getGrouperstate() {
        return grouperstate;
    }

    public void setGrouperstate(byte[][] grouperstate) {
        this.grouperstate = grouperstate;
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
}
