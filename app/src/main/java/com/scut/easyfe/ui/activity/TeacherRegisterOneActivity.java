package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.MapUtils;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 注册第一步(基本信息)
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

    private double mLatitude = -1d;    //定位所在的纬度
    private double mLongitude = -1d;   //定位所在的经度
    private String mAddress;           //定位所在的地址
    private String mCity;              //定位所在的城市
    private int mGender = Constants.Identifier.FEMALE;   //选择的性别

    private OptionsPickerView<String> mPicker;
    private TimePickerView mTimePicker;
    public static final ArrayList<String> sGenderType = new ArrayList<>();
    static {
        sGenderType.add("女");
        sGenderType.add("男");
    }

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_teacher_register_one);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mPicker = new OptionsPickerView<>(this);
        mPicker.setCancelable(true);

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

        ((TextView) findViewById(R.id.titlebar_tv_title)).setText("家教注册-基本信息");
        mBirthdayTextView.setText(OtherUtils.getTime(calendar.getTime(), "yyyy 年 MM 月 dd 日"));
        mSchoolTextView.setText(Constants.Data.schoolList.get(0));
        mGradeTextView.setText(Constants.Data.teacherGradeList.get(0));
        mProfessionTextView.setText(Constants.Data.professionList.get(0));
        mHadTeachChildTextView.setText(Constants.Data.hasTeachChildCountRangeList.get(0));
        mHadTeachTimeTextView.setText(Constants.Data.hasTeachChildTimeRangeList.get(0));
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
        OtherUtils.hideSoftInputWindow(mGenderTextView.getWindowToken());
        if (mPicker.isShowing()) {
            mPicker.dismiss();
            return;
        }
        mPicker.setTitle("选择您的性别");
        mPicker.setPicker(sGenderType);
        mPicker.setSelectOptions(0);
        mPicker.setCyclic(false);
        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
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
        mPicker.show();
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
        OtherUtils.hideSoftInputWindow(mGenderTextView.getWindowToken());
        if (mPicker.isShowing()) {
            mPicker.dismiss();
            return;
        }
        mPicker.setTitle("选择您的学校");
        mPicker.setPicker(Constants.Data.schoolList);
        mPicker.setSelectOptions(0);
        mPicker.setCyclic(false);
        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mSchoolTextView.setText(Constants.Data.schoolList.get(options1));
            }
        });
        mPicker.show();
    }

    /**
     * 点击选择年级
     */
    public void onGradeClick(View view) {
        OtherUtils.hideSoftInputWindow(mGenderTextView.getWindowToken());
        if (mPicker.isShowing()) {
            mPicker.dismiss();
            return;
        }
        mPicker.setTitle("选择您的专业");
        mPicker.setPicker(Constants.Data.teacherGradeList);
        mPicker.setSelectOptions(0);
        mPicker.setCyclic(false);
        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mGradeTextView.setText(Constants.Data.teacherGradeList.get(options1));
            }
        });
        mPicker.show();
    }

    /**
     * 点击选择专业
     */
    public void onProfessionClick(View view) {
        OtherUtils.hideSoftInputWindow(mGenderTextView.getWindowToken());
        if (mPicker.isShowing()) {
            mPicker.dismiss();
            return;
        }
        mPicker.setTitle("选择您的专业");
        mPicker.setPicker(Constants.Data.professionList);
        mPicker.setSelectOptions(0);
        mPicker.setCyclic(false);
        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mProfessionTextView.setText(Constants.Data.professionList.get(options1));
            }
        });
        mPicker.show();
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
        OtherUtils.hideSoftInputWindow(mHadTeachChildTextView.getWindowToken());
        if (mPicker.isShowing()) {
            mPicker.dismiss();
            return;
        }
        mPicker.setTitle("已家教过孩子数量");
        mPicker.setPicker(Constants.Data.hasTeachChildCountRangeList);
        mPicker.setSelectOptions(0);
        mPicker.setCyclic(false);
        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mHadTeachChildTextView.setText(Constants.Data.hasTeachChildCountRangeList.get(options1));
            }
        });
        mPicker.show();
    }

    /**
     * 点击选择家教时长
     */
    public void onTeachTimeClick(View view) {
        OtherUtils.hideSoftInputWindow(mHadTeachTimeTextView.getWindowToken());
        if (mPicker.isShowing()) {
            mPicker.dismiss();
            return;
        }
        mPicker.setTitle("已家教过的时长");
        mPicker.setPicker(Constants.Data.hasTeachChildTimeRangeList);
        mPicker.setSelectOptions(0);
        mPicker.setCyclic(false);
        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mHadTeachTimeTextView.setText(Constants.Data.hasTeachChildTimeRangeList.get(options1));
            }
        });
        mPicker.show();
    }

    /**
     * 家教注册第一步点击保存并进入下一页
     */
    public void onRegisterOneClick(View view){
        Bundle extras = new Bundle();
        extras.putBoolean(Constants.Key.IS_REGISTER, true);
        redirectToActivity(this, TeacherRegisterTwoActivity.class, extras);
    }

}
