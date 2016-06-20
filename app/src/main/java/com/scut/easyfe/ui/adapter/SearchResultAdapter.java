package com.scut.easyfe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.ui.base.BaseListViewScrollStateAdapter;
import com.scut.easyfe.ui.customView.CircleImageView;
import com.scut.easyfe.utils.ImageUtils;
import com.scut.easyfe.utils.OtherUtils;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

/**
 * 单次预约多次预约筛选结果适配器
 * Created by jay on 16/3/31.
 */
public class SearchResultAdapter extends BaseListViewScrollStateAdapter {

    private List<Order> mOrders;
    private WeakReference<Context> mContextReference;

    public SearchResultAdapter(Context context, List<Order> mOrders) {
        mContextReference = new WeakReference<>(context);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            if (null == mContextReference.get()) {
                return null;
            }

            convertView = LayoutInflater.from(mContextReference.get()).
                    inflate(R.layout.item_search_result, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.teacherName.setText(mOrders.get(position).getTeacher().getName());
        holder.price.setText(String.format(Locale.CHINA, "%.2f 元/小时", mOrders.get(position).getPerPrice() / 100));
        holder.content.setText(Order.getBaseInfo(mOrders.get(position)));
        ImageUtils.displayImage(mOrders.get(position).getTeacher().getAvatar(), holder.avatar);

        return convertView;
    }


    private class ViewHolder{
        TextView teacherName;
        TextView price;
        TextView content;
        CircleImageView avatar;

        public ViewHolder(View root) {
            teacherName = OtherUtils.findViewById(root, R.id.item_search_result_tv_teacher);
            price = OtherUtils.findViewById(root, R.id.item_search_result_tv_price);
            content = OtherUtils.findViewById(root, R.id.item_search_result_tv_content);
            avatar = OtherUtils.findViewById(root, R.id.item_search_result_civ_avatar);
        }
    }

}
