package com.scut.easyfe.entity;

/**
 * 可教授课程
 * Created by jay on 16/4/6.
 */
public class TeachableCourse extends BaseEntity{
    private int intId = 0;
    private String _id = "";
    private String course = "";
    private String grade = "";
    private float price = 0f;     //单位: 分
    private float addPrice = 0f;

    public TeachableCourse(){

    }

    public TeachableCourse(int intId, String course, String grade, float price) {
        this.intId = intId;
        this.course = course;
        this.grade = grade;
        this.price = price;
    }

    public float getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(float addPrice) {
        this.addPrice = addPrice;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getIntId() {
        return intId;
    }

    public void setIntId(int intId) {
        this.intId = intId;
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


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof TeachableCourse){
            TeachableCourse teachableCourse = (TeachableCourse)o;
            return teachableCourse.getCourse().equals(course) && teachableCourse.getGrade().equals(grade);
        }
        return super.equals(o);
    }
}
