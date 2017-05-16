package Chat_Server.MessageProtocol;

import Chat_Server.DataBase.FigureProperty;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Wu on 2017/5/8.
 */
public class MsgLoginResponse extends MsgHead{
    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    private byte state;

    public MsgLoginResponse() {}
    public MsgLoginResponse(byte checkmsg) {
        setTotalLen(14);
        setType((byte)0x22);
        setDestination(FigureProperty.LoginID);
        setSource(FigureProperty.ServerID);
        setState(checkmsg);
    }
    public byte[] packMessage() throws IOException {
        ByteArrayOutputStream bous = new ByteArrayOutputStream();
        DataOutputStream dous = new DataOutputStream(bous);
        packMessageHead(dous);
        dous.write(getState());
        dous.flush();
        byte[] data = bous.toByteArray();
        return data;
    }

}
