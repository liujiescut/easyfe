package com.scut.easyfe.entity.reward;

import android.text.SpannableStringBuilder;

/**
 * Created by jay on 16/5/12.
 */
@Deprecated
public class SpreadReward extends BaseReward{
    @Override
    public SpannableStringBuilder getAsString() {
        return null;
    }

    @Override
    public boolean isReceivable() {
        return getCount() > 0;
    }
}
