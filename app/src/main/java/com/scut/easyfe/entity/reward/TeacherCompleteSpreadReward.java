package com.scut.easyfe.entity.reward;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;

import java.util.ArrayList;

/**
 * 教师完成特价订单课室奖励
 * Created by jay on 16/5/3.
 */
public class TeacherCompleteSpreadReward extends BaseReward{
    private String description = "";
    private float money = 0f;

    public static ArrayList<BaseReward> getTestRewards(){
        ArrayList<BaseReward> rewards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TeacherCompleteSpreadReward reward = new TeacherCompleteSpreadReward();
            reward.setDescription("完成"+ (i * 10 + 10) + " 小时课时");
            reward.setMoney(i * 10 + 10);
            rewards.add(reward);
        }

        rewards.get(0).setReceivable(true);

        return rewards;
    }

    @Override
    public SpannableStringBuilder getAsString() {
        String content = "";
        content += description;
        content += "\n";
        content += "现金奖励: ";

        int start = content.length();
        content += String.format("%.0f 元", money);
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(new ForegroundColorSpan(App.get().getResources().getColor(R.color.theme_color)), start, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
