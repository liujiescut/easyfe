package com.scut.easyfe.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scut.easyfe.app.App;
import com.scut.easyfe.ui.fragment.MyOrderFragment;

import java.util.ArrayList;

/**
 * 我的订单页面各种订单Tab的Adapter
 * @author jay
 */
public class OrderPagerAdapter extends FragmentPagerAdapter {

    ArrayList<MyOrderFragment> fragments = new ArrayList<>();
    private  int[] mTitles;
    private  int[] mTypes;

    public OrderPagerAdapter(FragmentManager fm, int[] titles, int[] types) {
        super(fm);
        this.mTypes = types;
        this.mTitles = titles;

        fragments = new ArrayList<>(mTitles.length);
        for (int i = 0; i < mTypes.length; i++) {
            MyOrderFragment myOrderFragment = new MyOrderFragment();
            myOrderFragment.setType(mTypes[i]);
            fragments.add(myOrderFragment);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return App.get().getResources().getString(mTitles[position]);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public MyOrderFragment getItem(int position) {
        return fragments.get(position);
    }

}
