package com.scut.easyfe.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

/**
 * Created by roger on 9/25/14.
 */

public class DensityUtil {

    private static float scale = -1;
    private static int statusBarHeight = -1;
    private static int windowWidth = -1;
    private static int windowHeight = -1;

    private static float getScale(Context context) {
        if (scale == -1) {
            scale = context.getResources().getDisplayMetrics().density;
        }

        return scale;
    }

    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight < 0)
        {
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int sbar = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {

            }
        }

        return statusBarHeight;
    }

    public static int getWindowWidth(Context context) {
        if (windowWidth == -1)
        {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            windowWidth = dm.widthPixels;
        }

        return windowWidth;
    }

    public static int getWindowHeight(Context context) {
        if (windowHeight == -1)
        {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            windowHeight = dm.heightPixels;
        }

        return windowHeight;
    }

//    /**
//     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
//     */
    public static int dip2px(Context context, float dpValue) {
        return (int) (dpValue * getScale(context) + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / getScale(context) + 0.5f);
    }

    public static boolean hasGingerbread() { /*2.3.1 API 9*/
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {/*3.0.X  API 11*/
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {/*3.1.X  API 12*/
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasHoneycombMR2() {/*3.2 API 13*/
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    public static boolean hasIceCreamSandwich(){/*4.0, 4.0.1, 4.0.2 API 14*/
        return Build.VERSION.SDK_INT >- Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean hasJellyBean() {/*4.1, 4.1.1  API 16 */
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasKitKat() {/*4.4  API 19 */
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }


    //    /** 屏幕宽度   */
    private static int DisplayWidthPixels = 0;
    //    /** 屏幕高度   */
    private static int DisplayheightPixels = 0;

    private static void getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        DisplayWidthPixels = dm.widthPixels;// 宽度
        DisplayheightPixels = dm.heightPixels;// 高度
    }

    public static int getDisplayWidthPixels(Context context) {
        if (context == null) {
            return -1;
        }
        if (DisplayWidthPixels == 0) {
            getDisplayMetrics(context);
        }
        return DisplayWidthPixels;
    }



    public static int getScreenWidthDip(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        return (int) (dm.widthPixels * dm.density);
    }

    public static int getScreenHeightDip(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        return (int) (dm.heightPixels * dm.density);
    }

    public static int getScreenWidthPx(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        return (dm.widthPixels);
    }

    public static int getScreenHeightPx(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        return (dm.heightPixels);
    }
}