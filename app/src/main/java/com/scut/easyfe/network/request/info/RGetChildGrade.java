package com.scut.easyfe.network.request.info;

import com.fasterxml.jackson.databind.JavaType;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.ChildGrade;
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
 * 获取学生年级
 * Created by jay on 16/4/8.
 */
public class RGetChildGrade extends RequestBase<List<ChildGrade>>{
    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_CHILD_GRADE;
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
    public List<ChildGrade> parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        List<ChildGrade> result = new ArrayList<>();
        JavaType javaType = mObjectMapper.getTypeFactory().constructParametricType(List.class, ChildGrade.class);
        try {
            JSONArray grades = jsonObject.optJSONArray("grades");
            if(null != grades){
                /** 将返回的地址JsonArray转化为List<ChildGrade> */
                result = mObjectMapper.readValue(grades.toString(), javaType);
            }
        } catch (IOException e) {
            LogUtils.i("Json转换为List<ChildGrade>失败!");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
