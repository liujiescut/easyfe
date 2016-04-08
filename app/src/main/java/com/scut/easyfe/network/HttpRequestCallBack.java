package com.scut.easyfe.network;


import android.widget.Toast;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.kjFrame.http.HttpCallBack;
import com.scut.easyfe.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class HttpRequestCallBack<T> extends HttpCallBack {

    RequestListener<T> listener;
    RequestBase<T> requestBase;

    public HttpRequestCallBack(RequestBase<T> requestBase, RequestListener<T> listener) {
        this.listener = listener;
        this.requestBase = requestBase;
    }

    @Override
    public void onSuccess(String responseString) {
        LogUtils.i(RequestManager.TAG, requestBase.getClass().getSimpleName() + ": requestBase onSuccess, result:" + responseString);
        JSONObject json;
        try {
            json = new JSONObject(responseString);
        } catch (JSONException e) {
            listener.onFailed(requestBase, RequestListener.ERR_JSON_ONSUCCESS, RequestListener.MSG_JSON_ONSUCCESS_BODY);
            return;
        }
        String result = json.optString("result");
        if (!"fail".equals(result)) {
            if (listener != null) {
                String data = json.optString("data","");
                if(data.startsWith("{")) {
                    try {
                        listener.onSuccess(requestBase, requestBase.parseResultAsObject(json.optJSONObject("data")));
                    } catch (JSONException jsonE) {
                        listener.onFailed(requestBase, RequestListener.ERR_JSON_ONSUCCESS, jsonE.getMessage());
                    } catch (IOException ioE) {
                        listener.onFailed(requestBase, RequestListener.ERR_JSON_ONSUCCESS, ioE.getMessage());
                    }
                } else {
                    listener.onFailed(requestBase, RequestListener.ERR_JSON_ONSUCCESS, RequestListener.MSG_JSON_ONSUCCESS_DATA);
                }
            }
        } else {
            if (listener != null) {
                listener.onFailed(requestBase, json.optInt("code"), json.optString("message"));
            }
        }
    }

    @Override
    public void onFailure(int errorNo, String strMsg , String responseString) {
        LogUtils.i(RequestManager.TAG , requestBase.getClass().getSimpleName() + ": requestBase onFailure code -> " + errorNo + ",body ->  " + responseString);
        JSONObject json;
        String errMsg;
        try {
            json = new JSONObject(responseString);
            errMsg = json.optString("message");
        } catch (JSONException e) {
            errorNo = RequestListener.ERR_JSON_ONFAIL;
            errMsg = RequestListener.MSG_JSON_ONFAIL;
        }

        //统一捕捉的处理
        switch (errorNo){
            //Todo 统一捕捉的处理
//            case Constant.CODE.ERRCODE_TOKEN_EXPIRED:
//                App.getInstance().tokenExpired();
//                return;
        }

        if (listener != null) {
            listener.onFailed(requestBase, errorNo, errMsg);
        }

    }

    @Override
    public void onFinish() {
        listener = null;
        requestBase = null;
    }
}
