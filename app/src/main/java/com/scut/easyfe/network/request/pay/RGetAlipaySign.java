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
 * 获取支付宝订单的签名信息
 * Created by jay on 16/6/13.
 */
public class RGetAlipaySign extends RequestBase<JSONObject>{
    private String orderId = "";
    private String subject = "";
    private String body = "";
    private String price = "0";

    public RGetAlipaySign(String orderId, String subject, String body, String price) {
        this.orderId = orderId;
        this.subject = subject;
        this.body = body;
        this.price = price;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_ALIPAY_SIGN;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        HttpParams params = new HttpParams();
        params.putQueryParams("token", App.getUser().getToken());
        params.putQueryParams("orderId", orderId);
        params.putQueryParams("subject", subject);
        params.putQueryParams("body", body);
        params.putQueryParams("price", price);

        return params;
    }

    @Override
    public JSONObject parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        return jsonObject;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
