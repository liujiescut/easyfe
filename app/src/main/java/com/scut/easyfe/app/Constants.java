package com.scut.easyfe.app;

/**
 * 全局常量
 * Created by jay on 15/9/11.
 */
public class Constants {
    public class Config{
        public static final boolean ISDEBUG = true;      //是否在debug模式，可以设置一些只有debug才会有的东西
        public static final boolean ISLOG = true;        //是否显示log
        public static final boolean ISSHOWTOAST = true;  //是否显示使用LogUtil.toast出来的东西
        public static final String LOGTAG = "liujie";    //Log信息时的tag
    }

    /**
     * 用于在不同的模块显示不同的 Log信息 进行分类
     */
    public class Tag{
        public static final String BASE_TAG = "frame_";
        public static final String LOGIN_TAG = BASE_TAG + "login";
        public static final String MAP_TAG = BASE_TAG + "map";
    }

    public class Key{
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String ADDRESS = "address";
    }

    /**
     * 一些标识符
     */
    public class Identifier {
        public static final int MALE = 1;
        public static final int FEMALE = 0;
    }
}
