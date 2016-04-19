package com.scut.easyfe.network.request.user.teacher;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 家教取消订单接口
 * Created by gz on 16/4/17.
 */
public class RTeacherCancelOrder extends RequestBase<Integer>{
    private String mToken = "";
    private String mOrderId = "";

    public RTeacherCancelOrder(@NonNull String token, @NonNull String orderId) {
        this.mToken = token;
        this.mOrderId = orderId;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_TEACHER_CANCEL_ORDERS;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", mToken);
        params.put("orders", mOrderId);
        return params;
    }

    @Override
    public HttpParams getQueryParams() {
        return null;
    }

    @Override
    public Integer parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        return jsonObject.optInt("badRecord");
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.POST;
    }
}
