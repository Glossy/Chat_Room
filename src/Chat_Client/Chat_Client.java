package Chat_Client;

import Chat_Client.MessageProtocol.MsgHead;
import Chat_Client.MessageProtocol.MsgHeadWriter;
import Chat_Client.MessageProtocol.MsgParser;
import Chat_Client.MessageProtocol.MsgRegister;
import Chat_Client.UI.LoginUI.LoginUI;

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
            msgRegister.setDestination(Figures.ServerJK); // 服务器的JK号
            msgRegister.setSource(Figures.LoginJK);
            msgRegister.setNickName(NickName);
            msgRegister.setPassWord(PassWord);

            // 打包MsgReg
            byte[] sendMsg = MsgHeadWriter.packMessage(msgRegister);
            outputStream.write(sendMsg);

            // 接收服务器的反馈信息
            byte[] data = receiveMessage();

            // 将数组转换为类
            MsgHead recMsg = MsgParser.parseMsg(data);

        }catch (IOException e){
            e.printStackTrace();
        }
    }










    public static void main(String args[]){

    }
}