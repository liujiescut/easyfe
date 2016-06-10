package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.MyTimePicker;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.user.teacher.RTeacherInfoModify;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.TimeUtils;
import com.zcw.togglebutton.ToggleButton;

import org.json.JSONObject;

import java.util.Locale;

/**
 * 家教注册第二部(家教时间) 家教信息维护页
 *
 * @author jay
 */
public class TeacherRegisterTwoActivity extends BaseActivity {

    private TextView mTeachTimeTextView;            //授课时间
    private TextView mMinTeachTimeTextView;         //最短课时
    private TextView mTrafficTextView;              //能接受交通时间
    private TextView mMaxTrafficTextView;           //能接受最长交通时间
    private TextView mSubsidyTextView;              //交通补贴
    private TextView mTeachableCourseTextView;      //可教授课程
    private ToggleButton mWorkOrNotToggle;          //是否授课选择按钮

    private int mMinCourseTime = 120;               //最短课时(分钟)
    private int mTrafficTime = 120;                 //可接受交通时间(分钟)
    private int mMaxTrafficTime = 180;              //可接受最长交通时间(分钟)
    private int mSubsidy = 5;                       //交通补贴

    private MyTimePicker mTimePicker;

    /**
     * 选择时间的几种type
     */
    private static final int PICK_TIME_MIN_COURSE_TIME = 0;
    private static final int PICK_TIME_TRAFFIC = 1;
    private static final int PICK_TIME_MAX_TRAFFIC = 2;
    private int mPickTimeType = -1;

    private boolean mIsRegister = false;   //区分注册第二部跟家教信息维护
    private User mUser;

    @Override
    protected void onResume() {
        super.onResume();
        /** 让User保持最新 */
        mUser = App.getUser(!mIsRegister).getCopy();

        if (null != mUser) {
            mTeachTimeTextView.setText(mUser.getTeacherMessage().getSingleBookTime().size() == 0 ?
                    "请选择您方便授课的时间" : "已填写");

            mTeachableCourseTextView.setText(mUser.getTeacherMessage().getTeacherPrice().size() == 0 ?
                    "请完善此信息" : "已填写");
        }
    }

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_teacher_register_two);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = intent.getExtras();
            if (null != bundle) {
                mIsRegister = bundle.getBoolean(Constants.Key.IS_REGISTER, false);
            }
        }

        mUser = App.getUser(!mIsRegister).getCopy();

        if (null != mUser) {
            mMinCourseTime = mUser.getTeacherMessage().getMinCourseTime();
            mTrafficTime = mUser.getTeacherMessage().getFreeTrafficTime();
            mMaxTrafficTime = mUser.getTeacherMessage().getMaxTrafficTime();
            mSubsidy = mUser.getTeacherMessage().getSubsidy();
        }
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText(mIsRegister ? "家教注册 - 家教时间" : "家教信息维护");
        mTeachTimeTextView = OtherUtils.findViewById(this, R.id.teacher_register_two_tv_teach_time);
        mMinTeachTimeTextView = OtherUtils.findViewById(this, R.id.teacher_register_two_tv_min_teach_time);
        mTrafficTextView = OtherUtils.findViewById(this, R.id.teacher_register_two_tv_traffic_time);
        mMaxTrafficTextView = OtherUtils.findViewById(this, R.id.teacher_register_two_tv_max_traffic_time);
        mSubsidyTextView = OtherUtils.findViewById(this, R.id.teacher_register_two_tv_tip);
        mTeachableCourseTextView = OtherUtils.findViewById(this, R.id.teacher_register_two_tv_course);
        mWorkOrNotToggle = OtherUtils.findViewById(this, R.id.teacher_register_two_tb);

        mTimePicker = new MyTimePicker(this);

        if (!mIsRegister) {
            LinearLayout mWorkOrNotLinearLayout = OtherUtils.findViewById(this, R.id.teacher_register_two_ll_tb);
            mWorkOrNotLinearLayout.setVisibility(View.VISIBLE);
            if (mUser.getTeacherMessage().isLock()) {
                mWorkOrNotToggle.toggleOff();
            } else {
                mWorkOrNotToggle.toggleOn();
            }

            TextView mInfoOneTextView = OtherUtils.findViewById(this, R.id.teacher_register_two_tv_info_1);
            TextView mInfoTwoTextView = OtherUtils.findViewById(this, R.id.teacher_register_two_tv_info_2);
            mInfoOneTextView.setText(getResources().getString(R.string.teacher_register_step_two_info_modify));
            mInfoTwoTextView.setVisibility(View.GONE);

            TextView mSaveTextView = OtherUtils.findViewById(this, R.id.teacher_register_two_tv_submit);
            mSaveTextView.setText("确认并保存");
        }

        mMinTeachTimeTextView.setText(TimeUtils.getTimeFromMinute(mUser.getTeacherMessage().getMinCourseTime()));
        mTrafficTextView.setText(TimeUtils.getTimeFromMinute(mUser.getTeacherMessage().getFreeTrafficTime()));
        mMaxTrafficTextView.setText(TimeUtils.getTimeFromMinute(mUser.getTeacherMessage().getMaxTrafficTime()));
        mSubsidyTextView.setText(String.format(Locale.CHINA, "%d元", mUser.getTeacherMessage().getSubsidy()));

    }

    @Override
    protected void initListener() {
        mTimePicker.setOnPickListener(new MyTimePicker.OnPickListener() {
            @Override
            public void onPick(int hour, int minute) {
                String timeString = String.format("%s 小时 %s 分钟", hour, minute);
                int timeInt = hour * 60 + minute;
                switch (mPickTimeType) {
                    case PICK_TIME_TRAFFIC:
                        mTrafficTextView.setText(timeString);
                        mTrafficTime = timeInt;
                        break;

                    case PICK_TIME_MAX_TRAFFIC:
                        if (timeInt < mTrafficTime) {
                            toast("亲，“能接受的最长交通时间（超出部分收取交通补贴）”应大于“能接受的交通时间（无交通补贴）”");
                        } else {
                            mMaxTrafficTextView.setText(timeString);
                            mMaxTrafficTime = timeInt;
                        }
                        break;

                    case PICK_TIME_MIN_COURSE_TIME:
                        mMinTeachTimeTextView.setText(timeString);
                        mMinCourseTime = timeInt;
                        break;

                    default:
                        break;
                }
            }
        });

    }

    /**
     * 点击选择授课时间
     */
    public void onTeachTimeClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key.TO_TEACH_TIME_ACTIVITY_TYPE, mIsRegister ? Constants.Identifier.TYPE_REGISTER : Constants.Identifier.TYPE_MODIFY);
        redirectToActivity(this, TeachTimeActivity.class, bundle);
    }

    /**
     * 点击选择最短课时
     */
    public void onMinTeachTimeClick(View view) {
        mPickTimeType = PICK_TIME_MIN_COURSE_TIME;
        OtherUtils.hideSoftInputWindow(mMinTeachTimeTextView.getWindowToken());
        mTimePicker.setToShowTime(Constants.Data.teachTimeHourList, Constants.Data.teachTimeMinuteList);
        mTimePicker.setTitle("最短课时");
        mTimePicker.show();
    }

    /**
     * 能接受的交通时间
     */
    public void onTrafficTimeClick(View view) {
        mPickTimeType = PICK_TIME_TRAFFIC;
        OtherUtils.hideSoftInputWindow(mMinTeachTimeTextView.getWindowToken());
        mTimePicker.setToShowTime(Constants.Data.trafficTimeHourList, Constants.Data.trafficTimeMinuteList);
        mTimePicker.setTitle("能接受的交通时间");
        mTimePicker.show();
    }

    /**
     * 能接受的最大交通时间
     */
    public void onMaxTrafficTimeClick(View view) {
        mPickTimeType = PICK_TIME_MAX_TRAFFIC;
        OtherUtils.hideSoftInputWindow(mMinTeachTimeTextView.getWindowToken());
        mTimePicker.setToShowTime(Constants.Data.trafficTimeHourList, Constants.Data.trafficTimeMinuteList);
        mTimePicker.setTitle("能接受的最大交通时间");
        mTimePicker.show();
    }

    /**
     * 交通补贴
     */
    public void onTipClick(View view) {
        DialogUtils.makeInputDialog(mContext, "交通补贴", InputType.TYPE_CLASS_NUMBER, new DialogUtils.OnInputListener() {
            @Override
            public void onFinish(String message) {
                LogUtils.i(Constants.Tag.TEACHER_REGISTER_TAG, message);
                OtherUtils.hideSoftInputWindow(mSubsidyTextView.getWindowToken());
                try {
                    mSubsidy = Integer.parseInt(message);
                    mSubsidyTextView.setText(String.format("%s 元", message));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    toast("只能输入数字");
                }
            }
        }).show();
    }

    /**
     * 点击选择可教授课程
     */
    public void onTeachCourseClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key.TO_TEACH_COURSE_ACTIVITY_TYPE, mIsRegister ?
                Constants.Identifier.TYPE_REGISTER : Constants.Identifier.TYPE_MODIFY);
        redirectToActivity(mContext, TeachCourseActivity.class, bundle);
    }

    /**
     * 点击注册
     */
    public void onRegisterTwoClick(View view) {
        mUser.getTeacherMessage().setMinCourseTime(mMinCourseTime);
        mUser.getTeacherMessage().setFreeTrafficTime(mTrafficTime);
        mUser.getTeacherMessage().setMaxTrafficTime(mMaxTrafficTime);
        mUser.getTeacherMessage().setSubsidy(mSubsidy);

        if (!validate(mUser)) {
            return;
        }

        if (!mIsRegister) {
            mUser.getTeacherMessage().setIsLock(!mWorkOrNotToggle.isToggleOn());
            RequestManager.get().execute(new RTeacherInfoModify(mUser), new RequestListener<JSONObject>() {
                @Override
                public void onSuccess(RequestBase request, JSONObject result) {
                    toast("修改成功");
                    App.setUser(mUser);
                    redirectToActivity(mContext, MainActivity.class);
                    finish();
                }

                @Override
                public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                    toast(errorMsg);
                }
            });
        } else {
            toast("保存成功");
            App.setUser(mUser);
            redirectToActivity(this, ReceivablesChannelActivity.class);
        }
    }


    private boolean validate(User user) {
        if (mIsRegister && user.getTeacherMessage().getMultiBookTime().size() == 0) {
            toast("请先选择授课时间");
            return false;
        }

        if (mIsRegister && user.getTeacherMessage().getSingleBookTime().size() == 0) {
            toast("请确认最近两月授课时间");
            return false;
        }

        if (mIsRegister && user.getTeacherMessage().getTeacherPrice().size() == 0) {
            toast("请选择可教授课程");
            return false;
        }

        if (user.getTeacherMessage().getAngelPlan().isJoin() &&
                user.getTeacherMessage().getAngelPlan().getPrice() == 0) {
            toast("请输入天使计划价格");
            return false;
        }

        if (mMaxTrafficTime < mTrafficTime) {
            toast("亲，“能接受的最长交通时间（超出部分收取交通补贴）”应大于“能接受的交通时间（无交通补贴）”");
            return false;
        }

        return true;
    }

    public void onBackClick(View view) {
        finish();
    }
}
