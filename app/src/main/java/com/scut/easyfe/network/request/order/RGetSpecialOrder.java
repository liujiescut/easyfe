package com.scut.easyfe.network.request.order;

import com.fasterxml.jackson.databind.JavaType;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.SpecialOrder;
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
 * 获取特价订单
 * Created by jay on 16/4/11.
 */
public class RGetSpecialOrder extends RequestBase<List<SpecialOrder>>{
    private int mLimit = Constants.DefaultValue.DEFAULT_LOAD_COUNT;
    private int mSkip = -1;

    public RGetSpecialOrder(int mSkip) {
        this.mSkip = mSkip;
    }

    public RGetSpecialOrder(int mLimit, int mSkip) {
        this.mLimit = mLimit;
        this.mSkip = mSkip;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_GET_SPECIAL_ORDER;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        HttpParams params = new HttpParams();
        params.putQueryParams("skip", mSkip);
        params.putQueryParams("limit", mLimit);
        return params;
    }

    @Override
    public List<SpecialOrder> parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        List<SpecialOrder> result = new ArrayList<>();
        JavaType javaType = mObjectMapper.getTypeFactory().constructParametricType(List.class, SpecialOrder.class);
        try {
            JSONArray specialOrders = jsonObject.optJSONArray("orders");
            if(null != specialOrders){
                /** 将返回的地址JsonArray转化为List<SpecialOrder> */
                result = mObjectMapper.readValue(specialOrders.toString(), javaType);
            }
        } catch (IOException e) {
            LogUtils.i("Json转换为List<SpecialOrder>失败!");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
