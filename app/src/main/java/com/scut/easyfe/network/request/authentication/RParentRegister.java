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

/**
 * 家长注册
 */
public class RParentRegister extends RequestBase<User> {
    private User user;

    public RParentRegister(@NonNull User user){
        this.user = user;
    }


    @Override
    public int getMethod() {
        return Request.HttpMethod.POST;
    }


    @Override
    public String getUrl() {
        return Constants.URL.URL_PARENT_REGISTER;
    }

    @Override
    public JSONObject getJsonParams() {
        JSONObject params = new JSONObject();
        try {
            params.put("name", user.getName());
            params.put("gender", user.getGender());
            params.put("phone", user.getPhone());
            params.put("password", user.getPassword());
            params.put("childGender", user.getParentMessage().getChildGender());
            params.put("childGrade", user.getParentMessage().getChildGrade());

            params.put("position", user.getPosition().getAddressJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
    }

    @Override
    public HttpParams getQueryParams() {
        return null;
    }

    @Override
    public User parseResultAsObject(JSONObject jsonObject) {
        //此处会返回token , 需要缓存个人信息 , 并执行登录
        User user = new User();
        try {
            user = mObjectMapper.readValue(jsonObject.toString(),User.class);
        }catch (Exception e){
            LogUtils.i("CXUser  parseResultAsObject -> 解析出错");
            e.printStackTrace();
        }
        return user;
    }

}
