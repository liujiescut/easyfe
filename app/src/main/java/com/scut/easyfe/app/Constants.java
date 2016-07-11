package com.scut.easyfe.app;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

/**
 * 全局常量 {@link }
 * Created by jay on 15/9/11.
 */
public class Constants {
    @SuppressWarnings("unused")
    public class Config{
        public static final boolean ISDEBUG = false;      //是否在debug模式，可以设置一些只有debug才会有的东西
        public static final boolean ISLOG = true;        //是否显示log
        public static final boolean IS_SHOW_TOAST = true;  //是否显示使用LogUtil.toast出来的东西
        public static final boolean TO_MARKET = false;   //当分享时应用（例如wechat）不存在，是否跳转到应用市场
        public static final String APP_NAME = "优升学家教";
        public static final String TEACHER_UNCHECKED_INFO = "信息审核中,通过后才可用该功能";
        public static final String TEACHER_FORBIDDEN_INFO = "家长才可用该功能哟~";
        public static final String PARENT_FORBIDDEN_INFO = "家教才可用该功能哟~";
        public static final int VERIFY_INTERVAL = 60;
    }

    public class DefaultValue{
        public static final String DEFAULT_QINIU_URL = "http://7xrvd4.com1.z0.glb.clouddn.com/";            //七牛储存图片的地址
        public static final String DEFAULT_SHARE_LINK_INVITE_PARENT = "http://www.cadena.cn:3000/invite_parent.html?";
        public static final String DEFAULT_SHARE_LINK_INVITE_TEACHER = "http://www.cadena.cn:3000/invite_teacher.html?";
        public static final int DEFAULT_LOAD_COUNT = 15;
        public static final int MAX_BAD_RECORD = 3;
        public static final int DEFAULT_TUTOR_PRICE = -1;
    }

    public class URL{
        public static final String URL_BASE = "http://120.55.113.12/";
//        public static final String URL_BASE = "http://www.ysxeducation.com/";

        /** 用户相关 */
        public static final String URL_LOGIN = URL_BASE + "Authentication/Login";                   //登录
        public static final String URL_GET_SMS = URL_BASE + "Authentication/Sms";                   //发送验证码
        public static final String URL_CHECK_VERIFY_CODE = URL_BASE + "Authentication";             //验证验证码
        public static final String URL_UPDATE_USER = URL_BASE + "user/data";                        //更新用户信息
        public static final String URL_USER_INFO_MODIFY = URL_BASE + "User/Data";                   //修改用户信息
        public static final String URL_GET_USER_LEVEL = URL_BASE + "Share/Level";                   //用户等级
        public static final String URL_WALLET_INFO = URL_BASE + "Money/Wallet";                     //钱包信息
        public static final String URL_WALLET_WITHDRAW = URL_BASE + "Money/Withdraw";               //提现

        /** 家教相关 */
        public static final String URL_TEACHER_REGISTER =
                URL_BASE + "Authentication/SignUp/Teacher";                                         //家教注册
        public static final String URL_TEACHER_INFO_MODIFY = URL_BASE + "Teacher/Data";             //修改家教基本信息
        public static final String URL_TEACHER_ADD_COURSE = URL_BASE + "Teacher/Price";             //增加可教授课程
        public static final String URL_TEACHER_MULTI_BOOK_TIME_MODIFY =
                URL_BASE + "Teacher/MultiBookTime";                                                 //修改多次预约时间
        public static final String URL_TEACHER_SINGLE_BOOK_TIME_MODIFY =
                URL_BASE + "Teacher/SingleBookTime";                                                //修改单次预约时间

        /** 家长相关 */
        public static final String URL_PARENT_REGISTER = URL_BASE + "Authentication/SignUp/Parent"; //家长注册
        public static final String URL_GET_MATCH_TICKET = URL_BASE + "Parent/SuitableCoupon";       //合适优惠券
        public static final String URL_GET_MY_TICKET = URL_BASE + "Parent/Coupon";                  //我的优惠券

        /** 订单相关 */
        public static final String URL_GET_SPECIAL_ORDER = URL_BASE + "Share/DiscountOrder";        //特价订单列表
        public static final String URL_ORDERS = URL_BASE + "Order";                                 //我的订单列表
        public static final String URL_GET_ORDER_DETAIL = URL_BASE + "Order/Data";                  //订单详情
        public static final String URL_GET_TEACHER_COMMENT = URL_BASE + "Parent/Teacher/Comments";  //获取家教评论
        public static final String URL_PUBLISH_SPECIAL_ORDER =
                URL_BASE + "Teacher/DiscountOrder";                                                 //家教发布特价订单
        public static final String URL_TEACHER_CONFIRM_ORDER =
                URL_BASE + "Teacher/Sure/Order";                                                    //家教确认订单
        public static final String URL_TEACHER_CANCEL_ORDERS =
                URL_BASE + "Teacher/Cancel/Order";                                                  //家教取消订单
        public static final String URL_MODIFY_TUTOR = URL_BASE + "Teacher/Order/ThisTeachDetail";   //家教修改本次专业辅导信息
        public static final String URL_TEACHER_REPORT = URL_BASE + "Teacher/Report/Order";          //家教完成课程并反馈
        public static final String URL_SINGLE_RESERVE = URL_BASE + "Book/Single";                   //家长单次预约筛选
        public static final String URL_MULTI_RESERVE = URL_BASE + "Book/Multi";                     //家长多次预约筛选
        public static final String URL_CONFIRM_SPECIAL_ORDER = URL_BASE + "Parent/Sure/Special";    //家长确认特价订单
        public static final String URL_CONFIRM_SINGLE_ORDER = URL_BASE + "Parent/Sure/Single";      //家长确认单次预约订单
        public static final String URL_CONFIRM_MULTI_ORDER = URL_BASE + "Parent/Sure/Multi";        //家长确认多次预约订单
        public static final String URL_PARENT_CANCEL_ORDERS = URL_BASE + "Parent/Cancel/Order";     //家长取消订单
        public static final String URL_PARENT_MODIFY_ORDERS = URL_BASE + "Order";                   //家长修改订单
        public static final String URL_GET_TEACHER_DETAIL_INFO = URL_BASE + "Parent/Teacher/Data";  //家长获取家教具体信息
        public static final String URL_COMMENT_TEACHER = URL_BASE + "Parent/Comment/Teacher";       //家长评论家教
        public static final String URL_GET_FEEDBACK_REPORT = URL_BASE + "Share/Order/Report";       //获取反馈报告列表

        /** 公用接口 */
        public static final String URL_GET_MESSAGES = URL_BASE + "Share/Message";                   //消息中心
        public static final String URL_GET_CHILD_GRADE = URL_BASE + "Share/Grade";                  //学生年级
        public static final String URL_GET_COURSE = URL_BASE + "Share/Course";                      //可教授课程
        public static final String URL_GET_TUTOR_COURSE = URL_BASE + "Share/Professional/Course";   //专业辅导课程
        public static final String URL_GET_SCHOOL = URL_BASE + "Share/Education";                   //预约页面可选的家教所在学校
        public static final String URL_GET_TUTOR_INFO = URL_BASE + "Share/Professional/Data";       //获取专业辅导相关的信息(知识点,复习卷)
        public static final String URL_CALLBACK = URL_BASE + "Share/Feedback";                      //反馈
        public static final String URL_ONLINE_PARAMS = URL_BASE + "Share/OnlineParams";             //在线参数
        public static final String URL_POLLING_DATA = URL_BASE + "Share/DataUpdateTime";            //轮询

        /** 会员活动相关 */
        public static final String URL_GET_VIP_EVENT = URL_BASE + "Share/VipEvent";                 //会员活动列表
        public static final String URL_GET_MY_VIP_EVENT = URL_BASE + "Share/User/VipEvent";         //我的会员活动列表

        /** 支付相关 */
        public static final String URL_PAY_CALLBACK =
                "http://www.ysxeducation.com/money/notify/alipay";                                  //支付宝支付回调接口
        public static final String URL_CASH_PAY = URL_BASE + "Money/Pay/Cash";                      //现金支付
        public static final String URL_ALIPAY_PAY = URL_BASE + "Money/PrePay/AliPay";               //支付宝支付
        public static final String URL_WECHAT_PAY = URL_BASE + "Money/PrePay/Wechat";               //微信支付
        public static final String URL_BUY_VIP_EVENT_BY_SCORE = URL_BASE + "Money/Pay/Score";       //积分购买会员活动

        /** 任务奖励相关 */
        public static final String URL_GET_SPREAD_REWARD_LIST =
                URL_BASE + "Reward/Discount";                                                       //特价推广奖励
        public static final String URL_GET_SPREAD_REWARD =URL_BASE + "Reward/Discount";             //领取特价推广奖励
        public static final String URL_GET_TEACHER_INVITE_REWARD_LIST =
                URL_BASE + "Reward/Invite/Teacher";                                                 //获取邀请家教奖励列表
        public static final String URL_GET_TEACHER_INVITE_REWARD =
                URL_BASE + "Reward/Invite/Teacher";                                                 //领取邀请家教奖励
        public static final String URL_GET_PARENT_INVITE_REWARD_LIST =
                URL_BASE + "Reward/Invite/Parent";                                                  //获取邀请家长奖励列表
        public static final String URL_GET_PARENT_INVITE_REWARD =
                URL_BASE + "Reward/Invite/Parent";                                                  //领取邀请家长奖励
        public static final String URL_GET_PARENT_COMPLETE_COURSE_REWARD_LIST =
                URL_BASE + "Reward/FinishCourse/Parent";                                            //获取家长完成课时奖励列表
        public static final String URL_GET_PARENT_COMPLETE_COURSE_REWARD =
                URL_BASE + "Reward/FinishCourse/Parent";                                            //领取家长完成课时奖励
        public static final String URL_GET_TEACHER_COMPLETE_COURSE_REWARD_LIST =
                URL_BASE + "Reward/FinishCourse/Teacher";                                           //获取家教完成课时奖励列表
        public static final String URL_GET_TEACHER_COMPLETE_COURSE_REWARD =
                URL_BASE + "Reward/FinishCourse/Teacher";                                           //领取家教完成课时奖励
        public static final String URL_GET_PARENT_COMPLETE_ORDER_REWARD =
                URL_BASE + "Reward/FinishOrder";                                                    //家长领取现金券(完成订单后)
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
        public static final String POLLING_TAG = BASE_TAG + "polling";
    }

    public class Key{
        public static final String USER_CACHE = "user_cache";
        public static final String POLLING_DATA_CACHE = "polling_data_cache";
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
        public static final String ORDER_ID =  "order_id";
        public static final String ORDER =  "order";
        public static final String ORDERS =  "orders";
        public static final String TUTOR_DETAIL =  "tutor_detail";
        public static final String TUTOR_UPDATE_TO_THIS_TEACH_DETAIL =  "tutor_update_to_this_teach_detail";
        public static final String TEACH_WEEK =  "teach_week";
        public static final String RESERVE_WAY = "reserve_way";
        public static final String TO_TEACHER_INFO_ACTIVITY_TYPE = "to_teacher_info_activity_type";
        public static final String TO_TEACHER_REGISTER_ONE_ACTIVITY_TYPE = "to_teacher_register_one_activity_type";
        public static final String TO_PARENT_REGISTER_ACTIVITY_TYPE = "to_parent_register_activity_type";
        public static final String TO_TEACHER_REPORT_ACTIVITY_TYPE = "to_teacher_report_activity_type";
        public static final String IS_MY_VIP_ACTIVITY = "is_my_vip_activity";
        public static final String TO_TEACH_TIME_ACTIVITY_TYPE = "to_teach_time_activity_type";
        public static final String TO_SPECIAL_TIME_ACTIVITY_TYPE = "to_special_time_activity_type";
        public static final String TO_TEACH_COURSE_ACTIVITY_TYPE = "to_special_time_activity_type";
        public static final String IS_FIRST_IN_SPECIAL_TIME_ACTIVITY  = "is_fist_time_in_special_activity";
        public static final String QN_TOKEN = "qn_token";
        public static final String TEACHER_ID = "teacher_id";
        public static final String WEB_TITLE = "web_title";
        public static final String WEB_URL = "web_url";
        public static final String NEED_REFRESH = "need_refresh";
    }

    /**
     * 一些标识符
     */
    @SuppressWarnings("unused")
    public class Identifier {
        /** 用户分类 */
        public static final int USER_UNDEFINED = -1;      //未注册
        public static final int USER_WEB_REGISTER = 0;    //被邀请未注册
        public static final int USER_TEACHER = 1;         //家教
        public static final int USER_PARENT = 2;          //家长
        public static final int USER_TP = 3;              //即是家长又是家教

        /** 性别 */
        public static final int MALE = 1;
        public static final int FEMALE = 0;

        /**
         * 支付类型(0支付订单, 1支付会员活动, 2充值)
         */
        public static final int BUY_ORDER = 0;
        public static final int BUY_VIP_EVENT = 1;
        public static final int BUY_RECHARGE = 2;

        /**
         * 教师类型(0待审核, 1已审核, 2已拒绝)
         */
        public static final int TEACHER_UNCHECKED = 0;
        public static final int TEACHER_CHECKED = 1;
        public static final int TEACHER_REJECT = 2;

        /** 订单类型 */
        public static final int ORDER_ALL = -1;
        public static final int ORDER_RESERVATION = 0;
        public static final int ORDER_TO_DO = 1;
        public static final int ORDER_MODIFIED_WAIT_CONFIRM = 2; //前台将该订单显示在已预约那里
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

        /** 到家教反馈页面的功能 */
        public static final int TYPE_REPORT = 0;            //家教填写反馈报告
        public static final int TYPE_CONFIRM = 1;           //家长付款完确认反馈报告
        public static final int TYPE_SHOW = 2;              //显示反馈报告

        /** 专业辅导情况辅导方式 */
        public static final int TUTOR_WAY_KNOWLEDGE = 1;
        public static final int TUTOR_WAY_PAPER = 2;

        /** 排序方式 */
        public static final String SORT_BY_NAME = "name";
        public static final String SORT_BY_TIME = "time";
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

    @SuppressWarnings("unused")
    public static class Data {
        public static final String WECHAT_APP_ID = "wx5242965d8b495478";                            //微信分享的APP_ID
        public static final String WECHAT_PARTNER_ID = "1352452902";
        public static final String WEIBO_APP_KEY = "1033545693";                                    //微博分享
        public static final String QQ_APP_ID = "1105349762";

        public static final String WECHAT_PACKAGE_NAME = "com.tencent.mm";                          //第三方应用包名
        public static final String WEIBO_PACKAGE_NAME = "com.sina.weibo";
        public static final String QQ_PACKAGE_NAME = "com.tencent.mobileqq";

        public static final String DEFAULT_QN_TOKEN = "R2Rq9_dBXtrL6wqLwA8_GC6EZNR9JU06xaGegd19:mKzKD3gB-mkdQpjt1BVtBAZmNYw=:eyJzY29wZSI6ImVhc3lmZSIsImRlYWRsaW5lIjoxNDg0OTIxMjY4fQ==";
        public static final String ALIPAY_RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
        public static final String ALIPAY_RSA_PRIVATE =
                "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOcFZ2189zt1453g" +
                "9jQSW3oSwU8hT3DSwB4N3LspQ/3axpMyfS+CBcsEI22D7LyptaV9+S/WJcCBkP3p" +
                "e/l6ImWa/uHhL33wDUtRK7Lw5xXXJ3ouh4fVf43n3goai7bmM1Sce6XljHUhDeFS" +
                "wO5kv/aF1oacQhFaj7aAmVt3afd3AgMBAAECgYAWyv+zW7sA0NbOQt3HsW/5EVSl" +
                "PAIcfY6a6H84sA+XQjiJWVfkkg2ERDpCmduHma7kTxOA1r4OyjnF831Z1eUCtVSQ" +
                "zGhuLMa7vN+roBaYIMamX+UbWIdnj8IQQmtJ4IM53qUXjSv66uc6OeaeySjSv3TN" +
                "7GV1qvb0NldewcU88QJBAPXCqBpnCPTIiUlvPvXe9lWRthXyjWeedT3sJ5j86Y67" +
                "NngD3b0640ATqY3Zr49vc/kBXxeZkGg4q/tdxYPFEMMCQQDwpYjXmbc11A8/5o8S" +
                "JQQ2fQPlczJtIw1z9OSf+24XhkbExrhPdCthaDzzWbetBo5qpKO1njUSdr8+smWG" +
                "h5M9AkBD9ddcrb95SXdFemDzxvthwFCVEyTtUZFkVUjB8rZ3hOfUGmWYLY+oKYfK" +
                "tTbqOEzev/VW5l1bpMhSU6UKAlXXAkEAvuCeVV0w7DpjSi1HyH2iBNJOl94y3kxS" +
                "bGqQObGGVpSEZ6q0IRmlGyNCTUgZn922czJaREHUGOwlr8ZzLy00hQJBAM6gwMLl" +
                "u/7E0919aQF/6omk4rj0UQh2h+GBiP4P+EZi7ZqdBazSd2ou1cKV5SkWf4znXGGc" +
                "NrF6vay4QEnIhL4=";
        public static final String ALIPAY_PID = "2088221383776744";
        public static final String ALIPAY_SELLER = "jiajiaoyi2016@163.com";

        public static final ArrayList<Integer> teachTimeHourList = new ArrayList<>();               //授课时间之小时
        public static final ArrayList<ArrayList<Integer>> teachTimeMinuteList = new ArrayList<>();  //授课时间之分钟
        public static final ArrayList<Integer> trafficTimeHourList = new ArrayList<>();             //交通时间之小时
        public static final ArrayList<ArrayList<Integer>> trafficTimeMinuteList = new ArrayList<>();//交通时间之分钟
        public static final ArrayList<String> genderList = new ArrayList<>();                       //可选性别
        public static final ArrayList<String> teacherGradeList = new ArrayList<>();                 //家教的年级列表
        public static final ArrayList<String> weekList = new ArrayList<>();
        public static final ArrayList<String> ageList = new ArrayList<>();
        public static final ArrayList<String> scoreRangeList = new ArrayList<>();
        public static final ArrayList<String> hasTeachChildCountRangeList = new ArrayList<>();
        public static final ArrayList<String> hasTeachChildTimeRangeList = new ArrayList<>();
        public static final ArrayList<String> knowledgeEasyLevelList = new ArrayList<>();
        public static final ArrayList<String> paperEasyLevelList = new ArrayList<>();
        public static ArrayList<String> bankNameList = new ArrayList<>();
        public static ArrayList<String> tutorGradeList = new ArrayList<>();
        public static ArrayList<String> rightPercentList = new ArrayList<>();

        static {
            rightPercentList.add("50% 以下");
            rightPercentList.add("50% - 60%");
            rightPercentList.add("60% - 70%");
            rightPercentList.add("70% - 80%");
            rightPercentList.add("80% - 90%");
            rightPercentList.add("90% 以上");

            tutorGradeList.add("初中 一年级");
            tutorGradeList.add("初中 二年级");
            tutorGradeList.add("初中 三年级");

            tutorGradeList.add("高中 一年级");
            tutorGradeList.add("高中 二年级");
            tutorGradeList.add("高中 三年级");

            knowledgeEasyLevelList.add("简单");
            knowledgeEasyLevelList.add("较易");
            knowledgeEasyLevelList.add("一般");
            knowledgeEasyLevelList.add("较难");
            knowledgeEasyLevelList.add("困难");

            paperEasyLevelList.add("一般难度");
            paperEasyLevelList.add("升级难度");

            teachTimeHourList.add(0);
            teachTimeHourList.add(1);
            teachTimeHourList.add(2);
            teachTimeHourList.add(3);
            teachTimeHourList.add(4);

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

            bankNameList.add("工商银行");
            bankNameList.add("建设银行");
            bankNameList.add("中国银行");
            bankNameList.add("农业银行");
            bankNameList.add("交通银行");
            bankNameList.add("招商银行");
            bankNameList.add("邮政储蓄银行");
            bankNameList.add("光大银行");
            bankNameList.add("民生银行");
            bankNameList.add("平安银行");
            bankNameList.add("浦发银行");
            bankNameList.add("中信银行");
            bankNameList.add("兴业银行");
            bankNameList.add("华夏银行");
            bankNameList.add("北京银行");
            bankNameList.add("上海银行");

            genderList.add("女");
            genderList.add("男");

            teacherGradeList.add("大一");
            teacherGradeList.add("大二");
            teacherGradeList.add("大三");
            teacherGradeList.add("大四");
            teacherGradeList.add("大五");
            teacherGradeList.add("研一");
            teacherGradeList.add("研二");
            teacherGradeList.add("研三");
            teacherGradeList.add("博士");
            teacherGradeList.add("专业老师");

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
