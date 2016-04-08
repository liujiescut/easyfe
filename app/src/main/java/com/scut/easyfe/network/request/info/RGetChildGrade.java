package com.scut.easyfe.network.request.info;

import com.fasterxml.jackson.databind.JavaType;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Message;
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
public class RGetChildGrade extends RequestBase<List<List>>{
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
    public List<List> parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        List<List> result = new ArrayList<>();
        ArrayList<String> stateList = new ArrayList<>();
        ArrayList<ArrayList<String>> gradeList = new ArrayList<>();
        JSONArray data = jsonObject.optJSONArray("grades");
        if(null != data){
            JSONObject stateJSONObject;
            JSONArray gradeJSONArray;
            for (int i = 0; i < data.length(); i++) {
                stateJSONObject = data.getJSONObject(i);
                gradeJSONArray = stateJSONObject.optJSONArray("grade");
                stateList.add(stateJSONObject.optString("name"));
                ArrayList<String> gradeArrayList = new ArrayList<>();
                for(int j = 0; j < gradeJSONArray.length(); j++){
                    gradeArrayList.add(gradeJSONArray.getString(i));
                }
                gradeList.add(gradeArrayList);
            }
        }

        result.add(stateList);
        result.add(gradeList);

        return result;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
