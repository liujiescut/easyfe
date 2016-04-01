package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.MapUtils;
import com.scut.easyfe.utils.OtherUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 注册第一步(基本信息) 跟 修改信息
 * @author jay
 */
public class TeacherRegisterOneActivity extends BaseActivity {
    public static final int REQUEST_CODE = 0;
    private EditText mNameEditText;         //姓名
    private EditText mPhoneEditText;        //手机
    private EditText mPasswordEditText;     //密码
    private EditText mIdCardEditText;       //身份证
    private TextView mGenderTextView;       //性别
    private TextView mBirthdayTextView;     //出生年月日
    private TextView mSchoolTextView;       //学校
    private TextView mGradeTextView;        //年级
    private TextView mProfessionTextView;   //专业
    private TextView mAddressTextView;      //地址
    private TextView mHadTeachChildTextView;//已家教过孩子数量
    private TextView mHadTeachTimeTextView; //已家教时长
    private TextView mGoNextTextView;       //保存进入下一页

    /** 下面几项为修改信息时用到 */
    private LinearLayout mChildGenderLinearLayout;     //宝贝性别所在LinearLayout
    private LinearLayout mChildGradeLinearLayout;      //宝贝年级所在LinearLayout
    private TextView mChildGenderTextView;             //宝贝性别
    private TextView mChildGradeTextView;              //宝贝年级
    private TextView mDoModifyTextView;                //确认保存修改

    private double mLatitude = -1d;    //定位所在的纬度
    private double mLongitude = -1d;   //定位所在的经度
    private String mAddress;           //定位所在的地址
    private String mCity;              //定位所在的城市
    private int mGender = Constants.Identifier.FEMALE;   //选择的性别

    private OptionsPickerView<String> mSinglePicker;
    private OptionsPickerView<String> mDoublePicker;
    private TimePickerView mTimePicker;

    private int mFromType = Constants.Identifier.TYPE_REGISTER;   //到此页面的功能(注册还是修改信息)

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_teacher_register_one);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(null != intent){
            Bundle extras = intent.getExtras();
            if(null != extras){
                mFromType = extras.getInt(Constants.Key.TO_TEACHER_REGISTER_ONE_ACTIVITY_TYPE, Constants.Identifier.TYPE_REGISTER);
            }
        }
    }

    @Override
    protected void initView() {
        mSinglePicker = new OptionsPickerView<>(this);
        mSinglePicker.setCancelable(true);
        mDoublePicker = new OptionsPickerView<>(this);
        mDoublePicker.setCancelable(true);

        Calendar calendar = Calendar.getInstance();
        mTimePicker = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        mTimePicker.setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR)); //控制时间范围
        mTimePicker.setTime(new Date());
        mTimePicker.setCyclic(false);
        mTimePicker.setCancelable(true);

        mNameEditText = OtherUtils.findViewById(this, R.id.teacher_register_one_et_name);
        mPhoneEditText = OtherUtils.findViewById(this, R.id.teacher_register_one_et_phone);
        mPasswordEditText = OtherUtils.findViewById(this, R.id.teacher_register_one_et_password);
        mIdCardEditText = OtherUtils.findViewById(this, R.id.teacher_register_one_et_id_card);
        mBirthdayTextView = OtherUtils.findViewById(this, R.id.teacher_register_one_tv_birthday);
        mGenderTextView = OtherUtils.findViewById(this, R.id.teacher_register_one_tv_gender);
        mSchoolTextView = OtherUtils.findViewById(this, R.id.teacher_register_one_tv_school);
        mGradeTextView = OtherUtils.findViewById(this, R.id.teacher_register_one_tv_grade);
        mProfessionTextView = OtherUtils.findViewById(this, R.id.teacher_register_one_tv_profession);
        mAddressTextView = OtherUtils.findViewById(this, R.id.teacher_register_one_tv_address);
        mHadTeachChildTextView = OtherUtils.findViewById(this, R.id.teacher_register_one_tv_child_count);
        mHadTeachTimeTextView = OtherUtils.findViewById(this, R.id.teacher_register_one_tv_time);
        mGoNextTextView = OtherUtils.findViewById(this, R.id.teacher_register_one_tv_submit);

        mChildGenderLinearLayout = OtherUtils.findViewById(this, R.id.base_info_ll_child_gender);
        mChildGradeLinearLayout = OtherUtils.findViewById(this, R.id.base_info_ll_child_grade);
        mChildGenderTextView = OtherUtils.findViewById(this, R.id.base_info_tv_child_gender);
        mChildGradeTextView = OtherUtils.findViewById(this, R.id.base_info_tv_child_grade);
        mDoModifyTextView = OtherUtils.findViewById(this, R.id.base_info_tv_modify);

        mBirthdayTextView.setText(OtherUtils.getTime(calendar.getTime(), "yyyy 年 MM 月 dd 日"));
        mSchoolTextView.setText(Constants.Data.schoolList.get(0));
        mGradeTextView.setText(Constants.Data.teacherGradeList.get(0));
        mProfessionTextView.setText(Constants.Data.professionList.get(0));
        mHadTeachChildTextView.setText(Constants.Data.hasTeachChildCountRangeList.get(0));
        mHadTeachTimeTextView.setText(Constants.Data.hasTeachChildTimeRangeList.get(0));

        updateView();
    }

    public void updateView(){
        if(mFromType == Constants.Identifier.TYPE_REGISTER){
            ((TextView) findViewById(R.id.titlebar_tv_title)).setText("家教注册-基本信息");
        }else{
            ((TextView) findViewById(R.id.titlebar_tv_title)).setText("基本信息维护");
            mGoNextTextView.setVisibility(View.GONE);
            mDoModifyTextView.setVisibility(View.VISIBLE);

            if(App.getUser().getUserType() == Constants.Identifier.USER_TP){
                //既是家长又是家教
                mChildGenderLinearLayout.setVisibility(View.VISIBLE);
                mChildGradeLinearLayout.setVisibility(View.VISIBLE);
            }

            mNameEditText.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mGenderTextView.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mBirthdayTextView.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mPhoneEditText.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mPasswordEditText.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mIdCardEditText.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mSchoolTextView.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mGradeTextView.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mProfessionTextView.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mProfessionTextView.setEnabled(false);
            mGradeTextView.setEnabled(false);
            mSchoolTextView.setEnabled(false);
            mIdCardEditText.setEnabled(false);
            mPasswordEditText.setEnabled(false);
            mPhoneEditText.setEnabled(false);
            mBirthdayTextView.setEnabled(false);
            mGenderTextView.setEnabled(false);
            mNameEditText.setEnabled(false);
            mGenderTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mBirthdayTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mSchoolTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mGradeTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mProfessionTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
        }
    }

    @Override
    protected void fetchData() {
        MapUtils.getLocation(new MapUtils.LocationCallback() {
            @Override
            public void onSuccess(double latitude, double longitude, String address, String city) {
                mLatitude = latitude;
                mLongitude = longitude;
                mCity = city;
                mAddress = (null == address ? "" : address);
                mAddressTextView.setText(mAddress);
            }

            @Override
            public void onFailed() {
                LogUtils.i(Constants.Tag.MAP_TAG, "定位失败");
            }
        });
    }

    @Override
    protected void initListener() {
        mTimePicker.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                mBirthdayTextView.setText(OtherUtils.getTime(date, "yyyy 年 MM 月 dd 日"));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE && resultCode == AddressActivity.RESULT_OK && null != intent) {
            mAddress = intent.getStringExtra(Constants.Key.ADDRESS);
            mCity = intent.getStringExtra(Constants.Key.CITY);
            mLatitude = intent.getDoubleExtra(Constants.Key.LATITUDE, -1);
            mLongitude = intent.getDoubleExtra(Constants.Key.LONGITUDE, -1);

            if (-1 == mLatitude || -1 == mLongitude) {
                toast("获取地址失败,请重试");
            } else {
                mAddressTextView.setText(mAddress);
            }
        }else{
            toast("获取地址失败,请重试");
        }
    }

    /**
     * 点击选择性别
     */
    public void onTeacherGenderClick(View view) {
        hidePickerIfShowing();

        mSinglePicker.setTitle("选择您的性别");
        mSinglePicker.setPicker(Constants.Data.genderList);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                if (options1 == 0) {
                    //选择了女
                    mGender = Constants.Identifier.FEMALE;
                    mGenderTextView.setText(R.string.female);
                } else if (options1 == 1) {
                    //选择了男
                    mGender = Constants.Identifier.FEMALE;
                    mGenderTextView.setText(R.string.male);
                }
            }
        });
        mSinglePicker.show();
    }

    /**
     * 点击选择出生日期
     */
    public void onTeacherBirthdayClick(View view) {
        OtherUtils.hideSoftInputWindow(mBirthdayTextView.getWindowToken());
        mTimePicker.show();
    }

    /**
     * 点击选择学校
     */
    public void onSchoolClick(View view) {
        hidePickerIfShowing();

        mSinglePicker.setTitle("选择您的学校");
        mSinglePicker.setPicker(Constants.Data.schoolList);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mSchoolTextView.setText(Constants.Data.schoolList.get(options1));
            }
        });
        mSinglePicker.show();
    }

    /**
     * 点击选择年级
     */
    public void onGradeClick(View view) {
        hidePickerIfShowing();

        mSinglePicker.setTitle("选择您的专业");
        mSinglePicker.setPicker(Constants.Data.teacherGradeList);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mGradeTextView.setText(Constants.Data.teacherGradeList.get(options1));
            }
        });
        mSinglePicker.show();
    }

    /**
     * 点击选择专业
     */
    public void onProfessionClick(View view) {
        hidePickerIfShowing();

        mSinglePicker.setTitle("选择您的专业");
        mSinglePicker.setPicker(Constants.Data.professionList);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mProfessionTextView.setText(Constants.Data.professionList.get(options1));
            }
        });
        mSinglePicker.show();
    }

    /**
     * 点击选择地址
     */
    public void onAddressClick(View view) {
        if (mLatitude != -1) {
            Bundle bundle = new Bundle();
            bundle.putDouble(Constants.Key.LATITUDE, mLatitude);
            bundle.putDouble(Constants.Key.LONGITUDE, mLongitude);
            bundle.putString(Constants.Key.ADDRESS, mAddress);
            Intent intent = new Intent(this, AddressActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            MapUtils.getLocation(new MapUtils.LocationCallback() {
                @Override
                public void onSuccess(double latitude, double longitude, String address, String city) {
                    mLatitude = latitude;
                    mLongitude = longitude;
                    mAddress = address;
                    mCity = city;
                    Bundle bundle = new Bundle();
                    bundle.putDouble(Constants.Key.LATITUDE, mLatitude);
                    bundle.putDouble(Constants.Key.LONGITUDE, mLongitude);
                    bundle.putString(Constants.Key.ADDRESS, mAddress);
                    bundle.putString(Constants.Key.CITY, mCity);
                    Intent intent = new Intent(TeacherRegisterOneActivity.this, AddressActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_CODE);
                }

                @Override
                public void onFailed() {
                    LogUtils.i(Constants.Tag.MAP_TAG, "定位失败");
                    toast("定位失败,请确认是否允许相应权限");
                }
            });
        }
    }

    /**
     * 点击选择家教孩子数量
     */
    public void onTeachChildCountClick(View view) {
        hidePickerIfShowing();

        mSinglePicker.setTitle("已家教过孩子数量");
        mSinglePicker.setPicker(Constants.Data.hasTeachChildCountRangeList);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mHadTeachChildTextView.setText(Constants.Data.hasTeachChildCountRangeList.get(options1));
            }
        });
        mSinglePicker.show();
    }

    /**
     * 点击选择家教时长
     */
    public void onTeachTimeClick(View view) {
        hidePickerIfShowing();

        mSinglePicker.setTitle("已家教过的时长");
        mSinglePicker.setPicker(Constants.Data.hasTeachChildTimeRangeList);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mHadTeachTimeTextView.setText(Constants.Data.hasTeachChildTimeRangeList.get(options1));
            }
        });
        mSinglePicker.show();
    }

    /**
     * 家教注册第一步点击保存并进入下一页
     */
    public void onRegisterOneClick(View view){
        Bundle extras = new Bundle();
        extras.putBoolean(Constants.Key.IS_REGISTER, true);
        redirectToActivity(this, TeacherRegisterTwoActivity.class, extras);
    }

    public void onBackClick(View view){
        finish();
    }


    public void onModifyClick(View view){
        toast("就保存喽");
    }

    public void onChildGenderClick(View view){
        hidePickerIfShowing();

        mSinglePicker.setTitle("选择宝贝性别");
        mSinglePicker.setPicker(Constants.Data.genderList);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mChildGenderTextView.setText(Constants.Data.genderList.get(options1));
            }
        });
        mSinglePicker.show();
    }

    public void onChildGradeClick(View view){
        hidePickerIfShowing();

        mDoublePicker.setTitle("选择宝贝年级");
        mDoublePicker.setPicker(Constants.Data.studentStateList, Constants.Data.studentGradeList, true);
        mDoublePicker.setSelectOptions(0, 0);
        mDoublePicker.setCyclic(false);
        mDoublePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mChildGradeTextView.setText(String.format("%s %s",
                        Constants.Data.studentStateList.get(options1),
                        Constants.Data.studentGradeList.get(options1).get(option2)));
            }
        });
        mDoublePicker.show();
    }

    private boolean hidePickerIfShowing(){
        OtherUtils.hideSoftInputWindow(mChildGradeTextView.getWindowToken());
        boolean isShowing = false;
        if(mSinglePicker.isShowing()){
            mSinglePicker.dismiss();
            isShowing = true;
        }

        if(mDoublePicker.isShowing()){
            mDoublePicker.dismiss();
            isShowing = true;
        }

        return isShowing;
    }

}
