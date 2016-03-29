package com.scut.easyfe.ui.activity;

import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.base.BaseActivity;

public class ContactUsActivity extends BaseActivity {
    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_contact_us);
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.titlebar_tv_title)).setText("联系我们");
    }
}
