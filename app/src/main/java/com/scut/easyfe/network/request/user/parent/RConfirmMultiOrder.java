package com.scut.easyfe.network.request.user.parent;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;
import com.scut.easyfe.utils.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 家长确认多次预约订单
 * Created by jay on 16/4/10.
 */
public class RConfirmMultiOrder extends RequestBase<JSONObject>{
    private String mToken = "";
    private Order mOrder = new Order();
    private int mTimes = 0;

    public RConfirmMultiOrder(@NonNull String token, @NonNull Order order, int times) {
        this.mToken = token;
        this.mOrder = order;
        this.mTimes = times;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_CONFIRM_MULTI_ORDER;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", mToken);
        params.put("teacherId", mOrder.getTeacher().get_id());
        params.put("grade", mOrder.getGrade());
        params.put("course", mOrder.getCourse());
        params.put("time", mOrder.getTime());
        params.put("price", mOrder.getPrice());
        params.put("subsidy", mOrder.getSubsidy());
        params.put("childAge", mOrder.getChildAge());
        params.put("childGender", mOrder.getChildGender());

        JSONObject multiBookTime = new JSONObject();
        multiBookTime.put("weekDay", TimeUtils.getWeekIntFromString(mOrder.getTeachTime().getDate()));
        multiBookTime.put("time", mOrder.getTeachTime().getTime());
        params.put("multiBookTime", multiBookTime);

        //Todo teachTime

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
