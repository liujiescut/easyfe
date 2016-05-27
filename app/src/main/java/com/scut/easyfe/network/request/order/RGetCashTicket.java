package com.scut.easyfe.network.request.order;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 领取现金券
 * Created by Jayme on 16/5/14.
 */
public class RGetCashTicket extends RequestBase<JSONObject>{
    private String mOrderId;

    public RGetCashTicket(@NonNull String rewardId) {
        this.mOrderId = rewardId;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_PARENT_COMPLETE_ORDER_REWARD;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", App.getUser().getToken());
        params.put("orderId", mOrderId);
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
