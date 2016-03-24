package com.scut.easyfe.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.roomorama.caldroid.CalendarHelper;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.customView.SelectorButton;
import com.scut.easyfe.ui.fragment.CalendarFragment;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hirondelle.date4j.DateTime;

/**
 * 近两月实际特别特别安排
 *
 * @author jay
 */
public class SpecialTimeActivity extends BaseActivity {
    private SelectorButton mMorningSelectorButton;
    private SelectorButton mAfternoonSelectorButton;
    private SelectorButton mNightSelectorButton;
    private EditText mRememberThingsEditText;
    private TextView mSelectedDateTextView;
    private LinearLayout mTimeLinearLayout;
    private CalendarFragment mCalendarFragment;
    private long mMinDateTimeMills;
    private long mMaxDateTimeMills;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy 年 MM 月 dd 日 (EEEE)");

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_special_time);
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.titlebar_tv_title)).setText("近两月时间特别安排");
        initCalendar();

        mSelectedDateTextView =OtherUtils.findViewById(this, R.id.special_time_selected_date);
        mTimeLinearLayout = OtherUtils.findViewById(this, R.id.special_time_ll_time);
        mRememberThingsEditText = OtherUtils.findViewById(this, R.id.special_time_et_remember_things);
        mMorningSelectorButton = OtherUtils.findViewById(this, R.id.special_time_sb_morning);
        mAfternoonSelectorButton = OtherUtils.findViewById(this, R.id.special_time_sb_afternoon);
        mNightSelectorButton = OtherUtils.findViewById(this, R.id.special_time_sb_night);
        mMorningSelectorButton.setBothText(getResources().getString(R.string.morning));
        mAfternoonSelectorButton.setBothText(getResources().getString(R.string.afternoon));
        mNightSelectorButton.setBothText(getResources().getString(R.string.night));
        mMorningSelectorButton.setIsSelected(false);
        mAfternoonSelectorButton.setIsSelected(false);
        mNightSelectorButton.setIsSelected(false);
    }

    @Override
    protected void initListener() {
        mCalendarFragment.setCaldroidListener(new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view, int position) {
                LogUtils.i(Constants.Tag.TEACHER_REGISTER_TAG, mFormatter.format(date));
                mSelectedDateTextView.setText(mFormatter.format(date));
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                LogUtils.i(Constants.Tag.TEACHER_REGISTER_TAG, "onChangeMonth\n" + text);
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                LogUtils.i(Constants.Tag.TEACHER_REGISTER_TAG, "onLongClickDate\n" + date);
            }

            @Override
            public void onCaldroidViewCreated() {
                LogUtils.i(Constants.Tag.TEACHER_REGISTER_TAG, "onCaldroidViewCreated");
            }

        });
    }

    /**
     * 初始化日历
     */
    private void initCalendar() {
        mCalendarFragment = new CalendarFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
        args.putBoolean(CaldroidFragment.SHOW_NAVIGATION_ARROWS, false);
        args.putBoolean(CaldroidFragment.ENABLE_CLICK_ON_DISABLED_DATES, false);

        mCalendarFragment.setArguments(args);

        Calendar calendar = Calendar.getInstance();
        Date minDate = calendar.getTime();
        calendar.add(Calendar.MONTH, 2);
        Date maxDate = calendar.getTime();
        mCalendarFragment.setMinDate(minDate);
        mCalendarFragment.setMaxDate(maxDate);
        mMinDateTimeMills = minDate.getTime();
        mMaxDateTimeMills = maxDate.getTime();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.special_time_calendar, mCalendarFragment);
        fragmentTransaction.commit();
    }

    /**
     * 点击是否授课
     */
    public void onTeachOrNotClick(View view) {
        mTimeLinearLayout.setVisibility(mTimeLinearLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    /**
     * 点击确认并保存
     */
    public void onConfirmClick(View view) {
        //Todo 具体的逻辑处理
    }

    public void onBackClick(View view) {
        finish();
    }
}
