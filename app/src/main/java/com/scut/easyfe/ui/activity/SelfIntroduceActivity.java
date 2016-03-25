package com.scut.easyfe.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 个人介绍页面
 * @author jay
 */
public class SelfIntroduceActivity extends BaseActivity {
    private EditText mIntroduceEditText;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_self_introduce);
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.titlebar_tv_title)).setText("家教注册 - 个人介绍");

        mIntroduceEditText = OtherUtils.findViewById(this, R.id.self_introduce_et_content);
    }

    /**
     * 点击返回
     */
    public void onBackClick(View view){
        finish();
    }

    /**
     * 点击保存并进入下一页
     */
    public void onSaveClick(View view){
        //Todo 具体保存逻辑
    }
}
