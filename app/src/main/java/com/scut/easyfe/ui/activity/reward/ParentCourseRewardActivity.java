package com.scut.easyfe.ui.activity.reward;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.entity.reward.BaseReward;
import com.scut.easyfe.entity.reward.ParentCourseReward;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.reward.RGetParentCompleteCourseReward;
import com.scut.easyfe.network.request.reward.RGetParentCompleteCourseRewardList;
import com.scut.easyfe.ui.adapter.RewardAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 家长完成课时奖励
 */
public class ParentCourseRewardActivity extends BaseActivity {

    private ListView mRecordListView;
    private RewardAdapter mAdapter;
    private ArrayList<BaseReward> mRewards = new ArrayList<>();

    private boolean mIsLoadingClosedByUser = true;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_parent_course_reward);
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("完成课时现金券及积分奖励");
        mRecordListView = OtherUtils.findViewById(this, R.id.parent_course_reward_lv_rewards);

        mAdapter = new RewardAdapter(mContext, mRewards){

            @Override
            protected void onGetRewardClick(BaseReward baseReward, final OnGetRewardListener listener) {
                if(OtherUtils.isFastDoubleClick()){
                    return;
                }

                RequestManager.get().execute(new RGetParentCompleteCourseReward(baseReward.get_id()), new RequestListener<JSONObject>() {
                    @Override
                    public void onSuccess(RequestBase request, JSONObject result) {
                        listener.onResult(true);
                        toast(result.optString("message"));
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        listener.onResult(false);
                        toast(errorMsg);
                    }
                });
            }
        };
        mRecordListView.setAdapter(mAdapter);
    }

    @Override
    protected void fetchData() {
        startLoading("数据加载中", new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(mIsLoadingClosedByUser){
                    finish();
                }
            }
        });

        RequestManager.get().execute(new RGetParentCompleteCourseRewardList(), new RequestListener<List<ParentCourseReward>>() {
            @Override
            public void onSuccess(RequestBase request, List<ParentCourseReward> result) {
                mRewards.clear();
                mRewards.addAll(result);

                mAdapter.notifyDataSetChanged();

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

    public void onBackClick(View view){
        finish();
    }
}
