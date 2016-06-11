package com.scut.easyfe.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.ui.base.BaseActivity;

/**
 * 家教反馈报告
 */
public class TeacherReportActivity extends BaseActivity {
    private Order mOrder;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_teacher_report);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mOrder = (Order) extras.getSerializable(Constants.Key.ORDER);
            } else {
                mOrder = new Order();
            }
        } else {
            mOrder = new Order();
        }
    }
}
