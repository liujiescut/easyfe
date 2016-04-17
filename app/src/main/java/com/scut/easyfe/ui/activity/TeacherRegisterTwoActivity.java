package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.MyTimePicker;
import com.bigkoo.pickerview.OptionsPickerView;
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

import java.util.ArrayList;

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
    private TextView mJoinAngleTextView;            //是否加入天使计划
    private TextView mAngleBoyAgeTextView;          //能接受男孩最大年龄
    private TextView mAngleGirlAgeTextView;         //能接受女孩最大年龄
    private TextView mAnglePriceTextView;           //天使计划价格
    private TextView mSaveTextView;                 //保存并下一页
    private LinearLayout mAngelBoyAgeLinearLayout;
    private LinearLayout mAngelGirlAgeLinearLayout;
    private LinearLayout mAngelPriceLinearLayout;
    private LinearLayout mWorkOrNotLinearLayout;
    private ToggleButton mWorkOrNotToggle;


    private boolean mJoinAngelPlan = false;         //是否加入天使计划
    private int mMinCourseTime = 120;               //最短课时(分钟)
    private int mTrafficTime = 120;                 //可接受交通时间(分钟)
    private int mMaxTrafficTime = 180;              //可接受最长交通时间(分钟)
    private int mSubsidy = 5;                           //交通补贴
    private int mMaxBoyAge = 0;                    //天使计划男孩最大年龄(默认0表示不接受)
    private int mMaxGirlAge = 0;                   //天使计划女孩最大年龄(默认0表示不接受)
    private int mAngelPrice = 0;                    //天使计划价格

    private MyTimePicker mTimePicker;
    private OptionsPickerView<String> mOptionPicker;

    /**
     * 选择时间的几种type
     */
    private static final int PICK_TIME_MIN_COURSE_TIME = 0;
    private static final int PICK_TIME_TRAFFIC = 1;
    private static final int PICK_TIME_MAX_TRAFFIC = 2;
    private int mPickTimeType = -1;

    private static final int PICK_OPTION_ANGEL_BOY_AGE = 0;
    private static final int PICK_OPTION_ANGEL_GIRL_AGE = 1;
    private int mOptionPickType = -1;

    private boolean mIsRegister = false;   //区分注册第二部跟家教信息维护

    private static ArrayList<String> mAge = new ArrayList<>();

    private User mUser;

    static {
        mAge.add("不接受");
        mAge.addAll(Constants.Data.ageList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /** 让User保持最新 */
        mUser = App.getUser(!mIsRegister).getCopy();

        mTeachTimeTextView.setText(mUser.getTeacherMessage().getSingleBookTime().size() == 0 ?
                "请选择您方便授课的时间" : "已填写");

        mTeachableCourseTextView.setText(mUser.getTeacherMessage().getTeacherPrice().size() == 0 ?
                "请完善此信息" : "已填写");
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

        mMinCourseTime = mUser.getTeacherMessage().getMinCourseTime();
        mTrafficTime = mUser.getTeacherMessage().getFreeTrafficTime();
        mMaxTrafficTime = mUser.getTeacherMessage().getMaxTrafficTime();
        mSubsidy = mUser.getTeacherMessage().getSubsidy();
        mJoinAngelPlan = mUser.getTeacherMessage().getAngelPlan().isJoin();
        mMaxGirlAge = mUser.getTeacherMessage().getAngelPlan().getGirl();
        mMaxBoyAge = mUser.getTeacherMessage().getAngelPlan().getBoy();
        mAngelPrice = mUser.getTeacherMessage().getAngelPlan().getPrice();

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
        mJoinAngleTextView = OtherUtils.findViewById(this, R.id.teacher_register_two_tv_angel);
        mAngleBoyAgeTextView = OtherUtils.findViewById(this, R.id.teacher_register_two_tv_angel_boy_age);
        mAngleGirlAgeTextView = OtherUtils.findViewById(this, R.id.teacher_register_two_tv_angel_girl_age);
        mAnglePriceTextView = OtherUtils.findViewById(this, R.id.teacher_register_two_tv_angel_price);
        mSaveTextView = OtherUtils.findViewById(this, R.id.teacher_register_two_tv_submit);
        mAngelBoyAgeLinearLayout = OtherUtils.findViewById(this, R.id.teacher_register_two_ll_angel_boy_age);
        mAngelGirlAgeLinearLayout = OtherUtils.findViewById(this, R.id.teacher_register_two_ll_angel_girl_age);
        mAngelPriceLinearLayout = OtherUtils.findViewById(this, R.id.teacher_register_two_ll_angel_price);
        mWorkOrNotLinearLayout = OtherUtils.findViewById(this, R.id.teacher_register_two_ll_tb);
        mWorkOrNotToggle = OtherUtils.findViewById(this, R.id.teacher_register_two_tb);

        mTimePicker = new MyTimePicker(this);
        mOptionPicker = new OptionsPickerView<>(this);

        if (!mIsRegister) {
            mWorkOrNotLinearLayout.setVisibility(View.VISIBLE);
            if(mUser.getTeacherMessage().isLock() ) {
                mWorkOrNotToggle.toggleOff();
            } else{
                mWorkOrNotToggle.toggleOn();
            }

            mSaveTextView.setText("确认并保存");
        }

        mMinTeachTimeTextView.setText(TimeUtils.getTimeFromMinute(mUser.getTeacherMessage().getMinCourseTime()));
        mTrafficTextView.setText(TimeUtils.getTimeFromMinute(mUser.getTeacherMessage().getFreeTrafficTime()));
        mMaxTrafficTextView.setText(TimeUtils.getTimeFromMinute(mUser.getTeacherMessage().getMaxTrafficTime()));
        mSubsidyTextView.setText(String.format("%d元", mUser.getTeacherMessage().getSubsidy()));

        mJoinAngleTextView.setText(mJoinAngelPlan ? R.string.yes : R.string.no);
        mAngelBoyAgeLinearLayout.setVisibility(mJoinAngelPlan ? View.VISIBLE : View.GONE);
        mAngelGirlAgeLinearLayout.setVisibility(mJoinAngelPlan ? View.VISIBLE : View.GONE);
        mAngelPriceLinearLayout.setVisibility(mJoinAngelPlan ? View.VISIBLE : View.GONE);

        if (mJoinAngelPlan && mUser.getTeacherMessage().getAngelPlan().getPrice() != 0) {
            mJoinAngleTextView.setText(mUser.getTeacherMessage().getAngelPlan().isJoin() ? R.string.yes : R.string.no);
            mAngleBoyAgeTextView.setText(mUser.getTeacherMessage().getAngelPlan().getBoy() == 0 ?
                    "不接受" :
                    String.format("%d 岁", mUser.getTeacherMessage().getAngelPlan().getBoy()));

            mAngleGirlAgeTextView.setText(mUser.getTeacherMessage().getAngelPlan().getGirl() == 0 ?
                    "不接受" :
                    String.format("%d 岁", mUser.getTeacherMessage().getAngelPlan().getGirl()));
            mAnglePriceTextView.setText(String.format("%d元/小时", mUser.getTeacherMessage().getAngelPlan().getPrice()));
        }
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
                        mMaxTrafficTextView.setText(timeString);
                        mMaxTrafficTime = timeInt;
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

        mOptionPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                switch (mOptionPickType) {
                    case PICK_OPTION_ANGEL_BOY_AGE:
                        mMaxBoyAge = options1;
                        if (options1 == 0) {
                            mAngleBoyAgeTextView.setText(mAge.get(0));
                            break;
                        }
                        mAngleBoyAgeTextView.setText(String.format("%d 岁", mMaxBoyAge));
                        break;

                    case PICK_OPTION_ANGEL_GIRL_AGE:
                        mMaxGirlAge = options1;
                        if (options1 == 0) {
                            mAngleGirlAgeTextView.setText(mAge.get(0));
                            break;
                        }
                        mAngleGirlAgeTextView.setText(String.format("%d 岁", mMaxGirlAge));
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
        mTimePicker.setTitle("最短课时");
        mTimePicker.show();
    }

    /**
     * 能接受的交通时间
     */
    public void onTrafficTimeClick(View view) {
        mPickTimeType = PICK_TIME_TRAFFIC;
        OtherUtils.hideSoftInputWindow(mMinTeachTimeTextView.getWindowToken());
        mTimePicker.setTitle("能接受的交通时间");
        mTimePicker.show();
    }

    /**
     * 能接受的最大交通时间
     */
    public void onMaxTrafficTimeClick(View view) {
        mPickTimeType = PICK_TIME_MAX_TRAFFIC;
        OtherUtils.hideSoftInputWindow(mMinTeachTimeTextView.getWindowToken());
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
     * 点击选择是否参与天使计划
     */
    public void onAnglePlaneClick(View view) {
        mJoinAngelPlan = !mJoinAngelPlan;
        mJoinAngleTextView.setText(mJoinAngelPlan ? R.string.yes : R.string.no);
        mAngelBoyAgeLinearLayout.setVisibility(mJoinAngelPlan ? View.VISIBLE : View.GONE);
        mAngelGirlAgeLinearLayout.setVisibility(mJoinAngelPlan ? View.VISIBLE : View.GONE);
        mAngelPriceLinearLayout.setVisibility(mJoinAngelPlan ? View.VISIBLE : View.GONE);

        if (!mJoinAngelPlan) {
            mUser.getTeacherMessage().getAngelPlan().setBoy(-1);
            mUser.getTeacherMessage().getAngelPlan().setGirl(-1);
            mUser.getTeacherMessage().getAngelPlan().setPrice(0);
        }
    }

    /**
     * 点击查看天使计划详情
     */
    public void onAngleDetailClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_TITLE, "陪伴天使计划");
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_CONTENT, mResources.getString(R.string.about_us_content));
        redirectToActivity(mContext, ShowTextActivity.class, bundle);
    }

    /**
     * 天使计划男生可接受最大年龄
     */
    public void onMaleAgeClick(View view) {
        mOptionPickType = PICK_OPTION_ANGEL_BOY_AGE;
        mOptionPicker.setTitle("男生可接受最大年龄");
        mOptionPicker.setPicker(mAge);
        mOptionPicker.setSelectOptions(0);
        mOptionPicker.show();
    }

    /**
     * 天使计划女生可接受最大年龄
     */
    public void onFemaleAgeClick(View view) {
        mOptionPickType = PICK_OPTION_ANGEL_GIRL_AGE;
        mOptionPicker.setTitle("女生可接受最大年龄");
        mOptionPicker.setPicker(mAge);
        mOptionPicker.setSelectOptions(0);
        mOptionPicker.show();
    }

    /**
     * 输入天使计划价格
     */
    public void onAnglePriceClick(View view) {
        DialogUtils.makeInputDialog(mContext, "陪伴天使计划价格", InputType.TYPE_CLASS_NUMBER, new DialogUtils.OnInputListener() {
            @Override
            public void onFinish(String message) {
                LogUtils.i(Constants.Tag.TEACHER_REGISTER_TAG, message);
                try {
                    mAngelPrice = Integer.parseInt(message);
                    mAnglePriceTextView.setText(String.format("%s 元/小时", message));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    toast("只能输入数字");
                }
            }
        }).show();
    }

    /**
     * 点击注册
     */
    public void onRegisterTwoClick(View view) {
        mUser.getTeacherMessage().setMinCourseTime(mMinCourseTime);
        mUser.getTeacherMessage().setFreeTrafficTime(mTrafficTime);
        mUser.getTeacherMessage().setMaxTrafficTime(mMaxTrafficTime);
        mUser.getTeacherMessage().setSubsidy(mSubsidy);
        mUser.getTeacherMessage().getAngelPlan().setJoin(mJoinAngelPlan);

        if (mJoinAngelPlan) {
            mUser.getTeacherMessage().getAngelPlan().setBoy(mMaxBoyAge);
            mUser.getTeacherMessage().getAngelPlan().setGirl(mMaxGirlAge);
            mUser.getTeacherMessage().getAngelPlan().setPrice(mAngelPrice);
        }

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

        return true;
    }

    public void onBackClick(View view) {
        finish();
    }
}
