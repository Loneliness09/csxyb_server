package com.iflytek.csxyb.service;

import com.iflytek.csxyb.entity.Goods;
import com.iflytek.csxyb.entity.User;

import java.util.List;

public interface GoodsService {
    int deleteGoods(User user, Goods goods);
    int updateGoods(User user, Goods goods);
    List<Goods> findGoodsByName(User user, String goodsName, int pageNum, int pageSize);
}
