package com.scut.easyfe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.MyTimePicker;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.nostra13.universalimageloader.utils.L;
import com.roomorama.caldroid.CalendarHelper;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.BriefOrder;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.user.parent.RParentModifyOrders;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.TimeUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hirondelle.date4j.DateTime;

public class ModifyOrderActivity extends BaseActivity {
    private TextView mDateTextView;
    private TextView mTimeTextView;
    private TextView mTotalPriceTextView;
    private OptionsPickerView<String> mSinglePicker;
    private TimePickerView mDatePicker;
    private MyTimePicker mTimePicker;

    private List<BriefOrder> mOrders = new ArrayList<>();
    private BriefOrder exampleOrder = new BriefOrder();
    private String mDateStringToShow = "";
    private int mWeek = 0;
    private int mTeachTime = 120;
    private String mPeriod = "morning";
    private String mDate = "1970-01-01";


    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_modify_order);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle extra = intent.getExtras();
            if (null != extra) {
                mOrders = (List<BriefOrder>) extra.getSerializable(Constants.Key.ORDERS);
            }
        }
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("修改订单");

        if(mOrders.size() == 0){
            return;
        }

        mTimePicker = new MyTimePicker(this);

        mSinglePicker = new OptionsPickerView<>(this);
        mSinglePicker.setCancelable(true);

        Calendar calendar = Calendar.getInstance();
        mDatePicker = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        mDatePicker.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 1); //控制时间范围
        mDatePicker.setTime(new Date());
        mDatePicker.setCyclic(false);

        exampleOrder = mOrders.get(0);
        mDate = exampleOrder.getTeachTime().getDate();
        mPeriod = exampleOrder.getTeachTime().getTime();
        mTeachTime = exampleOrder.getTime();

        mDateTextView = OtherUtils.findViewById(this, R.id.modify_order_tv_date);
        mTimeTextView = OtherUtils.findViewById(this, R.id.modify_order_tv_time);
        mTotalPriceTextView = OtherUtils.findViewById(this, R.id.modify_order_tv_total_price);

        ((TextView)OtherUtils.findViewById(this, R.id.modify_order_tv_teacher)).setText(exampleOrder.getTeacherName());
        ((TextView)OtherUtils.findViewById(this, R.id.modify_order_tv_course)).setText(exampleOrder.getCourse());
        ((TextView)OtherUtils.findViewById(this, R.id.modify_order_tv_time)).setText(TimeUtils.getTimeFromMinute(exampleOrder.getTime()));
        ((TextView)OtherUtils.findViewById(this, R.id.modify_order_tv_price)).setText(
                String.format("%.2f", exampleOrder.getPrice()));
        ((TextView)OtherUtils.findViewById(this, R.id.modify_order_tv_tip)).setText(
                String.format("%.2f", exampleOrder.getSubsidy()));
        ((TextView)OtherUtils.findViewById(this, R.id.modify_order_tv_total_price)).setText(
                String.format("%.2f", exampleOrder.getTotalPrice()));

        if(mOrders.size() == 1){
            //修改一个订单
            ((TextView)OtherUtils.findViewById(this, R.id.modify_order_tv_date)).setText(
                    String.format("%s %s",
                            TimeUtils.getTime(TimeUtils.getDateFromString(exampleOrder.getTeachTime().getDate()), "yyyy 年 MM 月 dd 日(EEEE)") ,
                            exampleOrder.getTeachTime().getChineseTime()));

        }else{
            //修改多个订单
            ((TextView)OtherUtils.findViewById(this, R.id.modify_order_tv_date)).setText(
                    String.format("%s %s",
                            TimeUtils.getTime(TimeUtils.getDateFromString(exampleOrder.getTeachTime().getDate()), "EEEE") ,
                            exampleOrder.getTeachTime().getChineseTime()));
            mWeek = TimeUtils.getWeekIntFromString(exampleOrder.getTeachTime().getDate());
        }
    }

    @Override
    protected void initListener() {
        mDatePicker.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                if(date.getTime() < new Date().getTime()){
                    toast("请输入有效日期");
                    return;
                }

                mDateStringToShow = TimeUtils.getTime(date, "yyyy 年 MM 月 dd 日 (EEEE)");
                mDate = TimeUtils.getTime(date, "yyyy-MM-dd");
                LogUtils.i(Constants.Tag.ORDER_TAG, mDateStringToShow);
            }
        });

        mDatePicker.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
                if ("".equals(mDateStringToShow)) {
                    return;
                }

                showPeriodSelectView();
            }
        });

        mTimePicker.setOnPickListener(new MyTimePicker.OnPickListener() {
            @Override
            public void onPick(int hour, int minute) {
                String timeString = String.format("%s 小时 %s 分钟", hour, minute);
                mTeachTime = hour * 60 + minute;
                mTimeTextView.setText(timeString);
                exampleOrder.setTime(mTeachTime);
                mTotalPriceTextView.setText( String.format("%.2f", exampleOrder.getTotalPrice()));
            }
        });
    }

    public void onTimeClick(View view){
        OtherUtils.hideSoftInputWindow(mTimeTextView.getWindowToken());
        mTimePicker.setTitle("最短授课时长");
        mTimePicker.show();
    }

    public void onDateClick(View view){
        OtherUtils.hideSoftInputWindow(mDateTextView.getWindowToken());
        if(mOrders.size() == 1) {
            mDatePicker.show();
        }else{
            mSinglePicker.setTitle("每周授课时间");
            mSinglePicker.setPicker(Constants.Data.weekList);
            mSinglePicker.setSelectOptions(0);
            mSinglePicker.setCyclic(false);
            mSinglePicker.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(Object o) {
                    if ("".equals(mDateStringToShow)) {
                        return;
                    }

                    showPeriodSelectView();
                }
            });
            mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    mWeek = options1;
                    mDateStringToShow = Constants.Data.weekList.get(options1);
                }
            });
            mSinglePicker.show();
        }
    }


    private void showPeriodSelectView(){
        new AlertView("时间段", null, null, null,
                new String[]{"上午", "下午", "晚上"},
                ModifyOrderActivity.this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {
                    mDateStringToShow += " 上午";
                    mPeriod = "morning";
                } else if (position == 1) {
                    mDateStringToShow += " 下午";
                    mPeriod = "afternoon";
                } else if (position == 2) {
                    mDateStringToShow += " 晚上";
                    mPeriod = "evening";
                } else {
                    mDateStringToShow = "";
                    mPeriod = "";
                }
                mDateTextView.setText(mDateStringToShow);
            }
        }).show();
    }

    public void onConfirmModifyClick(View view){
        if(mOrders.size() == 1){
            mOrders.get(0).getTeachTime().setDate(mDate);
            mOrders.get(0).getTeachTime().setTime(mPeriod);
            mOrders.get(0).setTime(mTeachTime);
        }else {

            DateTime dateTime;
            for (BriefOrder order :
                    mOrders) {
                order.setTime(mTeachTime);
                order.getTeachTime().setTime(mPeriod);
                dateTime = CalendarHelper.convertDateToDateTime(TimeUtils.getDateFromString(order.getTeachTime().getDate()));
                while(dateTime.getWeekDay() - 1 != mWeek){
                    dateTime = dateTime.plusDays(1);
                }
                order.getTeachTime().setDate(TimeUtils.getTime(CalendarHelper.convertDateTimeToDate(dateTime), "yyyy-MM-dd"));
            }
        }

        RequestManager.get().execute(new RParentModifyOrders(App.getUser().getToken(), mOrders),
                new RequestListener<JSONObject>() {
                    @Override
                    public void onSuccess(RequestBase request, JSONObject result) {
                        toast(result.optString("message"));
                        redirectToActivity(mContext, MyOrderActivity.class);
                        finish();
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        toast(errorMsg);
                    }
                });
    }
}
