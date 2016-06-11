package com.scut.easyfe.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.fragment.SearchResultFragment;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 单次多次预约搜索结果
 */
public class SearchResultActivity extends BaseActivity {

    private int mReserveType = Constants.Identifier.RESERVE_MULTI;
    private List<Order> mOrders = new ArrayList<>();

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_search_result);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mReserveType = extras.getInt(Constants.Key.RESERVE_WAY, Constants.Identifier.RESERVE_MULTI);
                mOrders = (List<Order>) extras.getSerializable(Constants.Key.ORDERS);
            }
        }
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("筛选结果");
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setOrders(mOrders);
        fragment.setReserveType(mReserveType);
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.search_result_fl_container, fragment).
                commit();
    }

    public void onBackClick(View view){
        finish();
    }

}
