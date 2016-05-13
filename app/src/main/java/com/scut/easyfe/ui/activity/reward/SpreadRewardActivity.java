package com.scut.easyfe.ui.activity.reward;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.utils.L;
import com.scut.easyfe.R;
import com.scut.easyfe.entity.reward.BaseReward;
import com.scut.easyfe.entity.reward.TeacherCompleteSpreadReward;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.reward.GetSpreadRewardList;
import com.scut.easyfe.ui.adapter.RewardAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.customView.SelectorButton;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;
import java.util.List;

public class SpreadRewardActivity extends BaseActivity {
    private TextView mPublishSpreadRewardTextView;
    private SelectorButton mPublishSpreadRewardSelectorButton;
    private ListView mCompleteRewardListView;
    private RewardAdapter mAdapter;
    private ArrayList<BaseReward> rewards = new ArrayList<>();
    private boolean mIsLoadingClosedByUser = true;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_spread_reward);
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("特价推广奖励");
        View mPublishSpreadRewardView = OtherUtils.findViewById(this, R.id.spread_reward_view_published_reward);
        mPublishSpreadRewardTextView = OtherUtils.findViewById(mPublishSpreadRewardView, R.id.item_reward_tv_text);
        mPublishSpreadRewardSelectorButton = OtherUtils.findViewById(mPublishSpreadRewardView, R.id.item_reward_sb_get);
        mCompleteRewardListView = OtherUtils.findViewById(this, R.id.spread_reward_lv_complete_time);

        mAdapter = new RewardAdapter(mContext, rewards);
        mCompleteRewardListView.setAdapter(mAdapter);
    }

    @Override
    protected void fetchData() {
        startLoading("加载数据中");

        RequestManager.get().execute(new GetSpreadRewardList(), new RequestListener<List<TeacherCompleteSpreadReward>>() {
            @Override
            public void onSuccess(RequestBase request, List<TeacherCompleteSpreadReward> result) {

            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {

            }
        });
    }

    public void onBackClick(View view){
        finish();
    }
}
