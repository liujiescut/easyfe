package com.scut.easyfe.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.scut.easyfe.app.App;

import java.util.List;

public class ImagePagerAdapter<T> extends PagerAdapter {

    private List<T> mImageResourceIds;
    private OnItemClickListener mListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            //default implementation
        }
    };

    public ImagePagerAdapter(List<T> imageResourceIds) {
        this.mImageResourceIds = imageResourceIds;
    }

    @Override
    public int getCount() {
        return mImageResourceIds.size();
    }

    public int getFinalPosition() {
        return getCount() - 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setBackgroundColor(App.get().getResources().getColor(android.R.color.white));
        imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        if(mImageResourceIds.get(position) instanceof Integer) {
            imageView.setImageResource((Integer) mImageResourceIds.get(position));
        }else if(mImageResourceIds.get(position) instanceof String){
            ImageLoader.getInstance().displayImage((String)mImageResourceIds.get(position), imageView);
        }
        container.addView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(position);
            }
        });

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setOnItemClickListener(@NonNull OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
