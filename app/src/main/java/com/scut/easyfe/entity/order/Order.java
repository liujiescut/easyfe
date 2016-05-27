package com.scut.easyfe.entity.order;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.BaseEntity;
import com.scut.easyfe.entity.Comment;
import com.scut.easyfe.entity.user.ParentInfo;
import com.scut.easyfe.entity.user.TeacherInfo;
import com.scut.easyfe.utils.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通订单类(用在单双次预约结果解析并在预约流程中通用)
 * Created by jay on 16/4/12.
 */
public class Order extends BaseEntity {
    private String _id = "";
    private String orderNumber = "";     //订单号, O-2016000000, 开头是字母O
    private int type = 0;                //0表示普通订单，1表示特价订单
    private int state = 0;               //0未修改已预订，1待执行，2修改过待确定，3已完成，4无效订单
    private String tag = "";             //表示订单是否为同一批多次预约订单
    private int time = 120;              //单次家教的时间
    private String course = "";          //课程
    private String grade = "";           //孩子年级
    private float price = 0f;            //单价
    private float originalPrice = 0f;    //原价(特价订单用到)
    private int childAge = 0;            //孩子年龄
    private int childGender = Constants.Identifier.FEMALE; //孩子性别
    private String cancelPerson = "";    //订单状态为4才有的字段，“teacher”表示是教师取消订单，“parent”表示家长取消
    private boolean hadComment = false;  //是否已经评价
    private boolean hadGetCoupon = false;//是否已经领取现金券
    private long completedTime = 0;      //订单完成时间
    private TeachTime teachTime = new TeachTime();
    private TeacherInfo teacher = new TeacherInfo();
    private ParentInfo parent = new ParentInfo();
    private Insurance insurance = new Insurance();
    private float subsidy = 5;     //超过交通时间，收的交通补贴
    private float addPrice = 0;
    private List<Comment> comments = new ArrayList<>();

    private int trafficTime = 0;

    public boolean isHadGetCoupon() {
        return hadGetCoupon;
    }

    public void setHadGetCoupon(boolean hadGetCoupon) {
        this.hadGetCoupon = hadGetCoupon;
    }

    public float getPerPrice(){
        return price + addPrice;
    }
    public float getTotalPrice(){
        return (price + addPrice) * ((float)time / 60) + subsidy;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public static String getBaseInfo(Order order){
        String content = "";
        content += String.format("性别: %s\n", order.getTeacher().getGender() == Constants.Identifier.MALE ? "男" : "女");
        content += String.format("年龄: %d\n", TimeUtils.getAgeFromBirthday(order.getTeacher().getBirthday()));
        content += String.format("大学专业: %s %s\n", order.getTeacher().getTeacherMessage().getSchool(), order.getTeacher().getTeacherMessage().getProfession());
        content += String.format("已家教过的孩子数量：%s\n", order.getTeacher().getTeacherMessage().getTeachCount());
        content += String.format("已家教的时长：%s\n", order.getTeacher().getTeacherMessage().getHadTeach());
        content += String.format("综合评分：%.2f", order.getTeacher().getTeacherMessage().getScore());
        return content;
    }

    public float getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(float addPrice) {
        this.addPrice = addPrice;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getTrafficTime() {
        return trafficTime;
    }

    public void setTrafficTime(int trafficTime) {
        this.trafficTime = trafficTime;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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

    public String getCancelPerson() {
        return cancelPerson;
    }

    public void setCancelPerson(String cancelPerson) {
        this.cancelPerson = cancelPerson;
    }

    public boolean isHadComment() {
        return hadComment;
    }

    public void setHadComment(boolean hadComment) {
        this.hadComment = hadComment;
    }

    public long getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(long completedTime) {
        this.completedTime = completedTime;
    }

    public TeachTime getTeachTime() {
        return teachTime;
    }

    public void setTeachTime(TeachTime teachTime) {
        this.teachTime = teachTime;
    }

    public TeacherInfo getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherInfo teacher) {
        this.teacher = teacher;
    }

    public ParentInfo getParent() {
        return parent;
    }

    public void setParent(ParentInfo parent) {
        this.parent = parent;
    }

    public float getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(float subsidy) {
        this.subsidy = subsidy;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public class Insurance extends BaseEntity {
        private String insuranceNumber = "";
        private String _id = "";

        public JSONObject getInsuranceJson() {
            JSONObject json = new JSONObject();
            try {
                json.put("insuranceNumber", insuranceNumber);
                json.put("_id", _id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return json;
        }

        public String getInsuranceNumber() {
            return insuranceNumber;
        }

        public void setInsuranceNumber(String insuranceNumber) {
            this.insuranceNumber = insuranceNumber;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }
    }
}
