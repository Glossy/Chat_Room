package Chat_Server.MessageProtocol;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Wu on 2017/5/8.
 */
public class MsgAddFriendResponse extends MsgHead {
    private byte state;

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
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
