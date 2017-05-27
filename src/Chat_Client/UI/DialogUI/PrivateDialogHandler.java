package Chat_Client.UI.DialogUI;

import Chat_Client.DataBase.FigureProperty;
import Chat_Client.DataBase.GroupDialogDataBase;
import Chat_Client.DataBase.PrivateDialogDataBase;
import Chat_Client.DataBase.UserInfo;

import java.io.IOException;

/**
 * Created by Wu on 2017/5/6.
 * 此类处理私聊对话框的相关功能
 * 1.显示消息
 * 2.发送消息
 * 3.向数据库中注册会话窗口
 * 4.向数据库中删除会话窗口
 */
public class PrivateDialogHandler {
    /**
	 * 此方法用来显示消息
	 * @param from 发送者ID号
	 * @param msg 文本内容
	 */
    public static void showMessage(int from, String msg) throws IOException {
        /*
		 * 找到是否打开了对应的对话UI
		 */
        if(PrivateDialogDataBase.dialogDB.containsKey(String.valueOf(from))){
			/*
			 * 有的话
			 */
            PrivateDialogUI dialog = PrivateDialogDataBase.dialogDB.get(String.valueOf(from));
            dialog.ShowMsg(msg);
            //dialog.LetsShack();
        }
        else{
			/*
			 * 没有的话 A new Msg
			 */
            //Figures.list.Hav_Mem_Msg(from);
			/*
			 * 直接弹出窗口
			 */
            UserInfo user = FigureProperty.list.findUserByID(from);
            //System.out.println("New MSG From"+user.getNickName());
            PrivateDialogUI dialog =  new PrivateDialogUI(user.getNickName(),user.getPic(), from);
            RegisterDialog(from, dialog);
            dialog.ShowMsg(msg);
            dialog.Shack();
        }
    }
    /**
	 * 这个方法用来发送消息
	 * @param to 发送给的人的ID号
	 * @param msg 发送的信息
	 * @param cc 总服务Chat_Client
	 */
    public static void SendMessage(int to,String msg){
		// 发送信息
        try {
            FigureProperty.cc.sendMsg(to, msg);
            System.out.println("发送消息成功");
        } catch (IOException e) {
            System.out.println("发送消息失败");
            e.printStackTrace();
        }
    }
    //向数据库中注册PrivateDialogUI
    public static void RegisterDialog(int IDNum,PrivateDialogUI dialog){
        PrivateDialogDataBase.dialogDB.put(String.valueOf(IDNum), dialog);
    }

    //从数据库中删除PrivateDialogUI
    public static void DeleteDialog(int groupID){
        GroupDialogDataBase.dialogDB.remove(String.valueOf(groupID));
    }

}
