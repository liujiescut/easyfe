package com.scut.easyfe.utils;

import android.app.Activity;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.pay.RPrePay;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * 支付封装类
 * Created by jay on 16/6/15.
 */
public class PayUtil {
    private static WeakReference<Activity> mActivityReference;
    private PayListener listener;
    private String id = "";
    private String title = "";
    private int price = 0;
    private String info = "";
    private int buyType = -1;

    public PayUtil(Activity activity,int buyType, String id, String title, String info, int price, PayListener listener) {
        this.mActivityReference = new WeakReference<>(activity);
        this.buyType = buyType;
        this.id = id;
        this.title = title;
        this.price = price;
        this.info = info;
        this.listener = listener;

        //todo delete it
        this.price = 1;
    }

    public void showPayDialog(){
        if (null == mActivityReference.get()) {
            return;
        }

        AlertView mPayWayAlertView = new AlertView("付款方式", null, "取消", null,
                new String[]{"余额", "支付宝", "微信"},
                mActivityReference.get(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, final int position) {
                RPrePay request;
                String orderId = buyType == Constants.Identifier.BUY_ORDER ? id : "";
                String vipEventId = buyType == Constants.Identifier.BUY_VIP_EVENT ? id : "";
                if (position == 0) {
                    request = new RPrePay(orderId, vipEventId, price, buyType, Constants.Identifier.PAY_CASH);
                } else if (position == 1) {
                    request = new RPrePay(orderId, vipEventId, price, buyType, Constants.Identifier.PAY_ALIPAY);
                } else if (position == 2) {
                    request = new RPrePay(orderId, vipEventId, price, buyType, Constants.Identifier.PAY_WECHAT);
                }else {
                    request = new RPrePay(orderId, vipEventId, price, buyType, Constants.Identifier.PAY_ALIPAY);
                }

                RequestManager.get().execute(request, new RequestListener<JSONObject>() {
                    @Override
                    public void onSuccess(RequestBase request, JSONObject result) {
                        String payId = result.optString("prePayDataId");
                        if (position == 0) {
                            doBalancePay(payId);
                        } else if (position == 1) {
                            doAlipay(payId);
                        } else if (position == 2) {
                            doWechatPay(payId);
                        }
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        Toast.makeText(App.get().getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }).setButtonTextColor(R.color.theme_color);
        mPayWayAlertView.show();
    }

    private void doAlipay(String payId) {
        if (null == mActivityReference.get()) {
            return;
        }

        AlipayUtil.pay(mActivityReference.get(), payId, title,
                info, price / 1000 + "",
                listener);
    }

    private void doWechatPay(String payId) {
//        final IWXAPI msgAPI = WXAPIFactory.createWXAPI(ToDoOrderActivity.this, Constants.Data.WECHAT_APP_ID);


    }

    private void doBalancePay(String payId){

    }

    public interface PayListener {
        void onPayReturn(boolean success);
    }
}
