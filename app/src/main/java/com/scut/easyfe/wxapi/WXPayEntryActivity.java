package com.scut.easyfe.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.order.RGetOrderDetail;
import com.scut.easyfe.ui.activity.VipActivity;
import com.scut.easyfe.ui.activity.order.ReservedOrCompletedOrderActivity;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.LogUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.Data.WECHAT_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    @SuppressWarnings("all")
    protected void setLayoutView() {
        //设置背景透明
         ((ViewGroup) findViewById(android.R.id.content)).setBackgroundColor(R.color.transparent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        LogUtils.i("wechat_pay -> resp.errCode ->" + resp.errCode);
        LogUtils.i("wechat_pay -> resp.msg ->" + resp.errStr);
        if (resp instanceof PayResp) {
            PayResp payResp = (PayResp) resp;
            switch (payResp.errCode) {
                case 0:
                    String[] datas = payResp.extData.split("_");
                    int buyType = Integer.valueOf(datas[0]);
                    String id = datas[1];
                    switch (buyType) {
                        case Constants.Identifier.BUY_ORDER:
                            startLoading("刷新数据中");
                            RequestManager.get().execute(new RGetOrderDetail(id), new RequestListener<Order>() {
                                @Override
                                public void onSuccess(RequestBase request, Order result) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(Constants.Key.ORDER_TYPE, Constants.Identifier.ORDER_COMPLETED);
                                    bundle.putSerializable(Constants.Key.ORDER, result);
                                    redirectToActivity(WXPayEntryActivity.this, ReservedOrCompletedOrderActivity.class, bundle);
                                    stopLoading();
                                    finish();
                                }

                                @Override
                                public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                                    toast(errorMsg);
                                    stopLoading();
                                    finish();
                                }
                            });
                            break;

                        case Constants.Identifier.BUY_VIP_EVENT:
                            Bundle bundle = new Bundle();
                            bundle.putBoolean(Constants.Key.IS_MY_VIP_ACTIVITY, true);
                            redirectToActivity(mContext, VipActivity.class, bundle);
                            finish();
                            break;

                        case Constants.Identifier.BUY_RECHARGE:
                            finish();
                            break;

                        default:
                            break;
                    }
                    break;

                case -1:
                    toast("支付失败");
                    finish();
                    break;

                case -2:
                    toast("支付取消");
                    finish();
                    break;
            }
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        LogUtils.i("wechat_pay -> onReq ->" + baseReq.openId);
    }
}