package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.base.BaseFragment;
import com.scut.easyfe.ui.customView.CircleImageView;
import com.scut.easyfe.ui.fragment.HomeFragment;
import com.scut.easyfe.utils.ImageUtils;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 程序主界面
 * @author jay
 */
public class MainActivity extends BaseActivity {
    private static final int FRAGMENT_HOME = 0;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mLeftDrawer;
    private CircleImageView mAvatarImageView;
    private TextView mNameTextView;
    private Map<Integer, BaseFragment> mFragments = new HashMap<>();

    @Override
    protected void onResume() {
        super.onResume();

        if(null != mNameTextView && null != mAvatarImageView) {
            if (App.getUser().hasLogin()) {
                mNameTextView.setText(App.getUser().getName());
                ImageUtils.displayImage(App.getUser().getAvatar(), mAvatarImageView);
            } else {
                mNameTextView.setText("登录/注册");
                mAvatarImageView.setImageResource(R.mipmap.default_avatar);
            }
        }
    }

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        mDrawerLayout = OtherUtils.findViewById(this, R.id.drawer_layout);
        mLeftDrawer = OtherUtils.findViewById(this, R.id.drawer);
        mAvatarImageView = OtherUtils.findViewById(this, R.id.left_drawer_civ_avatar);
        mNameTextView = OtherUtils.findViewById(this, R.id.left_drawer_tv_name);
        mFragments.put(FRAGMENT_HOME, new HomeFragment());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, mFragments.get(FRAGMENT_HOME))
                .commit();
    }

    @Override
    protected void initListener() {
    }

    /**
     * 左上角个人按钮点击侧拉
     *
     * @param view 被点击View
     */
    public void onPersonImageClick(View view) {
        if (null != mDrawerLayout) {
            mDrawerLayout.openDrawer(mLeftDrawer);
        }
    }

    /**
     * 点击我的订单
     */
    public void onMyOrderClick(View view){
        redirectToActivity(mContext, MyOrderActivity.class);
    }

    /**
     * 点击头像
     * @param view 被点击视图
     */
    public void onAvatarClick(View view){
        if(App.getUser().hasLogin()) {
            if (App.getUser().getType() == Constants.Identifier.USER_PARENT) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.Key.TO_PARENT_REGISTER_ACTIVITY_TYPE, Constants.Identifier.TYPE_MODIFY);
                redirectToActivity(mContext, ParentRegisterActivity.class, bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.Key.TO_TEACHER_REGISTER_ONE_ACTIVITY_TYPE, Constants.Identifier.TYPE_MODIFY);
                redirectToActivity(mContext, TeacherRegisterOneActivity.class, bundle);
            }
        }else{
            redirectToActivity(mContext, LoginActivity.class);
        }
    }

    /**
     * 点击我的钱包
     */
    public void onPocketClick(View view){
        redirectToActivity(mContext, PocketActivity.class);
    }

    /**
     * 点击家教信息维护
     */
    public void onTeacherMsgManageClick(View view){
        Bundle extras = new Bundle();
        extras.putBoolean(Constants.Key.IS_REGISTER, false);
        redirectToActivity(this, TeacherRegisterTwoActivity.class, extras);
    }

    /**
     * 点击推广(特价订单)
     */
    public void onSpreadClick(View view){
        redirectToActivity(mContext, PublishSpreadActivity.class);
    }

    /**
     * 点击消息中心
     */
    public void onMessageCenterClick(View view){
        redirectToActivity(mContext, MessageCenterActivity.class);
    }

    /**
     * 点击联系我们
     */
    public void onContactUsClick(View view){
        redirectToActivity(mContext, ContactUsActivity.class);
    }

    /**
     * 点击更多
     */
    public void onMoreClick(View view){
        redirectToActivity(mContext, MoreActivity.class);
    }
}
