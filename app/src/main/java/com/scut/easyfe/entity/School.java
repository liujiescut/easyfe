package com.scut.easyfe.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 家教学校
 * Created by jay on 16/4/8.
 */
public class School extends BaseEntity{
    private String _id = "";
    private String school = "";
    private List<String> profession = new ArrayList<>();

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public List<String> getProfession() {
        return profession;
    }

    public void setProfession(List<String> profession) {
        this.profession = profession;
    }
}
