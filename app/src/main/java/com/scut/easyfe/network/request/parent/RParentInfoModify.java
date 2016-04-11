package com.scut.easyfe.network.request.parent;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 家长修改自己资料 Todo: test
 * Created by jay on 16/4/10.
 */
public class RParentInfoModify extends RequestBase<JSONObject>{
    User mUser;

    public RParentInfoModify(User user) {
        this.mUser = user;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_PARENT_INFO_MODIFY;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", mUser.getToken());
        params.put("position", mUser.getPosition().getAddressJson());
        params.put("childGender", mUser.getParentMessage().getChildGender());
        params.put("childGrade", mUser.getParentMessage().getChildGrade());
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
