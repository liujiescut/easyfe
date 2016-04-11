package com.scut.easyfe.network.request.order;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.SpecialOrder;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 家教发布特价订单
 * Created by jay on 16/4/10.
 */
public class RPublishSpecialOrder extends RequestBase<JSONObject>{

    private SpecialOrder mSpecialOrder = new SpecialOrder();

    public RPublishSpecialOrder(@NonNull SpecialOrder specialOrder) {
        this.mSpecialOrder = specialOrder;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_PUBLISH_SPECIAL_ORDER;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        //Todo
        return null;
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
        return 0;
    }
}
