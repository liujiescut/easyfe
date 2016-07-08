package com.scut.easyfe.network.request.user;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 用户修改自己基本资料(点击头像处)
 * Created by jay on 16/4/10.
 */
public class RUserInfoModify extends RequestBase<JSONObject>{
    User mUser;

    public RUserInfoModify(User user) {
        this.mUser = user;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_USER_INFO_MODIFY;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", mUser.getToken());
        params.put("position", mUser.getPosition().getAddressJson());
        if(mUser.isParent()) {
            JSONObject parentMessage = new JSONObject();
            parentMessage.put("childGender", mUser.getParentMessage().getChildGender());
            parentMessage.put("childGrade", mUser.getParentMessage().getChildGrade());
            parentMessage.put("childAge", mUser.getParentMessage().getChildAge());
            params.put("parentMessage", parentMessage);
        }

        if(mUser.isTeacher()){
            JSONObject teacherMessage = new JSONObject();
            teacherMessage.put("hadTeach", mUser.getTeacherMessage().getHadTeach());
            teacherMessage.put("teachCount", mUser.getTeacherMessage().getTeachCount());
            params.put("teacherMessage", teacherMessage);
        }
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
