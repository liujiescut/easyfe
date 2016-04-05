package com.scut.easyfe.entity.booktime;

/**
 * 单次预约的时间实体
 * Created by jay on 16/4/5.
 */
public class SingleBookTime extends BookTime{
    //具体日期的格林威治时间
    private String date = "0";

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
