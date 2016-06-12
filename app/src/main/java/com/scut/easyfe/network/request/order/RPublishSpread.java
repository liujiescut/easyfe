package com.scut.easyfe.network.request.order;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 家教发布特价订单
 * Created by jay on 16/4/10.
 */
public class RPublishSpread extends RequestBase<JSONObject>{

    private Order mOrder = new Order();
    private String mToken = "";

    public RPublishSpread(@NonNull String token, @NonNull Order order) {
        this.mToken = token;
        this.mOrder = order;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_PUBLISH_SPECIAL_ORDER;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", mToken);
        params.put("grade", mOrder.getGrade());
        params.put("course", mOrder.getCourse());
        params.put("time", mOrder.getTime());
        params.put("price", mOrder.getPrice());
        params.put("teachTime", mOrder.getTeachTime().getTeachTimeJson());
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
