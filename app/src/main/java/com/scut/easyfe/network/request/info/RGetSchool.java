package com.scut.easyfe.network.request.info;

import com.fasterxml.jackson.databind.JavaType;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Course;
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
 * 获取家教所在学校
 * Created by jay on 16/4/8.
 */
public class RGetSchool extends RequestBase<List<School>>{
    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_SCHOOL;
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
    public List<School> parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        List<School> result = new ArrayList<>();
        JavaType javaType = mObjectMapper.getTypeFactory().constructParametricType(List.class, School.class);
        try {
            JSONArray schools = jsonObject.optJSONArray("education");
            if(null != schools){
                /** 将返回的地址JsonArray转化为List<School> */
                result = mObjectMapper.readValue(schools.toString(), javaType);
            }
        } catch (IOException e) {
            LogUtils.i("Json转换为List<School>失败!");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
