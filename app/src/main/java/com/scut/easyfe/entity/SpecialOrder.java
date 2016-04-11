package com.scut.easyfe.entity;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.user.Teacher;

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
    private long price = 0;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
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

    public class SpecialOrderTeacher implements Serializable{
        String name = "";
        int gender = Constants.Identifier.FEMALE;
        long birthday = 0;
        Teacher teacherMessage = new Teacher();

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
    }

    public class TeachTime implements Serializable{
        private long date = 0;
        private String time = "";

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
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
