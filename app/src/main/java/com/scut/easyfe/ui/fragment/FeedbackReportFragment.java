package com.scut.easyfe.ui.fragment;

import android.view.View;
import android.widget.AdapterView;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.FeedbackReport;
import com.scut.easyfe.ui.adapter.FeedbackReportAdapter;
import com.scut.easyfe.ui.adapter.SpreadAdapter;
import com.scut.easyfe.ui.base.BaseRefreshFragment;
import com.scut.easyfe.utils.DensityUtil;

import java.util.ArrayList;

/**
 * 反馈报告使用的Fragment
 *
 * @author jay
 */
public class FeedbackReportFragment extends BaseRefreshFragment {
    private ArrayList<FeedbackReport> mOrders = new ArrayList<>();

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
        mAdapter = new FeedbackReportAdapter(getActivity(), mOrders);
        setBaseAdapter(mAdapter);
    }

    @Override
    protected void fetchData(View v) {
        loadData(0, Constants.DefaultValue.DEFAULT_LOAD_COUNT, false);
    }

    private void loadData(int skip, int limit, final boolean clear) {
        setIsLoading(true);

        //Todo
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
}
