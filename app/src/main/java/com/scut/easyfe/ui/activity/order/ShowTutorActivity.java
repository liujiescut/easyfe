package com.scut.easyfe.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 显示专业辅导内容(不可修改)
 * @author jay
 */
public class ShowTutorActivity extends BaseActivity {

    private Order.TutorDetail mTutorDetail = new Order.TutorDetail();

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_show_tutor);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mTutorDetail = (Order.TutorDetail) extras.getSerializable(Constants.Key.TUTOR_DETAIL);
            } else {
                mTutorDetail = new Order.TutorDetail();
            }
        } else {
            mTutorDetail = new Order.TutorDetail();
        }
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("专业辅导情况");

        TextView mStateTextView = OtherUtils.findViewById(this, R.id.show_tutor_tv_state);
        TextView mCourseTextView = OtherUtils.findViewById(this, R.id.show_tutor_tv_course);
        TextView mTutorWayTextView = OtherUtils.findViewById(this, R.id.show_tutor_tv_tutor_way);
        TextView mEasyLevelTextView = OtherUtils.findViewById(this, R.id.show_tutor_tv_easy_level);
        View mAccordingKnowledgeView = OtherUtils.findViewById(this, R.id.profession_tutor_ll_according_knowledge);
        View mAccordingPaperView = OtherUtils.findViewById(this, R.id.profession_tutor_ll_according_paper);

        mStateTextView.setText(mTutorDetail.getCategory());
        mCourseTextView.setText(mTutorDetail.getCourse());
        mEasyLevelTextView.setText(mTutorDetail.getEasyLevel());
        mTutorWayTextView.setText(getResources().getString(
                mTutorDetail.getTeachWay() == Constants.Identifier.TUTOR_WAY_KNOWLEDGE ?
                        R.string.tutor_according_knowledge : R.string.tutor_according_paper));

        if (mTutorDetail.getTeachWay() == Constants.Identifier.TUTOR_WAY_PAPER) {
            mAccordingKnowledgeView.setVisibility(View.GONE);
            mAccordingPaperView.setVisibility(View.VISIBLE);
            TextView mPaperTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_paper);
            TextView mGradeTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_grade);

            mPaperTextView.setText(mTutorDetail.getExamPaper());
            mGradeTextView.setText(mTutorDetail.getGrade());
            resetTextView(mPaperTextView);
            resetTextView(mGradeTextView);
        } else {

            mAccordingPaperView.setVisibility(View.GONE);
            mAccordingKnowledgeView.setVisibility(View.VISIBLE);
            TextView[][] mKnowLedgeTextViews = new TextView[3][3];
            mKnowLedgeTextViews[0][0] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_1_1);
            mKnowLedgeTextViews[0][1] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_1_2);
            mKnowLedgeTextViews[0][2] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_1_3);
            mKnowLedgeTextViews[1][0] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_2_1);
            mKnowLedgeTextViews[1][1] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_2_2);
            mKnowLedgeTextViews[1][2] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_2_3);
            mKnowLedgeTextViews[2][0] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_3_1);
            mKnowLedgeTextViews[2][1] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_3_2);
            mKnowLedgeTextViews[2][2] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_3_3);

            int index = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (index < mTutorDetail.getKnowledge().size()) {
                        mKnowLedgeTextViews[i][j].setText(mTutorDetail.getKnowledge().get(index++));
                    }
                    resetTextView(mKnowLedgeTextViews[i][j]);
                }
            }
        }
    }

    /**
     * 去掉TextView的一些属性
     */
    private void resetTextView(TextView view){
        view.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        view.setClickable(false);
        view.setTextColor(getResources().getColor(R.color.text_hint_color));
    }

    public void onBackClick(View view){
        finish();
    }
}
