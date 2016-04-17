package com.scut.easyfe.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.BriefOrder;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.ui.base.BaseListViewScrollStateAdapter;
import com.scut.easyfe.utils.DensityUtil;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.TimeUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单页面Adapter
 * Created by jay on 16/3/27.
 */
public class MyOrderAdapter extends BaseListViewScrollStateAdapter {
    private int mState = Constants.Identifier.STATE_NORMAL;

    private TranslateAnimation translateIn;
    private TranslateAnimation translateOut;
    private ScaleAnimation scaleIn;
    private ScaleAnimation scaleOut;

    private ArrayList<BriefOrder> mOrders = new ArrayList<>();
    private WeakReference<Activity> mActivityReference;

    public MyOrderAdapter(Activity activity, ArrayList<BriefOrder> mOrders) {
        mActivityReference = new WeakReference<>(activity);
        this.mOrders = mOrders;
    }

    @Override
    public int getCount() {
        return mOrders.size();
    }

    @Override
    public Object getItem(int position) {
        return mOrders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(null == convertView){
            if(null == mActivityReference.get()){
                return null;
            }

            if (translateIn == null) {
                initAnimation(parent.getContext());
            }

            convertView = LayoutInflater.from(mActivityReference.get()).
                    inflate(R.layout.item_my_order, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        BriefOrder order = mOrders.get(position);
        holder.orderNum.setText(String.format("订单 : %s", order.getOrderNumber()));
        holder.teacherName.setText(order.getTeacherName());
        holder.course.setText(order.getCourse());
        holder.date.setText(TimeUtils.getTime(TimeUtils.getDateFromString(order.getTeachTime().getDate()), "yyyy 年 MM 月 dd 日 (EEEE)"));
        holder.period.setText(order.getTeachTime().getChineseTime());
        holder.teachTime.setText(TimeUtils.getTimeFromMinute(order.getTime()));
        holder.price.setText(String.format("%.2f 元", order.getPrice()));

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(mOrders.get(position).isSelected());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOrders.get(position).setSelected(isChecked);
            }
        });

        if (mState == Constants.Identifier.STATE_NORMAL && holder.checkBox.getVisibility() == View.VISIBLE) {
            holder.background.startAnimation(translateOut);
            holder.checkBox.startAnimation(scaleOut);
            holder.checkBox.setVisibility(View.GONE);
        }
        if (mState == Constants.Identifier.STATE_EDIT && holder.checkBox.getVisibility() == View.GONE) {
            holder.background.startAnimation(translateIn);
            holder.checkBox.startAnimation(scaleIn);
            holder.checkBox.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private void initAnimation(Context context) {
        translateOut = new TranslateAnimation(DensityUtil.dip2px(context, 0), 0, 0, 0);
        translateOut.setDuration(250);
        translateOut.setFillAfter(true);
        translateOut.setInterpolator(new OvershootInterpolator());

        scaleOut = new ScaleAnimation(1f, 0, 1f, 0, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleOut.setDuration(250);
        scaleOut.setFillAfter(true);
        scaleOut.setInterpolator(new OvershootInterpolator());

        translateIn = new TranslateAnimation(0, DensityUtil.dip2px(context, 0), 0, 0);
        translateIn.setDuration(250);
        translateIn.setFillAfter(true);
        translateIn.setInterpolator(new OvershootInterpolator());

        scaleIn = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleIn.setDuration(250);
        scaleIn.setFillAfter(true);
        scaleIn.setInterpolator(new OvershootInterpolator());
    }

    private class ViewHolder {
        LinearLayout background;
        CheckBox checkBox;
        TextView orderNum;
        TextView teacherName;
        TextView course;
        TextView date;
        TextView period;
        TextView teachTime;
        TextView price;

        public ViewHolder(View root) {
            background = OtherUtils.findViewById(root , R.id.item_my_order_ll_background);
            checkBox = OtherUtils.findViewById(root, R.id.item_my_order_cb_select);
            orderNum = OtherUtils.findViewById(root, R.id.item_my_order_tv_num);
            teacherName = OtherUtils.findViewById(root, R.id.item_my_order_tv_teacher);
            course = OtherUtils.findViewById(root, R.id.item_my_order_tv_course);
            date = OtherUtils.findViewById(root, R.id.item_my_order_tv_date);
            period = OtherUtils.findViewById(root, R.id.item_my_order_tv_period);
            teachTime = OtherUtils.findViewById(root, R.id.item_my_order_tv_teach_time);
            price = OtherUtils.findViewById(root, R.id.item_my_order_tv_price);
        }
    }

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        this.mState = state;
    }
}
