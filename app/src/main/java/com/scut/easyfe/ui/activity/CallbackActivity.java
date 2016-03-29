package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 反馈页面
 */
public class CallbackActivity extends BaseActivity {

    private int mCallbackType= Constants.Identifier.CALLBACK_APP;
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
            }
        }
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.titlebar_tv_title)).setText("反馈");
        findViewById(R.id.titlebar_tv_right).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.titlebar_tv_right)).setText("提交");

        mContentEditText = OtherUtils.findViewById(this, R.id.callback_et_content);
    }

    public void onBackClick(View view){
        finish();
    }

    /**
     * 点击提交
     */
    public void onRightClick(View view){
        toast("我提交了喔");
    }
}
