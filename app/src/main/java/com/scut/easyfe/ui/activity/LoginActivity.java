package com.scut.easyfe.ui.activity;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.authentication.RLogin;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 登录注册页面
 *
 * @author jay
 */
public class LoginActivity extends BaseActivity {
    private EditText mPhoneEditText;
    private EditText mPasswordEditText;
    private TextView mAgreeLicenseTextView;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("登录注册");
        mPhoneEditText = OtherUtils.findViewById(this, R.id.login_et_phone);
        mPasswordEditText = OtherUtils.findViewById(this, R.id.login_et_password);
        mAgreeLicenseTextView = OtherUtils.findViewById(this, R.id.login_tv_agree_license);

        SpannableStringBuilder builder = new SpannableStringBuilder("点击登录,即表示您已同意用户协议");

        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_TITLE, "用户协议");
                bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_CONTENT, mResources.getString(R.string.user_protocol_content));
                redirectToActivity(mContext, ShowTextActivity.class, bundle);
            }
        }, 12, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(mResources.getColor(R.color.theme_color)), 12, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new UnderlineSpan(), 12, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mAgreeLicenseTextView.setText(builder);
        mAgreeLicenseTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void initListener() {

    }

    /**
     * 点击返回
     *
     * @param view 被点击视图
     */
    public void onBackClick(View view) {
        redirectToActivity(mContext, MainActivity.class);
        finish();
    }

    @Override
    public void onBackPressed() {
        onBackClick(null);
    }

    /**
     * 点击登录
     *
     * @param view 被点击视图
     */
    public void onLoginClick(View view) {
        String phone = mPhoneEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        if (!validate(phone, password)) {
            return;
        }

        RequestManager.get().execute(new RLogin(phone, password), new RequestListener<User>() {
            @Override
            public void onSuccess(RequestBase request, User user) {
                user.set_id(user.get_id());
                user.setToken(user.getToken());
                user.setName(user.getName());
                user.setType(user.getType());
                user.setAvatar(user.getAvatar());

                //Todo: 返回level?

                App.setUser(user);
                toast("登录成功");
                redirectToActivity(mContext, MainActivity.class);
                LogUtils.i(Constants.Tag.LOGIN_TAG, user.getToken());
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
            }
        });
    }

    private boolean validate(String phone, String password) {
        if (phone == null || phone.length() != 11) {
            toast("请输入有效手机号");
            return false;
        }

        if (password == null || password.length() == 0) {
            toast("密码不能为空");
            return false;
        }

        return true;
    }

    /**
     * 点击家长注册
     *
     * @param view 被点击视图
     */
    public void onParentRegisterClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key.TO_PARENT_REGISTER_ACTIVITY_TYPE, Constants.Identifier.TYPE_REGISTER);
        redirectToActivity(mContext, ParentRegisterActivity.class, bundle);
    }

    /**
     * 点击家教注册
     *
     * @param view 被点击视图
     */
    public void onTeacherRegisterClick(View view) {
        redirectToActivity(mContext, TeacherRegisterOneActivity.class);
    }

    /**
     * 点击协议
     *
     * @param view 被点击视图
     */
    public void onProtocolClick(View view) {
        toast("点击协议");
    }

}
