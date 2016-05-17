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
    private int finishTime = 0;
    private String grade = "";
    private String course = "";


//    public static ArrayList<BaseReward> getTestRewards(){
//        ArrayList<BaseReward> rewards = new ArrayList<>();
//        for (int i = 0; i < 8; i++) {
//            TeacherCourseReward reward = new TeacherCourseReward();
//            reward.setGrade("大学三年级");
//            reward.setCourse("软件需求分析");
//            rewards.add(reward);
//        }
//
//
//        return rewards;
//    }

    @Override
    public SpannableStringBuilder getAsString() {
        String content = "已授课科目及年级: ";
        content += grade + course;
        content += "\n";
        content += "已完成课时: ";
        content += String.format("%d 小时", finishTime);

        return new SpannableStringBuilder(content);
    }

    @Override
    public boolean isReceivable() {
        return canGet;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
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
