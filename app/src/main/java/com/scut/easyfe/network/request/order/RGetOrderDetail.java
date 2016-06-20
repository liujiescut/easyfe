package com.scut.easyfe.network.request.order;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;
import com.scut.easyfe.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 获取订单详情
 * Created by jay on 16/4/7.
 */
public class RGetOrderDetail extends RequestBase<Order> {
    private String mOrderId = "";

    public RGetOrderDetail(@NonNull String orderId) {
        this.mOrderId = orderId;
    }


    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_ORDER_DETAIL;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        HttpParams params = new HttpParams();
        params.putQueryParams("token", App.getUser().getToken());
        params.putQueryParams("orderId", mOrderId);
        return params;
    }

    @Override
    public Order parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        Order order = new Order();
        try {
            order = mObjectMapper.readValue(jsonObject.toString(), Order.class);
        }catch (Exception e){
            LogUtils.i("Order  parseResultAsObject -> 解析出错");
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
