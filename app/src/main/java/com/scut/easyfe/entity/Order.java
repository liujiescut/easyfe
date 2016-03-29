package com.scut.easyfe.entity;

import com.scut.easyfe.app.Constants;

import java.util.ArrayList;
import java.util.Date;

/**
 * 订单类
 * Created by jay on 16/3/27.
 */
public class Order extends BaseEntity {
    private String _id = "";                //订单id
    private String orderNumber = "";        //订单号
    private String insuranceNumber = "";    //保险单号

    private Date date = new Date();         //授课时间(年月日)
    private String teachPeriod = "";             //授课时间(上午下午晚上)
    private String courseName = "";         //授课课程名称
    private float price = 0f;               //单价
    private float specialPrice = 0f;        //特价订单使用
    private int teachTime = 0;              //授课时长
    private float tip = 0f;                 //交通补贴

    private String teacherName = "";        //教师姓名
    private String teacherPhone = "";       //家教手机
    private String teacherSchool = "";      //家教学校
    private String teacherProfession = "";  //家教专业
    private int teacherHasTeachCount = 10;     //家教孩子数量
    private int teacherHasTeachTime = 1000;    //家教时长
    private float teacherScore;             //家教综合评分
    private int teacherGender =             //家教性别
            Constants.Identifier.FEMALE;

    private String parentName = "";         //家长姓名
    private String parentPhone = "";        //家长手机
    private String parentAddress = "";      //家长地址
    private String studentState = "";       //大学,高中,初中,小学等
    private String studentGrade = "";       //大一大二大三这种
    private int studentAge = 0;             //学生年龄
    private int studentGender =             //学生性别
            Constants.Identifier.FEMALE;



    public static ArrayList<Order> getTestOrders(){
        ArrayList<Order> orders = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            Order order = new Order();

            order.setPrice(250f);
            order.setSpecialPrice(60f);
            order.setCourseName("数学");
            order.setTeachTime(120);
            order.setTeachPeriod("晚上");
            order.setTeachTime(100);
            order.setDate(new Date());

            order.setTeacherName("林老师");
            order.setTeacherGender(Constants.Identifier.MALE);
            order.setTeacherProfession("软件工程");
            order.setTeacherSchool("华南理工大学");
            order.setTeacherHasTeachCount(10);
            order.setTeacherHasTeachTime(200 + i * 10);
            order.setTeacherScore(4.8f);

            order.setStudentAge(16);
            order.setStudentGender(Constants.Identifier.MALE);
            order.setStudentState("小学");
            order.setStudentGrade("三年级");

            orders.add(order);
        }
        return orders;
    }

    public Order() {
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

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTeachPeriod() {
        return teachPeriod;
    }

    public void setTeachPeriod(String teachPeriod) {
        this.teachPeriod = teachPeriod;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTeachTime() {
        return teachTime;
    }

    public void setTeachTime(int teachTime) {
        this.teachTime = teachTime;
    }

    public float getTip() {
        return tip;
    }

    public void setTip(float tip) {
        this.tip = tip;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public String getTeacherSchool() {
        return teacherSchool;
    }

    public void setTeacherSchool(String teacherSchool) {
        this.teacherSchool = teacherSchool;
    }

    public String getTeacherProfession() {
        return teacherProfession;
    }

    public void setTeacherProfession(String teacherProfession) {
        this.teacherProfession = teacherProfession;
    }

    public float getTeacherScore() {
        return teacherScore;
    }

    public void setTeacherScore(float teacherScore) {
        this.teacherScore = teacherScore;
    }

    public int getTeacherGender() {
        return teacherGender;
    }

    public void setTeacherGender(int teacherGender) {
        this.teacherGender = teacherGender;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getParentAddress() {
        return parentAddress;
    }

    public void setParentAddress(String parentAddress) {
        this.parentAddress = parentAddress;
    }

    public String getStudentState() {
        return studentState;
    }

    public void setStudentState(String studentState) {
        this.studentState = studentState;
    }

    public String getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        this.studentGrade = studentGrade;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    public int getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(int studentGender) {
        this.studentGender = studentGender;
    }


    public int getTeacherHasTeachCount() {
        return teacherHasTeachCount;
    }

    public void setTeacherHasTeachCount(int teacherHasTeachCount) {
        this.teacherHasTeachCount = teacherHasTeachCount;
    }

    public int getTeacherHasTeachTime() {
        return teacherHasTeachTime;
    }

    public void setTeacherHasTeachTime(int teacherHasTeachTime) {
        this.teacherHasTeachTime = teacherHasTeachTime;
    }

    public float getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(float specialPrice) {
        this.specialPrice = specialPrice;
    }
}
