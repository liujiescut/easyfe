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
import com.scut.easyfe.network.request.pay.RAlipayPay;
import com.scut.easyfe.network.request.pay.RCashPay;
import com.scut.easyfe.network.request.pay.RWechatPay;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Date;

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

    public PayUtil(Activity activity, int buyType, String id, String title, String info, int price, PayListener listener) {
        mActivityReference = new WeakReference<>(activity);
        this.buyType = buyType;
        this.id = id;
        this.title = title;
        this.price = price;
        this.info = info;
        this.listener = listener;

        //todo delete it
        this.price = 1;
    }

    public void showPayDialog() {
        if (null == mActivityReference.get()) {
            return;
        }

        AlertView mPayWayAlertView = new AlertView("付款方式", null, "取消", null,
                new String[]{"余额", "支付宝", "微信"},
                mActivityReference.get(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, final int position) {

                RequestBase<JSONObject> request;
                String orderId = buyType == Constants.Identifier.BUY_ORDER ? id : "";
                String vipEventId = buyType == Constants.Identifier.BUY_VIP_EVENT ? id : "";
                if (position == 0) {
                    request = new RCashPay(buyType, orderId, vipEventId, price);
                } else if (position == 1) {
                    request = new RAlipayPay(buyType, orderId, vipEventId, price);
                } else if (position == 2) {
                    request = new RWechatPay(buyType, orderId, vipEventId, price);
                } else {
                    request = new RAlipayPay(buyType, orderId, vipEventId, price);
                }

                RequestManager.get().execute(request, new RequestListener<JSONObject>() {
                    @Override
                    public void onSuccess(RequestBase request, JSONObject result) {
                        if (position == 0) {
                            doBalancePay();

                        } else if (position == 1) {
                            String payId = result.optString("prePayDataId");
                            doAlipay(payId);

                        } else if (position == 2) {
                            doWechatPay(result);
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
                info, price / 100 + "",
                listener);
    }

    /**
     * 调用微信支付
     */
    private void doWechatPay(final JSONObject data) {
        if (null == mActivityReference || null == mActivityReference.get()) {
            return;
        }

        mActivityReference.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                IWXAPI api = WXAPIFactory.createWXAPI(mActivityReference.get(), Constants.Data.WECHAT_APP_ID, true);
                api.registerApp(Constants.Data.WECHAT_APP_ID);

                String appId = data.optString("appid");
                String partnerId = data.optString("partnerid");
                String prepayId = data.optString("prepayid");
                String sign = data.optString("sign");
                String packageValue = data.optString("package");
                String timestamp = data.optString("timestamp");
                String nonceStr = data.optString("noncestr");

                PayReq request = new PayReq();
                request.appId = appId;
                request.partnerId = partnerId;
                request.prepayId = prepayId;
                request.packageValue = packageValue;
                request.nonceStr = nonceStr;
                request.timeStamp = timestamp;
                request.sign = sign;

                if (!api.sendReq(request)) {
                    Toast.makeText(App.get().getApplicationContext(), "支付请求发送失败", Toast.LENGTH_SHORT).show();
                    listener.onPayReturn(false);

                } else {
                    Toast.makeText(App.get().getApplicationContext(), "支付请求发送成功", Toast.LENGTH_SHORT).show();
                    listener.onPayReturn(true);
                }
            }
        });
    }

    private void doBalancePay() {

    }

    public interface PayListener {
        void onPayReturn(boolean success);
    }
}
