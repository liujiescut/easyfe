package com.scut.easyfe.network.request;

import com.fasterxml.jackson.databind.JavaType;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.VipEvent;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;
import com.scut.easyfe.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取会员活动列表(传token的话就获取我的会员活动列表)
 * Created by jay on 16/6/14.
 */
public class RGetVipEvent extends RequestBase<List<VipEvent>>{
    private int limit = Constants.DefaultValue.DEFAULT_LOAD_COUNT;
    private int skip = 0;
    private boolean getMyVipEvent = false;

    public RGetVipEvent(int skip) {
        this(Constants.DefaultValue.DEFAULT_LOAD_COUNT, skip);
    }


    public RGetVipEvent(int limit, int skip) {
        this(limit, skip, false);
    }

    public RGetVipEvent(int limit, int skip, boolean getMyVipEvent) {
        this.limit = limit;
        this.skip = skip;
        this.getMyVipEvent = getMyVipEvent;
    }

    @Override
    public String getUrl() {
        return getMyVipEvent ? Constants.URL.URL_GET_MY_VIP_EVENT : Constants.URL.URL_GET_VIP_EVENT;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        HttpParams params = new HttpParams();
        params.putQueryParams("limit", limit);
        params.putQueryParams("skip", skip);
        if(getMyVipEvent){
            params.putQueryParams("token", App.getUser().getToken());
        }

        return params;
    }

    @Override
    public List<VipEvent> parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {

        List<VipEvent> result = new ArrayList<>();
        JavaType javaType = mObjectMapper.getTypeFactory().constructParametricType(List.class, VipEvent.class);
        try {
            JSONArray events = jsonObject.optJSONArray("events");
            if(null != events){
                /** 将返回的JsonArray转化为List<VipEvent> */
                result = mObjectMapper.readValue(events.toString(), javaType);
            }
        } catch (IOException e) {
            LogUtils.i("Json转换为List<VipEvent>失败!");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
