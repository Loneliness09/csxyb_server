package com.iflytek.csxyb.service;

import com.iflytek.csxyb.entity.Goods;
import com.iflytek.csxyb.entity.User;

import java.util.List;

public interface GoodsService {
    List<Goods> findAllGoods(int pageNum, int pageSize);
    int deleteGoods(Goods goods);
    int updateGoods(Goods goods);
    List<Goods> findGoodsByName(User user, String goodsName, int pageNum, int pageSize);
}
