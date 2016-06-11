package com.scut.easyfe.entity.order;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.BaseEntity;
import com.scut.easyfe.entity.Comment;
import com.scut.easyfe.entity.user.ParentInfo;
import com.scut.easyfe.entity.user.TeacherInfo;
import com.scut.easyfe.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 普通订单类(用在单双次预约结果解析并在预约流程中通用)
 * Created by jay on 16/4/12.
 */
public class Order extends BaseEntity {

    private String _id = "";
    private String orderNumber = "";                  //订单号, O-2016000000, 开头是字母O
    private int type = 0;                             //0表示普通订单，1表示特价订单
    private int state = 0;                            //0未修改已预订，1待执行，2修改过待确定，3已完成，4无效订单
    private String tag = "";                          //表示订单是否为同一批多次预约订单
    private int time = 120;                           //单次家教的时间
    private String course = "";                       //课程
    private String grade = "";                        //孩子年级
    private float price = 0f;                         //单价
    private float originalPrice = 0f;                 //原价(特价订单用到)
    private int childAge = 0;                         //孩子年龄
    private int childGender =
            Constants.Identifier.FEMALE;              //孩子性别
    private String cancelPerson = "";                 //订单状态为4才有的字段，“teacher”表示是教师取消订单，“parent”表示家长取消
    private boolean hadComment = false;               //是否已经评价
    private boolean hadGetCoupon = false;             //是否已经领取现金券
    private long completedTime = 0;                   //订单完成时间
    private TeachTime teachTime =
            new TeachTime();                          //授课的时间信息
    private TeacherInfo teacher =
            new TeacherInfo();                        //授课老师信息
    private ParentInfo parent =
            new ParentInfo();                         //授课家长信息
    private Insurance insurance =
            new Insurance();                          //授课保险信息
    private float subsidy = 5;                        //超过交通时间，收的交通补贴
    private float addPrice = 0;                       //家教完成课时单价增加的增加单价
    private List<Comment> comments =
            new ArrayList<>();                        //授课老师的前三条评论
    private float professionalTutorPrice = 0f;        //专业辅导单位价格
    private TutorDetail thisTeachDetail =
            new TutorDetail();                        //本次专业辅导信息
    private TutorDetail nextTeachDetail =
            new TutorDetail();                        //下次专业辅导信息
    private boolean isTeacherReport = false;          //家教是否完成课时并反馈
    private Coupon coupon = new Coupon();             //优惠券信息

    private int trafficTime = 0;                      //交通时间(客户端计算)

    /**
     * 是否需要显示专业辅导(初中高中才显示)
     */
    public boolean isProfessionTutorShow(){
        return grade.contains("初中") || grade.contains("高中");
    }

    public boolean hasProfessionTutor(){
        return professionalTutorPrice > 0;
    }

    public float getPerPrice(){
        return price + addPrice;
    }

    public float getTotalPrice(){
        return (price + addPrice + professionalTutorPrice) * ((float)time / 60) + subsidy - coupon.money;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public static String getBaseInfo(Order order){
        String content = "";
        content += String.format(Locale.CHINA, "性别: %s\n", order.getTeacher().getGender() == Constants.Identifier.MALE ? "男" : "女");
        content += String.format(Locale.CHINA, "年龄: %d\n", TimeUtils.getAgeFromBirthday(order.getTeacher().getBirthday()));
        content += String.format(Locale.CHINA, "大学专业: %s %s\n", order.getTeacher().getTeacherMessage().getSchool(), order.getTeacher().getTeacherMessage().getProfession());
        content += String.format(Locale.CHINA, "已家教过的孩子数量：%s\n", order.getTeacher().getTeacherMessage().getTeachCount());
        content += String.format(Locale.CHINA, "已家教的时长：%s\n", order.getTeacher().getTeacherMessage().getHadTeach());
        content += String.format(Locale.CHINA, "综合评分：%.2f", order.getTeacher().getTeacherMessage().getScore());
        return content;
    }


    public boolean isIsTeacherReport() {
        return isTeacherReport;
    }

    public void setIsTeacherReport(boolean teacherReport) {
        isTeacherReport = teacherReport;
    }


    public float getProfessionalTutorPrice() {
        return professionalTutorPrice;
    }

    public void setProfessionalTutorPrice(float tutorPrice) {
        this.professionalTutorPrice = tutorPrice;
    }

    public boolean isHadGetCoupon() {
        return hadGetCoupon;
    }

    public void setHadGetCoupon(boolean hadGetCoupon) {
        this.hadGetCoupon = hadGetCoupon;
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

    public TutorDetail getThisTeachDetail() {
        return thisTeachDetail;
    }

    public void setThisTeachDetail(TutorDetail thisTeachDetail) {
        this.thisTeachDetail = thisTeachDetail;
    }

    public TutorDetail getNextTeachDetail() {
        return nextTeachDetail;
    }

    public void setNextTeachDetail(TutorDetail nextTeachDetail) {
        this.nextTeachDetail = nextTeachDetail;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
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

    /**
     * 专业辅导信息
     */
    public static class TutorDetail extends BaseEntity{
        // 阶段: 高考, 中考, 高中, 初中
        private String category = "";
        // 课程: 地理...
        private String course = "";
        // 辅导方式: 针对知识点补习, 复习模拟卷
        private int teachWay = Constants.Identifier.TUTOR_WAY_KNOWLEDGE;
        // 年级, 辅导方式为: 复习模拟卷
        private String grade = "";
        // 复习模拟卷
        private String examPaper = "";
        // 难度
        private String easyLevel = "";
        // 知识点, 辅导方式为: 针对知识点补习时才有的字段
        private List<String> knowledge = new ArrayList<>();

        public JSONObject getAsJson(){
            JSONObject json = new JSONObject();
            try {
                json.put("category", category);
                json.put("course", course);
                json.put("teachWay", teachWay);
                json.put("grade", grade);
                json.put("examPaper", examPaper);
                json.put("easyLevel", easyLevel);
                json.put("knowledge", new JSONArray(knowledge));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return json;
        }

        public boolean hadFillIn(){
            //Todo 换一种更合适的判断
            return category.length() != 0;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public int getTeachWay() {
            return teachWay;
        }

        public void setTeachWay(int teachWay) {
            this.teachWay = teachWay;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getExamPaper() {
            return examPaper;
        }

        public void setExamPaper(String examPaper) {
            this.examPaper = examPaper;
        }

        public String getEasyLevel() {
            return easyLevel;
        }

        public void setEasyLevel(String easyLevel) {
            this.easyLevel = easyLevel;
        }

        public List<String> getKnowledge() {
            return knowledge;
        }

        public void setKnowledge(List<String> knowledge) {
            this.knowledge = knowledge;
        }
    }

    public class Coupon extends BaseEntity{
        private String _id = "";
        private float money = 0f;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public float getMoney() {
            return money;
        }

        public void setMoney(float money) {
            this.money = money;
        }
    }
}
