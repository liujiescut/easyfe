package com.scut.easyfe.ui.customView;

import android.view.View;

import com.scut.easyfe.utils.OtherUtils;

/**
 * 防止快速点击的点击监听器
 * Created by jay on 16/6/12.
 */
public abstract class FixedClickListener implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        if(OtherUtils.isFastDoubleClick()){
            return;
        }

        onFixClick(v);
    }

    public abstract void onFixClick(View view);
}
