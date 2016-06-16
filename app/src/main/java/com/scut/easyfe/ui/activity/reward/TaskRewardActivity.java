package com.scut.easyfe.ui.activity.reward;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Variables;
import com.scut.easyfe.entity.PollingData;
import com.scut.easyfe.event.DataChangeEvent;
import com.scut.easyfe.event.PDHandler;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

import org.greenrobot.eventbus.Subscribe;

public class TaskRewardActivity extends BaseActivity {

    private ImageView mSpreadNewImageView;
    private ImageView mInviteParentNewImageView;
    private ImageView mInviteTeacherNewImageView;
    private ImageView mCompleteCourseTeacherNewImageView;
    private ImageView mCompleteCourseParentNewImageView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        App.get().getEventBus().register(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.get().getEventBus().unregister(mContext);
    }

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_task_reward);
    }

    @Override
    @SuppressWarnings("all")
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("任务奖励");
        if (App.getUser().isTeacher()) {
            findViewById(R.id.task_reward_ll_teacher).setVisibility(View.VISIBLE);
        }

        if (App.getUser().isParent()) {
            findViewById(R.id.task_reward_ll_parent).setVisibility(View.VISIBLE);
        }

        mSpreadNewImageView = OtherUtils.findViewById(this, R.id.task_reward_tv_spread_new);
        mInviteParentNewImageView = OtherUtils.findViewById(this, R.id.task_reward_tv_parent_invite_new);
        mInviteTeacherNewImageView = OtherUtils.findViewById(this, R.id.task_reward_tv_teacher_invite_new);
        mCompleteCourseParentNewImageView = OtherUtils.findViewById(this, R.id.task_reward_tv_parent_complete_course_new);
        mCompleteCourseTeacherNewImageView = OtherUtils.findViewById(this, R.id.task_reward_tv_teacher_complete_course_new);

        refreshUI(PDHandler.get().getLatestData());
    }

    public void onBackClick(View view) {
        finish();
    }

    /**
     * 点击家教任务 特价订单推广
     */
    public void onTeacherSpreadClick(View view) {
        if (App.getUser().hasLogin()) {
            mSpreadNewImageView.setVisibility(View.INVISIBLE);
            Variables.localData.getMine().getReward().setSpecialOrder(
                    PDHandler.get().getLatestData().getMine().getReward().getSpecialOrder());
            Variables.localData.equals(PDHandler.get().getLatestData(), true);
            Variables.localData.save2Cache();
            App.get().getEventBus().post(new DataChangeEvent(PDHandler.get().getLatestData()));
            redirectToActivity(mContext, SpreadRewardActivity.class);
        }
    }

    /**
     * 点击家教任务 邀请有奖
     */
    public void onTeacherInviteClick(View view) {
        if (App.getUser().hasLogin()) {
            mInviteTeacherNewImageView.setVisibility(View.INVISIBLE);
            Variables.localData.getMine().getReward().setInviteTeacher(
                    PDHandler.get().getLatestData().getMine().getReward().getInviteTeacher());
            Variables.localData.equals(PDHandler.get().getLatestData(), true);
            Variables.localData.save2Cache();
            App.get().getEventBus().post(new DataChangeEvent(PDHandler.get().getLatestData()));
            redirectToActivity(mContext, InviteRewardInfoActivity.class);
        }
    }

    /**
     * 点击家教任务 完成课时单价增加奖励
     */
    public void onTeacherCompleteOrderClick(View view) {
        if (App.getUser().hasLogin()) {
            mCompleteCourseTeacherNewImageView.setVisibility(View.INVISIBLE);
            Variables.localData.getMine().getReward().setCompleteCourseTeacher(
                    PDHandler.get().getLatestData().getMine().getReward().getCompleteCourseTeacher());
            Variables.localData.equals(PDHandler.get().getLatestData(), true);
            Variables.localData.save2Cache();
            App.get().getEventBus().post(new DataChangeEvent(PDHandler.get().getLatestData()));
            redirectToActivity(mContext, TeacherCourseRewardActivity.class);
        }
    }

    /**
     * 点击家长任务 邀请有奖
     */
    public void onParentInviteClick(View view) {
        if (App.getUser().hasLogin()) {
            mInviteParentNewImageView.setVisibility(View.INVISIBLE);
            Variables.localData.getMine().getReward().setInviteParent(
                    PDHandler.get().getLatestData().getMine().getReward().getInviteParent());
            Variables.localData.equals(PDHandler.get().getLatestData(), true);
            Variables.localData.save2Cache();
            App.get().getEventBus().post(new DataChangeEvent(PDHandler.get().getLatestData()));
            redirectToActivity(mContext, InviteRewardInfoActivity.class);
        }
    }

    /**
     * 点击家长任务 完成课时现金券及积分奖励
     */
    public void onParentCompleteOrderClick(View view) {
        if (App.getUser().hasLogin()) {
            mCompleteCourseParentNewImageView.setVisibility(View.INVISIBLE);
            Variables.localData.getMine().getReward().setCompleteCourseParent(
                    PDHandler.get().getLatestData().getMine().getReward().getCompleteCourseParent());
            Variables.localData.equals(PDHandler.get().getLatestData(), true);
            Variables.localData.save2Cache();
            App.get().getEventBus().post(new DataChangeEvent(PDHandler.get().getLatestData()));

            redirectToActivity(mContext, ParentCourseRewardActivity.class);
        }
    }

    @Subscribe
    public void onEvent(DataChangeEvent event) {
        if (null != event) {
            refreshUI(event.getData());
        }
    }

    public void refreshUI(PollingData data) {
        PollingData.PollingRewardData pollingData = data.getMine().getReward();
        mSpreadNewImageView.setVisibility(
                Variables.localData.getMine().getReward().isSpreadNew(pollingData) ? View.VISIBLE : View.INVISIBLE);
        mInviteTeacherNewImageView.setVisibility(
                Variables.localData.getMine().getReward().isInviteTeacherNew(pollingData) ? View.VISIBLE : View.INVISIBLE);
        mCompleteCourseTeacherNewImageView.setVisibility(
                Variables.localData.getMine().getReward().isCompleteCourseTeacherNew(pollingData) ? View.VISIBLE : View.INVISIBLE);
        mInviteParentNewImageView.setVisibility(
                Variables.localData.getMine().getReward().isInviteParentNew(pollingData) ? View.VISIBLE : View.INVISIBLE);
        mCompleteCourseParentNewImageView.setVisibility(
                Variables.localData.getMine().getReward().isCompleteCourseParentNew(pollingData) ? View.VISIBLE : View.INVISIBLE);
    }
}
