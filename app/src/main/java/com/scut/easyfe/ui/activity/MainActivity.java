package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.base.BaseFragment;
import com.scut.easyfe.ui.fragment.HomeFragment;
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
    private Map<Integer, BaseFragment> mFragments = new HashMap<>();

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        mDrawerLayout = OtherUtils.findViewById(this, R.id.drawer_layout);
        mLeftDrawer = OtherUtils.findViewById(this, R.id.drawer);
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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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
}
