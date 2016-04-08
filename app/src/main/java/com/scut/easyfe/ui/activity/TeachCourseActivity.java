package com.scut.easyfe.ui.activity;

import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Course;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.info.RGetChildGrade;
import com.scut.easyfe.network.request.info.RGetCourse;
import com.scut.easyfe.ui.adapter.CourseAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 可教授课程页面
 *
 * @author jay
 */
public class TeachCourseActivity extends BaseActivity {
    private GridView mCourseGridView;
    private LinearLayout mGradeLinearLayout;
    private OptionsPickerView<String> mPicker;

    CourseAdapter mCourseAdapter;

    private ArrayList<String> mState = new ArrayList<>();
    private ArrayList<ArrayList<String>> mGrade = new ArrayList<>();
    private ArrayList<GradePriceItem> mPrices = new ArrayList<>();
    private static List<Course> mCourses = new ArrayList<>();
    private static List<String> mCourseNames = new ArrayList<>();
    private int mCurrentPosition = -1;


    private boolean mGetGradeSuccess = false;
    private boolean mGetCourseSuccess = false;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_teach_course);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("家教注册 - 可教授课程及收费");
        mCourseGridView = OtherUtils.findViewById(this, R.id.teach_course_gv_course);
        mGradeLinearLayout = OtherUtils.findViewById(this, R.id.teach_course_ll_container);
        mCourseAdapter = new CourseAdapter(mCourseNames);
        mCourseAdapter.setItemClickable(false);
        mCourseGridView.setAdapter(mCourseAdapter);

        mPicker = new OptionsPickerView<>(mContext);
    }

    @Override
    protected void initListener() {
        mCourseGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCourseAdapter.setSelectedPosition(position);
                mCourseAdapter.notifyDataSetChanged();
            }
        });

        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                int itemViewId = options1 * 1000 + option2;
                View itemView = mGradeLinearLayout.findViewById(itemViewId);
                if (null == itemView) {
                    itemView = getLayoutInflater().inflate(R.layout.item_course_price, null);
                    itemView.setId(itemViewId);
                    mGradeLinearLayout.addView(itemView);
                }

                int defaultPrice = 100;
                GradePriceItem gradePriceItem = new GradePriceItem(itemViewId, options1, option2, defaultPrice);
                mPrices.add(gradePriceItem);
                ((TextView) itemView.findViewById(R.id.item_course_price_tv_state)).setText(mState.get(options1));
                ((TextView) itemView.findViewById(R.id.item_course_price_tv_grade)).setText(mGrade.get(options1).get(option2));
                final TextView priceTextView = ((TextView) itemView.findViewById(R.id.item_course_price_tv_price));
                priceTextView.setText(String.format("%d 元/小时", defaultPrice));
                priceTextView.setTag(gradePriceItem);

                priceTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        DialogUtils.makeInputDialog(mContext, "收费", InputType.TYPE_CLASS_NUMBER, new DialogUtils.OnInputListener() {
                            @Override
                            public void onFinish(String message) {
                                LogUtils.i(Constants.Tag.TEACHER_REGISTER_TAG, message);
                                try {
                                    GradePriceItem clickPriceItem = (GradePriceItem) v.getTag();
                                    clickPriceItem.price = Integer.parseInt(message);
                                    for (GradePriceItem item :
                                            mPrices) {
                                        if (item.id == clickPriceItem.id) {
                                            item.price = clickPriceItem.price;
                                        }
                                    }
                                    priceTextView.setText(String.format("%s 元/小时", message));
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                    toast("只能输入数字");
                                }

                                for (GradePriceItem i :
                                        mPrices) {
                                    LogUtils.i(Constants.Tag.TEACHER_REGISTER_TAG, i.id + " " + i.price);
                                }
                            }
                        }).show();

                    }
                });
            }
        });
    }

    @Override
    protected void fetchData() {
        startLoading("加载数据中");
        RequestManager.get().execute(new RGetChildGrade(), new RequestListener<List<List>>() {

            @Override
            public void onSuccess(RequestBase request, List<List> result) {
                mState = (ArrayList<String>) result.get(0);
                mGrade = (ArrayList<ArrayList<String>>) result.get(1);
                mPicker.setPicker(mState, mGrade, true);
                mPicker.setCyclic(false);

                mGetGradeSuccess = true;
                if (mGetCourseSuccess) {
                    stopLoading();
                }
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
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
                mCourseAdapter.notifyDataSetChanged();

                mGetCourseSuccess = true;
                if (mGetGradeSuccess) {
                    stopLoading();
                }
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {

            }
        });

    }

    /**
     * 点击增加可教授课程
     */
    public void onAddClick(View view) {
        if (null != mPicker) {
            mPicker.show();
        }
    }

    /**
     * 点击确认并保存
     */
    public void onConfirmClick(View view) {
        redirectToActivity(mContext, TeacherRegisterTwoActivity.class);
    }

    /**
     * 点击返回
     */
    public void onBackClick(View view) {
        finish();
    }

    /**
     * 授课年级及收费的具体项
     */
    class GradePriceItem {
        int id;
        int state;
        int grade;
        int price;

        public GradePriceItem(int id, int state, int grade, int price) {

            this.id = id;
            this.state = state;
            this.grade = grade;
            this.price = price;
        }
    }
}
