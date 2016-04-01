package com.scut.easyfe.ui.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Order;
import com.scut.easyfe.ui.activity.TeacherInfoActivity;
import com.scut.easyfe.ui.adapter.SearchResultAdapter;
import com.scut.easyfe.ui.base.BaseRefreshFragment;
import com.scut.easyfe.utils.DensityUtil;

/**
 * 单次预约多次预约筛选结果页
 * Created by jay on 16/3/31.
 */
public class SearchResultFragment extends BaseRefreshFragment{
    private int mReserveType = Constants.Identifier.RESERVE_MULTI;

    @Override
    protected void initView(View view) {
        super.initView(view);

        if(null == getActivity()){
            return;
        }

        int mOrderNum = 4;

        TextView headView= new TextView(getActivity());
        headView.setMinimumHeight(DensityUtil.dip2px(mActivity, 5));
        if(mOrderNum > 5) {
            headView.setBackground(null);
        }else{
            headView.setBackgroundResource(R.color.search_result_tip_bg);
            headView.setGravity(Gravity.CENTER);
            headView.setTextColor(getResources().getColor(R.color.text_hint_color));
            headView.setText("人数太少？放宽点筛选项吧亲~O(∩_∩)O~");
            headView.setPadding(0, 32, 0, 32);
        }

        mDataListView.addHeaderView(headView);
        mDataListView.setDividerHeight(DensityUtil.dip2px(mActivity, 5));
        mAdapter = new SearchResultAdapter(getActivity(), Order.getTestOrders());
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
        if(null == mActivity){
            return;
        }

        //点击HeadView
        if(position == 0){
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key.RESERVE_WAY, getReserveType());
        bundle.putSerializable(Constants.Key.ORDER, Order.getTestOrders().get(position - 1));
        mActivity.redirectToActivity(mActivity, TeacherInfoActivity.class, bundle);
    }

    public int getReserveType() {
        return mReserveType;
    }

    public void setReserveType(int mReserveType) {
        this.mReserveType = mReserveType;
    }
}
