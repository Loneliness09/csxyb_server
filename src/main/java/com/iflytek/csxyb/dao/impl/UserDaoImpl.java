package com.iflytek.csxyb.dao.impl;

import com.iflytek.csxyb.dao.UserDao;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.entity.UserType;
import com.iflytek.csxyb.utils.DBCP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {
    @Override
    public User login(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User resUser = null;
        try {
            //数据库连接
            conn = DBCP.getConnection();
            ps = conn.prepareStatement("select * from user where loginText=? and password=?");
            ps.setString(1, user.getLoginText());
            ps.setString(2, user.getPassword());
            rs = ps.executeQuery();
            if (rs.next()) {
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
                resUser = new User(id, name, loginName, pwd,  phone,  email,  wx, type, avatar, remark, following, fans);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //注意!!!一定要关闭资源
            DBCP.release(conn, ps, rs);
        }
        return resUser;
    }
}
