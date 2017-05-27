package Chat_Client.DataBase;

/**
 * Created by Wu on 2017/5/7.
 * 群组列表
 */
public class GroupListInfo extends UserInfo {
    //以下存储的均为群组的自身信息，继承于UserInfo的属性只使用该用户的基本信息ID、昵称、头像
    //以下数据均排除了本人
    private String GGroupName;
    private int GGroupID;
    private int GGrouperID[];
    private String GGrouperNickName[];
    private byte GIsFriend[];
    private byte GGrouperState[];

    public String getGGroupName() {
        return GGroupName;
    }

    public void setGGroupName(String GGroupName) {
        this.GGroupName = GGroupName;
    }

    public int getGGroupID() {
        return GGroupID;
    }

    public void setGGroupID(int GGroupID) {
        this.GGroupID = GGroupID;
    }

    public int[] getGGrouperID() {
        return GGrouperID;
    }

    public void setGGrouperID(int[] GGrouperID) {
        this.GGrouperID = GGrouperID;
    }

    public String[] getGGrouperNickName() {
        return GGrouperNickName;
    }

    public void setGGrouperNickName(String[] GGrouperNickName) {
        this.GGrouperNickName = GGrouperNickName;
    }

    public byte[] getGIsFriend() {
        return GIsFriend;
    }

    public void setGIsFriend(byte[] GIsFriend) {
        this.GIsFriend = GIsFriend;
    }

    public byte[] getGGrouperState() {
        return GGrouperState;
    }

    public void setGGrouperState(byte[] GGrouperState) {
        this.GGrouperState = GGrouperState;
    }
}
