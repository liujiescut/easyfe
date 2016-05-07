package com.scut.easyfe.ui.activity.reward;

import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.entity.user.User;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

public class TaskRewardActivity extends BaseActivity {
    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_task_reward);
    }

    @Override
    @SuppressWarnings("all")
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("任务奖励");
        if(App.getUser().isTeacher()){
           findViewById(R.id.task_reward_ll_teacher).setVisibility(View.VISIBLE);
        }

        if(App.getUser().isParent()){
            findViewById(R.id.task_reward_ll_parent).setVisibility(View.VISIBLE);
        }
    }

    public void onBackClick(View view){
        finish();
    }

    /**
     * 点击家教任务 特价订单推广
     */
    public void onTeacherSpreadClick(View view){
        if(App.getUser().hasLogin()){
            redirectToActivity(mContext, SpreadRewardActivity.class);
        }
    }

    /**
     * 点击家教任务 邀请有奖
     */
    public void onTeacherInviteClick(View view){
        if(App.getUser().hasLogin()){
            redirectToActivity(mContext, InviteRewardInfoActivity.class);
        }
    }

    /**
     * 点击家教任务 完成课时单价增加奖励
     */
    public void onTeacherCompleteOrderClick(View view){
        if(App.getUser().hasLogin()){
            redirectToActivity(mContext, TeacherCourseRewardActivity.class);
        }
    }

    /**
     * 点击家长任务 邀请有奖
     */
    public void onParentInviteClick(View view){
        if(App.getUser().hasLogin()){
            redirectToActivity(mContext, InviteRewardInfoActivity.class);
        }
    }

    /**
     * 点击家长任务 完成课时现金券及积分奖励
     */
    public void onParentCompleteOrderClick(View view){
        if(App.getUser().hasLogin()){
            redirectToActivity(mContext, ParentCourseRewardActivity.class);
        }
    }
}
