package com.scut.easyfe.network.request.wallet;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Wallet;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;
import com.scut.easyfe.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 获取钱包信息  Todo: test
 * Created by jay on 16/4/7.
 */
public class RGetWalletInfo extends RequestBase<Wallet>{
    private String mToken = "";

    public RGetWalletInfo(@NonNull String token) {
        this.mToken = token;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_WALLET_INFO;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        HttpParams params = new HttpParams();
        params.putQueryParams("token", mToken);
        return params;
    }

    @Override
    public Wallet parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        Wallet wallet = new Wallet();
        try {
            wallet = mObjectMapper.readValue(jsonObject.toString(), Wallet.class);
        }catch (Exception e){
            LogUtils.i("Wallet  parseResultAsObject -> 解析出错");
            e.printStackTrace();
        }
        return wallet;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }
}
