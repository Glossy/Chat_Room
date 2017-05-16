package Chat_Server.ChatServer;

import Chat_Server.DataBase.*;
import Chat_Server.MessageProtocol.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Created by Wu on 2017/5/8.
 * 该类为服务器线程，每有一个客户端连接便开启一个线程
 */
public class ServerThread extends Thread {

    public boolean isSending = false;
    private Socket client;
    private OutputStream outputStream;
    private int userID;
    private boolean isOnline = false;       //判断客户端是否在线


    @Override
    public void run(){
        while (!isOnline){//若此客户端不在线
            try{
                processLogin();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(client.getRemoteSocketAddress() + "已断开连接");
                isOnline = false;
                ThreadDB.DelThread(userID);// 从线程数据库中间删除这条信息
                try {
                    client.close();
                }catch (IOException e1){
                    e1.printStackTrace();
                }
                break;
            }
        }
        while (isOnline){//若此客户端在线
            //开始更新列表
            try {
                processChat();
            } catch (Exception e) {
				/*
				 * 客户端断开连接
				 */
                System.out.println(client.getRemoteSocketAddress() + "已断开");
                ThreadDB.DelThread(userID);// 从线程数据库中间删除这条信息
                isOnline = false;
                try {
                    broadcastState();
                } catch (SQLException | IOException e2) {
                    e2.printStackTrace();
                }
                try {
                    client.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            }
        }
    }

    public ServerThread(Socket client){
        this.client = client;
    }

    /**
     * 该方法用于处理未登录前客户端发来的注册或登录消息
     * @throws Exception
     */
    private void processLogin() throws Exception{
        DBConnection dBconnection = DBConnection.getInstance();
        UserModel model = new UserModel(dBconnection);

        outputStream = client.getOutputStream();
        InputStream ins = client.getInputStream();
        DataInputStream dis = new DataInputStream(ins);

        MsgHead msg = MsgHead.readMessageFromStream(dis);

		/*
		 * 下面是针对不同的信息进行处理
		 */

        // 如果传过来的是注册信息
        if (msg.getType() == 0x01) {
            MsgRegister mr = (MsgRegister) msg;

            // 注册用户

            UserInfo newUser = model.createUser(mr.getPwd(), mr.getNickName(), 1);
            int IDNum = newUser.getIDNum();

			/*
			 * 服务器准备返回信息
			 */
            byte state = 0;
            MsgRegisterResponse mrr = new MsgRegisterResponse(IDNum, state);
            mrr.send(outputStream);
        }

        // 如果传过来是登陆信息
        else if (msg.getType() == 0x02) {
            MsgLogin ml = (MsgLogin) msg;

            byte checkmsg;// 用来保存状态信息
            if (ThreadDB.threadDB.containsKey(String.valueOf(ml.getSource()))) {//已经在线了
                checkmsg = 2;
            } else if (model.userAuthorization(ml.getSource(), ml.getPwd())) {// 如果验证了用户存在
                checkmsg = 0;
            } else {
                checkmsg = 1;
            }

			/*
			 * 服务器准备返回信息
			 */
            MsgLoginResponse mlr = new MsgLoginResponse(checkmsg);
            mlr.send(outputStream);

			/*
			 * 如果登陆操作完成， 发送好友列表
			 */
            if (checkmsg == 0) {
                userID = ml.getSource();
                ThreadDB.RegThread(this); // 向线程数据库中注册这个线程
                sendFriendList();
                isOnline = true;// 设置已登录客户端
                broadcastState();
            }

        }
        dBconnection.close();
    }
    /*
     * 该方法用于处理从客户端传过来的信息 (已登录)
     */
    public void processChat() throws Exception {
        InputStream ins = client.getInputStream();
        DataInputStream dis = new DataInputStream(ins);

        int totalLen = dis.readInt();
        byte[] data = new byte[totalLen - 4];
        dis.readFully(data);
        MsgHead msg = MsgParser.parseMsg(data);// 解包该信息

		/*
		 * 下面是针对不同的信息进行处理
		 */

        if (msg.getType() == 0x04) {//如果收到的是发送信息请求
            MsgChatText mct = (MsgChatText) msg;
            int from = mct.getSource();
            int to = mct.getDestination();
            String msgText = mct.getMsgText();
			System.out.println("Sending Test!!");
			System.out.println("From "+from+" To "+to+" Text "+msgText);

            if (!ChatTool.sendMsg(from, to, msgText)) {
                System.out.println("Save On Server");

                //保存到服务器上
                ChatTool.saveOnServer(from, to, msgText);
            }
        } else if (msg.getType() == 0x05) {//如果受到添加好友的请求
            System.out.println("Add friend request");
            MsgAddFriend maf = (MsgAddFriend) msg;
            int own_id = maf.getSource();
            int add_id = maf.getAdd_ID();
            String list_name = maf.getList_name();
            DBConnection conn = DBConnection.getInstance();
            UserModel model = new UserModel(conn);
            int result = model.addFriend(add_id, own_id, list_name);
            System.out.println("Add finish " + result);
            MsgAddFriendResponse mafr = new MsgAddFriendResponse();
            mafr.setSource(FigureProperty.ServerID);
            mafr.setDestination(own_id);
            mafr.setTotalLen(14);
            mafr.setType((byte) 0x55);
            if (result == 0) {//success
                model.addFriend(own_id, add_id, "New Friend");
                //send add_id new list
                mafr.setState((byte) 0);
                //send own_id new list
            } else if (result == 1) {//不存在这个人
                mafr.setState((byte) 1);
            } else if (result == 2) {//如果已经存在了这个人
                mafr.setState((byte) 2);
            } else if (result == 3) {//创建列表失败
                mafr.setState((byte) 3);
            }
            mafr.send(outputStream);

            sendFriendList();

            //send Add_ID Friend list
            //model.addFriend(own_id, add_id, list_name);
            //给被添加者更新列表
            ServerThread st = ThreadDB.threadDB.get(String.valueOf(add_id));
            if (st != null) {
                st.sendFriendList();
            }
            conn.close();
        }

    }

    /**
     * 用于上线后对所有好友的好友列表进行刷新
     * @throws SQLException
     * @throws IOException
     */
    private void broadcastState() throws SQLException, IOException {
        DBConnection conn = DBConnection.getInstance();
        UserModel model = new UserModel(conn);
        UserInfo user = model.getUserByID(userID);
        for (int i = 0; i < user.getCollectionCount(); i++) {
            for (int j = 0; j < user.getFriendCount()[i]; j++) {
                ServerThread st = ThreadDB.threadDB.get(String.valueOf(user.getFriendID()[i][j]));
                if (st != null) {
                    st.sendFriendList();
                }
            }
        }
        conn.close();
    }

    /**
     * 发送好友列表
     *
     * @throws IOException
     * @throws SQLException
     */
    private void sendFriendList() throws IOException, SQLException {
        System.out.println("发送好友列表");

        DBConnection conn = DBConnection.getInstance();
        UserModel model = new UserModel(conn);
        UserInfo user = model.getUserByID(userID);
        MsgFriendList mfl = new MsgFriendList(user);
        mfl.send(outputStream);
        conn.close();
    }

    /*
	 * 该方法用来向用户发送其他人来的信息
	 */
    public void sendMsg(int from, String msg) throws IOException {
        MsgChatText mct = new MsgChatText(from, userID, msg);
        mct.send(outputStream);
    }

    public int getUserID() {
        return userID;
    }
}
