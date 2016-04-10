package com.scut.easyfe.entity.booktime;

import com.scut.easyfe.utils.OtherUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

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

    public JSONObject getSingleBookTimeJson(){
        JSONObject json = new JSONObject();
        try {
            json.put("date", OtherUtils.getTime(new Date(date), "yyyy-MM-dd"));
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
}
