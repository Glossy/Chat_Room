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
    }
}
