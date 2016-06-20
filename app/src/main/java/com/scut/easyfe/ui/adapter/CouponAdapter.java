package com.scut.easyfe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.entity.Coupon;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 我的现金券适配器
 * Created by jay on 16/5/15.
 */
public class CouponAdapter extends BaseAdapter{
    private WeakReference<Context> mContextReference;
    private List<Coupon> mCoupons = new ArrayList<>();

    public CouponAdapter(Context context, List<Coupon> coupons) {
        this.mCoupons = coupons;
        mContextReference = new WeakReference<>(context);
    }

    public CouponAdapter(Context context) {
    }

    @Override
    public int getCount() {
        return mCoupons.size();
    }

    @Override
    public Object getItem(int position) {
        return mCoupons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(null == convertView){
            if(null == mContextReference.get()){
                return null;
            }

            convertView = LayoutInflater.from(mContextReference.get()).
                    inflate(R.layout.item_coupon, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.detail.setText(mCoupons.get(position).toString());
        holder.money.setText(String.format(Locale.CHINA, "%d 元 x %d",
                mCoupons.get(position).getMoney() / 100, mCoupons.get(position).getCount()));

        return convertView;
    }

    private class ViewHolder{
        TextView detail;
        TextView money;

        public ViewHolder(View root) {
            detail = (TextView) root.findViewById(R.id.item_coupon_tv_text);
            money = (TextView) root.findViewById(R.id.item_coupon_tv_money);
        }
    }
}
