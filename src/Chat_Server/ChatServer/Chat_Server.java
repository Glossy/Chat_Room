package Chat_Server.ChatServer;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Wu on 2017/4/27.
 * 服务器类
 */
public class Chat_Server extends JFrame{
    /**
     * 程序入口
     */
    public static void main(String args[]){
        Chat_Server cs = new Chat_Server();
        cs.setupServer(9090);
    }

    private ServerSocket server;
    public static JFrame jFrame;

    public Chat_Server(){
        jFrame = this;

    }

    public void setupServer(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("服务器搭建成功");
            while (true) {
                Socket client = server.accept();
                System.out.println("Incoming" + client.getRemoteSocketAddress());
                ServerThread st = new ServerThread(client);
                st.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
