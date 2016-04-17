package com.scut.easyfe.network.request.user.teacher;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 家教确认订单
 * Created by jay on 16/4/10.
 */
public class RTeacherComfirmOrder extends RequestBase<JSONObject>{
    private String mToken = "";
    private String mOrderId = "";

    public RTeacherComfirmOrder(@NonNull String token, @NonNull String orderId) {
        this.mToken = token;
        this.mOrderId = orderId;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_TEACHER_CONFIRM_ORDER;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", mToken);
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
