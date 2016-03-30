package com.scut.easyfe.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.ToSelectItem;
import com.scut.easyfe.ui.adapter.SelectItemAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.ListViewUtil;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;

/**
 * 预约界面
 */
public class ReserveActivity extends BaseActivity {
    private TextView mGradeTextView;
    private TextView mCourseTextView;
    private TextView mTeachDateLabelTextView;
    private TextView mTeachDateTextView;
    private TextView mTeachTimeLabelTextView;
    private TextView mTeachTimeTextView;
    private TextView mStudentAgeTextView;
    private TextView mStudentGenderTextView;
    private TextView mSchoolLabelTextView;
    private TextView mPriceLabelTextView;
    private TextView mScoreLabelTextView;
    private ListView mSchoolListView;
    private ListView mPriceListView;
    private ListView mScoreListView;
    private ScrollView mContainerScrollView;
    ArrayList<ToSelectItem> mSchoolItems;
    ArrayList<ToSelectItem> mPriceItems;
    ArrayList<ToSelectItem> mScoreItems;

    private int mReserveType = Constants.Identifier.RESERVE_MULTI;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_reserve);
    }

    @Override
    protected void initData() {
        mSchoolItems = new ArrayList<>();
        mPriceItems = new ArrayList<>();
        mScoreItems = new ArrayList<>();

        for (String school :
                Constants.Data.schoolList) {
            mSchoolItems.add(new ToSelectItem(school, false));
        }

        mPriceItems.add(new ToSelectItem("50以下", false));
        mPriceItems.add(new ToSelectItem("50 - 80", false));
        mPriceItems.add(new ToSelectItem("80 - 100", false));
        mPriceItems.add(new ToSelectItem("100 - 120", false));
        mPriceItems.add(new ToSelectItem("120 - 140", false));
        mPriceItems.add(new ToSelectItem("140 - 160", false));
        mPriceItems.add(new ToSelectItem("160 - 180", false));

        mScoreItems.add(new ToSelectItem("3分以下", false));
        mScoreItems.add(new ToSelectItem("3分 - 4分", false));
        mScoreItems.add(new ToSelectItem("4分 - 5分", false));
        mScoreItems.add(new ToSelectItem("5分 - 6分", false));
        mScoreItems.add(new ToSelectItem("6分 - 7分", false));
        mScoreItems.add(new ToSelectItem("7分 - 8分", false));
        mScoreItems.add(new ToSelectItem("8分 - 9分", false));
        mScoreItems.add(new ToSelectItem("10以上", false));
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.titlebar_tv_title)).setText(
                mReserveType == Constants.Identifier.RESERVE_MULTI ? "多次预约" : "单次预约");

        mGradeTextView = OtherUtils.findViewById(this, R.id.reserve_tv_grade);
        mCourseTextView = OtherUtils.findViewById(this, R.id.reserve_tv_course);
        mTeachDateTextView = OtherUtils.findViewById(this, R.id.reserve_tv_date);
        mTeachDateLabelTextView = OtherUtils.findViewById(this, R.id.reserve_tv_date_label);
        mTeachTimeTextView = OtherUtils.findViewById(this, R.id.reserve_tv_time);
        mTeachTimeLabelTextView = OtherUtils.findViewById(this, R.id.reserve_tv_time_label);
        mStudentAgeTextView = OtherUtils.findViewById(this, R.id.reserve_tv_student_age);
        mStudentGenderTextView = OtherUtils.findViewById(this, R.id.reserve_tv_student_gender);
        mSchoolLabelTextView = OtherUtils.findViewById(this, R.id.reserve_tv_school_label);
        mPriceLabelTextView = OtherUtils.findViewById(this, R.id.reserve_tv_price_label);
        mScoreLabelTextView = OtherUtils.findViewById(this, R.id.reserve_tv_score_label);
        mSchoolListView = OtherUtils.findViewById(this, R.id.reserve_lv_school);
        mPriceListView = OtherUtils.findViewById(this, R.id.reserve_lv_price);
        mScoreListView = OtherUtils.findViewById(this, R.id.reserve_lv_score);
        mContainerScrollView = OtherUtils.findViewById(this, R.id.reserve_sv_container);

        mSchoolListView.setAdapter(new SelectItemAdapter(this, mSchoolItems));
        mPriceListView.setAdapter(new SelectItemAdapter(this, mPriceItems));
        mScoreListView.setAdapter(new SelectItemAdapter(this, mScoreItems));
        ListViewUtil.setListViewHeightBasedOnChildren(mSchoolListView);
        ListViewUtil.setListViewHeightBasedOnChildren(mPriceListView);
        ListViewUtil.setListViewHeightBasedOnChildren(mScoreListView);

        if(mReserveType == Constants.Identifier.RESERVE_MULTI){
            mTeachDateLabelTextView.setText("每周授课时间");
            mTeachTimeLabelTextView.setText("最短授课时长");
        }

        mContainerScrollView.smoothScrollTo(0, 0);
    }

    @Override
    protected void initListener() {
        mSchoolLabelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSchoolLabelTextView.setBackgroundResource(R.mipmap.reserve_selected_bg);
                mPriceLabelTextView.setBackgroundResource(R.mipmap.reserve_unselected_bg);
                mScoreLabelTextView.setBackgroundResource(R.mipmap.reserve_unselected_bg);
                mSchoolListView.setVisibility(View.VISIBLE);
                mPriceListView.setVisibility(View.GONE);
                mScoreListView.setVisibility(View.GONE);
            }
        });

        mPriceLabelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSchoolLabelTextView.setBackgroundResource(R.mipmap.reserve_unselected_bg);
                mPriceLabelTextView.setBackgroundResource(R.mipmap.reserve_selected_bg);
                mScoreLabelTextView.setBackgroundResource(R.mipmap.reserve_unselected_bg);
                mSchoolListView.setVisibility(View.GONE);
                mPriceListView.setVisibility(View.VISIBLE);
                mScoreListView.setVisibility(View.GONE);
            }
        });

        mScoreLabelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSchoolLabelTextView.setBackgroundResource(R.mipmap.reserve_unselected_bg);
                mPriceLabelTextView.setBackgroundResource(R.mipmap.reserve_unselected_bg);
                mScoreLabelTextView.setBackgroundResource(R.mipmap.reserve_selected_bg);
                mSchoolListView.setVisibility(View.GONE);
                mPriceListView.setVisibility(View.GONE);
                mScoreListView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onBackClick(View view){
        finish();
    }
}
