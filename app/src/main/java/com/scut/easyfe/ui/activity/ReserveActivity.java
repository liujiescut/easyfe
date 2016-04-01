package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.MyTimePicker;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.ToSelectItem;
import com.scut.easyfe.ui.adapter.SelectItemAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.ListViewUtil;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    private OptionsPickerView<String> mSinglePicker;
    private OptionsPickerView<String> mDoublePicker;
    private MyTimePicker mTimePicker;
    private TimePickerView mDatePicker;

    private String mDateString = "";

    private int mReserveType = Constants.Identifier.RESERVE_MULTI;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_reserve);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(null != intent){
            Bundle extras = intent.getExtras();
            if(null != extras){
                mReserveType = extras.getInt(Constants.Key.RESERVE_WAY, Constants.Identifier.RESERVE_MULTI);
            }
        }

        mSchoolItems = new ArrayList<>();
        mPriceItems = new ArrayList<>();
        mScoreItems = new ArrayList<>();

        for (String school :
                Constants.Data.schoolList) {
            mSchoolItems.add(new ToSelectItem(school, false));
        }

        for (String score :
                Constants.Data.scoreRangeList) {
            mScoreItems.add(new ToSelectItem(score, false));
        }

        mPriceItems.add(new ToSelectItem("50以下", false));
        mPriceItems.add(new ToSelectItem("50 - 80", false));
        mPriceItems.add(new ToSelectItem("80 - 100", false));
        mPriceItems.add(new ToSelectItem("100 - 120", false));
        mPriceItems.add(new ToSelectItem("120 - 140", false));
        mPriceItems.add(new ToSelectItem("140 - 160", false));
        mPriceItems.add(new ToSelectItem("160 - 180", false));

    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText(
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

        mDoublePicker = new OptionsPickerView<>(this);
        mDoublePicker.setCancelable(true);

        mSinglePicker = new OptionsPickerView<>(this);
        mSinglePicker.setCancelable(true);

        mTimePicker = new MyTimePicker(this);

        Calendar calendar = Calendar.getInstance();
        mDatePicker = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        mDatePicker.setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR)); //控制时间范围
        mDatePicker.setTime(new Date());
        mDatePicker.setCyclic(false);

        mGradeTextView.setText(String.format("%s %s",
                Constants.Data.studentStateList.get(0),
                Constants.Data.studentGradeList.get(0).get(0)));

        mCourseTextView.setText(String.format("%s(%s)",
                Constants.Data.courseList.get(0),
                Constants.Data.courseGradeList.get(0).get(0)));
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

        mTimePicker.setOnPickListener(new MyTimePicker.OnPickListener() {
            @Override
            public void onPick(int hour, int minute) {
                String timeString = String.format("%s 小时 %s 分钟", hour, minute);
                int timeInt = hour * 60 + minute;
                mTeachTimeTextView.setText(timeString);
            }
        });

        mDatePicker.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                mDateString = OtherUtils.getTime(date, "yyyy 年 MM 月 dd 日 (EEEE)");
                LogUtils.i(Constants.Tag.ORDER_TAG, mDateString);
            }
        });

        mDatePicker.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
                if ("".equals(mDateString)) {
                    return;
                }

                showPeriodSelectView();
            }
        });
    }

    private void showPeriodSelectView(){
        new AlertView("时间段", null, null, null,
                new String[]{"上午", "下午", "晚上"},
                ReserveActivity.this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {
                    mDateString += " 上午";
                } else if (position == 1) {
                    mDateString += " 下午";
                } else if (position == 2) {
                    mDateString += " 晚上";
                } else {
                    mDateString = "";
                }
                mTeachDateTextView.setText(mDateString);
            }
        }).show();
    }

    public void onGradeClick(View view){
        OtherUtils.hideSoftInputWindow(mGradeTextView.getWindowToken());
        if (mDoublePicker.isShowing()) {
            mDoublePicker.dismiss();
            return;
        }

        mDoublePicker.setTitle("选择授课年级");
        mDoublePicker.setPicker(Constants.Data.studentStateList, Constants.Data.studentGradeList, true);
        mDoublePicker.setSelectOptions(0, 0);
        mDoublePicker.setCyclic(false);
        mDoublePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mGradeTextView.setText(String.format("%s %s",
                        Constants.Data.studentStateList.get(options1),
                        Constants.Data.studentGradeList.get(options1).get(option2)));
            }
        });
        mDoublePicker.show();
    }

    public void onCourseClick(View view){
        OtherUtils.hideSoftInputWindow(mCourseTextView.getWindowToken());
        if (mDoublePicker.isShowing()) {
            mDoublePicker.dismiss();
            return;
        }

        mDoublePicker.setTitle("选择授课课程");
        mDoublePicker.setPicker(Constants.Data.courseList, Constants.Data.courseGradeList, true);
        mDoublePicker.setSelectOptions(0, 0);
        mDoublePicker.setCyclic(false);
        mDoublePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mCourseTextView.setText(String.format("%s(%s)",
                        Constants.Data.courseList.get(options1),
                        Constants.Data.courseGradeList.get(options1).get(option2)));
            }
        });
        mDoublePicker.show();
    }

    public void onDateClick(View view){
        OtherUtils.hideSoftInputWindow(mTeachDateTextView.getWindowToken());
        if(mReserveType == Constants.Identifier.RESERVE_SINGLE) {
            mDatePicker.show();
        }else{
            mSinglePicker.setTitle("每周授课时间");
            mSinglePicker.setPicker(Constants.Data.weekList);
            mSinglePicker.setSelectOptions(0);
            mSinglePicker.setCyclic(false);
            mSinglePicker.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(Object o) {
                    if ("".equals(mDateString)) {
                        return;
                    }

                    showPeriodSelectView();
                }
            });
            mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    mDateString = Constants.Data.weekList.get(options1);
                }
            });
            mSinglePicker.show();
        }
    }

    public void onTimeClick(View view){
        OtherUtils.hideSoftInputWindow(mTeachTimeTextView.getWindowToken());
        mTimePicker.setTitle("最短授课时长");
        mTimePicker.show();
    }

    public void onStudentAgeClick(View view){
        OtherUtils.hideSoftInputWindow(mStudentAgeTextView.getWindowToken());
        if (mSinglePicker.isShowing()) {
            mSinglePicker.dismiss();
            return;
        }

        mSinglePicker.setTitle("被授课对象年龄");
        mSinglePicker.setPicker(Constants.Data.ageList);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setOnDismissListener(null);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mStudentAgeTextView.setText(Constants.Data.ageList.get(options1));
            }
        });
        mSinglePicker.show();
    }

    public void onStudentGenderClick(View view){
        OtherUtils.hideSoftInputWindow(mStudentAgeTextView.getWindowToken());
        if (mSinglePicker.isShowing()) {
            mSinglePicker.dismiss();
            return;
        }

        mSinglePicker.setTitle("被授课对象性别");
        mSinglePicker.setPicker(Constants.Data.genderList);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setOnDismissListener(null);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mStudentGenderTextView.setText(Constants.Data.genderList.get(options1));
            }
        });
        mSinglePicker.show();
    }

    public void onSearchClick(View view){
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key.RESERVE_WAY, mReserveType);
        redirectToActivity(mContext, SearchResultActivity.class, bundle);
    }
    public void onBackClick(View view){
        finish();
    }
}
