package com.scut.easyfe.ui.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;

public class InviteRewardActivity extends BaseActivity {
    private TextView mInviteParentTextView;
    private TextView mInviteTeacherTextView;
    private TextView mActivityDescriptionTextView;
    private View mActivityDescriptionView;
    private TextView mShareLinkTextView;

    private IWeiboShareAPI mWeiboShareApi = null;
    private IWXAPI mWechatShareApi;
    private Tencent mTencent;


    private TencentShareUIListener tencentShareUIListener = new TencentShareUIListener();

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_invite_reward);
    }

    @Override
    protected void initData() {
        mWeiboShareApi = WeiboShareSDK.createWeiboAPI(this.getApplicationContext(), Constants.Data.WEIBO_APP_KEY);
        mWeiboShareApi.registerApp(); // 将应用注册到微博客户端
        mWechatShareApi = WXAPIFactory.createWXAPI(this.getApplicationContext(), Constants.Data.WECHAT_APP_ID, true);
        mWechatShareApi.registerApp(Constants.Data.WECHAT_APP_ID);
        mTencent = Tencent.createInstance(Constants.Data.QQ_APP_ID, this.getApplicationContext());
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("邀请有奖");
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

    public void shareToQzone(View view) {
        Bundle params = new Bundle();
        //分享类型
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, getString(R.string.app_name));
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, getString(R.string.share_details));
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, Constants.DefaultValue.DEFAULT_SHARE_LINK);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, new ArrayList<String>());  //这行不能删,不然空间SDK会出Bug
        mTencent.shareToQzone(this, params, tencentShareUIListener);
    }

    public void shareToQQ(View view) {
        if (!OtherUtils.isAppInstalled(this, Constants.Data.QQ_PACKAGE_NAME, Constants.Config.TO_MARKET)) {
            toast("QQ未安装");
            finish();
            return;
        }

//        if (bitmap != null) {
//            File file = ImageUtils.saveBitmapToFile(bitmap, "temp.jpg");
//            if (null != file && file.exists()) {
//                LogUtils.i("code image filePath : " + file.getAbsolutePath());
//                LogUtils.i("code image filePath : " + file.getPath());
//                Bundle bundle = new Bundle();
//                bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//                bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, file.getAbsolutePath());
//                mTencent.shareToQQ(this, bundle, tencentShareUIListener);
//            } else {
//                showToast("QQ分享失败");
//            }
//        } else {
        Bundle bundle = new Bundle();
//        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, mRoom.getAvatar());
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, Constants.DefaultValue.DEFAULT_SHARE_LINK);
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, getString(R.string.app_name));
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, getString(R.string.share_details));
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "back to" + Constants.Config.APP_NAME);
        bundle.putString(QQShare.SHARE_TO_QQ_SITE, Constants.Config.APP_NAME + Constants.Data.QQ_APP_ID);
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        mTencent.shareToQQ(this, bundle, tencentShareUIListener);
    }

    public void shareToWechat(View view) {
        doShareToWechat(false);
    }

    public void shareToFriends(View view) {
        doShareToWechat(true);
    }

    /**
     * 分享到微信
     *
     * @param toFriends 是否为朋友圈
     */
    private void doShareToWechat(boolean toFriends) {
        /**
         * wechat
         * 先判断软件是否安装
         */
        if (!OtherUtils.isAppInstalled(this, Constants.Data.WECHAT_PACKAGE_NAME, Constants.Config.TO_MARKET)) {
            toast("微信未安装");
            finish();
            return;
        }

        String url = Constants.DefaultValue.DEFAULT_SHARE_LINK;
        WXWebpageObject webPageObject = new WXWebpageObject();
        webPageObject.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webPageObject);
        msg.title = getString(R.string.app_name);
        msg.description = getString(R.string.share_details);

//        if (android.os.Build.VERSION.SDK_INT > 12 && roomAvatarBitmap.getByteCount() < 57600)
//            msg.setThumbImage(roomAvatarBitmap);
//        else {
//            msg.setThumbImage(Bitmap.createScaledBitmap(roomAvatarBitmap, 120, 120, true));
//        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;

        req.scene = toFriends ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

        if (!mWechatShareApi.sendReq(req)) {
            toast("分享失败");
        }
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

    /**
     * qq分享回调
     */
    static class TencentShareUIListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            Toast.makeText(App.get(), "分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(App.get(), "分享失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {

        }
    }
}
