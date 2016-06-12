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
import com.scut.easyfe.entity.FeedbackReport;
import com.scut.easyfe.entity.order.Order;
import com.scut.easyfe.entity.user.TeacherInfo;
import com.scut.easyfe.ui.activity.auth.ParentRegisterActivity;
import com.scut.easyfe.ui.activity.order.ConfirmOrderActivity;
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
public class FeedbackReportAdapter extends BaseListViewScrollStateAdapter {
    private ArrayList<FeedbackReport> mReports;
    private WeakReference<Context> mContextReference;

    public FeedbackReportAdapter(Context context, ArrayList<FeedbackReport> mReports) {
        this.mReports = mReports;
        mContextReference = new WeakReference<>(context);
    }

    @Override
    public int getCount() {
        return mReports.size();
    }

    @Override
    public Object getItem(int position) {
        return mReports.get(position);
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

        return convertView;
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
