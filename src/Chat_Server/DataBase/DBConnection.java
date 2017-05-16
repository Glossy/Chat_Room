package Chat_Server.DataBase;

import java.sql.*;

/**
 * Created by Wu on 2017/5/8.
 * 此类为与数据库连接的抽象类
 */
public abstract class DBConnection {
    protected Connection conn;

    public static DBConnection getInstance(){
        return new ConnectionImplent();
    }

    public void close() throws SQLException {
        conn.close();
    }

    /**
     * 此方法用于得到执行查询语句后的结果集
     * @param sql 查询语句
     * @return
     * @throws SQLException
     */
    public ResultSet query (String sql) throws SQLException {
        Statement stmt = conn.createStatement();//创建Statement对象查询sql
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }

    /**
     * 更新数据库(INSERT, UPDATE, or DELETE statement or an SQL statement that returns nothing, such as an SQL DDL statement.)
     * @param sql
     * @return 数据操作语言（DML）的行数
     * @return 0 声明语句statment
     * @throws SQLException
     */
    public int update(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        int res = stmt.executeUpdate(sql);
//        conn.commit();
        return res;
    }

    /**
     * 插入并返回id
     * @param sql
     * @return
     * @throws SQLException
     */
    public int insertAndGet(String sql) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //动态查询，生成可检索的键值
        pstmt.executeUpdate();  //执行语句
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {//将cursor指向第一行
            int id = rs.getInt(1); //检索并返回当前行中列值为1的值
            return id;
        }
        return -1;
    }
}
