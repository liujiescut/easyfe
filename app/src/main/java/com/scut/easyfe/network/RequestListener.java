package com.scut.easyfe.network;


public interface RequestListener<T> {

    int ERR_JSON_ONSUCCESS = -0xA;
    String MSG_JSON_ONSUCCESS_BODY = "请求返回成功,但body数据格式有误";
    String MSG_JSON_ONSUCCESS_DATA = "请求返回成功,但data数据格式有误";

    int ERR_JSON_ONFAIL = -0xB;
    String MSG_JSON_ONFAIL = "请求返回失败,请检查网络连接";


    void onSuccess(RequestBase request, T result);
    void onFailed(RequestBase request, int errorCode, String errorMsg);
}
