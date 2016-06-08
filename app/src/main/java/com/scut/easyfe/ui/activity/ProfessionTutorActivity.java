package com.scut.easyfe.ui.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.TutorInfo;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.info.RGetTutorCourse;
import com.scut.easyfe.network.request.info.RGetTutorInfo;
import com.scut.easyfe.ui.adapter.CourseAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;
import java.util.List;

public class ProfessionTutorActivity extends BaseActivity {
    private GridView mCourseGridView;
    //College Entrance Exam
    private TextView mCEETextView;
    //HighSchool Entrance Exam
    private TextView mHEETextView;
    private TextView mHighSchoolTextView;
    private TextView mMiddleSchoolTextView;
    private TextView mWayKnowledgeTextView;
    private TextView mWayPaperTextView;
    private LinearLayout mWayKnowledgeLinearLayout;
    private LinearLayout mWayPaperLinearLayout;

    private CourseAdapter mCourseAdapter;

    private String mCourseName = "";
    private String mCategory = "高考";
    private TutorInfo mTutorInfo = new TutorInfo();
    private List<String> mCourseList = new ArrayList<>();

    private int mSelectedCoursePosition = 0;
    private boolean mIsLoadingCloseByUser = true;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_profession_tutor);
    }


    @Override
    protected void initView() {
        mCEETextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_state_cee);
        mHEETextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_state_hee);
        mHighSchoolTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_state_high);
        mMiddleSchoolTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_state_middle);
        mWayKnowledgeTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_way_knowledge);
        mWayPaperTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_way_paper);
        mMiddleSchoolTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_state_middle);
        mWayKnowledgeLinearLayout = OtherUtils.findViewById(this, R.id.profession_tutor_ll_according_knowledge);
        mWayPaperLinearLayout = OtherUtils.findViewById(this, R.id.profession_tutor_ll_according_paper);

        mCourseGridView = OtherUtils.findViewById(this, R.id.profession_tutor_gv_course);
        mCourseAdapter = new CourseAdapter(Constants.Data.professionTutorCourseList);
        mCourseAdapter.setItemClickable(false);
        mCourseGridView.setAdapter(mCourseAdapter);
    }

    @Override
    protected void fetchData() {
        startLoading("加载数据中", new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mIsLoadingCloseByUser) {
                    finish();
                }
            }
        });

        RequestManager.get().execute(new RGetTutorCourse(), new RequestListener<List<String>>() {
            @Override
            public void onSuccess(RequestBase request, List<String> result) {
                if (null != result && result.size() > 0) {
                    mCourseList.clear();
                    mCourseList.addAll(result);
                    mCourseAdapter.setSelectedPosition(mSelectedCoursePosition);
                    mCourseAdapter.notifyDataSetChanged();
                    mCourseName = mCourseList.get(mSelectedCoursePosition);
                    getTutorInfo(mCategory, mCourseName);
                } else {
                    toast("加载数据失败");
                    stopLoading();
                }
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast("加载数据失败");
                stopLoading();
            }
        });
    }

    private void getTutorInfo(String category, String course) {
        RequestManager.get().execute(new RGetTutorInfo(category, course), new RequestListener<TutorInfo>() {
            @Override
            public void onSuccess(RequestBase request, TutorInfo result) {
                if (null != result) {
                    mTutorInfo = result;

                    mIsLoadingCloseByUser = false;
                } else {
                    toast("加载数据失败");
                }
                stopLoading();
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast("加载数据失败");
                stopLoading();
            }
        });
    }

    @Override
    protected void initListener() {
        StateClickListener stateClickListener = new StateClickListener();
        mCEETextView.setOnClickListener(stateClickListener);
        mHEETextView.setOnClickListener(stateClickListener);
        mHighSchoolTextView.setOnClickListener(stateClickListener);
        mMiddleSchoolTextView.setOnClickListener(stateClickListener);

        TutorWayClickListener tutorWayClickListener = new TutorWayClickListener();
        mWayPaperTextView.setOnClickListener(tutorWayClickListener);
        mWayKnowledgeTextView.setOnClickListener(tutorWayClickListener);

        mCourseGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedCoursePosition = position;
                mCourseAdapter.setSelectedPosition(position);
                mCourseAdapter.notifyDataSetChanged();
//                mGrade = mCourses.get(position).getGrade();
//                mPicker.setPicker(mCourses.get(position).getGrade());
            }
        });
    }

    private class StateClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (OtherUtils.isFastDoubleClick() || !(v instanceof TextView)) {
                return;
            }

            mCEETextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mHEETextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mHighSchoolTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mMiddleSchoolTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);

            ((TextView) v).setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.icon_gold_arrow_padding, 0, 0, 0);
        }
    }

    private class TutorWayClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (OtherUtils.isFastDoubleClick() || !(v instanceof TextView)) {
                return;
            }

            mWayKnowledgeTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mWayPaperTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);

            ((TextView) v).setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.icon_gold_arrow_padding, 0, 0, 0);

            switch (v.getId()) {
                case R.id.profession_tutor_tv_way_knowledge:
                    mWayKnowledgeLinearLayout.setVisibility(View.VISIBLE);
                    mWayPaperLinearLayout.setVisibility(View.GONE);
                    break;

                case R.id.profession_tutor_tv_way_paper:
                    mWayKnowledgeLinearLayout.setVisibility(View.GONE);
                    mWayPaperLinearLayout.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }
    }
}
