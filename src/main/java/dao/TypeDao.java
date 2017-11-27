package dao;

import conn.ConnUtil;
import vo.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TypeDao {
    private static final String TABLE_NAME = "type";
    private static final String INSERT_FILEDS = " tname, updateDate, label ";
    private static final String QUERY_FILEDS = " tid, tname, updateDate, label ";

    public void insertType(Type type){
        String sql = "INSERT " + TABLE_NAME + "(" + INSERT_FILEDS + ")" + " VALUE " + "(?, ?, ?);";
        try {
            Connection connection = ConnUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, type.getTname());
            ps.setString(2, type.getUpdateDate());
            ps.setString(3, type.getLabel());
            ps.executeUpdate();
            ps.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Type queryTypeByName(String tname){
        String sql ="SELECT " + QUERY_FILEDS + " FROM " + TABLE_NAME + "WHERE tname = ?";
        Type type = new Type();
        try {
            Connection connection = ConnUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tname);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet != null){
                while (resultSet.next()){
                    type.setTid(resultSet.getInt("tid"));
                    type.setTname(resultSet.getString("tname"));
                    type.setUpdateDate(resultSet.getString("updateDate"));
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return type;
    }

    public List<Type> queryAllType(){
        String sql = "SELECT * From " + TABLE_NAME ;
        ArrayList<Type> types = new ArrayList<>();
        try {
            Connection connection = ConnUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet != null){
                while (resultSet.next()){
                    Type type = new Type();
                    type.setTid(resultSet.getInt("tid"));
                    type.setTname(resultSet.getString("tname"));
                    type.setUpdateDate(resultSet.getString("updateDate"));
                    type.setLabel(resultSet.getString("label"));
                    types.add(type);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return types;
    }

    public void deleteTypeByTname(String tname){
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE tname = ?";
        try {
            PreparedStatement preparedStatement = ConnUtil.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, tname);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
