package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.RCallBackRequest;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

import org.json.JSONObject;

/**
 * 反馈页面
 */
public class CallbackActivity extends BaseActivity {

    private int mCallbackType= Constants.Identifier.CALLBACK_APP;
    private String mHintText = "";
    private EditText mContentEditText;
    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_callback);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(null != intent){
            Bundle extras = intent.getExtras();
            if(null != extras){
                mCallbackType = extras.getInt(Constants.Key.CALLBACK_TYPE, Constants.Identifier.CALLBACK_APP);
                mHintText = extras.getString(Constants.Key.CALLBACK_HINT_TEXT, "");
            }
        }
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("反馈");
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_right)).setVisibility(View.VISIBLE);
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_right)).setText("提交");

        mContentEditText = OtherUtils.findViewById(this, R.id.callback_et_content);
        mContentEditText.setHint(mHintText);
    }

    public void onBackClick(View view){
        finish();
    }

    /**
     * 点击提交
     */
    public void onRightClick(View view){
        if(mContentEditText.getText().toString().length() == 0){
            toast("请输入内容");
            return;
        }

        RequestManager.get().execute(new RCallBackRequest(mCallbackType, mContentEditText.getText().toString()), new RequestListener<JSONObject>() {
            @Override
            public void onSuccess(RequestBase request, JSONObject result) {
                toast("提交成功");
                finish();
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
            }
        });
    }
}
