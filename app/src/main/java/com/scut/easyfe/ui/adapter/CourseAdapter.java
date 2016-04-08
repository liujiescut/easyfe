package com.scut.easyfe.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.customView.SelectorButton;

/**
 * 可教授课程课程的Adapter
 * Created by jay on 16/3/25.
 */
public class CourseAdapter extends BaseAdapter{
    private String[] mCourses;
    private boolean mItemClickable = true;

    public CourseAdapter(String [] mCourses) {
        this.mCourses = mCourses;
    }

    @Override
    public int getCount() {
        return mCourses.length;
    }

    @Override
    public Object getItem(int position) {
        return mCourses[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(null == convertView){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_teach_course, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.selectorButton.setBothText(mCourses[position]);

        return convertView;
    }

    public void setItemClickable(boolean itemClickable) {
        this.mItemClickable = itemClickable;
    }

    private class ViewHolder{
        SelectorButton selectorButton;
        public ViewHolder(View root) {
            this.selectorButton = (SelectorButton) root.findViewById(R.id.item_teach_course);
            this.selectorButton.setClickable(mItemClickable);
        }
    }
}
