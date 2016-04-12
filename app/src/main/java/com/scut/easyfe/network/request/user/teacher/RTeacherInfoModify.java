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
 * 家教信息维护页面的修改
 * Created by jay on 16/4/10.
 */
public class RTeacherInfoModify extends RequestBase<JSONObject>{
    User mUser;

    public RTeacherInfoModify(User user) {
        this.mUser = user;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_TEACHER_INFO_MODIFY;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", mUser.getToken());
        params.put("isLock", mUser.getTeacherMessage().isLock());
        params.put("freeTrafficTime", mUser.getTeacherMessage().getFreeTrafficTime());
        params.put("maxTrafficTime", mUser.getTeacherMessage().getMaxTrafficTime());
        params.put("minCourseTime", mUser.getTeacherMessage().getMinCourseTime());
        params.put("subsidy", mUser.getTeacherMessage().getSubsidy());
        params.put("angelPlan", mUser.getTeacherMessage().getAngelPlan().getAngelPlanJson());

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
