package com.scut.easyfe.entity.user;

import com.scut.easyfe.entity.BaseEntity;
import com.scut.easyfe.entity.TeachableCourse;
import com.scut.easyfe.entity.book.MultiBookTime;
import com.scut.easyfe.entity.book.SingleBookTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 家教属性类
 * Created by jay on 16/4/5.
 */
public class Teacher extends BaseEntity {
    //家教身份证号码
    private String idCard = "";

    //家教个人简介
    private String profile = "";

    //家教上传的图片(身份证正反面跟免冠照片)
    private CheckImage images = new CheckImage();

    //家教等级
    private int level = 0;

    //已家教时长(家教手动输入)
    private String hadTeach = "";

    //已家教孩子数量(家教手动输入)
    private String teachCount = "";

    //平台上家教时长(平台记录)
    private int teachTime = 0;

    //综合评分
    private float score = 0f;

    //孩子喜欢程度
    private float childAccept = 0f;

    //专业胜任程度
    float ability = 0f;

    //准时程度
    float punctualScore = 0f;

    //被评论次数
    private int commentTime = 0;

    //是否可以被查找到
    private boolean isLock = false;

    //家教所在学校
    private String school = "";

    //家教所在年级
    private String grade = "";

    //家教所在专业
    private String profession = "";

    //多次预约时间
    private List<MultiBookTime> multiBookTime = new ArrayList<>();

    //单词预约时间
    private List<SingleBookTime> singleBookTime = new ArrayList<>();

    //可教授课程
    private List<TeachableCourse> teacherPrice = new ArrayList<>();

    //最短授课时间
    private int minCourseTime = 120;

    //不收交通补贴的最长交通时间
    private int freeTrafficTime = 120;

    //交通时间超过此时间不接单
    private int maxTrafficTime = 180;

    //超过交通时间，收的交通补贴
    private int subsidy = 5;

    //是否已经通过审核
    private boolean isCheck = false;

    //天使计划
    private AngelPlan angelPlan = new AngelPlan();

    //Todo 家教奖励相关字段

    public String getScoreInfo(){
        String scoreInfo = "";
        scoreInfo += String.format("综合评分：%.1f\n", score);
        scoreInfo += String.format("孩子喜欢程度：%.1f\n", childAccept);
        scoreInfo += String.format("专业胜任程度：%.1f\n", ability);
        scoreInfo += String.format("准时态度：%.1f", punctualScore);

        return scoreInfo;
    }

    public boolean isIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean check) {
        isCheck = check;
    }

    public float getPunctualScore() {
        return punctualScore;
    }

    public void setPunctualScore(float punctualScore) {
        this.punctualScore = punctualScore;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

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

    public CheckImage getImages() {
        return images;
    }

    public void setImages(CheckImage images) {
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

    public List<TeachableCourse> getTeacherPrice() {
        return teacherPrice;
    }

    public void setTeacherPrice(List<TeachableCourse> teacherPrice) {
        this.teacherPrice = teacherPrice;
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

    public int getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(int subsidy) {
        this.subsidy = subsidy;
    }

    public AngelPlan getAngelPlan() {
        return angelPlan;
    }

    public void setAngelPlan(AngelPlan angelPlan) {
        this.angelPlan = angelPlan;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * 用于审核的图片
     */
    public class CheckImage implements Serializable {
        private String idCard = "";
        private String studentCard = "";
        private String official = "";

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getStudentCard() {
            return studentCard;
        }

        public void setStudentCard(String studentCard) {
            this.studentCard = studentCard;
        }

        public String getOfficial() {
            return official;
        }

        public void setOfficial(String official) {
            this.official = official;
        }

        public JSONObject getImageJson(){
            JSONObject imageJson = new JSONObject();
            try {
                imageJson.put("idCard", idCard);
                imageJson.put("studentCard", studentCard);
                imageJson.put("official", official);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return imageJson;
        }
    }

    public class AngelPlan implements Serializable {
        private boolean join = false;
        private int boy = 0;
        private int girl = 0;
        private int price = 0;

        public boolean isJoin() {
            return join;
        }

        public void setJoin(boolean join) {
            this.join = join;
        }

        public int getBoy() {
            return boy;
        }

        public void setBoy(int boy) {
            this.boy = boy;
        }

        public int getGirl() {
            return girl;
        }

        public void setGirl(int girl) {
            this.girl = girl;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public JSONObject getAngelPlanJson(){
            JSONObject json = new JSONObject();
            try {
                json.put("join", join);
                json.put("boy", boy);
                json.put("girl", girl);
                json.put("price", price);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }
    }

    public JSONArray getTeachCourseJsonArray() {
        JSONArray teachCourseArray = new JSONArray();
        try {
            for (TeachableCourse course :
                    teacherPrice) {
                JSONObject courseJson = new JSONObject();
                courseJson.put("course", course.getCourse());
                courseJson.put("grade", course.getGrade());
                courseJson.put("price", course.getPrice());

                teachCourseArray.put(courseJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return teachCourseArray;
    }

    public JSONObject getTeacherJson(){
        JSONObject teacherJson = new JSONObject();
        try {
            teacherJson.put("idCard", idCard);
            teacherJson.put("images", images.getImageJson());
            teacherJson.put("school", school);
            teacherJson.put("profession", profession);
            teacherJson.put("hadTeach", hadTeach);
            teacherJson.put("teachCount", teachCount);
            teacherJson.put("teachTime", teachTime);
            teacherJson.put("singleBookTime", getSingleBookTimeArray());
            teacherJson.put("multiBookTime", getMultiBookTimeArray());
            teacherJson.put("minCourseTime", minCourseTime);
            teacherJson.put("freeTrafficTime", freeTrafficTime);
            teacherJson.put("maxTrafficTime", maxTrafficTime);
            teacherJson.put("subsidy", subsidy);
            teacherJson.put("angelPlan", angelPlan.getAngelPlanJson());
            teacherJson.put("profile", profile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return teacherJson;
    }

    public JSONArray getSingleBookTimeArray(){
        JSONArray jsonArray = new JSONArray();
        for (SingleBookTime time :
                singleBookTime) {
            jsonArray.put(time.getSingleBookTimeJson());
        }
        return jsonArray;
    }


    public JSONArray getMultiBookTimeArray(){
        JSONArray jsonArray = new JSONArray();
        for (MultiBookTime time :
                multiBookTime) {
            jsonArray.put(time.getMultiBookTimeJson());
        }
        return jsonArray;
    }
}
