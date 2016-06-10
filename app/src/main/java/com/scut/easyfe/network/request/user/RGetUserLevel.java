package com.scut.easyfe.network.request.user;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.UserLevel;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;
import com.scut.easyfe.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 获取用户级别
 * Created by jay on 16/6/10.
 */
public class RGetUserLevel extends RequestBase<UserLevel> {

    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_USER_LEVEL;
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
    public UserLevel parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        UserLevel userLevel = new UserLevel();
        try {
            userLevel = mObjectMapper.readValue(jsonObject.toString(), UserLevel.class);
        }catch (Exception e){
            LogUtils.i("UserLevel  parseResultAsObject -> 解析出错");
            e.printStackTrace();
        }
        return userLevel;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
