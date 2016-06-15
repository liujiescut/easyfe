package com.scut.easyfe.entity;

/**
 * 轮询数据
 * Created by jay on 16/6/15.
 */
public class PollingData {
    private int data;

    public PollingData(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof PollingData)){
            return false;
        }

        return data == ((PollingData)other).data || super.equals(other);
    }
}
