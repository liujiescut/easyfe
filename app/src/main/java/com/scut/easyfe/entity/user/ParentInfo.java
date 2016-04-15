package com.scut.easyfe.entity.user;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Address;
import com.scut.easyfe.entity.BaseEntity;

/**
 * 用于解析家长信息
 * Created by jay on 16/4/14.
 */
public class ParentInfo extends BaseEntity {
    private String name = "";
    private String phone = "";
    private String avatar = "";
    private int gender = Constants.Identifier.FEMALE;
    private Parent parentMessage = new Parent();
    private Address position = new Address();

    public Address getPosition() {
        return position;
    }

    public void setPosition(Address position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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
