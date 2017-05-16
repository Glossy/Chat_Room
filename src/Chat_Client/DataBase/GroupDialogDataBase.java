package Chat_Client.DataBase;

import Chat_Client.UI.GroupDialogUI.GroupDialogUI;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wu on 2017/5/16.
 * 用来保存客户端会话窗口的数据库
 * 利用Hash Map来保存
 * 打开的窗口将会记录进该数据库
 */
public class GroupDialogDataBase {
    public static Map<String, GroupDialogUI> dialogDB = new HashMap<String, GroupDialogUI>();
}
