package com.scut.easyfe.ui.activity;

import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.MyTimePicker;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 发布推广(特价订单)页面
 *
 * @author jay
 */
public class PublishSpreadActivity extends BaseActivity {

    private TextView mGradeTextView;
    private TextView mCourseTextView;
    private TextView mDateTextView;
    private TextView mTimeTextView;
    private TextView mPriceTextView;

    private MyTimePicker mTimePicker;
    private TimePickerView mDatePicker;

    private String mDateString = "";


    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_publish_spread);
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("发布特价订单");

        mGradeTextView = OtherUtils.findViewById(this, R.id.publish_spread_grade);
        mCourseTextView = OtherUtils.findViewById(this, R.id.publish_spread_course);
        mDateTextView = OtherUtils.findViewById(this, R.id.publish_spread_date);
        mTimeTextView = OtherUtils.findViewById(this, R.id.publish_spread_time);
        mPriceTextView = OtherUtils.findViewById(this, R.id.publish_spread_price);

        mTimePicker = new MyTimePicker(this);

        Calendar calendar = Calendar.getInstance();
        mDatePicker = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        mDatePicker.setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR)); //控制时间范围
        mDatePicker.setTime(new Date());
        mDatePicker.setCyclic(false);
    }

    @Override
    protected void initListener() {
        mTimePicker.setOnPickListener(new MyTimePicker.OnPickListener() {
            @Override
            public void onPick(int hour, int minute) {
                String timeString = String.format("%s 小时 %s 分钟", hour, minute);
                int timeInt = hour * 60 + minute;
                mTimeTextView.setText(timeString);
            }
        });

        mDatePicker.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                mDateString = OtherUtils.getTime(date, "yyyy 年 MM 月 dd 日 (EEEE)");
                LogUtils.i(Constants.Tag.ORDER_TAG, mDateString);
            }
        });

        mDatePicker.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
                if("".equals(mDateString)){
                    return;
                }

                new AlertView("时间段", null, null, null,
                        new String[]{"上午", "下午", "晚上"},
                        PublishSpreadActivity.this, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0) {
                            mDateString += " 上午";
                        } else if (position == 1) {
                            mDateString += " 下午";
                        } else if (position == 2) {
                            mDateString += " 晚上";
                        } else {
                            mDateString = "";
                        }
                        mDateTextView.setText(mDateString);
                    }
                }).show();

            }
        });
    }

    /**
     * 点击选择授课年级
     */
    public void onGradeClick(View view) {

    }

    /**
     * 点击选择授课课程
     */
    public void onCourseClick(View view) {

    }

    /**
     * 点击选择授课时间
     */
    public void onDateClick(View view) {
        OtherUtils.hideSoftInputWindow(view.getWindowToken());
        mDateString = "";
        mDateTextView.setText(mDateString);
        mDatePicker.show();
    }

    /**
     * 点击选择授课时长
     */
    public void onTimeClick(View view) {
        OtherUtils.hideSoftInputWindow(view.getWindowToken());
        mTimePicker.show();
    }

    /**
     * 点击选择单位价格
     */
    public void onPriceClick(View view) {
        DialogUtils.makeInputDialog(mContext, "单位价格", InputType.TYPE_CLASS_NUMBER, new DialogUtils.OnInputListener() {
            @Override
            public void onFinish(String message) {
                LogUtils.i(Constants.Tag.ORDER_TAG, message);
                OtherUtils.hideSoftInputWindow(mPriceTextView.getWindowToken());
                try {
                    mPriceTextView.setText(String.format("%s 元/小时", message));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    toast("只能输入数字");
                }
            }
        }).show();
    }

    /**
     * 点击选择授课年级
     */
    public void onConfirmClick(View view) {

    }
}
