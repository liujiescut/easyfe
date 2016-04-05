package com.scut.easyfe.entity.booktime;

/**
 * 多次预约时间实体类
 * Created by jay on 16/4/5.
 */
public class MultiBookTime extends BookTime{
    //0-6代表周日到周六
    int weekDay = 0;

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }
}
