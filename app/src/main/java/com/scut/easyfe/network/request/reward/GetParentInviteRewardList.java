package com.scut.easyfe.network.request.reward;

import com.fasterxml.jackson.databind.JavaType;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.reward.ParentInviteReward;
import com.scut.easyfe.entity.reward.TeacherInviteReward;
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
 *  获取邀请奖励的列表（家长）
 * Created by jay on 16/5/12.
 */
public class GetParentInviteRewardList extends RequestBase<List<ParentInviteReward>>{
    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_PARENT_INVITE_REWARD_LIST;
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
    public List<ParentInviteReward> parseResultAsObject(JSONObject jsonObject)
            throws IOException, JSONException {
        List<ParentInviteReward> result = new ArrayList<>();
        JavaType javaType = mObjectMapper.getTypeFactory().constructParametricType(List.class, ParentInviteReward.class);
        try {
            JSONArray rewards = jsonObject.optJSONArray("list");
            if(null != rewards){
                /** 将返回的地址JsonArray转化为List<ParentInviteReward> */
                result = mObjectMapper.readValue(rewards.toString(), javaType);
            }
        } catch (IOException e) {
            LogUtils.i("Json转换为List<ParentInviteReward>失败!");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
