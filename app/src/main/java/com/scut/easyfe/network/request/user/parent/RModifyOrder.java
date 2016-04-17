package com.scut.easyfe.network.request.user.parent;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 家长修改订单
 * Created by jay on 16/4/16.
 */
public class RModifyOrder extends RequestBase<JSONPObject>{
    @Override
    public String getUrl() {
        return Constants.URL.URL_ORDERS;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        return null;
    }

    @Override
    public JSONPObject parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        return null;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.PUT;
    }
}
