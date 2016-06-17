package com.scut.easyfe.network.request.pay;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 预支付
 * Created by jay on 16/6/13.
 */
public class RPrePay extends RequestBase<JSONObject>{
    private String orderId = "";        //支付订单时传
    private String vipEventId = "";     //支付会员活动时传
    private int money = 0;              //单位: 分
    private int buy = -1;               //0支付订单, 1支付会员活动, 2充值
    private int payType = -1;           //0现金, 1支付宝, 2微信

    public RPrePay(String orderId, String vipEventId, int money, int buyType, int payType) {
        this.orderId = orderId;
        this.vipEventId = vipEventId;
        this.money = money;
        this.buy = buyType;
        this.payType = payType;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_PRE_PAY;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", App.getUser().getToken());
        params.put("orderId", orderId);
        params.put("money", money);
        params.put("buy", buy);
        params.put("payType", payType);

        if (null != orderId && orderId.length() != 0) {
            params.put("orderId", orderId);
        }

        if (null != vipEventId && vipEventId.length() != 0) {
            params.put("vipEventId", vipEventId);
        }

        return params;
    }

    @Override
    public HttpParams getQueryParams() {
        return null;
    }

    @Override
    public JSONObject parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        return jsonObject;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.POST;
    }
}
