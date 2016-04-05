package com.scut.easyfe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.entity.test.Problem;
import com.scut.easyfe.utils.OtherUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 问题的Adapter
 * Created by jay on 16/3/29.
 */
public class ProblemAdapter extends BaseAdapter{
    private ArrayList<Problem> mProblems;
    private WeakReference<Context> mContextReference;

    public ProblemAdapter(Context context, ArrayList<Problem> mProblems) {
        this.mProblems = mProblems;
        mContextReference = new WeakReference<>(context);
    }

    @Override
    public int getCount() {
        return mProblems.size();
    }

    @Override
    public Object getItem(int position) {
        return mProblems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(null == convertView){
            if(null == mContextReference.get()){
                return null;
            }

            convertView = LayoutInflater.from(mContextReference.get()).
                    inflate(R.layout.item_problem, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.question.setText(mProblems.get(position).getQuestion());
        holder.answer.setText(mProblems.get(position).getAnswer());

        holder.question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.answer.getVisibility() == View.GONE){
                    holder.answer.setVisibility(View.VISIBLE);
                    holder.question.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_up_arrow_padding, 0);
                }else{
                    holder.answer.setVisibility(View.GONE);
                    holder.question.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_down_arrow_padding, 0);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder{
        TextView question;
        TextView answer;

        public ViewHolder(View root) {
            question = OtherUtils.findViewById(root, R.id.item_problem_tv_question);
            answer = OtherUtils.findViewById(root, R.id.item_problem_tv_answer);
        }
    }

}
