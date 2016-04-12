package com.scut.easyfe.network.request.authentication;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 家教注册
 * Created by jay on 16/4/9.
 */
public class RTeacherRegister extends RequestBase<JSONObject>{
    private User mUser;

    public RTeacherRegister(User user) {
        this.mUser = user;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_TEACHER_REGISTER;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("name", mUser.getName());
        params.put("gender", mUser.getGender());
        params.put("birthday", mUser.getBirthday());
        params.put("phone", mUser.getPhone());
        params.put("password", mUser.getPassword());
        params.put("position", mUser.getPosition().getAddressJson());
        params.put("business", mUser.getBusiness().getBusinessJson());
        params.put("teacherPrice", mUser.getTeacherMessage().getTeachCourseJsonArray());
        params.put("teacherMessage", mUser.getTeacherMessage().getTeacherJson());

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
