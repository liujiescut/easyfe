package com.scut.easyfe.ui.activity.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.TutorInfo;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.info.RGetTutorCourse;
import com.scut.easyfe.network.request.info.RGetTutorInfo;
import com.scut.easyfe.network.request.order.RModifyTutorInfo;
import com.scut.easyfe.ui.adapter.CourseAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.OtherUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditTutorActivity extends BaseActivity {
    public static final String NO_UPPER_KNOWLEDGE_CHOSEN_INFO = "请先选择上级知识点";
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
    private TextView mEasyLevelTextView;
    private LinearLayout mWayKnowledgeLinearLayout;
    private LinearLayout mWayPaperLinearLayout;
    private OptionsPickerView<String> mPicker;

    //知识点相关的TextView
    private TextView[][] mKnowLedgeTextViews;

    private CourseAdapter mCourseAdapter;

    private String mCourseName = "";
    private String mCategory = "高考";
    private String mPaper = "";
    private String mGrade = "";
    private String mEasyLevel = "";
    private int mTutorWay = Constants.Identifier.TUTOR_WAY_KNOWLEDGE;
    private TutorInfo mTutorInfo = new TutorInfo();
    private List<String> mCourseList = new ArrayList<>();

    //知识点选中的Index(-1标记为未选中)
    private int[][] mSelectLevelIndex = new int[][]{{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};

    private boolean mIsModify = false;                          //是否是修改专业辅导信息
    private boolean mUpdateToThisTeachDetail = false;           //是否同步到订单Id对应订单的thisTeachDetail的服务器数据
    private int mSelectedCoursePosition = 0;
    private boolean mIsLoadingCloseByUser = true;

    private Order.TutorDetail mTutorDetail = new Order.TutorDetail();
    private String mOrderId = "";

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_edit_tutor);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mTutorDetail = (Order.TutorDetail) extras.getSerializable(Constants.Key.TUTOR_DETAIL);
                mOrderId = extras.getString(Constants.Key.ORDER_ID, "");
                mUpdateToThisTeachDetail = extras.getBoolean(Constants.Key.TUTOR_UPDATE_TO_THIS_TEACH_DETAIL, false);
                if (null != mTutorDetail) {
                    mIsModify = mTutorDetail.hadFillIn();

                } else {
                    mTutorDetail = new Order.TutorDetail();
                }
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
        mCEETextView = OtherUtils.findViewById(this, R.id.edit_tutor_tv_state_cee);
        mHEETextView = OtherUtils.findViewById(this, R.id.edit_tutor_tv_state_hee);
        mHighSchoolTextView = OtherUtils.findViewById(this, R.id.edit_tutor_tv_state_high);
        mMiddleSchoolTextView = OtherUtils.findViewById(this, R.id.edit_tutor_tv_state_middle);
        mWayKnowledgeTextView = OtherUtils.findViewById(this, R.id.edit_tutor_tv_way_knowledge);
        mWayPaperTextView = OtherUtils.findViewById(this, R.id.edit_tutor_tv_way_paper);
        mMiddleSchoolTextView = OtherUtils.findViewById(this, R.id.edit_tutor_tv_state_middle);
        mPaperTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_paper);
        mGradeTextView = OtherUtils.findViewById(this, R.id.profession_tutor_tv_grade);
        mEasyLevelTextView = OtherUtils.findViewById(this, R.id.edit_tutor_tv_difficulty);
        mWayKnowledgeLinearLayout = OtherUtils.findViewById(this, R.id.profession_tutor_ll_according_knowledge);
        mWayPaperLinearLayout = OtherUtils.findViewById(this, R.id.profession_tutor_ll_according_paper);

        mCourseGridView = OtherUtils.findViewById(this, R.id.edit_tutor_gv_course);
        mCourseAdapter = new CourseAdapter(mCourseList);
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

        if (mIsModify) {
            mCourseName = mTutorDetail.getCourse();
            mCategory = mTutorDetail.getCategory();
            mGrade = mTutorDetail.getGrade();
            mPaper = mTutorDetail.getExamPaper();
            mEasyLevel = mTutorDetail.getEasyLevel();
            mTutorWay = mTutorDetail.getTeachWay();

            mGradeTextView.setText(mGrade);
            mPaperTextView.setText(mPaper);
            mEasyLevelTextView.setText(mEasyLevel);

            //初始化Category的选中状态
            mCEETextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    mTutorDetail.getCategory().equals(mCEETextView.getText().toString()) ? R.mipmap.icon_gold_arrow_padding : 0, 0, 0, 0);
            mHEETextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    mTutorDetail.getCategory().equals(mHEETextView.getText().toString()) ? R.mipmap.icon_gold_arrow_padding : 0, 0, 0, 0);
            mHighSchoolTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    mTutorDetail.getCategory().equals(mHighSchoolTextView.getText().toString()) ? R.mipmap.icon_gold_arrow_padding : 0, 0, 0, 0);
            mMiddleSchoolTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    mTutorDetail.getCategory().equals(mMiddleSchoolTextView.getText().toString()) ? R.mipmap.icon_gold_arrow_padding : 0, 0, 0, 0);

            mWayKnowledgeTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    mTutorWay == Constants.Identifier.TUTOR_WAY_KNOWLEDGE ? R.mipmap.icon_gold_arrow_padding : 0, 0, 0, 0);
            mWayPaperTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    mTutorWay == Constants.Identifier.TUTOR_WAY_PAPER ? R.mipmap.icon_gold_arrow_padding : 0, 0, 0, 0);
            mWayKnowledgeLinearLayout.setVisibility(mTutorWay == Constants.Identifier.TUTOR_WAY_KNOWLEDGE ? View.VISIBLE : View.GONE);
            mWayPaperLinearLayout.setVisibility(mTutorWay == Constants.Identifier.TUTOR_WAY_KNOWLEDGE ? View.GONE : View.VISIBLE);

            int index = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (index < mTutorDetail.getKnowledge().size()) {
                        mKnowLedgeTextViews[i][j].setText(mTutorDetail.getKnowledge().get(index++));
                    }
                }
            }

        } else {

            mGrade = mTutorDetail.getGrade();
            mGradeTextView.setText(mGrade);
        }
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

        mGradeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OtherUtils.isFastDoubleClick()){
                    return;
                }

                mPicker.setPicker(Constants.Data.tutorGradeList);
                mPicker.setCyclic(false);
                mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        mPaper = Constants.Data.tutorGradeList.get(options1);
                        mGradeTextView.setText(mPaper);
                    }
                });
                mPicker.show();
            }
        });

        mPaperTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OtherUtils.isFastDoubleClick()) {
                    return;
                }

                if (mTutorInfo.getExamPaper().size() == 0) {
                    toast("该课程没有复习模拟卷,请选择按照知识点复习");
                    return;
                }

                ArrayList<String> papers = new ArrayList<>();
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

        mEasyLevelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OtherUtils.isFastDoubleClick()) {
                    return;
                }

                mPicker.setPicker(Constants.Data.paperEasyLevelList);
                mPicker.setCyclic(false);
                mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        mEasyLevel = Constants.Data.paperEasyLevelList.get(options1);
                        mEasyLevelTextView.setText(mEasyLevel);
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
            for (int j = 0; j < mKnowLedgeTextViews[i].length; j++) {
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
                    if (mIsModify) {
                        mSelectedCoursePosition = mCourseList.indexOf(mCourseName);
                    } else {
                        mSelectedCoursePosition = 0;
                        mCourseName = mCourseList.get(mSelectedCoursePosition);
                    }

                    mCourseAdapter.setSelectedPosition(mSelectedCoursePosition);
                    mCourseAdapter.notifyDataSetChanged();
                    getTutorInfo(mCategory, mCourseName, !mIsModify);
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

    private void startLoading() {
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
        getTutorInfo(category, course, true);
    }

    private void getTutorInfo(String category, String course, final boolean clear) {
        if (clear) {
            resetKnowledgeArea();
        }

        RequestManager.get().execute(new RGetTutorInfo(category, course), new RequestListener<TutorInfo>() {
            @Override
            public void onSuccess(RequestBase request, TutorInfo result) {
                if (null != result) {
                    mTutorInfo = result;

                    //修改进来重新设置选中的LevelIndex
                    if (!clear) {
                        for (int i = 0; i < 3; i++) {
                            mSelectLevelIndex[i][0] = mTutorInfo.getLevelOneList().indexOf(mKnowLedgeTextViews[i][0].getText().toString());
                        }

                        for (int i = 0; i < 3; i++) {
                            mSelectLevelIndex[i][1] = mTutorInfo.getLevelTwoList(mSelectLevelIndex[i][0]).indexOf(mKnowLedgeTextViews[i][1].getText().toString());
                        }

                        for (int i = 0; i < 3; i++) {
                            mSelectLevelIndex[i][2] = mTutorInfo.getLevelThreeList(mSelectLevelIndex[i][0], mSelectLevelIndex[i][1]).indexOf(mKnowLedgeTextViews[i][2].getText().toString());
                        }
                    }

                    if (mTutorInfo.getExamPaper().size() != 0) {
                        mPaper = mTutorInfo.getExamPaper().get(0);
                        mPaperTextView.setText(mPaper);
                    } else {
                        toast("当前阶段课程分类暂没有复习模拟卷");
                    }
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

    private void resetKnowledgeArea() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mKnowLedgeTextViews[i][j].setText("");
                mSelectLevelIndex[i][j] = -1;
            }
        }
    }

    public void onSaveClick(View view) {
        if (!validate()) {
            return;
        }

        mTutorDetail.setCategory(mCategory);
        mTutorDetail.setCourse(mCourseName);
        mTutorDetail.setTeachWay(mTutorWay);
        mTutorDetail.setEasyLevel(mEasyLevel);
        if (mTutorWay == Constants.Identifier.TUTOR_WAY_KNOWLEDGE) {
            mTutorDetail.getKnowledge().clear();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    mTutorDetail.getKnowledge().add(mKnowLedgeTextViews[i][j].getText().toString());
                }
            }
        } else {
            mTutorDetail.setGrade(mGrade);
            mTutorDetail.setExamPaper(mPaper);
        }

        if(mUpdateToThisTeachDetail) {
            startLoading();
            RequestManager.get().execute(new RModifyTutorInfo(mOrderId, mTutorDetail), new RequestListener<JSONObject>() {
                @Override
                public void onSuccess(RequestBase request, JSONObject result) {
                    mIsLoadingCloseByUser = false;
                    stopLoading();
                    toast(result.optString("message"));

                    setResultAndFinish();
                }

                @Override
                public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                    toast(errorMsg);
                }
            });

        }else{
            setResultAndFinish();

        }
    }

    private void setResultAndFinish(){
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.Key.TUTOR_DETAIL, mTutorDetail);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        EditTutorActivity.this.setResult(0, intent);
        finish();
    }

    private boolean validate() {
        if (mCategory.length() == 0) {
            toast("请选择阶段");
            return false;
        }

        if (mCourseName.length() == 0) {
            toast("请选择课程");
            return false;
        }

        if (mTutorWay == Constants.Identifier.TUTOR_WAY_KNOWLEDGE) {   //针对知识点复习
            int[] levelValue = new int[3];
            for (int i = 0; i < 3; i++) {
                if (mSelectLevelIndex[i][0] == -1) {
                    toast("请选择三个知识点");
                    return false;
                }
                levelValue[i] = mSelectLevelIndex[i][0] * 100 + mSelectLevelIndex[i][1] * 10 + mSelectLevelIndex[i][2];
            }

            if (levelValue[0] == levelValue[1] || levelValue[0] == levelValue[2] || levelValue[1] == levelValue[2]) {
                toast("请选择三个不同知识点");
                return false;
            }

        } else {                                                       //复习模拟卷
            if (mGrade.length() == 0) {
                toast("请选择年级");
                return false;
            }

            if (mPaper.length() == 0) {
                toast("请选择复习模拟卷");
                return false;
            }
        }

        if (mEasyLevel.length() == 0) {
            toast("请选择难易程度");
            return false;
        }

        return true;
    }

    public void onBackClick(View view) {
        finish();
    }

    private class KnowLedgeClickListener implements View.OnClickListener {
        private int row = -1;
        private int column = -1;

        public KnowLedgeClickListener(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void onClick(View v) {
            ArrayList<String> toPickData = new ArrayList<>();
            switch (column) {
                case 0:
                    toPickData = mTutorInfo.getLevelOneList();
                    break;

                case 1:
                    //一级知识点没有选择
                    if (mSelectLevelIndex[row][0] == -1) {
                        toast(NO_UPPER_KNOWLEDGE_CHOSEN_INFO);
                        return;
                    }

                    toPickData = mTutorInfo.getLevelTwoList(mSelectLevelIndex[row][0]);
                    break;

                case 2:
                    //一级知识点没有选择
                    if (mSelectLevelIndex[row][1] == -1) {
                        toast(NO_UPPER_KNOWLEDGE_CHOSEN_INFO);
                        return;
                    }

                    toPickData = mTutorInfo.getLevelThreeList(mSelectLevelIndex[row][0], mSelectLevelIndex[row][1]);
                    break;
            }

            if (toPickData.size() == 0) {
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
            mCategory = ((TextView) v).getText().toString();

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
                case R.id.edit_tutor_tv_way_knowledge:
                    mTutorWay = Constants.Identifier.TUTOR_WAY_KNOWLEDGE;
                    mWayKnowledgeLinearLayout.setVisibility(View.VISIBLE);
                    mWayPaperLinearLayout.setVisibility(View.GONE);
                    break;

                case R.id.edit_tutor_tv_way_paper:
                    mTutorWay = Constants.Identifier.TUTOR_WAY_PAPER;
                    mWayKnowledgeLinearLayout.setVisibility(View.GONE);
                    mWayPaperLinearLayout.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }
    }
}
