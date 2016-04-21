package com.scut.easyfe.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 其他一些常用工具
 * Created by jay on 16/3/15.
 */
public class OtherUtils {

    private static long lastClickTime;
    private static final long timeInterval = 600;

    /**
     * 判断是否未连续的快速点击
     *
     * @return 判断结果
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < timeInterval) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

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
     * 判断对应包名应用有没有安装
     *
     * @param context     上下文
     * @param packageName 要判断的应用的包名
     * @param toMarket    是否到应用市场下载
     * @return 判断结果
     */
    public static boolean isAppInstalled(Context context, String packageName, boolean toMarket) {
        List<String> packageNameList = getAllPackageName(context);          //用于存储所有已安装程序的包名
        boolean hasInstalled = packageNameList.contains(packageName);
        if (hasInstalled) {
            return true;
        } else {
            if (toMarket && isMarketInstalled(context, openAppMarketIntent(packageName))) {
                context.startActivity(openAppMarketIntent(packageName));
            }
            return false;
        }
    }

    /**
     * 获取手机上安装的所有包名
     *
     * @param context 上下文
     * @return 所有包名的 List对象
     */
    public static List<String> getAllPackageName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        List<String> packageNameList = new ArrayList<>();
        if (packageInfoList != null) {
            for (int i = 0; i < packageInfoList.size(); i++) {
                packageNameList.add(packageInfoList.get(i).packageName);
            }
        }

        return packageNameList;
    }

    /**
     * 获取打开应用市场下载的Intent
     * @param packageName 要下载应用的包名
     * @return 相应Intent对象
     */
    public static Intent openAppMarketIntent(String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);//id为包名
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    /**
     * 判断应用市场有没有安装
     * @param context       上下文
     * @param marketIntent  openAppMarketIntent 返回对象
     * @return              判断结果
     */
    public static boolean isMarketInstalled(Context context, Intent marketIntent) {
        PackageManager pm = context.getPackageManager();
        ComponentName cn = marketIntent.resolveActivity(pm);
        return cn != null;
    }
}
