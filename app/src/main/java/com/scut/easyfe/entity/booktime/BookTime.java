package com.scut.easyfe.entity.booktime;

import com.scut.easyfe.entity.BaseEntity;

/**
 * 预约的时间基类
 * Created by jay on 16/4/5.
 */
public class BookTime extends BaseEntity{
    //是否可以授课
    private boolean isOk = false;
    //早上授课
    private boolean morning = false;
    //下午授课
    private boolean afternoon = false;
    //晚上授课
    private boolean evening = false;

    public boolean isOk() {
        return isOk;
    }

    public void setIsOk(boolean isOk) {
        this.isOk = isOk;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public boolean isAfternoon() {
        return afternoon;
    }

    public void setAfternoon(boolean afternoon) {
        this.afternoon = afternoon;
    }

    public boolean isEvening() {
        return evening;
    }

    public void setEvening(boolean evening) {
        this.evening = evening;
    }
}
