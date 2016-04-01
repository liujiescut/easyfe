package com.scut.easyfe.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 登录注册页面
 * @author jay
 */
public class LoginActivity extends BaseActivity {
    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("登录注册");
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
        App.getUser().setHasLogin(true);
        toast("登录成功");
    }

    /**
     * 点击家长注册
     * @param view 被点击视图
     */
    public void onParentRegisterClick(View view){
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key.TO_PARENT_REGISTER_ACTIVITY_TYPE, Constants.Identifier.TYPE_REGISTER);
                    redirectToActivity(mContext, ParentRegisterActivity.class, bundle);
        redirectToActivity(mContext, ParentRegisterActivity.class, bundle);
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
