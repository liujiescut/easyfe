package com.scut.easyfe.entity.book;

import android.support.annotation.NonNull;

import com.scut.easyfe.utils.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * 单次预约的时间实体
 * Created by jay on 16/4/5.
 */
public class SingleBookTime extends BaseBookTime implements Comparable<SingleBookTime>{
    //具体日期的格林威治时间
    private String date = "1970-01-01";

    private Date dateDate = new Date();

    private String memo = "";

    public SingleBookTime() {
    }

    public SingleBookTime(Date dateDate, boolean morning, boolean afternoon, boolean evening, boolean isOk) {
        this.dateDate = dateDate;
        this.date = TimeUtils.getTime(dateDate, "yyyy-MM-dd");
        this.setMorning(morning);
        this.setAfternoon(afternoon);
        this.setEvening(evening);
        this.setIsOk(isOk);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getDateDate() {
        return dateDate;
    }

    public void setDateDate(Date dateDate) {
        this.dateDate = dateDate;
        this.date = TimeUtils.getTime(dateDate, "yyyy-MM-dd");
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public JSONObject getSingleBookTimeJson(){
        JSONObject json = new JSONObject();
        try {
            json.put("date", date);
            json.put("isOk", isOk());
            json.put("morning", isMorning());
            json.put("afternoon", isAfternoon());
            json.put("evening", isEvening());
            json.put("memo", memo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public int compareTo(@NonNull SingleBookTime another) {
        return (int) ((dateDate.getTime() - another.getDateDate().getTime()) % Integer.MAX_VALUE);
    }
}
