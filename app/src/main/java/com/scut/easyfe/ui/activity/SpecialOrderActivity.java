package com.scut.easyfe.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.fragment.SpecialOrderFragment;

public class SpecialOrderActivity extends BaseActivity {

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_special_order);
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.titlebar_tv_title)).setText("特价订单");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.special_order_fl_container, new SpecialOrderFragment())
                .commit();
    }

    public void onBackClick(View view){
        finish();
    }
}
