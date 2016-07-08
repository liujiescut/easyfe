package com.scut.easyfe.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.app.Variables;
import com.scut.easyfe.entity.PollingData;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.event.DataChangeEvent;
import com.scut.easyfe.event.PDHandler;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.order.RGetOrderDetail;
import com.scut.easyfe.network.request.user.parent.RGetTeacherInfo;
import com.scut.easyfe.ui.activity.ShowTextActivity;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.PayUtil;
import com.scut.easyfe.utils.TimeUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.Locale;

public class ToDoOrderActivity extends BaseActivity {

    public static final int REQUEST_TUTOR_DETAIL = 1;

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
    private TextView mTeacherActionTextView;
    private TextView mParentActionTextView;
    private TextView mTutorPriceTextView;
    private TextView mCouponTextView;
    private TextView mThisTutorIncompleteInfoTextView;          //本次专业辅导情况没有填写的提示文本(未预定该服务或未填写)
    private TextView mThisTutorCompleteTextView;                //本次专业辅导情况填写之后显示的TextView,点击可以查看专业辅导情况
    private LinearLayout mThisTutorIncompleteLinearLayout;      //本次专业辅导情况没有填写的LinearLayout(当已填写时不可见)
    private View mTutorContainer;
    private View mCouponContainer;
    private View mStudentInfoContainer;
    private View mParentAddressContainer;

    private Order mOrder;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        App.get().getEventBus().register(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.get().getEventBus().unregister(mContext);
    }

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
        mTutorPriceTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_tutor_price);
        mCouponTextView = OtherUtils.findViewById(this, R.id.order_base_info_tv_coupon);
        mTutorContainer = OtherUtils.findViewById(this, R.id.order_base_info_ll_tutor);
        mCouponContainer = OtherUtils.findViewById(this, R.id.order_base_info_ll_coupon);
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
        mTeacherActionTextView = OtherUtils.findViewById(this, R.id.to_do_order_tv_teacher_action);
        mParentActionTextView = OtherUtils.findViewById(this, R.id.to_do_order_tv_parent_action);

        mThisTutorIncompleteInfoTextView = OtherUtils.findViewById(this, R.id.to_do_order_tv_tutor_incomplete_info);
        mThisTutorCompleteTextView = OtherUtils.findViewById(this, R.id.to_do_order_tv_tutor_complete);
        mThisTutorIncompleteLinearLayout = OtherUtils.findViewById(this, R.id.to_do_order_ll_tutor_incomplete);

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
        mPriceTextView.setText(String.format(Locale.CHINA, "%.2f 元/小时", mOrder.getPrice() / 100));
        mTipTextView.setText(String.format(Locale.CHINA, "%.2f 元", mOrder.getSubsidy() / 100));
        mTotalPriceTextView.setText(String.format(Locale.CHINA, "%.2f 元", mOrder.getTotalPrice() / 100));

        if (isTeacher()) {
            mNameTextView.setText(mOrder.getParent().getName());
            mPhoneTextView.setText(mOrder.getParent().getPhone());
            mParentAddressTextView.setText(mOrder.getParent().getPosition().getAddress());

            mStudentAgeTextView.setText(String.format(Locale.CHINA, "%d 岁", mOrder.getChildAge()));
            mStudentGenderTextView.setText(mOrder.getChildGender() == Constants.Identifier.MALE ? "男" : "女");
            mTeacherActionTextView.setText(mOrder.isIsTeacherReport() ? "待家长完成课程并付款" : "完成课程并反馈");
        } else {
            mNameLabelTextView.setText("家教姓名");
            mNameTextView.setText(mOrder.getTeacher().getName());
            mNameTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.icon_right_arrow_padding, 0);
            mPhoneTextView.setText(mOrder.getTeacher().getPhone());
            mParentAddressContainer.setVisibility(View.GONE);
            mStudentInfoContainer.setVisibility(View.GONE);
            mTeacherActionTextView.setVisibility(View.GONE);
            mParentActionTextView.setVisibility(View.VISIBLE);
            mParentActionTextView.setText(mOrder.isIsTeacherReport() ? "完成课程并付款" : "待家教完成课程并反馈");
        }

        if (!mOrder.hasProfessionTutor()) {
            mCouponContainer.setVisibility(View.GONE);
            mTutorPriceTextView.setText("未预定此服务");
            mThisTutorIncompleteInfoTextView.setText("未预定此服务");
        } else {
            mCouponTextView.setText(String.format(Locale.CHINA, "减 %.2f 元", mOrder.getCoupon().getMoney() / 100));
            mTutorPriceTextView.setText(String.format(Locale.CHINA, "%.2f 元/小时", mOrder.getProfessionalTutorPrice() / 100));
            if (mOrder.getThisTeachDetail().hadFillIn()) {
                mThisTutorIncompleteLinearLayout.setVisibility(View.GONE);
                mThisTutorCompleteTextView.setVisibility(View.VISIBLE);
                mThisTutorCompleteTextView.setText("专业辅导情况");
            } else {
                if (isTeacher()) {
                    mThisTutorIncompleteLinearLayout.setVisibility(View.GONE);
                    mThisTutorCompleteTextView.setVisibility(View.VISIBLE);
                    mThisTutorCompleteTextView.setText("填写首次专业辅导情况");
                } else {
                    mThisTutorIncompleteInfoTextView.setText("未填写");
                }
            }
        }
    }

    public void onCompleteTutorClick(View view) {
        if (OtherUtils.isFastDoubleClick()) {
            return;
        }

        Bundle bundle = new Bundle();
        mOrder.getThisTeachDetail().setGrade(mOrder.getGrade());
        bundle.putSerializable(Constants.Key.TUTOR_DETAIL, mOrder.getThisTeachDetail());

        if (isTeacher() && !mOrder.getThisTeachDetail().hadFillIn()) {
            bundle.putString(Constants.Key.ORDER_ID, mOrder.get_id());
            bundle.putBoolean(Constants.Key.TUTOR_UPDATE_TO_THIS_TEACH_DETAIL, true);
            Intent intent = new Intent(mContext, EditTutorActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_TUTOR_DETAIL);

        } else {                     //填写了专业辅导之后只能查看
            Intent intent = new Intent(mContext, ShowTutorActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }

    /**
     * 家教底部按钮点击
     */
    public void onTeacherActionClick(View view) {
        if (mOrder.isIsTeacherReport()) {           //家教已经完成课程并反馈
            DialogUtils.makeConfirmDialog(mContext, "温馨提示", "您已完成课时并反馈,\n等待家长完成课程并付款中").show();

        } else {                                  //家教未完成课程并反馈
            if (mOrder.hasProfessionTutor() && !mOrder.getThisTeachDetail().hadFillIn()) {      //家教未完成本次专业辅导情况
                toast("请先填写首次专业辅导情况");

            } else {                                              //家教已完成本次专业辅导情况
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.Key.ORDER, mOrder);
                bundle.putInt(Constants.Key.TO_TEACHER_REPORT_ACTIVITY_TYPE, Constants.Identifier.TYPE_REPORT);
                redirectToActivity(mContext, TeacherReportActivity.class, bundle);
            }
        }
    }

    /**
     * 家长底部按钮点击
     */
    public void onParentActionClick(View view) {
        if (OtherUtils.isFastDoubleClick()) {
            return;
        }

        if (mOrder.isIsTeacherReport()) {
            new PayUtil(this, Constants.Identifier.BUY_ORDER, mOrder.get_id(), mOrder.getPayTitle(), mOrder.getPayInfo(), (int)mOrder.getTotalPrice(), new PayUtil.PayListener() {
                @Override
                public void onAlipayReturn(boolean success) {
                    //支付宝支付回调该接口
                    if(success){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.Key.ORDER, mOrder);
                        bundle.putInt(Constants.Key.TO_TEACHER_REPORT_ACTIVITY_TYPE, Constants.Identifier.TYPE_CONFIRM);
                        ToDoOrderActivity.this.redirectToActivity(mContext, TeacherReportActivity.class, bundle);
                    }
                }

                @Override
                public void onWechatPaySend(boolean success) {

                }

                @Override
                public void onCashPayReturn(boolean success){
                    if(success){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.Key.ORDER, mOrder);
                        bundle.putInt(Constants.Key.TO_TEACHER_REPORT_ACTIVITY_TYPE, Constants.Identifier.TYPE_CONFIRM);
                        ToDoOrderActivity.this.redirectToActivity(mContext, TeacherReportActivity.class, bundle);
                    }
                }
            }).showPayDialog();

        } else {
            DialogUtils.makeConfirmDialog(this, "温馨提示", "待老师完成课时并反馈之后才能付款哦~~").show();
        }
    }

    public void onInsuranceClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_TITLE, "关于保险");
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_CONTENT, mResources.getString(R.string.about_insurance));
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

    public void onProfessionGuideClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_TITLE, "专业辅导");
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_CONTENT, mResources.getString(R.string.tutor_order_explain_content));
        redirectToActivity(mContext, ShowTextActivity.class, bundle);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TUTOR_DETAIL:
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (null != bundle) {
                        Order.TutorDetail tutorDetail = (Order.TutorDetail) bundle.getSerializable(Constants.Key.TUTOR_DETAIL);
                        if (null != tutorDetail) {
                            mOrder.setThisTeachDetail(tutorDetail);
                            updateView();
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    @Subscribe
    public void onEvent(DataChangeEvent event) {
        if(Variables.localData.getMine().isOrderNew(event.getData().getMine())){
            PollingData.PollingPrivateData.NewOrderInfo orderInfo = Variables.localData.getMine().getNewOrderInfo(event.getData().getMine());
            if(null != mOrder && orderInfo.ids.contains(mOrder.get_id())){

                startLoading("刷新数据中");
                RequestManager.get().execute(new RGetOrderDetail(mOrder.get_id()), new RequestListener<Order>() {
                    @Override
                    public void onSuccess(RequestBase request, Order result) {
                        stopLoading();
                        mOrder = result;
                        if(mOrder.getState() == Constants.Identifier.ORDER_COMPLETED){
                            Bundle bundle = new Bundle();
                            bundle.putInt(Constants.Key.ORDER_TYPE, Constants.Identifier.ORDER_COMPLETED);
                            bundle.putSerializable(Constants.Key.ORDER, mOrder);
                            redirectToActivity(ToDoOrderActivity.this, ReservedOrCompletedOrderActivity.class, bundle);

                        }else {
                            updateView();
                        }
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        toast(errorMsg);
                        stopLoading();
                    }
                });

                for (int latestIndex = 0; latestIndex < PDHandler.get().getLatestData().getMine().getOrder().size(); latestIndex++) {
                    if (PDHandler.get().getLatestData().getMine().getOrder().get(latestIndex).getOrderId().equals(mOrder.get_id())) {
                        boolean orderInLocal = false;
                        for (int localIndex = 0; localIndex < Variables.localData.getMine().getOrder().size(); localIndex++) {
                            if (Variables.localData.getMine().getOrder().get(localIndex).getOrderId().equals(mOrder.get_id())) {

                                Variables.localData.getMine().getOrder().get(localIndex).setTimestamp(PDHandler.get().getLatestData().getMine().getOrder().get(latestIndex).getTimestamp());
                                Variables.localData.getMine().getOrder().get(localIndex).setState(PDHandler.get().getLatestData().getMine().getOrder().get(latestIndex).getState());
                                Variables.localData.equals(PDHandler.get().getLatestData(), true);
                                Variables.localData.save2Cache(App.getUser().getPhone());
                                App.get().getEventBus().post(new DataChangeEvent(PDHandler.get().getLatestData()));
                                orderInLocal = true;
                                break;
                            }
                        }

                        if(!orderInLocal){
                            Variables.localData.getMine().getOrder().add(PDHandler.get().getLatestData().getMine().getOrder().get(latestIndex));
                            Variables.localData.equals(PDHandler.get().getLatestData(), true);
                            Variables.localData.save2Cache(App.getUser().getPhone());
                            App.get().getEventBus().post(new DataChangeEvent(PDHandler.get().getLatestData()));
                        }
                    }
                }
            }
        }
    }
}
