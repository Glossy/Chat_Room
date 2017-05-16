package Chat_Server.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Wu on 2017/5/14.
 */
public class CollectionInfo {
    private int ownerID;
    private List<UserInfo> members;
    private String name;
    private int id;     //此表的主键id

    /**
     * CollectionInfo
     * 从collection表中读取的数据构造info对象
     * @param rs
     * @throws SQLException
     */
    CollectionInfo(ResultSet rs) throws SQLException {
        ownerID = rs.getInt("user_id");
        name = rs.getString("name");
        id = rs.getInt("collection_id");
    }

    /**
     * getMembers
     * 获取一个collection中的对象
     * @return List<UserInfo>
     * @throws Exception
     */
    public List<UserInfo> getMembers() throws Exception {
        if (members == null) {
            DBConnection conn = DBConnection.getInstance();
            UserModel userModel = new UserModel(conn);
            members = userModel.getUsersInCollection(id);
            conn.close();
        }
        return members;
    }

    /**
     * 输出测试
     */
    public String toString() {
        return String.format("Collection: %s, id: %d, ownerJK: %d", name, id, ownerID);
    }


    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
