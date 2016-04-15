package com.scut.easyfe.entity.test;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * 订单类
 * Created by jay on 16/3/27.
 */
public class Order extends BaseEntity implements Serializable{
    private String _id = "";                //订单id
    private String orderNumber = "";        //订单号
    private String insuranceNumber = "";    //保险单号

    private Date date = new Date();         //授课时间(年月日)
    private String teachPeriod = "";             //授课时间(上午下午晚上)
    private String courseName = "";         //授课课程名称
    private float price = 0f;               //单价
    private float specialPrice = 0f;        //特价订单使用
    private int teachTime = 0;              //授课时长
    private float tip = 10f;                 //交通补贴

    private double parentLatitude = 0d;     //家长地址纬度
    private double parentLongitude = 0d;    //家长地址经度
    private double teacherLatitude = 0d;    //家教地址纬度
    private double teacherLongitude = 0d;   //家教地址经度
    private String city = "广州";            //家教所在城市

    private String teacherName = "";        //教师姓名
    private int teacherAge = 28;            //教师年龄
    private String teacherAvatar = "http://imgsrc.baidu.com/forum/w%3D580/sign=09af8227004f78f0800b9afb49310a83/3e7ce6f0f736afc38b732316b019ebc4b745127d.jpg";      //教师头像
    private String teacherPhone = "";       //家教手机
    private String teacherSchool = "";      //家教学校
    private String teacherProfession = "";  //家教专业
    private int teacherHasTeachCount = 10;  //家教孩子数量
    private int teacherHasTeachTime = 1000; //家教时长
    private int teacherAcceptTime = 60;     //家教可接受的路程分钟数
    private int teacherMaxAcceptTime = 100; //家教可接受的最大路程分钟数
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

    public Order() {
    }

//    public static ArrayList<Order> getTestOrders() {
//        ArrayList<Order> orders = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            Order order = new Order();
//
//            if (i == 0) {
//                //不用交通补贴
//                order.setParentLatitude(23.06555387370726);
//                order.setParentLongitude(113.41226534667324);
//            } else if (i == 1) {
//                //需要交通补贴
//                order.setParentLatitude(23.132843978618187);
//                order.setParentLongitude(113.3695868519083);
//            } else {
//                //超出最大家教时间范围
//                order.setParentLatitude(23.215710873792418);
//                order.setParentLongitude(113.28358308241141);
//            }
//
//            order.setTeacherLatitude(23.059061572474914);
//            order.setTeacherLongitude(113.40638144558483);
//            order.setCity("广州");
//
//            order.setPrice(250f);
//            order.setSpecialPrice(60f);
//            order.setCourseName("数学");
//            order.setTeachTime(120);
//            order.setTeachPeriod("晚上");
//            order.setTeachTime(100);
//            order.setDate(new Date());
//
//            order.setTeacherName("林老师");
//            order.setTeacherGender(Constants.Identifier.MALE);
//            order.setTeacherProfession("软件工程");
//            order.setTeacherSchool("华南理工大学");
//            order.setTeacherHasTeachCount(10);
//            order.setTeacherHasTeachTime(200 + i * 10);
//            order.setTeacherScore(4.8f);
//
//            order.setStudentAge(16);
//            order.setStudentGender(Constants.Identifier.MALE);
//            order.setStudentState("小学");
//            order.setStudentGrade("三年级");
//
//            orders.add(order);
//        }
//
//        return orders;
//    }

//    public static String getBaseInfo(Order order){
//        String content = "";
//        content += String.format("性别: %s\n", order.getTeacherGender() == Constants.Identifier.MALE ? "男" : "女");
//        content += String.format("年龄: %d\n", order.getTeacherAge());
//        content += String.format("大学专业: %s %s\n", order.getTeacherSchool(), order.getTeacherProfession());
//        content += String.format("已家教过的孩子数量：%d\n", order.getTeacherHasTeachCount());
//        content += String.format("已家教的时长：%d 小时\n", order.getTeacherHasTeachTime());
//        content += String.format("综合评分：%.2f", order.getTeacherScore());
//        return content;
//    }
//
//    public static String getScoreInfo(Order order){
//        return "";
//    }

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

    public double getParentLatitude() {
        return parentLatitude;
    }

    public void setParentLatitude(double parentLatitude) {
        this.parentLatitude = parentLatitude;
    }

    public double getParentLongitude() {
        return parentLongitude;
    }

    public void setParentLongitude(double parentLongitude) {
        this.parentLongitude = parentLongitude;
    }

    public double getTeacherLatitude() {
        return teacherLatitude;
    }

    public void setTeacherLatitude(double teacherLatitude) {
        this.teacherLatitude = teacherLatitude;
    }

    public double getTeacherLongitude() {
        return teacherLongitude;
    }

    public void setTeacherLongitude(double teacherLongitude) {
        this.teacherLongitude = teacherLongitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public int getTeacherAcceptTime() {
        return teacherAcceptTime;
    }

    public void setTeacherAcceptTime(int teacherAcceptTime) {
        this.teacherAcceptTime = teacherAcceptTime;
    }

    public int getTeacherMaxAcceptTime() {
        return teacherMaxAcceptTime;
    }

    public void setTeacherMaxAcceptTime(int teacherMaxAcceptTime) {
        this.teacherMaxAcceptTime = teacherMaxAcceptTime;
    }

    public int getTeacherAge() {
        return teacherAge;
    }

    public void setTeacherAge(int teacherAge) {
        this.teacherAge = teacherAge;
    }

    public String getTeacherAvatar() {
        return teacherAvatar;
    }

    public void setTeacherAvatar(String teacherAvatar) {
        this.teacherAvatar = teacherAvatar;
    }
}
