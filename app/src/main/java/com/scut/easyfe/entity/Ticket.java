package com.scut.easyfe.entity;

import com.scut.easyfe.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * 优惠券
 * Created by jay on 16/5/15.
 */
public class Ticket extends BaseEntity{
    private String tag = "";                            //表示是否为同一种优惠券
    ArrayList<String> grade = new ArrayList<>();        //适用于小学一二年级
    ArrayList<Integer> weekday = new ArrayList<>();     //适用周一到周日，分别对应1~7
    private int time = 0;                               //当课时大于两个小时才可以使用
    private int money = 0;                              //可以优惠10元
    private long deadline = 0l;                         //时间戳，优惠券过期时间

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("适用年级: ");
        sb.append(getGradeString());
        sb.append("\n");
        sb.append("适用时间: ");
        sb.append(getWeekDayString());

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

    private String getGradeString(){
        StringBuilder sb = new StringBuilder();
        for (String item :
                grade) {
            sb.append(item);
            sb.append("、");
        }
        return sb.toString();
    }

    private String getWeekDayString(){
        StringBuilder sb = new StringBuilder();
        for (Integer week :
                weekday) {
            sb.append(TimeUtils.getWeekStringFromInt(week - 1 ));
            sb.append("、");
        }

        return sb.toString();
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

    public ArrayList<String> getGrade() {
        return grade;
    }

    public void setGrade(ArrayList<String> grade) {
        this.grade = grade;
    }

    public ArrayList<Integer> getWeekday() {
        return weekday;
    }

    public void setWeekday(ArrayList<Integer> weekday) {
        this.weekday = weekday;
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
