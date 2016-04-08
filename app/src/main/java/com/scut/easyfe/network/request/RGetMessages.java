package com.scut.easyfe.network.request;

import com.fasterxml.jackson.databind.JavaType;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Message;
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
 * 获取消息中心列表
 * Created by jay on 16/4/7.
 */
public class RGetMessages extends RequestBase<List<Message>>{
    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_MESSAGES;
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
    public List<Message> parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        List<Message> result = new ArrayList<>();
        JavaType javaType = mObjectMapper.getTypeFactory().constructParametricType(List.class, Message.class);
        try {
            JSONArray messages = jsonObject.optJSONArray("messages");
            if(null != messages){
            /** 将返回的地址JsonArray转化为List<CXRoom> */
            result = mObjectMapper.readValue(messages.toString(), javaType);
            }
        } catch (IOException e) {
            LogUtils.i("Json转换为List<CXRoom>失败!");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
