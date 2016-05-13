package com.scut.easyfe.entity.reward;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;

import java.util.ArrayList;

/**
 * 家长的邀请奖励
 * Created by jay on 16/5/3.
 */
public class ParentInviteReward extends BaseReward{
    private String inviteePhone = "";
    private int totalCount = 2;
    private int completeCount = 0;
    private float money = 0f;

    public static ArrayList<BaseReward> getTestRewards(){
        ArrayList<BaseReward> rewards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ParentInviteReward reward = new ParentInviteReward();
            reward.setInviteePhone("1881411111" + i);
            reward.setTotalCount(2);
            reward.setCompleteCount(i);
            reward.setMoney(5);
            rewards.add(reward);
        }


        return rewards;
    }

    @Override
    public SpannableStringBuilder getAsString() {
        String content = "已邀请手机号码: ";
        content += inviteePhone;
        content += "\n";
        content += "已完成订单次数: ";
        content += String.format("%d次 / %d次", completeCount, totalCount);
        content += "\n";
        content += "现金券奖励: ";

        int start = content.length();
        content += String.format("%.0f 元", money);
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(new ForegroundColorSpan(App.get().getResources().getColor(R.color.theme_color)), start, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public String getInviteePhone() {
        return inviteePhone;
    }

    public void setInviteePhone(String inviteePhone) {
        this.inviteePhone = inviteePhone;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCompleteCount() {
        return completeCount;
    }

    public void setCompleteCount(int completeCount) {
        this.completeCount = completeCount;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
