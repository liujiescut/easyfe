package com.scut.easyfe.ui.fragment;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.entity.Order;
import com.scut.easyfe.ui.base.BaseRefreshFragment;
import com.scut.easyfe.utils.DensityUtil;

/**
 * 单次预约多次预约筛选结果页
 * Created by jay on 16/3/31.
 */
public class SearchResultFragment extends BaseRefreshFragment{

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

    }
}
