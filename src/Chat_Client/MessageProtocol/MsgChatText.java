package Chat_Client.MessageProtocol;

/**
 * Created by Wu on 2017/5/7.
 * 聊天消息体
 * type 0x04
 */
public class MsgChatText extends MsgHead{
    private String msgText;

    public String getMsgText() {
        return msgText;
    }
    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }
}
