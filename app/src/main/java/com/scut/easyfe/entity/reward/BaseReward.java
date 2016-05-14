package com.scut.easyfe.entity.reward;

import android.text.SpannableStringBuilder;

import com.scut.easyfe.entity.BaseEntity;

/**
 * 奖励的基类
 * Created by jay on 16/5/2.
 */
public abstract class BaseReward extends BaseEntity{
    private String _id = "";

    protected boolean canGet = false;

    /**
     * 特价订单奖励中代表可领取的次数
     * 邀请有奖中代表被邀请人完成订单的次数
     */
    protected int count = 0;

    /**
     * 将奖励以文本形式显示出来(不同的奖励显示形式自定义)
     * @return 显示文本
     */
    public abstract SpannableStringBuilder getAsString();

    /**
     * 是否可以领取
     */
    public abstract boolean isReceivable();

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
