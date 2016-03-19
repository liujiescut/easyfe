package com.scut.easyfe.utils;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

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
}
