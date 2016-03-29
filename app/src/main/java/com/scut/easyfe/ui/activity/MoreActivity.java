package com.scut.easyfe.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;

/**
 * 更多页面
 * @author jay
 */
public class MoreActivity extends BaseActivity {

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_more);
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.titlebar_tv_title)).setText("更多");
    }

    public void onBackClick(View view){
        finish();
    }

    public void onHelpClick(View view){
        redirectToActivity(mContext, ProblemAndHelpActivity.class);
    }

    public void onUserProtocolClick(View view){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_TITLE, "用户条款");
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_CONTENT, mResources.getString(R.string.user_protocol_content));
        redirectToActivity(mContext, ShowTextActivity.class, bundle);
    }

    public void onAboutUsClick(View view){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_TITLE, "关于我们");
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_CONTENT, mResources.getString(R.string.about_us_content));
        redirectToActivity(mContext, ShowTextActivity.class, bundle);
    }

    public void onCallbackClick(View view){
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key.CALLBACK_TYPE, Constants.Identifier.CALLBACK_APP);
        redirectToActivity(mContext, CallbackActivity.class, bundle);
    }
}
