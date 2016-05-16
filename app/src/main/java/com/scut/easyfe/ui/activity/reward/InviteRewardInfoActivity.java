package com.scut.easyfe.ui.activity.reward;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.entity.reward.BaseReward;
import com.scut.easyfe.entity.reward.ParentInviteReward;
import com.scut.easyfe.entity.reward.TeacherInviteReward;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.kjFrame.http.JsonRequest;
import com.scut.easyfe.network.request.reward.GetParentInviteReward;
import com.scut.easyfe.network.request.reward.GetParentInviteRewardList;
import com.scut.easyfe.network.request.reward.GetSpreadReward;
import com.scut.easyfe.network.request.reward.GetTeacherInviteReward;
import com.scut.easyfe.network.request.reward.GetTeacherInviteRewardList;
import com.scut.easyfe.ui.adapter.RewardAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 邀请奖励页面(邀请执行情况)
 */
public class InviteRewardInfoActivity extends BaseActivity {

    private ListView mRewardListView;
    private RewardAdapter mAdapter;
    private ArrayList<BaseReward> mRewards = new ArrayList<>();
    private boolean mIsLoadingCloseByUser = true;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_invite_reward_info);
    }


    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("邀请有奖");
        ((TextView) OtherUtils.findViewById(this, R.id.invite_reward_info_tv_explain)).setText(
                App.getUser().isParent() ? R.string.invite_reward_info_parent_explain : R.string.invite_reward_info_teacher_explain);

        mRewardListView = OtherUtils.findViewById(this, R.id.invite_reward_info_lv_rewards);

        mAdapter = new RewardAdapter(mContext, mRewards) {
            @Override
            protected void onGetRewardClick(BaseReward baseReward, final OnGetRewardListener listener) {

                if(App.getUser().isParent()){
                    if (!baseReward.isReceivable() || !(baseReward instanceof ParentInviteReward)) {
                        return;
                    }

                    ParentInviteReward reward = (ParentInviteReward)baseReward;
                    RequestManager.get().execute(new GetParentInviteReward(reward.getPhone()), new RequestListener<JSONObject>() {
                        @Override
                        public void onSuccess(RequestBase request, JSONObject result) {
                            listener.onResult(true);
                            toast("领取成功");
                        }

                        @Override
                        public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                            listener.onResult(false);
                            toast(errorMsg);
                        }
                    });

                }else{
                    if (!baseReward.isReceivable() || !(baseReward instanceof TeacherInviteReward)) {
                        return;
                    }

                    TeacherInviteReward reward = (TeacherInviteReward)baseReward;
                    RequestManager.get().execute(new GetTeacherInviteReward(reward.getPhone()), new RequestListener<JSONObject>() {
                        @Override
                        public void onSuccess(RequestBase request, JSONObject result) {
                            listener.onResult(true);
                            toast("领取成功");
                        }

                        @Override
                        public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                            listener.onResult(false);
                            toast(errorMsg);
                        }
                    });
                }
            }
        };

        mRewardListView.setAdapter(mAdapter);
    }

    @Override
    protected void fetchData() {
        startLoading("加载数据中", new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(mIsLoadingCloseByUser){
                    finish();
                }
            }
        });

        if(App.getUser().isParent()) {
            RequestManager.get().execute(new GetParentInviteRewardList(), new RequestListener<List<ParentInviteReward>>() {
                @Override
                public void onSuccess(RequestBase request, List<ParentInviteReward> result) {
                    mRewards.clear();
                    mRewards.addAll(result);
                    mAdapter.notifyDataSetChanged();

                    mIsLoadingCloseByUser = false;
                    stopLoading();
                }

                @Override
                public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                    mIsLoadingCloseByUser = false;
                    stopLoading();
                }
            });

        }else {
            RequestManager.get().execute(new GetTeacherInviteRewardList(), new RequestListener<List<TeacherInviteReward>>() {
                @Override
                public void onSuccess(RequestBase request, List<TeacherInviteReward> result) {
                    mRewards.clear();
                    mRewards.addAll(result);
                    mAdapter.notifyDataSetChanged();

                    mIsLoadingCloseByUser = false;
                    stopLoading();
                }

                @Override
                public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                    toast(errorMsg);
                    mIsLoadingCloseByUser = false;
                    stopLoading();
                }
            });
        }
    }

    public void onBackClick(View view) {
        finish();
    }
}
