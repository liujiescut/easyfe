package com.scut.easyfe.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.test.Problem;
import com.scut.easyfe.ui.adapter.ProblemAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.ListViewUtil;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 常见问题帮助页面
 * @author jay
 */
public class ProblemAndHelpActivity extends BaseActivity {
    private ListView mProblemListView;
    private TextView mOtherTextView;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_problem_and_help);
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("常见问题帮助");

        mProblemListView = OtherUtils.findViewById(this, R.id.problems_and_help_lv_problems);
        mOtherTextView = OtherUtils.findViewById(this, R.id.problems_and_help_tv_other);

        mProblemListView.setAdapter(new ProblemAdapter(this, Problem.getProblems()));
        ListViewUtil.setListViewHeightBasedOnChildren(mProblemListView);
    }

    @Override
    protected void initListener() {
        mProblemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewUtil.setListViewHeightBasedOnChildren(mProblemListView);
            }
        });
    }

    public void onMoreProblemsClick(View view){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_TITLE, "更多问题");
        bundle.putString(Constants.Key.SHOW_TEXT_ACTIVITY_CONTENT, Problem.getOtherProblemsText());
        redirectToActivity(mContext, ShowTextActivity.class, bundle);
    }

    public void onBackClick(View view){
        finish();
    }
}
