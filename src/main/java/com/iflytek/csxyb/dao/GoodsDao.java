package com.iflytek.csxyb.dao;

import com.iflytek.csxyb.dao.base.BaseDao;
import com.iflytek.csxyb.entity.Goods;
import com.iflytek.csxyb.entity.User;

import java.util.List;

public interface GoodsDao extends BaseDao<Goods> {
    int insert(Goods goods);
    int update(Goods goods);
    int delete(Goods goods);
    int updateStatus(Goods goods);
    List<Goods> selectAll(int pageNum, int pageSize);
    List<Goods> selectByName(String name, int pageNum, int pageSize);
    List<Goods> selectById(int userId, int pageNum, int pageSize);
    List<Goods> selectByIdAndName(int userId, String name, int pageNum, int pageSize);
    List<Goods> selectAllByType(String goodsType, int pageNum, int pageSize);
    List<Goods> selectByNameAndType(String name, String goodsType, int pageNum, int pageSize);
    List<Goods> selectByIdAndType(int userId, String goodsType, int pageNum, int pageSize);
    List<Goods> selectByIdAndNameAndType(int userId, String name, String goodsType, int pageNum, int pageSize);
    int getTotalSize();
    int getTotalSizeByName(String name);
    int getTotalSizeById(int userId);
    int getTotalSizeByIdAndName(int userId, String name);
    int getTotalSizeByType(String type);
    int getTotalSizeByNameAndType(String name, String type);
    int getTotalSizeByIdAndType(int userId, String type);
    int getTotalSizeByIdAndNameAndType(int userId, String name, String type);
    User findGoodsOwner(Goods goods);
    Goods findGoods(int goodsId);
}
