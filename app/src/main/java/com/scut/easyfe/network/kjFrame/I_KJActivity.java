package com.scut.easyfe.network.kjFrame;

/**
 * Created by WilliamHao on 15/8/29.
 */
import android.view.View;

/**
 * KJFrameActivity接口协议，实现此接口可使用KJActivityManager堆栈<br>
 *
 * <b>创建时间</b> 2014-3-1 <br>
 * <b>最后修改时间</b> 2014-5-30
 *
 * @author kymjs (https://github.com/kymjs)
 * @version 1.0
 */
public interface I_KJActivity {
    /** 设置root界面 */
    void setRootView();

    /** 初始化数据 */
    void initData();

    /** 在线程中初始化数据 */
    void initDataFromThread();

    /** 初始化控件 */
    void initWidget();

    /** 点击事件回调方法 */
    void widgetClick(View v);
}

