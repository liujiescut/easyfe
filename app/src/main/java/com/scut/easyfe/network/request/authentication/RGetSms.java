package com.scut.easyfe.network.request.authentication;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 获取验证码
 * Created by jay on 16/6/9.
 */
public class RGetSms extends RequestBase<JSONObject>{
    private String mPhone = "";

    public RGetSms(@NonNull String mPhone) {
        this.mPhone = mPhone;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_SMS;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        HttpParams params = new HttpParams();
        params.putQueryParams("phone", mPhone);
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
