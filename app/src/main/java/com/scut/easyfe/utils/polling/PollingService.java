package com.scut.easyfe.utils.polling;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.app.Variables;
import com.scut.easyfe.entity.PollingData;
import com.scut.easyfe.event.PDHandler;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.RPollingData;
import com.scut.easyfe.utils.LogUtils;

public class PollingService extends Service {

    private long mPollingTime = 0;
    private long mPollingSuccessTime = 0;

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
                if(App.getUser(false).hasLogin()) {
                    polling();
                }

                try {
                    Thread.sleep(Variables.POLLING_INTERVAL_SECONDS * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 执行轮询操作
     */
    public void polling(){
        final long finalPollingTime = mPollingTime++;
        RequestManager.get().execute(new RPollingData(), new RequestListener<PollingData>() {
            @Override
            public void onSuccess(RequestBase request, PollingData result) {
                if(mPollingSuccessTime > finalPollingTime){
                    return;
                }

                mPollingSuccessTime = finalPollingTime;
                if(App.getUser(false).hasLogin()){
                    PDHandler.get().handleData(result);
                }
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {

            }
        });
    }
}
