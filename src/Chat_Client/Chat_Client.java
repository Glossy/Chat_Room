package Chat_Client;

import Chat_Client.MessageProtocol.*;
import Chat_Client.UI.CustomizedUI.Customized_JOptionPane;
import Chat_Client.UI.LoginUI.LoginUI;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * ChatClient 该类用于与服务器进行通信
 * Created by Wu on 2017/4/27.
 */
public class Chat_Client extends Thread{
   private String serverIP;
   private int port;
   private Socket client_socket;
   private static int IDnum;
   private InputStream inputStream;
   private OutputStream outputStream;

    /**
     * ChatClient的构造函数
     * @param serverIP
     *            服务器的IP
     * @param port
     *            端口
     */
    public Chat_Client(String serverIP,int port){
        this.serverIP = serverIP;
        this.port = port;
    }
    /**
     * 处理从服务器发来的信息
     */
    public void run(){
        while (true){
            try{
                processMsg();
            }catch (IOException e){
                e.printStackTrace();
                System.out.println("脱离主进程与服务器断开连接");
                new Customized_JOptionPane( "ERROR", "与服务器断开连接");
                System.exit(0);
            }
        }
    }
    /**
     * 连接到服务器
     *
     * @return 是否连接到服务器
     */
    public boolean ConnectServer() {
        try {
            client_socket = new Socket(serverIP,port);
            System.out.println("服务器已连接");
            inputStream = client_socket.getInputStream();
            outputStream = client_socket.getOutputStream();// 获取该连接的输入输出流
            return true;
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("连接服务器出错");
            //弹窗
            return false;
        }
    }
    /**
     * Register 注册用户
     *
     * @param NickName
     *            昵称
     * @param PassWord
     *            密码
     * @return 注册状态
     */
    public boolean Register(String NickName, String PassWord){
        try{
            MsgRegister msgRegister = new MsgRegister();
            int len = 33;// msgRegister长度固定为33
            byte type = 0x01; // MsgReg类型为0x01
            // 设置MsgReg的参数
            msgRegister.setTotalLength(len);
            msgRegister.setType(type);
            msgRegister.setDestination(Figures.ServerJK); // 服务器的ID号
            msgRegister.setSource(Figures.LoginJK);
            msgRegister.setNickName(NickName);
            msgRegister.setPassWord(PassWord);

            // 打包MsgReg
            byte[] sendMsg = MsgHeadWriter.packMessage(msgRegister);
            outputStream.write(sendMsg);

            // 接收服务器的反馈信息
            byte[] data = receiveMessage();

            // 将数组解构为注册消息类
            MsgHead recMsg = MsgParser.parseMsg(data);
            if (recMsg.getType() != 0x11) {// 不是回应注册消息
                System.out.println("通讯协议错误");
                return false;
            }
            MsgRegisterResponse msgRegisterResponse = (MsgRegisterResponse) recMsg;

            if (msgRegisterResponse.getState() == 0){//注册成功
                JOptionPane.showMessageDialog(null, "注册成功\nJK码为" + msgRegisterResponse.getDestination());
                return true;
            }else {//注册失败
                return false;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("与服务器断开连接");
        return false;
    }
    /**
     * processMsg 接受服务器传来的消息
     *
     * @throws IOException
     */
    public void processMsg() throws IOException{
        byte[] data = receiveMessage();
        // 将数组转换为消息头类
        MsgHead receiveMessage = MsgParser.parseMsg(data);
        byte msgType = receiveMessage.getType();

        // 根据不同的信息头部进行处理
        if(msgType == 0x04){    //聊天消息
            MsgChatText msgChatText = new MsgChatText();
        }else if(msgType == 0x03){  //更新好友列表
            System.out.println("Refresh friend list");
        }else if(msgType == 0x55){  //好友申请
            MsgAddFriendResponse msgAddFriendResponse = MsgAddFriendResponse();
        }
    }

    /**
	 * 这个方法用于从输入流中间读取一定长度的信息 信息长度为最前面的一个整数
	 *
	 * @return byte[]读出的长度信息
	 */
    public byte[] receiveMessage()throws IOException{
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int totalLength = dataInputStream.readInt();
        System.out.println("TotalLength :" + totalLength);
        byte[] data = new byte[totalLength - 4];
        dataInputStream.readFully(data);
        return data;
    }









    public static void main(String args[]){

    }
}