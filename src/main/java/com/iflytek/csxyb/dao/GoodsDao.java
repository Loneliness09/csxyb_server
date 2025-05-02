package com.iflytek.csxyb.dao;

import com.iflytek.csxyb.dao.base.BaseDao;
import com.iflytek.csxyb.entity.Goods;

import java.util.List;

public interface GoodsDao extends BaseDao<Goods> {
    List<Goods> selectAll(int pageNum, int pageSize);
    int insert(Goods goods);
    int update(Goods goods);
    int delete(Goods goods);
    List<Goods> selectByName(String name, int pageNum, int pageSize);
    List<Goods> selectById(int userId, int pageNum, int pageSize);
    List<Goods> selectByIdAndName(int userId, String name, int pageNum, int pageSize);
}
