package com.scut.easyfe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.scut.easyfe.R;
import com.scut.easyfe.entity.reward.BaseReward;
import com.scut.easyfe.ui.customView.SelectorButton;
import com.scut.easyfe.utils.OtherUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 问题的Adapter
 * Created by jay on 16/3/29.
 */
public class RewardAdapter extends BaseAdapter{
    private ArrayList<BaseReward> mRewords;
    private WeakReference<Context> mContextReference;

    public RewardAdapter(Context context, ArrayList<BaseReward> mRewords) {
        this.mRewords = mRewords;
        mContextReference = new WeakReference<>(context);
    }

    @Override
    public int getCount() {
        return mRewords.size();
    }

    @Override
    public Object getItem(int position) {
        return mRewords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(null == convertView){
            if(null == mContextReference.get()){
                return null;
            }

            convertView = LayoutInflater.from(mContextReference.get()).
                    inflate(R.layout.item_reward, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.rewardText.setText(mRewords.get(position).getAsString());

        if(mRewords.get(position).isReceivable()){
            holder.get.setIsSelected(false);
            holder.get.setEnabled(true);
            holder.get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!mRewords.get(position).isReceivable()){
                        return;
                    }

                    Toast.makeText(mContextReference.get(), "领取成功", Toast.LENGTH_LONG).show();
                }
            });

        }else{
            holder.get.setIsSelected(true);
            holder.get.setEnabled(false);
        }

        return convertView;
    }

    private class ViewHolder{
        TextView rewardText;
        //selected == true为不可领取
        SelectorButton get;

        public ViewHolder(View root) {
            rewardText = OtherUtils.findViewById(root, R.id.item_reward_tv_text);
            get = OtherUtils.findViewById(root, R.id.item_reward_sb_get);

            get.setSelectedTextColor(R.color.text_area_bg);
            get.setSelectedDrawable(R.drawable.shape_reward_selected);
            get.setUnselectedTextColor(R.color.theme_color);
            get.setUnselectDrawable(R.drawable.shape_reward_unselected);
        }
    }

}
