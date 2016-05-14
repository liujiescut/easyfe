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
        public static final boolean IS_SHOW_TOAST = true;  //是否显示使用LogUtil.toast出来的东西
        public static final boolean TO_MARKET = false;   //当分享时应用（例如wechat）不存在，是否跳转到应用市场
        public static final String APP_NAME = "直播易";
    }

    public class DefaultValue{
        public static final String DEFAULT_QINIU_URL = "http://7xrvd4.com1.z0.glb.clouddn.com/";            //七牛储存图片的地址
        public static final String DEFAULT_SHARE_LINK = "http://blog.csdn.net/ljyangscut/";
        public static final int DEFAULT_LOAD_COUNT = 15;
        public static final int MAX_BAD_RECORD = 2;
    }

    public class URL{
        public static final String URL_BASE = "http://121.42.37.233/";

        public static final String URL_LOGIN = URL_BASE + "Authentication/Login";
        public static final String URL_UPDATE_USER = URL_BASE + "user/data";
        public static final String URL_ORDERS = URL_BASE + "Order";
        public static final String URL_GET_ORDER_DETAIL = URL_BASE + "Order/Data";
        public static final String URL_GET_TEACHER_COMMENT = URL_BASE + "Parent/Teacher/Comments";
        public static final String URL_USER_INFO_MODIFY = URL_BASE + "User/Data";
        public static final String URL_GET_SPECIAL_ORDER = URL_BASE + "Share/DiscountOrder";
        public static final String URL_GET_MESSAGES = URL_BASE + "Share/Message";
        public static final String URL_GET_CHILD_GRADE = URL_BASE + "Share/Grade";
        public static final String URL_GET_COURSE = URL_BASE + "Share/Course";
        public static final String URL_GET_SCHOOL = URL_BASE + "Share/Education";
        public static final String URL_CALLBACK = URL_BASE + "Share/Feedback";
        public static final String URL_ONLINE_PARAMS = URL_BASE + "Share/OnlineParams";

        public static final String URL_TEACHER_REGISTER = URL_BASE + "Authentication/SignUp/Teacher";
        public static final String URL_TEACHER_INFO_MODIFY = URL_BASE + "Teacher/Data";
        public static final String URL_TEACHER_ADD_COURSE = URL_BASE + "Teacher/Price";
        public static final String URL_TEACHER_MULTI_BOOK_TIME_MODIFY = URL_BASE + "Teacher/MultiBookTime";
        public static final String URL_TEACHER_SINGLE_BOOK_TIME_MODIFY = URL_BASE + "Teacher/SingleBookTime";
        public static final String URL_PUBLISH_SPECIAL_ORDER = URL_BASE + "Teacher/DiscountOrder";
        public static final String URL_TEACHER_CONFIRM_ORDER = URL_BASE + "Teacher/Sure/Order";
        public static final String URL_TEACHER_CANCEL_ORDERS = URL_BASE + "Teacher/Cancel/Order";

        public static final String URL_WALLET_INFO = URL_BASE + "Money/Wallet";
        public static final String URL_WALLET_WITHDRAW = URL_BASE + "Money/Withdraw";

        public static final String URL_PARENT_REGISTER = URL_BASE + "Authentication/SignUp/Parent";
        public static final String URL_CONFIRM_SPECIAL_ORDER = URL_BASE + "Parent/Sure/Special";
        public static final String URL_CONFIRM_SINGLE_ORDER = URL_BASE + "Parent/Sure/Single";
        public static final String URL_CONFIRM_MULTI_ORDER = URL_BASE + "Parent/Sure/Multi";
        public static final String URL_PARENT_CANCEL_ORDERS = URL_BASE + "Parent/Cancel/Order";
        public static final String URL_PARENT_MODIFY_ORDERS = URL_BASE + "Order";
        public static final String URL_SINGLE_RESERVE = URL_BASE + "Book/Single";
        public static final String URL_MULTI_RESERVE = URL_BASE + "Book/Multi";
        public static final String URL_GET_TEACHER_DETAIL_INFO = URL_BASE + "Parent/Teacher/Data";
        public static final String URL_COMMENT_TEACHER = URL_BASE + "Parent/Comment/Teacher";

        public static final String URL_GET_SPREAD_REWARD_LIST = URL_BASE + "Reward/Discount";
        public static final String URL_GET_SPREAD_REWARD =URL_BASE + "Reward/Discount";
        public static final String URL_GET_TEACHER_INVITE_REWARD_LIST = URL_BASE + "Reward/Invite/Teacher";
        public static final String URL_GET_TEACHER_INVITE_REWARD = URL_BASE + "Reward/Invite/Teacher";
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
        public static final String CALLBACK_HINT_TEXT =  "callback_hint_text";
        public static final String CONFIRM_ORDER_TYPE =  "confirm_order_type";
        public static final String ORDER =  "order";
        public static final String ORDERS =  "orders";
        public static final String TEACH_WEEK =  "teach_week";
        public static final String RESERVE_WAY = "reserve_way";
        public static final String TO_TEACHER_INFO_ACTIVITY_TYPE = "to_teacher_info_activity_type";
        public static final String TO_TEACHER_REGISTER_ONE_ACTIVITY_TYPE = "to_teacher_register_one_activity_type";
        public static final String TO_PARENT_REGISTER_ACTIVITY_TYPE = "to_parent_register_activity_type";
        public static final String TO_TEACH_TIME_ACTIVITY_TYPE = "to_teach_time_activity_type";
        public static final String TO_SPECIAL_TIME_ACTIVITY_TYPE = "to_special_time_activity_type";
        public static final String TO_TEACH_COURSE_ACTIVITY_TYPE = "to_special_time_activity_type";
        public static final String IS_FIRST_IN_SPECIAL_TIME_ACTIVITY  = "is_fist_time_in_special_activity";
        public static final String QN_TOKEN = "qn_token";
        public static final String TEACHER_ID = "teacher_id";

        public static final String CHAT_OR_CIRCLE = "chat_or_circle";
        public static final String SHARE_FLAG = "flag";
        public static final String DATA_KEY = "data_key";
    }

    /**
     * 一些标识符
     */
    public class Identifier {
        /** 用户分类 */
        public static final int USER_UNDEFINED = -1;      //未注册
        public static final int USER_WEB_REGISTED = 0;    //被邀请未注册
        public static final int USER_TEACHER = 1;         //家教
        public static final int USER_PARENT = 2;          //家长
        public static final int USER_TP = 3;              //即是家长又是家教

        /** 性别 */
        public static final int MALE = 1;
        public static final int FEMALE = 0;

        /** 订单类型 */
        public static final int ORDER_ALL = -1;
        public static final int ORDER_RESERVATION = 0;
        public static final int ORDER_TO_DO = 1;
        public static final int ORDER_MODIFIED_WAILT_CONFIRM = 2; //前台将该订单显示在已预约那里
        public static final int ORDER_COMPLETED = 3;
        public static final int ORDER_INVALID = 4;

        /** 我的订单页面状态 */
        public static final int STATE_NORMAL = 0;
        public static final int STATE_EDIT = 1;

        /** 反馈页面的标识,表明是哪种反馈(需求反馈,应用反馈,投诉) */
        public static final int CALLBACK_NEED = 1;           //首页预约不到想要的点这里进行反馈
        public static final int CALLBACK_APP = 2;            //更多页面应用反馈
        public static final int CALLBACK_COMPLAINTS = 3;     //完成订单后投诉

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
        //Todo 换换换
        public static final String WECHAT_APP_ID = "wx9b7ee5f6471184da";                            //微信分享的APP_ID
        public static final String WEIBO_APP_KEY = "1033545693";                                    //微博分享
        public static final String QQ_APP_ID = "1105349762";

        public static final String WECHAT_PACKAGE_NAME = "com.tencent.mm";                          //第三方应用包名
        public static final String WEIBO_PACKAGE_NAME = "com.sina.weibo";
        public static final String QQ_PACKAGE_NAME = "com.tencent.mobileqq";

        public static final String DEFAULT_QN_TOKEN = "R2Rq9_dBXtrL6wqLwA8_GC6EZNR9JU06xaGegd19:mKzKD3gB-mkdQpjt1BVtBAZmNYw=:eyJzY29wZSI6ImVhc3lmZSIsImRlYWRsaW5lIjoxNDg0OTIxMjY4fQ==";



        public static final ArrayList<Integer> teachTimeHourList = new ArrayList<>();               //授课时间之小时
        public static final ArrayList<ArrayList<Integer>> teachTimeMinuteList = new ArrayList<>();  //授课时间之分钟
        public static final ArrayList<Integer> trafficTimeHourList = new ArrayList<>();               //授课时间之小时
        public static final ArrayList<ArrayList<Integer>> trafficTimeMinuteList = new ArrayList<>();  //授课时间之分钟
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
        public static ArrayList<String> mBankNameList = new ArrayList<>();

        static {

            teachTimeHourList.add(0);
            teachTimeHourList.add(1);
            teachTimeHourList.add(2);
            teachTimeHourList.add(3);

            for (int i = 0; i < teachTimeHourList.size(); i++) {
                ArrayList<Integer> teachTimeMinutes = new ArrayList<>();
                teachTimeMinutes.add(0);
                teachTimeMinutes.add(30);
                teachTimeMinuteList.add(teachTimeMinutes);
            }

            trafficTimeHourList.add(0);
            trafficTimeHourList.add(1);
            trafficTimeHourList.add(2);

            for (int i = 0; i < trafficTimeHourList.size(); i++) {
                ArrayList<Integer> trafficTimeMinutes = new ArrayList<>();
                trafficTimeMinutes.add(0);
                trafficTimeMinutes.add(10);
                trafficTimeMinutes.add(20);
                trafficTimeMinutes.add(30);
                trafficTimeMinutes.add(40);
                trafficTimeMinutes.add(50);
                trafficTimeMinuteList.add(trafficTimeMinutes);
            }

            mBankNameList.add("工商银行");
            mBankNameList.add("建设银行");
            mBankNameList.add("中国银行");
            mBankNameList.add("农业银行");
            mBankNameList.add("交通银行");
            mBankNameList.add("招商银行");
            mBankNameList.add("邮政储蓄银行");
            mBankNameList.add("光大银行");
            mBankNameList.add("民生银行");
            mBankNameList.add("平安银行");
            mBankNameList.add("浦发银行");
            mBankNameList.add("中信银行");
            mBankNameList.add("兴业银行");
            mBankNameList.add("华夏银行");
            mBankNameList.add("北京银行");
            mBankNameList.add("上海银行");

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

            for (int i = 2; i <= 20; i++) {
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
