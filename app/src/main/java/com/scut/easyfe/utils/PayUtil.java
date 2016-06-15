package com.scut.easyfe.utils;

import android.app.Activity;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.scut.easyfe.R;

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
    private String price = "";
    private String info = "";

    public PayUtil(Activity activity, String id, String title, String info, String price, PayListener listener) {
        this.mActivityReference = new WeakReference<Activity>(activity);
        this.id = id;
        this.title = title;
        this.price = price;
        this.info = info;
        this.listener = listener;
    }

    public void showPayDialog(){
        if (null == mActivityReference.get()) {
            return;
        }

        AlertView mPayWayAlertView = new AlertView("付款方式", null, "取消", null,
                new String[]{"支付宝", "微信", "余额"},
                mActivityReference.get(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {
                    doAlipay();
                } else if (position == 1) {
                    doWechatPay();
                } else if (position == 2) {
                    doBalancePay();
                }
            }

        }).setButtonTextColor(R.color.theme_color);
        mPayWayAlertView.show();
    }

    private void doAlipay() {
        if (null == mActivityReference.get()) {
            return;
        }

        AlipayUtil.pay(mActivityReference.get(), id, title,
                info, price,
                listener);
    }

    private void doWechatPay() {
//        final IWXAPI msgAPI = WXAPIFactory.createWXAPI(ToDoOrderActivity.this, Constants.Data.WECHAT_APP_ID);


    }

    private void doBalancePay(){

    }

    public interface PayListener {
        void onPayReturn(boolean success);
    }
}
