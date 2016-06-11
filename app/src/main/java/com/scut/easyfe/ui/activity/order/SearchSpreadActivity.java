package com.scut.easyfe.ui.activity.order;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.scut.easyfe.R;
import com.scut.easyfe.entity.Course;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.info.RGetCourse;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.fragment.SpecialOrderFragment;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索特价订单页面
 */
public class SearchSpreadActivity extends BaseActivity {

    private OptionsPickerView<String> mPicker;
    private TextView mGradeTextView;
    private TextView mCourseTextView;
    private SpecialOrderFragment mFragment;

    private ArrayList<String> mGrade = new ArrayList<>();
    private static List<Course> mCourses = new ArrayList<>();
    private static ArrayList<String> mCourseNames = new ArrayList<>();
    private int mSelectedCoursePosition = -1;
    private boolean mISLoadingCloseByUser = true;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_search_spread);
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("搜索特价订单");

        mGradeTextView = OtherUtils.findViewById(this, R.id.search_spread_tv_grade);
        mCourseTextView = OtherUtils.findViewById(this, R.id.search_spread_tv_course);
        mPicker = new OptionsPickerView<>(mContext);

        mFragment = new SpecialOrderFragment();
        mFragment.setShowSearchResult(true);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.search_spread_fl_container, mFragment)
                .commit();
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

    public void onSearchClick(View view) {
        if (null == mFragment) {
            return;
        }

        mFragment.setSearchCourse(mCourseTextView.getText().toString());
        mFragment.setSearchGrade(mGradeTextView.getText().toString());
        mFragment.doSearch();
    }

    public void onBackClick(View view) {
        finish();
    }
}
