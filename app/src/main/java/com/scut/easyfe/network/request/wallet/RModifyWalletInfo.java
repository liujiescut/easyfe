package com.scut.easyfe.network.request.wallet;

import android.support.annotation.NonNull;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Wallet;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 修改钱包信息
 * Created by jay on 16/4/7.
 */
public class RModifyWalletInfo extends RequestBase<JSONObject>{
    private String mToken = "";
    private Wallet mWallet = new Wallet();

    public RModifyWalletInfo(@NonNull String token, @NonNull Wallet wallet) {
        this.mToken = token;
        this.mWallet = wallet;
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
        params.putQueryParams("ali", mWallet.getAli());
        params.putQueryParams("wechat", mWallet.getWechat());
        params.putQueryParams("bank", mWallet.getBank().getBankJson().toString());
        return params;
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
