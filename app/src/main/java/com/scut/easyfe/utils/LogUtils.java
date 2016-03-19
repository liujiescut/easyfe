package com.scut.easyfe.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.scut.easyfe.app.Constants;


/**
 * @author kingofglory
 *         email: kingofglory@yeah.net
 *         blog:  http:www.google.com
 * @date 2014-2-21
 * TODO Log工具类，设置开关，防止发布版本时log信息泄露
 */

public class LogUtils {

    public static void println(Object object) {
        if (Constants.Config.ISLOG) {
            System.out.println(object);
        }
    }

    public static void v(String LOGTAG, String msg) {
        if (Constants.Config.ISLOG) {
            Log.v(LOGTAG, msg);
        }

    }

    public static void d(String LOGTAG, String msg) {
        if (Constants.Config.ISLOG) {
            Log.d(LOGTAG, msg);
        }

    }

    public static void i(String LOGTAG, String msg) {
        if (Constants.Config.ISLOG) {
            Log.i(LOGTAG, msg);
        }

    }

    public static void w(String LOGTAG, String msg) {
        if (Constants.Config.ISLOG) {
            Log.w(LOGTAG, msg);
        }

    }

    public static void e(String LOGTAG, String msg) {
        if (Constants.Config.ISLOG) {
            Log.e(LOGTAG, msg);
        }
    }

    public static void v(String msg) {
        if (Constants.Config.ISLOG) {
            Log.v(Constants.Config.LOGTAG, msg);
        }

    }

    public static void d(String msg) {
        if (Constants.Config.ISLOG) {
            Log.d(Constants.Config.LOGTAG, msg);
        }

    }

    public static void i(String msg) {
        if (Constants.Config.ISLOG) {
            Log.i(Constants.Config.LOGTAG, msg);
        }

    }

    public static void w(String msg) {
        if (Constants.Config.ISLOG) {
            Log.w(Constants.Config.LOGTAG, msg);
        }

    }

    public static void e(String msg) {
        if (Constants.Config.ISLOG) {
            Log.e(Constants.Config.LOGTAG, msg);
        }
    }

    public static void forcePrint(String msg) {
        Log.i(Constants.Config.LOGTAG, msg);
    }

    public static void toastError(Context context, String msg) {
        if (Constants.Config.ISSHOWTOAST) {
            Toast.makeText(context, "操作失败 :" + msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void toast(Context context, String msg) {
        if (Constants.Config.ISSHOWTOAST) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

}
