package com.scut.easyfe.entity;

/**
 * 用户等级
 * Created by jay on 16/6/10.
 */
public class UserLevel extends BaseEntity{
    private String level = "";
    private int time = 0;
    private int score = 0;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
