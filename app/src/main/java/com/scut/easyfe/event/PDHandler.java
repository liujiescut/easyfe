package com.scut.easyfe.event;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.app.Variables;
import com.scut.easyfe.entity.PollingData;
import com.scut.easyfe.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 处理轮询数据(根据有没有变化来确定是否发送更新事件)
 * Created by jay on 16/6/15.
 */
public class PDHandler {

    private static PDHandler mInstance;
    private static EventBus mEventBus;
    private PollingData mLatestData = new PollingData();

    /**
     * 获取轮询数据处理类单例
     * @return 实例
     */
    public static PDHandler get(){
        if (null == mInstance) {
            synchronized (PDHandler.class) {
                if (null == mInstance) {
                    mInstance = new PDHandler();
                }
            }
        }

        return mInstance;
    }

    /**
     *  确保单例构造函数(勿删)
     */
    private PDHandler(){
        mEventBus = App.get().getEventBus();
    }

    /**
     * 处理轮询得到的数据
     * @param data 轮询数据
     * @return     是否发送了更新UI事件
     */
    public boolean handleData(PollingData data){
        LogUtils.i(Constants.Tag.POLLING_TAG, "PDHandler -> handleData(PollingData data)");
        if(mLatestData.equals(data)){
            LogUtils.i(Constants.Tag.POLLING_TAG, "Polling data no change");
            return false;
        }

        LogUtils.i(Constants.Tag.POLLING_TAG, "### post data change event!!! ###");
        mLatestData = data;
        mEventBus.post(new DataChangeEvent(data));
        return true;
    }

    public PollingData getLatestData() {
        return mLatestData;
    }

    public void resetOrderNormal(String orderId){
        for (int latestIndex = 0; latestIndex < PDHandler.get().getLatestData().getMine().getOrder().size(); latestIndex++) {
            if (PDHandler.get().getLatestData().getMine().getOrder().get(latestIndex).getOrderId().equals(orderId)) {
                boolean orderInLocal = false;
                for (int localIndex = 0; localIndex < Variables.localData.getMine().getOrder().size(); localIndex++) {
                    if (Variables.localData.getMine().getOrder().get(localIndex).getOrderId().equals(orderId)) {

                        Variables.localData.getMine().getOrder().get(localIndex).setTimestamp(PDHandler.get().getLatestData().getMine().getOrder().get(latestIndex).getTimestamp());
                        Variables.localData.getMine().getOrder().get(localIndex).setState(PDHandler.get().getLatestData().getMine().getOrder().get(latestIndex).getState());
                        Variables.localData.equals(PDHandler.get().getLatestData(), true);
                        Variables.localData.save2Cache(App.getUser().getPhone());
                        App.get().getEventBus().post(new DataChangeEvent(PDHandler.get().getLatestData()));
                        orderInLocal = true;
                        break;
                    }
                }

                if(!orderInLocal){
                    Variables.localData.getMine().getOrder().add(PDHandler.get().getLatestData().getMine().getOrder().get(latestIndex));
                    Variables.localData.equals(PDHandler.get().getLatestData(), true);
                    Variables.localData.save2Cache(App.getUser().getPhone());
                    App.get().getEventBus().post(new DataChangeEvent(PDHandler.get().getLatestData()));
                }

            }
        }
    }
}
