package com.scut.easyfe.ui.activity.reward;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.entity.reward.BaseReward;
import com.scut.easyfe.entity.reward.TeacherCompleteSpreadReward;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.reward.GetSpreadReward;
import com.scut.easyfe.network.request.reward.GetSpreadRewardList;
import com.scut.easyfe.ui.adapter.RewardAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.customView.SelectorButton;
import com.scut.easyfe.utils.OtherUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpreadRewardActivity extends BaseActivity {
    private TextView mPublishSpreadRewardTextView;
    private SelectorButton mPublishSpreadRewardSelectorButton;
    private ListView mCompleteRewardListView;
    private RewardAdapter mAdapter;
    private ArrayList<BaseReward> mRewards = new ArrayList<>();
    private boolean mIsLoadingClosedByUser = true;
    private BaseReward mPublishReward;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_spread_reward);
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("特价推广奖励");
        View mPublishSpreadRewardView = OtherUtils.findViewById(this, R.id.spread_reward_view_published_reward);
        mPublishSpreadRewardTextView = OtherUtils.findViewById(mPublishSpreadRewardView, R.id.item_reward_tv_text);
        mPublishSpreadRewardSelectorButton = OtherUtils.findViewById(mPublishSpreadRewardView, R.id.item_reward_sb_get);
        mCompleteRewardListView = OtherUtils.findViewById(this, R.id.spread_reward_lv_complete_time);

        mAdapter = new RewardAdapter(mContext, mRewards) {
            @Override
            protected void onGetRewardClick(BaseReward baseReward, final OnGetRewardListener listener) {
                if (!baseReward.isReceivable()) {
                    return;
                }

                RequestManager.get().execute(new GetSpreadReward(baseReward.get_id()), new RequestListener<JSONObject>() {
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
        };
        mCompleteRewardListView.setAdapter(mAdapter);

        mPublishSpreadRewardSelectorButton.setSelectedTextColor(R.color.text_area_bg);
        mPublishSpreadRewardSelectorButton.setSelectedDrawable(R.drawable.shape_reward_selected);
        mPublishSpreadRewardSelectorButton.setUnselectedTextColor(R.color.theme_color);
        mPublishSpreadRewardSelectorButton.setUnselectDrawable(R.drawable.shape_reward_unselected);
    }

    @Override
    protected void initListener() {
        mPublishSpreadRewardSelectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OtherUtils.isFastDoubleClick() || !mPublishReward.isReceivable()) {
                    return;
                }

                RequestManager.get().execute(new GetSpreadReward(mPublishReward.get_id()), new RequestListener<JSONObject>() {
                    @Override
                    public void onSuccess(RequestBase request, JSONObject result) {
                        mPublishReward.setCount(mPublishReward.getCount() - 1);
                        mPublishSpreadRewardSelectorButton.setIsSelected(!mPublishReward.isReceivable());
                        toast("领取成功");
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        toast(errorMsg);
                    }
                });
            }
        });
    }

    @Override
    protected void fetchData() {
        startLoading("加载数据中", new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mIsLoadingClosedByUser) {
                    finish();
                }
            }
        });

        RequestManager.get().execute(new GetSpreadRewardList(), new RequestListener<List<TeacherCompleteSpreadReward>>() {
            @Override
            public void onSuccess(RequestBase request, List<TeacherCompleteSpreadReward> result) {
                mRewards.addAll(result);
                mAdapter.notifyDataSetChanged();

                if (mRewards.size() >= 1) {
                    mPublishReward = mRewards.get(0);
                    mRewards.remove(0);
                    mPublishSpreadRewardTextView.setText(mPublishReward.getAsString());
                    mPublishSpreadRewardSelectorButton.setIsSelected(!mPublishReward.isReceivable());
                }
                mIsLoadingClosedByUser = false;
                stopLoading();
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
                mIsLoadingClosedByUser = false;
                stopLoading();
            }
        });
    }

    public void onBackClick(View view) {
        finish();
    }
}
