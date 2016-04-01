package com.scut.easyfe.utils;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 图像相关的工具(跟具体的图片加载工具解耦)
 * Created by jay on 16/4/1.
 */
public class ImageUtils {
    public static void displayImage(String url, ImageView imageView){
        ImageLoader.getInstance().displayImage(url, imageView);
    }
}
