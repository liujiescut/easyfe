package com.scut.easyfe.entity;

/**
 * 可教授课程类
 * Created by jay on 16/4/8.
 */
public class Course extends BaseEntity {
    private String _id = "";
    private String course = "";

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
}
