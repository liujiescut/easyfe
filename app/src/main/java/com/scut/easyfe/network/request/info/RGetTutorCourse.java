package com.scut.easyfe.network.request.info;

import com.fasterxml.jackson.databind.JavaType;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.School;
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
 * 获取专业辅导课程
 * Created by jay on 16/6/8.
 */
public class RGetTutorCourse extends RequestBase<List<String>>{
    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_TUTOR_COURSE;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        return null;
    }

    @Override
    public List<String> parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        List<String> result = new ArrayList<>();
        JavaType javaType = mObjectMapper.getTypeFactory().constructParametricType(List.class, String.class);
        try {
            JSONArray courses = jsonObject.optJSONArray("list");
            if(null != courses){
                /** 将返回的专业辅导课程JsonArray转化为List<String> */
                result = mObjectMapper.readValue(courses.toString(), javaType);
            }
        } catch (IOException e) {
            LogUtils.i("Json转换为List<Course>失败!");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
