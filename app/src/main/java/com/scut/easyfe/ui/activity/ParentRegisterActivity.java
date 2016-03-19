package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.MapUtils;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;

/**
 * 家长注册页面
 */
public class ParentRegisterActivity extends BaseActivity {
    public static final int REQUEST_CODE = 0;

    private OptionsPickerView<String> mPicker;
    public static final ArrayList<String> sGenderType = new ArrayList<>();

    private EditText mParentNameEditText;           //家长姓名
    private EditText mParentPhoneEditText;          //家长手机
    private EditText mParentPasswordEditText;       //家长密码
    private TextView mParentGenderTextView;         //家长性别
    private TextView mAddressTextView;              //家庭地址
    private TextView mChildGenderTextView;          //宝贝性别
    private TextView mChildGradeTextView;           //宝贝年级

    private int mParentGender = Constants.Identifier.FEMALE;   //家长选择的性别
    private int mChildGender = Constants.Identifier.FEMALE;    //宝贝的性别

    private double mLatitude = -1d;    //定位所在的纬度
    private double mLongitude = -1d;   //定位所在的经度
    private String mAddress;     //定位所在的地址

    static {
        sGenderType.add("女");
        sGenderType.add("男");
    }

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_parent_register);
    }

    @Override
    protected void initView() {
        mPicker = new OptionsPickerView<>(this);
        mPicker.setCancelable(true);

        ((TextView) findViewById(R.id.titlebar_tv_title)).setText("家长注册");
        mParentNameEditText = OtherUtils.findViewById(this, R.id.parent_register_et_name);
        mParentPhoneEditText = OtherUtils.findViewById(this, R.id.parent_register_et_phone);
        mParentPasswordEditText = OtherUtils.findViewById(this, R.id.parent_register_et_password);
        mParentGenderTextView = OtherUtils.findViewById(this, R.id.parent_register_tv_parent_gender);
        mParentGenderTextView = OtherUtils.findViewById(this, R.id.parent_register_tv_parent_gender);
        mChildGenderTextView = OtherUtils.findViewById(this, R.id.parent_register_tv_child_gender);
        mChildGradeTextView = OtherUtils.findViewById(this, R.id.parent_register_tv_child_grade);
        mAddressTextView = OtherUtils.findViewById(this, R.id.parent_register_tv_address);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void fetchData() {
        MapUtils.getLocation(new MapUtils.LocationCallback() {
            @Override
            public void onSuccess(double latitude, double longitude, String address) {
                mLatitude = latitude;
                mLongitude = longitude;
                mAddress = (null == address ? "" : address);
                mAddressTextView.setText(mAddress);
            }

            @Override
            public void onFailed() {
                LogUtils.i(Constants.Tag.MAP_TAG, "定位失败");
            }
        });
    }

    /**
     * 点击左上角返回
     *
     * @param view 被点击视图
     */
    public void onBackClick(View view) {

    }

    /**
     * 选择本人性别
     *
     * @param view 被点击视图
     */
    public void onParentGenderClick(View view) {
        if (mPicker.isShowing()) {
            mPicker.dismiss();
            return;
        }
        mPicker.setTitle("选择您的性别");
        mPicker.setPicker(sGenderType);
        mPicker.setSelectOptions(0);
        mPicker.setCyclic(false);
        mPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                if (options1 == 0) {
                    //选择了女
                    mParentGender = Constants.Identifier.FEMALE;
                    mParentGenderTextView.setText(R.string.female);
                } else if (options1 == 1) {
                    //选择了男
                    mParentGender = Constants.Identifier.FEMALE;
                    mParentGenderTextView.setText(R.string.male);
                }
            }
        });
        mPicker.show();
    }

    /**
     * @param view 被点击视图
     */
    public void onChildGenderClick(View view) {
        if (mPicker.isShowing()) {
            mPicker.dismiss();
            return;
        }
        mPicker.setTitle("选择宝贝性别");
        mPicker.setPicker(sGenderType);
        mPicker.setSelectOptions(0);
        mPicker.setCyclic(false);
        mPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                if (options1 == 0) {
                    //选择了女
                    mChildGender = Constants.Identifier.FEMALE;
                    mChildGenderTextView.setText(R.string.female);
                } else if (options1 == 1) {
                    //选择了男
                    mChildGender = Constants.Identifier.FEMALE;
                    mChildGenderTextView.setText(R.string.male);
                }
            }
        });
        mPicker.show();
    }

    /**
     * @param view 被点击视图
     */
    public void onChildGradeClick(View view) {
        //Todo 选择宝贝年级
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
            Intent intent = new Intent(this, AddressActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            MapUtils.getLocation(new MapUtils.LocationCallback() {
                @Override
                public void onSuccess(double latitude, double longitude, String address) {
                    mLatitude = latitude;
                    mLongitude = longitude;
                    mAddress = address;
                    Bundle bundle = new Bundle();
                    bundle.putDouble(Constants.Key.LATITUDE, mLatitude);
                    bundle.putDouble(Constants.Key.LONGITUDE, mLongitude);
                    bundle.putString(Constants.Key.ADDRESS, mAddress);
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE && resultCode == AddressActivity.RESULT_OK && null != intent) {
            mAddress = intent.getStringExtra(Constants.Key.ADDRESS);
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
}
