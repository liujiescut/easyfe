package com.scut.easyfe.network.request;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 反馈接口
 * Created by jay on 16/4/8.
 */
public class RCallBackRequest extends RequestBase<JSONObject>{
    private int mType = Constants.Identifier.CALLBACK_NEED;
    private String mContent = "";

    public RCallBackRequest(int type, @NonNull String content) {
        this.mType = type;
        this.mContent = content;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_CALLBACK;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject param = new JSONObject();
        param.put("type", mType);
        param.put("content", mContent);

        return param;
    }

    @Override
    public HttpParams getQueryParams() {
        return null;
    }

    @Override
    public JSONObject parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        return null;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.POST;
    }
}
