package Chat_Server.DataBase;

import java.sql.DriverManager;

/**
 * Created by Wu on 2017/5/8.
 * 此类为数据库连接的具体实现类
 */
public class ConnectionImplent extends DBConnection {
    public ConnectionImplent() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:db/test.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(-1);
        }
    }
}
