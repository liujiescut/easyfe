package com.scut.easyfe.network.request.reward;

import com.fasterxml.jackson.databind.JavaType;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.reward.ParentCourseReward;
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
 * 获取完成课时奖励列表（家长）
 * Created by jay on 16/5/12.
 */
public class RGetParentCompleteCourseRewardList extends RequestBase<List<ParentCourseReward>>{
    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_PARENT_COMPLETE_COURSE_REWARD_LIST;
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
    public List<ParentCourseReward> parseResultAsObject(JSONObject jsonObject)
            throws IOException, JSONException {
        List<ParentCourseReward> result = new ArrayList<>();
        JavaType javaType = mObjectMapper.getTypeFactory().constructParametricType(List.class, ParentCourseReward.class);
        try {
            JSONArray rewards = jsonObject.optJSONArray("list");
            if(null != rewards){
                /** 将返回的地址JsonArray转化为List<ParentCourseReward> */
                result = mObjectMapper.readValue(rewards.toString(), javaType);
            }
        } catch (IOException e) {
            LogUtils.i("Json转换为List<ParentCourseReward>失败!");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
