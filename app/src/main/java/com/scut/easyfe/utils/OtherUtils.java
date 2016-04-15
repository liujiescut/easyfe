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
}
