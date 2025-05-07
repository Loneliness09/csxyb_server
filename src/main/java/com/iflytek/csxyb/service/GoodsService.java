package com.iflytek.csxyb.service;

import com.iflytek.csxyb.entity.Goods;
import com.iflytek.csxyb.entity.User;

import java.util.List;

public interface GoodsService {
    int deleteGoods(User user, Goods goods);
    List<Goods> findGoodsByName(User user, String goodsName, int pageNum, int pageSize);
    List<Goods> findGoodsByNameAndType(User user, String goodsName, String goodsType, int pageNum, int pageSize);
    int getGoodsTotalSizeByName(User user, String goodsName);

    int updateGoods(User user, Goods goods);

    int updateStatus(User user, Goods goods);
}
