package com.scut.easyfe.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidGridAdapter;
import com.scut.easyfe.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hirondelle.date4j.DateTime;

/**
 * 日历页面每个项显示的Adapter
 * Created by jay on 16/3/24.
 */
public class DayAdapter extends CaldroidGridAdapter {
    private List<DateTime> mWorkDays = new ArrayList<>();

    /**
     * 构造函数
     *
     * @param context      上下文
     * @param month        当前月份
     * @param year         当前年
     * @param caldroidData Caldroid的一些数据
     * @param extraData    自定义的数据
     */
    public DayAdapter(Context context, int month, int year, Map<String, Object> caldroidData, Map<String, Object> extraData) {
        super(context, month, year, caldroidData, extraData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cellView = convertView;

        if (convertView == null) {
            cellView = inflater.inflate(R.layout.item_calendar_day, null);
        }

        cellView.setBackgroundColor(resources.getColor(R.color.text_area_bg_inner));

        TextView dayTextView = (TextView) cellView.findViewById(R.id.calendar_day_tv_day);
        ImageView pointImageView = (ImageView) cellView.findViewById(R.id.calendar_day_iv_point);

        //当前 Item 所对应的 DateTime
        DateTime currentDateTime = this.datetimeList.get(position);
        Resources resources = context.getResources();


        dayTextView.setTextColor(resources.getColor(R.color.text_area_text_color));
        dayTextView.setBackgroundColor(resources.getColor(R.color.text_area_bg_inner));


        dayTextView.setText("" + currentDateTime.getDay());

        //处理Disable的Date跟不在minDay跟maxDay之间的Date
        boolean isDisableDate = (minDateTime != null && currentDateTime.lt(minDateTime))
                || (maxDateTime != null && currentDateTime.gt(maxDateTime))
                || (disableDates != null && disableDates.indexOf(currentDateTime) != -1);
        dayTextView.setTextColor(resources.getColor(isDisableDate ? R.color.text_area_text_color_alpha : R.color.text_area_text_color));

        //处理一个页面内不是当前月份日期的隐藏
        dayTextView.setVisibility(currentDateTime.getMonth() != month ? View.INVISIBLE : View.VISIBLE);

        // 处理选中的日期
        if (selectedDates != null && selectedDates.indexOf(currentDateTime) != -1) {
            dayTextView.setTextColor(resources.getColor(R.color.title_text_color));
            dayTextView.setBackgroundColor(resources.getColor(R.color.theme_color));
        }

        pointImageView.setVisibility(mWorkDays.contains(currentDateTime) && !isDisableDate && currentDateTime.getMonth() == month
                ? View.VISIBLE : View.GONE);
        return cellView;
    }

    public List<DateTime> getWorkDays() {
        return mWorkDays;
    }

    public void setWorkDays(List<DateTime> mWorkDays) {
        this.mWorkDays = mWorkDays;
    }
}
