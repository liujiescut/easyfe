package com.scut.easyfe.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 孩子的年级实体类
 * Created by jay on 16/4/10.
 */
public class ChildGrade extends BaseEntity{
    private String _id = "";
    private String name = "";
    private List<String> grade = new ArrayList<>();

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

    public List<String> getGrade() {
        return grade;
    }

    public void setGrade(List<String> grade) {
        this.grade = grade;
    }
}
