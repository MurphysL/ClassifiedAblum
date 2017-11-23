package conn;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnUtil {
    private static Connection connection = null;
    private static final String URL = "jdbc:mysql://localhost:3306/ablum?useUnicode=true&characterEncoding=utf8&useSSL=false&autoReconnect=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "yq578797";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private ConnUtil() {

    }


    /**
     * 获取数据库连接
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);//加载数据库驱动
        if (connection == null) {
            synchronized (ConnUtil.class) {
                if (connection == null) {
                    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                }
            }
        }
        return connection;
    }

    /**
     * 关闭数据库连接
     *
     * @throws SQLException
     */
    public static void closeConn() throws SQLException {
        if (connection != null)
            connection.close();
    }

}
