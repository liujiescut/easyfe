package com.scut.easyfe.utils;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Activity收集以及释放
 */
public class ActivityManagerUtils {

    private ArrayList<Activity> activityList = new ArrayList<Activity>();

    private static ActivityManagerUtils instance;

    private ActivityManagerUtils() {

    }

    public static ActivityManagerUtils getInstance() {
        if (null == instance) {
            instance = new ActivityManagerUtils();
        }
        return instance;
    }

    public Activity getTopActivity() {
        return activityList.size() == 0 ? null : activityList.get(activityList.size() - 1);
    }

    public void addActivity(Activity ac) {
        activityList.add(ac);
    }

    public void removeActivity(Activity ac) {
        activityList.remove(ac);
    }

    public void removeAllActivity() {
        for (Activity ac : activityList) {
            if (null != ac) {
                if (!ac.isFinishing()) {
                    ac.finish();
                }
            }
        }
        activityList.clear();
    }
}
