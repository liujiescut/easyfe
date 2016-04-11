package com.scut.easyfe.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.scut.easyfe.app.App;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 其他一些常用工具
 * Created by jay on 16/3/15.
 */
public class OtherUtils {
    public static <T> T findViewById(@NonNull View v, @IdRes int id) {
        return (T) v.findViewById(id);
    }

    public static <T> T findViewById(@NonNull Activity activity, @IdRes int id) {
        return (T) activity.findViewById(id);
    }

    public static <T> T findViewById(@NonNull Dialog dialog, @IdRes int id) {
        return (T) dialog.findViewById(id);
    }

    /**
     * 关闭软键盘
     */
    public static void hideSoftInputWindow(IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) App.get().getApplicationContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    /**
     * 转换Date
     * @param date     需要转化的Date
     * @param format   转换的格式
     * @return         转换后字符串
     */
    public static String getTime(Date date, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 转换分钟数为小时加分钟
     * @param minutes 被转换分钟数
     * @return  转换后字符串
     */
    public static String getTimeFromMinute(int minutes){
        int hours = minutes / 60;
        int minute = minutes % 60;
        return String.format("%s小时%s分钟", hours, minute);
    }

    public static Date getDateFromString(String dateString) {
        return getDateFromString(dateString, "yyyy-MM-dd");
    }

        @Nullable
    public static Date getDateFromString(String dateString, String formatString){
        DateFormat format = new SimpleDateFormat(formatString, Locale.CHINA);

        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
