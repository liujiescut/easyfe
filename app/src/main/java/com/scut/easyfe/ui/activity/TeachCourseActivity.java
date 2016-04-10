package com.scut.easyfe.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Course;
import com.scut.easyfe.entity.TeachableCourse;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
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

    private ArrayList<String> mGrade = new ArrayList<>();
    private static List<Course> mCourses = new ArrayList<>();
    private static List<String> mCourseNames = new ArrayList<>();
    private List<TeachableCourse> mTeachableCourses = new ArrayList<>();

    private int mSelectedCoursePosition = 0;

    private boolean mISLoadingCloseByUser = true;
    private User mUser;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_teach_course);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUser = App.getUser();
    }

    @Override
    protected void initData() {
        mUser = App.getUser();
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

        mTeachableCourses.addAll(mUser.getTeacherMessage().getTeacherPrice());
        for (TeachableCourse teachableCourse :
                mTeachableCourses) {
            addTeachableCourseItem(teachableCourse.getCourse(), teachableCourse.getGrade(), teachableCourse.getPrice());
        }

    }

    @Override
    protected void initListener() {
        mCourseGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedCoursePosition = position;
                mCourseAdapter.setSelectedPosition(position);
                mCourseAdapter.notifyDataSetChanged();
                mGrade = mCourses.get(position).getGrade();
                mPicker.setPicker(mCourses.get(position).getGrade());

                refreshGradeLinearLayout(mCourseNames.get(position));
            }
        });

        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                addTeachableCourseItem(mCourseNames.get(mSelectedCoursePosition), mGrade.get(options1), 100);
            }
        });
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
                if (mCourses.size() > 0) {
                    mCourseAdapter.setSelectedPosition(mSelectedCoursePosition);
                    mCourseAdapter.notifyDataSetChanged();
                    mGrade = mCourses.get(0).getGrade();
                    mPicker.setPicker(mGrade);
                    mPicker.setCyclic(false);

                    refreshGradeLinearLayout(mCourseNames.get(0));
                } else {
                    toast("数据加载失败");
                }

                mISLoadingCloseByUser = false;
                stopLoading();
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {

            }
        });

    }

    private void addTeachableCourseItem(String courseName, String  mGradeName, float price){
        int itemViewId = courseName.hashCode() * mGradeName.hashCode() + mGradeName.hashCode();
        View itemView = mGradeLinearLayout.findViewById(itemViewId);
        if (null == itemView) {
            itemView = getLayoutInflater().inflate(R.layout.item_course_price, null);
            itemView.setId(itemViewId);
            mGradeLinearLayout.addView(itemView);
        }

        TeachableCourse teachableCourse = new TeachableCourse(itemViewId, courseName, mGradeName, price);
        if(!mTeachableCourses.contains(teachableCourse)) {
            mTeachableCourses.add(teachableCourse);
        }
        ((TextView) itemView.findViewById(R.id.item_course_price_tv_state)).setText(Course.getStateFromGrade(mGradeName));
        ((TextView) itemView.findViewById(R.id.item_course_price_tv_grade)).setText(Course.getGradeFromGrade(mGradeName));
        final TextView priceTextView = ((TextView) itemView.findViewById(R.id.item_course_price_tv_price));
        priceTextView.setText(String.format("%.0f 元/小时", price));
        itemView.setTag(teachableCourse);

        final View finalItemView = itemView;
        priceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogUtils.makeInputDialog(mContext, "收费", InputType.TYPE_CLASS_NUMBER, new DialogUtils.OnInputListener() {
                    @Override
                    public void onFinish(String message) {
                        LogUtils.i(Constants.Tag.TEACHER_REGISTER_TAG, message);
                        try {
                            TeachableCourse clickPriceItem = (TeachableCourse) finalItemView.getTag();
                            clickPriceItem.setPrice(Integer.parseInt(message));
                            for (TeachableCourse item :
                                    mTeachableCourses) {
                                if (item.get_id() == clickPriceItem.get_id()) {
                                    item.setPrice(clickPriceItem.getPrice());
                                }
                            }
                            priceTextView.setText(String.format("%s 元/小时", message));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            toast("只能输入数字");
                        }
                    }
                }).show();

            }
        });
    }

    private void refreshGradeLinearLayout(String course){
        if(null == mGradeLinearLayout){
            return;
        }

        for (int index = 0; index < mGradeLinearLayout.getChildCount(); index++){
            TeachableCourse teachableCourse = (TeachableCourse) mGradeLinearLayout.getChildAt(index).getTag();
            if(teachableCourse.getCourse().equals(course)){
                mGradeLinearLayout.getChildAt(index).setVisibility(View.VISIBLE);
            }else{
                mGradeLinearLayout.getChildAt(index).setVisibility(View.GONE);
            }
        }
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
        mUser.getTeacherMessage().getTeacherPrice().clear();
        mUser.getTeacherMessage().getTeacherPrice().addAll(mTeachableCourses);
        if(mTeachableCourses.size() == 0){
            toast("请先选择授课课程");
            return;
        }

        App.setUser(mUser);

        Bundle extras = new Bundle();
        extras.putBoolean(Constants.Key.IS_REGISTER, true);
        redirectToActivity(this, TeacherRegisterTwoActivity.class, extras);
    }

    /**
     * 点击返回
     */
    public void onBackClick(View view) {
        finish();
    }
}
