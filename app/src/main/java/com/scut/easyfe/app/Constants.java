package com.scut.easyfe.app;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

/**
 * 全局常量 {@link }
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

        public static final String URL_BASE = "http://121.42.37.233/";
        public static final String URL_PARENT_REGISTER = URL_BASE + "Authentication/SignUp/Parent";
        public static final String URL_LOGIN = URL_BASE + "Authentication/Login";
        public static final String URL_GET_MESSAGES = URL_BASE + "Share/Message";
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
        public static final String USER_CACHE = "user_cache";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String ADDRESS = "address";
        public static final String CITY = "city";
        public static final String ORDER_TYPE = "order_type";
        public static final String IS_REGISTER =  "is_register";
        public static final String SHOW_TEXT_ACTIVITY_TITLE =  "show_text_activity_title";
        public static final String SHOW_TEXT_ACTIVITY_CONTENT =  "show_text_activity_content";
        public static final String CALLBACK_TYPE =  "callback_type";
        public static final String CONFIRM_ORDER_TYPE =  "confirm_order_type";
        public static final String ORDER =  "order";
        public static final String TEACH_WEEK =  "teach_week";
        public static final String RESERVE_WAY = "reserve_way";
        public static final String TO_TEACHER_INFO_ACTIVITY_TYPE = "to_teacher_info_activity_type";
        public static final String TO_TEACHER_REGISTER_ONE_ACTIVITY_TYPE = "to_teacher_register_one_activity_type";
        public static final String TO_PARENT_REGISTER_ACTIVITY_TYPE = "to_parent_register_activity_type";
        public static final String IS_FIRST_IN_SPECIAL_TIME_ACTIVITY  = "is_fist_time_in_special_activity";
    }

    /**
     * 一些标识符
     */
    public class Identifier {
        /** 用户分类 */
        public static final int USER_WEB_REGISTED = 0;    //被邀请未注册
        public static final int USER_TEACHER = 1;         //家教
        public static final int USER_PARENT = 2;          //家长
        public static final int USER_TP = 3;              //即是家长又是家教

        /** 性别 */
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

        /** 反馈页面的标识,表明是哪种反馈(需求反馈,应用反馈,投诉) */
        public static final int CALLBACK_NEED = 0;           //首页预约不到想要的点这里进行反馈
        public static final int CALLBACK_APP = 1;            //更多页面应用反馈
        public static final int CALLBACK_COMPLAINTS = 2;     //完成订单后投诉

        /** 确认订单页面所需 */
        public static final int CONFIRM_ORDER_SPECIAL = 0;       //特价订单
        public static final int CONFIRM_ORDER_SINGLE_RESERVE = 1;   //单次预约订单
        public static final int CONFIRM_ORDER_MULTI_RESERVE = 2;    //多次预约订单

        /** 预约页面 */
        public static final int RESERVE_MULTI = 0;
        public static final int RESERVE_SINGLE = 1;

        /** 到家教信息页面的功能 */
        public static final int TYPE_SEE_TEACHER_INFO = 0;
        public static final int TYPE_RESERVE = 1;

        /** 到家教注册页面(基本信息)的功能 */
        public static final int TYPE_REGISTER = 0;
        public static final int TYPE_MODIFY = 1;
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

    public static class Data {
        public static final ArrayList<String> genderList = new ArrayList<>();                       //可选性别
        public static final ArrayList<String> schoolList = new ArrayList<>();                       //可选性别
        public static final ArrayList<String> professionList = new ArrayList<>();                   //可选的专业
        public static final ArrayList<String> courseList = new ArrayList<>();                       //可选的课程
        public static final ArrayList<ArrayList<String>> courseGradeList = new ArrayList<>();
        public static final ArrayList<String> studentStateList = new ArrayList<>();                 //可选的年级一级分类
        public static final ArrayList<ArrayList<String>> studentGradeList = new ArrayList<>();      //可选的年级二级分类
        public static final ArrayList<String> teacherGradeList = new ArrayList<>();                 //家教的年级列表
        public static final ArrayList<String> weekList = new ArrayList<>();
        public static final ArrayList<String> ageList = new ArrayList<>();
        public static final ArrayList<String> scoreRangeList = new ArrayList<>();
        public static final ArrayList<String> hasTeachChildCountRangeList = new ArrayList<>();
        public static final ArrayList<String> hasTeachChildTimeRangeList = new ArrayList<>();

        static {

            schoolList.add("华南理工大学");
            schoolList.add("中山大学");
            schoolList.add("暨南大学");
            schoolList.add("华南师范大学");
            schoolList.add("华南农业大学");
            schoolList.add("南方医科大学");
            schoolList.add("广东工业大学");
            schoolList.add("广东外语外贸大学");
            schoolList.add("广州大学");
            schoolList.add("广州中医药大学");
            schoolList.add("广东财经大学");

            genderList.add("女");
            genderList.add("男");

            professionList.add("数学");
            professionList.add("英语");
            professionList.add("国际学院内设专业(英文授课)");
            professionList.add("物理类");
            professionList.add("化学类");
            professionList.add("生物学类");
            professionList.add("师范专业(包括教育学)");
            professionList.add("心理学");
            professionList.add("管理类");
            professionList.add("经济类");
            professionList.add("外语系(英语专业以外)");
            professionList.add("建筑类");
            professionList.add("法学");
            professionList.add("新闻");
            professionList.add("中文");
            professionList.add("政治");
            professionList.add("历史");
            professionList.add("地质地理");
            professionList.add("医学/药学");
            professionList.add("社会学类");
            professionList.add("农林专业");
            professionList.add("体育");
            professionList.add("艺术");
            professionList.add("计算机");
            professionList.add("电子信息科学");
            professionList.add("自然类科学");
            professionList.add("其他");

            courseList.add("数学(非高中)");
            courseList.add("数学(文科)");
            courseList.add("数学(理科)");
            courseList.add("英语");
            courseList.add("物理");
            courseList.add("化学");
            courseList.add("生物");
            courseList.add("语文");
            courseList.add("小学全科");

            ArrayList<String> mathCourseGradeOne = new ArrayList<>();
            ArrayList<String> mathCourseGradeTwo = new ArrayList<>();
            ArrayList<String> mathCourseGradeThree = new ArrayList<>();
            ArrayList<String> englishCourseGrade = new ArrayList<>();
            ArrayList<String> physicalCourseGrade = new ArrayList<>();
            ArrayList<String> chemistryCourseGrade = new ArrayList<>();
            ArrayList<String> biologicalCourseGrade = new ArrayList<>();
            ArrayList<String> chineseCourseGrade = new ArrayList<>();
            ArrayList<String> primaryGeneralCourseGrade = new ArrayList<>();


            chineseCourseGrade.add("一年级至三年级");
            chineseCourseGrade.add("四年级");
            chineseCourseGrade.add("五年级");
            chineseCourseGrade.add("六年级");

            mathCourseGradeOne.addAll(chineseCourseGrade);
            mathCourseGradeOne.add("初一");
            mathCourseGradeOne.add("初二");
            mathCourseGradeOne.add("初三");

            mathCourseGradeTwo.add("高一");
            mathCourseGradeTwo.add("高二");
            mathCourseGradeTwo.add("高三");

            mathCourseGradeThree.addAll(mathCourseGradeTwo);

            englishCourseGrade.addAll(mathCourseGradeOne);
            englishCourseGrade.addAll(mathCourseGradeTwo);

            physicalCourseGrade.add("初一");
            physicalCourseGrade.add("初二");
            physicalCourseGrade.add("初三");
            physicalCourseGrade.addAll(mathCourseGradeTwo);

            chemistryCourseGrade.addAll(physicalCourseGrade);

            biologicalCourseGrade.addAll(physicalCourseGrade);

            primaryGeneralCourseGrade.addAll(chineseCourseGrade);

            courseGradeList.add(mathCourseGradeOne);
            courseGradeList.add(mathCourseGradeTwo);
            courseGradeList.add(mathCourseGradeThree);
            courseGradeList.add(englishCourseGrade);
            courseGradeList.add(physicalCourseGrade);
            courseGradeList.add(chemistryCourseGrade);
            courseGradeList.add(biologicalCourseGrade);
            courseGradeList.add(chineseCourseGrade);
            courseGradeList.add(primaryGeneralCourseGrade);

            studentStateList.add("小学");
            studentStateList.add("初中");
            studentStateList.add("高中");

            ArrayList<String> primaryStateGrade = new ArrayList<>();
            ArrayList<String> middleStateGrade = new ArrayList<>();
            ArrayList<String> highStateGrade = new ArrayList<>();

            primaryStateGrade.add("一年级至三年级");
            primaryStateGrade.add("四年级");
            primaryStateGrade.add("五年级");
            primaryStateGrade.add("六年级");

            middleStateGrade.add("初一");
            middleStateGrade.add("初二");
            middleStateGrade.add("初三");

            highStateGrade.add("高一");
            highStateGrade.add("高二");
            highStateGrade.add("高三");

            studentGradeList.add(primaryStateGrade);
            studentGradeList.add(middleStateGrade);
            studentGradeList.add(highStateGrade);

            teacherGradeList.add("大一");
            teacherGradeList.add("大二");
            teacherGradeList.add("大三");
            teacherGradeList.add("大四");
            teacherGradeList.add("大五");
            teacherGradeList.add("研一");
            teacherGradeList.add("研二");
            teacherGradeList.add("研三");
            teacherGradeList.add("博士");

            weekList.add("星期日");
            weekList.add("星期一");
            weekList.add("星期二");
            weekList.add("星期三");
            weekList.add("星期四");
            weekList.add("星期五");
            weekList.add("星期六");

            for (int i = 1; i < 100; i++) {
                ageList.add(i + "");
            }

            scoreRangeList.add("6 分以上");
            scoreRangeList.add("8 分以上");
            scoreRangeList.add("10 分以上");

            hasTeachChildCountRangeList.add("0个");
            hasTeachChildCountRangeList.add("1个");
            hasTeachChildCountRangeList.add("2个");
            hasTeachChildCountRangeList.add("3个");
            hasTeachChildCountRangeList.add("3个以上");
            hasTeachChildCountRangeList.add("5个以上");

            hasTeachChildTimeRangeList.add("20小时以内");
            hasTeachChildTimeRangeList.add("20-50小时");
            hasTeachChildTimeRangeList.add("50-100小时");
            hasTeachChildTimeRangeList.add("100小时以上");

        }
    }

}
