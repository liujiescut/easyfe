package com.scut.easyfe.entity.reward;

import android.text.SpannableStringBuilder;

import com.scut.easyfe.entity.BaseEntity;

/**
 * 奖励的基类
 * Created by jay on 16/5/2.
 */
public abstract class BaseReward extends BaseEntity{
    private String _id = "";

    private boolean receivable = false;

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
        return receivable;
    }

    public void setReceivable(boolean receivable) {
        this.receivable = receivable;
    }
}
