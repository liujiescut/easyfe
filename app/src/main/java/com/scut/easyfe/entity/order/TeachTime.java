package com.scut.easyfe.entity.order;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 授课时间
 * Created by jay on 16/4/12.
 */
public class TeachTime implements Serializable {
    private String date = "";
    private String time = "";

    public JSONObject getTeachTimeJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("date", date);
            json.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public String getChineseTime() {
        if (time.equals("morning")) {
            return "上午";
        }

        if (time.equals("afternoon")) {
            return "下午";
        }

        if (time.equals("evening")) {
            return "晚上";
        }

        return "";
    }

    public void setTime(String time) {
        this.time = time;
    }
}
