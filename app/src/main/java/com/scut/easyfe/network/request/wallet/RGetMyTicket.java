package com.scut.easyfe.network.request.wallet;

import com.fasterxml.jackson.databind.JavaType;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Ticket;
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
 * 获取我的优惠券(家长)
 * Created by jay on 16/4/7.
 */
public class RGetMyTicket extends RequestBase<List<Ticket>>{
    private String mToken = "";

    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_MY_TICKET;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        HttpParams params = new HttpParams();
        params.putQueryParams("token", App.getUser().getToken());
        return params;
    }

    @Override
    public List<Ticket> parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        List<Ticket> result = new ArrayList<>();
        JavaType javaType = mObjectMapper.getTypeFactory().constructParametricType(List.class, Ticket.class);
        try {
            JSONArray tickets = jsonObject.optJSONArray("list");
            if(null != tickets){
                /** 将返回的地址JsonArray转化为List<Ticket> */
                result = mObjectMapper.readValue(tickets.toString(), javaType);
            }
        } catch (IOException e) {
            LogUtils.i("Json转换为List<Ticket>失败!");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
