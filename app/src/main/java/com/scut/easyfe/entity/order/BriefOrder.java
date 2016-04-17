package com.scut.easyfe.entity.order;

import com.scut.easyfe.entity.BaseEntity;

/**
 * Order的简要信息(用于显示在我的订单页面)
 * Created by jay on 16/4/16.
 */
public class BriefOrder extends BaseEntity{
    private String _id = "";
    private String course = "";
    private String tag = "";
    private String teacherName = "";
    private String orderNumber = "";
    private int time = 0;
    private float price = 0;
    private int type = 0;
    private int state = 0;
    private TeachTime teachTime = new TeachTime();
    private boolean selected = false;     //取消跟修改操作是否被选中

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public TeachTime getTeachTime() {
        return teachTime;
    }

    public void setTeachTime(TeachTime teachTime) {
        this.teachTime = teachTime;
    }
}
