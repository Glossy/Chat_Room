package Chat_Server.MessageProtocol;

import Chat_Server.DataBase.FigureProperty;
import Chat_Server.DataBase.ThreadDB;
import Chat_Server.DataBase.UserInfo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Wu on 2017/5/8.
 */
public class MsgFriendList extends MsgHead{
    private String UserName;
    private int pic;
    private byte listCount;
    private String listName[];
    private byte friendCount[];
    private int friendID[][];
    private int friendPic[][];
    private String nickName[][];
    private byte friendState[][];
    public MsgFriendList() {}
    public MsgFriendList(UserInfo user) {
        setType((byte)0x03);
        setDestination(user.getIDNum());
        setSource(FigureProperty.ServerID);
        setUserName(user.getNickName());
        setPic(user.getAvatar());
        setListCount(user.getCollectionCount());
        setListName(user.getListName());
        setFriendCount(user.getFriendCount());
        setFriendID(user.getFriendID());
        setFriendPic(user.getFriendPic());
        setNickName(user.getFriendName());
        int len = 13;
        len += 15; // userName
        len += 4;  //pic
        len += 1; // listCount
        len += (15 * listCount); // listName
        len += listCount; // friendCount
        friendState = new byte[listCount][];

        for (int i = 0; i < listCount; i++) {
            len += friendCount[i] * 24; // 每个好友长度为24 ： 头像 4、昵称 15、 ID 4、 状态 1

            friendState[i] = new byte[friendCount[i]];
        }
		/*
		 * 检查好友在线状态 实现方法 去线程数据库看看是不是存在同样IDNUM的线程
		 */

        for (int i = 0; i < listCount; i++) {
            for (int j = 0; j < friendCount[i]; j++) {
                if (ThreadDB.threadDB.containsKey(String.valueOf(friendID[i][j]))) {
                    friendState[i][j] = 0;
                } else {
                    friendState[i][j] = 1;
                }
            }
        }
        setTotalLen(len);

    }

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

    public String[][] getNickName() {
        return nickName;
    }

    public void setNickName(String[][] nickName) {
        this.nickName = nickName;
    }

    public byte[][] getFriendState() {
        return friendState;
    }

    public void setBodyState(byte[][] friendState) {
        this.friendState = friendState;
    }

    public int[][] getFriendPic() {
        return friendPic;
    }

    public void setFriendPic(int friendPic[][]) {
        this.friendPic = friendPic;
    }

    @Override
    public byte[] packMessage() throws IOException {
        ByteArrayOutputStream bous = new ByteArrayOutputStream();
        DataOutputStream dous = new DataOutputStream(bous);
        packMessageHead(dous);
        // 从mtl中获取信息
        String userName = getUserName();
        int pic = getPic();
        byte listCount = getListCount();
        String listName[] = getListName();
        byte friendCount[] = getFriendCount();
        int friendID[][] = getFriendID();
        int friendPic[][] = getFriendPic();
        String nickName[][] = getNickName();
        byte friendState[][] = getFriendState();

        // 开始写入流中
        writeString(dous, 15, userName);
        dous.writeInt(pic);
        dous.write(listCount);// 分组个数
        for (int i = 0; i < listCount; i++) {
            writeString(dous, 15, listName[i]);
            dous.write(friendCount[i]);
            for (int j = 0; j < friendCount[i]; j++) {// 每个组里面
                dous.writeInt(friendID[i][j]);
                dous.writeInt(friendPic[i][j]);
                writeString(dous, 15, nickName[i][j]);
                dous.write(friendState[i][j]);
            }
        }
        dous.flush();
        byte[] data = bous.toByteArray();
        return data;
    }
}
