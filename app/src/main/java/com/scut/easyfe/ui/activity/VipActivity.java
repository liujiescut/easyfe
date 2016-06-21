package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.fragment.VipFragment;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 会员活动页面 我的会员活动页面
 * @author jay
 */
public class VipActivity extends BaseActivity {
    private boolean mIsMyVipEvent = false;
    private VipFragment mFragment;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_vip);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = intent.getExtras();
            if (null != bundle) {
                mIsMyVipEvent = bundle.getBoolean(Constants.Key.IS_MY_VIP_ACTIVITY, false);
                if(bundle.getBoolean(Constants.Key.NEED_REFRESH, false)){
                    refresh();
                }
            }
        }
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = intent.getExtras();
            if (null != bundle) {
                mIsMyVipEvent = bundle.getBoolean(Constants.Key.IS_MY_VIP_ACTIVITY, false);
            }
        }
    }

    @Override
    protected void initView() {
        initTitle();

        mFragment = new VipFragment();
        mFragment.setIsMyVipEvent(mIsMyVipEvent);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.vip_fl_container, mFragment)
                .commit();
    }

    private void initTitle(){
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).
                setText(mIsMyVipEvent ? "我的会员活动" : "会员活动");
    }


    public void refresh(){
        initTitle();
        if (null != mFragment) {
            mFragment.refresh();
        }
    }

    public void setIsMyVipEvent(boolean mIsMyVipEvent) {
        this.mIsMyVipEvent = mIsMyVipEvent;
        if (null != mFragment) {
            mFragment.setIsMyVipEvent(mIsMyVipEvent);
        }
    }

    public void onBackClick(View view){
        finish();
    }
}
