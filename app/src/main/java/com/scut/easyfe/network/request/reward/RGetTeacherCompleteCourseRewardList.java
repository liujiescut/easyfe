package com.scut.easyfe.network.request.reward;

import com.fasterxml.jackson.databind.JavaType;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.reward.ParentCourseReward;
import com.scut.easyfe.entity.reward.TeacherCourseReward;
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
 * 获取单一课程单价增加奖励列表（家教）
 * Created by jay on 16/5/12.
 */
public class RGetTeacherCompleteCourseRewardList extends RequestBase<List<TeacherCourseReward>>{
    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_TEACHER_COMPLETE_COURSE_REWARD_LIST;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        HttpParams params = new HttpParams();
        params.putQueryParams("token", App.getUser().getToken());
        return params;
    }

    @Override
    public List<TeacherCourseReward> parseResultAsObject(JSONObject jsonObject)
            throws IOException, JSONException {
        List<TeacherCourseReward> result = new ArrayList<>();
        JavaType javaType = mObjectMapper.getTypeFactory().constructParametricType(List.class, TeacherCourseReward.class);
        try {
            JSONArray rewards = jsonObject.optJSONArray("list");
            if(null != rewards){
                /** 将返回的地址JsonArray转化为List<TeacherCourseReward> */
                result = mObjectMapper.readValue(rewards.toString(), javaType);
            }
        } catch (IOException e) {
            LogUtils.i("Json转换为List<TeacherCourseReward>失败!");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
