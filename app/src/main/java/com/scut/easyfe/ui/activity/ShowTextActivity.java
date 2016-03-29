package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;

/**
 * 显示文本的页面
 * @author jay
 */
public class ShowTextActivity extends BaseActivity {

    private String mTitle = "";
    private String mContent = "";


    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_show_text);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(null != intent){
            Bundle extras = intent.getExtras();
            if(null != extras){
                mTitle = extras.getString(Constants.Key.SHOW_TEXT_ACTIVITY_TITLE, "");
                mContent = extras.getString(Constants.Key.SHOW_TEXT_ACTIVITY_CONTENT, "");
            }
        }
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.titlebar_tv_title)).setText(mTitle);
        ((TextView)findViewById(R.id.show_text_content)).setText(mContent);
    }

    public void onBackClick(View view){
        finish();
    }
}
