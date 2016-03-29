package com.scut.easyfe.app;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

/**
 * 全局常量
 * Created by jay on 15/9/11.
 */
public class Constants {
    public class Config{
        public static final boolean ISDEBUG = true;      //是否在debug模式，可以设置一些只有debug才会有的东西
        public static final boolean ISLOG = true;        //是否显示log
        public static final boolean ISSHOWTOAST = true;  //是否显示使用LogUtil.toast出来的东西
    }

    public class URL{
        public static final String DEFAULT_QINIU_URL = "http://7xrvd4.com1.z0.glb.clouddn.com/";            //七牛储存图片的地址
    }

    /**
     * 用于在不同的模块显示不同的 Log信息 进行分类
     */
    public class Tag{
        public static final String BASE_TAG = "easyfe_";
        public static final String LOGIN_TAG = BASE_TAG + "login";
        public static final String MAP_TAG = BASE_TAG + "map";
        public static final String ORDER_TAG = BASE_TAG + "order";
        public static final String TEACHER_REGISTER_TAG = BASE_TAG + "teacher_register";
    }

    public class Key{
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String ADDRESS = "address";
        public static final String ORDER_TYPE = "order_type";
        public static final String IS_REGISTER =  "is_register";
        public static final String SHOW_TEXT_ACTIVITY_TITLE =  "show_text_activity_title";
        public static final String SHOW_TEXT_ACTIVITY_CONTENT =  "show_text_activity_content";

    }

    /**
     * 一些标识符
     */
    public class Identifier {
        public static final int MALE = 1;
        public static final int FEMALE = 0;

        /** 订单类型 */
        public static final int ORDER_ALL = 0;
        public static final int ORDER_RESERVATION = 1;
        public static final int ORDER_TO_DO = 2;
        public static final int ORDER_COMPLETED = 3;
        public static final int ORDER_INVALID = 4;

        /** 我的订单页面状态 */
        public static final int STATE_NORMAL = 0;
        public static final int STATE_EDIT = 1;
    }

    public static class Path {
        public static final String SDCardRoot = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
        //默认的文件夹名称
        public static final String DEFAULT_DIR_NAME = "easyfe";
        public static final String DIR_WITH_SEPARATE = DEFAULT_DIR_NAME + File.separator;
        public static final String DIR_WITHOUT_SEPARATE = DEFAULT_DIR_NAME;
        public static final String COMPLETE_PATH = SDCardRoot + DIR_WITH_SEPARATE;
    }

}
