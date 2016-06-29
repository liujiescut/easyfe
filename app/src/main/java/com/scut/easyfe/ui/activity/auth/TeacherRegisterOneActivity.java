package com.scut.easyfe.ui.activity.auth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Address;
import com.scut.easyfe.entity.ChildGrade;
import com.scut.easyfe.entity.School;
import com.scut.easyfe.entity.user.Teacher;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.authentication.RCheckVerifyCode;
import com.scut.easyfe.network.request.authentication.RGetSms;
import com.scut.easyfe.network.request.info.RGetChildGrade;
import com.scut.easyfe.network.request.info.RGetSchool;
import com.scut.easyfe.network.request.user.RUserInfoModify;
import com.scut.easyfe.ui.activity.AddressActivity;
import com.scut.easyfe.ui.activity.MainActivity;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.MapUtils;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.TimeUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 注册第一步(基本信息) 跟 修改信息
 *
 * @author jay
 */
public class TeacherRegisterOneActivity extends BaseActivity {
    public static final int REQUEST_CODE = 0;
    private EditText mNameEditText;         //姓名
    private EditText mPhoneEditText;        //手机
    private EditText mVerifyCodeEditText;     //密码
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
    private TextView mGetVerifyCodeTextView;//获取验证码

    /**
     * 下面几项为修改信息时用到
     */
    private LinearLayout mChildGenderLinearLayout;     //宝贝性别所在LinearLayout
    private LinearLayout mChildGradeLinearLayout;      //宝贝年级所在LinearLayout
    private LinearLayout mVerifyLinearLayout;      //宝贝年级所在LinearLayout
    private TextView mChildGenderTextView;             //宝贝性别
    private TextView mChildGradeTextView;              //宝贝年级
    private TextView mDoModifyTextView;                //确认保存修改
    private int mChildGender = Constants.Identifier.FEMALE;

    private double mLatitude = -1d;    //定位所在的纬度
    private double mLongitude = -1d;   //定位所在的经度
    private String mAddress;           //定位所在的地址
    private String mCity;              //定位所在的城市
    private int mGender = Constants.Identifier.FEMALE;   //选择的性别

    private OptionsPickerView<String> mSinglePicker;
    private OptionsPickerView<String> mDoublePicker;
    private TimePickerView mTimePicker;

    private int mFromType = Constants.Identifier.TYPE_REGISTER;   //到此页面的功能(注册还是修改信息)
    private int mSecondLeft = Constants.Config.VERIFY_INTERVAL;   //多少秒后可再次发送验证码

    private User mUser;
    private Date mBirthday = new Date();

    private List<School> mSchools = new ArrayList<>();
    private ArrayList<String> mSchoolNames = new ArrayList<>();
    private ArrayList<String> mProfessionNames = new ArrayList<>();
    private boolean mGetSchoolSuccess = false;
    private boolean mGetGradeSuccess = false;
    private ArrayList<String> mStates = new ArrayList<>();
    private ArrayList<ArrayList<String>> mGrades = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        /** 让User保持最新 */
        mUser = App.getUser(mFromType != Constants.Identifier.TYPE_REGISTER).getCopy();
    }

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_teacher_register_one);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mFromType = extras.getInt(Constants.Key.TO_TEACHER_REGISTER_ONE_ACTIVITY_TYPE, Constants.Identifier.TYPE_REGISTER);
            }
        }

        mUser = App.getUser(mFromType != Constants.Identifier.TYPE_REGISTER).getCopy();
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
        mVerifyCodeEditText = OtherUtils.findViewById(this, R.id.teacher_register_one_et_verify_code);
        mGetVerifyCodeTextView = OtherUtils.findViewById(this, R.id.teacher_register_one_tv_verify);
        mVerifyLinearLayout = OtherUtils.findViewById(this, R.id.teacher_register_one_ll_verify);
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

        mBirthdayTextView.setText(TimeUtils.getTime(calendar.getTime(), "yyyy 年 MM 月 dd 日"));
        mGradeTextView.setText(Constants.Data.teacherGradeList.get(0));
        mHadTeachChildTextView.setText(Constants.Data.hasTeachChildCountRangeList.get(0));
        mHadTeachTimeTextView.setText(Constants.Data.hasTeachChildTimeRangeList.get(0));

        updateView();
    }

    public void updateView() {
        if (mFromType == Constants.Identifier.TYPE_REGISTER) {
            ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("家教注册-基本信息");
        } else {
            ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("基本信息维护");
            mVerifyLinearLayout.setVisibility(View.GONE);
            mGoNextTextView.setVisibility(View.GONE);
            mDoModifyTextView.setVisibility(View.VISIBLE);

            if (App.getUser().getType() == Constants.Identifier.USER_TP) {
                //既是家长又是家教
                mChildGenderLinearLayout.setVisibility(View.VISIBLE);
                mChildGradeLinearLayout.setVisibility(View.VISIBLE);
            }

            mNameEditText.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mGenderTextView.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mBirthdayTextView.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mPhoneEditText.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mVerifyCodeEditText.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mIdCardEditText.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mSchoolTextView.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mGradeTextView.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mProfessionTextView.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mProfessionTextView.setEnabled(false);
            mGradeTextView.setEnabled(false);
            mSchoolTextView.setEnabled(false);
            mIdCardEditText.setEnabled(false);
            mVerifyCodeEditText.setEnabled(false);
            mPhoneEditText.setEnabled(false);
            mBirthdayTextView.setEnabled(false);
            mGenderTextView.setEnabled(false);
            mNameEditText.setEnabled(false);
            mGenderTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mBirthdayTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mSchoolTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mGradeTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mProfessionTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        }

        mNameEditText.setText(mUser.getName());
        mGenderTextView.setText(mUser.getGender() == Constants.Identifier.MALE ? R.string.male : R.string.female);
        mBirthdayTextView.setText(TimeUtils.getTime(new Date(mUser.getBirthday()), "yyyy 年 MM 月 dd 日"));
        mPhoneEditText.setText(mUser.getPhone());
        mVerifyCodeEditText.setText(mUser.getPassword());
        mIdCardEditText.setText(mUser.getTeacherMessage().getIdCard());
        if (mUser.getTeacherMessage().getSchool().length() != 0) {
            mSchoolTextView.setText(mUser.getTeacherMessage().getSchool());
        }
        if (mUser.getTeacherMessage().getGrade().length() != 0) {
            mGradeTextView.setText(mUser.getTeacherMessage().getGrade());
        }
        if (mUser.getTeacherMessage().getProfession().length() != 0) {
            mProfessionTextView.setText(mUser.getTeacherMessage().getProfession());
        }
        if (mUser.getTeacherMessage().getTeachCount().length() != 0) {
            mHadTeachChildTextView.setText(mUser.getTeacherMessage().getTeachCount());
        }
        if (mUser.getTeacherMessage().getHadTeach().length() != 0) {
            mHadTeachTimeTextView.setText(mUser.getTeacherMessage().getHadTeach());
        }

        if (mUser.getParentMessage().getChildGrade().length() != 0) {
            mChildGradeTextView.setText(mUser.getParentMessage().getChildGrade());
        }
        mChildGenderTextView.setText(mUser.getParentMessage().getChildGender() == Constants.Identifier.MALE ? R.string.male : R.string.female);
    }

    @Override
    protected void fetchData() {
        if (mFromType == Constants.Identifier.TYPE_REGISTER) {
            startLoading("加载数据中", new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (!(mGetSchoolSuccess && mGetGradeSuccess)) {
                        toast("加载数据失败");
                        finish();
                    }
                }
            });

            RequestManager.get().execute(new RGetSchool(), new RequestListener<List<School>>() {
                @Override
                public void onSuccess(RequestBase request, List<School> result) {
                    mSchools.clear();
                    mSchools.addAll(result);

                    mSchoolNames.clear();
                    for (School school :
                            mSchools) {
                        mSchoolNames.add(school.getSchool());
                    }

                    if (mSchools.size() > 0) {
                        mProfessionNames.addAll(mSchools.get(0).getProfession());
                        mSchoolTextView.setText(mSchoolNames.get(0));
                        mProfessionTextView.setText(mProfessionNames.get(0));
                    }

                    mGetSchoolSuccess = true;
                    if (mGetGradeSuccess) {
                        stopLoading();
                    }
                }

                @Override
                public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                    toast(errorMsg);
                    stopLoading();
                }
            });

            RequestManager.get().execute(new RGetChildGrade(), new RequestListener<List<ChildGrade>>() {
                @Override
                public void onSuccess(RequestBase request, List<ChildGrade> result) {
                    for (ChildGrade childGrade :
                            result) {
                        mStates.add(childGrade.getName());
                        ArrayList<String> tempGrades = new ArrayList<>();
                        tempGrades.addAll(childGrade.getGrade());
                        mGrades.add(tempGrades);
                    }

                    if (mStates.size() > 0) {
                        mChildGradeTextView.setText(String.format("%s %s", mStates.get(0), mGrades.get(0).get(0)));
                    }

                    mGetGradeSuccess = true;
                    if (mGetSchoolSuccess) {
                        stopLoading();
                    }
                }

                @Override
                public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                    toast(errorMsg);
                    stopLoading();
                }
            });
        }

        if (null == mUser.getPosition().getAddress() || mUser.getPosition().getAddress().length() == 0) {

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
        } else {
            mLatitude = mUser.getPosition().getLatitude();
            mLongitude = mUser.getPosition().getLongitude();
            mAddress = mUser.getPosition().getAddress();
            mCity = mUser.getPosition().getCity();
            mAddressTextView.setText(mAddress);
        }
    }

    @Override
    protected void initListener() {
        mTimePicker.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                mBirthdayTextView.setText(TimeUtils.getTime(date, "yyyy 年 MM 月 dd 日"));
                mBirthday = date;
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
        } else {
            toast("获取地址失败,请重试");
        }
    }


    static Handler handler = new Handler();

    /**
     * 显示倒计时第二次获取验证码
     */
    private void showTimer() {
        mGetVerifyCodeTextView.setClickable(false);
        mGetVerifyCodeTextView.setBackgroundResource(R.drawable.shape_login_verify_unable);
        mGetVerifyCodeTextView.setTextColor(getResources().getColor(R.color.text_area_bg));
        mGetVerifyCodeTextView.setText(String.format(Locale.CHINA, "%d 秒后重试", mSecondLeft));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSecondLeft--;
                if (mSecondLeft == 0) {
                    mSecondLeft = Constants.Config.VERIFY_INTERVAL;
                    mGetVerifyCodeTextView.setBackgroundResource(R.drawable.selector_spread_reserve);
                    mGetVerifyCodeTextView.setTextColor(getResources().getColor(R.color.theme_color));
                    mGetVerifyCodeTextView.setText("获取验证码");
                    mGetVerifyCodeTextView.setClickable(true);
                } else {
                    mGetVerifyCodeTextView.setText(String.format(Locale.CHINA, "%d 秒后重试", mSecondLeft));
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);

    }

    public void onVerifyClick(View v){
        String phone = mPhoneEditText.getText().toString();
        if(phone.length() != 11){
            toast("请输入有效手机号码");
            return;
        }

        showTimer();
        RequestManager.get().execute(new RGetSms(phone), new RequestListener<JSONObject>() {
            @Override
            public void onSuccess(RequestBase request, JSONObject result) {
                toast(result.optString("message"));
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast("发送验证码失败,请稍后重试");
            }
        });
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
        mSinglePicker.setPicker(mSchoolNames);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mSchoolTextView.setText(mSchoolNames.get(options1));
                mProfessionNames.clear();
                mProfessionNames.addAll(mSchools.get(options1).getProfession());
                mProfessionTextView.setText(mProfessionNames.get(0));
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

        mSinglePicker.setTitle("选择您的年级");
        mSinglePicker.setPicker(mProfessionNames);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mProfessionTextView.setText(mProfessionNames.get(options1));
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
    public void onRegisterOneClick(View view) {
        getBaseUserInfo();

        if (!validate(mUser)) {
            return;
        }

        RequestManager.get().execute(
                new RCheckVerifyCode(mUser.getPhone(), mVerifyCodeEditText.getText().toString()),
                new RequestListener<JSONObject>() {
                    @Override
                    public void onSuccess(RequestBase request, JSONObject result) {
                        if(result.optBoolean("auth", false)){
                            toast("保存成功");
                            App.setUser(mUser);

                            Bundle extras = new Bundle();
                            extras.putBoolean(Constants.Key.IS_REGISTER, true);
                            redirectToActivity(mContext, TeacherRegisterTwoActivity.class, extras);
                        }else{
                            toast("验证码错误");
                        }
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        toast(errorMsg);
                    }
                });
    }

    private void getBaseUserInfo() {
        if (null == mUser) {
            mUser = new User();
        }

        mUser.setName(mNameEditText.getText().toString());
        mUser.setGender(mGender);
        mUser.setBirthday(mBirthday.getTime());
        mUser.setPhone(mPhoneEditText.getText().toString());

        Teacher teacher = mUser.getTeacherMessage();
        teacher.setIdCard(mIdCardEditText.getText().toString());
        teacher.setSchool(mSchoolTextView.getText().toString());
        teacher.setGrade(mGradeTextView.getText().toString());
        teacher.setProfession(mProfessionTextView.getText().toString());
        teacher.setTeachCount(mHadTeachChildTextView.getText().toString());
        teacher.setHadTeach(mHadTeachTimeTextView.getText().toString());
        mUser.setTeacherMessage(teacher);

        Address address = mUser.getPosition();
        address.setAddress(mAddress);
        address.setLatitude(mLatitude);
        address.setLongitude(mLongitude);
        address.setCity(mCity);
        mUser.setPosition(address);
    }

    private boolean validate(User user) {
        return validate(user, true);
    }
    private boolean validate(User user, boolean hasSms) {
        if (null == user.getName() || user.getName().length() == 0) {
            toast("请输入用户名");
            return false;
        }

        if (null == user.getPhone() || user.getPhone().length() != 11) {
            toast("请输入有效手机号");
            return false;
        }

        if (hasSms && mVerifyCodeEditText.getText().toString().length() != 6) {
            toast("请输入有效验证码");
            return false;
        }

        if (null == user.getTeacherMessage().getIdCard() || user.getTeacherMessage().getIdCard().length() == 0) {
            toast("请输入身份证号");
            return false;
        }

        if (null == user.getPosition() ||
                user.getPosition().getAddress() == null || user.getPosition().getAddress().length() == 0 ||
                user.getPosition().getLatitude() == -1 ||
                user.getPosition().getLongitude() == -1) {
            DialogUtils.makeConfirmDialog(TeacherRegisterOneActivity.this, "温馨提示", getResources().getString(R.string.address_disable_info_teacher)).show();
            return false;
        }

        return true;
    }

    public void onBackClick(View view) {
        finish();
    }


    public void onModifyClick(View view) {
        getBaseUserInfo();
        validate(mUser, false);

        mUser.getTeacherMessage().setTeachCount(mHadTeachChildTextView.getText().toString());
        mUser.getTeacherMessage().setHadTeach(mHadTeachTimeTextView.getText().toString());

        if (mUser.getType() == Constants.Identifier.USER_TP) {
            mUser.getParentMessage().setChildGrade(mChildGradeTextView.getText().toString());
            mUser.getParentMessage().setChildGender(mChildGender);
        }

        RequestManager.get().execute(new RUserInfoModify(mUser), new RequestListener<JSONObject>() {
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
    }

    public void onChildGenderClick(View view) {
        hidePickerIfShowing();

        mSinglePicker.setTitle("选择宝贝性别");
        mSinglePicker.setPicker(Constants.Data.genderList);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mChildGenderTextView.setText(Constants.Data.genderList.get(options1));
                mChildGender = options1;
            }
        });
        mSinglePicker.show();
    }

    public void onChildGradeClick(View view) {
        hidePickerIfShowing();

        mDoublePicker.setTitle("选择宝贝年级");
        mDoublePicker.setPicker(mStates, mGrades, true);
        mDoublePicker.setSelectOptions(0, 0);
        mDoublePicker.setCyclic(false);
        mDoublePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mChildGradeTextView.setText(String.format("%s %s",
                        mStates.get(options1),
                        mGrades.get(options1).get(option2)));
            }
        });
        mDoublePicker.show();
    }

    private boolean hidePickerIfShowing() {
        OtherUtils.hideSoftInputWindow(mChildGradeTextView.getWindowToken());
        boolean isShowing = false;
        if (mSinglePicker.isShowing()) {
            mSinglePicker.dismiss();
            isShowing = true;
        }

        if (mDoublePicker.isShowing()) {
            mDoublePicker.dismiss();
            isShowing = true;
        }

        return isShowing;
    }

}
