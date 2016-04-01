package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Order;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.ImageUtils;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 已预定跟已完成订单详情
 */
public class ReservedOrCompletedOrderActivity extends BaseActivity {
    private TextView mOrderNumTextView;
    private TextView mInsuranceNumTextView;
    private TextView mGradeTextView;
    private TextView mCourseTextView;
    private TextView mDateTextView;
    private TextView mTimeTextView;
    private TextView mPriceTextView;
    private TextView mTipTextView;
    private TextView mTotalPriceTextView;
    private TextView mParentNameTextView;
    private TextView mParentGenderTextView;
    private TextView mStudentAgeTextView;
    private TextView mStudentGenderTextView;
    private TextView mTeacherNameTextView;
    private TextView mTeacherInfoTextView;
    private TextView mWaitTeacherApplyTextView;
    private ImageView mTeacherAvatarImageView;
    private View mParentInfoContainer;
    private View mStudentInfoContainer;
    private View mTeacherInfoContainer;
    private View mOperationButtonsContainer;
    private View mNumContainer;

    private Order mOrder;
    private int mOrderType = Constants.Identifier.ORDER_RESERVATION;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_r_or_c_order);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(null != intent){
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mOrder = (Order) extras.getSerializable(Constants.Key.ORDER);
                mOrderType = extras.getInt(Constants.Key.ORDER_TYPE, Constants.Identifier.ORDER_RESERVATION);
            }else{
                mOrder = new Order();
            }
        }else{
            mOrder = new Order();
        }
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("已预订订单");

        mGradeTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_grade);
        mCourseTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_course);
        mDateTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_date);
        mTimeTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_time);
        mPriceTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_price);
        mTipTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_tip);
        mTotalPriceTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_total_price);

        mNumContainer = OtherUtils.findViewById(this, R.id.reserved_order_layout_num_info);
        mOrderNumTextView = OtherUtils.findViewById(this, R.id.order_base_num_tv_order);
        mInsuranceNumTextView = OtherUtils.findViewById(this, R.id.order_base_num_tv_insurance);

        mParentInfoContainer = OtherUtils.findViewById(this, R.id.r_or_c_order_layout_parent_info);
        mParentNameTextView = OtherUtils.findViewById(this, R.id.order_base_parent_info_tv_name);
        mParentGenderTextView = OtherUtils.findViewById(this, R.id.order_base_parent_info_tv_gender);

        mStudentInfoContainer = OtherUtils.findViewById(this, R.id.r_or_c_order_layout_student_info);
        mStudentAgeTextView = OtherUtils.findViewById(this, R.id.order_base_student_info_tv_age);
        mStudentGenderTextView = OtherUtils.findViewById(this, R.id.order_base_student_info_tv_gender);

        mOperationButtonsContainer = OtherUtils.findViewById(this, R.id.r_or_c_order_ll_operation);
        mTeacherInfoContainer = OtherUtils.findViewById(this, R.id.r_or_c_order_layout_teacher_info);

        mTeacherNameTextView = OtherUtils.findViewById(this, R.id.item_search_result_tv_teacher);
        ((View)OtherUtils.findViewById(this, R.id.item_search_result_tv_price)).setVisibility(View.GONE);
        mTeacherInfoTextView = OtherUtils.findViewById(this, R.id.item_search_result_tv_content);
        mTeacherAvatarImageView = OtherUtils.findViewById(this, R.id.item_search_result_civ_avatar);

        mWaitTeacherApplyTextView = OtherUtils.findViewById(this, R.id.r_or_c_order_tv_waiting);

        updateView();
    }

    private void updateView(){
        mGradeTextView.setText(String.format("%s %s", mOrder.getStudentState(), mOrder.getStudentGrade()));
        mCourseTextView.setText(mOrder.getCourseName());
        mDateTextView.setText(String.format("%s %s", OtherUtils.getTime(mOrder.getDate(), "yyyy年MM月dd日(EEEE)"), mOrder.getTeachPeriod()));
        mTimeTextView.setText(OtherUtils.getTimeFromMimute(mOrder.getTeachTime()));
        mPriceTextView.setText(String.format("%.2f", mOrder.getPrice()));
        mTipTextView.setText(String.format("%.2f", mOrder.getTip()));
        mTotalPriceTextView.setText(String.format("%.2f", mOrder.getPrice() + mOrder.getTip()));

        if(mOrderType == Constants.Identifier.ORDER_COMPLETED){
            mNumContainer.setVisibility(View.VISIBLE);
        }

        if(isTeacher()){
            mParentNameTextView.setText(mOrder.getParentName());
            mParentGenderTextView.setText("男");   //Todo fix it
            mStudentGenderTextView.setText(mOrder.getStudentGender() == Constants.Identifier.MALE ? "男" : "女");
            mStudentAgeTextView.setText(String.format("%d", mOrder.getStudentAge()));

            if(mOrderType == Constants.Identifier.ORDER_COMPLETED){
                mOperationButtonsContainer.setVisibility(View.GONE);
            }
        }else{
            mParentInfoContainer.setVisibility(View.GONE);
            mStudentInfoContainer.setVisibility(View.GONE);
            mOperationButtonsContainer.setVisibility(View.GONE);
            mTeacherInfoContainer.setVisibility(View.VISIBLE);
            mWaitTeacherApplyTextView.setVisibility(View.VISIBLE);

            mTeacherNameTextView.setText(mOrder.getTeacherName());
            mTeacherInfoTextView.setText(Order.getBaseInfo(mOrder));
            ImageUtils.displayImage(mOrder.getTeacherAvatar(), mTeacherAvatarImageView);

            if(mOrderType == Constants.Identifier.ORDER_COMPLETED){
                mWaitTeacherApplyTextView.setVisibility(View.GONE);
            }
        }
    }

    public void onBackClick(View view){
        finish();
    }

    private boolean isTeacher(){
        return true;
    }
}
