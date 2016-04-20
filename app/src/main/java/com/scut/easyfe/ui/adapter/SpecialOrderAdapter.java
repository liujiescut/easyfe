package com.scut.easyfe.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.entity.user.TeacherInfo;
import com.scut.easyfe.ui.activity.ConfirmOrderActivity;
import com.scut.easyfe.ui.activity.LoginActivity;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.base.BaseListViewScrollStateAdapter;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.LogUtils;
import com.scut.easyfe.utils.MapUtils;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.TimeUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 特价订单页面使用的Adapter
 * Created by jay on 16/3/29.
 */
public class SpecialOrderAdapter extends BaseListViewScrollStateAdapter {
    private ArrayList<Order> mOrders;
    private WeakReference<Context> mContextReference;

    public SpecialOrderAdapter(Context context, ArrayList<Order> mOrders) {
        this.mOrders = mOrders;
        mContextReference = new WeakReference<>(context);
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
        if (null == convertView) {
            if (mContextReference.get() == null) {
                return null;
            }
            convertView = LayoutInflater.from(mContextReference.get()).
                    inflate(R.layout.item_special_order, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.teacherName.setText(mOrders.get(position).getTeacher().getName());
        holder.price.setText(String.format("%.2f 元/小时", mOrders.get(position).getTotalPrice() - mOrders.get(position).getSubsidy()));
        holder.contentUp.setText(getContentUp(position));
        holder.contentDown.setText(getContentDown(position));
        holder.reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!App.getUser(false).isParent()){
                    if(null == mContextReference.get()){
                        return;
                    }

                    DialogUtils.makeChooseDialog(mContextReference.get(), "提示", "只有家长才可以预约特价订单呦-.-\n去注册家长?", new DialogUtils.OnChooseListener() {
                        @Override
                        public void onChoose(boolean sure) {
                            if (sure) {
                                if(null == mContextReference.get()){
                                    return;
                                }

                                BaseActivity activity = (BaseActivity) mContextReference.get();
                                activity.redirectToActivity(activity, LoginActivity.class);
                            }
                        }
                    });
                    return;
                }

                if(App.getUser().get_id().equals(mOrders.get(position).getTeacher().get_id())){
                    DialogUtils.makeConfirmDialog(mContextReference.get(), "提示", "您不能预约自己的特价订单呦");
                    return;
                }

                final TeacherInfo teacher = mOrders.get(position).getTeacher();

                MapUtils.getDurationFromPosition(
                        teacher.getPosition().getLatitude(),
                        teacher.getPosition().getLongitude(),
                        App.getUser().getPosition().getLatitude(),
                        App.getUser().getPosition().getLongitude(),
                        teacher.getPosition().getCity(),
                        new MapUtils.GetDurationCallback() {

                            @Override
                            public void onSuccess(int durationSeconds) {
                                if(null == mContextReference.get()){
                                    return;
                                }

                                if (durationSeconds / 60 > teacher.getTeacherMessage().getMaxTrafficTime()) {
                                    DialogUtils.makeConfirmDialog(mContextReference.get(),
                                            "温馨提示", "您与家教老师的距离已超过他（她）设定的最远距离，试试别的老师吧。");

                                } else {
                                    mOrders.get(position).setTrafficTime(durationSeconds / 60);
                                    if (durationSeconds / 60 < teacher.getTeacherMessage().getFreeTrafficTime()) {
                                        mOrders.get(position).setSubsidy(0);
                                    }else {
                                        //因为服务器返回的路程费在家教信息中
                                        mOrders.get(position).setSubsidy(mOrders.get(position).getTeacher().getTeacherMessage().getSubsidy());
                                    }

                                    if (null != mContextReference.get()) {
                                        Bundle extras = new Bundle();
                                        extras.putInt(Constants.Key.CONFIRM_ORDER_TYPE, Constants.Identifier.CONFIRM_ORDER_SPECIAL);
                                        extras.putSerializable(Constants.Key.ORDER, mOrders.get(position));
                                        ((BaseActivity) mContextReference.get()).redirectToActivity(mContextReference.get(), ConfirmOrderActivity.class, extras);
                                    }

                                }

                            }

                            @Override
                            public void onFailed(String errorMsg) {
                                LogUtils.i(Constants.Tag.MAP_TAG, errorMsg);
                                DialogUtils.makeConfirmDialog(mContextReference.get(),
                                        "温馨提示", "您与家教老师的距离已超过他（她）设定的最远距离，试试别的老师吧。");
                            }
                        });

            }
        });

        return convertView;
    }

    private String getContentUp(int position) {
        TeacherInfo teacher = mOrders.get(position).getTeacher();
        String contentUp = "";
        contentUp += "性别: ";
        contentUp += teacher.getGender() == Constants.Identifier.MALE ? "男\n" : "女\n";
        contentUp += "大学专业: ";
        contentUp += teacher.getTeacherMessage().getSchool() + " "
                + teacher.getTeacherMessage().getProfession() + "\n";
        contentUp += "已家教过的孩子数量: ";
        contentUp += teacher.getTeacherMessage().getTeachCount() + "\n";
        contentUp += "已家教时长: ";
        contentUp += teacher.getTeacherMessage().getHadTeach() + "\n";
        contentUp += "综合评分: ";
        contentUp += String.format("%.2f 分", teacher.getTeacherMessage().getScore());
        return contentUp;
    }

    private String getContentDown(int position) {
        String contentDown = "";
        contentDown += "授课年级: ";
        contentDown += mOrders.get(position).getGrade() + "\n";
        contentDown += "授课课程: ";
        contentDown += mOrders.get(position).getCourse() + "\n";
        contentDown += "授课时间: ";
        contentDown += TimeUtils.getTime(
                TimeUtils.getDateFromString(mOrders.get(position).getTeachTime().getDate()),
                "yyyy年MM月dd日(EEEE)") + " " +
                mOrders.get(position).getTeachTime().getChineseTime() + "\n";
        contentDown += "授课时长: ";
        contentDown += TimeUtils.getTimeFromMinute(mOrders.get(position).getTime());
        if(0 != mOrders.get(position).getOriginalPrice()) {
            contentDown += "\n原价: ";
            contentDown += String.format("%.0f 元/小时", mOrders.get(position).getOriginalPrice());
        }
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
