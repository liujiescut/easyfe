package com.scut.easyfe.ui.fragments;

import android.content.Intent;
import android.view.View;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.activity.TeacherRegisterOneActivity;
import com.scut.easyfe.ui.activity.TeacherRegisterTwoActivity;
import com.scut.easyfe.ui.base.BaseFragment;

/**
 * 主页Fragment
 * Created by jay on 16/3/15.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener{

    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.fragment_home;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
    }

    @Override
    protected void initListener(View v) {
        super.initListener(v);
        v.findViewById(R.id.home_main_text).setOnClickListener(this);
        v.findViewById(R.id.home_second_text).setOnClickListener(this);
        v.findViewById(R.id.home_third_text).setOnClickListener(this);
        v.findViewById(R.id.home_vertical_divider).setOnClickListener(this);
        v.findViewById(R.id.home_need_report).setOnClickListener(this);
        v.findViewById(R.id.home_book_multi).setOnClickListener(this);
        v.findViewById(R.id.home_book_multi_text).setOnClickListener(this);
        v.findViewById(R.id.home_book_once).setOnClickListener(this);
        v.findViewById(R.id.home_book_once_text).setOnClickListener(this);
        v.findViewById(R.id.home_teacher).setOnClickListener(this);
        v.findViewById(R.id.home_teacher_text).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_main_text:
            case R.id.home_second_text:
            case R.id.home_third_text:
            case R.id.home_vertical_divider:
                onSpecialOrderClick(v);
                break;

            case R.id.home_need_report:
                onNeedReportClick(v);
                break;

            case R.id.home_book_multi:
            case R.id.home_book_multi_text:
                onMultiBookClick(v);
                break;

            case R.id.home_book_once:
            case R.id.home_book_once_text:
                onOnceBookClick(v);
                break;

            case R.id.home_teacher:
            case R.id.home_teacher_text:
                onTeacherClick(v);
                break;

            default:
                break;
        }
    }

    /**
     * 点击特价订单
     * @param view 被点击视图
     */
    private void onSpecialOrderClick(View view){
        toast("点击特价订单");
    }

    /**
     * 点击反馈需求
     * @param view 被点击视图
     */
    private void onNeedReportClick(View view){
        toast("点击反馈需求");
    }

    /**
     * 点击多次预约
     * @param view 被点击视图
     */
    private void onMultiBookClick(View view){
        toast("点击多次预约");
    }

    /**
     * 点击单次预约
     * @param view 被点击视图
     */
    private void onOnceBookClick(View view){
        toast("点击单次预约");
    }

    /**
     * 点击我是家教
     * @param view 被点击视图
     */
    private void onTeacherClick(View view){
        if(null != mActivity) {
            mActivity.redirectToActivity(mActivity, TeacherRegisterTwoActivity.class);
        }
    }
}
