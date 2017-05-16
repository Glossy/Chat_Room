package Chat_Server.DataBase;

import Chat_Server.ChatServer.ServerThread;

import java.io.IOException;

/**
 * Created by Wu on 2017/5/15.
 * 这个工具用来实现以下功能
 * 1.向目标JK号发送信息
 * 2.将未发送成功的信息存在服务器
 */
public class ChatTool {
    /*
	 * 这个方法用来向IDNum的用户发送内容为Msg的信息
	 *
	 * @return 是否成功发送
	 */
    public static boolean sendMsg(int from,int to, String msg) {
		/*
		 * Check User Online
		 */
        ServerThread st = ThreadDB.threadDB.get(String.valueOf(to));

        if(st == null ) {
            System.out.println("目标不在线");
            return false;
        }

		/*
		 * 向用户发送信息
		 */
        try {
            st.sendMsg(from, msg);
			System.out.println("Finish Sending");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * 这个方法由于IDNum的用户不在线 保存向IDNum的用户发送内容为Msg的信息
     */
    public static void saveOnServer(int from,int to, String Msg) {

    }
}
