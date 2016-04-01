package com.scut.easyfe.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.entity.ToSelectItem;
import com.scut.easyfe.utils.OtherUtils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * ListView 用于多选时的Adapter
 * Created by jay on 16/3/30.
 */
public class SelectItemAdapter extends BaseAdapter{
    private WeakReference<Context> mContextReference;
    private List<ToSelectItem> mToSelectItems;
    private boolean mSelectable = true;

    public SelectItemAdapter(Context context, List<ToSelectItem> toSelectItems) {
        this.mContextReference = new WeakReference<>(context);
        this.mToSelectItems = toSelectItems;
    }

    @Override
    public int getCount() {
        return mToSelectItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mToSelectItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (null == convertView) {
            if(null == mContextReference.get()){
                return null;
            }

            convertView = LayoutInflater.from(mContextReference.get()).
                    inflate(R.layout.item_to_select, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.item.setText(mToSelectItems.get(position).getText());
        if(mSelectable) {
            holder.item.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,
                    mToSelectItems.get(position).isSelected() ? R.mipmap.icon_yes_grey_padding : 0, 0);
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mToSelectItems.get(position).setSelected(!mToSelectItems.get(position).isSelected());
                    holder.item.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,
                            mToSelectItems.get(position).isSelected() ? R.mipmap.icon_yes_grey_padding : 0, 0);
                }
            });
        }else{
            holder.item.setClickable(false);
            holder.item.setTextColor(App.get().getResources().getColor(R.color.text_area_text_color));
            holder.item.setTextSize(12);
        }
        return convertView;
    }

    private class ViewHolder{
        TextView item;

        public ViewHolder(View root) {
            item = OtherUtils.findViewById(root, R.id.to_select_tv_item);
        }
    }

    public void setSelectable(boolean mSelectable) {
        this.mSelectable = mSelectable;
    }
}
