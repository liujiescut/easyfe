package com.scut.easyfe.entity.book;

import com.scut.easyfe.entity.BaseEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 多次预约条件类
 * Created by jay on 16/4/12.
 */
public class MultiBookCondition extends BaseBookCondition {
    private MultiTimeCondition multiBookTime = new MultiTimeCondition();

    public MultiTimeCondition getMultiBookTime() {
        return multiBookTime;
    }

    public void setMultiBookTime(MultiTimeCondition multiBookTime) {
        this.multiBookTime = multiBookTime;
    }

    public class MultiTimeCondition extends BaseEntity{
        private int weekDay = 0;          //0-6表示周日到周六
        private String time = "";         //morning表示预约早上,还有afternoon和evening

        public JSONObject getMultiBookConditionJson(){
            JSONObject json = new JSONObject();
            try {
                json.put("weekDay", weekDay);
                json.put("time", time);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return json;
        }

        public int getWeekDay() {
            return weekDay;
        }

        public void setWeekDay(int weekDay) {
            this.weekDay = weekDay;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
