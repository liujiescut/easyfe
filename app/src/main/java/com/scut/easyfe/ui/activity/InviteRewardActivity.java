package com.scut.easyfe.ui.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.umeng.socialize.UMShareAPI;

public class InviteRewardActivity extends BaseActivity {
    private TextView mInviteParentTextView;
    private TextView mInviteTeacherTextView;
    private TextView mActivityDescriptionTextView;
    private View mActivityDescriptionView;
    private TextView mShareLinkTextView;

    private IWeiboShareAPI mWeiboShareApi = null;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_invite_reward);
    }

    @Override
    protected void initData() {
        mWeiboShareApi = WeiboShareSDK.createWeiboAPI(this.getApplicationContext(), Constants.Data.WEIBO_APP_KEY);
        mWeiboShareApi.registerApp(); // 将应用注册到微博客户端
    }

    @Override
    protected void initView() {
        mInviteParentTextView = OtherUtils.findViewById(this, R.id.invite_reward_tv_parent);
        mInviteTeacherTextView = OtherUtils.findViewById(this, R.id.invite_reward_tv_teacher);
        mActivityDescriptionTextView = OtherUtils.findViewById(this, R.id.invite_reward_tv_description);
        mActivityDescriptionView = OtherUtils.findViewById(this, R.id.invite_reward_ll_container);
        mShareLinkTextView = OtherUtils.findViewById(this, R.id.invite_reward_tv_link);

        SpannableStringBuilder builder = new SpannableStringBuilder("活动说明");

        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                YoYo.with(Techniques.SlideInUp).duration(300).playOn(mActivityDescriptionView);
                mActivityDescriptionView.setVisibility(View.VISIBLE);
            }
        }, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(mResources.getColor(R.color.invite_reward_text_color)), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new UnderlineSpan(), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mActivityDescriptionTextView.setText(builder);
        mActivityDescriptionTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void initListener() {
        mActivityDescriptionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.SlideOutDown).duration(300).playOn(mActivityDescriptionView);
                mActivityDescriptionView.setVisibility(View.GONE);
            }
        });

        mShareLinkTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toast("已复制到剪切版");
                ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(mShareLinkTextView.getText().toString());
                return true;
            }
        });
    }

    public void onInviteParentClick(View view) {
        mInviteTeacherTextView.setBackgroundResource(R.color.transparent);
        mInviteParentTextView.setBackgroundResource(R.drawable.shape_invite_type_selected);
        mInviteTeacherTextView.setTextColor(getResources().getColor(R.color.invite_reward_text_color));
        mInviteParentTextView.setTextColor(getResources().getColor(R.color.invite_reward_theme_color));
    }

    public void onInviteTeacherClick(View view) {
        mInviteParentTextView.setBackgroundResource(R.color.transparent);
        mInviteTeacherTextView.setBackgroundResource(R.drawable.shape_invite_type_selected);
        mInviteParentTextView.setTextColor(getResources().getColor(R.color.invite_reward_text_color));
        mInviteTeacherTextView.setTextColor(getResources().getColor(R.color.invite_reward_theme_color));
    }

    public void shareToQQ(View view) {
        toast("分享");
    }

    public void shareToWechat(View view) {
        toast("分享");
    }

    public void shareToQQArea(View view) {
        toast("分享");
    }

    public void shareToFriends(View view) {
        toast("分享");
    }

    public void shareToWeibo(View view) {
//        new Sh

        /**
         * 微博分享
         *
         * @param bitmap 分享图片的Bitmap
         */
        if (!OtherUtils.isAppInstalled(this, Constants.Data.WEIBO_PACKAGE_NAME, Constants.Config.TO_MARKET)) {
            toast("微博未安装");
            finish();
            return;
        }

        WeiboMessage weiboMessage = new WeiboMessage();

//        if (bitmap != null) {
//            ImageObject weiboImage = new ImageObject();
//            weiboImage.setImageObject(bitmap);
//            weiboMessage.mediaObject = weiboImage;
//        } else {
//            String text = Constant.Config.SHARE_TEXT_PREFIX + mRoom.getCode();
//            TextObject shareText = new TextObject();
//            shareText.text = text;
//            weiboMessage.mediaObject = shareText;
//        }

        String text = Constants.DefaultValue.DEFAULT_SHARE_LINK;
        TextObject shareText = new TextObject();
        shareText.text = text;
        weiboMessage.mediaObject = shareText;

        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;

        boolean flag_weibo = mWeiboShareApi.sendRequest(InviteRewardActivity.this, request);

        try {
            synchronized (mWeiboShareApi) {
                mWeiboShareApi.wait(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!flag_weibo) {
            toast("分享失败");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void onBackClick(View view) {
        finish();
    }
}
