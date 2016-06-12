package com.scut.easyfe.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.activity.order.SearchSpreadActivity;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.fragment.SpreadFragment;
import com.scut.easyfe.utils.OtherUtils;

public class FeedbackReportActivity extends BaseActivity {
    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_feedback_report);
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("反馈报告");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.feedback_report_fl_container, new SpreadFragment())
                .commit();

    }

    public void onBackClick(View view){
        finish();
    }
}
