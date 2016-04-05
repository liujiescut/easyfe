package com.scut.easyfe.entity.user;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Address;
import com.scut.easyfe.entity.BaseEntity;
import com.scut.easyfe.app.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户基本信息类(家长家教共有的)
 * Created by jay on 16/4/1.
 */
public class User extends BaseEntity{
    //用户id
    private String _id = "";
    //用户姓名
    private String name = "";
    //用户手机
    private String phone = "";
    //用户密码
    private String password = "";
    //用户类型
    private int type = Constants.Identifier.USER_TEACHER;
    //用户头像
    private String avatar = "";
    //用户地址(包括地址名称,经纬度)
    private Address position = new Address();
    //用户出生日期
    private String birthday = "";
    //用户不良记录次数
    private int badRecord = 0;
    //用户邀请的用户的_id列表
    private List<String> invitePerson = new ArrayList<>();
    //家长信息
    private Parent parentMessage = new Parent();
    //家教信息
    private Teacher teacherMessage = new Teacher();

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Address getPosition() {
        return position;
    }

    public void setPosition(Address position) {
        this.position = position;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getBadRecord() {
        return badRecord;
    }

    public void setBadRecord(int badRecord) {
        this.badRecord = badRecord;
    }

    public List<String> getInvitePerson() {
        return invitePerson;
    }

    public void setInvitePerson(List<String> invitePerson) {
        this.invitePerson = invitePerson;
    }

    public Parent getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(Parent parentMessage) {
        this.parentMessage = parentMessage;
    }

    public Teacher getTeacherMessage() {
        return teacherMessage;
    }

    public void setTeacherMessage(Teacher teacherMessage) {
        this.teacherMessage = teacherMessage;
    }
}
