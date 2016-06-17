package com.scut.easyfe.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.VipEvent;
import com.scut.easyfe.ui.base.BaseListViewScrollStateAdapter;
import com.scut.easyfe.ui.customView.FixedClickListener;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.PayUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;

/**
 * 特价订单页面使用的Adapter
 * Created by jay on 16/3/29.
 */
public class VipAdapter extends BaseListViewScrollStateAdapter {
    private ArrayList<VipEvent> mVipEvents;
    private WeakReference<Context> mContextReference;
    private boolean mIsMyVipActivity = false;

    public VipAdapter(Context context, ArrayList<VipEvent> mVipEvents) {
        this(context, mVipEvents, false);
    }

    public VipAdapter(Context context, ArrayList<VipEvent> mVipEvents, boolean mIsMyVipActivity) {
        this.mVipEvents = mVipEvents;
        mContextReference = new WeakReference<>(context);
        this.mIsMyVipActivity = mIsMyVipActivity;
    }

    @Override
    public int getCount() {
        return mVipEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return mVipEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            if (mContextReference.get() == null) {
                return null;
            }
            convertView = LayoutInflater.from(mContextReference.get()).
                    inflate(R.layout.item_vip, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            if(mIsMyVipActivity){
                holder.reservable.setVisibility(View.GONE);
                holder.reserveArea.setVisibility(View.GONE);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(mVipEvents.get(position).getTitle());
        holder.detail.setText(mVipEvents.get(position).getDetail());
        holder.score.setText(String.format(Locale.CHINA, "%.0f 积分\n参加", mVipEvents.get(position).getScore()));
        holder.money.setText(String.format(Locale.CHINA, "%.0f 元\n参加", mVipEvents.get(position).getMoney()));
        holder.reservable.setText(mVipEvents.get(position).isReservable() ? "可预约呦~~" : "已订满啦 ~~");

        holder.score.setOnClickListener(new FixedClickListener() {
            @Override
            public void onFixClick(View view) {
                //Todo
            }
        });

        holder.money.setOnClickListener(new FixedClickListener() {
            @Override
            public void onFixClick(View view) {
                if (mContextReference.get() == null){
                    return;
                }

                new PayUtil((Activity) mContextReference.get(),
                        Constants.Identifier.BUY_VIP_EVENT, mVipEvents.get(position).get_id(),
                        mVipEvents.get(position).getPayTitle(),
                        mVipEvents.get(position).getPayInfo(),
                        (int)(mVipEvents.get(position).getMoney() * 1000),
                        new PayUtil.PayListener() {
                    @Override
                    public void onPayReturn(boolean success) {
                        if(success){
                            //Todo
                            LogUtils.i("支付成功");
                        }
                    }
                }).showPayDialog();
            }
        });
        boolean isReservable = mVipEvents.get(position).isReservable();
        int backgroundRes = isReservable ? R.drawable.selector_spread_reserve : R.drawable.shape_vip_unreservable;
        int textColor = App.get().getResources().getColor(isReservable ? R.color.theme_color : R.color.vip_un_reservable);
        holder.money.setBackgroundResource(backgroundRes);
        holder.money.setTextColor(textColor);
        holder.score.setBackgroundResource(backgroundRes);
        holder.score.setTextColor(textColor);

        return convertView;
    }

    private class ViewHolder {
        private TextView title;
        private TextView detail;
        private TextView reservable;
        private TextView money;
        private TextView score;
        private View reserveArea;

        public ViewHolder(View root) {
            title = OtherUtils.findViewById(root, R.id.item_vip_tv_title);
            detail = OtherUtils.findViewById(root, R.id.item_vip_tv_detail);
            score = OtherUtils.findViewById(root, R.id.item_vip_tv_score);
            money = OtherUtils.findViewById(root, R.id.item_vip_tv_money);
            reservable = OtherUtils.findViewById(root, R.id.item_vip_tv_reservable);
            reserveArea = OtherUtils.findViewById(root, R.id.item_vip_ll_reserve);
        }
    }
}
