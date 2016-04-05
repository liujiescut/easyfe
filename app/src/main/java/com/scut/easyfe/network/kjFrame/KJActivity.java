package com.scut.easyfe.network.kjFrame;

/**
 * Created by WilliamHao on 15/8/29.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.scut.easyfe.network.kjFrame.utils.KJLoger;
import com.scut.easyfe.utils.LogUtils;

/**
 * @author kymjs (https://github.com/kymjs)
 */
public abstract class KJActivity extends FrameActivity {

    /**
     * 当前Activity状态
     */
    public static enum ActivityState {
        RESUME, PAUSE, STOP, DESTROY
    }

    public Activity aty;
    /** Activity状态 */
    public ActivityState activityState = ActivityState.DESTROY;

    /***************************************************************************
     *
     * print Activity callback methods
     *
     ***************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        aty = this;
        KJActivityStack.create().addActivity(this);
        KJLoger.state(this.getClass().getName(), "---------onCreate ");
        LogUtils.i(this.getClass().getName(), "---------onCreate ");

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        KJLoger.state(this.getClass().getName(), "---------onStart ");
        LogUtils.i(this.getClass().getName(), "---------onStart ");

    }

    @Override
    protected void onResume() {
        super.onResume();
        activityState = ActivityState.RESUME;
        KJLoger.state(this.getClass().getName(), "---------onResume ");
        LogUtils.i(this.getClass().getName(), "---------onResume ");

    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = ActivityState.PAUSE;
        KJLoger.state(this.getClass().getName(), "---------onPause ");
        LogUtils.i(this.getClass().getName(), "---------onPause ");

    }

    @Override
    protected void onStop() {
        super.onStop();
        activityState = ActivityState.STOP;
        KJLoger.state(this.getClass().getName(), "---------onStop ");
        LogUtils.i(this.getClass().getName(), "---------onStop ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        KJLoger.state(this.getClass().getName(), "---------onRestart ");
        LogUtils.i(this.getClass().getName(), "---------onRestart ");

    }

    @Override
    protected void onDestroy() {
        activityState = ActivityState.DESTROY;
        KJLoger.state(this.getClass().getName(), "---------onDestroy ");
        LogUtils.i(this.getClass().getName(), "---------onDestroy ");

        super.onDestroy();
        KJActivityStack.create().finishActivity(this);
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Class<?> cls) {
        showActivity(aty, cls);
        aty.finish();
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Intent it) {
        showActivity(aty, it);
        aty.finish();
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Class<?> cls, Bundle extras) {
        showActivity(aty, cls, extras);
        aty.finish();
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Intent it) {
        aty.startActivity(it);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }
}

