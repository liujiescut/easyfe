package com.scut.easyfe.network.request.user.parent;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.BriefOrder;
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
 * 修改订单接口
 * Created by gz on 16/4/17.
 */
public class RParentModifyOrders extends RequestBase<JSONObject>{
    private String mToken = "";
    private List<BriefOrder> mOrders = new ArrayList<>();

    public RParentModifyOrders(@NonNull String token, @NonNull List<BriefOrder> orders) {
        this.mToken = token;
        this.mOrders = orders;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_PARENT_MODIFY_ORDERS;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        JSONArray orderArray = new JSONArray();
        for (BriefOrder order :
                mOrders) {
            JSONObject orderJson = new JSONObject();
            orderJson.put("orderId", order.get_id());
            orderJson.put("time", order.getTime());
            orderJson.put("teachTime", order.getTeachTime().getTeachTimeJson());
            orderArray.put(orderJson);
        }

        params.put("token", mToken);
        params.put("orders", orderArray);
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
        return Request.HttpMethod.PUT;
    }
}
