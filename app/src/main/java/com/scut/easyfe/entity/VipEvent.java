package com.scut.easyfe.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员活动
 * Created by jay on 16/6/7.
 */
public class VipEvent extends BaseEntity{
    //活动标题
    private String title = "";
    //活动详情
    private String detail = "";
    //购买活动所需积分
    private float score = 0f;
    //购买活动所需现金
    private float money = 0f;
    //是否可以购买
    private boolean reservable = false;

    public static List<VipEvent> getTest(){
        ArrayList<VipEvent> events = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            VipEvent event = new VipEvent();
            event.setTitle("活动" + i);
            event.setDetail(
                    "我是会员活动详情\n" +
                    "我是会员活动详情\n" +
                    "我是会员活动详情\n" +
                    "我是会员活动详情");
            event.setScore(i * 10 + 2 * i);
            event.setMoney(i);
            if(i % 2 == 0){
                event.setReservable(true);
            }
            events.add(event);
        }

        return events;
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
        return reservable;
    }

    public void setReservable(boolean reservable) {
        this.reservable = reservable;
    }
}
