package com.scut.easyfe.network.request.user.teacher;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.TeachableCourse;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 家教增加可教授课程
 * Created by jay on 16/4/14.
 */
public class RTeacherAddCourse extends RequestBase<JSONObject>{
    private TeachableCourse mTeachableCourse;
    private String mToken;

    public RTeacherAddCourse(@NonNull String token, @NonNull TeachableCourse teachableCourse) {
        this.mTeachableCourse = teachableCourse;
        this.mToken = token;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_TEACHER_ADD_COURSE;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", mToken);
        params.put("grade", mTeachableCourse.getGrade());
        params.put("course", mTeachableCourse.getCourse());
        params.put("price", mTeachableCourse.getPrice());

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
