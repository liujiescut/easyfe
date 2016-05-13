package com.scut.easyfe.entity.reward;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;

import java.util.ArrayList;

/**
 * 完成课时现金券及积分奖励
 * Created by jay on 16/5/3.
 */
public class TeacherCourseReward extends BaseReward{
    private int time = 0;
    private float addPrice = 0f;
    private String grade = "";
    private String course = "";


    public static ArrayList<BaseReward> getTestRewards(){
        ArrayList<BaseReward> rewards = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            TeacherCourseReward reward = new TeacherCourseReward();
            reward.setTime(10 + 20 * i);
            reward.setGrade("大学三年级");
            reward.setCourse("软件需求分析");
            rewards.add(reward);
        }


        return rewards;
    }

    @Override
    public SpannableStringBuilder getAsString() {
        String content = "已授课科目及年级: ";
        content += grade + course;
        content += "\n";
        content += "已完成课时: ";
        content += String.format("%d 小时", time);

        return new SpannableStringBuilder(content);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(float addPrice) {
        this.addPrice = addPrice;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
