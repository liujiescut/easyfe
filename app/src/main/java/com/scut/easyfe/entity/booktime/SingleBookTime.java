package com.scut.easyfe.entity.booktime;

/**
 * 单次预约的时间实体
 * Created by jay on 16/4/5.
 */
public class SingleBookTime extends BookTime{
    //具体日期的格林威治时间
    private long date = 0;

    private String memo = "";

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
