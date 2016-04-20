package com.scut.easyfe.network.request.user.parent;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 家长评价家教
 * Created by jay on 16/4/19.
 */
public class RCommentTeacher extends RequestBase<JSONObject>{
    private String mToken = "";
    private String mOrderId = "";
    private String mContent = "";
    private float mAbility = 0f;
    private float mPunctualScore = 0f;
    private float mChildAccept = 0f;

    public RCommentTeacher(String token, String orderId, String content, float ability, float punctualScore, float childAccept) {
        this.mToken = token;
        this.mOrderId = orderId;
        this.mContent = content;
        this.mAbility = ability;
        this.mPunctualScore = punctualScore;
        this.mChildAccept = childAccept;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_COMMENT_TEACHER;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", mToken);
        params.put("orderId", mOrderId);
        params.put("content", mContent);
        params.put("ability", mAbility);
        params.put("punctualScore", mPunctualScore);
        params.put("childAccept", mChildAccept);
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
        return Request.HttpMethod.POST;
    }
}