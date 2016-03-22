package com.scut.easyfe.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;

/**
 * 显示Dialog的工具
 * Dialog显示主要依赖项目 alertview 实现仿iOS对话框
 * Created by jay on 16/3/15.
 */
public class DialogUtils {

    /**
     * 显示提醒确认信息的Dialog
     *
     * @param activity Dialog所在的Activity
     * @param title    显示标题
     * @param content  显示内容
     */
    public static void makeConfirmDialog(Context activity, String title, String content) {
        new AlertView(title, content, null, null, new String[]{"好"}, activity, AlertView.Style.Alert, null).show();
    }

    /**
     * 创建一个可以选择确认或取消的对话框
     *
     * @param activity       Dialog所在的Activity
     * @param title          对话框标题
     * @param content        确认内容
     * @param chooseListener 选择监听
     */
    public static void makeChooseDialog(Context activity, String title, String content, final OnChooseListener chooseListener) {
        OnItemClickListener listener = new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (null == chooseListener) {
                    return;
                }
                chooseListener.onChoose(position == 0);
            }
        };
        new AlertView(title, content, "取消", null, new String[]{"确认"}, activity, AlertView.Style.Alert, listener).show();
    }

    /**
     * 创建一个输入的对话框
     *
     * @param activity      Dialog所在Activity
     * @param title         标题
     * @param inputType     输入类型
     * @param inputListener 监听
     */
    public static AlertView makeInputDialog(@NonNull final Context activity, @NonNull String title, int inputType, @Nullable final OnInputListener inputListener) {
        final ViewGroup container = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.alertview_edit_text, null);
        final EditText etInput = (EditText) container.findViewById(R.id.alertview_et_input);
        etInput.setInputType(inputType);
        OnItemClickListener listener = new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0 && null != inputListener) {
                    inputListener.onFinish(etInput.getText().toString());
                }
                OtherUtils.hideSoftInputWindow(container.getWindowToken());
            }
        };
        final AlertView mAlertView =
                new AlertView(title, null, "取消", null, new String[]{"完成"}, activity, AlertView.Style.Alert, listener);
        etInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                //输入框出来则往上移动
                boolean isOpen = ((InputMethodManager) App.get().getApplicationContext().
                        getSystemService(Context.INPUT_METHOD_SERVICE)).isActive();
                mAlertView.setMarginBottom(isOpen && focus ? 120 : 0);
                System.out.println(isOpen);
            }
        });
        mAlertView.addExtView(container);
        return mAlertView;
    }

    /**
     * 带输入的对话框的输入完成回调
     */
    public interface OnInputListener {
        /**
         * 输入完成回调
         *
         * @param msg 输入内容
         */
        void onFinish(String msg);
    }

    /**
     * 选择确认取消的回调
     */
    public interface OnChooseListener {
        /**
         * 回调函数
         *
         * @param sure 是否点击了确定
         */
        void onChoose(boolean sure);
    }
}
