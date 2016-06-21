package com.scut.easyfe.network.request;

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
 * 使用积分购买会员活动
 * Created by jay on 16/6/18.
 */
public class RBuyVipEventByScore extends RequestBase<JSONObject>{

    private String vipEventId = "";

    public RBuyVipEventByScore(@NonNull String vipEventId) {
        this.vipEventId = vipEventId;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_BUY_VIP_EVENT_BY_SCORE;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", App.getUser().getToken());
        params.put("vipEventId", vipEventId);

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
