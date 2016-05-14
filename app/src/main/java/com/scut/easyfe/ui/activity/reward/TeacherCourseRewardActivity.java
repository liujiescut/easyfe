package com.scut.easyfe.ui.activity.reward;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.reward.TeacherCourseReward;
import com.scut.easyfe.ui.activity.ShowTextActivity;
import com.scut.easyfe.ui.adapter.RewardAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

public class TeacherCourseRewardActivity extends BaseActivity {
    private TextView mExplainTextView;
    private ListView mRewardListView;
    private RewardAdapter mAdapter;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_teacher_course_reward);
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("完成课时单价增加奖励");
        mExplainTextView = OtherUtils.findViewById(this, R.id.teacher_course_reward_tv_explain);
        mRewardListView = OtherUtils.findViewById(this, R.id.teacher_course_reward_lv_rewards);

        String explainText = getResources().getString(R.string.teacher_course_reward_explain);
        SpannableStringBuilder builder =
                new SpannableStringBuilder(explainText);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                redirectToActivity(mContext, TeacherPriceAddExplainActivity.class);
            }
        },explainText.length() - 5, explainText.length() -1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(mResources.getColor(R.color.theme_color)),
                explainText.length() - 5, explainText.length() -1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mExplainTextView.setText(builder);
        mExplainTextView.setMovementMethod(LinkMovementMethod.getInstance());

//        mAdapter = new RewardAdapter(mContext, TeacherCourseReward.getTestRewards());
        mRewardListView.setAdapter(mAdapter);
    }

    public void onBackClick(View view){
        finish();
    }
}
