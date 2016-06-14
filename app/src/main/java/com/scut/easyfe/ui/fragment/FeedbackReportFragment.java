package com.scut.easyfe.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.FeedbackReport;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.order.RGetFeedbackReport;
import com.scut.easyfe.network.request.order.RGetOrderDetail;
import com.scut.easyfe.ui.activity.order.TeacherReportActivity;
import com.scut.easyfe.ui.adapter.FeedbackReportAdapter;
import com.scut.easyfe.ui.base.BaseRefreshFragment;
import com.scut.easyfe.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 反馈报告使用的Fragment
 *
 * @author jay
 */
public class FeedbackReportFragment extends BaseRefreshFragment {
    private ArrayList<FeedbackReport> mReports = new ArrayList<>();
    private String mSort = Constants.Identifier.SORT_BY_NAME;

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
        mAdapter = new FeedbackReportAdapter(mActivity, mReports);
        setBaseAdapter(mAdapter);
    }

    @Override
    protected void fetchData(View v) {
        loadData(0, Constants.DefaultValue.DEFAULT_LOAD_COUNT, false);
    }

    private void loadData(int skip, int limit, final boolean clear) {
        setIsLoading(true);

        RequestManager.get().execute(new RGetFeedbackReport(limit, skip, mSort), new RequestListener<List<FeedbackReport>>() {
            @Override
            public void onSuccess(RequestBase request, List<FeedbackReport> result) {
                if(clear){
                    mReports.clear();
                }

                if (null != result) {
                    if(result.size() != 0){
                        mReports.addAll(result);
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
        loadData(mReports.size(), Constants.DefaultValue.DEFAULT_LOAD_COUNT, false);
    }

    @Override
    protected void onRefreshData() {
        loadData(0, Constants.DefaultValue.DEFAULT_LOAD_COUNT, true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void setSortWay(String way){
        if(Constants.Identifier.SORT_BY_NAME.equals(way) || Constants.Identifier.SORT_BY_TIME.equals(way)){
            mSort = way;
            onRefreshData();
        }
    }
}
