package com.iflytek.csxyb.dao.impl;

import com.iflytek.csxyb.dao.UserDao;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.entity.UserType;
import com.iflytek.csxyb.utils.DBCP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public List<User> selectAll(int pageNum, int pageSize, UserType type) {
        List<User> resLst = new ArrayList<>();
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createSelectAllPreparedStatement(conn, pageNum, pageSize, type);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resLst.add(mapRowToUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLst;
    }

    private PreparedStatement createSelectAllPreparedStatement(Connection conn, int pageNum, int pageSize, UserType type) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from user where type!='root' and type=? limit ?, ?");
        ps.setString(1, type.toString());
        ps.setInt(2, (pageNum - 1) * pageSize);
        ps.setInt(3, pageSize);
        return ps;
    }

    @Override
    public List<User> selectByName(String userName, int pageNum, int pageSize, UserType type) {
        List<User> resLst = new ArrayList<>();
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createSelectByNamePreparedStatement(conn, userName, pageNum, pageSize, type);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resLst.add(mapRowToUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLst;
    }

    private PreparedStatement createSelectByNamePreparedStatement(Connection conn, String userName, int pageNum, int pageSize, UserType type) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from user where userName like ? and type!='root' and type=? limit ?, ?");
        ps.setString(1, "%" + userName + "%");
        ps.setString(2, type.toString());
        ps.setInt(3, (pageNum - 1) * pageSize);
        ps.setInt(4, pageSize);
        return ps;
    }

    @Override
    public int getTotalSizeAll(UserType type) {
        int totalSize = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createGetTotalSizeAllPreparedStatement(conn, type);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalSize = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSize;
    }

    private PreparedStatement createGetTotalSizeAllPreparedStatement(Connection conn, UserType type) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select count(userId) from user where type!='root' and type=?");
        ps.setString(1, type.toString());
        return ps;
    }

    @Override
    public int getTotalSizeByName(String userName, UserType type) {
        int totalSize = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createGetTotalSizeByNamePreparedStatement(conn, userName, type);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalSize = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSize;
    }
    private PreparedStatement createGetTotalSizeByNamePreparedStatement(Connection conn, String userName, UserType type) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select count(userId) from user where userName like ? and type!='root' and type=?");
        ps.setString(1, "%" + userName + "%");
        ps.setString(2, type.toString());
        return ps;
    }

    @Override
    public User loginByText(User user) {
        User resUser = null;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createLoginByTextPreparedStatement(conn, user);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                resUser = mapRowToUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resUser;
    }



    private PreparedStatement createLoginByTextPreparedStatement(Connection conn, User user) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from user where loginText=? and password=?");
        ps.setString(1, user.getLoginText());
        ps.setString(2, user.getPassword());
        return ps;
    }

    public static User mapRowToUser(ResultSet rs) throws SQLException {
        if (rs == null) {
            return null;
        }
        int id = rs.getInt("userId");
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
        int status = rs.getInt("status");
        return new User(id, name, loginName, pwd, phone, email, wx, type, avatar, remark, following, fans, status);
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
        PreparedStatement ps = conn.prepareStatement("INSERT INTO user (userName, loginText, password, phoneNumber, email, type) VALUES (?, ?, ?, ?, ?, ?)");
        ps.setString(1, user.getUserName());
        ps.setString(2, user.getLoginText());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getPhoneNumber());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getType().name());
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
        PreparedStatement ps = conn.prepareStatement("UPDATE user SET userName=?, loginText=?, password=?, phoneNumber=?, email=?, wx=?, type=?, userTxImg=?, geXin=? WHERE userId=?");
        ps.setString(1, user.getUserName());
        ps.setString(2, user.getLoginText());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getPhoneNumber());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getWx());
        ps.setString(7, user.getType().name());
        ps.setString(8, user.getAvatar());
        ps.setString(9, user.getRemark());
        ps.setInt(10, user.getUserId());
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
        PreparedStatement ps1 = conn.prepareStatement("DELETE FROM goods WHERE userId=?");
        ps1.setInt(1, user.getUserId());
        ps1.executeUpdate();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM user WHERE userId=?");
        ps.setInt(1, user.getUserId());
        return ps;
    }

    @Override
    public int updateStatus(User user) {
        int affectedRows = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createUpdateStatusPreparedStatement(conn, user)) {
            affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }
    private PreparedStatement createUpdateStatusPreparedStatement(Connection conn, User user) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE user SET status=? WHERE userId=?");
        ps.setInt(1, (user.getStatus() == 1 ? 0 : 1));
        ps.setInt(2, user.getUserId());
        return ps;
    }

    @Override
    public int updateStatus(User user, int userStatus) {
        int affectedRows = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createUpdateStatusPreparedStatement(conn, user, userStatus)) {
            affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }
    private PreparedStatement createUpdateStatusPreparedStatement(Connection conn, User user, int userStatus) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE user SET status=? WHERE userId=?");
        ps.setInt(1, userStatus);
        ps.setInt(2, user.getUserId());
        return ps;
    }

    @Override
    public UserType findUserType(User user) {
        UserType userType = null;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createFindUserTypePreparedStatement(conn, user);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                userType = UserType.valueOf(rs.getString("type"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userType;
    }

    private PreparedStatement createFindUserTypePreparedStatement(Connection conn, User user) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT type FROM user WHERE userId=?");
        ps.setInt(1, user.getUserId());
        return ps;
    }

    @Override
    public User findUserByLoginText(String loginText) {
        User user = null;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createFindUserByLoginTextPreparedStatement(conn, loginText);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                user = mapRowToUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private PreparedStatement createFindUserByLoginTextPreparedStatement(Connection conn, String loginText) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE loginText=?");
        ps.setString(1, loginText);
        return ps;
    }

    @Override
    public List<User> selectAll(int pageNum, int pageSize) {
        return null;
    }

}
