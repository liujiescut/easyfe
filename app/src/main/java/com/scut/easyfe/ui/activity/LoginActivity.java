package com.scut.easyfe.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.base.BaseActivity;

/**
 * 登录注册页面
 */
public class LoginActivity extends BaseActivity {
    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.titlebar_tv_title)).setText("登录注册");
    }

    @Override
    protected void initListener() {

    }

    /**
     * 点击返回
     * @param view 被点击视图
     */
    public void onBackClick(View view){
        toast("点击返回");
    }

    /**
     * 点击登录
     * @param view 被点击视图
     */
    public void onLoginClick(View view){
        toast("点击登录");
    }

    /**
     * 点击家长注册
     * @param view 被点击视图
     */
    public void onParentRegisterClick(View view){
        redirectToActivity(mContext, ParentRegisterActivity.class);
    }

    /**
     * 点击家教注册
     * @param view 被点击视图
     */
    public void onTeacherRegisterClick(View view) {
        redirectToActivity(mContext, TeacherRegisterOneActivity.class);
    }

    /**
     * 点击协议
     * @param view 被点击视图
     */
    public void onProtocolClick(View view){
        toast("点击协议");
    }

}
