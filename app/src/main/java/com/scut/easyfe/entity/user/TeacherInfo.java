package com.scut.easyfe.entity.user;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Address;
import com.scut.easyfe.entity.BaseEntity;

/**
 * 用于解析家教信息
 * Created by jay on 16/4/14.
 */
public class TeacherInfo extends BaseEntity {
    private String name = "";
    private String phone = "";
    private String _id = "";
    private String avatar = "";
    private float addPrice = 0f;
    private int gender = Constants.Identifier.FEMALE;
    private long birthday = 0;
    private Teacher teacherMessage = new Teacher();
    private Address position = new Address();

    public float getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(float addPrice) {
        this.addPrice = addPrice;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

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