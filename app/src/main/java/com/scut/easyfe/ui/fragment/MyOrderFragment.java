package com.scut.easyfe.ui.fragment;

import android.view.View;
import android.widget.AdapterView;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Order;
import com.scut.easyfe.ui.adapter.MyOrderAdapter;
import com.scut.easyfe.ui.base.BaseRefreshFragment;
import com.scut.easyfe.utils.DensityUtil;

/**
 * 我的订单页面使用的Fragment
 * @author jay
 */
public class MyOrderFragment extends BaseRefreshFragment {
    private int mOrderType;
    private int mState = Constants.Identifier.STATE_NORMAL;

    @Override
    protected void initView(View view) {
        super.initView(view);

        if(null == getActivity()){
            return;
        }

        View headView= new View(getActivity());
        headView.setMinimumHeight(DensityUtil.dip2px(mActivity, 5));
        headView.setBackground(null);

        mDataListView.addHeaderView(headView);
        mDataListView.setDividerHeight(DensityUtil.dip2px(mActivity, 5));
        mAdapter = new MyOrderAdapter(getActivity(), Order.getTestOrders());
        ((MyOrderAdapter)mAdapter).setState(mState);
        setBaseAdapter(mAdapter);
    }

    @Override
    protected void onLoadingData() {

    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void setType(int type){
        mOrderType = type;
    }

    public void setState(int state){
        mState = state;
        if(null != mAdapter){
            ((MyOrderAdapter)mAdapter).setState(state);
            mAdapter.notifyDataSetChanged();
        }
    }

    public int getState(){
        return mState;
    }
}
