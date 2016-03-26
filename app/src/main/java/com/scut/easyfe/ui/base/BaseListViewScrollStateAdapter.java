package com.scut.easyfe.ui.base;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by warren on 12/5/15.
 * 这个adapter用于listView那些滑动的时候的回调，主要是处理图片滑动的时候不加载
 */
public abstract class BaseListViewScrollStateAdapter extends BaseAdapter {
    public static ImageLoader imageLoader;

    //正在滑动
    public void ListViewScrolling() {
        if (imageLoader != null)
            imageLoader.pause();
    }

    //停止滑动
    public void ListViewStopScrolling() {
        if (imageLoader != null)
            imageLoader.resume();
    }

    public static ImageLoader getImageLoader() {
        if (imageLoader == null)
            synchronized (ImageLoader.class) {
                if (imageLoader == null)
                    imageLoader = ImageLoader.getInstance();
            }
        return imageLoader;
    }
    //闪烁问题(ImageView要setTag)
    public ImageLoadingListener getDefaultListener(){

        return new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                ((ImageView)view).setImageBitmap(null);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (loadedImage != null &&view.getTag()!=null&& view.getTag().equals(imageUri))
                    ((ImageView)view).setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        };
    }
}
