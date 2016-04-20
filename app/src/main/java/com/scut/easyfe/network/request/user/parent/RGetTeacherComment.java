package com.scut.easyfe.network.request.user.parent;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.JavaType;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Comment;
import com.scut.easyfe.entity.Message;
import com.scut.easyfe.entity.order.Order;
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
 * 获取订单详情
 * Created by jay on 16/4/7.
 */
public class RGetTeacherComment extends RequestBase<List<Comment>> {
    private String mToken = "";
    private String mTeacherId = "";

    public RGetTeacherComment(@NonNull String token, @NonNull String teacherId) {
        this.mToken = token;
        this.mTeacherId = teacherId;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_TEACHER_COMMENT;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        HttpParams params = new HttpParams();
        params.putQueryParams("token", mToken);
        params.putQueryParams("teacherId", mTeacherId);
        return params;
    }

    @Override
    public List<Comment> parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        List<Comment> comments = new ArrayList<>();
        JavaType javaType = mObjectMapper.getTypeFactory().constructParametricType(List.class, Comment.class);
        try {
            JSONArray messages = jsonObject.optJSONArray("messages");
            if(null != messages){
                /** 将返回的地址JsonArray转化为List<Comment> */
                comments = mObjectMapper.readValue(messages.toString(), javaType);
            }
        } catch (IOException e) {
            LogUtils.i("Json转换为List<Comment>失败!");
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
