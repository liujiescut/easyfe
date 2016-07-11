package com.scut.easyfe.ui.activity.order;

import android.content.DialogInterface;
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
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.app.Variables;
import com.scut.easyfe.entity.Course;
import com.scut.easyfe.entity.PollingData;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.event.DataChangeEvent;
import com.scut.easyfe.event.PDHandler;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.info.RGetCourse;
import com.scut.easyfe.network.request.order.RPublishSpread;
import com.scut.easyfe.ui.activity.MainActivity;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.TimeUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    private OptionsPickerView<String> mPicker;
    private ArrayList<String> mGrade = new ArrayList<>();
    private static List<Course> mCourses = new ArrayList<>();
    private static ArrayList<String> mCourseNames = new ArrayList<>();
    private int mSelectedCoursePosition = -1;
    private Date mTeachDate = null;
    private String mPeriod = "";
    private int mTeachTime = 60;
    private long mPrice = 100;


    private boolean mISLoadingCloseByUser = true;


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
        mTimePicker.setToShowTime(Constants.Data.teachTimeHourList, Constants.Data.teachTimeMinuteList);
        mPicker = new OptionsPickerView<>(mContext);

        Calendar calendar = Calendar.getInstance();
        mDatePicker = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        mDatePicker.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 2); //控制时间范围
        mDatePicker.setTime(new Date());
        mDatePicker.setCyclic(false);

        mTeachDate = new Date();
        mPeriod = "morning";
        mDateString = TimeUtils.getTime(mTeachDate, "yyyy 年 MM 月 dd 日 (EEEE)");
        mDateString += " 上午";
        mDateTextView.setText(mDateString);

        mTimeTextView.setText(TimeUtils.getTimeFromMinute(mTeachTime));

        mPriceTextView.setText(String.format(Locale.CHINA, "%d 元/小时", mPrice / 100));
    }

    @Override
    protected void fetchData() {

        startLoading("加载数据中", new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mISLoadingCloseByUser) {
                    toast("数据加载失败");
                    finish();
                }
            }
        });

        RequestManager.get().execute(new RGetCourse(), new RequestListener<List<Course>>() {
            @Override
            public void onSuccess(RequestBase request, List<Course> result) {
                mCourses.clear();
                mCourses.addAll(result);
                mCourseNames.clear();
                for (Course course :
                        mCourses) {
                    mCourseNames.add(course.getCourse());
                }

                if(mCourses.size() > 0){
                    mSelectedCoursePosition = 0;
                    mGrade = mCourses.get(0).getGrade();
                    mCourseTextView.setText(mCourseNames.get(0));
                    mGradeTextView.setText(mGrade.get(0));
                }

                mISLoadingCloseByUser = false;
                stopLoading();
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {

            }
        });
    }

    @Override
    protected void initListener() {
        mTimePicker.setOnPickListener(new MyTimePicker.OnPickListener() {
            @Override
            public void onPick(int hour, int minute) {
                String timeString = String.format("%s 小时 %s 分钟", hour, minute);
                mTeachTime = hour * 60 + minute;
                mTimeTextView.setText(timeString);
            }
        });

        mDatePicker.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                if(date.getTime() < new Date().getTime()){
                    toast("请选择有效日期");
                    return;
                }

                mTeachDate = date;
                mDateString = TimeUtils.getTime(date, "yyyy 年 MM 月 dd 日 (EEEE)");
                LogUtils.i(Constants.Tag.ORDER_TAG, mDateString);
            }
        });

        mDatePicker.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
                if ("".equals(mDateString)) {
                    return;
                }

                new AlertView("时间段", null, null, null,
                        new String[]{"上午", "下午", "晚上"},
                        PublishSpreadActivity.this, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0) {
                            mDateString += " 上午";
                            mPeriod = "morning";
                        } else if (position == 1) {
                            mDateString += " 下午";
                            mPeriod = "afternoon";
                        } else if (position == 2) {
                            mDateString += " 晚上";
                            mPeriod = "evening";
                        } else {
                            mDateString = "";
                            mPeriod = "";
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

        if(mSelectedCoursePosition == -1 || mGrade.size() == 0){
            toast("请先选择授课课程");
            return;
        }

        mPicker.setPicker(mGrade);
        mPicker.setCyclic(false);
        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mGradeTextView.setText(mGrade.get(options1));
            }
        });
        mPicker.show();

    }

    /**
     * 点击选择授课课程
     */
    public void onCourseClick(View view) {

        mPicker.setPicker(mCourseNames);
        mPicker.setCyclic(false);
        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mSelectedCoursePosition = options1;
                mGrade = mCourses.get(mSelectedCoursePosition).getGrade();
                mCourseTextView.setText(mCourseNames.get(mSelectedCoursePosition));
                if(mGrade.size() != 0){
                    mGradeTextView.setText(mGrade.get(0));
                }else {
                    mGradeTextView.setText("");
                }
            }
        });
        mPicker.show();

    }

    /**
     * 点击选择授课时间
     */
    public void onDateClick(View view) {
        OtherUtils.hideSoftInputWindow(view.getWindowToken());
        mDateString = "";
        mPeriod = "";
        mTeachDate = null;
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
                if(message == null || message.length() == 0){
                    return;
                }

                mPrice = Integer.valueOf(message) * 100;
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

    public void onBackClick(View view){
        finish();
    }

    /**
     * 点击选择授课年级
     */
    public void onConfirmClick(View view) {
        Order order = new Order();
        order.setCourse(mCourseTextView.getText().toString());
        order.setGrade(mGradeTextView.getText().toString());
        order.setTime(mTeachTime);
        order.setPrice(mPrice);
        if (null != mTeachDate) {
            order.getTeachTime().setDate(TimeUtils.getTime(mTeachDate, "yyyy-MM-dd"));
            order.getTeachTime().setTime(mPeriod);
        }

        if(!validate(order)){
            return;
        }

        RequestManager.get().execute(new RPublishSpread(App.getUser().getToken(), order), new RequestListener<JSONObject>() {
            @Override
            public void onSuccess(RequestBase request, JSONObject result) {
//                String orderId = result.optString("orderId");
//                PollingData.PollingOrderData data = new PollingData.PollingOrderData();
//                data.setOrderId(orderId);
//                if(!Variables.localData.getMine().getOrder().contains(data)){
//                    Variables.localData.getMine().getOrder().add(data);
//                    Variables.localData.save2Cache(App.getUser().getPhone());
//                }
//
//                App.get().getEventBus().post(new DataChangeEvent(PDHandler.get().getLatestData()));

                DialogUtils.makeConfirmDialog(PublishSpreadActivity.this, "提示", "特价订单发布成功\n待工作人员审核通过后即可在特价订单页可见", new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        redirectToActivity(mContext, MainActivity.class);
                    }
                }).show();
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
            }
        });

    }

    private boolean validate(Order order){
        if(null == order.getCourse() || order.getCourse().length() == 0){
            toast("请选择授课课程");
            return false;
        }

        if(null == order.getGrade() || order.getGrade().length() == 0){
            toast("请选择授课年级");
            return false;
        }

        if(order.getTime()== 0){
            toast("请选择授课时长");
            return false;
        }
        if(order.getTeachTime().getDate().length() == 0 || order.getTeachTime().getTime().length() == 0){
            toast("请选择授课时间");
            return false;
        }

        if(order.getPrice() == 0){
            toast("请选择单位价格");
            return false;
        }

        return true;
    }

}
