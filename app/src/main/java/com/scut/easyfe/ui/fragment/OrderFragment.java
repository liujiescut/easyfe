package com.scut.easyfe.ui.fragment;

/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.view.View;
import android.widget.AdapterView;

import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Order;
import com.scut.easyfe.ui.adapter.OrderAdapter;
import com.scut.easyfe.ui.base.BaseRefreshFragment;
import com.scut.easyfe.utils.DensityUtil;

public class OrderFragment extends BaseRefreshFragment {
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
        mAdapter = new OrderAdapter(getActivity(), Order.getTestOrders());
        ((OrderAdapter)mAdapter).setState(mState);
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
            ((OrderAdapter)mAdapter).setState(state);
            mAdapter.notifyDataSetChanged();
        }
    }

    public int getState(){
        return mState;
    }
}
