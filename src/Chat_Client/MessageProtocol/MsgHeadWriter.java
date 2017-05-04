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
            MsgGroupList

        }else if(msgType == 0x04){
            MsgChatText
        }else if(msgType == 0x05){
            MsgAddFriend
        }else if(msgType == 0x55){
            MsgAddFriendResponse
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
