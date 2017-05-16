package Chat_Server.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wu on 2017/5/14.
 */
public class CollectionModel {
    private DBConnection connection;
    public CollectionModel(DBConnection conn) {
        connection = conn;
    }

    /**
     * getCollectionsByJK
     * 根据用户的ID号获取collectionInfo 的list
     * @param id ID号
     * @return List<CollectionInfo>
     * @throws SQLException SQL异常
     */
    public List<CollectionInfo> getCollectionsByID(int id) throws SQLException {
        ResultSet rs = connection.query("SELECT * FROM collection where user_id=" + id);
        ArrayList<CollectionInfo> res = new ArrayList<>();
        while (rs.next()) {
            res.add(new CollectionInfo(rs));
        }
        rs.close();
        return res;
    }

    /**
     * addUserToCollection
     * @param id 用户ID号
     * @param coll_id 列表id
     * @return 添加的数目
     * @throws SQLException SQL异常
     */
    public int addUserToCollection(int id, int coll_id) throws SQLException {
        return connection.update(String.format("INSERT INTO collection_entry (user_id, collection_id) VALUES (%d, %d)", id, coll_id));
    }

    /**
     * createCollection
     * @param id 用户ID号
     * @param collName 列表名
     * @return 新建的列表
     * @throws SQLException SQL异常
     */
    public CollectionInfo createCollection(int id, String collName) throws SQLException {
        String sql = String.format("INSERT INTO collection (name, user_id) VALUES ('%s', %d)", collName, id);
        int tid = connection.insertAndGet(sql);
        return getCollection(tid);
    }

    /**
     * getCollection
     * 获取指定的好友列表
     * @param id 列表ID
     * @return 找到的列表。无为null
     * @throws SQLException SQL异常
     */
    public CollectionInfo getCollection(int id) throws SQLException {
        String sql = String.format("SELECT * FROM collection where collection_id=%d", id);
        ResultSet rs = connection.query(sql);
        if (!rs.next()) {
            return null;
        }
        CollectionInfo result = new CollectionInfo(rs);
        rs.close();
        return result;
    }

    public boolean isUserInCollection(int userJK, int collectionId) throws SQLException {
        ResultSet rs = connection.query(String.format("SELECT * FROM collection_entry where user_id=%d AND collection_id=%d", userJK, collectionId));
        return rs.next();
    }

    /**
     *
     * @param name
     * @param jk
     * @return
     * @throws SQLException
     */
    public CollectionInfo getCollectionByNameAndOwner(String name, int jk) throws SQLException {
        String sql = String.format("SELECT * FROM collection where user_id=%d AND name='%s'", jk, name);
        ResultSet rs = connection.query(sql);
        if (!rs.next()) {
            return null;
        }
        CollectionInfo result = new CollectionInfo(rs);
        rs.close();
        return result;
    }

    /**
     * removeCollection
     * @param id
     * @return
     * @throws SQLException
     */
    public int removeCollection(int id) throws SQLException {
        String sql = String.format("DELETE FROM collection WHERE collection_id=%d", id);
        return connection.update(sql);
    }
}
