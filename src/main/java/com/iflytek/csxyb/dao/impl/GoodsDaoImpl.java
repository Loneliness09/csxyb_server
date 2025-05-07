package com.iflytek.csxyb.dao.impl;

import com.iflytek.csxyb.dao.GoodsDao;
import com.iflytek.csxyb.entity.Goods;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.utils.DBCP;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsDaoImpl implements GoodsDao {

    @Override
    public List<Goods> selectAll(int pageNum, int pageSize) {
        List<Goods> resLst = new ArrayList<>();
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createSelectAllPreparedStatement(conn, pageNum, pageSize);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resLst.add(mapRowToGoods(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLst;
    }

    private PreparedStatement createSelectAllPreparedStatement(Connection conn, int pageNum, int pageSize) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from Goods limit ?, ?");
        ps.setInt(1, (pageNum - 1) * pageSize);
        ps.setInt(2, pageSize);
        return ps;
    }

    private Goods mapRowToGoods(ResultSet rs) throws SQLException {
        if (rs == null) {
            return null;
        }

        int goodsId = rs.getInt("goodsId");
        int userId = rs.getInt("userId");
        String goodsText = rs.getString("goodsText");
        String goodsImg = rs.getString("goodsImg");
        String goodsTopImg = rs.getString("goodsTopImg");
        String goodsLabel = rs.getString("goodsLable");
        int pinLunNumber = rs.getInt("pinLunNumber");
        Date goodsTime = rs.getDate("goodsTime");
        double goodsPrice = rs.getDouble("goodsPrice");
        String goodsPriceText = rs.getString("goodsPriceText");
        int status = rs.getInt("status");

        return new Goods(goodsId, userId, goodsText, goodsImg, goodsTopImg, goodsLabel, pinLunNumber, goodsTime, goodsPrice, goodsPriceText, status);
    }


    @Override
    public int insert(Goods goods) {
        int affectedRows = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createInsertPreparedStatement(conn, goods)) {
            affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting Goods failed, no rows affected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    private PreparedStatement createInsertPreparedStatement(Connection conn, Goods goods) throws SQLException {
        // 注意：SQL语句中的列名和值占位符应根据实际数据库表结构进行调整
        String sql = "INSERT INTO Goods (userId, goodsText, goodsImg, goodsTopImg, goodsLable, goodsPrice, goodsPriceText) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, goods.getUserId());
        ps.setString(2, goods.getGoodsText());
        ps.setString(3, goods.getGoodsImg());
        ps.setString(4, goods.getGoodsTopImg());
        ps.setString(5, goods.getGoodsLabel());
        ps.setDouble(6, goods.getGoodsPrice());
        ps.setString(7, goods.getGoodsPriceText());

        return ps;
    }


    @Override
    public int update(Goods goods) {
        int affectedRows = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createUpdatePreparedStatement(conn, goods)) {
            affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating Goods failed, no rows affected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    private PreparedStatement createUpdatePreparedStatement(Connection conn, Goods goods) throws SQLException {
        // 注意：SQL语句中的列名和值占位符应根据实际数据库表结构进行调整
        String sql = "UPDATE Goods SET userId=?, goodsText=?, goodsImg=?, goodsTopImg=?, goodsLable=?, goodsPrice=?, goodsPriceText=? WHERE goodsId=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, goods.getUserId());
        ps.setString(2, goods.getGoodsText());
        ps.setString(3, goods.getGoodsImg());
        ps.setString(4, goods.getGoodsTopImg());
        ps.setString(5, goods.getGoodsLabel());
        ps.setDouble(6, goods.getGoodsPrice());
        ps.setString(7, goods.getGoodsPriceText());
        ps.setInt(8, goods.getGoodsId());
        return ps;
    }

    @Override
    public int delete(Goods goods) {
        int affectedRows = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createDeletePreparedStatement(conn, goods)) {
            affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting Goods failed, no rows affected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    private PreparedStatement createDeletePreparedStatement(Connection conn, Goods goods) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM Goods WHERE GoodsId=?");
        ps.setInt(1, goods.getGoodsId());
        return ps;
    }

    @Override
    public List<Goods> selectByName(String name, int pageNum, int pageSize) {
        List<Goods> resLst = new ArrayList<>();
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createSelectByNamePreparedStatement(conn, name, pageNum, pageSize);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resLst.add(mapRowToGoods(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLst;
    }



    private PreparedStatement createSelectByNamePreparedStatement(Connection conn, String name, int pageNum, int pageSize) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from Goods where goodsText like ? limit ?, ?");
        ps.setString(1, "%" + name + "%");
        ps.setInt(2, (pageNum - 1) * pageSize);
        ps.setInt(3, pageSize);
        return ps;
    }

    @Override
    public List<Goods> selectById(int userId, int pageNum, int pageSize) {
        List<Goods> resLst = new ArrayList<>();
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createSelectByIdPreparedStatement(conn, userId, pageNum, pageSize);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resLst.add(mapRowToGoods(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLst;
    }

    private PreparedStatement createSelectByIdPreparedStatement(Connection conn, int userId, int pageNum, int pageSize) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from Goods where userId=? limit ?, ?");
        ps.setInt(1, userId);
        ps.setInt(2, (pageNum - 1) * pageSize);
        ps.setInt(3, pageSize);
        return ps;
    }

    @Override
    public List<Goods> selectByIdAndName(int userId, String name, int pageNum, int pageSize) {
        List<Goods> resLst = new ArrayList<>();
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createSelectByIdAndNamePreparedStatement(conn, userId, name, pageNum, pageSize);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resLst.add(mapRowToGoods(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLst;
    }

    private PreparedStatement createSelectByIdAndNamePreparedStatement(Connection conn, int userId, String name, int pageNum, int pageSize) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from Goods where userId=? and goodsText like ? limit ?, ?");
        ps.setInt(1, userId);
        ps.setString(2, "%" + name + "%");
        ps.setInt(3, (pageNum - 1) * pageSize);
        ps.setInt(4, pageSize);
        return ps;
    }

    @Override
    public List<Goods> selectAllByType(String goodsType, int pageNum, int pageSize) {
        List<Goods> resLst = new ArrayList<>();
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createSelectAllByTypePreparedStatement(conn, goodsType, pageNum, pageSize);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resLst.add(mapRowToGoods(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLst;
    }

    private PreparedStatement createSelectAllByTypePreparedStatement(Connection conn, String goodsType, int pageNum, int pageSize) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from Goods where JSON_CONTAINS(goodsLable, ?) limit ?, ?");
        ps.setString(1, '"' + goodsType + '"');
        ps.setInt(2, (pageNum - 1) * pageSize);
        ps.setInt(3, pageSize);
        return ps;
    }

    @Override
    public List<Goods> selectByNameAndType(String name, String goodsType, int pageNum, int pageSize) {
        List<Goods> resLst = new ArrayList<>();
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createSelectByNameAndTypePreparedStatement(conn, goodsType, name, pageNum, pageSize);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resLst.add(mapRowToGoods(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLst;
    }



    private PreparedStatement createSelectByNameAndTypePreparedStatement(Connection conn, String name, String goodsType, int pageNum, int pageSize) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from Goods where goodsText like ? and JSON_CONTAINS(goodsLable, ?) limit ?, ?");
        ps.setString(1, "%" + name + "%");
        ps.setString(2, '"' + goodsType + '"');
        ps.setInt(3, (pageNum - 1) * pageSize);
        ps.setInt(4, pageSize);
        return ps;
    }

    @Override
    public List<Goods> selectByIdAndType(int userId, String goodsType, int pageNum, int pageSize) {
        List<Goods> resLst = new ArrayList<>();
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createSelectByIdAndTypePreparedStatement(conn, userId, goodsType, pageNum, pageSize);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resLst.add(mapRowToGoods(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLst;
    }

    private PreparedStatement createSelectByIdAndTypePreparedStatement(Connection conn, int userId, String goodsType, int pageNum, int pageSize) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from Goods where userId=? and JSON_CONTAINS(goodsLable, ?) limit ?, ?");
        ps.setInt(1, userId);
        ps.setString(2, '"' + goodsType + '"');
        ps.setInt(3, (pageNum - 1) * pageSize);
        ps.setInt(4, pageSize);
        return ps;
    }
    @Override
    public List<Goods> selectByIdAndNameAndType(int userId, String name, String goodsType, int pageNum, int pageSize) {
        List<Goods> resLst = new ArrayList<>();
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createSelectByIdAndNameAndTypePreparedStatement(conn, userId, goodsType, name, pageNum, pageSize);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resLst.add(mapRowToGoods(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLst;
    }

    private PreparedStatement createSelectByIdAndNameAndTypePreparedStatement(Connection conn, int userId, String goodsType, String name, int pageNum, int pageSize) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from Goods where userId=? and goodsText like ? and JSON_CONTAINS(goodsLable, ?) limit ?, ?");
        ps.setInt(1, userId);
        ps.setString(2, "%" + name + "%");
        ps.setString(3, '"' + goodsType + '"');
        ps.setInt(4, (pageNum - 1) * pageSize);
        ps.setInt(5, pageSize);
        return ps;
    }

    @Override
    public int getTotalSize() {
        int totalSize = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createGetTotalSizePreparedStatement(conn);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                totalSize = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSize;
    }

    private PreparedStatement createGetTotalSizePreparedStatement(Connection conn) throws SQLException {
        return conn.prepareStatement("select count(goodsId) from Goods");
    }

    @Override
    public int getTotalSizeByName(String name) {
        int totalSize = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createGetTotalSizeByNamePreparedStatement(conn, name);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                totalSize = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSize;
    }

    private PreparedStatement createGetTotalSizeByNamePreparedStatement(Connection conn, String name) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select count(goodsId) from Goods where goodsText like ?");
        ps.setString(1, "%" + name + "%");
        return ps;
    }

    @Override
    public int getTotalSizeById(int userId) {
        int totalSize = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createGetTotalSizeByIdPreparedStatement(conn, userId);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                totalSize = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSize;
    }

    private PreparedStatement createGetTotalSizeByIdPreparedStatement(Connection conn, int userId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select count(goodsId) from Goods where userId=?");
        ps.setInt(1, userId);
        return ps;
    }

    @Override
    public int getTotalSizeByIdAndName(int userId, String name) {
        int totalSize = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createGetTotalSizeByIdAndNamePreparedStatement(conn, userId, name);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                totalSize = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSize;
    }

    private PreparedStatement createGetTotalSizeByIdAndNamePreparedStatement(Connection conn, int userId, String name) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select count(goodsId) from Goods where userId=? and goodsText like ?");
        ps.setInt(1, userId);
        ps.setString(2, "%" + name + "%");
        return ps;
    }

    @Override
    public int getTotalSizeByType(String type) {
        int totalSize = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createGetTotalSizeByTypePreparedStatement(conn, type);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                totalSize = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSize;
    }

    private PreparedStatement createGetTotalSizeByTypePreparedStatement(Connection conn, String type) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select count(goodsId) from Goods where JSON_CONTAINS(goodsLable, ?)");
        ps.setString(1, '"' + type + '"');
        return ps;
    }

    @Override
    public int getTotalSizeByNameAndType(String name, String type) {
        int totalSize = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createGetTotalSizeByNameAndTypePreparedStatement(conn, name, type);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                totalSize = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSize;
    }

    private PreparedStatement createGetTotalSizeByNameAndTypePreparedStatement(Connection conn, String name, String type) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select count(goodsId) from Goods where goodsText like ? and JSON_CONTAINS(goodsLable, ?)");
        ps.setString(1, "%" + name + "%");
        ps.setString(2, '"' + type + '"');
        return ps;
    }

    @Override
    public int getTotalSizeByIdAndType(int userId, String type) {
        int totalSize = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createGetTotalSizeByIdAndTypePreparedStatement(conn, userId, type);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                totalSize = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSize;
    }

    private PreparedStatement createGetTotalSizeByIdAndTypePreparedStatement(Connection conn, int userId, String type) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select count(goodsId) from Goods where userId=? and JSON_CONTAINS(goodsLable, ?)");
        ps.setInt(1, userId);
        ps.setString(2, '"' + type + '"');
        return ps;
    }

    @Override
    public int getTotalSizeByIdAndNameAndType(int userId, String name, String type) {
        int totalSize = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createGetTotalSizeByIdAndNameAndTypePreparedStatement(conn, userId, name, type);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                totalSize = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSize;
    }

    private PreparedStatement createGetTotalSizeByIdAndNameAndTypePreparedStatement(Connection conn, int userId, String name, String type) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select count(goodsId) from Goods where userId=? and goodsText like ? and JSON_CONTAINS(goodsLable, ?)");
        ps.setInt(1, userId);
        ps.setString(2, "%" + name + "%");
        ps.setString(3, '"' + type + '"');
        return ps;
    }

    @Override
    public int updateStatus(Goods goods) {
        int affectedRows = 0;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createUpdateStatusPreparedStatement(conn, goods)) {
            affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating Goods failed, no rows affected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    private PreparedStatement createUpdateStatusPreparedStatement(Connection conn, Goods goods) throws SQLException {
        // 注意：SQL语句中的列名和值占位符应根据实际数据库表结构进行调整
        String sql = "UPDATE Goods SET status=? WHERE goodsId=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, (goods.getStatus() == 0 ? 1 : 0));
        ps.setInt(2, goods.getGoodsId());
        return ps;
    }

    @Override
    public User findGoodsOwner(Goods goods) {
        User resUser = null;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createFindGoodsOwnerPreparedStatement(conn, goods);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                resUser = UserDaoImpl.mapRowToUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resUser;
    }

    private PreparedStatement createFindGoodsOwnerPreparedStatement(Connection conn, Goods goods) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from user where userId=?");
        ps.setInt(1, goods.getUserId());
        return ps;
    }

    @Override
    public Goods findGoods(int goodsId) {
        Goods resGoods = null;
        try (Connection conn = DBCP.getConnection();
             PreparedStatement ps = createFindGoodsPreparedStatement(conn, goodsId);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                resGoods = mapRowToGoods(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resGoods;
    }

    private PreparedStatement createFindGoodsPreparedStatement(Connection conn, int goodsId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select * from goods where goodsId=?");
        ps.setInt(1, goodsId);
        return ps;
    }
}
