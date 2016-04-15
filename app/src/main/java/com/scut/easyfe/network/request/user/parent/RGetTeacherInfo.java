package com.scut.easyfe.network.request.user.parent;

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
 * 家长获取家教信息
 * Created by jay on 16/4/14.
 */
public class RGetTeacherInfo extends RequestBase<Order>{
    private String mToken = "";
    private String mTeacherId = "";

    public RGetTeacherInfo(String token, String teacherId) {
        this.mToken = token;
        this.mTeacherId = teacherId;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_TEACHER_DETAIL_INFO;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        HttpParams params = new HttpParams();
        params.putQueryParams("token", mToken);
        params.putQueryParams("teacherId", mTeacherId);

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
