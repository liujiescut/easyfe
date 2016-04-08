package com.scut.easyfe.network.request.authentication;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;
import com.scut.easyfe.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 登陆请求
 * Created by jay on 16/4/7.
 */
public class RLogin extends RequestBase<User> {
    //手机号
    private String mPhone = "";
    //密码
    private String mPassword = "";

    public RLogin(@NonNull String mPhone, @NonNull String mPassword) {
        this.mPhone = mPhone;
        this.mPassword = mPassword;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_LOGIN;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        HttpParams params = new HttpParams();
        params.putQueryParams("phone", mPhone);
        params.putQueryParams("password", mPassword);
        return params;
    }

    @Override
    public User parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        User user = new User();
        try {
            user = mObjectMapper.readValue(jsonObject.toString(), User.class);
        }catch (Exception e){
            LogUtils.i("CXUser  parseResultAsObject -> 解析出错");
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
