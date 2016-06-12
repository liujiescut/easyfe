package com.scut.easyfe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.entity.FeedbackReport;
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
                //Todo Go to detail
            }
        });

        return convertView;
    }

    private String getContent(FeedbackReport report){
        StringBuilder builder = new StringBuilder();
        builder.append("订单号: ");
        builder.append(report.getOrderNum());
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
            enthusiasm.setClickable(false);
            absorption.setClickable(false);
        }
    }


}
