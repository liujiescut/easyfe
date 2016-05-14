package com.scut.easyfe.ui.activity.reward;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.entity.reward.ParentCourseReward;
import com.scut.easyfe.ui.adapter.RewardAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 家长完成课时奖励
 */
public class ParentCourseRewardActivity extends BaseActivity {

    private ListView mRecordListView;
    private RewardAdapter mAdapter;
    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_parent_course_reward);
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("完成课时现金券及积分奖励");
        mRecordListView = OtherUtils.findViewById(this, R.id.parent_course_reward_lv_rewards);

//        mAdapter = new RewardAdapter(mContext, ParentCourseReward.getTestRewards());
        mRecordListView.setAdapter(mAdapter);
    }
    
    public void onBackClick(View view){
        finish();
    }
}
