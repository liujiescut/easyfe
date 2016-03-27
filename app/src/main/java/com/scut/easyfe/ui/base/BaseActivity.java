package com.scut.easyfe.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.scut.easyfe.app.App;


/**
 * Activity基类
 * Created by jay on 15/9/12.
 */
public abstract class BaseActivity extends AppCompatActivity{
    protected String mTAG;

    protected App mApplication;
    protected Resources mResources;
    protected Context mContext;
    protected Toast mToast;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mTAG = this.getClass().getSimpleName();
        initConfigure();

        setLayoutView();
        initData();
        initView();
        initListener();
        fetchData();
        //LogUtils.i("ACTIVITY_CREATE", this.getClass().getName());
    }

    @Override
    protected void onDestroy(){
        mApplication.removeActivity(this);
        super.onDestroy();
    }

    private void initConfigure() {
        mContext = this;
        mApplication = App.get();
        mApplication.addActivity(this);
        mResources = getResources();
    }

    /**
     * Activity跳转
     */
    public void redirectToActivity(Context context, Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * Activity跳转
     */
    public void redirectToActivity(Context context, Class<?> targetActivity) {
        redirectToActivity(context, targetActivity, null);
    }

    /**
     * 设置Activity的Layout文件(必须重写这个函数)
     * onCreate中第一个被调用
     */
    protected abstract void setLayoutView();

    /**
     * 数据的初始化全部写在这里面（比如读取Intent的数据等）
     * onCreate中第二个被调用
     */
    protected void initData(){}

    /**
     * 初始化View，设置TextView的text属性等
     * onCreate中第三个被调用
     */
    protected void initView(){}

    /**
     * 控件的监听全部写在这个函数里面
     * onCreate中第四个被调用
     */
    protected void initListener(){}

    /**
     * 从网络加载信息
     * onCreate中第五个被调用
     */
    protected void fetchData(){}

    /**
     * 显示Toast
     * @param text 显示文本
     */
    public void toast(final String text) {
        if (!"".equals(text)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), text,
                                Toast.LENGTH_SHORT);
                    } else {
                        mToast.setText(text);
                    }
                    mToast.show();
                }
            });

        }
    }

}
