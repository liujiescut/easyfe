package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.test.Order;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

public class ToDoOrderActivity extends BaseActivity {

    private TextView mNameTextView;
    private TextView mNameLabelTextView;
    private TextView mPhoneTextView;
    private TextView mParentAddressTextView;
    private TextView mOrderNumTextView;
    private TextView mInsuranceNumTextView;
    private TextView mGradeTextView;
    private TextView mCourseTextView;
    private TextView mDateTextView;
    private TextView mTimeTextView;
    private TextView mPriceTextView;
    private TextView mTipTextView;
    private TextView mTotalPriceTextView;
    private TextView mStudentAgeTextView;
    private TextView mStudentGenderTextView;
    private TextView mWarningTextView;
    private TextView mDoingTextView;
    private TextView mPayTextView;
    private View mStudentInfoContainer;
    private View mParentAddressContainer;

    private Order mOrder;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_to_do_order);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(null != intent){
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mOrder = (Order) extras.getSerializable(Constants.Key.ORDER);
            }else{
                mOrder = new Order();
            }
        }else{
            mOrder = new Order();
        }
    }

    @Override
    protected void initView() {

        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("待执行订单");

        mNameTextView = OtherUtils.findViewById(this, R.id.to_do_order_tv_name);
        mNameLabelTextView = OtherUtils.findViewById(this, R.id.to_do_order_tv_name_label);
        mPhoneTextView = OtherUtils.findViewById(this, R.id.to_do_order_tv_phone);
        mParentAddressTextView = OtherUtils.findViewById(this, R.id.to_do_order_tv_parent_address);
        mParentAddressContainer = OtherUtils.findViewById(this, R.id.to_do_order_ll_parent_address);

        mStudentInfoContainer = OtherUtils.findViewById(this, R.id.to_do_order_layout_student_info);
        mStudentAgeTextView = OtherUtils.findViewById(this, R.id.order_base_student_info_tv_age);
        mStudentGenderTextView = OtherUtils.findViewById(this, R.id.order_base_student_info_tv_gender);

        mOrderNumTextView = OtherUtils.findViewById(this, R.id.order_base_num_tv_order);
        mInsuranceNumTextView = OtherUtils.findViewById(this, R.id.order_base_num_tv_insurance);

        mGradeTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_grade);
        mCourseTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_course);
        mDateTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_date);
        mTimeTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_time);
        mPriceTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_price);
        mTipTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_tip);
        mTotalPriceTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_total_price);
        mWarningTextView = OtherUtils.findViewById(this, R.id.to_do_order_tv_warning);
        mDoingTextView = OtherUtils.findViewById(this, R.id.to_do_order_tv_doing);
        mPayTextView = OtherUtils.findViewById(this, R.id.to_do_order_tv_pay);

        updateView();
    }

    private void updateView(){
        mOrderNumTextView.setText("XXXX XXXX XXXX");
        mInsuranceNumTextView.setText("XXXX XXXX XXXX");

        mGradeTextView.setText(String.format("%s %s", mOrder.getStudentState(), mOrder.getStudentGrade()));
        mCourseTextView.setText(mOrder.getCourseName());
        mDateTextView.setText(String.format("%s %s", OtherUtils.getTime(mOrder.getDate(), "yyyy年MM月dd日(EEEE)"), mOrder.getTeachPeriod()));
        mTimeTextView.setText(OtherUtils.getTimeFromMimute(mOrder.getTeachTime()));
        mPriceTextView.setText(String.format("%.2f", mOrder.getPrice()));
        mTipTextView.setText(String.format("%.2f", mOrder.getTip()));
        mTotalPriceTextView.setText(String.format("%.2f", mOrder.getPrice() + mOrder.getTip()));
        mWarningTextView.setText("我就是温馨提示喽");

        if(isTeacher()){
            mNameTextView.setText(mOrder.getParentName());
            mPhoneTextView.setText(mOrder.getParentPhone());
            mParentAddressTextView.setText(mOrder.getParentAddress());

            mStudentAgeTextView.setText(String.format("%d", mOrder.getStudentAge()));
            mStudentGenderTextView.setText(mOrder.getStudentGender() == Constants.Identifier.MALE ? "男" : "女");
        }else {
            mNameLabelTextView.setText("家教姓名");
            mNameTextView.setText(mOrder.getTeacherName());
            mNameTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.mipmap.icon_right_arrow_padding,0);
            mPhoneTextView.setText(mOrder.getTeacherPhone());
            mParentAddressContainer.setVisibility(View.GONE);
            mStudentInfoContainer.setVisibility(View.GONE);
            mDoingTextView.setVisibility(View.GONE);
            mPayTextView.setVisibility(View.VISIBLE);
        }
    }

    public void onBackClick(View view){
        finish();
    }

    private boolean isTeacher(){
        return true;
    }
}
