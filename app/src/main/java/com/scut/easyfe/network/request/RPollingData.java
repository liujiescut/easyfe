package com.scut.easyfe.network.request;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.PollingData;
import com.scut.easyfe.entity.UserLevel;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;
import com.scut.easyfe.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 轮询接口
 * Created by jay on 16/6/10.
 */
public class RPollingData extends RequestBase<PollingData> {

    @Override
    public String getUrl() {
        return Constants.URL.URL_POLLING_DATA;
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
    public PollingData parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        PollingData data = new PollingData();

        try {
            data = mObjectMapper.readValue(jsonObject.toString(), PollingData.class);
            data.setDataString(jsonObject.toString());

        }catch (Exception e){
            LogUtils.i("PollingData  parseResultAsObject -> 解析出错");
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
