package com.scut.easyfe.ui.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Order;
import com.scut.easyfe.ui.base.BaseListViewScrollStateAdapter;
import com.scut.easyfe.ui.customView.CircleImageView;
import com.scut.easyfe.utils.OtherUtils;

import java.lang.ref.WeakReference;
import java.util.List;

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

        holder.teacherName.setText(mOrders.get(position).getTeacherName());
        holder.price.setText(String.format("%.2f 元/小时", mOrders.get(position).getPrice()));
        holder.content.setText(getContent(mOrders.get(position)));
        ImageLoader.getInstance().displayImage(mOrders.get(position).getTeacherAvatar(), holder.avatar);

        return convertView;
    }

    private String getContent(Order order){
        String content = "";
        content += String.format("性别: %s\n", order.getTeacherGender() == Constants.Identifier.MALE ? "男" : "女");
        content += String.format("年龄: %d\n", order.getTeacherAge());
        content += String.format("大学专业: %s %s\n", order.getTeacherSchool(), order.getTeacherProfession());
        content += String.format("已家教过的孩子数量：%d\n", order.getTeacherHasTeachCount());
        content += String.format("已家教的时长：%d 小时\n", order.getTeacherHasTeachTime());
        content += String.format("综合评分：%.2f", order.getTeacherScore());
        return content;
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
