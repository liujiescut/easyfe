package com.scut.easyfe.network.kjFrame;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.scut.easyfe.ui.base.BaseActivity;


/**
 * Activity's framework,the developer shouldn't extends it<br>
 *
 * <b>创建时间</b> 2014-3-1 <br>
 * <b>最后修改时间</b> 2014-10-17<br>
 *
 * @author kymjs (https://github.com/kymjs)
 * @version 1.8
 */
public abstract class FrameActivity extends BaseActivity implements
        OnClickListener, I_BroadcastReg, I_KJActivity, I_SkipActivity {

    protected KJFragment currentKJFragment;
    protected SupportFragment currentSupportFragment;


    /**
     * 如果调用了initDataFromThread()，则当数据初始化完成后将回调该方法。
     */
    protected void threadDataInited() {}

    /**
     * 在线程中初始化数据，注意不能在这里执行UI操作
     */
    @Override
    public void initDataFromThread() {
    }

    @Override
    public void initData() {}

    @Override
    public void initWidget() {}

    // 仅仅是为了代码整洁点
    private void initializer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                initDataFromThread();
            }
        }).start();
        initData();
        initWidget();
    }

    /** listened widget's click method */
    @Override
    public void widgetClick(View v) {}

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    protected <T extends View> T bindView(int id) {
        return (T) findViewById(id);
    }

    protected <T extends View> T bindView(int id, boolean click) {
        T view = (T) findViewById(id);
        if (click) {
            view.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void registerBroadcast() {}

    @Override
    public void unRegisterBroadcast() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(); // 必须放在annotate之前调用
        AnnotateUtil.initBindView(this);
        initializer();
        registerBroadcast();
    }

    @Override
    protected void onDestroy() {
        unRegisterBroadcast();
        super.onDestroy();
    }

    /**
     * 用Fragment替换视图
     *
     * @param resView
     *            将要被替换掉的视图
     * @param targetFragment
     *            用来替换的Fragment
     */
    public void changeFragment(int resView, KJFragment targetFragment) {
        if (targetFragment.equals(currentKJFragment)) {
            return;
        }
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass()
                    .getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }
        if (currentKJFragment != null && currentKJFragment.isVisible()) {
            transaction.hide(currentKJFragment);
        }
        currentKJFragment = targetFragment;
        transaction.commit();
    }

    /**
     * 用Fragment替换视图
     *
     * @param resView
     *            将要被替换掉的视图
     * @param targetFragment
     *            用来替换的Fragment
     */
    public void changeFragment(int resView, SupportFragment targetFragment) {
        if (targetFragment.equals(currentSupportFragment)) {
            return;
        }
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass()
                    .getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }
        if (currentSupportFragment != null
                && currentSupportFragment.isVisible()) {
            transaction.hide(currentSupportFragment);
        }
        currentSupportFragment = targetFragment;
        transaction.commit();
    }
}

