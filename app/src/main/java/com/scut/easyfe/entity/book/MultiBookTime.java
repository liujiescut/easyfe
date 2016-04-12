package com.scut.easyfe.entity.book;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 多次预约时间实体类
 * Created by jay on 16/4/5.
 */
public class MultiBookTime extends BaseBookTime {
    //0-6代表周日到周六
    int weekDay = 0;

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public JSONObject getMultiBookTimeJson(){
        JSONObject json = new JSONObject();
        try {
            json.put("isOk", isOk());
            json.put("morning", isMorning());
            json.put("afternoon", isAfternoon());
            json.put("evening", isEvening());
            json.put("weekDay", weekDay);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
