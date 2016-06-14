package com.scut.easyfe.entity;

import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.entity.order.TeachTime;

import java.util.ArrayList;

/**
 * 反馈报告
 * Created by jay on 16/6/12.
 */
public class FeedbackReport extends BaseEntity{
    private String _id = "";                    //订单_id
    private String orderNumber = "";                   //订单号
    private String teacherName = "";                //家教姓名
    private String parentName = "";                 //家长姓名
    private TeachTime teachTime = new TeachTime();  //授课时间
    private String teacherComment = "";             //家教评论
    private String rightPercent = "";               //正确率
    private int enthusiasm = 5;                     //积极性
    private int getLevel = 5;                       //吸收程度
    private Order.TutorDetail thisTeachDetail =     //本次专业辅导情况
            new Order.TutorDetail();
    private Order.TutorDetail nextTeachDetail =     //下次专业辅导情况
            new Order.TutorDetail();

    public static ArrayList<FeedbackReport> getTestData(){
        ArrayList<FeedbackReport> reports = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            FeedbackReport report = new FeedbackReport();
            report.setOrderNumber("120161324" + i);
            report.setTeacherName("家教");
            report.setParentName("家长");
            report.setEnthusiasm(i % 4 + 1);
            report.setGetLevel(i % 3 + 1);
            report.setRightPercent("80% - 90%");
            report.setTeacherComment("孩子挺聪明的~~~");
            TeachTime teachTime = new TeachTime();
            teachTime.setDate("2016-6-15");
            teachTime.setTime("afternoon");
            report.setTeachTime(teachTime);

            reports.add(report);
        }

        return reports;
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public TeachTime getTeachTime() {
        return teachTime;
    }

    public void setTeachTime(TeachTime teachTime) {
        this.teachTime = teachTime;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public String getRightPercent() {
        return rightPercent;
    }

    public void setRightPercent(String rightPercent) {
        this.rightPercent = rightPercent;
    }

    public int getEnthusiasm() {
        return enthusiasm;
    }

    public void setEnthusiasm(int enthusiasm) {
        this.enthusiasm = enthusiasm;
    }

    public int getGetLevel() {
        return getLevel;
    }

    public void setGetLevel(int getLevel) {
        this.getLevel = getLevel;
    }

    public Order.TutorDetail getThisTeachDetail() {
        return thisTeachDetail;
    }

    public void setThisTeachDetail(Order.TutorDetail thisTeachDetail) {
        this.thisTeachDetail = thisTeachDetail;
    }

    public Order.TutorDetail getNextTeachDetail() {
        return nextTeachDetail;
    }

    public void setNextTeachDetail(Order.TutorDetail nextTeachDetail) {
        this.nextTeachDetail = nextTeachDetail;
    }
}
