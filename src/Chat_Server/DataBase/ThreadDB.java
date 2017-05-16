package Chat_Server.DataBase;

import Chat_Server.ChatServer.ServerThread;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wu on 2017/5/14.
 * 用来保存服务器线程的数据库
 * 利用Hash Map来保存
 * 只有在登陆后才会记载入该数据库
 * 两个静态方法
 * 1.将登陆的客户对应的线程保存到线程数据库
 * 2.将下线的客户对应的线程从数据库中删除
 */
public class ThreadDB {
    public static Map<String, ServerThread> threadDB = new HashMap<String, ServerThread>();

    // 注册到线程数据库
    public static void RegThread(ServerThread thread) {
        ThreadDB.threadDB.put(String.valueOf(thread.getUserID()), thread);
    }

    // 从线程数据库中间删除
    public static void DelThread(int UserID) {
		System.out.println("Del ID");
        ThreadDB.threadDB.remove(String.valueOf(UserID));
    }
}
