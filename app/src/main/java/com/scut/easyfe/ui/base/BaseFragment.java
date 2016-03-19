package com.scut.easyfe.ui.base;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 万物起源
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;

    @LayoutRes
    protected int layoutRes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mActivity = (BaseActivity)getActivity();

        setLayoutRes();

        View v = LayoutInflater.from(getActivity()).inflate(layoutRes, container, false);

        initView(v);
        initListener(v);
        fetchData(v);

        return v;
    }

    /**
     * 设置Fragment的layout文件(必须实现该文件)
     */
    protected abstract void setLayoutRes();

    /**
     * 初始化View，设置TextView的text属性等
     * @param v     Fragment的根视图
     */
    protected void initView(View v){}

    /**
     * 控件的监听全部写在这个函数里面
     * @param v     Fragment的根视图
     */
    protected void initListener(View v){}

    /**
     * 拉取数据的操作
     * @param v     Fragment的根视图
     */
    protected void fetchData(View v){}


    /**
     * 跳转到另一个 Activity
     *
     * @param targetActivity 要跳转到的 Activity
     */
    public void toActivity(@NonNull Class<?> targetActivity) {
        toActivity(targetActivity, null);
    }


    /**
     * 跳转到另一个 Activity, 并附带 Bundle
     *
     * @param targetActivity 要跳转到的 Activity
     * @param bundle         要附带的 Bundle
     */
    public void toActivity(@NonNull Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(getContext(), targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 跳转到另一个 Activity 请求结果
     * <p/>
     * 注意: 要保证 Fragment 能调用 onActivityResult,
     * 该 Fragment 所处的 Activity
     * 的 onActivityResult 中必须调用 super.onActivityResult.
     *
     * @param targetActivity 要跳转到的 Activity
     * @param requestCode    此次请求的标记
     */
    public void toActivityForResult(@NonNull Class<?> targetActivity, int requestCode) {
        Intent intent = new Intent(getContext(), targetActivity);
        startActivityForResult(intent, requestCode);
    }


    public void toActivityForResult(@NonNull Class<?> targetActivity, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getContext(), targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * 显示一个 Toast, 不用于 Debug
     *
     * @param resId Toast 的内容的 id
     */
    public void toast(@StringRes int resId) {
        toast(getString(resId));
    }


    /**
     * 显示一个 Toast, 不用于 Debug,
     * 基于 BaseActivity 中的 toast
     *
     * @param text Toast 的内容
     */
    public void toast(@NonNull String text) {
        if (mActivity != null)
            mActivity.toast(text);
    }

}
