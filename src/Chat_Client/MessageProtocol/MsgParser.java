package Chat_Client.MessageProtocol;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Wu on 2017/5/4.
 * 此类用于解构消息体
 */
public class MsgParser {
    /**
	 * 从流中读取固定长度的字节，并将其转换为字符串
	 *
	 * @param dins: 读取的流对象
	 *
	 * @param len: 读取的字节长度
	 *
	 * @return 转换后的字符串
	 */
    private static String readString(DataInputStream dins, int len)
            throws IOException {
        byte[] data = new byte[len];
        dins.readFully(data);//读指定长度的byte数组，若长度不足则阻塞，超时抛出EOFException
        return new String(data).trim();
    }

	/**
     * 此方法用于解构消息
     *
     * @param data 字节信息
     *
     * @return 消息解构后赋值的MsgHead的子类
     */
    public static MsgHead parseMsg(byte[] data)
			throws IOException{
		int totalLength = data.length + 4;// 之前已经读取了4个字节的长度信息Msg中的int totalLength
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
		DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
		byte msgType = dataInputStream.readByte();
        System.out.print("receive MsgType 0x");
        System.out.printf("%02x \n",msgType);
        int destination = dataInputStream.readInt();
		int source = dataInputStream.readInt();
        if (msgType == 0x01) {// 如果是注册信息
            String nickname = readString(dataInputStream, 10);
            String password = readString(dataInputStream, 10);
            MsgRegister msgRegister = new MsgRegister();
            msgRegister.setTotalLength(totalLength);
            msgRegister.setType(msgType);
            msgRegister.setPassWord(password);
            msgRegister.setNickName(nickname);
            msgRegister.setSource(source);
            msgRegister.setDestination(destination);
            return msgRegister;
        }else if(msgType == 0x11){ // 如果是注册返回信息
            byte state = dataInputStream.readByte();
            MsgRegisterResponse msgRegisterResponse = new MsgRegisterResponse();
            msgRegisterResponse.setType(msgType);
            msgRegisterResponse.setTotalLength(totalLength);
            msgRegisterResponse.setState(state);
            msgRegisterResponse.setDestination(destination);
            msgRegisterResponse.setSource(source);
            return msgRegisterResponse;
        }else if(msgType == 0x02){// 如果是登陆信息
            String password = readString(dataInputStream, 10);
            MsgLogin msgLogin = new MsgLogin();
            msgLogin.setTotalLength(totalLength);
            msgLogin.setDestination(destination);
            msgLogin.setSource(source);
            msgLogin.setType(msgType);
            msgLogin.setPwd(password);
            return msgLogin;
        }else if(msgType == 0x22){// 如果是登陆返回信息
            byte state = dataInputStream.readByte();
            MsgLoginResponse msgLoginResponse = new MsgLoginResponse();
            msgLoginResponse.setType(msgType);
            msgLoginResponse.setSource(source);
            msgLoginResponse.setDestination(destination);
            msgLoginResponse.setTotalLength(totalLength);
            msgLoginResponse.setState(state);
            return msgLoginResponse;
        }else if(msgType == 0x03){ // 如果是好友列表
            int i, j;
            String username = readString(dataInputStream, 10);
            int pic = dataInputStream.readInt();
            byte listCount = dataInputStream.readByte();// 读取好友分组个数
            String listName[] = new String[listCount];
            byte friendCount[] = new byte[listCount];//每组好友数

            int friendID[][];
            friendID = new int[listCount][];// 设置第一维长度

            int friendPic[][];
            friendPic = new int[listCount][];

            String nickName[][];
            nickName = new String[listCount][];

            byte friendState[][];
            friendState = new byte[listCount][];

            for (i = 0; i < listCount; i++) {
                listName[i] = readString(dataInputStream, 10);
                friendCount[i] = dataInputStream.readByte();

                friendID[i] = new int[friendCount[i]];// 设置第二维长度
                friendPic[i] = new int[friendCount[i]];
                nickName[i] = new String[friendCount[i]];
                friendState[i] = new byte[friendCount[i]];

                for (j = 0; j < friendCount[i]; j++) {
                    friendID[i][j] = dataInputStream.readInt();
                    friendPic[i][j] = dataInputStream.readInt();
                    nickName[i][j] = readString(dataInputStream, 10);
                    friendState[i][j] = dataInputStream.readByte();
                }
            }

			// 读取结束，写入对象
            MsgFriendList msgFriendList = new MsgFriendList();
            msgFriendList.setUserName(username);
            msgFriendList.setPic(pic);
            msgFriendList.setTotalLength(totalLength);
            msgFriendList.setType(msgType);
            msgFriendList.setDestination(destination);
            msgFriendList.setSource(source);
            msgFriendList.setListCount(listCount);
            msgFriendList.setListName(listName);
            msgFriendList.setFriendNum(friendCount);
            msgFriendList.setFriendID(friendID);
            msgFriendList.setFriendPic(friendPic);
            msgFriendList.setNickName(nickName);
            msgFriendList.setFriendState(friendState);

            return msgFriendList;
        }else if(msgType == 0x04) {  //如果是传送消息
            MsgChatText msgChatText = new MsgChatText();
            String msgText = readString(dataInputStream, totalLength - 13);
            msgChatText.setTotalLength(totalLength);
            msgChatText.setType(msgType);
            msgChatText.setDestination(destination);
            msgChatText.setSource(source);
            msgChatText.setMsgText(msgText);

            return msgChatText;
        }else if(msgType == 0x55){  //添加好友回执
            MsgAddFriendResponse msgAddFriendResponse = new MsgAddFriendResponse();
            byte state = dataInputStream.readByte();
            msgAddFriendResponse.setTotalLength(totalLength);
            msgAddFriendResponse.setType(msgType);
            msgAddFriendResponse.setDestination(destination);
            msgAddFriendResponse.setSource(source);
            msgAddFriendResponse.setState(state);
            return msgAddFriendResponse;
        }
        return null;
    }
}
