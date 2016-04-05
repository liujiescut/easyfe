package com.scut.easyfe.entity.user;

import com.scut.easyfe.entity.BaseEntity;
import com.scut.easyfe.entity.booktime.MultiBookTime;
import com.scut.easyfe.entity.booktime.SingleBookTime;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 家教属性类
 * Created by jay on 16/4/5.
 */
public class Teacher {
    //家教身份证号码
    String idCard = "";

    //家教个人简介
    String profile = "";

    //家教上传的图片(身份证正反面跟免冠照片)
    JSONObject images = new JSONObject();

    //家教等级
    int level = 0;

    //已家教时长(家教手动输入)
    String hadTeach = "0";

    //已家教孩子数量(家教手动输入)
    String teachCount = "0";

    //平台上家教时长(平台记录)
    int teachTime = 0;

    //综合评分
    float score = 0f;

    //孩子喜欢程度
    float childAccept = 0f;

    //专业胜任程度
    float ability = 0f;

    //被评论次数
    int commentTime = 0;

    //是否可以被查找到
    boolean isLock = false;

    //家教所在学校
    String school = "";

    //家教所在专业
    String profession = "";

    //多次预约时间
    List<MultiBookTime> multiBookTime = new ArrayList<>();

    //单词预约时间
    List<SingleBookTime> singleBookTime = new ArrayList<>();

    //可教授课程
    List<String> course = new ArrayList<>();

    //最短授课时间
    int minCourseTime = 120;

    //不收交通补贴的最长交通时间
    int freeTrafficTime = 120;

    //交通时间超过此时间不接单
    int maxTrafficTime = 180;

    //超过交通时间，收的交通补贴
    float subsidy = 5f;

    //天使计划
    JSONObject angelPlan = new JSONObject();

    //Todo 家教奖励相关字段


    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public JSONObject getImages() {
        return images;
    }

    public void setImages(JSONObject images) {
        this.images = images;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getHadTeach() {
        return hadTeach;
    }

    public void setHadTeach(String hadTeach) {
        this.hadTeach = hadTeach;
    }

    public String getTeachCount() {
        return teachCount;
    }

    public void setTeachCount(String teachCount) {
        this.teachCount = teachCount;
    }

    public int getTeachTime() {
        return teachTime;
    }

    public void setTeachTime(int teachTime) {
        this.teachTime = teachTime;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getChildAccept() {
        return childAccept;
    }

    public void setChildAccept(float childAccept) {
        this.childAccept = childAccept;
    }

    public float getAbility() {
        return ability;
    }

    public void setAbility(float ability) {
        this.ability = ability;
    }

    public int getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(int commentTime) {
        this.commentTime = commentTime;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setIsLock(boolean isLock) {
        this.isLock = isLock;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public List<MultiBookTime> getMultiBookTime() {
        return multiBookTime;
    }

    public void setMultiBookTime(List<MultiBookTime> multiBookTime) {
        this.multiBookTime = multiBookTime;
    }

    public List<SingleBookTime> getSingleBookTime() {
        return singleBookTime;
    }

    public void setSingleBookTime(List<SingleBookTime> singleBookTime) {
        this.singleBookTime = singleBookTime;
    }

    public List<String> getCourse() {
        return course;
    }

    public void setCourse(List<String> course) {
        this.course = course;
    }

    public int getMinCourseTime() {
        return minCourseTime;
    }

    public void setMinCourseTime(int minCourseTime) {
        this.minCourseTime = minCourseTime;
    }

    public int getFreeTrafficTime() {
        return freeTrafficTime;
    }

    public void setFreeTrafficTime(int freeTrafficTime) {
        this.freeTrafficTime = freeTrafficTime;
    }

    public int getMaxTrafficTime() {
        return maxTrafficTime;
    }

    public void setMaxTrafficTime(int maxTrafficTime) {
        this.maxTrafficTime = maxTrafficTime;
    }

    public float getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(float subsidy) {
        this.subsidy = subsidy;
    }

    public JSONObject getAngelPlan() {
        return angelPlan;
    }

    public void setAngelPlan(JSONObject angelPlan) {
        this.angelPlan = angelPlan;
    }
}
