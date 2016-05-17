package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.order.RPayOrder;
import com.scut.easyfe.network.request.user.parent.RGetTeacherInfo;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.TimeUtils;

import org.json.JSONObject;

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
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mOrder = (Order) extras.getSerializable(Constants.Key.ORDER);
            } else {
                mOrder = new Order();
            }
        } else {
            mOrder = new Order();
        }
    }

    @Override
    protected void initView() {

        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("待执行订单");

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

    private void updateView() {
        mOrderNumTextView.setText(mOrder.getOrderNumber());
        mInsuranceNumTextView.setText(mOrder.getInsurance().getInsuranceNumber());

        mGradeTextView.setText(String.format("%s", mOrder.getGrade()));
        mCourseTextView.setText(mOrder.getCourse());
        mDateTextView.setText(String.format("%s %s",
                TimeUtils.getTime(TimeUtils.getDateFromString(mOrder.getTeachTime().getDate()), "yyyy年MM月dd日(EEEE)"),
                mOrder.getTeachTime().getChineseTime()));
        mTimeTextView.setText(TimeUtils.getTimeFromMinute(mOrder.getTime()));
        mPriceTextView.setText(String.format("%.2f", mOrder.getPrice()));
        mTipTextView.setText(String.format("%.2f", mOrder.getSubsidy()));
        mTotalPriceTextView.setText(String.format("%.2f", mOrder.getTotalPrice()));
        mWarningTextView.setText("我就是温馨提示喽");

        if (isTeacher()) {
            mNameTextView.setText(mOrder.getParent().getName());
            mPhoneTextView.setText(mOrder.getParent().getPhone());
            mParentAddressTextView.setText(mOrder.getParent().getPosition().getAddress());

            mStudentAgeTextView.setText(String.format("%d", mOrder.getChildAge()));
            mStudentGenderTextView.setText(mOrder.getChildGender() == Constants.Identifier.MALE ? "男" : "女");
        } else {
            mNameLabelTextView.setText("家教姓名");
            mNameTextView.setText(mOrder.getTeacher().getName());
            mNameTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.icon_right_arrow_padding, 0);
            mPhoneTextView.setText(mOrder.getTeacher().getPhone());
            mParentAddressContainer.setVisibility(View.GONE);
            mStudentInfoContainer.setVisibility(View.GONE);
            mDoingTextView.setVisibility(View.GONE);
            mPayTextView.setVisibility(View.VISIBLE);
        }
    }

    public void onPayClick(View view) {
        if (OtherUtils.isFastDoubleClick()) {
            return;
        }

        RequestManager.get().execute(new RPayOrder(mOrder.get_id()), new RequestListener<JSONObject>() {
            @Override
            public void onSuccess(RequestBase request, JSONObject result) {
                toast(result.optString("message"));
                mOrder.setState(Constants.Identifier.ORDER_COMPLETED);

                Bundle bundle = new Bundle();
                bundle.putInt(Constants.Key.ORDER_TYPE, Constants.Identifier.ORDER_COMPLETED);
                bundle.putSerializable(Constants.Key.ORDER, mOrder);
                redirectToActivity(mContext, EvaluationActivity.class, bundle);
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
            }
        });
    }

    public void onInsuranceClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_TITLE, "关于保险");
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_CONTENT, mResources.getString(R.string.about_us_content));
        redirectToActivity(mContext, ShowTextActivity.class, bundle);
    }

    public void onTeacherNameClick(View view) {
        if (App.getUser().isTeacher()) {
            return;
        }

        RequestManager.get().execute(new RGetTeacherInfo(App.getUser().getToken(), mOrder.getTeacher().get_id()), new RequestListener<Order>() {
            @Override
            public void onSuccess(RequestBase request, Order result) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.Key.TO_TEACHER_INFO_ACTIVITY_TYPE, Constants.Identifier.TYPE_SEE_TEACHER_INFO);
                bundle.putSerializable(Constants.Key.ORDER, result);
                redirectToActivity(mContext, TeacherInfoActivity.class, bundle);
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
            }
        });
    }

    public void onBackClick(View view) {
        redirectToActivity(mContext, MyOrderActivity.class);
        finish();
    }

    @Override
    public void onBackPressed() {
        onBackClick(null);
    }

    private boolean isTeacher() {
        return App.getUser().get_id().equals(mOrder.getTeacher().get_id());
    }
}
