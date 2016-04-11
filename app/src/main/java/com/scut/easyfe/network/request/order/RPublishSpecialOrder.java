package com.scut.easyfe.network.request.order;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.SpecialOrder;
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
public class RPublishSpecialOrder extends RequestBase<JSONObject>{

    private SpecialOrder mSpecialOrder = new SpecialOrder();
    private String mToken = "";

    public RPublishSpecialOrder(@NonNull String token, @NonNull SpecialOrder specialOrder) {
        this.mToken = token;
        this.mSpecialOrder = specialOrder;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_PUBLISH_SPECIAL_ORDER;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", mToken);
        params.put("grade", mSpecialOrder.getGrade());
        params.put("course", mSpecialOrder.getCourse());
        params.put("time", mSpecialOrder.getTime());
        params.put("price", mSpecialOrder.getPrice());
        params.put("teachTime", mSpecialOrder.getTeachTime().getTeachTimeJson());
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
