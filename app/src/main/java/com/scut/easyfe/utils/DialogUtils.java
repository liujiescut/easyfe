package com.scut.easyfe.utils;

import android.app.Activity;
import android.content.Context;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;

/**
 * 显示Dialog的工具
 * Dialog显示主要依赖项目 alertview 实现仿iOS对话框
 * Created by jay on 16/3/15.
 */
public class DialogUtils {

    /**
     * 显示提醒确认信息的Dialog
     * @param activity Dialog所在的Activity
     * @param title    显示标题
     * @param content  显示内容
     */
    public static void makeConfirmDialog(Context activity, String title, String content){
        new AlertView(title, content, null, null, new String[]{"好"}, activity, AlertView.Style.Alert, null).show();
    }

}
