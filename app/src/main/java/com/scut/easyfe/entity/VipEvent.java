package com.scut.easyfe.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员活动
 * Created by jay on 16/6/7.
 */
public class VipEvent extends BaseEntity{
    //会员活动id
    private String _id = "";
    //活动标题
    private String title = "";
    //活动详情
    private String detail = "";
    //购买活动所需积分
    private float score = 0f;
    //购买活动所需现金 单位: 分
    private float money = 0f;
    //预约的最大人数
    private int allowCount = 0;
    //已预约的人数
    private int bookCount = 0;

    public String getPayTitle(){
        return title;
    }

    public String getPayInfo(){
        return  detail;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getAllowCount() {
        return allowCount;
    }

    public void setAllowCount(int allowCount) {
        this.allowCount = allowCount;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public boolean isReservable() {
        return allowCount > bookCount;
    }

}
