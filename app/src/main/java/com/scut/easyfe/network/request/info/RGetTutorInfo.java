package com.scut.easyfe.network.request.info;

import com.fasterxml.jackson.databind.JavaType;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.School;
import com.scut.easyfe.entity.TutorInfo;
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
 * 获取专业辅导的信息(由课程跟状态(高中 初中 高考 中考)决定)
 * Created by jay on 16/6/8.
 */
public class RGetTutorInfo extends RequestBase<TutorInfo>{
    private String mCategory = "";
    private String mCourse = "";

    public RGetTutorInfo(String mCategory, String mCourse) {
        this.mCategory = mCategory;
        this.mCourse = mCourse;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_TUTOR_INFO;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        HttpParams params = new HttpParams();
        params.putQueryParams("category", mCategory);
        params.putQueryParams("course", mCourse);
        return params;
    }

    @Override
    public TutorInfo parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        TutorInfo result = new TutorInfo();
        try {
            result = mObjectMapper.readValue(jsonObject.toString(), TutorInfo.class);
        }catch (Exception e){
            LogUtils.i("TutorInfo  parseResultAsObject -> 解析出错");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
