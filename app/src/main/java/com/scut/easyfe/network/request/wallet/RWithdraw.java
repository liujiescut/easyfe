package com.scut.easyfe.network.request.wallet;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 提现接口   Todo: 测试跟接上去
 * Created by jay on 16/4/10.
 */
public class RWithdraw extends RequestBase<JSONObject>{
    private String mToken = "";
    private float mMoney = 0f;

    public RWithdraw(@NonNull String mToken, @NonNull float mMoney) {
        this.mToken = mToken;
        this.mMoney = mMoney;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_WALLET_INFO;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", mToken);
        params.put("money", mMoney);
        return params;
    }

    @Override
    public HttpParams getQueryParams() {
        return null;
    }

    @Override
    public JSONObject parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        return null;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.POST;
    }
}
