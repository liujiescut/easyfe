package com.scut.easyfe.entity;

/**
 * 可教授课程
 * Created by jay on 16/4/6.
 */
public class TeachableCourse extends BaseEntity{
    private int _id = 0;
    private String course = "";
    private String grade = "";
    private float price = 0f;

    public TeachableCourse(int _id, String course, String grade, float price) {
        this._id = _id;
        this.course = course;
        this.grade = grade;
        this.price = price;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
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
