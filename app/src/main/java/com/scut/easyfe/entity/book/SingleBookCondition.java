package com.scut.easyfe.entity.book;

import com.scut.easyfe.entity.BaseEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 单次预约条件类
 * Created by jay on 16/4/12.
 */
public class SingleBookCondition extends BaseBookCondition {
    private SingleTimeCondition singleBookTime = new SingleTimeCondition();

    public SingleTimeCondition getSingleBookTime() {
        return singleBookTime;
    }

    public void setSingleBookTime(SingleTimeCondition singleBookTime) {
        this.singleBookTime = singleBookTime;
    }

    public class SingleTimeCondition extends BaseEntity{
        private String date = "";
        private String time = "";         //morning表示预约早上,还有afternoon和evening

        public JSONObject getSingleTimeConditionJson(){
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

        public void setTime(String time) {
            this.time = time;
        }
    }
}
