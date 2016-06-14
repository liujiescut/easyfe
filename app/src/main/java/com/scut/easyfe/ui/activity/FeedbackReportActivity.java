package com.scut.easyfe.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.activity.order.SearchSpreadActivity;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.fragment.FeedbackReportFragment;
import com.scut.easyfe.ui.fragment.SpreadFragment;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 反馈报告
 * @author jay
 */
public class FeedbackReportActivity extends BaseActivity {
    private TextView mSortByTimeTextView;
    private ImageView mSortByTimeImageView;
    private TextView mSortByNameTextView;
    private ImageView mSortByNameImageView;
    private FeedbackReportFragment mFragment;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_feedback_report);
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("反馈报告");

        mFragment = new FeedbackReportFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.feedback_report_fl_container, mFragment)
                .commit();

        mSortByNameTextView = OtherUtils.findViewById(this, R.id.feedback_report_tv_sort_name);
        mSortByTimeTextView = OtherUtils.findViewById(this, R.id.feedback_report_tv_sort_time);
        mSortByNameImageView = OtherUtils.findViewById(this, R.id.feedback_report_iv_sort_name);
        mSortByTimeImageView = OtherUtils.findViewById(this, R.id.feedback_report_iv_sort_time);
    }

    public void onSortByNameClick(View view) {
        mSortByNameImageView.setVisibility(View.VISIBLE);
        mSortByTimeImageView.setVisibility(View.INVISIBLE);
        mSortByNameTextView.setTextColor(getResources().getColor(R.color.theme_color));
        mSortByTimeTextView.setTextColor(getResources().getColor(R.color.text_area_text_color));
        if (null != mFragment) {
            mFragment.setSortWay(Constants.Identifier.SORT_BY_NAME);
        }
    }

    public void onSortByTimeClick(View view) {
        mSortByNameImageView.setVisibility(View.INVISIBLE);
        mSortByTimeImageView.setVisibility(View.VISIBLE);
        mSortByNameTextView.setTextColor(getResources().getColor(R.color.text_area_text_color));
        mSortByTimeTextView.setTextColor(getResources().getColor(R.color.theme_color));

        if (null != mFragment) {
            mFragment.setSortWay(Constants.Identifier.SORT_BY_TIME);
        }
    }

    public void onBackClick(View view){
        finish();
    }
}
