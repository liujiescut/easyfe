package com.scut.easyfe.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.Zone;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.ui.activity.auth.LoginActivity;
import com.scut.easyfe.utils.ActivityManagerUtils;
import com.scut.easyfe.utils.SpUtils;
import com.scut.easyfe.utils.polling.PollingUtil;
import com.umeng.socialize.PlatformConfig;

import org.greenrobot.eventbus.EventBus;


/**
 * 自定义 Application
 * Created by jay on 15/9/11.
 */
public class App extends Application {
    private static App mInstance;           // Application单例
    private static User mUser;
    private static SpUtils mSpUtils;            // 用于管理 SharePreference 的工具类对象
    private static EventBus mEventBus;
    private ActivityManagerUtils mActivityManagerUtils;             // 用于管理 Activity 的工具类对象
    private UploadManager qiniuManager;             //上传头像工具

    private static String mQNToken = "";
    private static String mServicePhone = "";

    /**
     * 单例获取Application的实例
     *
     * @return application
     */
    public static synchronized App get() {
        if (null == mInstance) {
            mInstance = new App();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mActivityManagerUtils = ActivityManagerUtils.getInstance();
        PollingUtil.start(this);
        init();
    }

    public static synchronized User getUser() {
        return getUser(true);
    }

    public static synchronized User getUser(boolean goLogin) {
        if (null == mUser) {
            mUser = new User();
        }

        if (goLogin && mUser.getToken().length() == 0) {
            if (App.get().getTopActivity() != null) {
                Toast.makeText(App.get().getTopActivity(), "请重新登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(App.get().getTopActivity(), LoginActivity.class);
                App.get().getTopActivity().startActivity(intent);
            }
        }

        return mUser;
    }

    public static void setUser(User user) {
        setUser(user, true);
    }

    public static void setUser(User user, boolean saveToCache) {
        mUser = user;
        if(saveToCache) {
            mUser.save2Cache();
        }
    }

    /**
     * 获取 SpUtils 实例(整个应用没有特殊要求统一使用这个实例)
     *
     * @return SpUtils 实例
     */
    public static SpUtils getSpUtils() {
        if (null == mSpUtils) {
            mSpUtils = new SpUtils(get().getApplicationContext());
        }
        return mSpUtils;
    }


    /**
     * 获取七牛的管理工具
     *
     * @return 七牛管理工具对象
     */
    public UploadManager getQiniuManager() {
        if (null == qiniuManager) {
            initQiniu();
        }
        return qiniuManager;
    }

    public EventBus getEventBus(){
        if (null == mEventBus) {
            initEventBus();
        }
        return mEventBus;
    }


    /**
     * Application 默认构造函数
     */
    public App() {
    }

    /**
     * 执行初始化操作
     */
    private void init() {
        initQiniu();
        initBaiduMap();
        initImageLoader();
        initUmeng();
        initEventBus();

        /** 用户登陆*/
        User.doLogin();
    }

    private void initEventBus(){
        mEventBus = EventBus.getDefault();
    }


    /**
     * 初始化七牛相关配置(用于保存图片)
     */
    private void initQiniu() {
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认 256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认 512K
                .connectTimeout(10) // 链接超时。默认 10秒
                .responseTimeout(60) // 服务器响应超时。默认 60秒
                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
        // 重用 uploadManager。
        qiniuManager = new UploadManager(config);

        mQNToken = getSpUtils().getValue(Constants.Key.QN_TOKEN, Constants.Data.DEFAULT_QN_TOKEN);
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap() {
        SDKInitializer.initialize(this);
    }

    private void initImageLoader() {
        // ImageLoader 的初始化
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageForEmptyUri(R.mipmap.image_fail)
//                .showImageOnFail(R.mipmap.image_fail)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)//设置为RGB565比起默认的ARGB_8888要节省大量的内存
                        //.delayBeforeLoading(100)//载入图片前稍做延时可以提高整体滑动的流畅度
                .considerExifParams(true)// 考虑旋转角
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();

        ImageLoader.getInstance().init(config);
    }

    private void initUmeng(){
        // 微信 appid appsecret
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");

        // 新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("3921700954","04b48b094faeb16683c32669824ebdad");

        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }



    public static String getServicePhone() {
        return mServicePhone;
    }

    public static void setServicePhone(String servicePhone) {
        mServicePhone = servicePhone;
    }

    public static String getQNToken() {
        return mQNToken;
    }

    public static void setQNToken(String token) {
        mQNToken = token;
        mSpUtils.setValue(Constants.Key.QN_TOKEN, mQNToken);
    }

    /**
     * 向 activity栈 中添加一个 activity
     *
     * @param activity Activity引用
     */
    public void addActivity(Activity activity) {
        mActivityManagerUtils.addActivity(activity);
    }


    /**
     * 从 activity栈 中移除一个 activity
     *
     * @param activity Activity引用
     */
    public void removeActivity(Activity activity) {
        mActivityManagerUtils.removeActivity(activity);
    }


    public void removeAllActivity(){
        mActivityManagerUtils.removeAllActivity();
    }


    /**
     * 获取 activity栈 中栈顶的 activity
     */
    public Activity getTopActivity() {
        return mActivityManagerUtils.getTopActivity();
    }

    /**
     * 退出应用时清空所有的 activity
     */
    public void exit() {
        mActivityManagerUtils.removeAllActivity();
        PollingUtil.stop();
    }

}
