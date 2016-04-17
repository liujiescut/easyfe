package com.scut.easyfe.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.scut.easyfe.R;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.ui.adapter.OrderPagerAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.OtherUtils;

/**
 * 我的订单页面
 *
 * @author jay
 */
public class MyOrderActivity extends BaseActivity {

    private final int[] titles = {R.string.order_all, R.string.order_reservation,
            R.string.order_to_do, R.string.order_completed, R.string.order_invalid};
    private final int[] types = {Constants.Identifier.ORDER_ALL, Constants.Identifier.ORDER_RESERVATION,
            Constants.Identifier.ORDER_TO_DO, Constants.Identifier.ORDER_COMPLETED, Constants.Identifier.ORDER_INVALID};

    private final int BUTTON_TYPE_NONE = 0;
    private final int BUTTON_TYPE_BOTH = 1;
    private final int BUTTON_TYPE_ONE_MODIFY = 2;
    private final int BUTTON_TYPE_ONE_CANCEL = 3;
    private int mButtonType = BUTTON_TYPE_BOTH;

    private LinearLayout mBtnLinearLayout;
    private TextView mModifyTextView;
    private TextView mCancelTextView;
    private ViewPager mViewPager;
    private OrderPagerAdapter mPagerAdapter;
    private int mCurrentOrderType = Constants.Identifier.ORDER_TO_DO;
    private int mSelectedPage = 2;

    private View.OnClickListener mModifyListener;
    private View.OnClickListener mDoModifyListener;
    private View.OnClickListener mCancelListener;
    private View.OnClickListener mDoCancelListener;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_my_order);
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("我的订单");

        mBtnLinearLayout = OtherUtils.findViewById(this, R.id.my_order_ll_buttons);
        mModifyTextView = OtherUtils.findViewById(this, R.id.my_order_tv_button_1);
        mCancelTextView = OtherUtils.findViewById(this, R.id.my_order_tv_button_2);

        mPagerAdapter = new OrderPagerAdapter(getSupportFragmentManager(), titles, types);
        mViewPager = (ViewPager) findViewById(R.id.my_order_viewpager);
        mViewPager.setAdapter(mPagerAdapter);
        PagerSlidingTabStrip mTabs = (PagerSlidingTabStrip) findViewById(R.id.my_order_tabs);
        if(null!= mTabs) {
            mTabs.setTextColor(mResources.getColor(R.color.text_area_text_color));
            mTabs.setDividerColor(mResources.getColor(R.color.transparent));
            mTabs.setIndicatorHeight(8);
            mTabs.setIndicatorColor(mResources.getColor(R.color.theme_color));
            mTabs.setUnderlineHeight(0);
            mTabs.setTabPaddingLeftRight(6);
            mTabs.setShouldExpand(true);
            mTabs.setViewPager(mViewPager);
        }

        mViewPager.setCurrentItem(mSelectedPage);
    }

    @Override
    protected void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mSelectedPage = position;
                mCurrentOrderType = types[position];
                refreshButtonsState(getButtonTypeFromOrderType(mCurrentOrderType));
                mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_NORMAL);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mModifyListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshButtonsState(BUTTON_TYPE_ONE_MODIFY);
                switch (mCurrentOrderType) {
                    case Constants.Identifier.ORDER_RESERVATION:
                        if(mPagerAdapter.getItem(mSelectedPage).getState() == Constants.Identifier.STATE_NORMAL){
                            mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_EDIT);
                        }else{
                            mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_NORMAL);
                        }
                        break;

                    case Constants.Identifier.ORDER_TO_DO:
                        if(mPagerAdapter.getItem(mSelectedPage).getState() == Constants.Identifier.STATE_NORMAL){
                            mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_EDIT);
                        }else{
                            mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_NORMAL);
                        }
                        break;

                    default:
                        break;
                }
            }
        };

        mCancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshButtonsState(BUTTON_TYPE_ONE_CANCEL);
                switch (mCurrentOrderType) {
                    case Constants.Identifier.ORDER_RESERVATION:
                        if(mPagerAdapter.getItem(mSelectedPage).getState() == Constants.Identifier.STATE_NORMAL){
                            mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_EDIT);
                        }else{
                            mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_NORMAL);
                        }
                        break;

                    case Constants.Identifier.ORDER_TO_DO:
                        if(mPagerAdapter.getItem(mSelectedPage).getState() == Constants.Identifier.STATE_NORMAL){
                            mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_EDIT);
                        }else{
                            mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_NORMAL);
                        }
                        break;

                    default:
                        break;
                }

            }
        };

        mDoModifyListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("修改成功");
                mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_NORMAL);
                refreshButtonsState(BUTTON_TYPE_BOTH);
            }
        };

        mDoCancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("取消成功");
                mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_NORMAL);
                refreshButtonsState(BUTTON_TYPE_BOTH);
            }
        };

        refreshButtonsState(getButtonTypeFromOrderType(mCurrentOrderType));
    }

    /**
     * 根据 Order 的 type 来获取按钮的显示状态
     */
    private int getButtonTypeFromOrderType(int orderType){
        if(orderType == Constants.Identifier.ORDER_RESERVATION ||
                orderType == Constants.Identifier.ORDER_TO_DO){
            return BUTTON_TYPE_BOTH;
        }

        return BUTTON_TYPE_NONE;
    }
    /**
     * 根据当前显示订单类型修改底部按钮显示
     *
     * @param buttonType 订单类型
     */
    private void refreshButtonsState(int buttonType) {
        switch (buttonType) {
            case BUTTON_TYPE_BOTH:
                mBtnLinearLayout.setVisibility(View.VISIBLE);
                mModifyTextView.setVisibility(View.VISIBLE);
                mCancelTextView.setVisibility(View.VISIBLE);
                mModifyTextView.setOnClickListener(mModifyListener);
                mCancelTextView.setOnClickListener(mCancelListener);
                break;

            case BUTTON_TYPE_NONE:
                mBtnLinearLayout.setVisibility(View.GONE);
                mModifyTextView.setVisibility(View.GONE);
                mCancelTextView.setVisibility(View.GONE);
                break;

            case BUTTON_TYPE_ONE_MODIFY:
                mBtnLinearLayout.setVisibility(View.VISIBLE);
                mModifyTextView.setVisibility(View.VISIBLE);
                mCancelTextView.setVisibility(View.GONE);
                mModifyTextView.setOnClickListener(mDoModifyListener);
                break;

            case BUTTON_TYPE_ONE_CANCEL:
                mBtnLinearLayout.setVisibility(View.VISIBLE);
                mModifyTextView.setVisibility(View.GONE);
                mCancelTextView.setVisibility(View.VISIBLE);
                mCancelTextView.setOnClickListener(mDoCancelListener);
                break;

            default:
                mBtnLinearLayout.setVisibility(View.GONE);
                break;
        }
    }

    public void onBackClick(View view){
        finish();
    }

}
