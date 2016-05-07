package com.bigkoo.pickerview;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * 自定义时间选择(选择小时跟分钟)
 * Created by jay on 16/3/22.
 * Todo 优化小时跟分钟的关联关系
 */
public class MyTimePicker {
    private static MyTimePicker mInstance;
    private OnPickListener mListener;
    private static  OptionsPickerView<Integer> mPicker;
    private static ArrayList<Integer> mHours = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> mMinutes = new ArrayList<>();

    /**
     * 初始化要显示的小时跟分钟数
     */
    static {
        for (int i = 0; i <= 12; i++) {
            mHours.add(i);
        }

        for (int i = 0; i < mHours.size(); i++) {
            ArrayList<Integer> minutes = new ArrayList<>();
            for(int j = 0; j < 60; j++){
                minutes.add(j);
            }
            mMinutes.add(minutes);
        }
    }

    /**
     * 构造函数
     * @param context 传入Activity上下文
     */
    public MyTimePicker(Context context){
        mPicker = new OptionsPickerView<Integer>(context);
        mPicker.setPicker(mHours, mMinutes, false);
        mPicker.setLabels("小时","分钟");
        mPicker.setSelectOptions(2, 0);
        mPicker.setCyclic(false);
        mPicker.setCancelable(true);
        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                if(null != mListener){
                    mListener.onPick(mHours.get(options1), mMinutes.get(mHours.get(options1)).get(option2));
                }
            }
        });
    }

    public void show(){
        if(null != mPicker){
            mPicker.show();
        }
    }

    public void dismiss(){
        if(null != mPicker){
            mPicker.dismiss();
        }
    }

    /**
     * 设置选择标题
     * @param title
     */
    public void setTitle(String title){
        if(null != mPicker){
            mPicker.setTitle(title);
        }
    }

    /**
     * 设置选择监听
     */
    public void setOnPickListener(OnPickListener listener){
        mListener = listener;
    }


    /**
     * 选择回调
     */
    public interface OnPickListener{
        /**
         * 选择回调函数
         * @param hour    选中小时
         * @param minute  选中分钟
         */
        void onPick(int hour, int minute);
    }

    /**
     * 设置可选的时间
     * @param hours    可选的小时
     * @param minutes  可选的分钟
     */
    public void setToShowTime(@NotNull ArrayList<Integer> hours, @NotNull ArrayList<ArrayList<Integer>> minutes) {
        mHours.clear();
        mMinutes.clear();
        mHours.addAll(hours);
        mMinutes.addAll(minutes);
        mPicker.setPicker(mHours, mMinutes, false);
    }

}
