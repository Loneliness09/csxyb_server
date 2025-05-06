package com.iflytek.csxyb.service.impl;

import com.iflytek.csxyb.dao.GoodsDao;
import com.iflytek.csxyb.dao.impl.GoodsDaoImpl;
import com.iflytek.csxyb.entity.Goods;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.entity.UserType;
import com.iflytek.csxyb.service.GoodsService;

import java.util.List;

public class GoodsServiceImpl implements GoodsService {
    GoodsDao goodsDao = new GoodsDaoImpl();
    @Override
    public int deleteGoods(User user, Goods goods) {
        User owner = goodsDao.findGoodsOwner(goods);
        if (user.getType() == UserType.regular && user.getUserId() != owner.getUserId()) {
            return 0;
        } else {
            return goodsDao.delete(goods);
        }
    }

    @Override
    public int updateGoods(User user, Goods goods) {
        if (user.getType() == UserType.regular && user.getUserId() != goods.getUserId()) {
            return 0;
        } else {
            return goodsDao.update(goods);
        }
    }

    @Override
    public List<Goods> findGoodsByName(User user, String goodsName, int pageNum, int pageSize) {
        if (user.getType() != UserType.regular) {
            if (goodsName == null || "".equals(goodsName)) {
                return goodsDao.selectAll(pageNum, pageSize);
            } else {
                return goodsDao.selectByName(goodsName, pageNum, pageSize);
            }
        } else {
            if (goodsName == null || "".equals(goodsName)) {
                return goodsDao.selectById(user.getUserId(), pageNum, pageSize);
            } else {
                return goodsDao.selectByIdAndName(user.getUserId(), goodsName, pageNum, pageSize);
            }
        }
    }
}
