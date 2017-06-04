package Chat_Client;

import Chat_Client.DataBase.FigureProperty;
import Chat_Client.DataBase.FriendListInfo;
import Chat_Client.DataBase.GroupListInfo;
import Chat_Client.MessageProtocol.*;
import Chat_Client.UI.CustomizedUI.CustomizedJOptionPane;
import Chat_Client.UI.CustomizedUI.ListPane;
import Chat_Client.UI.DialogUI.PrivateDialogHandler;
import Chat_Client.UI.GroupDialogUI.GroupDialogHandler;
import Chat_Client.UI.LoginUI.LoginAction;
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

    /**
     * 程序入口
     */
    public static void main(String args[]) throws InterruptedException {
        LoginUI loginUI = new LoginUI();
        LoginAction.LoginJF = loginUI;
    }



   private String serverIP;
   private int port;
   private Socket client_socket;
   private static int IDNum;
   private InputStream inputStream;
   private OutputStream outputStream;
   private int iserverIP;

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

    public Chat_Client(int iserverIP, int port){
        this.iserverIP = iserverIP;
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
                new CustomizedJOptionPane( "ERROR", "与服务器断开连接");
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
            new CustomizedJOptionPane("Error","连接服务器出错");
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
            msgRegister.setDestination(FigureProperty.ServerID); // 服务器的ID号
            msgRegister.setSource(FigureProperty.LoginID);
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
               new CustomizedJOptionPane("Message","注册成功\nID码为" + msgRegisterResponse.getDestination());
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
     * Login 向服务器发送登陆请求
     *
     * @param id
     * @param pwd
     * @return 能否登陆
     * 0 为成功 1为重复登录 3,4,5异常
     */
    public int Login(int id, String pwd){
        try {
            MsgLogin msgLogin = new MsgLogin();
            int len = 23;
            byte type = 0x02;

            // 设置MsgLogin参数
            msgLogin.setTotalLength(len);
            msgLogin.setType(type);
            msgLogin.setDestination(FigureProperty.ServerID);
            msgLogin.setSource(id);
            msgLogin.setPwd(pwd);
            // 打包MsgLogin
            byte[] sendmsg = MsgHeadWriter.packMessage(msgLogin);
            outputStream.write(sendmsg);
            // 接收服务器的反馈信息
            byte[] data = receiveMessage();
            // 将数组转换为类
            MsgHead recMsg = MsgParser.parseMsg(data);
            if (recMsg.getType() != 0x22) {// 不是登陆反馈信息
                System.out.println("通讯协议错误");
                return 5;
            }
            MsgLoginResponse msgLoginResponse = (MsgLoginResponse) recMsg;
            byte resp = msgLoginResponse.getState();
            if (resp == 0) {
                System.out.println("登陆成功");
                IDNum = id;
                return 0;
            } else if (resp == 1) {
                System.out.println("ID号或密码错误");
                return 1;
            } else if(resp == 2){
                System.out.println("这个账号已经登陆");
                return 2;
            } else {
                System.out.println("未知错误");
                return 3;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("与服务器断开连接");
        return 4;
    }
    /**
     * processMsg 接受服务器传来的消息
     *
     * @throws IOException
     */
    public void processMsg() throws IOException {
        byte[] data = receiveMessage();
        // 将数组转换为消息头类
        MsgHead receiveMessage = MsgParser.parseMsg(data);
        byte msgType = receiveMessage.getType();

        // 根据不同的信息头部进行处理
        if (msgType == 0x04) {    //聊天消息
            System.out.println("Process ChatMessage");
            MsgChatText msgChatText =  (MsgChatText) receiveMessage;
            int from = msgChatText.getSource();
            String Msg = msgChatText.getMsgText();
            PrivateDialogHandler.showMessage(from, Msg);//检查是否打开了对话框，没有则打开，打开则置顶
        } else if (msgType == 0x03) {  //更新好友列表
            System.out.println("Refresh list");
            FriendListInfo list = packlist(receiveMessage); //将消息中的属性传入一个新的FriendListInfo中
            FigureProperty.list.Refresh_List(list); //根据新的FriendListInfo刷新好友列表
        } else if (msgType == 0x55) {  //好友申请
            MsgAddFriendResponse msgAddFriendResponse = (MsgAddFriendResponse) receiveMessage;
            byte result = msgAddFriendResponse.getState();
            System.out.println("Add Friend Result " + result);
            if (FigureProperty.addFriendUI != null) {
				System.out.println("To show Result");
                FigureProperty.addFriendUI.showResult(result);//弹窗显示申请结果
            }
        }else if(msgType == 0x66){  //更新群聊列表
            System.out.println("Refresh Group List");
            GroupListInfo list = packGroupList(receiveMessage);
            FigureProperty.groupList.Refresh_List(list);
        }else if(msgType == 0x06) { //群聊消息
            System.out.println("Process Group Chat Message");
            MsgGroupChatText msgGroupChatText = (MsgGroupChatText)receiveMessage;
            int groupID = msgGroupChatText.getDestination();
            int from = msgGroupChatText.getSource();
            String Msg = msgGroupChatText.getMsgText();
            GroupDialogHandler.showMessage(from,groupID,Msg);

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
        System.out.println("Receive Msg TotalLength :" + totalLength);
        byte[] data = new byte[totalLength - 4];
        dataInputStream.readFully(data);
        return data;
    }

    /**
     * 此类将传入的MsgFriendList类中参数输入到FriendListInfo中并返回得到的FriendListInfo
     * @param recMsg
     * @return
     */
    public FriendListInfo packlist(MsgHead recMsg){
        FriendListInfo list = new FriendListInfo();
        MsgFriendList msgFriendList = (MsgFriendList) recMsg;
        list.setNickName(msgFriendList.getUserName());
        list.setIDNum(msgFriendList.getDestination());
        list.setPic(msgFriendList.getPic());
        list.setListCount(msgFriendList.getListCount());
        list.setListName(msgFriendList.getListName());
        list.setFriendCount(msgFriendList.getFriendNum());
        list.setFriendID(msgFriendList.getFriendID());
        list.setFriendPic(msgFriendList.getFriendPic());
        list.setFriendNickName(msgFriendList.getNickName());
        list.setFriendState(msgFriendList.getFriendState());
        return list;
    }
    public GroupListInfo packGroupList(MsgHead recMsg){
        GroupListInfo list = new GroupListInfo();
        MsgGroupList msgGroupList = (MsgGroupList) recMsg;

        list.setGroupCount(msgGroupList.getGroupCount());
        list.setGroupID(msgGroupList.getGroupID());
        list.setGroupName(msgGroupList.getGroupName());
        list.setGrouperCount(msgGroupList.getGrouperCount());
        list.setGrouperID(msgGroupList.getGrouperID());
        list.setGrouperNickName(msgGroupList.getGrouperNickName());
        list.setState(msgGroupList.getGrouperstate());
        list.setIsFriend(msgGroupList.getIsFriend());

        return list;
    }
    /**
     * ListInfo
     * 接收好友列表
     *
     * @return
     * @throws IOException
     */
    public FriendListInfo getlist() throws IOException {
        byte[] data = receiveMessage();
        MsgHead recMsg = MsgParser.parseMsg(data);
        if (recMsg.getType() != 0x03) {
            System.out.println("通讯协议错误");
            System.exit(0);
        }
        return packlist(recMsg);
    }

    public GroupListInfo getGroupList() throws IOException{
        byte[] data = receiveMessage();
        MsgHead recMsg = MsgParser.parseMsg(data);
        if (recMsg.getType() != 0x66) {
            System.out.println("通讯协议错误");
            System.exit(0);
        }
        return packGroupList(recMsg);
    }

    /**
     * 这个方法用于添加好友
     * @param add_id
     * @param list_name
     * @return 0  成功
     * @return 1 查无此人
     * @return 2 已有此人
     * @return 3 创建错误
     * @return 4 通讯协议错误
     * @throws IOException
     */
    public void SendaddFriend(int add_id, String list_name) throws IOException {
        MsgAddFriend msgAddFriend = new MsgAddFriend();
        byte data[] = list_name.getBytes();
        int TotalLen = 17;
        TotalLen += data.length;
        byte type = 0x05;
        msgAddFriend.setTotalLength(TotalLen);
        msgAddFriend.setType(type);
        msgAddFriend.setDestination(FigureProperty.ServerID);
        msgAddFriend.setSource(IDNum);
        msgAddFriend.setAdd_ID(add_id);
        msgAddFriend.setList_name(list_name);
        byte[] sendMsg = MsgHeadWriter.packMessage(msgAddFriend);
        outputStream.write(sendMsg);
        outputStream.flush();
    }

    public void sendAddGroup(int addGroupID) throws IOException{
        MsgAddGroup msgAddGroup = new MsgAddGroup();
    }

    /**
     * sendMsg 发送信息
     *
     * @param to
     * @param Msg
     * @throws IOException
     */
    public void sendMsg(int to, String Msg) throws IOException {
        MsgChatText mct = new MsgChatText();
        byte data[] = Msg.getBytes();
        int TotalLen = 13;
        TotalLen += data.length;
        byte type = 0x04;
        mct.setTotalLength(TotalLen);
        mct.setType(type);
        mct.setDestination(to);
        mct.setSource(IDNum);
        mct.setMsgText(Msg);

        byte[] sendMsg = MsgHeadWriter.packMessage(mct);
        outputStream.write(sendMsg);
        outputStream.flush();
    }

    public void sendGroupMsg(int groupID, String Msg) throws IOException{
        MsgGroupChatText msgGroupChatText = new MsgGroupChatText();
        byte data[] = Msg.getBytes();
        int TotalLen = 13;
        TotalLen += data.length;
        byte type = 0x06;
        msgGroupChatText.setTotalLength(TotalLen);
        msgGroupChatText.setType(type);
        msgGroupChatText.setDestination(groupID);
        msgGroupChatText.setSource(IDNum);
        msgGroupChatText.setMsgText(Msg);

        byte[] sendMsg = MsgHeadWriter.packMessage(msgGroupChatText);
        outputStream.write(sendMsg);
        outputStream.flush();
    }


}