package com.scut.easyfe.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.OnDismissListener;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.app.Variables;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.order.RGetMatchTicket;
import com.scut.easyfe.network.request.user.parent.RConfirmMultiOrder;
import com.scut.easyfe.network.request.user.parent.RConfirmSingleOrder;
import com.scut.easyfe.network.request.user.parent.RConfirmSpecialOrder;
import com.scut.easyfe.network.request.user.parent.RGetTeacherInfo;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.TimeUtils;

import org.json.JSONObject;

import java.util.Locale;

/**
 * 确认订单页面(包括特价推广订单,单次预约订单,多次预约订单)
 * @author jay
 */
public class ConfirmOrderActivity extends BaseActivity {
    private int mConfirmOrderType = Constants.Identifier.CONFIRM_ORDER_SPECIAL;
    private TextView mTeacherTextView;
    private TextView mTeachGradeTextView;
    private TextView mTeachCourseTextView;
    private TextView mTeachDateTextView;
    private TextView mTeachDateLabelTextView;
    private TextView mTeachTimeTextView;
    private TextView mTeachTimeLabelTextView;
    private TextView mTeachWeekTextView;
    private TextView mTeachPriceTextView;
    private TextView mTeachTipTextView;
    private TextView mTeachTipLabelTextView;
    private TextView mTeachTotalPriceTextView;
    private TextView mTeachTotalPriceLabelTextView;
    private LinearLayout mWeekLinearLayout;

    private TextView mProfessionGuidePriceTextView;
    private TextView mTicketTextView;
    private LinearLayout mTicketLinearLayout;
    private LinearLayout mTutorLinearLayout;
    private CheckBox mProfessionGuideCheckBox;

    private Order mOrder;
    private int mTeachWeek = 0; //多次预约时预约多少次
    private int mTicketMoney = 0;

    private boolean mIsLoadingCloseByUser = true;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_confirm_order);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(null != intent){
            Bundle extra = intent.getExtras();
            if(null != extra){
                mConfirmOrderType = extra.getInt(Constants.Key.CONFIRM_ORDER_TYPE);
                mOrder = (Order) extra.getSerializable(Constants.Key.ORDER);

                if (null == mOrder) {
                    mOrder = new Order();
                }

                if(mOrder.isProfessionTutorShow()) {
                    mOrder.setTutorPrice(Variables.TUTOR_PRICE);
                }

                if(mConfirmOrderType == Constants.Identifier.CONFIRM_ORDER_MULTI_RESERVE){
                    mTeachWeek = extra.getInt(Constants.Key.TEACH_WEEK);
                }
            }
        }
    }

    @Override
    protected void initView() {
        mWeekLinearLayout = OtherUtils.findViewById(this, R.id.confirm_order_ll_week);
        mTeacherTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_teacher);
        mTeachGradeTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_grade);
        mTeachCourseTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_course);
        mTeachDateTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_date);
        mTeachDateLabelTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_date_label);
        mTeachWeekTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_week);
        mTeachTimeTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_time);
        mTeachTimeLabelTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_time_label);
        mTeachCourseTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_course);
        mTeachPriceTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_price);
        mTeachTipTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_tip);
        mTeachTipLabelTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_tip_label);
        mTeachTotalPriceTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_total_price);
        mTeachTotalPriceLabelTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_total_price_label);

        mProfessionGuidePriceTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_profession_guide_price);
        mProfessionGuideCheckBox = OtherUtils.findViewById(this, R.id.confirm_order_cb_profession_guide);
        mTicketLinearLayout = OtherUtils.findViewById(this, R.id.confirm_order_ll_ticket);
        mTutorLinearLayout = OtherUtils.findViewById(this, R.id.confirm_order_ll_tutor);
        mTicketTextView = OtherUtils.findViewById(this, R.id.confirm_order_tv_ticket);

        mTeacherTextView.setText(mOrder.getTeacher().getName());
        mTeachGradeTextView.setText(String.format("%s", mOrder.getGrade()));
        mTeachCourseTextView.setText(mOrder.getCourse());
        mTeachTimeTextView.setText(TimeUtils.getTimeFromMinute(mOrder.getTime()));

        mTeachPriceTextView.setText(String.format(Locale.CHINA, "%.2f 元", mOrder.getPerPrice()));

        mTeachTipTextView.setText(String.format(Locale.CHINA, "%.2f 元", mOrder.getSubsidy()));
        mTeachTotalPriceTextView.setText(String.format(Locale.CHINA, "%.2f 元", mOrder.getTotalPrice()));
        mProfessionGuidePriceTextView.setText(String.format(Locale.CHINA, "%d 元/小时", Variables.TUTOR_PRICE));

        if(!mOrder.isProfessionTutorShow()){
            mTutorLinearLayout.setVisibility(View.GONE);
            mTicketLinearLayout.setVisibility(View.GONE);
        }

        initOtherViews();
    }

    @Override
    protected void initListener() {
        if(mOrder.isProfessionTutorShow()) {
            mProfessionGuideCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mTicketLinearLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                    mOrder.setTicketMoney(isChecked ? mTicketMoney : 0);
                    mOrder.setTutorPrice(isChecked ? Variables.TUTOR_PRICE : 0);
                    mTeachTotalPriceTextView.setText(String.format(Locale.CHINA, "%.2f 元", mOrder.getTotalPrice()));
                }
            });
        }
    }

    @Override
    protected void fetchData() {
        if(mOrder.isProfessionTutorShow()) {
            startLoading("加载数据中", new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (mIsLoadingCloseByUser) {
                        finish();
                    }
                }
            });

            RequestManager.get().execute(new RGetMatchTicket(mOrder.getTeachTime().getDate(),
                            mOrder.getTime(), mOrder.getGrade()),
                    new RequestListener<JSONObject>() {
                        @Override
                        public void onSuccess(RequestBase request, JSONObject result) {
                            int money = result.optInt("money", -1);
                            if (-1 == money) {
                                mTicketMoney = 0;
                                toast(result.optString("message"));
                            } else {
                                mTicketMoney = money;
                            }

                            mOrder.setTicketMoney(mTicketMoney);
                            mTicketTextView.setText(String.format(Locale.CHINA, "减 %d 元", mTicketMoney));
                            mTeachTotalPriceTextView.setText(String.format(Locale.CHINA, "%.2f 元", mOrder.getTotalPrice()));


                            mIsLoadingCloseByUser = false;
                            stopLoading();
                        }

                        @Override
                        public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                            toast("获取优惠券数据失败");
                            mIsLoadingCloseByUser = false;
                            mTicketMoney = 0;
                            mOrder.setTicketMoney(mTicketMoney);
                            mTicketTextView.setText(String.format(Locale.CHINA, "减 %d 元", mTicketMoney));
                            mTeachTotalPriceTextView.setText(String.format(Locale.CHINA, "%.2f 元", mOrder.getTotalPrice()));

                            stopLoading();
                        }
                    });
        }
    }

    private void initOtherViews(){

        String title = "";
        switch (mConfirmOrderType){
            case Constants.Identifier.CONFIRM_ORDER_SPECIAL:
                mWeekLinearLayout.setVisibility(View.GONE);
                mTeacherTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.mipmap.icon_right_arrow_padding, 0);
                title = "特价订单详情";
                mTeachDateTextView.setText(String.format("%s %s",
                        TimeUtils.getTime(TimeUtils.getDateFromString(mOrder.getTeachTime().getDate()), "yyyy年MM月dd日(EEEE)"),
                        mOrder.getTeachTime().getChineseTime()));
                if (mOrder.getSubsidy() != 0) {
                    DialogUtils.makeConfirmDialog(mContext, null, getString(R.string.add_tip_info));
                }
                break;

            case Constants.Identifier.CONFIRM_ORDER_MULTI_RESERVE:
                mTeachWeekTextView.setText(mTeachWeek + "");
                mTeachTipLabelTextView.setText("每次交通补贴");
                mTeachDateLabelTextView.setText("每周授课时间");
                mTeachTimeLabelTextView.setText("每次授课时长");
                mTeachTotalPriceLabelTextView.setText("每次价格");
                title = "多次预约订单信息";
                mTeachDateTextView.setText(String.format("%s %s",
                        mOrder.getTeachTime().getDate(),
                        mOrder.getTeachTime().getChineseTime()));
                break;

            case Constants.Identifier.CONFIRM_ORDER_SINGLE_RESERVE:
                mWeekLinearLayout.setVisibility(View.GONE);
                mTeachDateTextView.setText(String.format("%s %s",
                        TimeUtils.getTime(TimeUtils.getDateFromString(mOrder.getTeachTime().getDate()), "yyyy年MM月dd日(EEEE)"),
                        mOrder.getTeachTime().getChineseTime()));
                title = "单次预约订单信息";
                break;

            default:
                break;
        }

        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText(title);
    }

    public void onTeacherNameClick(View view){
        if(mConfirmOrderType == Constants.Identifier.CONFIRM_ORDER_SPECIAL){
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
    }

    public void onConfirmClick(View view){
        switch (mConfirmOrderType){
            case Constants.Identifier.CONFIRM_ORDER_SPECIAL:
                RequestManager.get().execute(new RConfirmSpecialOrder(App.getUser().getToken(),
                        mOrder.getTrafficTime(), mOrder.get_id(), (int)mOrder.getTutorPrice()),
                        new RequestListener<JSONObject>() {
                    @Override
                    public void onSuccess(RequestBase request, JSONObject result) {
                        showDialog(mOrder.getTeacher().getName());
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        toast(errorMsg);
                    }
                });
                break;

            case Constants.Identifier.CONFIRM_ORDER_SINGLE_RESERVE:
                RequestManager.get().execute(new RConfirmSingleOrder(App.getUser().getToken(), mOrder), new RequestListener<JSONObject>() {
                    @Override
                    public void onSuccess(RequestBase request, JSONObject result) {
                        showDialog(mOrder.getTeacher().getName());
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        toast(errorMsg);
                    }
                });
                break;

            case Constants.Identifier.CONFIRM_ORDER_MULTI_RESERVE:
                RequestManager.get().execute(new RConfirmMultiOrder(App.getUser().getToken(),
                        mOrder, mTeachWeek),
                        new RequestListener<JSONObject>() {
                    @Override
                    public void onSuccess(RequestBase request, JSONObject result) {
                        showDialog(mOrder.getTeacher().getName());
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        toast(errorMsg);
                    }
                });
                break;

            default:
                break;
        }
    }

    private void showDialog(String teacherName){
        DialogUtils.makeConfirmDialog(mContext, "生成订单成功!",
                String.format("%s确认后将于第一时间与您联系，在老师联系您前可以修改或取消订单。", teacherName)).
                setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(Object o) {
                        redirectToActivity(mContext, MyOrderActivity.class);
                    }
                });
    }

    public void onProfessionGuideClick(View view){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_TITLE, "专业辅导");
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_CONTENT, mResources.getString(R.string.user_protocol_content));
        redirectToActivity(mContext, ShowTextActivity.class, bundle);
    }

    public void onBackClick(View view){
        finish();
    }

}
