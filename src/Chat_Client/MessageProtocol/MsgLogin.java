package Chat_Client.MessageProtocol;

/**
 * Created by Wu on 2017/5/4.
 * 登陆消息体
 */
public class MsgLogin extends MsgHead {
    private String password;
    public String getPwd() {return password;}
    public void setPwd(String password) {
        this.password = password;
    }
}
