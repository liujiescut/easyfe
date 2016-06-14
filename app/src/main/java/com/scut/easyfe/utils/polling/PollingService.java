package com.scut.easyfe.utils.polling;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.app.Variables;
import com.scut.easyfe.utils.LogUtils;

public class PollingService extends Service {
    public static final String ACTION = "com.scut.easyfe.service.PollingService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.i(Constants.Tag.POLLING_TAG, "onBind");
        Variables.POLLING_KILLED = false;
        new PollingThread().start();

        return null;
    }

    @Override
    public void onDestroy() {
        LogUtils.i(Constants.Tag.POLLING_TAG, "onDestroy");
        Variables.POLLING_KILLED = true;
        super.onDestroy();
    }

    int count = 0;

    /**
     * 模拟向Server轮询的异步线程
     */
    class PollingThread extends Thread {
        @Override
        public void run() {
            while(Variables.POLLING) {
                LogUtils.i(Constants.Tag.POLLING_TAG, "轮询中 --> " + count++);
                polling();

                try {
                    Thread.sleep(Variables.POLLING_INTERVAL_SECONDS * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 执行轮寻操作
     */
    public void polling(){
        if(App.getUser(false).hasLogin()){
        }
    }
}
