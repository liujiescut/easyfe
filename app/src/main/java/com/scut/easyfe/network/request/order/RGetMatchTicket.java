package com.scut.easyfe.network.request.order;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 获取合适的优惠券
 * Created by jay on 16/6/9.
 */
public class RGetMatchTicket extends RequestBase<JSONObject>{

    //订单执行日期格式: 2016-06-09
    private String mDate = "";
    //家教时长
    private int mTime = 0;
    //年级
    private String mGrade = "";

    public RGetMatchTicket(String mDate, int mTime, String mGrade) {
        this.mDate = mDate;
        this.mTime = mTime;
        this.mGrade = mGrade;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_MATCH_TICKET;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", App.getUser().getToken());
        params.put("date", mDate);
        params.put("time", mTime);
        params.put("grade", mGrade);
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
