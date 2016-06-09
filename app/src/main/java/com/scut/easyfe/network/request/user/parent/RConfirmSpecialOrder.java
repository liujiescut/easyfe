package com.scut.easyfe.network.request.user.parent;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 家长确认特价订单
 * Created by jay on 16/4/10.
 */
public class RConfirmSpecialOrder extends RequestBase<JSONObject>{
    private String mToken = "";
    private int mTrafficTime = 0;
    private int mProfessionalTutor = 0;
    private String mOrderId = "";

    public RConfirmSpecialOrder(@NonNull String token, @NonNull int trafficTime,
                                @NonNull String orderId, @NonNull int professionalTutor) {
        this.mToken = token;
        this.mTrafficTime = trafficTime;
        this.mOrderId = orderId;
        this.mProfessionalTutor = professionalTutor;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_CONFIRM_SPECIAL_ORDER;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", mToken);
        params.put("orderId", mOrderId);
        params.put("trafficTime", mTrafficTime);
        if(0 != mProfessionalTutor) {
            params.put("professionalTutor", mProfessionalTutor);
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
