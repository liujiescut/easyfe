package com.scut.easyfe.entity.reward;

import android.text.SpannableStringBuilder;

import com.scut.easyfe.entity.BaseEntity;

/**
 * 奖励的基类
 * Created by jay on 16/5/2.
 */
public abstract class BaseReward extends BaseEntity{
    private String _id = "";

    private boolean canGet = false;

    private int count = 0;

    /**
     * 将奖励以文本形式显示出来(不同的奖励显示形式自定义)
     * @return 显示文本
     */
    public abstract SpannableStringBuilder getAsString();

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isReceivable() {
        return canGet || count > 0;
    }

    public boolean isCanGet() {
        return canGet;
    }

    public void setCanGet(boolean canGet) {
        this.canGet = canGet;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
