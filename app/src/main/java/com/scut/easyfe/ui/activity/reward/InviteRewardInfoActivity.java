package com.scut.easyfe.ui.activity.reward;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.entity.reward.ParentInviteReward;
import com.scut.easyfe.entity.reward.TeacherInviteReward;
import com.scut.easyfe.ui.adapter.RewardAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 邀请奖励页面(邀请执行情况)
 */
public class InviteRewardInfoActivity extends BaseActivity {

    private ListView mRewardListView;
    private RewardAdapter mAdapter;
    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_invite_reward_info);
    }


    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("邀请有奖");
        ((TextView) OtherUtils.findViewById(this, R.id.invite_reward_info_tv_explain)).setText(
                App.getUser().isParent() ? R.string.invite_reward_info_parent_explain : R.string.invite_reward_info_teacher_explain);

        mRewardListView = OtherUtils.findViewById(this, R.id.invite_reward_info_lv_rewards);

        if(App.getUser().isParent()){
            mAdapter = new RewardAdapter(mContext, ParentInviteReward.getTestRewards());
        }else {
            mAdapter = new RewardAdapter(mContext, TeacherInviteReward.getTestRewards());
        }

        mRewardListView.setAdapter(mAdapter);
    }

    public void onBackClick(View view){
        finish();
    }
}
