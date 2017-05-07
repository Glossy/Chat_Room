package Chat_Client.MessageProtocol;

/**
 * Created by Wu on 2017/5/4.
 * 服务器应答注册状态消息体
 * type 0x11
 */
public class MsgRegisterResponse extends MsgHead {
    private byte state;
    public byte getState(){return state;}
    public void setState(byte state){
        this.state = state;
    }
}