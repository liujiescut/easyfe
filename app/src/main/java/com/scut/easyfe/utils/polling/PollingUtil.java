package com.scut.easyfe.utils.polling;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.app.Variables;
import com.scut.easyfe.utils.LogUtils;

import java.lang.ref.WeakReference;

/**
 * 轮询工具类
 * Created by jay on 16/6/14.
 */
public class PollingUtil {
    private static WeakReference<Context> mContextReference;
    private static ServiceConnection mConnection;
    private static PollingService mPollingService;
    private static boolean mIsPolling = false;

    /**
     * 每个页面onResume的时候检测是否需要重新开启
     */
    public static void onResume(){
        if(Variables.POLLING_KILLED && mContextReference.get() != null){
            start(mContextReference.get());
        }
    }

    //开启轮询服务
    public static void start(Context context) {
        mContextReference = new WeakReference<>(context);
        Intent intent = new Intent(context, PollingService.class);
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mPollingService = ((PollingService.PollingBinder)binder).getService();

                if(mIsPolling){
                    mPollingService.setIsUserLogin(mIsPolling);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        context.bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
    }

    /**
     * 是否真的从服务器拉取数据
     */
    public static void setIsPolling(boolean isPolling){
        mIsPolling = isPolling;
        if (null != mPollingService) {
            mPollingService.setIsUserLogin(mIsPolling);
        }
    }

    //停止轮询服务
    public static void stop() {
        try {
            mContextReference.get().unbindService(mConnection);
        }catch (Exception e){
            LogUtils.i(Constants.Tag.POLLING_TAG, "关闭轮询出现异常");
            e.printStackTrace();
        }
    }


}
