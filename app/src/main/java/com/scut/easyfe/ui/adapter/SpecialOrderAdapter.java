package com.scut.easyfe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Order;
import com.scut.easyfe.ui.base.BaseListViewScrollStateAdapter;
import com.scut.easyfe.utils.OtherUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 特价订单页面使用的Adapter
 * Created by jay on 16/3/29.
 */
public class SpecialOrderAdapter extends BaseListViewScrollStateAdapter {
    private ArrayList<Order> mSpecialOrders;
    private WeakReference<Context> mContextReference;

    public SpecialOrderAdapter(Context context, ArrayList<Order> mSpecialOrders) {
        this.mSpecialOrders = mSpecialOrders;
        mContextReference = new WeakReference<>(context);
    }

    @Override
    public int getCount() {
        return mSpecialOrders.size();
    }

    @Override
    public Object getItem(int position) {
        return mSpecialOrders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            if (mContextReference.get() == null) {
                return null;
            }
            convertView = LayoutInflater.from(mContextReference.get()).
                    inflate(R.layout.item_special_order, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.teacherName.setText(mSpecialOrders.get(position).getTeacherName());
        holder.price.setText(String.format("%.2f 元/小时", mSpecialOrders.get(position).getSpecialPrice()));
        holder.contentUp.setText(getContentUp(position));
        holder.contentDown.setText(getContentDown(position));
        holder.reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo 实现地理位置公交时间获取对比判断
            }
        });

        return convertView;
    }

    private String getContentUp(int position){
        String contentUp = "";
        contentUp += "性别: ";
        contentUp += mSpecialOrders.get(position).getTeacherGender() == Constants.Identifier.MALE ? "男\n" : "女\n";
        contentUp += "大学专业: ";
        contentUp += mSpecialOrders.get(position).getTeacherSchool() + " " + mSpecialOrders.get(position).getTeacherProfession() + "\n";
        contentUp += "已家教过的孩子数量: ";
        contentUp += mSpecialOrders.get(position).getTeacherHasTeachCount() + "\n";
        contentUp += "已家教时长: ";
        contentUp += OtherUtils.getTimeFromMimute(mSpecialOrders.get(position).getTeacherHasTeachTime()) + "\n";
        contentUp += "综合评分: ";
        contentUp += String.format("%.2f 分", mSpecialOrders.get(position).getTeacherScore());
        return contentUp;
    }

    private String getContentDown(int position){
        String contentDown = "";
        contentDown += "授课年级: ";
        contentDown += mSpecialOrders.get(position).getStudentState() + " " + mSpecialOrders.get(position).getStudentGrade() + "\n";
        contentDown += "授课课程: ";
        contentDown += mSpecialOrders.get(position).getCourseName() + "\n";
        contentDown += "授课时间: ";
        contentDown += OtherUtils.getTime(mSpecialOrders.get(position).getDate(), "yyyy年MM月dd日(EEEE)") + " " + mSpecialOrders.get(position).getTeachPeriod() + "\n";
        contentDown += "授课时长: ";
        contentDown += OtherUtils.getTimeFromMimute(mSpecialOrders.get(position).getTeachTime()) + "\n";
        contentDown += "原价: ";
        contentDown += String.format("%f 元/小时", mSpecialOrders.get(position).getPrice());
        return contentDown;
    }

    private class ViewHolder {
        private TextView teacherName;
        private TextView price;
        private TextView contentUp;
        private TextView contentDown;
        private TextView reserve;

        public ViewHolder(View root) {
            teacherName = OtherUtils.findViewById(root, R.id.item_special_order_tv_teacher);
            price = OtherUtils.findViewById(root, R.id.item_special_order_tv_price);
            contentUp = OtherUtils.findViewById(root, R.id.item_special_order_tv_content_up);
            contentDown = OtherUtils.findViewById(root, R.id.item_special_order_tv_content_down);
            reserve = OtherUtils.findViewById(root, R.id.item_special_order_tv_reserve);
        }
    }
}
