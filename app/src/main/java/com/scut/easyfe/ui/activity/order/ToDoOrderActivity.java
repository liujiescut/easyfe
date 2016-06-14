package com.scut.easyfe.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.PayResult;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.order.RPayOrder;
import com.scut.easyfe.network.request.pay.RGetAlipaySign;
import com.scut.easyfe.network.request.user.parent.RGetTeacherInfo;
import com.scut.easyfe.ui.activity.ShowTextActivity;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.SignUtils;
import com.scut.easyfe.utils.TimeUtils;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

public class ToDoOrderActivity extends BaseActivity {

    public static final int REQUEST_TUTOR_DETAIL = 1;
    public static final int SDK_PAY_FLAG = 2;

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

    //支付宝支付Handler
    private Handler mAlipayHandler = new MyHandler();

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
        mWarningTextView = OtherUtils.findViewById(this, R.id.edit_tutor_tv_warning);
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
        mPriceTextView.setText(String.format(Locale.CHINA, "%.2f 元/小时", mOrder.getPrice()));
        mTipTextView.setText(String.format(Locale.CHINA, "%.2f 元", mOrder.getSubsidy()));
        mTotalPriceTextView.setText(String.format(Locale.CHINA, "%.2f 元", mOrder.getTotalPrice()));
        mWarningTextView.setText("我就是温馨提示喽");

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
            mCouponTextView.setText(String.format(Locale.CHINA, "减 %.0f 元", mOrder.getCoupon().getMoney()));
            mTutorPriceTextView.setText(String.format(Locale.CHINA, "%.0f 元/小时", mOrder.getProfessionalTutorPrice()));
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

        if (isTeacher() && !mOrder.isIsTeacherReport()) {          //家教可以进行修改
            bundle.putString(Constants.Key.ORDER_ID, mOrder.get_id());
            bundle.putBoolean(Constants.Key.TUTOR_UPDATE_TO_THIS_TEACH_DETAIL, true);
            Intent intent = new Intent(mContext, EditTutorActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_TUTOR_DETAIL);

        } else {                     //家长只能查看
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

            RequestManager.get().execute(new RPayOrder(mOrder.get_id()), new RequestListener<JSONObject>() {
                @Override
                public void onSuccess(RequestBase request, JSONObject result) {
                    toast(result.optString("message"));
                    mOrder.setState(Constants.Identifier.ORDER_COMPLETED);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.Key.ORDER, mOrder);
                    bundle.putInt(Constants.Key.TO_TEACHER_REPORT_ACTIVITY_TYPE, Constants.Identifier.TYPE_CONFIRM);
                    redirectToActivity(mContext, TeacherReportActivity.class, bundle);
                }

                @Override
                public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                    toast(errorMsg);
                }
            });
        } else {
            DialogUtils.makeConfirmDialog(this, "温馨提示", "待老师完成课时并反馈之后才能付款哦~~").show();
        }
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

    public void onProfessionGuideClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_TITLE, "专业辅导");
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_CONTENT, mResources.getString(R.string.user_protocol_content));
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

    static class MyHandler extends Handler {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&docType=1) 建议商户依赖异步通知
                     * Todo verify from server
                     */
                    String resultInfo = payResult.getResult(); // 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(App.get().getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(App.get().getApplicationContext(), "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(App.get().getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }

    /**
     * 调用支付宝支付
     */
    public void doAlipay() {
        /*
         * 获取支付信息的签名
         */
        RequestManager.get().execute(new RGetAlipaySign(mOrder.get_id(), mOrder.getPayTitle(), mOrder.getPayInfo(), mOrder.getTotalPrice() + ""), new RequestListener<JSONObject>() {
            @Override
            public void onSuccess(RequestBase request, JSONObject result) {
                String sign = result.optString("sign_string");
                final String orderInfo = result.optString("sign_before");
                LogUtils.i("cxtag", "orderInfo --> " + orderInfo);
                LogUtils.i("cxtag", "sign  --> " + sign);
                if (sign.length() != 0) {
                    try {
                        /**
                         * 仅需对sign 做URL编码
                         */
                        sign = URLEncoder.encode(sign, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    final String finalSign = sign;

                    /**
                     * 完整的符合支付宝参数规范的订单信息
                     */
                    final String payInfo = orderInfo + "&sign=\"" + finalSign + "\"&" + "sign_type=\"RSA\"";

                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            // 构造PayTask 对象
                            PayTask alipay = new PayTask(ToDoOrderActivity.this);
                            // 调用支付接口，获取支付结果
                            String result = alipay.pay(payInfo, true);

                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mAlipayHandler.sendMessage(msg);
                        }
                    };

                    // 必须异步调用
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                } else {
                    toast("支付失败,请联系客服");
                }
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {

            }
        });

        LogUtils.i("cxtag", "orderInfo --> " + getOrderInfo(mOrder.getPayTitle(), mOrder.getPayInfo(), mOrder.getTotalPrice() + ""));
        LogUtils.i("cxtag", "sign1 --> " + SignUtils.sign(getOrderInfo(mOrder.getPayTitle(), mOrder.getPayInfo(), mOrder.getTotalPrice() + ""), Constants.Data.ALIPAY_RSA_PRIVATE));
    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + Constants.Data.ALIPAY_PID + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Constants.Data.ALIPAY_SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + mOrder.get_id() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://www.cadena.cn/money/notify/alipay" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    private void doWechatPay() {
        final IWXAPI msgAPI = WXAPIFactory.createWXAPI(ToDoOrderActivity.this, Constants.Data.WECHAT_APP_ID);


    }
}
