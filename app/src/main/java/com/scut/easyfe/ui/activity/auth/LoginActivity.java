package com.scut.easyfe.ui.activity.auth;

import android.os.Bundle;
import android.os.Handler;
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
import com.scut.easyfe.network.request.authentication.RGetSms;
import com.scut.easyfe.network.request.authentication.RLogin;
import com.scut.easyfe.ui.activity.MainActivity;
import com.scut.easyfe.ui.activity.ShowTextActivity;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;

import org.json.JSONObject;

/**
 * 登录注册页面
 *
 * @author jay
 */
public class LoginActivity extends BaseActivity {
    private EditText mPhoneEditText;
    private EditText mVerifyCodeEditText;
    private TextView mVerifyCodeTextView;
    private TextView mAgreeLicenseTextView;

    int secondLeft = Constants.Config.VERIFY_INTERVAL;


    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("登录注册");
        ((TextView) OtherUtils.findViewById(this, R.id.login_tv_service_hint)).setText(String.format("如忘记密码，请联系客服电话：%s", App.getServicePhone()));

        mPhoneEditText = OtherUtils.findViewById(this, R.id.login_et_phone);
        mVerifyCodeEditText = OtherUtils.findViewById(this, R.id.login_et_verify_code);
        mVerifyCodeTextView = OtherUtils.findViewById(this, R.id.login_tv_verify);
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
        String verifyCode = mVerifyCodeEditText.getText().toString();

        if (!validate(phone, verifyCode)) {
            return;
        }

        RequestManager.get().execute(new RLogin(phone, verifyCode), new RequestListener<User>() {
            @Override
            public void onSuccess(RequestBase request, User user) {
                user.set_id(user.get_id());
                user.setToken(user.getToken());
                user.setName(user.getName());
                user.setType(user.getType());
                user.setAvatar(user.getAvatar());

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
            toast("请输入验证码");
            return false;
        }

        return true;
    }

    static Handler handler = new Handler();

    /**
     * 显示倒计时第二次获取验证码
     */
    private void showTimer() {
        mVerifyCodeTextView.setClickable(false);
        mVerifyCodeTextView.setBackgroundResource(R.drawable.shape_login_verify_unable);
        mVerifyCodeTextView.setTextColor(getResources().getColor(R.color.text_area_bg));
        mVerifyCodeTextView.setText(secondLeft + " 秒后重试");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                secondLeft--;
                if (secondLeft == 0) {
                    secondLeft = Constants.Config.VERIFY_INTERVAL;
                    mVerifyCodeTextView.setBackgroundResource(R.drawable.selector_spread_reserve);
                    mVerifyCodeTextView.setTextColor(getResources().getColor(R.color.theme_color));
                    mVerifyCodeTextView.setText("获取验证码");
                    mVerifyCodeTextView.setClickable(true);
                } else {
                    mVerifyCodeTextView.setText(secondLeft + " 秒后重试");
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);

    }

    public void onVerifyClick(View v){
        String phone = mPhoneEditText.getText().toString();

        if(phone.length() != 11){
            toast("请输入有效手机号码");
            return;
        }

        showTimer();
        RequestManager.get().execute(new RGetSms(phone), new RequestListener<JSONObject>() {
            @Override
            public void onSuccess(RequestBase request, JSONObject result) {
                toast(result.optString("message"));
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast("发送验证码失败,请稍后重试");
            }
        });
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
