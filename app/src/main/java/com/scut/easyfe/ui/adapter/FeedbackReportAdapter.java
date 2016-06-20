package com.scut.easyfe.ui.adapter;

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
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.order.RGetOrderDetail;
import com.scut.easyfe.ui.activity.order.TeacherReportActivity;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.base.BaseListViewScrollStateAdapter;
import com.scut.easyfe.ui.customView.FixedClickListener;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.TimeUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.techery.properratingbar.ProperRatingBar;

/**
 * 特价订单页面使用的Adapter
 * Created by jay on 16/3/29.
 */
public class FeedbackReportAdapter extends BaseListViewScrollStateAdapter {
    private ArrayList<FeedbackReport> mReports;
    private WeakReference<BaseActivity> mActivityReference;

    public FeedbackReportAdapter(BaseActivity context, ArrayList<FeedbackReport> mReports) {
        this.mReports = mReports;
        mActivityReference = new WeakReference<>(context);
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
            if (mActivityReference.get() == null) {
                return null;
            }
            convertView = LayoutInflater.from(mActivityReference.get()).
                    inflate(R.layout.item_feedback_report, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.content.setText(getContent(mReports.get(position)));
        holder.absorption.setRating(mReports.get(position).getGetLevel());
        holder.enthusiasm.setRating(mReports.get(position).getEnthusiasm());
        holder.detail.setOnClickListener(new FixedClickListener() {
            @Override
            public void onFixClick(View view) {
                if (null != mActivityReference.get()) {
                    mActivityReference.get().startLoading("请稍候");
                }
                RequestManager.get().execute(new RGetOrderDetail(
                                mReports.get(position).get_id()),
                        new RequestListener<Order>() {
                            @Override
                            public void onSuccess(RequestBase request, Order result) {
                                if (null != result && null != mActivityReference.get()) {
                                    mActivityReference.get().stopLoading();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(Constants.Key.ORDER, result);
                                    bundle.putInt(Constants.Key.TO_TEACHER_REPORT_ACTIVITY_TYPE, Constants.Identifier.TYPE_SHOW);
                                    mActivityReference.get().redirectToActivity(mActivityReference.get(), TeacherReportActivity.class, bundle);
                                }
                            }

                            @Override
                            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                                if (null != mActivityReference.get()) {
                                    mActivityReference.get().toast(errorMsg);
                                }
                            }
                        });
            }
        });

        return convertView;
    }

    private String getContent(FeedbackReport report){
        StringBuilder builder = new StringBuilder();
        builder.append("订单号: ");
        builder.append(report.getOrderNumber());
        builder.append("\n");
        if(App.getUser().isTeacher()){
            builder.append("家长: ");
            builder.append(report.getParentName());
            builder.append("\n");
        }else{
            builder.append("家教: ");
            builder.append(report.getTeacherName());
            builder.append("\n");
        }
        builder.append("时间: ");
        builder.append(String.format("%s %s",
                TimeUtils.getTime(TimeUtils.getDateFromString(report.getTeachTime().getDate()), "yyyy年MM月dd日(EEEE)"),
                report.getTeachTime().getChineseTime()));
        builder.append("\n");
        builder.append("正确率: ");
        builder.append(report.getRightPercent());

        return builder.toString();
    }

    private class ViewHolder {
        private TextView content;
        private TextView detail;
        private ProperRatingBar enthusiasm;
        private ProperRatingBar absorption;

        public ViewHolder(View root) {
            content = OtherUtils.findViewById(root, R.id.item_feedback_report_tv_content);
            detail = OtherUtils.findViewById(root, R.id.item_feedback_report_tv_detail);
            enthusiasm = OtherUtils.findViewById(root, R.id.item_feedback_report_prb_enthusiasm);
            absorption = OtherUtils.findViewById(root, R.id.item_feedback_report_prb_absorption_rate);
        }
    }


}
