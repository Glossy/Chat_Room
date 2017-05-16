package Chat_Server.MessageProtocol;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Wu on 2017/5/8.
 * 登陆消息体
 */
public class MsgLogin extends MsgHead {
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    @Override
    public byte[] packMessage() throws IOException {
        ByteArrayOutputStream bous = new ByteArrayOutputStream();
        DataOutputStream dous = new DataOutputStream(bous);
        packMessageHead(dous);
        writeString(dous, 10, getPwd());
        dous.flush();
        return bous.toByteArray();
    }

}