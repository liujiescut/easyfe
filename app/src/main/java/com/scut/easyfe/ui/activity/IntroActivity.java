package com.scut.easyfe.ui.activity;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.adapter.ImagePagerAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.customView.CircleIndicator;
import com.scut.easyfe.ui.customView.ScrollableViewPager;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 第一次运行程序时的介绍和展示
 */
public class IntroActivity extends BaseActivity {
    private ScrollableViewPager mViewPager;
    private CircleIndicator mIndicator;
    private ImagePagerAdapter mIntroPagerAdapter;

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

        mIntroPagerAdapter = new ImagePagerAdapter(imageResourceIds);

        mViewPager.setAdapter(mIntroPagerAdapter);
//        mViewPager.setPageTransformer(true,new DepthPageTransformer());
        mIndicator.setViewPager(mViewPager);
    }

    @Override
    protected void initListener() {
        mIntroPagerAdapter.setOnItemClickListener(new ImagePagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(position == mIntroPagerAdapter.getCount() - 1){
                    enter();
                }
            }
        });
    }

    public void enter() {
        redirectToActivity(this, MainActivity.class);
        finish();
    }

    @Override
    public void onBackPressed() {
        enter();
    }
}
