package com.scut.easyfe.network.request.order;

import com.fasterxml.jackson.databind.JavaType;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.FeedbackReport;
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
 * 获取反馈报告列表
 * Created by jay on 16/6/14.
 */
public class RGetFeedbackReport extends RequestBase<List<FeedbackReport>>{
    private String token = "";
    private int limit = Constants.DefaultValue.DEFAULT_LOAD_COUNT;
    private int skip = 0;
    private String sort = Constants.Identifier.SORT_BY_NAME;



    public RGetFeedbackReport(int skip, String sort) {
        this(Constants.DefaultValue.DEFAULT_LOAD_COUNT, skip, sort);
    }

    public RGetFeedbackReport(int limit, int skip, String sort) {
        this.limit = limit;
        this.skip = skip;
        this.sort = sort;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_FEEDBACK_REPORT;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        HttpParams params = new HttpParams();
        params.putQueryParams("token", App.getUser().getToken());
        params.putQueryParams("limit", limit);
        params.putQueryParams("skip", skip);
        params.putQueryParams("sort", sort);

        return params;
    }

    @Override
    public List<FeedbackReport> parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {

        List<FeedbackReport> result = new ArrayList<>();
        JavaType javaType = mObjectMapper.getTypeFactory().constructParametricType(List.class, FeedbackReport.class);
        try {
            JSONArray specialOrders = jsonObject.optJSONArray("orders");
            if(null != specialOrders){
                /** 将返回的JsonArray转化为List<FeedbackReport> */
                result = mObjectMapper.readValue(specialOrders.toString(), javaType);
            }
        } catch (IOException e) {
            LogUtils.i("Json转换为List<FeedbackReport>失败!");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
