package com.scut.easyfe.entity.book;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.BaseEntity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约条件基类
 * Created by jay on 16/4/12.
 */
public class BaseBookCondition extends BaseEntity{
    private String token = "";
    private String course = "";
    private String grade = "";
    private int time = 120;     //授课时长
    private int childAge = 7;
    private int childGender = Constants.Identifier.FEMALE;
    private List<String> school = new ArrayList<>();
    private List<JSONArray> price = new ArrayList<>();             //[100, 200]表示匹配大于100，小于200的家教
    private int score = 0;                                      //最小家教评分

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course
                = course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getChildAge() {
        return childAge;
    }

    public void setChildAge(int childAge) {
        this.childAge = childAge;
    }

    public int getChildGender() {
        return childGender;
    }

    public void setChildGender(int childGender) {
        this.childGender = childGender;
    }

    public List<String> getSchool() {
        return school;
    }

    public void setSchool(List<String> school) {
        this.school = school;
    }

    public List<JSONArray> getPrice() {
        return price;
    }

    public void setPrice(List<JSONArray> price) {
        this.price = price;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
