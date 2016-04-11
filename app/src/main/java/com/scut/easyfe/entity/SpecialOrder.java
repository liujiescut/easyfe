package com.scut.easyfe.entity;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.user.Teacher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 特价订单
 * Created by jay on 16/4/10.
 */
public class SpecialOrder extends BaseEntity {
    private String _id = "";
    private String course = "";
    private String grade = "";
    private long time = 0;
    private float price = 0;
    private long tip = 0;
    private TeachTime teachTime = new TeachTime();
    private SpecialOrderTeacher teacher = new SpecialOrderTeacher();

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public TeachTime getTeachTime() {
        return teachTime;
    }

    public void setTeachTime(TeachTime teachTime) {
        this.teachTime = teachTime;
    }

    public SpecialOrderTeacher getTeacher() {
        return teacher;
    }

    public void setTeacher(SpecialOrderTeacher teacher) {
        this.teacher = teacher;
    }

    public long getTip() {
        return tip;
    }

    public void setTip(long tip) {
        this.tip = tip;
    }

    public class SpecialOrderTeacher implements Serializable {
        String name = "";
        String _id = "";
        int gender = Constants.Identifier.FEMALE;
        long birthday = 0;
        Teacher teacherMessage = new Teacher();
        Address position = new Address();

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public Teacher getTeacherMessage() {
            return teacherMessage;
        }

        public void setTeacherMessage(Teacher teacherMessage) {
            this.teacherMessage = teacherMessage;
        }

        public Address getPosition() {
            return position;
        }

        public void setPosition(Address position) {
            this.position = position;
        }
    }

    public class TeachTime implements Serializable {
        private long date = 0;
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

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
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
}
