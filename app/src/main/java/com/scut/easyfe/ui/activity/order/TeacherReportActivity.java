package com.scut.easyfe.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.order.RTeacherReport;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

import org.json.JSONObject;

import io.techery.properratingbar.ProperRatingBar;

/**
 * 家教反馈报告
 */
public class TeacherReportActivity extends BaseActivity {

    public static final int REQUEST_TUTOR_DETAIL = 1;

    private EditText mCommentEditText;
    private TextView mRightPercentTextView;
    private OptionsPickerView<String> mPicker;
    private ProperRatingBar mEnthusiasmBar;
    private ProperRatingBar mAbsorptionBar;

    private Order mOrder;
    private Order.TutorDetail mThisTeachDetail;
    private Order.TutorDetail mNextTeachDetail;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_teacher_report);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mOrder = (Order) extras.getSerializable(Constants.Key.ORDER);
                if (null != mOrder) {
                    mThisTeachDetail = mOrder.getThisTeachDetail();
                    mNextTeachDetail = mOrder.getNextTeachDetail();

                } else {
                    mOrder = new Order();
                    mThisTeachDetail = new Order.TutorDetail();
                    mNextTeachDetail = new Order.TutorDetail();
                }

            } else {
                mOrder = new Order();
                mThisTeachDetail = new Order.TutorDetail();
                mNextTeachDetail = new Order.TutorDetail();
            }

        } else {
            mOrder = new Order();
            mThisTeachDetail = new Order.TutorDetail();
            mNextTeachDetail = new Order.TutorDetail();
        }
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("完成课程并反馈");

        initThisTeachDetailViews();
        updateNextTeachDetailViews();

        mCommentEditText = OtherUtils.findViewById(this, R.id.teacher_report_et_comment);
        mRightPercentTextView = OtherUtils.findViewById(this, R.id.teacher_report_tv_correct_rate);
        mEnthusiasmBar = OtherUtils.findViewById(this, R.id.teacher_report_prb_enthusiasm);
        mAbsorptionBar = OtherUtils.findViewById(this, R.id.teacher_report_prb_absorption_rate);

        mPicker = new OptionsPickerView<>(this);
        mPicker.setPicker(Constants.Data.rightPercentList);
        mPicker.setCyclic(false);

        //如果订单没有专业辅导就隐藏相关内容
        if(!mOrder.hasProfessionTutor()){
            ((View)OtherUtils.findViewById(this, R.id.teacher_report_view_this_teach_detail)).setVisibility(View.GONE);
            ((View)OtherUtils.findViewById(this, R.id.teacher_report_ll_tutor_1_detail)).setVisibility(View.GONE);
            ((View)OtherUtils.findViewById(this, R.id.teacher_report_ll_this_teach_detail_label)).setVisibility(View.GONE);
            ((View)OtherUtils.findViewById(this, R.id.teacher_report_view_next_teach_detail)).setVisibility(View.GONE);
            ((View)OtherUtils.findViewById(this, R.id.teacher_report_ll_tutor_2_detail)).setVisibility(View.GONE);
            ((View)OtherUtils.findViewById(this, R.id.teacher_report_ll_next_teach_detail_label)).setVisibility(View.GONE);
            ((View)OtherUtils.findViewById(this, R.id.teacher_report_ll_edit_next_teach_detail)).setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mRightPercentTextView.setText(Constants.Data.rightPercentList.get(options1));
            }
        });
    }

    /**
     * 刷新下次专业辅导情况显示
     */
    public void updateNextTeachDetailViews(){
        //包含知识点跟复习模拟卷的View
        View mNextTeachDetailContainer = OtherUtils.findViewById(this, R.id.teacher_report_view_next_teach_detail);
        //包含知识点的View
        View mNextAccordingKnowledgeView = OtherUtils.findViewById(mNextTeachDetailContainer, R.id.profession_tutor_ll_according_knowledge);
        //包含复习模拟卷的View
        View mNextAccordingPaperView = OtherUtils.findViewById(mNextTeachDetailContainer, R.id.profession_tutor_ll_according_paper);
        //包含下次专业辅导情况下面"详情"的View
        View mNextTeachDetailView = OtherUtils.findViewById(this, R.id.teacher_report_ll_tutor_2_detail);

        mNextTeachDetailContainer.setVisibility(View.VISIBLE);
        mNextTeachDetailView.setVisibility(View.VISIBLE);

        if(mNextTeachDetail.hadFillIn()){
            //填写或修改下次专业辅导情况的View
            TextView mNextTeachDetailActionTextView = OtherUtils.findViewById(this, R.id.teacher_report_tv_edit_next_teach_detail);
            mNextTeachDetailActionTextView.setText(getResources().getString(R.string.modify_next_teach_detail));

            if (mNextTeachDetail.getTeachWay() == Constants.Identifier.TUTOR_WAY_PAPER) {
                mNextAccordingKnowledgeView.setVisibility(View.GONE);
                mNextAccordingPaperView.setVisibility(View.VISIBLE);
                TextView mPaperTextView = OtherUtils.findViewById(mNextTeachDetailContainer, R.id.profession_tutor_tv_paper);
                TextView mGradeTextView = OtherUtils.findViewById(mNextTeachDetailContainer, R.id.profession_tutor_tv_grade);

                mPaperTextView.setText(mNextTeachDetail.getExamPaper());
                mGradeTextView.setText(mNextTeachDetail.getGrade());
                resetTextView(mPaperTextView);
                resetTextView(mGradeTextView);

            } else {
                mNextAccordingPaperView.setVisibility(View.GONE);
                mNextAccordingKnowledgeView.setVisibility(View.VISIBLE);
                TextView[][] mKnowLedgeTextViews = new TextView[3][3];
                mKnowLedgeTextViews[0][0] = OtherUtils.findViewById(mNextTeachDetailContainer, R.id.profession_tutor_tv_knowledge_1_1);
                mKnowLedgeTextViews[0][1] = OtherUtils.findViewById(mNextTeachDetailContainer, R.id.profession_tutor_tv_knowledge_1_2);
                mKnowLedgeTextViews[0][2] = OtherUtils.findViewById(mNextTeachDetailContainer, R.id.profession_tutor_tv_knowledge_1_3);
                mKnowLedgeTextViews[1][0] = OtherUtils.findViewById(mNextTeachDetailContainer, R.id.profession_tutor_tv_knowledge_2_1);
                mKnowLedgeTextViews[1][1] = OtherUtils.findViewById(mNextTeachDetailContainer, R.id.profession_tutor_tv_knowledge_2_2);
                mKnowLedgeTextViews[1][2] = OtherUtils.findViewById(mNextTeachDetailContainer, R.id.profession_tutor_tv_knowledge_2_3);
                mKnowLedgeTextViews[2][0] = OtherUtils.findViewById(mNextTeachDetailContainer, R.id.profession_tutor_tv_knowledge_3_1);
                mKnowLedgeTextViews[2][1] = OtherUtils.findViewById(mNextTeachDetailContainer, R.id.profession_tutor_tv_knowledge_3_2);
                mKnowLedgeTextViews[2][2] = OtherUtils.findViewById(mNextTeachDetailContainer, R.id.profession_tutor_tv_knowledge_3_3);

                int index = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (index < mNextTeachDetail.getKnowledge().size()) {
                            mKnowLedgeTextViews[i][j].setText(mNextTeachDetail.getKnowledge().get(index++));
                        }
                        resetTextView(mKnowLedgeTextViews[i][j]);
                    }
                }
            }

        }else{
            mNextTeachDetailContainer.setVisibility(View.GONE);
            mNextTeachDetailView.setVisibility(View.GONE);

        }
    }

    /**
     * 初始化本次专业辅导情况显示
     */
    private void initThisTeachDetailViews() {
        View mThisTeachDetailContainer = OtherUtils.findViewById(this, R.id.teacher_report_view_this_teach_detail);
        View mThisAccordingKnowledgeView = OtherUtils.findViewById(mThisTeachDetailContainer, R.id.profession_tutor_ll_according_knowledge);
        View mThisAccordingPaperView = OtherUtils.findViewById(mThisTeachDetailContainer, R.id.profession_tutor_ll_according_paper);
        if (mThisTeachDetail.getTeachWay() == Constants.Identifier.TUTOR_WAY_PAPER) {
            mThisAccordingKnowledgeView.setVisibility(View.GONE);
            mThisAccordingPaperView.setVisibility(View.VISIBLE);
            TextView mPaperTextView = OtherUtils.findViewById(mThisTeachDetailContainer, R.id.profession_tutor_tv_paper);
            TextView mGradeTextView = OtherUtils.findViewById(mThisTeachDetailContainer, R.id.profession_tutor_tv_grade);

            mPaperTextView.setText(mThisTeachDetail.getExamPaper());
            mGradeTextView.setText(mThisTeachDetail.getGrade());
            resetTextView(mPaperTextView);
            resetTextView(mGradeTextView);
        } else {

            mThisAccordingPaperView.setVisibility(View.GONE);
            mThisAccordingKnowledgeView.setVisibility(View.VISIBLE);
            TextView[][] mKnowLedgeTextViews = new TextView[3][3];
            mKnowLedgeTextViews[0][0] = OtherUtils.findViewById(mThisTeachDetailContainer, R.id.profession_tutor_tv_knowledge_1_1);
            mKnowLedgeTextViews[0][1] = OtherUtils.findViewById(mThisTeachDetailContainer, R.id.profession_tutor_tv_knowledge_1_2);
            mKnowLedgeTextViews[0][2] = OtherUtils.findViewById(mThisTeachDetailContainer, R.id.profession_tutor_tv_knowledge_1_3);
            mKnowLedgeTextViews[1][0] = OtherUtils.findViewById(mThisTeachDetailContainer, R.id.profession_tutor_tv_knowledge_2_1);
            mKnowLedgeTextViews[1][1] = OtherUtils.findViewById(mThisTeachDetailContainer, R.id.profession_tutor_tv_knowledge_2_2);
            mKnowLedgeTextViews[1][2] = OtherUtils.findViewById(mThisTeachDetailContainer, R.id.profession_tutor_tv_knowledge_2_3);
            mKnowLedgeTextViews[2][0] = OtherUtils.findViewById(mThisTeachDetailContainer, R.id.profession_tutor_tv_knowledge_3_1);
            mKnowLedgeTextViews[2][1] = OtherUtils.findViewById(mThisTeachDetailContainer, R.id.profession_tutor_tv_knowledge_3_2);
            mKnowLedgeTextViews[2][2] = OtherUtils.findViewById(mThisTeachDetailContainer, R.id.profession_tutor_tv_knowledge_3_3);

            int index = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (index < mThisTeachDetail.getKnowledge().size()) {
                        mKnowLedgeTextViews[i][j].setText(mThisTeachDetail.getKnowledge().get(index++));
                    }
                    resetTextView(mKnowLedgeTextViews[i][j]);
                }
            }
        }
    }

    /**
     * 去掉TextView的一些属性
     */
    private void resetTextView(TextView view) {
        view.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        view.setClickable(false);
        view.setTextColor(getResources().getColor(R.color.text_hint_color));
    }

    public void onThisTeachDetailClick(View view) {
        goShowTutorActivity(mThisTeachDetail);
    }

    public void onNextTeachDetailClick(View view) {
        goShowTutorActivity(mNextTeachDetail);
    }

    private void goShowTutorActivity(Order.TutorDetail detail){
        if (OtherUtils.isFastDoubleClick()) {
            return;
        }

        Bundle bundle = new Bundle();
        mOrder.getThisTeachDetail().setGrade(mOrder.getGrade());
        bundle.putSerializable(Constants.Key.TUTOR_DETAIL, detail);
        Intent intent = new Intent(mContext, ShowTutorActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onSubmitClick(View view) {
        if(!validate()){
            return;
        }

        //Todo 没有专业辅导情况的订单的处理情况
        startLoading("提交评价中");
        RequestManager.get().execute(new RTeacherReport(mOrder.get_id(), mCommentEditText.getText().toString(),
                mRightPercentTextView.getText().toString(), mEnthusiasmBar.getRating(),
                mAbsorptionBar.getRating(), mNextTeachDetail),
                new RequestListener<JSONObject>() {
            @Override
            public void onSuccess(RequestBase request, JSONObject result) {
                stopLoading();
                toast(result.optString("message"));

                mOrder.setIsTeacherReport(true);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.Key.ORDER, mOrder);
                TeacherReportActivity.this.redirectToActivity(TeacherReportActivity.this
                        , ToDoOrderActivity.class, bundle);
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                stopLoading();
                toast(errorMsg);
            }
        });
    }

    private boolean validate(){
        if(mCommentEditText.getText().length() == 0){
            toast("请输入对学生评价");
            return false;
        }

        if(mRightPercentTextView.getText().length() == 0){
            toast("请选择正确率");
            return false;
        }

        if(mOrder.hasProfessionTutor() && !mNextTeachDetail.hadFillIn()){
            toast("请先填写下次专业辅导情况");
            return false;
        }

        return true;
    }

    public void onModifyNextTeachDetailClick(View view) {
        if(OtherUtils.isFastDoubleClick()){
            return;
        }

        Bundle bundle = new Bundle();
        if(!mNextTeachDetail.hadFillIn()) {
            mNextTeachDetail.setGrade(mOrder.getGrade());
        }
        bundle.putSerializable(Constants.Key.TUTOR_DETAIL, mNextTeachDetail);

        bundle.putString(Constants.Key.ORDER_ID, mOrder.get_id());
        Intent intent = new Intent(mContext, EditTutorActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_TUTOR_DETAIL);
    }

    public void onRightPercentClick(View view) {
        if (null != mPicker) {
            mPicker.show();
        }
    }

    public void onBackClick(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TUTOR_DETAIL:
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (null != bundle) {
                        Order.TutorDetail tutorDetail = (Order.TutorDetail) bundle.getSerializable(Constants.Key.TUTOR_DETAIL);
                        if (null != tutorDetail) {
                            mNextTeachDetail = tutorDetail;
                            mOrder.setNextTeachDetail(mNextTeachDetail);
                            updateNextTeachDetailViews();
                        }
                    }
                }
                break;

            default:
                break;
        }
    }
}
