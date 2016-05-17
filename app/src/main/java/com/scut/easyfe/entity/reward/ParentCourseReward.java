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
public class ParentCourseReward extends BaseReward {
    private int time = 0;

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    private float money = 0f;
    private float score = 0f;

    @Override
    public SpannableStringBuilder getAsString() {
        String content = "完成课时要求: ";
        content += String.format("%d 小时", time);
        content += "\n";
        content += "现金券奖励: ";

        int start1 = content.length();
        String moneyReward = String.format("%.0f 元", money);
        content += moneyReward;

        content += "\n";
        content += "积分奖励: ";
        int start2 = content.length();
        String integralReward = String.format("%.0f 分", score);
        content += integralReward;

        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(new ForegroundColorSpan(App.get().getResources().getColor(R.color.theme_color)),
                start1, start1 + moneyReward.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(App.get().getResources().getColor(R.color.theme_color)),
                start2, start2 + integralReward.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }

    @Override
    public boolean isReceivable() {
        return canGet;
    }
}
