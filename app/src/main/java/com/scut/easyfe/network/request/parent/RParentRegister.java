package com.scut.easyfe.network.request.parent;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.test.User;
import com.scut.easyfe.network.CXRequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;
import com.scut.easyfe.utils.LogUtils;

import org.json.JSONObject;

public class RParentRegister extends CXRequestBase<User> {
    private String phone;
    private String password;

    public RParentRegister(String phone, String password){
        this.phone = phone;
        this.password = password;
    }


    @Override
    public int getMethod() {
        return Request.HttpMethod.GET;
    }


    @Override
    public String getUrl() {
        return Constants.URL.URL_PARENT_REGISTER;
    }

    @Override
    public JSONObject getJsonParams() {
        return null;
    }

    @Override
    public HttpParams getQueryParams() {
        return null;
    }

    @Override
    public User parseResultAsObject(JSONObject jsonObject) {
        //此处会返回token , 需要缓存个人信息 , 并执行登录
        User cxUser = new User();
        try {
            cxUser = mObjectMapper.readValue(jsonObject.toString(),User.class);
        }catch (Exception e){
            LogUtils.i("CXUser  parseResultAsObject -> 解析出错");
            e.printStackTrace();
        }
        return cxUser;
    }

}
