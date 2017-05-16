package Chat_Server.MessageProtocol;

import Chat_Server.DataBase.FigureProperty;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Wu on 2017/5/8.
 */
public class MsgRegisterResponse extends MsgHead {
    private byte state;

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public MsgRegisterResponse() {}

    public MsgRegisterResponse(int idDest, byte state) {
        setTotalLen(14);
        setType((byte)0x11);
        setDestination(idDest);
        setSource(FigureProperty.ServerID);
        setState(state);
    }
    @Override
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
