package com.scut.easyfe.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
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
import com.scut.easyfe.entity.Course;
import com.scut.easyfe.entity.School;
import com.scut.easyfe.entity.book.BaseBookCondition;
import com.scut.easyfe.entity.book.MultiBookCondition;
import com.scut.easyfe.entity.book.SingleBookCondition;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.entity.test.ToSelectItem;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.book.RMultiBook;
import com.scut.easyfe.network.request.info.RGetCourse;
import com.scut.easyfe.network.request.book.RSingleBook;
import com.scut.easyfe.network.request.info.RGetSchool;
import com.scut.easyfe.ui.activity.order.SearchResultActivity;
import com.scut.easyfe.ui.adapter.SelectItemAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.ListViewUtil;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 预约界面
 */
public class BookActivity extends BaseActivity {
    private TextView mGradeTextView;
    private TextView mCourseTextView;
    private TextView mTeachDateLabelTextView;
    private TextView mTeachDateTextView;
    private TextView mTeachTimeLabelTextView;
    private TextView mTeachTimeTextView;
    private TextView mStudentAgeTextView;
    private TextView mStudentGenderTextView;
    private TextView mSchoolLabelTextView;
    private TextView mPriceLabelTextView;
    private TextView mScoreLabelTextView;
    private ListView mSchoolListView;
    private ListView mPriceListView;
    private ListView mScoreListView;
    private ScrollView mContainerScrollView;
    private EditText mTeacherNameEditText;
    ArrayList<ToSelectItem> mSchoolItems;
    ArrayList<ToSelectItem> mPriceItems;
    ArrayList<ToSelectItem> mScoreItems;

    private SelectItemAdapter mSchoolAdapter;

    private OptionsPickerView<String> mSinglePicker;
    private MyTimePicker mTimePicker;
    private TimePickerView mDatePicker;

    private ArrayList<String> mGrade = new ArrayList<>();
    private static List<Course> mCourses = new ArrayList<>();
    private static ArrayList<String> mCourseNames = new ArrayList<>();
    private int mSelectedCoursePosition = -1;

    private boolean mGetCourseSuccess = false;
    private boolean mGetSchoolSuccess = false;

    private int mReserveType = Constants.Identifier.RESERVE_MULTI;

    private String mDateStringToShow = "";
    private String mPeriodStringToShow = "";

    private User mUser;
    private String mDate = TimeUtils.getTime(new Date(), "yyyy-MM-dd");
    private int mChildAge = 10;
    private int mChildGender = Constants.Identifier.FEMALE;
    private int mWeek = 0;
    private String mPeriod = "morning";
    private int mTeachTime = 120;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_book);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(null != intent){
            Bundle extras = intent.getExtras();
            if(null != extras){
                mReserveType = extras.getInt(Constants.Key.RESERVE_WAY, Constants.Identifier.RESERVE_MULTI);
            }
        }

        mUser = App.getUser(false);

        mSchoolItems = new ArrayList<>();
        mPriceItems = new ArrayList<>();
        mScoreItems = new ArrayList<>();

        mScoreItems.add(new ToSelectItem("6 分以上", false, "6"));
        mScoreItems.add(new ToSelectItem("7 分以上", false, "7"));
        mScoreItems.add(new ToSelectItem("9 分以上", false, "9"));

        try {
            mPriceItems.add(new ToSelectItem("40元以下", false, new JSONArray("[0,40]")));
            mPriceItems.add(new ToSelectItem("40-50(包括50)", false, new JSONArray("[40,50]")));
            mPriceItems.add(new ToSelectItem("50-60(包括60)", false, new JSONArray("[50,60]")));
            mPriceItems.add(new ToSelectItem("60-70(包括70)", false, new JSONArray("[60,70]")));
            mPriceItems.add(new ToSelectItem("70-80(包括80)", false, new JSONArray("[70,80]")));
            mPriceItems.add(new ToSelectItem("80-90(包括90)", false, new JSONArray("[80,90]")));
            mPriceItems.add(new ToSelectItem("90以上", false, new JSONArray("[90," + Integer.MAX_VALUE + "]")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText(
                mReserveType == Constants.Identifier.RESERVE_MULTI ? "多次预约" : "单次预约");

        mGradeTextView = OtherUtils.findViewById(this, R.id.book_tv_grade);
        mCourseTextView = OtherUtils.findViewById(this, R.id.book_tv_course);
        mTeachDateTextView = OtherUtils.findViewById(this, R.id.book_tv_date);
        mTeachDateLabelTextView = OtherUtils.findViewById(this, R.id.book_tv_date_label);
        mTeachTimeTextView = OtherUtils.findViewById(this, R.id.book_tv_time);
        mTeachTimeLabelTextView = OtherUtils.findViewById(this, R.id.book_tv_time_label);
        mStudentAgeTextView = OtherUtils.findViewById(this, R.id.book_tv_student_age);
        mStudentGenderTextView = OtherUtils.findViewById(this, R.id.book_tv_student_gender);
        mSchoolLabelTextView = OtherUtils.findViewById(this, R.id.book_tv_school_label);
        mPriceLabelTextView = OtherUtils.findViewById(this, R.id.book_tv_price_label);
        mScoreLabelTextView = OtherUtils.findViewById(this, R.id.book_tv_score_label);
        mTeacherNameEditText = OtherUtils.findViewById(this, R.id.book_et_teacher_name);
        mSchoolListView = OtherUtils.findViewById(this, R.id.book_lv_school);
        mPriceListView = OtherUtils.findViewById(this, R.id.book_lv_price);
        mScoreListView = OtherUtils.findViewById(this, R.id.book_lv_score);
        mContainerScrollView = OtherUtils.findViewById(this, R.id.book_sv_container);

        mSchoolAdapter = new SelectItemAdapter(this, mSchoolItems);
        mSchoolListView.setAdapter(mSchoolAdapter);
        mPriceListView.setAdapter(new SelectItemAdapter(this, mPriceItems));
        mScoreListView.setAdapter(new SelectItemAdapter(this, mScoreItems));
        ListViewUtil.setListViewHeightBasedOnChildren(mPriceListView);
        ListViewUtil.setListViewHeightBasedOnChildren(mScoreListView);

        mContainerScrollView.smoothScrollTo(0, 0);

        mSinglePicker = new OptionsPickerView<>(this);
        mSinglePicker.setCancelable(true);

        mTimePicker = new MyTimePicker(this);
        mTimePicker.setToShowTime(Constants.Data.teachTimeHourList, Constants.Data.teachTimeMinuteList);

        Calendar calendar = Calendar.getInstance();
        mDatePicker = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        mDatePicker.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 2); //控制时间范围
        mDatePicker.setTime(new Date());
        mDatePicker.setCyclic(false);

        mTeachTimeTextView.setText("2 小时 0 分钟");

        mStudentAgeTextView.setText(String.format("%d", mChildAge));

        if(mReserveType == Constants.Identifier.RESERVE_MULTI){
            mTeachDateLabelTextView.setText("每周授课时间");
            mTeachTimeLabelTextView.setText(getResources().getString(R.string.teach_time_length));
            mTeachDateTextView.setText("星期日 上午");
        }else {
            mTeachDateTextView.setText(String.format("%s %s", TimeUtils.getTime(new Date(), "yyyy年MM月dd日(EEEE)"), "上午"));
        }
    }

    @Override
    protected void initListener() {
        mSchoolLabelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSchoolLabelTextView.setBackgroundResource(R.mipmap.book_selected_bg);
                mPriceLabelTextView.setBackgroundResource(R.mipmap.book_unselected_bg);
                mScoreLabelTextView.setBackgroundResource(R.mipmap.book_unselected_bg);
                mSchoolListView.setVisibility(View.VISIBLE);
                mPriceListView.setVisibility(View.GONE);
                mScoreListView.setVisibility(View.GONE);
            }
        });

        mPriceLabelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSchoolLabelTextView.setBackgroundResource(R.mipmap.book_unselected_bg);
                mPriceLabelTextView.setBackgroundResource(R.mipmap.book_selected_bg);
                mScoreLabelTextView.setBackgroundResource(R.mipmap.book_unselected_bg);
                mSchoolListView.setVisibility(View.GONE);
                mPriceListView.setVisibility(View.VISIBLE);
                mScoreListView.setVisibility(View.GONE);
            }
        });

        mScoreLabelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSchoolLabelTextView.setBackgroundResource(R.mipmap.book_unselected_bg);
                mPriceLabelTextView.setBackgroundResource(R.mipmap.book_unselected_bg);
                mScoreLabelTextView.setBackgroundResource(R.mipmap.book_selected_bg);
                mSchoolListView.setVisibility(View.GONE);
                mPriceListView.setVisibility(View.GONE);
                mScoreListView.setVisibility(View.VISIBLE);
            }
        });

        mTimePicker.setOnPickListener(new MyTimePicker.OnPickListener() {
            @Override
            public void onPick(int hour, int minute) {
                String timeString = String.format("%s 小时 %s 分钟", hour, minute);
                mTeachTime = hour * 60 + minute;
                mTeachTimeTextView.setText(timeString);
            }
        });

        mDatePicker.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
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

                mPeriod = "";
                mPeriodStringToShow = "";
                showPeriodSelectView();
            }
        });
    }

    @Override
    protected void fetchData() {
        startLoading("加载数据中", new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (! (mGetCourseSuccess && mGetSchoolSuccess)) {
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

                if (mCourseNames.size() > 0) {
                    mSelectedCoursePosition = 0;
                    mCourseTextView.setText(mCourseNames.get(0));
                    mGrade = mCourses.get(0).getGrade();
                    mGradeTextView.setText(mGrade.get(0));
                }

                mGetCourseSuccess = true;
                if(mGetSchoolSuccess) {
                    stopLoading();
                }
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
                stopLoading();
            }
        });

        RequestManager.get().execute(new RGetSchool(), new RequestListener<List<School>>() {
            @Override
            public void onSuccess(RequestBase request, List<School> result) {
                mSchoolItems.clear();
                for (School school :
                        result) {
                    mSchoolItems.add(new ToSelectItem(school.getSchool(), false));
                }
                mSchoolAdapter.notifyDataSetChanged();
                ListViewUtil.setListViewHeightBasedOnChildren(mSchoolListView);

                mGetSchoolSuccess = true;
                if (mGetCourseSuccess) {
                    stopLoading();
                }
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
                stopLoading();
            }
        });

    }

    private void showPeriodSelectView(){
        new AlertView("时间段", null, null, null,
                new String[]{"上午", "下午", "晚上"},
                BookActivity.this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {
                    mPeriodStringToShow += " 上午";
                    mPeriod = "morning";
                } else if (position == 1) {
                    mPeriodStringToShow += " 下午";
                    mPeriod = "afternoon";
                } else if (position == 2) {
                    mPeriodStringToShow += " 晚上";
                    mPeriod = "evening";
                } else {
                    mPeriodStringToShow = "";
                    mPeriod = "";
                }
                mTeachDateTextView.setText(mDateStringToShow + mPeriodStringToShow);
            }
        }).show();
    }

    public void onGradeClick(View view){
        OtherUtils.hideSoftInputWindow(mGradeTextView.getWindowToken());
        if (mSinglePicker.isShowing()) {
            mSinglePicker.dismiss();
            return;
        }

        if(mSelectedCoursePosition == -1 || mGrade.size() == 0){
            toast("请先选择授课课程");
            return;
        }

        mSinglePicker.setTitle("选择授课年级");
        mSinglePicker.setPicker(mGrade);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mGradeTextView.setText(mGrade.get(options1));
            }
        });
        mSinglePicker.show();
    }

    public void onCourseClick(View view) {
        OtherUtils.hideSoftInputWindow(mCourseTextView.getWindowToken());
        if (mSinglePicker.isShowing()) {
            mSinglePicker.dismiss();
            return;
        }

        mSinglePicker.setTitle("选择授课课程");
        mSinglePicker.setPicker(mCourseNames);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mSelectedCoursePosition = options1;
                mGrade = mCourses.get(mSelectedCoursePosition).getGrade();
                mCourseTextView.setText(mCourseNames.get(mSelectedCoursePosition));
                mGradeTextView.setText(mGrade.get(0));
            }
        });
        mSinglePicker.show();

    }

    public void onDateClick(View view){
        OtherUtils.hideSoftInputWindow(mTeachDateTextView.getWindowToken());
        if(mReserveType == Constants.Identifier.RESERVE_SINGLE) {
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

                    mPeriod = "";
                    mPeriodStringToShow = "";
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

    public void onTimeClick(View view){
        OtherUtils.hideSoftInputWindow(mTeachTimeTextView.getWindowToken());
        mTimePicker.setTitle("授课时长");
        mTimePicker.show();
    }

    public void onStudentAgeClick(View view){
        OtherUtils.hideSoftInputWindow(mStudentAgeTextView.getWindowToken());
        if (mSinglePicker.isShowing()) {
            mSinglePicker.dismiss();
            return;
        }

        mSinglePicker.setTitle("被授课对象年龄");
        mSinglePicker.setPicker(Constants.Data.ageList);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setOnDismissListener(null);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mStudentAgeTextView.setText(Constants.Data.ageList.get(options1));
                mChildAge = Integer.parseInt(Constants.Data.ageList.get(options1));
            }
        });
        mSinglePicker.show();
    }

    public void onStudentGenderClick(View view){
        OtherUtils.hideSoftInputWindow(mStudentAgeTextView.getWindowToken());
        if (mSinglePicker.isShowing()) {
            mSinglePicker.dismiss();
            return;
        }

        mSinglePicker.setTitle("被授课对象性别");
        mSinglePicker.setPicker(Constants.Data.genderList);
        mSinglePicker.setSelectOptions(0);
        mSinglePicker.setOnDismissListener(null);
        mSinglePicker.setCyclic(false);
        mSinglePicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mStudentGenderTextView.setText(Constants.Data.genderList.get(options1));
                mChildGender = options1;
            }
        });
        mSinglePicker.show();
    }

    public void onSearchClick(View view){
        if(OtherUtils.isFastDoubleClick()){
            return;
        }

        if(App.getUser().isTeacher()){
            toast(Constants.Config.TEACHER_FORBIDDEN_INFO);
            return;
        }

        startLoading("筛选中");

        if(mReserveType == Constants.Identifier.RESERVE_SINGLE){
            final SingleBookCondition condition = getSingleBookCondition();
            if(null == condition){
                return;
            }

            RequestManager.get().execute(new RSingleBook(condition), new RequestListener<List<Order>>() {
                @Override
                public void onSuccess(RequestBase request, List<Order> result) {
                    for (Order order :
                            result) {

                        order.setSubsidy(order.getTeacher().getTeacherMessage().getSubsidy());      //同步一下(搜索只返回 teacher 里面的)

                        /** 加上这些信息在后面预约会用到 */
                        order.getTeachTime().setDate(condition.getSingleBookTime().getDate());
                        order.getTeachTime().setTime(condition.getSingleBookTime().getTime());
                        order.setChildAge(condition.getChildAge());
                        order.setChildGender(condition.getChildGender());
                        order.setCourse(condition.getCourse());
                        order.setGrade(condition.getGrade());
                        order.setTime(condition.getTime());
                    }

                    stopLoading();

                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.Key.RESERVE_WAY, mReserveType);
                    bundle.putSerializable(Constants.Key.ORDERS, new ArrayList<>(result));
                    redirectToActivity(mContext, SearchResultActivity.class, bundle);
                }

                @Override
                public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                    toast(errorMsg);
                    stopLoading();
                }
            });

        }else{
            final MultiBookCondition condition = getMultiBookCondition();
            if(null == condition){
                return;
            }

            RequestManager.get().execute(new RMultiBook(condition), new RequestListener<List<Order>>() {
                @Override
                public void onSuccess(RequestBase request, List<Order> result) {

                    for (Order order :
                            result) {
                        order.setSubsidy(order.getTeacher().getTeacherMessage().getSubsidy());      //同步一下(搜索只返回 teacher 里面的)

                        /** 加上这些信息在后面预约会用到 */
                        order.getTeachTime().setDate(TimeUtils.getWeekStringFromInt(condition.getMultiBookTime().getWeekDay()));
                        order.getTeachTime().setTime(condition.getMultiBookTime().getTime());
                        order.setChildAge(condition.getChildAge());
                        order.setChildGender(condition.getChildGender());
                        order.setCourse(condition.getCourse());
                        order.setGrade(condition.getGrade());
                        order.setTime(condition.getTime());
                    }

                    stopLoading();

                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.Key.RESERVE_WAY, mReserveType);
                    bundle.putSerializable(Constants.Key.ORDERS, new ArrayList<>(result));
                    redirectToActivity(mContext, SearchResultActivity.class, bundle);
                }

                @Override
                public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                    toast(errorMsg);
                    stopLoading();
                }
            });
        }
    }


    /**
     * 获取单次预约的搜索条件
     * @return 搜索条件
     */
    private SingleBookCondition getSingleBookCondition(){
        SingleBookCondition condition = new SingleBookCondition();
        getBaseCondition(condition);
        condition.getSingleBookTime().setDate(mDate);
        condition.getSingleBookTime().setTime(mPeriod);

        return condition;
    }

    private MultiBookCondition getMultiBookCondition(){
        MultiBookCondition condition  = new MultiBookCondition();
        getBaseCondition(condition);

        condition.getMultiBookTime().setWeekDay(mWeek);
        condition.getMultiBookTime().setTime(mPeriod);

        return condition;
    }


    /**
     * 获取单次预约多次预约共有的条件
     * @param condition  预约条件
     */
    private void getBaseCondition(BaseBookCondition condition){
        condition.setToken(App.getUser(false).getToken());
        condition.setCourse(mCourseTextView.getText().toString());
        condition.setGrade(mGradeTextView.getText().toString());
        condition.setChildGender(mChildGender);
        condition.setChildAge(mChildAge);
        condition.setTime(mTeachTime);

        if(mTeacherNameEditText.getText().toString().length() != 0){
            condition.setTeacherName(mTeacherNameEditText.getText().toString());
        }


        for(ToSelectItem school : mSchoolItems){
            if(school.isSelected()) {
                condition.getSchool().add(school.getText());
            }
        }

        for(ToSelectItem price : mPriceItems){
            if(price.isSelected()){
                condition.getPrice().add((JSONArray)price.getFormatText());
            }
        }

        for(ToSelectItem score : mScoreItems){
            if(score.isSelected()){
                condition.setScore(Integer.parseInt((String)score.getFormatText()));
                break;
            }
        }
    }
    public void onBackClick(View view){
        finish();
    }
}
