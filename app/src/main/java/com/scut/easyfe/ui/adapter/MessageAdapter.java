package com.scut.easyfe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.entity.Message;
import com.scut.easyfe.utils.OtherUtils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 消息中心Adapter
 * Created by jay on 16/3/29.
 */
public class MessageAdapter extends BaseAdapter{
    private List<Message> mMessages;
    private WeakReference<Context> mContextReference;
    public MessageAdapter(Context context, List<Message> messages) {
        this.mMessages = messages;
        this.mContextReference = new WeakReference<>(context);
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(null == convertView){
            if(null == mContextReference.get()){
                return null;
            }
            convertView = LayoutInflater.from(mContextReference.get()).
                    inflate(R.layout.item_message_center, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.sender.setText(mMessages.get(position).getSenderName());
        holder.date.setText(OtherUtils.getTime(mMessages.get(position).getTimestamp(), "yyyy-MM-dd HH: mm"));
        holder.content.setText(mMessages.get(position).getContent());
        return convertView;
    }

    private class ViewHolder{
        TextView sender;
        TextView date;
        TextView content;

        public ViewHolder(View root) {
            this.sender = OtherUtils.findViewById(root, R.id.item_message_tv_sender);
            this.date = OtherUtils.findViewById(root, R.id.item_message_tv_date);
            this.content = OtherUtils.findViewById(root, R.id.item_message_tv_content);
        }
    }
}
