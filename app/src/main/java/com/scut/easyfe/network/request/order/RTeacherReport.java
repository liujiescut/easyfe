package com.scut.easyfe.network.request.order;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.kjFrame.http.HttpParams;
import com.scut.easyfe.network.kjFrame.http.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 家教完成课程并反馈
 * Created by jay on 16/6/11.
 */
public class RTeacherReport extends RequestBase<JSONObject>{
    private String mOrderId = "";
    private String mTeacherComment = "";
    private String mRightPercent = "";
    private int mEnthusiasm = 0;
    private int mGetLevel = 0;
    Order.TutorDetail mTutorDetail = new Order.TutorDetail();

    public RTeacherReport(String mOrderId, String mTeacherComment, String mRightPercent, int mEnthusiasm, int mGetLevel, Order.TutorDetail mTutorDetail) {
        this.mOrderId = mOrderId;
        this.mTeacherComment = mTeacherComment;
        this.mRightPercent = mRightPercent;
        this.mEnthusiasm = mEnthusiasm;
        this.mGetLevel = mGetLevel;
        this.mTutorDetail = mTutorDetail;
    }

    @Override
    public String getUrl() {
        return Constants.URL.URL_TEACHER_REPORT;
    }

    @Override
    public JSONObject getJsonParams() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("token", App.getUser().getToken());
        params.put("orderId", mOrderId);
        params.put("teacherComment", mTeacherComment);
        params.put("rightPercent", mRightPercent);
        params.put("enthusiasm", mEnthusiasm);
        params.put("getLevel", mGetLevel);
        params.put("nextTeachDetail", mTutorDetail.getAsJson());

        return params;
    }

    @Override
    public HttpParams getQueryParams() {
        return null;
    }

    @Override
    public JSONObject parseResultAsObject(JSONObject jsonObject) throws IOException, JSONException {
        return jsonObject;
    }

    @Override
    public int getMethod() {
        return Request.HttpMethod.POST;
    }
}
