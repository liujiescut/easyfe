package com.scut.easyfe.entity.book;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 单次预约的时间实体
 * Created by jay on 16/4/5.
 */
public class SingleBookTime extends BaseBookTime {
    //具体日期的格林威治时间
    private String date = "1970-01-01";

    private String memo = "";

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
}
