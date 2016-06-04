package com.scut.easyfe.ui.activity;

import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.adapter.CourseAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

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
        mCourseGridView.setAdapter(new CourseAdapter(Constants.Data.professionTutorCourseList));
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
    }

    private class StateClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(OtherUtils.isFastDoubleClick() || !(v instanceof TextView)){
                return;
            }

            mCEETextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mHEETextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mHighSchoolTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mMiddleSchoolTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);

            ((TextView)v).setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.icon_gold_arrow_padding, 0, 0, 0);
        }
    }

    private class TutorWayClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(OtherUtils.isFastDoubleClick() || !(v instanceof TextView)){
                return;
            }

            mWayKnowledgeTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            mWayPaperTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);

            ((TextView)v).setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.icon_gold_arrow_padding, 0, 0, 0);

            switch (v.getId()){
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
