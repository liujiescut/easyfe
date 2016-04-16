package com.scut.easyfe.ui.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.customView.CircleIndicator;
import com.scut.easyfe.ui.customView.ScrollableViewPager;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lex Luther
 *         <p>
 *         第一次运行程序时的介绍和展示
 */
public class IntroActivity extends BaseActivity {
    private ScrollableViewPager mViewPager;
    private CircleIndicator mIndicator;
    private IntroPagerAdapter mIntroPagerAdapter;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_intro);
    }

    @Override
    protected void initView() {
        super.initView();
        mViewPager = OtherUtils.findViewById(this, R.id.intro_vp);
        mIndicator = OtherUtils.findViewById(this, R.id.intro_indicator);

        /** 展示页所用图片的 ID 们 */
        List<Integer> imageResourceIds = new ArrayList<>();

        imageResourceIds.add(R.mipmap.image_splash_1);
        imageResourceIds.add(R.mipmap.image_splash_2);
        imageResourceIds.add(R.mipmap.image_splash_3);

        mIntroPagerAdapter = new IntroPagerAdapter(imageResourceIds);

        mViewPager.setAdapter(mIntroPagerAdapter);
//        mViewPager.setPageTransformer(true,new DepthPageTransformer());
        mIndicator.setViewPager(mViewPager);
    }

    public void enter() {
        redirectToActivity(this, MainActivity.class);
        finish();
    }

    private class IntroPagerAdapter extends PagerAdapter {

        private List<Integer> mImageResourceIds;

        public IntroPagerAdapter(List<Integer> imageResourceIds) {
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
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setBackgroundColor(getResources().getColor(android.R.color.white));
            imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
            imageView.setImageResource(mImageResourceIds.get(position));
            container.addView(imageView);

            if(position == getCount() - 1) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        enter();
                    }
                });
            }

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onBackPressed() {
        enter();
    }
}
