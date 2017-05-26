package Chat_Server.MessageProtocol;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Wu on 2017/5/8.
 * 用于解构消息
 */
public class MsgParser {
    /*
	 * 从流中读取固定长度的字节，并将其转换为字符串
	 *
	 * @param dins: 读取的流对象
	 *
	 * @param len: 读取的字节长度
	 *
	 * @return 转换后的字符串
	 */
    private static String readString(DataInputStream dins, int len) throws IOException {
        byte[] data = new byte[len];
        dins.readFully(data);
        return new String(data).trim();
    }

    /**
	 * 此方法用于解构消息
	 *
	 * @param data 字节信息
	 *
	 * @return 消息解构后赋值的MsgHead的子类
	 */
    public static MsgHead parseMsg(byte[] data) throws IOException {
        int totalLen = data.length + 4; // 之前已经读取了4个字节的长度信息
        ByteArrayInputStream bins = new ByteArrayInputStream(data);
        DataInputStream dins = new DataInputStream(bins);
        byte msgtype = dins.readByte();
        int dest = dins.readInt();
        int src = dins.readInt();
        if (msgtype == 0x01) {// 如果是注册信息
            String nikeName = readString(dins, 15);
            String pwd = readString(dins, 10);
            MsgRegister mr = new MsgRegister();
            mr.setTotalLen(totalLen);
            mr.setType(msgtype);
            mr.setDestination(dest);
            mr.setSource(src);
            mr.setNickName(nikeName);
            mr.setPwd(pwd);
            return mr;
        }

        else if (msgtype == 0x11) { // 如果是注册返回信息
            byte state = dins.readByte();
            MsgRegisterResponse mrr = new MsgRegisterResponse();
            mrr.setTotalLen(totalLen);
            mrr.setType(msgtype);
            mrr.setDestination(dest);
            mrr.setSource(src);
            mrr.setState(state);
            return mrr;
        }

        else if (msgtype == 0x02) { // 如果是登陆信息
            String pwd = readString(dins, 10);
            MsgLogin mli = new MsgLogin();
            mli.setTotalLen(totalLen);
            mli.setType(msgtype);
            mli.setDestination(dest);
            mli.setSource(src);
            mli.setPwd(pwd);
            return mli;
        }

        else if (msgtype == 0x22) { // 如果是登陆返回信息
            byte state = dins.readByte();
            MsgLoginResponse mlr = new MsgLoginResponse();
            mlr.setTotalLen(totalLen);
            mlr.setType(msgtype);
            mlr.setDestination(dest);
            mlr.setSource(src);
            mlr.setState(state);
            return mlr;
        }

        else if (msgtype == 0x03) { // 如果是好友列表
            int i, j;

            String UserName = readString(dins, 15);// 读取用户名
            int pic = dins.readInt();
            byte listCount = dins.readByte();// 读取好友分组个数
            String listName[] = new String[listCount];
            byte friendCount[] = new byte[listCount];

            int friendID[][];
            friendID = new int[listCount][];// 设置第一维长度

            int friendPic[][];
            friendPic = new int[listCount][];

            String nickName[][];
            nickName = new String[listCount][];// 设置第一维长度

            byte friendState[][];
            friendState = new byte[listCount][];// 设置第一维长度

            for (i = 0; i < listCount; i++) {
                listName[i] = readString(dins, 15);
                friendCount[i] = dins.readByte();

                friendID[i] = new int[friendCount[i]];// 设置第二维长度
                friendPic[i] = new int[friendCount[i]];
                nickName[i] = new String[friendCount[i]];// 设置第二维长度
                friendState[i] = new byte[friendCount[i]];// 设置第二维长度

                for (j = 0; j < friendCount[i]; j++) {
                    friendID[i][j] = dins.readInt();
                    friendPic[i][j] = dins.readInt();
                    nickName[i][j] = readString(dins, 15);
                    friendState[i][j] = dins.readByte();
                    System.out.println(friendID[i][j]+" "+ friendPic[i][j]+" " + nickName[i][j] + " " + friendState[i][j]);
                }

            }

			/*
			 * 读取结束，写入对象
			 */
            MsgFriendList mtl = new MsgFriendList();
            mtl.setUserName(UserName);
            mtl.setPic(pic);
            mtl.setTotalLen(totalLen);
            mtl.setType(msgtype);
            mtl.setDestination(dest);
            mtl.setSource(src);
            mtl.setListCount(listCount);
            mtl.setListName(listName);
            mtl.setFriendCount(friendCount);
            mtl.setFriendID(friendID);
            mtl.setFriendPic(friendPic);
            mtl.setNickName(nickName);
            mtl.setBodyState(friendState);

            return mtl;

        }

        else if (msgtype == 0x04) { //如果是传送消息
            MsgChatText mct = new MsgChatText();
            String msgText = readString(dins, totalLen - 13);
            mct.setTotalLen(totalLen);
            mct.setType(msgtype);
            mct.setDestination(dest);
            mct.setSource(src);
            mct.setMsgText(msgText);

            return mct;
        }

        else if (msgtype == 0x05){ //添加好友
            MsgAddFriend maf = new MsgAddFriend();
            int add_id = dins.readInt();
            String list_name = readString(dins, totalLen - 17);
            maf.setTotalLen(totalLen);
            maf.setType(msgtype);
            maf.setDestination(dest);
            maf.setSource(src);
            maf.setAdd_ID(add_id);
            maf.setList_name(list_name);
            return maf;
        }

        else if (msgtype == 0x55){ //添加好友回执
            MsgAddFriendResponse mafr = new MsgAddFriendResponse();
            byte state = dins.readByte();
            mafr.setTotalLen(totalLen);
            mafr.setType(msgtype);
            mafr.setDestination(dest);
            mafr.setSource(src);
            mafr.setState(state);
            return mafr;
        }

        return null;

    }
}
