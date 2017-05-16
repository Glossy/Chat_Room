package Chat_Client.DataBase;

import Chat_Client.UI.DialogUI.PrivateDialogUI;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wu on 2017/5/6.
 * 用来保存客户端会话窗口的数据库
 * 利用Hash Map来保存
 * 打开的窗口将会记录进该数据库
 */
public class PrivateDialogDataBase {
    public static Map<String, PrivateDialogUI> dialogDB = new HashMap<String, PrivateDialogUI>();
}
