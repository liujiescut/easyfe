package com.scut.easyfe.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单类
 * Created by jay on 16/3/27.
 */
public class Order extends BaseEntity {
    private String _id = "";                //订单id
    private String orderNumber = "";        //订单号
    private String teacherName = "";        //教师姓名
    private String courseName = "";         //授课课程名称
    private Date date = new Date();         //授课时间(年月日)
    private int teachTime = 0;              //授课时长
    private String period = "";             //授课时间(上午下午晚上)
    private float price = 0f;

    public Order(String _id, String orderNumber, String teacherName, String courseName, Date date, int teachTime, String period, float price) {
        this._id = _id;
        this.orderNumber = orderNumber;
        this.teacherName = teacherName;
        this.courseName = courseName;
        this.date = date;
        this.teachTime = teachTime;
        this.period = period;
        this.price = price;
    }

    public static ArrayList<Order> getTestOrders(){
        ArrayList<Order> orders = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            orders.add(new Order(i + "", 100000000 + i + "", "黄教授", "人生伦理", new Date(), 120, "晚上", 200));
        }
        return orders;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTeachTime() {
        return teachTime;
    }

    public void setTeachTime(int teachTime) {
        this.teachTime = teachTime;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
