package com.scut.easyfe.network.request.parent;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Wallet;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;
import com.scut.easyfe.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 获取家长信息
 * Created by jay on 16/4/7.
 */
public class RGetParentInfo extends RequestBase<User>{
    private String mToken = "";

    public RGetParentInfo(@NonNull String token) {
        this.mToken = token;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_PARENT_INFO;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        HttpParams params = new HttpParams();
        params.putQueryParams("token", mToken);
        return params;
    }

    @Override
    public User parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        User user = new User();
        try {
            user = mObjectMapper.readValue(jsonObject.toString(), User.class);
        }catch (Exception e){
            LogUtils.i("User  parseResultAsObject -> 解析出错");
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
