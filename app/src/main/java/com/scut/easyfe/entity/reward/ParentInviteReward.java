package com.scut.easyfe.entity.reward;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;

import java.util.Locale;

/**
 * 家长的邀请奖励
 * Created by jay on 16/5/3.
 */
public class ParentInviteReward extends BaseReward{
    private String phone = "";
    private int canGetCount = 2;   //达到可以领取的次数
    private float money = 0f;


    @Override
    public SpannableStringBuilder getAsString() {
        String content = "已邀请手机号码: ";
        content += phone;
        content += "\n";
        content += "已完成订单次数: ";
        content += String.format(Locale.CHINA, "%d次 / %d次", count, canGetCount);
        content += "\n";
        content += "现金券奖励: ";

        int start = content.length();
        content += String.format(Locale.CHINA, "%.0f 元", money / 100);
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(new ForegroundColorSpan(App.get().getResources().getColor(R.color.theme_color)), start, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    @Override
    public boolean isReceivable() {
        return canGet && count >= canGetCount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCanGetCount() {
        return canGetCount;
    }

    public void setCanGetCount(int canGetCount) {
        this.canGetCount = canGetCount;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
