package com.scut.easyfe.network;

import android.text.TextUtils;

import com.scut.easyfe.network.kjFrame.KJHttp;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;
import com.scut.easyfe.utils.LogUtils;

/**
 * 发起请求的管理类
 * <p>
 * Created by janbean on 15/11/17.
 */
public class CXRM {
    public static final String TAG = "cxtag";

    private static class SingletonHolder {
        private static final CXRM sRequestManager = new CXRM();
    }

    public static CXRM get() {
        return SingletonHolder.sRequestManager;
    }

    private KJHttp mKJHttp = new KJHttp();

    public static KJHttp getKJHttp() {
        return get().mKJHttp;
    }

    public <T> void execute(CXRequestBase<T> cxRequest, CXRequestListener<T> listener) {
        LogUtils.i(cxRequest.getClass().getSimpleName() + ": cxRequest start");

        if (TextUtils.isEmpty(cxRequest.getUrl())) {
            LogUtils.e("url can not be empty!");
            return;
        }

        HttpParams httpParams = cxRequest.getHttpParams();
        if (httpParams != null && httpParams.getJsonParams() != null) {
            LogUtils.i(TAG, "CXRequest.getJsonParams  ->" + httpParams.getJsonParams());
            // 如果httpParams设置了jsonParams，就使用json
            if (cxRequest.getMethod() == Request.HttpMethod.GET) {
                getKJHttp().json(cxRequest.getMethod(), cxRequest.getUrl(), httpParams, new CXHttpRequestCallBack<>(cxRequest, listener));
            } else {
                getKJHttp().json(cxRequest.getMethod(), cxRequest.getUrl() + httpParams.getUrlParams(), httpParams, new CXHttpRequestCallBack<>(cxRequest, listener));
            }
        } else {
            String url = cxRequest.getUrl();
            if (httpParams != null) {
                url += httpParams.getUrlParams();
            }
            getKJHttp().form(cxRequest.getMethod(), url, httpParams, new CXHttpRequestCallBack<T>(cxRequest, listener));
        }


    }


}
