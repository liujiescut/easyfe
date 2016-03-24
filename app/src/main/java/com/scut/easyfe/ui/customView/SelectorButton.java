package com.scut.easyfe.ui.customView;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;

/**
 * 可选中跟不选中的Button
 * Created by jay on 16/3/24.
 */
public class SelectorButton extends TextView{
    private boolean mIsSelected = false;              //是否选中
    private String mSelectedText = "";                //选中文字
    private String mUnselectedText = "";                //未选中文字
    private int mSelectedDrawable = R.drawable.shape_selector_btn_selected;               //选中背景
    private int mUnselectedDrawable = R.drawable.shape_selector_btn_unselect;               //未选中背景
    private int mSelectedTextColor = R.color.title_text_color;                      //选中文字颜色
    private int mUnselectedTextColor = R.color.theme_color_dark;                      //未选中文字颜色
    public SelectorButton(Context context) {
        super(context);
        init();
    }

    public SelectorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        setClickable(true);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsSelected = !mIsSelected;
                refresh();
            }
        });
        refresh();
    }

    private void refresh(){
        setBackground(getResources().getDrawable(mIsSelected ?  mSelectedDrawable : mUnselectedDrawable));
        setTextColor(getResources().getColor(mIsSelected ? mSelectedTextColor : mUnselectedTextColor));
        setBothText(mIsSelected ? mSelectedText : mUnselectedText);
    }

    /**
     * 设置选中状态文字
     */
    public void setSelectedText(String mSelectedText) {
        this.mSelectedText = mSelectedText;
    }

    /**
     * 设置是否选中
     */
    public void setIsSelected(boolean mIsSelected) {
        this.mIsSelected = mIsSelected;
        init();
    }

    /**
     * 设置未选中状态文字
     */
    public void setUnselectText(String mUnselectedText) {
        this.mUnselectedText = mUnselectedText;
    }

    /**
     * 设置选中跟未选中文字
     */
    public void setBothText(String text){
        mSelectedText = text;
        mUnselectedText = text;
        setText(text);
    }

    /**
     * 设置选中状态背景
     */
    public void setSelectedDrawable(@DrawableRes int mSelectedDrawable) {
        this.mSelectedDrawable = mSelectedDrawable;
    }

    /**
     * 设置未选中状态背景
     */
    public void setUnselectDrawable(@DrawableRes int mUnselectedDrawable) {
        this.mUnselectedDrawable = mUnselectedDrawable;
    }

    /**
     * 设置选中状态文字颜色
     */
    public void setSelectedTextColor(@ColorRes int mSelectedTextColor) {
        this.mSelectedTextColor = mSelectedTextColor;
    }

    /**
     * 设置未选中状态文字颜色
     */
    public void setUnselectTextColor(@ColorRes int mUnselectedTextColor) {
        this.mUnselectedTextColor = mUnselectedTextColor;
    }
}
