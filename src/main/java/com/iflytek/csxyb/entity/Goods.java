package com.iflytek.csxyb.entity;

import com.google.gson.Gson;

import java.sql.Date;

public class Goods {
    private int goodsId;
    private int userId;
    private String goodsText;
    private String goodsImg;
    private String goodsTopImg;
    private String goodsLabel;
    private int pinLunNumber;
    private Date goodsTime;
    private double goodsPrice;
    private String goodsPriceText;
    private int status;

    public Goods() {
    }


    public Goods(int goodsId, int userId, String goodsText, String goodsImg, String goodsTopImg, String goodsLabel, int pinLunNumber, Date goodsTime, double goodsPrice, String goodsPriceText, int status) {
        this.goodsId = goodsId;
        this.userId = userId;
        this.goodsText = goodsText;
        this.goodsImg = goodsImg;
        this.goodsTopImg = goodsTopImg;
        this.goodsLabel = goodsLabel;
        this.pinLunNumber = pinLunNumber;
        this.goodsTime = goodsTime;
        this.goodsPrice = goodsPrice;
        this.goodsPriceText = goodsPriceText;
        this.status = status;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGoodsText() {
        return goodsText;
    }

    public void setGoodsText(String goodsText) {
        this.goodsText = goodsText;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsTopImg() {
        return goodsTopImg;
    }

    public void setGoodsTopImg(String goodsTopImg) {
        this.goodsTopImg = goodsTopImg;
    }

    public String getGoodsLabel() {
        return goodsLabel;
    }

    public void setGoodsLabel(String goodsLabel) {
        this.goodsLabel = goodsLabel;
    }

    public int getPinLunNumber() {
        return pinLunNumber;
    }

    public void setPinLunNumber(int pinLunNumber) {
        this.pinLunNumber = pinLunNumber;
    }

    public Date getGoodsTime() {
        return goodsTime;
    }

    public void setGoodsTime(Date goodsTime) {
        this.goodsTime = goodsTime;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsPriceText() {
        return goodsPriceText;
    }

    public void setGoodsPriceText(String goodsPriceText) {
        this.goodsPriceText = goodsPriceText;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
