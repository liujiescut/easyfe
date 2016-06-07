package com.scut.easyfe.ui.fragment;

import android.view.View;
import android.widget.AdapterView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.order.RGetSpecialOrder;
import com.scut.easyfe.ui.adapter.SpecialOrderAdapter;
import com.scut.easyfe.ui.base.BaseRefreshFragment;
import com.scut.easyfe.utils.DensityUtil;
import com.sina.weibo.sdk.api.share.BaseRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 特价订单使用的Fragment
 * @author jay
 */
public class SpecialOrderFragment extends BaseRefreshFragment {
    private ArrayList<Order> mOrders = new ArrayList<>();

    //是否显示的是搜索结果
    private boolean mShowSearchResult = false;
    private String mCourse = "";
    private String mGrade = "";

    @Override
    protected void initView(View view) {
        super.initView(view);

        if(null == mActivity){
            return;
        }

        View headView= new View(mActivity);
        headView.setMinimumHeight(DensityUtil.dip2px(mActivity, 5));
        headView.setBackground(null);

        mDataListView.addHeaderView(headView);
        mDataListView.setDividerHeight(DensityUtil.dip2px(mActivity, 5));
        mAdapter = new SpecialOrderAdapter(getActivity(), mOrders);
        setBaseAdapter(mAdapter);
    }

    @Override
    protected void fetchData(View v) {
        if(!mShowSearchResult) {
            loadData(0, Constants.DefaultValue.DEFAULT_LOAD_COUNT, false);
        }
    }

    private void loadData(int skip, int limit, final boolean clear){
        setIsLoading(true);

        RequestBase<List<Order>> request;
        if(mShowSearchResult){
            request = new RGetSpecialOrder(mCourse, mGrade, limit, skip);
        }else{
            request = new RGetSpecialOrder(limit, skip);
        }
        RequestManager.get().execute(request, new RequestListener<List<Order>>() {
            @Override
            public void onSuccess(RequestBase request, List<Order> result) {
                if(clear){
                    mOrders.clear();
                }
                mOrders.addAll(result);
                mAdapter.notifyDataSetChanged();

                if(result.size() == 0){
                    if(mShowSearchResult && mOrders.size() == 0){
                        showTips(R.string.search_spread_empty_info);
                    }else {
                        toast("没有更多数据可以加载");
                    }
                }else {
                    hideTips();
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
        loadData(mOrders.size(), Constants.DefaultValue.DEFAULT_LOAD_COUNT, false);
    }

    @Override
    protected void onRefreshData() {
        loadData(0, Constants.DefaultValue.DEFAULT_LOAD_COUNT, true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void setShowSearchResult(boolean mShowSearchResult) {
        this.mShowSearchResult = mShowSearchResult;
    }

    public void setSearchCourse(String mCourse) {
        this.mCourse = mCourse;
    }

    public void setSearchGrade(String mGrade) {
        this.mGrade = mGrade;
    }

    /**
     * 当被用作搜索页面时才可用这个Api
     */
    public void doSearch(){
        loadData(0, Constants.DefaultValue.DEFAULT_LOAD_COUNT, true);
    }
}
