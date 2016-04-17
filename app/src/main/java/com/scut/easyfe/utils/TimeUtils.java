package com.scut.easyfe.utils;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

import com.roomorama.caldroid.CalendarHelper;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.fragment.CalendarFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import hirondelle.date4j.DateTime;

/**
 * 关于时间的工具类
 * Created by jay on 16/4/14.
 */
public class TimeUtils {
    /**
     * 转换Date
     * @param date     需要转化的Date
     * @param format   转换的格式
     * @return         转换后字符串
     */
    public static String getTime(Date date, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 转换分钟数为小时加分钟
     * @param minutes 被转换分钟数
     * @return  转换后字符串
     */
    public static String getTimeFromMinute(int minutes){
        int hours = minutes / 60;
        int minute = minutes % 60;
        return String.format("%s小时%s分钟", hours, minute);
    }

    public static Date getDateFromString(String dateString) {
        return getDateFromString(dateString, "yyyy-MM-dd");
    }

    @Nullable
    public static Date getDateFromString(String dateString, String formatString){
        DateFormat format = new SimpleDateFormat(formatString, Locale.CHINA);

        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int getAgeFromBirthday(Date birthdayDate){
        DateTime today = CalendarHelper.convertDateToDateTime(new Date());
        DateTime birthday = CalendarHelper.convertDateToDateTime(birthdayDate);

        return today.getYear() - birthday.getYear() + 1;
    }

    public static int getAgeFromBirthday(long birthdayLong){
        DateTime today = CalendarHelper.convertDateToDateTime(new Date());
        DateTime birthday = CalendarHelper.convertDateToDateTime(new Date(birthdayLong));

        return today.getYear() - birthday.getYear() + 1;
    }

    /**
     * 将0-6的数字转换为星期几
     */
    public static String getWeekStringFromInt(@IntRange(from = 0, to = 6) int weekDay){
        return Constants.Data.weekList.get(weekDay);
    }

    public static int getWeekIntFromString(String weekString){
        if(weekString.equals("星期日") || weekString.equals("星期天")){
            return 0;
        }
        if(weekString.equals("星期一")){
            return 1;
        }
        if(weekString.equals("星期二")){
            return 2;
        }
        if(weekString.equals("星期三")){
            return 3;
        }
        if(weekString.equals("星期四")){
            return 4;
        }
        if(weekString.equals("星期五")){
            return 5;
        }
        if(weekString.equals("星期六")){
            return 6;
        }


        return -1;
    }

}
