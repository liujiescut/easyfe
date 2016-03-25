package com.scut.easyfe.app;

import android.app.Activity;
import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.Zone;
import com.scut.easyfe.utils.ActivityManagerUtils;
import com.scut.easyfe.utils.SpUtils;


/**
 * 自定义 Application
 * Created by jay on 15/9/11.
 */
public class App extends Application{
    private static App mInstance;           // Application单例
    private static SpUtils mSpUtils;            // 用于管理 SharePreference 的工具类对象
    private ActivityManagerUtils mActivityManagerUtils;             // 用于管理 Activity 的工具类对象
    private UploadManager qiniuManager;             //上传头像工具

    /**
     * 单例获取Application的实例
     * @return application
     */
    public static App get(){
        if(null == mInstance){
            mInstance = new App();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mActivityManagerUtils = ActivityManagerUtils.getInstance();
        init();
    }

    /**
     * 获取 SpUtils 实例(整个应用没有特殊要求统一使用这个实例)
     * @return SpUtils 实例
     */
    public static SpUtils getSpUtils(){
        if (null == mSpUtils) {
            mSpUtils = new SpUtils(get().getApplicationContext());
        }
        return mSpUtils;
    }


    /**
     * 获取七牛的管理工具
     * @return  七牛管理工具对象
     */
    public UploadManager getQiniuManager(){
        if(null == qiniuManager){
            initQiniu();
        }
        return qiniuManager;
    }


    /**
     * Application 默认构造函数
     */
    public App(){
    }

    /**
     * 执行初始化操作
     */
    private void init(){
        initQiniu();
        initBaiduMap();
    }


    /**
     * 初始化七牛相关配置(用于保存图片)
     */
    private void initQiniu(){
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认 256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认 512K
                .connectTimeout(10) // 链接超时。默认 10秒
                .responseTimeout(60) // 服务器响应超时。默认 60秒
                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
        // 重用 uploadManager。
        qiniuManager = new UploadManager(config);
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap(){
        SDKInitializer.initialize(this);
    }



    /**
     * 向 activity栈 中添加一个 activity
     * @param activity Activity引用
     */
    public void addActivity(Activity activity){
        mActivityManagerUtils.addActivity(activity);
    }


    /**
     * 从 activity栈 中移除一个 activity
     * @param activity Activity引用
     */
    public void removeActivity(Activity activity){
        mActivityManagerUtils.removeActivity(activity);
    }


    /**
     * 获取 activity栈 中栈顶的 activity
     */
    public Activity getTopActivity(){
        return mActivityManagerUtils.getTopActivity();
    }

    /**
     * 退出应用时清空所有的 activity
     */
    public void exit(){
        mActivityManagerUtils.removeAllActivity();
    }

}
