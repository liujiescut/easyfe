package com.scut.easyfe.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scut.easyfe.network.kjFrame.http.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * 关于token的传入方式 , 若某请求子类的传入参数需要有token . 无需在代码外传入 ， 直接在子类
 * 的参数拼接处调用 App.getToken() 传入,在需要判断用户是否已经登录的地方请先做判断
 * @param <T>
 */

public abstract class RequestBase<T> {


    /**
     * 子类使用此来解析 ， json结果格式复杂的话也可以自己写解析方式
     */
    public static ObjectMapper mObjectMapper = new ObjectMapper();

    abstract public String getUrl();
    /**
     * 组装参数，返回请求参数
     * @return
     */
    public HttpParams getHttpParams(){
        HttpParams httpParams = getQueryParams();
        if(httpParams == null){
            httpParams = new HttpParams();
        }
        try {
            JSONObject jsonParams = getJsonParams();
            if (jsonParams != null) {
                httpParams.putJsonParams(jsonParams.toString());
            }
        } catch (JSONException e) {

        }
        return httpParams;
    }


    /**
     * 封装json请求需要的参数,跟GET与POST无关
     * @return
     */
    abstract public JSONObject getJsonParams() throws JSONException;

    /**
     * 封装url请求参数需要的query参数
     * @return
     */
    abstract public HttpParams getQueryParams();


    /**
     * 解析jsonObject为需要的object，用于之后返回给listener处理
     * result不为error才会调用
     * @param jsonObject 服务端返回的json数据
     * @return
     */
    abstract public T parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException;


    /**
     *
     * @return POST or GET
     */
    abstract public int getMethod();
}
