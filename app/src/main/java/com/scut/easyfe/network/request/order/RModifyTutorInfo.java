package com.scut.easyfe.network.request.order;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 修改订单反馈信息
 * Created by jay on 16/6/11.
 */
public class RModifyTutorInfo extends RequestBase<JSONObject>{
    private String mOrderId = "";
    private Order.TutorDetail mThisTeachDetail = new Order.TutorDetail();

    public RModifyTutorInfo(@NonNull String mOrderId, @NonNull Order.TutorDetail mThisTeachDetail) {
        this.mOrderId = mOrderId;
        this.mThisTeachDetail = mThisTeachDetail;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_MODIFY_TUTOR;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", App.getUser().getToken());
        params.put("orderId", mOrderId);
        params.put("thisTeachDetail", mThisTeachDetail.getAsJson());

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
