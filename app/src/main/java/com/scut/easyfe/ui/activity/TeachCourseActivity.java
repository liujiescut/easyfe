package com.scut.easyfe.ui.activity;

import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.scut.easyfe.R;
import com.scut.easyfe.ui.adapter.CourseAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.customView.SelectorButton;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;

/**
 * 可教授课程页面
 * @author jay
 */
public class TeachCourseActivity extends BaseActivity {
    private GridView mCourseGridView;
    private LinearLayout mGradeLinearLayout;
    private OptionsPickerView<String> mPicker;
    private ArrayList<String> mState = new ArrayList<>();
    private ArrayList<ArrayList<String>> mGrade = new ArrayList<>();
    private ArrayList<GradePriceItem> mPrices = new ArrayList<>();
    private static final String[] mCourses = new String[16];
    static {
        for(int i = 0; i < mCourses.length; i++){
            mCourses[i] = "计算机";
        }
    }

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_teach_course);
    }

    @Override
    protected void initData() {
        mState.add("小学");
        mState.add("初中");
        mState.add("高中");

        ArrayList<String> primarySchool = new ArrayList<>();
        ArrayList<String> middleSchool = new ArrayList<>();
        ArrayList<String> highSchool = new ArrayList<>();
        primarySchool.add("一年级");
        primarySchool.add("二年级");
        primarySchool.add("三年级");
        primarySchool.add("四年级");
        primarySchool.add("五年级");
        primarySchool.add("六年级");
        middleSchool.add("初一");
        middleSchool.add("初二");
        middleSchool.add("初三");
        highSchool.add("高一");
        highSchool.add("高二");
        highSchool.add("高三");
        mGrade.add(primarySchool);
        mGrade.add(middleSchool);
        mGrade.add(highSchool);
    }

    @Override
    protected void initView() {
        mPicker = new OptionsPickerView<>(mContext);
        mPicker.setPicker(mState, mGrade, true);
        mPicker.setCyclic(false);
        mCourseGridView = OtherUtils.findViewById(this, R.id.teach_course_gv_course);
        mGradeLinearLayout = OtherUtils.findViewById(this, R.id.teach_course_ll_container);
        mCourseGridView.setAdapter(new CourseAdapter(mCourses));
    }

    /**
     * 初始化课程的显示
     */
    private void initGridView(){
        GridView.LayoutParams params =
                new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT);
        for(int i = 0; i< mCourses.length; i++){
            SelectorButton courseButton = new SelectorButton(mContext);
            courseButton.setLayoutParams(params);
            courseButton.setBothText(mCourses[i]);
            mCourseGridView.addView(courseButton);
        }
    }

    @Override
    protected void initListener() {
        mPicker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                int itemViewId = options1 * 1000 + option2;
                View itemView = mGradeLinearLayout.findViewById(itemViewId);
                if(null == itemView) {
                    itemView = getLayoutInflater().inflate(R.layout.item_course_price, null);
                    itemView.setId(itemViewId);
                    mGradeLinearLayout.addView(itemView);
                }

                //Todo 输入价格
                int defaultPrice = 100;
                mPrices.add(new GradePriceItem(options1, option2, defaultPrice));
                ((TextView)itemView.findViewById(R.id.item_course_price_tv_state)).setText(mState.get(options1));
                ((TextView)itemView.findViewById(R.id.item_course_price_tv_grade)).setText(mGrade.get(options1).get(option2));
                ((TextView)itemView.findViewById(R.id.item_course_price_tv_price)).setText(String.format("%d 元/小时", defaultPrice));
            }
        });
    }

    /**
     * 点击增加可教授课程
     */
    public void onAddClick(View view){
        mPicker.show();
    }

    /**
     * 点击确认并保存
     */
    public void onConfirmClick(View view){

    }

    /**
     * 点击返回
     */
    public void onBackClick(View view){
        finish();
    }

    /**
     * 授课年级及收费的具体项
     */
    class GradePriceItem{
        int state;
        int grade;
        int price;

        public GradePriceItem(int state, int grade, int price) {
            this.state = state;
            this.grade = grade;
            this.price = price;
        }
    }
}
