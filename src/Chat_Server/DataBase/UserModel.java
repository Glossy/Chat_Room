package Chat_Server.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wu on 2017/5/8.
 */
public class UserModel {
    DBConnection db;

    public UserModel(DBConnection conn) {
        this.db = conn;
    }

    public UserInfo getUserByID(int ID) throws SQLException {
        ResultSet rs = db.query("SELECT * FROM users where user_id=" + ID);
        if (!rs.next()) return null;
        UserInfo user = new UserInfo(rs);

        //Get Friend List
        CollectionModel collectionModel = new CollectionModel(db);
        List<CollectionInfo> coll = collectionModel.getCollectionsByID(ID);
        CollectionInfo collection;
        List<UserInfo> memberlist;
        UserInfo member;

        int collectionCount = coll.size();
        int memberCount = 0;
        user.setCollectionCount((byte) collectionCount);

        String[] ListName = new String[collectionCount];
        byte[] friendCount = new byte[collectionCount];//
        int friendID[][] = new int[collectionCount][];//
        int friendpic[][] = new int[collectionCount][];//
        String friendName[][] = new String[collectionCount][];//

        for (int j = 0; j < coll.size(); j++) {
            try {
                collection = coll.get(j);
                ListName[j] = collection.getName();
                memberlist = collection.getMembers();

                memberCount = memberlist.size();
                friendCount[j] = (byte) memberCount;

                friendID[j] = new int[memberCount];
                friendName[j] = new String[memberCount];
                friendpic[j] = new int[memberCount];

                for (int i = 0; i < memberlist.size(); i++) {
                    member = memberlist.get(i);
                    friendID[j][i] = member.getIDNum();
                    friendName[j][i] = member.getNickName();
                    friendpic[j][i] = member.getAvatar();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //set friend list
        user.setFriendName(friendName);
        user.setListName(ListName);
        user.setFriendCount(friendCount);
        user.setFriendID(friendID);
        user.setFriendPic(friendpic);

        rs.close();
        return user;
    }

    /**
     * 查询用户名和密码是否匹配
     * @param id
     * @param passwd
     * @return false 不匹配
     * @return true 匹配
     * @throws SQLException
     */
    public boolean userAuthorization(int id, String passwd) throws SQLException {
        ResultSet rs = db.query(String.format("SELECT * FROM users WHERE user_id=%d AND password='%s'", id , passwd));
        if (!rs.next()) return false;
        rs.close();
        return true;
    }

    /**
     * 查询是否为用户好友
     * @param target
     * @param id
     * @return
     * @throws SQLException
     */
    public boolean isFriendsOfUser(int target, int id) throws SQLException {
        String sql = String.format(
                "SELECT\n" +
                        "	*\n" +
                        "FROM\n" +
                        "	users\n" +
                        "WHERE\n" +
                        "	user_id = %d\n" +
                        "AND users.user_id IN (\n" +
                        "	SELECT\n" +
                        "		user_id\n" +
                        "	FROM\n" +
                        "		collection_entry\n" +
                        "	WHERE\n" +
                        "		collection_id IN (\n" +
                        "			SELECT\n" +
                        "				collection_id\n" +
                        "			FROM\n" +
                        "				collection\n" +
                        "			WHERE\n" +
                        "				user_id = %d\n" +
                        "		)\n" +
                        ")", target, id);
        ResultSet rs = db.query(sql);
        return rs.next();
    }

    /**
     * 返回UserInfo的List
     * @param coll_id
     * @return
     * @throws SQLException
     */
    public List<UserInfo> getUsersInCollection(int coll_id) throws SQLException {
        ResultSet rs = db.query("SELECT * FROM users WHERE user_id IN (SELECT user_id FROM collection_entry WHERE collection_id = " + coll_id + ")");
        ArrayList<UserInfo> res = new ArrayList<>();
        while (rs.next()) {
            res.add(new UserInfo(rs));
        }
        rs.close();
        return res;
    }

    /**
     * 创建用户
     * @param passwd
     * @param nickname
     * @param avatar
     * @return
     * @throws SQLException
     */
    public UserInfo createUser(String passwd, String nickname, int avatar) throws SQLException {
        String sql = String.format("INSERT INTO users (nickname, password, avatar) VALUES ('%s', '%s', %d)", nickname, passwd, avatar);
        int res = db.insertAndGet(sql);
        return getUserByID(res);
    }

    /**
     * 删除用户
     * @param id
     * @return
     * @throws SQLException
     */
    public int removeUser(int id) throws SQLException {
        String sql = String.format("DELETE FROM users WHERE user_id=%d", id);
        int res = db.update(sql);
        return res;
    }

    /**
     * 加好友
     * @param addID
     * @param ownID
     * @param listName
     * @return
     * @throws Exception
     */
    public int addFriend(int addID, int ownID, String listName) throws Exception {
        //check add_jk
        UserInfo dest = getUserByID(addID);
        if (dest == null) {
            //不存在这个人
            return 1;
        }
        CollectionModel collectionModel = new CollectionModel(db);
        CollectionInfo collection = collectionModel.getCollectionByNameAndOwner(listName, ownID);
        if (collection == null) {
            collection = collectionModel.createCollection(ownID, listName);
        } else if (isFriendsOfUser(addID, ownID)) {//已是好友
            return 2;
        }
        collectionModel.addUserToCollection(addID, collection.getId());
        return 0;
    }
}
