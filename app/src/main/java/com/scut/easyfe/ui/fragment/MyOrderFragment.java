package com.scut.easyfe.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.order.RGetOrders;
import com.scut.easyfe.ui.activity.EvaluationActivity;
import com.scut.easyfe.ui.activity.ReservedOrCompletedOrderActivity;
import com.scut.easyfe.ui.activity.ToDoOrderActivity;
import com.scut.easyfe.ui.adapter.MyOrderAdapter;
import com.scut.easyfe.ui.base.BaseRefreshFragment;
import com.scut.easyfe.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单页面使用的Fragment
 * @author jay
 */
public class MyOrderFragment extends BaseRefreshFragment {
    private int mOrderType;
    private int mState = Constants.Identifier.STATE_NORMAL;
    private ArrayList<Order> mOrders = new ArrayList<>();

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
        mAdapter = new MyOrderAdapter(getActivity(), mOrders);
        ((MyOrderAdapter)mAdapter).setState(mState);
        setBaseAdapter(mAdapter);
    }

    @Override
    protected void onLoadingData() {
        RequestManager.get().execute(
                new RGetOrders(App.getUser().getToken(), mOrderType, Constants.DefaultValue.DEFAULT_LOAD_COUNT, mOrders.size()),
                new RequestListener<List<Order>>() {
                    @Override
                    public void onSuccess(RequestBase request, List<Order> result) {
                        mOrders.addAll(result);
                        setIsLoading(false);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        toast(errorMsg);
                        setIsLoading(false);
                    }
                });
    }

    @Override
    protected void onRefreshData() {
        RequestManager.get().execute(
                new RGetOrders(App.getUser().getToken(), mOrderType, Constants.DefaultValue.DEFAULT_LOAD_COUNT, 0),
                new RequestListener<List<Order>>() {
            @Override
            public void onSuccess(RequestBase request, List<Order> result) {
                mOrders.clear();
                mOrders.addAll(result);
                setIsLoading(false);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
                setIsLoading(false);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(null == mActivity){
            return;
        }

        Bundle bundle = new Bundle();
        switch (mOrderType){
            case Constants.Identifier.ORDER_RESERVATION:
                bundle.putInt(Constants.Key.ORDER_TYPE, Constants.Identifier.ORDER_RESERVATION);
                bundle.putSerializable(Constants.Key.ORDER, mOrders.get(position));
                mActivity.redirectToActivity(mActivity, ReservedOrCompletedOrderActivity.class, bundle);
                break;

            case Constants.Identifier.ORDER_COMPLETED:
                bundle.putInt(Constants.Key.ORDER_TYPE, Constants.Identifier.ORDER_COMPLETED);
                bundle.putSerializable(Constants.Key.ORDER, mOrders.get(position));
//                mActivity.redirectToActivity(mActivity, ReservedOrCompletedOrderActivity.class, bundle);
                mActivity.redirectToActivity(mActivity, EvaluationActivity.class, bundle);
                break;

            case Constants.Identifier.ORDER_TO_DO:
                bundle.putSerializable(Constants.Key.ORDER, mOrders.get(position));
                mActivity.redirectToActivity(mActivity, ToDoOrderActivity.class, bundle);
                break;

            default:
                break;
        }
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
