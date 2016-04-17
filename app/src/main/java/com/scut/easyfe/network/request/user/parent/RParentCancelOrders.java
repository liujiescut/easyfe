package com.scut.easyfe.network.request.user.parent;

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
 * 取消订单接口
 * Created by gz on 16/4/17.
 */
public class RParentCancelOrders extends RequestBase<JSONObject>{
    private String mToken = "";
    private List<String> mOrders = new ArrayList<>();

    public RParentCancelOrders(@NonNull String token, @NonNull List<String> orders) {
        this.mToken = token;
        this.mOrders = orders;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_PARENT_CANCEL_ORDERS;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", mToken);
        params.put("orders", new JSONArray(mOrders));
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
