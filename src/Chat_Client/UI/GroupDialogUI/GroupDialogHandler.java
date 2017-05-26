package Chat_Client.UI.GroupDialogUI;

import Chat_Client.DataBase.FigureProperty;
import Chat_Client.DataBase.GroupDialogDataBase;
import Chat_Client.DataBase.GroupListInfo;
import Chat_Client.DataBase.UserInfo;

import java.io.IOException;

/**
 * 此类用于对群聊对话框的处理
 * Created by Wu on 2017/5/16.
 */
public class GroupDialogHandler {
    public static void showMessage(int from,int groupID, String msg) throws IOException {
        //获取from的Nickname
        String name = FigureProperty.groupList.findNameByID(from);
        /*
		 * 找到是否打开了对应的对话UI
		 */
        if(GroupDialogDataBase.dialogDB.containsKey(String.valueOf(groupID))){
			/*
			 * 有的话
			 */
			GroupDialogUI dialog = GroupDialogDataBase.dialogDB.get(String.valueOf(groupID));
            dialog.ShowMsg(name,msg);
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
            GroupListInfo group = FigureProperty.groupList.findGroupByID(groupID);
            //System.out.println("New MSG From"+user.getNickName());
            GroupDialogUI dialog =  new GroupDialogUI(group.getGGroupName(),groupID);
            RegisterDialog(groupID, dialog);
            dialog.ShowMsg(name, msg);
            dialog.Shack();
        }
    }
    /**
     * 这个方法用来发送消息
     * @param msg 发送的信息
     */
    public static void SendMessage(int groupID,String msg){
        // 发送信息
        try {
            FigureProperty.cc.sendGroupMsg(groupID, msg);
            System.out.println("发送消息成功");
        } catch (IOException e) {
            System.out.println("发送消息失败");
            e.printStackTrace();
        }
    }

    //向数据库中注册PrivateDialogUI
    public static void RegisterDialog(int groupID,GroupDialogUI dialog){
        GroupDialogDataBase.dialogDB.put(String.valueOf(groupID), dialog);
    }

    //从数据库中删除PrivateDialogUI
    public static void DeleteDialog(int groupID){
        GroupDialogDataBase.dialogDB.remove(String.valueOf(groupID));
    }
}
