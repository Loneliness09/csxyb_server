package com.iflytek.csxyb.dao.impl;

import com.iflytek.csxyb.dao.UserDao;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.entity.UserType;
import com.iflytek.csxyb.utils.DBCP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    @Override
    public User select(User user) {
        User resUser = null;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createSelectPreparedStatement(conn, user);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                resUser = mapRowToUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resUser;
    }

    private PreparedStatement createSelectPreparedStatement(Connection conn, User user) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from user where loginText=? and password=?");
        ps.setString(1, user.getLoginText());
        ps.setString(2, user.getPassword());
        return ps;
    }

    private User mapRowToUser(ResultSet rs) throws SQLException {
        String id = rs.getString("userId");
        String name = rs.getString("userName");
        String loginName = rs.getString("loginText");
        String pwd = rs.getString("password");
        String phone = rs.getString("phoneNumber");
        String email = rs.getString("email");
        String wx = rs.getString("wx");
        UserType type = UserType.valueOf(rs.getString("type"));
        String avatar = rs.getString("userTxImg");
        String remark = rs.getString("geXin");
        int following = rs.getInt("GuanZhu");
        int fans = rs.getInt("Fans");
        return new User(id, name, loginName, pwd, phone, email, wx, type, avatar, remark, following, fans);
    }


    @Override
    public int insert(User user) {
        int affectedRows = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createInsertPreparedStatement(conn, user)) {
            affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting user failed, no rows affected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    private PreparedStatement createInsertPreparedStatement(Connection conn, User user) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO user (userId, userName, loginText, password, phoneNumber, email, wx, type, userTxImg, geXin, GuanZhu, Fans) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, user.getUserId());
        ps.setString(2, user.getUserName());
        ps.setString(3, user.getLoginText());
        ps.setString(4, user.getPassword());
        ps.setString(5, user.getPhoneNumber());
        ps.setString(6, user.getEmail());
        ps.setString(7, user.getWx());
        ps.setString(8, user.getType().name());
        ps.setString(9, user.getAvatar());
        ps.setString(10, user.getRemark());
        ps.setInt(11, user.getFollowing());
        ps.setInt(12, user.getFans());
        return ps;
    }

    @Override
    public int update(User user) {
        int affectedRows = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createUpdatePreparedStatement(conn, user)) {
            affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    private PreparedStatement createUpdatePreparedStatement(Connection conn, User user) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE user SET userName=?, loginText=?, password=?, phoneNumber=?, email=?, wx=?, type=?, userTxImg=?, geXin=?, GuanZhu=?, Fans=? WHERE userId=?");
        ps.setString(1, user.getUserName());
        ps.setString(2, user.getLoginText());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getPhoneNumber());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getWx());
        ps.setString(7, user.getType().name());
        ps.setString(8, user.getAvatar());
        ps.setString(9, user.getRemark());
        ps.setInt(10, user.getFollowing());
        ps.setInt(11, user.getFans());
        ps.setString(12, user.getUserId());
        return ps;
    }

    @Override
    public int delete(User user) {
        int affectedRows = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createDeletePreparedStatement(conn, user)) {
            affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    private PreparedStatement createDeletePreparedStatement(Connection conn, User user) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM user WHERE userId=?");
        ps.setString(1, user.getUserId());
        return ps;
    }

}
