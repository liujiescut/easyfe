package com.scut.easyfe.ui.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
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
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;
import java.util.List;

public class ProfessionTutorActivity extends BaseActivity {
    public static final String NO_UPPER_KNOWLEDGE_CHOOSEN_INFO = "请先选择上级知识点";
    private GridView mCourseGridView;
    //College Entrance Exam
    private TextView mCEETextView;
    //HighSchool Entrance Exam
    private TextView mHEETextView;
    private TextView mHighSchoolTextView;
    private TextView mMiddleSchoolTextView;
    //按知识点复习
    private TextView mWayKnowledgeTextView;
    //按复习模拟卷复习
    private TextView mWayPaperTextView;
    //复习模拟卷选择
    private TextView mPaperTextView;
    //复习模拟卷对应的年级
    private TextView mGradeTextView;
    //难易程度
    private TextView mDifficultyTextView;
    private LinearLayout mWayKnowledgeLinearLayout;
    private LinearLayout mWayPaperLinearLayout;
    private OptionsPickerView<String> mPicker;

    //知识点相关的TextView
    private TextView[][] mKnowLedgeTextViews;

    private CourseAdapter mCourseAdapter;

    private String mCourseName = "";
    private String mCategory = "高考";
    private TutorInfo mTutorInfo = new TutorInfo();
    private List<String> mCourseList = new ArrayList<>();

    //知识点选中的Index(-1标记为未选中)
    private int [][] mSelectLevelIndex = new int[][]{{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};

    private int mSelectedCoursePosition = 0;
    private boolean mIsLoadingCloseByUser = true;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_profession_tutor);
    }


    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("专业辅导情况");
        mCEETextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_state_cee);
        mHEETextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_state_hee);
        mHighSchoolTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_state_high);
        mMiddleSchoolTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_state_middle);
        mWayKnowledgeTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_way_knowledge);
        mWayPaperTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_way_paper);
        mMiddleSchoolTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_state_middle);
        mPaperTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_paper);
        mGradeTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_grade);
        mDifficultyTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_difficulty);
        mWayKnowledgeLinearLayout = OtherUtils.findViewById(this, R.id.profession_tutor_ll_according_knowledge);
        mWayPaperLinearLayout = OtherUtils.findViewById(this, R.id.profession_tutor_ll_according_paper);

        mCourseGridView = OtherUtils.findViewById(this, R.id.profession_tutor_gv_course);
        mCourseAdapter = new CourseAdapter(Constants.Data.professionTutorCourseList);
        mCourseAdapter.setItemClickable(false);
        mCourseGridView.setAdapter(mCourseAdapter);

        mKnowLedgeTextViews = new TextView[3][3];
        mKnowLedgeTextViews[0][0] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_1_1);
        mKnowLedgeTextViews[0][1] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_1_2);
        mKnowLedgeTextViews[0][2] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_1_3);
        mKnowLedgeTextViews[1][0] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_2_1);
        mKnowLedgeTextViews[1][1] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_2_2);
        mKnowLedgeTextViews[1][2] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_2_3);
        mKnowLedgeTextViews[2][0] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_3_1);
        mKnowLedgeTextViews[2][1] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_3_2);
        mKnowLedgeTextViews[2][2] = OtherUtils.findViewById(this, R.id.profession_tutor_tv_knowledge_3_3);

        mPicker = new OptionsPickerView<>(mContext);
    }

    @Override
    protected void initListener() {
        CategoryClickListener categoryClickListener = new CategoryClickListener();
        mCEETextView.setOnClickListener(categoryClickListener);
        mHEETextView.setOnClickListener(categoryClickListener);
        mHighSchoolTextView.setOnClickListener(categoryClickListener);
        mMiddleSchoolTextView.setOnClickListener(categoryClickListener);

        TutorWayClickListener tutorWayClickListener = new TutorWayClickListener();
        mWayPaperTextView.setOnClickListener(tutorWayClickListener);
        mWayKnowledgeTextView.setOnClickListener(tutorWayClickListener);

        mPaperTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OtherUtils.isFastDoubleClick()){
                    return;
                }

                if(mTutorInfo.getExamPaper().size() == 0){
                    toast("该课程没有复习模拟卷,请选择按照知识点复习");
                    return;
                }

                ArrayList<String > papers = new ArrayList<>();
                papers.addAll(mTutorInfo.getExamPaper());
                mPicker.setPicker(papers);
                mPicker.setCyclic(false);
                mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        mPaperTextView.setText(mTutorInfo.getExamPaper().get(options1));
                    }
                });
                mPicker.show();
            }
        });

        mDifficultyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OtherUtils.isFastDoubleClick()){
                    return;
                }

                mPicker.setPicker(Constants.Data.paperDifficultyList);
                mPicker.setCyclic(false);
                mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        mDifficultyTextView.setText(Constants.Data.paperDifficultyList.get(options1));
                    }
                });
                mPicker.show();
            }
        });

        mCourseGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedCoursePosition = position;
                mCourseAdapter.setSelectedPosition(position);
                mCourseAdapter.notifyDataSetChanged();
                mCourseName = mCourseList.get(position);

                startLoading();
                getTutorInfo(mCategory, mCourseName);
            }
        });

        for (int i = 0; i < mKnowLedgeTextViews.length; i++) {
            for (int j = 0; j < mKnowLedgeTextViews[i].length; j++){
                mKnowLedgeTextViews[i][j].setOnClickListener(new KnowLedgeClickListener(i, j));
            }
        }
    }

    @Override
    protected void fetchData() {
        startLoading();

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

    private void startLoading(){
        mIsLoadingCloseByUser = true;
        startLoading("加载数据中", new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mIsLoadingCloseByUser) {
                    finish();
                }
            }
        });
    }

    private void getTutorInfo(String category, String course) {
        RequestManager.get().execute(new RGetTutorInfo(category, course), new RequestListener<TutorInfo>() {
            @Override
            public void onSuccess(RequestBase request, TutorInfo result) {
                if (null != result) {
                    mTutorInfo = result;

                    resetKnowledgeArea();
                } else {
                    mTutorInfo = new TutorInfo();
                    toast("加载数据失败");
                }

                mIsLoadingCloseByUser = false;
                stopLoading();
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast("加载数据失败");
                mIsLoadingCloseByUser = false;
                mTutorInfo = new TutorInfo();
                stopLoading();
            }
        });
    }

    private void resetKnowledgeArea(){
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++){
                mKnowLedgeTextViews[i][j].setText("");
                mSelectLevelIndex[i][j] = -1;
            }
        }
    }

    public void onBackClick(View view){
        finish();
    }

    private class KnowLedgeClickListener implements View.OnClickListener{
        private int row = -1;
        private int column = -1;

        public KnowLedgeClickListener(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void onClick(View v) {
            ArrayList<String> toPickData = new ArrayList<>();
            switch (column){
                case 0:
                    toPickData = mTutorInfo.getLevelOneList();
                    break;

                case 1:
                    //一级知识点没有选择
                    if(mSelectLevelIndex[row][0] == -1){
                        toast(NO_UPPER_KNOWLEDGE_CHOOSEN_INFO);
                        return;
                    }

                    toPickData = mTutorInfo.getLevelTwoList(mSelectLevelIndex[row][0]);
                    break;

                case 2:
                    //一级知识点没有选择
                    if(mSelectLevelIndex[row][1] == -1){
                        toast(NO_UPPER_KNOWLEDGE_CHOOSEN_INFO);
                        return;
                    }

                    toPickData = mTutorInfo.getLevelThreeList(mSelectLevelIndex[row][0], mSelectLevelIndex[row][1]);
                    break;
            }

            if(toPickData.size() == 0){
                DialogUtils.makeConfirmDialog(mContext, "提示", "当前知识点已没有下级知识点分类");
                return;
            }

            mPicker.setPicker(toPickData);
            mPicker.setCyclic(false);
            final ArrayList<String> finalToPickData = toPickData;
            mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    mKnowLedgeTextViews[row][column].setText(finalToPickData.get(options1));
                    mSelectLevelIndex[row][column] = options1;

                    //重置该知识点的子知识点
                    for (int i = column + 1; i < 3; i++) {
                        mKnowLedgeTextViews[row][i].setText("");
                        mSelectLevelIndex[row][i] = -1;
                    }
                }
            });
            mPicker.show();
        }
    }

    private class CategoryClickListener implements View.OnClickListener {
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
            mCategory = ((TextView)v).getText().toString();

            startLoading();
            getTutorInfo(mCategory, mCourseName);
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
