package com.scut.easyfe.entity;

import com.scut.easyfe.utils.TimeUtils;

import java.util.Date;

/**
 * 优惠券
 * Created by jay on 16/5/15.
 */
public class Coupon extends BaseEntity{
    private String tag = "";                            //表示是否为同一种优惠券
    String grade = "";                                  //适用年级
    String weekday = "";                                //适用时间
    private int time = 0;                               //当课时大于两个小时才可以使用
    private int money = 0;                              //可以优惠10元
    private long deadline = 0l;                         //时间戳，优惠券过期时间
    private int count = 0;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("适用年级: ");
        sb.append(grade);
        sb.append("\n");
        sb.append("适用时间: ");
        sb.append(weekday);

        if(time != 0) {
            sb.append("\n");
            sb.append("使用限制: ");
            sb.append(getConstraint());
        }

        if(deadline != 0) {
            sb.append("\n");
            sb.append("到期时间: ");
            sb.append(TimeUtils.getTime(new Date(deadline), "yyyy-MM-dd"));
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Coupon) {
            return tag.equals(((Coupon)o).getTag());
        }

        return super.equals(o);
    }

    public int getCount() {
        return count;
    }

    public void addCount(){
        ++count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    private String getConstraint(){
        return String.format("授课时间超过%d小时可用", time);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }
}
