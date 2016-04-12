package com.scut.easyfe.entity.order;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Address;
import com.scut.easyfe.entity.BaseEntity;
import com.scut.easyfe.entity.user.Parent;
import com.scut.easyfe.entity.user.Teacher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 普通订单类
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
    private int childAge = 0;            //孩子年龄
    private int childGender = Constants.Identifier.FEMALE; //孩子性别
    private String cancelPerson = "";    //订单状态为4才有的字段，“teacher”表示是教师取消订单，“parent”表示家长取消
    private boolean hadComment = false;  //是否已经评价
    private long completedTime = 0;      //订单完成时间
    private TeachTime teachTime = new TeachTime();
    private TeacherInfo teacher = new TeacherInfo();
    private ParentInfo parent = new ParentInfo();
    private float subsidy = 5;     //超过交通时间，收的交通补贴
    private int trafficeTime = 0;

    public int getTrafficeTime() {
        return trafficeTime;
    }

    public void setTrafficeTime(int trafficeTime) {
        this.trafficeTime = trafficeTime;
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

    public class TeacherInfo implements Serializable {
        String name = "";
        String _id = "";
        int gender = Constants.Identifier.FEMALE;
        long birthday = 0;
        Teacher teacherMessage = new Teacher();
        Address position = new Address();

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public Teacher getTeacherMessage() {
            return teacherMessage;
        }

        public void setTeacherMessage(Teacher teacherMessage) {
            this.teacherMessage = teacherMessage;
        }

        public Address getPosition() {
            return position;
        }

        public void setPosition(Address position) {
            this.position = position;
        }
    }

    public class ParentInfo extends BaseEntity{
        private String name = "";
        private String avatar = "";
        private int gender = Constants.Identifier.FEMALE;
        private Parent parentMessage = new Parent();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public Parent getParentMessage() {
            return parentMessage;
        }

        public void setParentMessage(Parent parentMessage) {
            this.parentMessage = parentMessage;
        }
    }
}
