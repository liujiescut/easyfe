package com.scut.easyfe.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.user.teacher.RTeacherCancelOrder;
import com.scut.easyfe.network.request.user.teacher.RTeacherConfirmOrder;
import com.scut.easyfe.ui.activity.ShowTextActivity;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.ImageUtils;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.TimeUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private TextView mTutorPriceTextView;
    private TextView mCouponTextView;
    private ImageView mTeacherAvatarImageView;
    private View mParentInfoContainer;
    private View mStudentInfoContainer;
    private View mTeacherInfoContainer;
    private View mOperationButtonsContainer;
    private View mNumContainer;
    private View mCouponContainer;



    private Order mOrder;
    private int mOrderType = Constants.Identifier.ORDER_RESERVATION;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_r_or_c_order);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mOrder = (Order) extras.getSerializable(Constants.Key.ORDER);
                mOrderType = extras.getInt(Constants.Key.ORDER_TYPE, Constants.Identifier.ORDER_RESERVATION);
            } else {
                mOrder = new Order();
            }
        } else {
            mOrder = new Order();
        }
    }

    @Override
    protected void initView() {

        mGradeTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_grade);
        mCourseTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_course);
        mDateTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_date);
        mTimeTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_time);
        mPriceTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_price);
        mTipTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_tip);
        mTutorPriceTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_tutor_price);
        mCouponTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_coupon);
        mTotalPriceTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_total_price);

        mNumContainer = OtherUtils.findViewById(this, R.id.reserved_order_layout_num_info);
        mCouponContainer = OtherUtils.findViewById(this, R.id.order_base_info_ll_coupon);
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
        ((View) OtherUtils.findViewById(this, R.id.item_search_result_tv_price)).setVisibility(View.GONE);
        mTeacherInfoTextView = OtherUtils.findViewById(this, R.id.item_search_result_tv_content);
        mTeacherAvatarImageView = OtherUtils.findViewById(this, R.id.item_search_result_civ_avatar);

        mWaitTeacherApplyTextView = OtherUtils.findViewById(this, R.id.r_or_c_order_tv_waiting);

        updateView();
    }


    public void onCancelOrderClick(View view) {
        //Todo 判断有没有修改,然后取消做相应判断
        if (App.getUser().getBadRecord() >= Constants.DefaultValue.MAX_BAD_RECORD) {
            DialogUtils.makeConfirmDialog(mContext, "温馨提示", "您已经取消过订单" + Constants.DefaultValue.MAX_BAD_RECORD + "次,\n不能再取消订单了呦\n(完成6次订单可增加一次取消机会)");
            return;
        }

        List<String> orderIds = new ArrayList<>();
        orderIds.add(mOrder.get_id());
        RequestManager.get().execute(new RTeacherCancelOrder(App.getUser().getToken(), orderIds),
                new RequestListener<Integer>() {
                    @Override
                    public void onSuccess(RequestBase request, Integer badRecord) {
                        toast("取消成功");
                        App.getUser().setBadRecord(badRecord);
                        App.getUser().save2Cache();

                        redirectToActivity(mContext, MyOrderActivity.class);
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        toast(errorMsg);
                    }
                });
    }

    public void onConfirmOrderClick(View view) {
        List<String> orders = new ArrayList<>();
        orders.add(mOrder.get_id());
        RequestManager.get().execute(new RTeacherConfirmOrder(App.getUser().getToken(), orders),
                new RequestListener<JSONObject>() {
                    @Override
                    public void onSuccess(RequestBase request, JSONObject result) {
                        toast(result.optString("message"));

                        mOrder.setState(Constants.Identifier.ORDER_TO_DO);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.Key.ORDER, mOrder);
                        redirectToActivity(mContext, ToDoOrderActivity.class, bundle);
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        toast(errorMsg);
                    }
                });
    }

    private void updateView() {
        mGradeTextView.setText(String.format("%s", mOrder.getGrade()));
        mCourseTextView.setText(mOrder.getCourse());
        mDateTextView.setText(String.format("%s %s",
                TimeUtils.getTime(TimeUtils.getDateFromString(mOrder.getTeachTime().getDate()), "yyyy年MM月dd日(EEEE)"),
                mOrder.getTeachTime().getChineseTime()));
        mTimeTextView.setText(TimeUtils.getTimeFromMinute(mOrder.getTime()));
        mPriceTextView.setText(String.format(Locale.CHINA, "%.2f 元/小时", mOrder.getPrice() / 100));
        mTipTextView.setText(String.format(Locale.CHINA, "%.2f 元", mOrder.getSubsidy() / 100));
        mTotalPriceTextView.setText(String.format(Locale.CHINA, "%.2f 元", mOrder.getTotalPrice() / 100));

        if (mOrderType == Constants.Identifier.ORDER_COMPLETED) {
            mNumContainer.setVisibility(View.VISIBLE);
            mOrderNumTextView.setText(mOrder.getOrderNumber());
            mInsuranceNumTextView.setText(mOrder.getInsurance().getInsuranceNumber());
            ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("已完成订单");
        } else {
            ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("已预订订单");
        }

        if (isTeacher()) {
            mParentNameTextView.setText(mOrder.getParent().getName());
            mParentGenderTextView.setText(mOrder.getParent().getGender() == Constants.Identifier.MALE ? "男" : "女");
            mStudentGenderTextView.setText(mOrder.getParent().getParentMessage().getChildGender() == Constants.Identifier.MALE ? "男" : "女");
            mStudentAgeTextView.setText(String.format(Locale.CHINA, "%d岁", mOrder.getChildAge()));

            if (mOrderType == Constants.Identifier.ORDER_COMPLETED) {
                mOperationButtonsContainer.setVisibility(View.GONE);
            }
        } else {
            mParentInfoContainer.setVisibility(View.GONE);
            mStudentInfoContainer.setVisibility(View.GONE);
            mOperationButtonsContainer.setVisibility(View.GONE);
            mTeacherInfoContainer.setVisibility(View.VISIBLE);
            mWaitTeacherApplyTextView.setVisibility(View.VISIBLE);

            mTeacherNameTextView.setText(mOrder.getTeacher().getName());
            mTeacherInfoTextView.setText(Order.getBaseInfo(mOrder));
            ImageUtils.displayImage(mOrder.getTeacher().getAvatar(), mTeacherAvatarImageView);

            if (mOrderType == Constants.Identifier.ORDER_COMPLETED) {
                mWaitTeacherApplyTextView.setVisibility(View.GONE);
            }
        }

        if(!mOrder.hasProfessionTutor()){
            mTutorPriceTextView.setText("未预定此服务");
        }else{
            mTutorPriceTextView.setText(String.format(Locale.CHINA, "%.2f 元/小时", mOrder.getProfessionalTutorPrice() / 100));
        }
        mCouponTextView.setText(String.format(Locale.CHINA, "减 %.2f 元", mOrder.getCoupon().getMoney() / 100));

    }

    public void onInsuranceClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_TITLE, "关于保险");
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_CONTENT, mResources.getString(R.string.about_insurance));
        redirectToActivity(mContext, ShowTextActivity.class, bundle);
    }

    public void onBackClick(View view) {
        redirectToActivity(mContext, MyOrderActivity.class);
        finish();
    }

    public void onProfessionGuideClick(View view){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_TITLE, "专业辅导");
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_CONTENT, mResources.getString(R.string.tutor_order_explain_content));
        redirectToActivity(mContext, ShowTextActivity.class, bundle);
    }

    @Override
    public void onBackPressed() {
        onBackClick(null);
    }

    private boolean isTeacher() {
        return mOrder.getTeacher().get_id().equals(App.getUser().get_id());
    }
}
