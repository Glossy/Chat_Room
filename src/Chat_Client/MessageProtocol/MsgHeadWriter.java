package Chat_Client.MessageProtocol;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Wu on 2017/5/3.
 * 此类用于打包信息
 */
public class MsgHeadWriter {
    /**
	 * 向流对象中写入固定长度的字符串
	 *
	 * @param dous 流对象
	 *
	 * @param len 字节的长度
	 *
	 * @param s 写入的字符串
	 */
    private static void writeString(DataOutputStream dous, int len, String s)
            throws IOException{
        byte[] data = s.getBytes();
        if(data.length > len){
            throw new IOException("写入长度超长");
        }
        dous.write(data);
        while (data.length < len){
            dous.writeByte('\0');
            len--;
        }
    }

    public static byte[] packMessage(MsgHead msg)
            throws IOException{
        ByteArrayOutputStream bous = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bous);
        writeHead(dos,msg);
        int msgType = msg.getType();
        if(msgType == 0x01){
            MsgRegister mr = (MsgRegister) msg;
            writeString(dos, 10, mr.getNickName());
            writeString(dos, 10, mr.getPassWord());
        }else if(msgType == 0x11){
            MsgRegisterResponse msgRegisterResponse = (MsgRegisterResponse)msg;
            dos.write(msgRegisterResponse.getState());
        }else if(msgType == 0x02){
            MsgLogin msgLogin = (MsgLogin) msg;
            writeString(dos, 10, msgLogin.getPwd());
        }else if(msgType == 0x22){
            MsgLoginResponse msgLoginResponse = (MsgLoginResponse)msg;
            dos.write(msgLoginResponse.getState());
        }else if(msgType == 0x03){
           // MsgFriendList
            MsgFriendList msgFriendList = (MsgFriendList) msg;

            String userName = msgFriendList.getUserName();
            int pic = msgFriendList.getPic();
            byte listCount = msgFriendList.getListCount();
            String listName[] = msgFriendList.getListName();
            byte friendNum[] = msgFriendList.getFriendNum();
            int friendID[][] = msgFriendList.getFriendID();
            int friendPic[][] = msgFriendList.getFriendPic();
            String nickName[][] = msgFriendList.getNickName();
            byte friendState[][] = msgFriendList.getFriendState();

            // 开始写入流中
            int i, j;
            writeString(dos, 10, userName);
            dos.writeInt(pic);
            dos.write(listCount);// 分组个数
            for (i = 0; i < listCount; i++) {
                writeString(dos, 10, listName[i]);
                dos.write(friendNum[i]);
                for (j = 0; j < friendNum[i]; j++) {// 每个组里面
                    dos.writeInt(friendID[i][j]);
                    dos.writeInt(friendPic[i][j]);
                    writeString(dos, 10, nickName[i][j]);
                    dos.write(friendState[i][j]);
                }
            }
        }else if(msgType == 0x04){
            //MsgChatText
            MsgChatText msgChatText = (MsgChatText) msg;
            writeString(dos, msgChatText.getTotalLength() - 13, msgChatText.getMsgText());
        }else if(msgType == 0x05){
            //MsgAddFriend
            MsgAddFriend msgAddFriend = (MsgAddFriend) msg;
            dos.writeInt(msgAddFriend.getAdd_ID());
            writeString(dos,msgAddFriend.getTotalLength() -  17,msgAddFriend.getList_name());
        }else if(msgType == 0x55){
            //MsgAddFriendResponse
            MsgAddFriendResponse msgAddFriendResponse = (MsgAddFriendResponse) msg;
            dos.write(msgAddFriendResponse.getState());
        }
        dos.flush();
        byte[] data = bous.toByteArray();
        return data;
    }
    private static void writeHead(DataOutputStream dataOutputStream,MsgHead msgHead)
            throws IOException{
        dataOutputStream.writeInt(msgHead.getTotalLength());
        dataOutputStream.writeByte(msgHead.getType());
        dataOutputStream.writeInt(msgHead.getDestination());
        dataOutputStream.writeInt(msgHead.getSource());
    }
}
