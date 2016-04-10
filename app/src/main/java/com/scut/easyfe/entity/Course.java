package com.scut.easyfe.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 可教授课程类
 * Created by jay on 16/4/8.
 */
public class Course extends BaseEntity {
    private String _id = "";

    //教授课程名
    private String course = "";

    //教授年级(小学 一年级)这种格式
    private ArrayList<String> grade = new ArrayList<>();

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

    public ArrayList<String> getGrade() {
        return grade;
    }

    public void setGrade(ArrayList<String> grade) {
        this.grade = grade;
    }

    public static String getStateFromGrade(String grade){
        return grade.split(" ")[0];
    }

    public static String getGradeFromGrade(String grade){
        return grade.split(" ")[1];
    }
}
