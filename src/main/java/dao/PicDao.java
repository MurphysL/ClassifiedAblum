package dao;

import conn.ConnUtil;
import vo.Pic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PicDao {

    private static final String TABLE_NAME = "pic";
    private static final String INSERT_FILEDS = " tid, uid, username, tname, pname, size, uploadDate, path ";
    private static final String QUERY_FILEDS = " pid, tid, uid, username, tname, pname, size, uploadDate, path ";

    public void insertPic(Pic pic){
        String now = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(System.currentTimeMillis());
        String sql = "INSERT " + TABLE_NAME + "(" + INSERT_FILEDS + ")" + " VALUE " + "(?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            Connection connection = ConnUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pic.getTid());
            ps.setInt(2, pic.getUid());
            ps.setString(3, pic.getUsername());
            ps.setString(4, pic.getTname());
            ps.setString(5, pic.getPname());
            ps.setInt(6, pic.getSize());
            ps.setString(7, now);
            ps.setString(8, pic.getPath());

            ps.executeUpdate();
            ps.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Pic queryPic(int pid){
        String sql ="SELECT " + QUERY_FILEDS + " FROM " + TABLE_NAME + "WHERE pid = ?";
        Pic pic = new Pic();
        try {
            Connection connection = ConnUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pid);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet != null){
                if (resultSet.next()){
                    pic.setPid(resultSet.getInt("pid"));
                    pic.setTid(resultSet.getInt("tid"));
                    pic.setUid(resultSet.getInt("uid"));
                    pic.setUsername(resultSet.getString("username"));
                    pic.setTname(resultSet.getString("tname"));
                    pic.setPname(resultSet.getString("pname"));
                    pic.setSize(resultSet.getInt("size"));
                    pic.setUploadDate(resultSet.getString("uploadDate"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return pic;
    }

    /**
     * 获取 uid 对应用户的所有图片类别
     * @param uid 用户id
     * @return 图片类别及数量
     */
    public Map<String, Integer> queryUserPicType(int uid){
        String sql = "SELECT tname FROM " + TABLE_NAME + " WHERE uid = ?;";
        Map<String , Integer> types = new HashMap<>();
        try {
            Connection connection = ConnUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, uid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet != null){
                while(resultSet.next()){
                    String type = resultSet.getString("tname");
                    if(types.containsKey(type)){
                        int num = types.get(type);
                        num ++;
                        types.put(type, num);
                    }else{
                        types.put(type, 1);
                    }
                }
                resultSet.close();
            }
            preparedStatement.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return types;
    }

    /**
     * 获取用户对应类别下的所有图片
     * @param uid 用户id
     * @param tid 类别id
     * @return 图片列表
     */
    public List<Pic> getPicList(int uid, int tid){
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE uid = ? AND tid = ?";
        ArrayList<Pic> pics = new ArrayList<Pic>();
        try {
            Connection connection = ConnUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.getResultSet();
            if(resultSet != null){
                while(resultSet.next()){
                    Pic pic = new Pic();
                    pic.setPid(resultSet.getInt("pid"));
                    pic.setUid(resultSet.getInt("uid"));
                    pic.setTid(resultSet.getInt("tid"));
                    pic.setUsername(resultSet.getString("username"));
                    pic.setTname(resultSet.getString("tname"));
                    pic.setPname(resultSet.getString("pname"));
                    pic.setSize(resultSet.getInt("size"));
                    pic.setUploadDate(resultSet.getString("uploadDate"));
                    pics.add(pic);
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return pics;
    }

    public List<Pic> getPicList(int uid, String tname){
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE uid = ? AND tname = ?";
        List<Pic> pics = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = ConnUtil.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, uid);
            preparedStatement.setString(2, tname);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet != null){
                while (resultSet.next()){
                    Pic pic = new Pic();
                    pic.setPid(resultSet.getInt("pid"));
                    pic.setUid(resultSet.getInt("uid"));
                    pic.setTid(resultSet.getInt("tid"));
                    pic.setUsername(resultSet.getString("username"));
                    pic.setTname(resultSet.getString("tname"));
                    pic.setPname(resultSet.getString("pname"));
                    pic.setUploadDate(resultSet.getString("uploadDate"));
                    pic.setSize(resultSet.getInt("size"));
                    pic.setPath(resultSet.getString("path"));
                    System.out.println(pic.toString());
                    pics.add(pic);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return pics;
    }
}
