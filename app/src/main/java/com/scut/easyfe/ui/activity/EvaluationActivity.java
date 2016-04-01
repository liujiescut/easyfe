package com.scut.easyfe.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

public class EvaluationActivity extends BaseActivity {

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_evaluation);
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("已完成订单");
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_right)).setText("保存修改");
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_right)).setVisibility(View.VISIBLE);
    }
}
