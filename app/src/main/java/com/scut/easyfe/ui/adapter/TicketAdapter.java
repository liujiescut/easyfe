package com.scut.easyfe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.entity.Ticket;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的现金券适配器
 * Created by jay on 16/5/15.
 */
public class TicketAdapter extends BaseAdapter{
    private WeakReference<Context> mContextReference;
    private List<Ticket> mTickets = new ArrayList<>();

    public TicketAdapter(Context context, List<Ticket> tickets) {
        this.mTickets = tickets;
        mContextReference = new WeakReference<>(context);
    }

    public TicketAdapter(Context context) {
    }

    @Override
    public int getCount() {
        return mTickets.size();
    }

    @Override
    public Object getItem(int position) {
        return mTickets.get(position);
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
                    inflate(R.layout.item_ticket, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.detail.setText(mTickets.get(position).toString());
        holder.money.setText(String.format("%.0f 元", mTickets.get(position).getMoney()));

        return convertView;
    }

    private class ViewHolder{
        TextView detail;
        TextView money;

        public ViewHolder(View root) {
            detail = (TextView) root.findViewById(R.id.item_ticket_tv_text);
            money = (TextView) root.findViewById(R.id.item_ticket_tv_money);
        }
    }
}
