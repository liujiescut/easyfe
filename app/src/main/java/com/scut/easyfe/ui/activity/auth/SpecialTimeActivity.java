package com.scut.easyfe.ui.activity.auth;

import android.content.Intent;
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
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.book.MultiBookTime;
import com.scut.easyfe.entity.book.SingleBookTime;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.user.teacher.RTeacherSingleBookTimeModify;
import com.scut.easyfe.ui.activity.auth.TeacherRegisterTwoActivity;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.customView.SelectorButton;
import com.scut.easyfe.ui.fragment.CalendarFragment;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.TimeUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hirondelle.date4j.DateTime;

/**
 * 近两月实际特别特别安排
 *
 * @author jay
 */
public class SpecialTimeActivity extends BaseActivity {
    private SelectorButton mMorningSelectorButton;
    private SelectorButton mAfternoonSelectorButton;
    private SelectorButton mEveningSelectorButton;
    private EditText mMemoEditText;
    private TextView mSelectedDateTextView;
    private TextView mTeachOrNotTextView;
    private LinearLayout mTimeLinearLayout;
    private CalendarFragment mCalendarFragment;

    private User mUser;
    private Date mSelectedDate = null;
    private DateTime mSelectedDateTime = null;
    private Map<Long, SingleBookTime> mSingleBookTimes = new HashMap<>();
    List<DateTime> mWorkDays = new ArrayList<>();

    private boolean mTeachOrNot = false;

    private int mFromType = Constants.Identifier.TYPE_REGISTER;

    private SelectorButton.OnSelectChangeListener mOnSelectChangeListener;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_special_time);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUser = App.getUser(false).getCopy();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mFromType = extras.getInt(Constants.Key.TO_SPECIAL_TIME_ACTIVITY_TYPE, Constants.Identifier.TYPE_REGISTER);
            }
        }

        mUser = App.getUser(false).getCopy();
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("近两月时间特别安排");
        initCalendar();

        mSelectedDateTextView = OtherUtils.findViewById(this, R.id.special_time_tv_selected_date);
        mTeachOrNotTextView = OtherUtils.findViewById(this, R.id.special_time_tv_teachable);
        mTimeLinearLayout = OtherUtils.findViewById(this, R.id.special_time_ll_time);
        mMemoEditText = OtherUtils.findViewById(this, R.id.special_time_et_remember_things);
        mMorningSelectorButton = OtherUtils.findViewById(this, R.id.special_time_sb_morning);
        mAfternoonSelectorButton = OtherUtils.findViewById(this, R.id.special_time_sb_afternoon);
        mEveningSelectorButton = OtherUtils.findViewById(this, R.id.special_time_sb_evening);
        mMorningSelectorButton.setBothText(getResources().getString(R.string.morning));
        mAfternoonSelectorButton.setBothText(getResources().getString(R.string.afternoon));
        mEveningSelectorButton.setBothText(getResources().getString(R.string.night));
        mMorningSelectorButton.setIsSelected(false);
        mAfternoonSelectorButton.setIsSelected(false);
        mEveningSelectorButton.setIsSelected(false);

    }

    @Override
    protected void initListener() {
        mCalendarFragment.setCaldroidListener(new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view, int position) {
                LogUtils.i(Constants.Tag.TEACHER_REGISTER_TAG, TimeUtils.getTime(date, "yyyy 年 MM 月 dd 日 (EEEE)"));
                saveDateInfo();
                refreshDateInfo(date);
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

        mOnSelectChangeListener = new SelectorButton.OnSelectChangeListener() {
            @Override
            public void onSelectChange(boolean isSelected) {
                if (!mMorningSelectorButton.isSelected() &&
                        !mAfternoonSelectorButton.isSelected() &&
                        !mEveningSelectorButton.isSelected()) {
                    mTeachOrNot = false;
                    mTeachOrNotTextView.setText(R.string.no);
                    mTimeLinearLayout.setVisibility(View.GONE);
                    mWorkDays.remove(mSelectedDateTime);
                    mSingleBookTimes.remove(mSelectedDate.getTime());

                } else {
                    mTeachOrNot = true;
                }
            }
        };
        mMorningSelectorButton.setOnSelectChangeListener(mOnSelectChangeListener);
        mAfternoonSelectorButton.setOnSelectChangeListener(mOnSelectChangeListener);
        mEveningSelectorButton.setOnSelectChangeListener(mOnSelectChangeListener);
    }

    /**
     * 保存每天的信息
     */
    private void saveDateInfo() {
        if (mSelectedDate == null) {
            return;
        }

        if (mTeachOrNot) {
            SingleBookTime singleBookTime = mSingleBookTimes.get(mSelectedDate.getTime());
            if (null == singleBookTime) {
                singleBookTime = new SingleBookTime();
                mSingleBookTimes.put(mSelectedDate.getTime(), singleBookTime);
            }

            singleBookTime.setIsOk(true);
            singleBookTime.setDate(TimeUtils.getTime(mSelectedDate, "yyyy-MM-dd"));
            singleBookTime.setMorning(mMorningSelectorButton.isSelected());
            singleBookTime.setAfternoon(mAfternoonSelectorButton.isSelected());
            singleBookTime.setEvening(mEveningSelectorButton.isSelected());
            singleBookTime.setMemo(mMemoEditText.getText().toString());

            if(!mWorkDays.contains(mSelectedDateTime)) {
                mWorkDays.add(mSelectedDateTime);
            }
        }
    }

    private void refreshDateInfo(Date date) {
        mSelectedDate = date;
        mSelectedDateTime = CalendarHelper.convertDateToDateTime(mSelectedDate);
        SingleBookTime singleBookTime = mSingleBookTimes.get(mSelectedDate.getTime());
        mSelectedDateTextView.setText(TimeUtils.getTime(mSelectedDate, "yyyy 年 MM 月 dd 日 (EEEE)"));
        if (null != singleBookTime) {
            mTeachOrNot = true;
            mTeachOrNotTextView.setText(R.string.yes);
            mTimeLinearLayout.setVisibility(View.VISIBLE);
            mMorningSelectorButton.setIsSelected(singleBookTime.isMorning());
            mAfternoonSelectorButton.setIsSelected(singleBookTime.isAfternoon());
            mEveningSelectorButton.setIsSelected(singleBookTime.isEvening());
            mMemoEditText.setText(singleBookTime.getMemo());
        } else {
            mTeachOrNot = false;
            mTeachOrNotTextView.setText(R.string.no);
            mTimeLinearLayout.setVisibility(View.GONE);
            mMemoEditText.setText("");
        }
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

//        if (App.getSpUtils().getValue(Constants.Key.IS_FIRST_IN_SPECIAL_TIME_ACTIVITY, true)) {
//            //这里表示第一次进入到特别时间安排页面,帮他默认选中
//
//            List<Integer> workWeekDays = new ArrayList<>();   //工作的星期
//            Map<Integer, MultiBookTime> workWeekMap = new HashMap<>();
//            for (MultiBookTime multiBookTime :
//                    mUser.getTeacherMessage().getMultiBookTime()) {
//                workWeekDays.add(multiBookTime.getWeekDay() + 1);//加1是为了跟DateTime返回的相同
//                workWeekMap.put(multiBookTime.getWeekDay() + 1, multiBookTime);
//            }
//            DateTime minDateTime = CalendarHelper.convertDateToDateTime(minDate);
//            DateTime maxDateTime = CalendarHelper.convertDateToDateTime(maxDate);
//
//            mSingleBookTimes = new HashMap<>();
//
//            mUser.getTeacherMessage().getSingleBookTime().clear();
//            MultiBookTime tempMultiBookTime;
//            while (minDateTime.lteq(maxDateTime)) {
//                if (workWeekDays.contains(minDateTime.getWeekDay())) {
//                    mWorkDays.add(minDateTime);
//
//                    tempMultiBookTime = workWeekMap.get(minDateTime.getWeekDay());
//
//                    SingleBookTime singleBookTime = new SingleBookTime();
//                    singleBookTime.setIsOk(true);
//                    singleBookTime.setMorning(tempMultiBookTime.isMorning());
//                    singleBookTime.setAfternoon(tempMultiBookTime.isAfternoon());
//                    singleBookTime.setEvening(tempMultiBookTime.isEvening());
//                    singleBookTime.setDate(TimeUtils.getTime(CalendarHelper.convertDateTimeToDate(minDateTime), "yyyy-MM-dd"));
//
//                    mSingleBookTimes.put(TimeUtils.getDateFromString(singleBookTime.getDate()).getTime(), singleBookTime);
//                    mUser.getTeacherMessage().getSingleBookTime().add(singleBookTime);
//                    App.setUser(mUser);
//                }
//                minDateTime = minDateTime.plusDays(1);
//            }
//
//            App.getSpUtils().setValue(Constants.Key.IS_FIRST_IN_SPECIAL_TIME_ACTIVITY, false);
//        } else {

            /** 重新进来特别时间安排 */
            for (SingleBookTime singleBookTime :
                    mUser.getTeacherMessage().getSingleBookTime()) {
                mSingleBookTimes.put(TimeUtils.getDateFromString(singleBookTime.getDate()).getTime(), singleBookTime);
                mWorkDays.add(CalendarHelper.convertDateToDateTime(TimeUtils.getDateFromString(singleBookTime.getDate())));
            }
//        }

        mCalendarFragment.setWorkDays(mWorkDays);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.special_time_calendar, mCalendarFragment);
        fragmentTransaction.commit();
    }

    /**
     * 点击是否授课
     */
    public void onTeachOrNotClick(View view) {
        if (null == mSelectedDate) {
            toast("请先选择日期");
            return;
        }

        mTeachOrNot = !mTeachOrNot;
        mTeachOrNotTextView.setText(mTeachOrNot ? R.string.yes : R.string.no);
        mTimeLinearLayout.setVisibility(mTeachOrNot ? View.VISIBLE : View.GONE);
        if (mTeachOrNot) {
            SingleBookTime singleBookTime = mSingleBookTimes.get(mSelectedDate.getTime());
            if (null == singleBookTime) {
                mMorningSelectorButton.setIsSelected(true);
                mAfternoonSelectorButton.setIsSelected(true);
                mEveningSelectorButton.setIsSelected(true);
                singleBookTime = new SingleBookTime();
                singleBookTime.setDate(TimeUtils.getTime(mSelectedDate, "yyyy-MM-dd"));
                singleBookTime.setIsOk(true);
                singleBookTime.setMorning(true);
                singleBookTime.setAfternoon(true);
                singleBookTime.setEvening(true);
                mSingleBookTimes.put(mSelectedDate.getTime(), singleBookTime);
                mWorkDays.add(mSelectedDateTime);

            } else {
                mMorningSelectorButton.setIsSelected(singleBookTime.isMorning());
                mAfternoonSelectorButton.setIsSelected(singleBookTime.isAfternoon());
                mEveningSelectorButton.setIsSelected(singleBookTime.isEvening());
            }
        }else{
            mWorkDays.remove(mSelectedDateTime);
            mSingleBookTimes.remove(mSelectedDate.getTime());
        }
    }

    /**
     * 点击确认并保存
     */
    public void onConfirmClick(View view) {
        saveDateInfo();

        List<SingleBookTime> singleBookTimes = mUser.getTeacherMessage().getSingleBookTime();
        singleBookTimes.clear();
        Set<Long> keys = mSingleBookTimes.keySet();
        for (Long key :
                keys) {
            singleBookTimes.add(mSingleBookTimes.get(key));
        }
        mUser.getTeacherMessage().setSingleBookTime(singleBookTimes);

        if(mFromType == Constants.Identifier.TYPE_REGISTER) {
            App.setUser(mUser);
            toast("保存成功");

            Bundle extras = new Bundle();
            extras.putBoolean(Constants.Key.IS_REGISTER, true);
            redirectToActivity(mContext, TeacherRegisterTwoActivity.class, extras);
        }else{

            RequestManager.get().execute(new RTeacherSingleBookTimeModify(mUser), new RequestListener<JSONObject>() {
                @Override
                public void onSuccess(RequestBase request, JSONObject result) {
                    toast("修改成功");
                    App.setUser(mUser);
                }

                @Override
                public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                    toast(errorMsg);
                }
            });
        }
    }

    public void onBackClick(View view) {
        finish();
    }
}
