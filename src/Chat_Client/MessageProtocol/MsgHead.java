package Chat_Client.MessageProtocol;

/**
 * Created by Wu on 2017/5/3.
 * MshHead为所有消息类的基类
 */
public class MsgHead {
    private int totalLength;
    private byte type;//消息类型
    private int destination;//目标id号
    private int source;//发送id号

    public int getTotalLength(){return totalLength;}
    public void setTotalLength(int length){
        totalLength = length;
    }

    public byte getType(){return type;}
    public void setType(byte t){type = t;}

    public int getDestination(){return destination;}
    public void setDestination(int des){
        destination = des;
    }

    public int getSource(){return source;}
    public void setSource(int src){
        source = src;
    }

}
