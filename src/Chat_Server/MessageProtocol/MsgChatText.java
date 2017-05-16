package Chat_Server.MessageProtocol;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Wu on 2017/5/14.
 */
public class MsgChatText extends MsgHead {
    private String msgText;

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }
    public MsgChatText() {}
    public MsgChatText(int from, int target, String text) {
        int totalLen = 13;
        byte msgType = 0x04;
        byte[] data = text.getBytes();

        totalLen += data.length;

        setTotalLen(totalLen);
        setType(msgType);
        setDestination(target);
        setSource(from);
        setMsgText(text);
    }
    @Override
    public byte[] packMessage() throws IOException {
        ByteArrayOutputStream bous = new ByteArrayOutputStream();
        DataOutputStream dous = new DataOutputStream(bous);
        packMessageHead(dous);
        writeString(dous, getTotalLen() - 13, getMsgText());
        dous.flush();
        byte[] data = bous.toByteArray();
        return data;
    }
}
