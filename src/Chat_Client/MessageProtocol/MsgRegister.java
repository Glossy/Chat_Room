package Chat_Client.MessageProtocol;

/**
 * Created by Wu on 2017/5/3.
 * MsgReg为注册消息体
 * 固定长度为33
 * type默认值为0x01
 */
public class MsgRegister extends  MsgHead{
    private String nickName;
    private String passWord;

    public String getPassWord(){return passWord;}
    public void setPassWord(String pwd){
        passWord = pwd;
    }

    public String getNickName(){return nickName;}
    public void setNickName(String nickname){
        nickName = nickname;
    }
}
