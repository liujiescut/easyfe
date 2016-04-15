package com.scut.easyfe.network.request.user.teacher;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 修改多次预约时间
 * Created by jay on 16/4/10.
 */
public class RTeacherMultiBookTimeModify extends RequestBase<JSONObject>{
    User mUser;

    public RTeacherMultiBookTimeModify(User user) {
        this.mUser = user;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_TEACHER_MULTI_BOOK_TIME_MODIFY;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", mUser.getToken());
        params.put("multiBookTime", mUser.getTeacherMessage().getMultiBookTimeArray());

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
        return Request.HttpMethod.PUT;
    }
}
