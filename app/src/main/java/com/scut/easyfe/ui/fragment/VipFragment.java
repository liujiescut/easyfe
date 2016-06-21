package com.scut.easyfe.ui.fragment;

import android.view.View;
import android.widget.AdapterView;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.VipEvent;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.RGetVipEvent;
import com.scut.easyfe.ui.adapter.VipAdapter;
import com.scut.easyfe.ui.base.BaseRefreshFragment;
import com.scut.easyfe.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员活动页面 我的会员活动页面
 *
 * @author jay
 */
public class VipFragment extends BaseRefreshFragment {
    private ArrayList<VipEvent> mVipEvents = new ArrayList<>();

    //是否是我的会员活动页面
    private boolean mIsMyVipEvent = false;

    @Override
    protected void initView(View view) {
        super.initView(view);

        if (null == mActivity) {
            return;
        }

        View headView = new View(mActivity);
        headView.setMinimumHeight(DensityUtil.dip2px(mActivity, 5));
        headView.setBackground(null);

        mDataListView.addHeaderView(headView);
        mDataListView.setDividerHeight(DensityUtil.dip2px(mActivity, 5));
        mAdapter = new VipAdapter(getActivity(), mVipEvents, mIsMyVipEvent);
        setBaseAdapter(mAdapter);
    }

    @Override
    protected void fetchData(View v) {
        loadData(0, Constants.DefaultValue.DEFAULT_LOAD_COUNT, false);
    }

    private void loadData(int skip, int limit, final boolean clear) {
        setIsLoading(true);
        RequestManager.get().execute(new RGetVipEvent(limit, skip, mIsMyVipEvent), new RequestListener<List<VipEvent>>() {
            @Override
            public void onSuccess(RequestBase request, List<VipEvent> result) {
                if (clear){
                    mVipEvents.clear();
                }

                if (null != result) {
                    if(result.size() != 0){
                        mVipEvents.addAll(result);
                        mAdapter.notifyDataSetChanged();
                    }else{
                        toast("没有更多数据可以加载");
                    }
                }

                setIsLoading(false);
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
                setIsLoading(false);
            }
        });
    }

    @Override
    protected void onLoadingData() {
        loadData(mVipEvents.size(), Constants.DefaultValue.DEFAULT_LOAD_COUNT, false);
    }

    @Override
    protected void onRefreshData() {
        loadData(0, Constants.DefaultValue.DEFAULT_LOAD_COUNT, true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void setIsMyVipEvent(boolean isMyVipEvent){
        mIsMyVipEvent = isMyVipEvent;
    }

    public void refresh(){
        if (null != mAdapter) {
            ((VipAdapter)mAdapter).setIsMyVipActivity(mIsMyVipEvent);
            onRefreshData();
        }
    }
}
