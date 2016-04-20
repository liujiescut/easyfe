package com.scut.easyfe.entity.user;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.BaseEntity;

/**
 * 家长属性类
 * Created by jay on 16/4/5.
 */
public class Parent extends BaseEntity{
    //预约次数
    private int bookCount = 0;

    //孩子年级
    private String childGrade = "";

    //孩子性别
    private int childGender = Constants.Identifier.FEMALE;

    private int childAge = 10;

    //Todo 奖励相关


    public int getChildAge() {
        return childAge;
    }

    public void setChildAge(int childAge) {
        this.childAge = childAge;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public String getChildGrade() {
        return childGrade;
    }

    public void setChildGrade(String childGrade) {
        this.childGrade = childGrade;
    }

    public int getChildGender() {
        return childGender;
    }

    public void setChildGender(int childGender) {
        this.childGender = childGender;
    }
}
