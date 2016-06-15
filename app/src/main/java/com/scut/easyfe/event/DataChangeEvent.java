package com.scut.easyfe.event;

import android.support.annotation.NonNull;

import com.scut.easyfe.entity.PollingData;

/**
 * 轮询数据发生变化的事件
 * Created by jay on 16/6/15.
 */
public class DataChangeEvent {
    private PollingData data = new PollingData(0);

    public DataChangeEvent(@NonNull PollingData data) {
        this.data = data;
    }

    public PollingData getData() {
        return data;
    }
}
