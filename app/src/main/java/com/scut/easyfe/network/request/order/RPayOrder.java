package com.scut.easyfe.network.request.order;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 支付接口
 * Created by jayme on 16/5/17.
 */
public class RPayOrder extends RequestBase<JSONObject>{
    private String mOrderId = "";

    public RPayOrder(String mOrderId) {
        this.mOrderId = mOrderId;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_PAY_ORDER;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject param = new JSONObject();
        param.put("token", App.getUser().getToken());
        param.put("orderId", mOrderId);
        return param;
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
