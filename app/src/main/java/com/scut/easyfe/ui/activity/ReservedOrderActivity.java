package com.scut.easyfe.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.entity.Order;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 已预定订单详情
 */
public class ReservedOrderActivity extends BaseActivity {
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
    private TextView mTeacherAvatarImageView;
    private View mParentInfoContainer;
    private View mStudentInfoContainer;
    private View mTeacherInfoContainer;
    private View mOperationButtonsContainer;

    private Order mOrder;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_reserved_order);
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

        mParentInfoContainer = OtherUtils.findViewById(this, R.id.reserved_order_layout_parent_info);
        mParentNameTextView = OtherUtils.findViewById(this, R.id.order_base_parent_info_tv_name);
        mParentGenderTextView = OtherUtils.findViewById(this, R.id.order_base_parent_info_tv_gender);

        mStudentInfoContainer = OtherUtils.findViewById(this, R.id.reserved_order_layout_student_info);
        mStudentAgeTextView = OtherUtils.findViewById(this, R.id.order_base_student_info_tv_age);
        mStudentGenderTextView = OtherUtils.findViewById(this, R.id.order_base_student_info_tv_gender);

        mOperationButtonsContainer = OtherUtils.findViewById(this, R.id.reserved_order_ll_operation);
        mTeacherInfoContainer = OtherUtils.findViewById(this, R.id.reserved_order_layout_teacher_info);

        mTeacherNameTextView = OtherUtils.findViewById(this, R.id.item_search_result_tv_teacher);
        ((View)OtherUtils.findViewById(this, R.id.item_search_result_tv_price)).setVisibility(View.GONE);
        mTeacherInfoTextView = OtherUtils.findViewById(this, R.id.item_search_result_tv_content);
        mTeacherAvatarImageView = OtherUtils.findViewById(this, R.id.item_search_result_civ_avatar);
    }
}
