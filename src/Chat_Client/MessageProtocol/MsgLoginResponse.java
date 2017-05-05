package Chat_Client.MessageProtocol;

/**
 * Created by Wu on 2017/5/4.
 * 此消息体为登陆状态返回
 */
public class MsgLoginResponse extends MsgHead {
        private byte state;
        public void setState(byte state){
            this.state = state;
        }
        public byte getState(){return state;}
}
