package dao;

import conn.ConnUtil;
import vo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final String TABLE_NAME = " user ";
    private static final String INSERT_FIELDS = " username, email, password, type ";
    private static final String QUERY_FIELDS = " uid, username, email, password, type ";

    /**
     * 增加用户
     * @param user
     */
    public void insertUser(User user) {
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = ConnUtil.getConnection();
            String sql = "INSERT INTO" + TABLE_NAME + "(" + INSERT_FIELDS + ")" + "VALUES(?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getType());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 全部用户信息
     *
     * @return 用户列表
     */
    public List<User> selectUserList() {
        Connection connection;
        ArrayList<User> arrayList = null;

        try {
            connection = ConnUtil.getConnection();
            String sql = "SELECT" + QUERY_FIELDS + "FROM" + TABLE_NAME ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            arrayList = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    User user = new User();
                    arrayList.add(user);
                    user.setUid(resultSet.getInt("uid"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setType(resultSet.getString("type"));
                }

                resultSet.close();
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }


    /**
     * 查询用户
     *
     * @param uid
     * @return uid 对应用户信息
     */
    public User selectUser(int uid) {
        Connection connection;
        User user = new User();
        try {
            connection = ConnUtil.getConnection();
            String sql = "SELECT" + QUERY_FIELDS + "FROM" + TABLE_NAME + "WHERE uid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, uid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null) {
                while (resultSet.next()) {
                    user.setUid(resultSet.getInt("uid"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setType(resultSet.getString("type"));
                    user.setPassword(resultSet.getString("password"));
                }
                resultSet.close();
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 查询用户
     *
     * @param email
     * @return uid 对应用户信息
     */
    public User selectUser(String email) {
        Connection connection;
        User user = new User();
        try {
            connection = ConnUtil.getConnection();
            String sql = "SELECT" + QUERY_FIELDS + "FROM" + TABLE_NAME + "WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null) {
                while (resultSet.next()) {
                    user.setUsername(resultSet.getString("username"));
                    user.setUid(resultSet.getInt("uid"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setType(resultSet.getString("type"));
                }
                resultSet.close();
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
