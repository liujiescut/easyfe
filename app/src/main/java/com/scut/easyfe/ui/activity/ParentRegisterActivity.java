package com.scut.easyfe.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Address;
import com.scut.easyfe.entity.ChildGrade;
import com.scut.easyfe.entity.user.Parent;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.authentication.RParentRegister;
import com.scut.easyfe.network.request.info.RGetChildGrade;
import com.scut.easyfe.network.request.user.RUserInfoModify;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.MapUtils;
import com.scut.easyfe.utils.OtherUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 家长注册 跟 修改基本信息
 *
 * @author jay
 */
public class ParentRegisterActivity extends BaseActivity {
    public static final int REQUEST_CODE = 0;

    private OptionsPickerView<String> mDoublePicker;
    private OptionsPickerView<String> mSinglePicker;

    private LinearLayout mVerifyLinearLayout;
    private EditText mParentNameEditText;           //家长姓名
    private EditText mParentPhoneEditText;          //家长手机
    private EditText mVerifyCodeEditText;       //家长密码
    private TextView mParentGenderTextView;         //家长性别
    private TextView mAddressTextView;              //家庭地址
    private TextView mChildGenderTextView;          //宝贝性别
    private TextView mChildAgeTextView;             //宝贝年龄
    private TextView mChildGradeTextView;           //宝贝年级
    private TextView mHasAccountHintTextView;       //已经有账号时的提示信息
    private TextView mRegisterTextView;             //注册按钮
    private TextView mGetVerifyCodeTextView;           //发送验证码按钮

    private int mSecondLeft = Constants.Config.VERIFY_INTERVAL; //多少秒后可以再次发送验证码
    private int mParentGender = Constants.Identifier.FEMALE;   //家长选择的性别
    private int mChildGender = Constants.Identifier.FEMALE;    //宝贝的性别
    private int mChildAge = 10;                                //孩子年龄默认为10

    private double mLatitude = -1d;    //定位所在的纬度
    private double mLongitude = -1d;   //定位所在的经度
    private String mAddress;           //定位所在的地址
    private String mCity;              //定位所在的城市

    private int mFromType = Constants.Identifier.TYPE_REGISTER;   //到此页面的功能(注册还是修改家教信息)

    private User mUser = new User();
    private boolean mGetGradeSuccess = false;
    private ArrayList<String> mStates = new ArrayList<>();
    private ArrayList<ArrayList<String>> mGrades = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        mUser = App.getUser(mFromType != Constants.Identifier.TYPE_REGISTER).getCopy();
    }

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_parent_register);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mFromType = extras.getInt(Constants.Key.TO_PARENT_REGISTER_ACTIVITY_TYPE, Constants.Identifier.TYPE_REGISTER);
            }
        }

        mUser = App.getUser(mFromType != Constants.Identifier.TYPE_REGISTER).getCopy();

        mAddress = mUser.getPosition().getAddress();
        mLatitude = mUser.getPosition().getLatitude();
        mLongitude = mUser.getPosition().getLongitude();
        mCity = mUser.getPosition().getCity();
    }

    @Override
    protected void initView() {
        mDoublePicker = new OptionsPickerView<>(this);
        mSinglePicker = new OptionsPickerView<>(this);
        mDoublePicker.setCancelable(true);
        mSinglePicker.setCancelable(false);

        mVerifyLinearLayout = OtherUtils.findViewById(this, R.id.parent_register_ll_verify);
        mParentNameEditText = OtherUtils.findViewById(this, R.id.parent_register_et_name);
        mParentPhoneEditText = OtherUtils.findViewById(this, R.id.parent_register_et_phone);
        mGetVerifyCodeTextView = OtherUtils.findViewById(this, R.id.parent_register_tv_verify);
        mVerifyCodeEditText = OtherUtils.findViewById(this, R.id.parent_register_et_verify_code);
        mParentGenderTextView = OtherUtils.findViewById(this, R.id.parent_register_tv_parent_gender);
        mParentGenderTextView = OtherUtils.findViewById(this, R.id.parent_register_tv_parent_gender);
        mChildGenderTextView = OtherUtils.findViewById(this, R.id.parent_register_tv_child_gender);
        mChildAgeTextView = OtherUtils.findViewById(this, R.id.parent_register_tv_child_age);
        mChildGradeTextView = OtherUtils.findViewById(this, R.id.parent_register_tv_child_grade);
        mAddressTextView = OtherUtils.findViewById(this, R.id.parent_register_tv_address);
        mRegisterTextView = OtherUtils.findViewById(this, R.id.parent_register_tv_submit);
        mHasAccountHintTextView = OtherUtils.findViewById(this, R.id.parent_register_tv_has_account_hint);

        updateView();
    }

    private void updateView() {
        if (mFromType == Constants.Identifier.TYPE_REGISTER) {
            ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("家长注册");

        } else {
            ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("基本信息维护");
            mHasAccountHintTextView.setVisibility(View.GONE);
            mVerifyLinearLayout.setVisibility(View.GONE);
            mRegisterTextView.setText("确认并保存");
            mParentNameEditText.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mParentNameEditText.setEnabled(false);
            mParentGenderTextView.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mParentGenderTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mParentGenderTextView.setEnabled(false);
            mParentPhoneEditText.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mParentPhoneEditText.setEnabled(false);
            mVerifyCodeEditText.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mVerifyCodeEditText.setEnabled(false);
        }

        mParentNameEditText.setText(mUser.getName());
        mParentGenderTextView.setText(mUser.getGender() == Constants.Identifier.MALE ? R.string.male : R.string.female);
        mParentPhoneEditText.setText(mUser.getPhone());
        mVerifyCodeEditText.setText(mUser.getPassword());
        mChildGenderTextView.setText(mUser.getParentMessage().getChildGender() == Constants.Identifier.MALE ? R.string.male : R.string.female);
        mChildGradeTextView.setText(mUser.getParentMessage().getChildGrade());
        mChildAgeTextView.setText(String.format("%s 岁", mUser.getParentMessage().getChildAge()));
        mAddressTextView.setText(mUser.getPosition().getAddress());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void fetchData() {
        if(null == mAddress || mAddress.length() == 0) {
            MapUtils.getLocation(new MapUtils.LocationCallback() {
                @Override
                public void onSuccess(double latitude, double longitude, String address, String city) {
                    mLatitude = latitude;
                    mLongitude = longitude;
                    mAddress = (null == address ? "" : address);
                    mCity = city;

                    if (mFromType == Constants.Identifier.TYPE_REGISTER) {
                        mAddressTextView.setText(mAddress);
                    }
                }

                @Override
                public void onFailed() {
                    LogUtils.i(Constants.Tag.MAP_TAG, "定位失败");
                }
            });
        }

        startLoading("加载数据中", new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!mGetGradeSuccess) {
                    toast("获取数据失败");
                    finish();
                }
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
                    mDoublePicker.setPicker(mStates, mGrades, true);
                    mDoublePicker.setCyclic(false);
                    if(mFromType == Constants.Identifier.TYPE_REGISTER) {
                        mDoublePicker.setSelectOptions(0, 0);
                        mChildGradeTextView.setText(String.format("%s %s", mStates.get(0), mGrades.get(0).get(0)));
                    }
                }

                mGetGradeSuccess = true;
                stopLoading();
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
            }
        });
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
        toast("get verification");
        showTimer();
    }

    /**
     * 点击左上角返回
     *
     * @param view 被点击视图
     */
    public void onBackClick(View view) {
        finish();
    }

    /**
     * 选择本人性别
     *
     * @param view 被点击视图
     */
    public void onParentGenderClick(View view) {
        OtherUtils.hideSoftInputWindow(mParentGenderTextView.getWindowToken());
        if (mSinglePicker.isShowing()) {
            mSinglePicker.dismiss();
            return;
        }
        mSinglePicker.setTitle("选择您的性别");
        mSinglePicker.setPicker(Constants.Data.genderList);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mParentGender = options1 == 0 ? Constants.Identifier.FEMALE : Constants.Identifier.MALE;
                mParentGenderTextView.setText(Constants.Data.genderList.get(options1));
            }
        });
        mSinglePicker.show();
    }

    /**
     * @param view 被点击视图
     */
    public void onChildGenderClick(View view) {
        OtherUtils.hideSoftInputWindow(mChildGenderTextView.getWindowToken());
        if (mSinglePicker.isShowing()) {
            mSinglePicker.dismiss();
            return;
        }
        mSinglePicker.setTitle("选择宝贝性别");
        mSinglePicker.setPicker(Constants.Data.genderList);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mChildGender = options1 == 0 ? Constants.Identifier.FEMALE : Constants.Identifier.MALE;
                mChildGenderTextView.setText(Constants.Data.genderList.get(options1));
            }
        });
        mSinglePicker.show();
    }

    public void onChildAgeClick(View view){
        OtherUtils.hideSoftInputWindow(mChildAgeTextView.getWindowToken());
        if (mSinglePicker.isShowing()) {
            mSinglePicker.dismiss();
            return;
        }
        mSinglePicker.setTitle("选择宝贝年龄");
        mSinglePicker.setPicker(Constants.Data.ageList);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mChildAge = options1 + 1;
                mChildAgeTextView.setText(String.format("%s 岁", Constants.Data.ageList.get(options1)));
            }
        });
        mSinglePicker.show();
    }

    /**
     * @param view 被点击视图
     */
    public void onChildGradeClick(View view) {
        OtherUtils.hideSoftInputWindow(mChildGradeTextView.getWindowToken());
        if (mDoublePicker.isShowing()) {
            mDoublePicker.dismiss();
            return;
        }

        mDoublePicker.setTitle("选择宝贝年级");
        mDoublePicker.setSelectOptions(0, 0);
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

    /**
     * @param view 被点击视图
     */
    public void onAddressClick(View view) {
        if (mLatitude != -1) {
            Bundle bundle = new Bundle();
            bundle.putDouble(Constants.Key.LATITUDE, mLatitude);
            bundle.putDouble(Constants.Key.LONGITUDE, mLongitude);
            bundle.putString(Constants.Key.ADDRESS, mAddress);
            bundle.putString(Constants.Key.CITY, mCity);
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
                    Intent intent = new Intent(ParentRegisterActivity.this, AddressActivity.class);
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
     * @param view 被点击视图
     */
    public void onRegisterClick(View view) {
        mUser.setName(mParentNameEditText.getText().toString());
        mUser.setGender(mParentGender);
        mUser.setPhone(mParentPhoneEditText.getText().toString());

        Parent parentMsg = new Parent();
        parentMsg.setChildGender(mChildGender);
        parentMsg.setChildGrade(mChildGradeTextView.getText().toString());
        parentMsg.setChildAge(mChildAge);
        mUser.setParentMessage(parentMsg);

        Address address = new Address();
        address.setAddress(mAddress);
        address.setLatitude(mLatitude);
        address.setLongitude(mLongitude);
        address.setCity(mCity);
        mUser.setPosition(address);

        if (!validate(mUser)) {
            return;
        }

        if (mFromType == Constants.Identifier.TYPE_REGISTER) {
            RequestManager.get().execute(new RParentRegister(mUser), new RequestListener<User>() {
                @Override
                public void onSuccess(RequestBase request, User result) {
                    toast("注册成功");
                    mUser.set_id(result.get_id());
                    mUser.setToken(result.getToken());
                    mUser.setAvatar(result.getAvatar());
                    mUser.setType(result.getType());
                    App.setUser(mUser);
                    redirectToActivity(mContext, MainActivity.class);
                }

                @Override
                public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                    toast(errorMsg);
                }
            });
        } else if (mFromType == Constants.Identifier.TYPE_MODIFY) {
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

    }

    private boolean validate(User user) {
        if (null == user.getName() || user.getName().length() == 0) {
            toast("请输入用户名");
            return false;
        }

        if (null == user.getPhone() || user.getPhone().length() != 11) {
            toast("请输入有效手机号");
            return false;
        }

        if (mVerifyCodeEditText.getText().toString().length() != 6) {
            toast("请输入有效验证码");
            return false;
        }

        if (null == user.getParentMessage().getChildGrade() || user.getParentMessage().getChildGrade().length() == 0) {
            toast("请选择宝贝年级");
            return false;
        }

        if (null == user.getPosition() ||
                user.getPosition().getAddress() == null || user.getPosition().getAddress().length() == 0 ||
                user.getPosition().getLatitude() == -1 ||
                user.getPosition().getLongitude() == -1 ||
                user.getPosition().getCity().length() == 0) {
            toast("选择地址无效,请重新选择");
            return false;
        }

        return true;
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
}
