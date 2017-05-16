package Chat_Server.MessageProtocol;

import java.io.*;

/**
 * Created by Wu on 2017/5/8.
 */
public class MsgRegister extends MsgHead {

    private String nickName;
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public byte[] packMessage() throws IOException {
        ByteArrayOutputStream bous = new ByteArrayOutputStream();
        DataOutputStream dous = new DataOutputStream(bous);
        packMessageHead(dous);
        writeString(dous, 10, getNickName());
        writeString(dous, 10, getPwd());
        dous.flush();
        byte[] data = bous.toByteArray();
        return data;
    }
}

