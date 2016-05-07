package com.scut.easyfe.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.bigkoo.alertview.OnItemClickListener;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.order.BriefOrder;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.user.parent.RParentCancelOrders;
import com.scut.easyfe.ui.adapter.OrderPagerAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;
import java.util.List;

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
    protected void onResume() {
        super.onResume();
        if (null != mPagerAdapter && null != mPagerAdapter.getItem(mSelectedPage)) {
            mPagerAdapter.getItem(mSelectedPage).updateData();
        }
    }

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_my_order);
    }

    @Override
    protected void initView() {

        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("我的订单");

        mBtnLinearLayout = OtherUtils.findViewById(this, R.id.my_order_ll_buttons);
        mModifyTextView = OtherUtils.findViewById(this, R.id.my_order_tv_button_1);
        mCancelTextView = OtherUtils.findViewById(this, R.id.my_order_tv_button_2);

        mPagerAdapter = new OrderPagerAdapter(getSupportFragmentManager(), titles, types);
        mViewPager = (ViewPager) findViewById(R.id.my_order_viewpager);
        mViewPager.setAdapter(mPagerAdapter);
        PagerSlidingTabStrip mTabs = (PagerSlidingTabStrip) findViewById(R.id.my_order_tabs);
        if (null != mTabs) {
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
                    case Constants.Identifier.ORDER_TO_DO:
                        changePageState();
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
                    case Constants.Identifier.ORDER_TO_DO:
                        changePageState();
                        break;

                    default:
                        break;
                }

            }
        };

        mDoModifyListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_NORMAL);
                refreshButtonsState(getButtonTypeFromOrderType(mCurrentOrderType));

                if (!validateOrders(mPagerAdapter.getItem(mSelectedPage).getSelectedOrders())) {
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.Key.ORDERS, new ArrayList<>(mPagerAdapter.getItem(mSelectedPage).getSelectedOrders()));
                redirectToActivity(mContext, ModifyOrderActivity.class, bundle);
            }
        };

        mDoCancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_NORMAL);
                refreshButtonsState(getButtonTypeFromOrderType(mCurrentOrderType));

                if (!validateOrders(mPagerAdapter.getItem(mSelectedPage).getSelectedOrders())) {
                    return;
                }

                if (App.getUser().getBadRecord() >= Constants.DefaultValue.MAX_BAD_RECORD) {
                    DialogUtils.makeConfirmDialog(mContext, "警告", "您已经取消过订单两次,\n不能再取消订单了呦\n(完成6次订单可增加一次取消机会)");
                    return;
                }

                mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_NORMAL);
                refreshButtonsState(getButtonTypeFromOrderType(mCurrentOrderType));

                DialogUtils.makeChooseDialog(mContext, "提醒", "取消订单将会产生一次不良记录\n不良记录超过两次将不能再取消订单\n(完成6次订单可增加一次取消机会)\n确认取消?",
                        new DialogUtils.OnChooseListener() {
                            @Override
                            public void onChoose(boolean sure) {
                                if(sure){
                                    doCancelOrder();
                                }
                            }
                        });
            }
        };

        refreshButtonsState(getButtonTypeFromOrderType(mCurrentOrderType));
    }

    private void doCancelOrder(){
        RequestManager.get().execute(new RParentCancelOrders(App.getUser().getToken(), mPagerAdapter.getItem(mSelectedPage).getSelectedOrderIds()),
                new RequestListener<Integer>() {
                    @Override
                    public void onSuccess(RequestBase request, Integer result) {
                        toast("取消成功");

                        if (null != mPagerAdapter.getItem(mSelectedPage)) {
                            mPagerAdapter.getItem(mSelectedPage).updateData();
                        }

                        App.getUser().setBadRecord(result);
                        App.getUser().save2Cache();

                        redirectToActivity(mContext, MyOrderActivity.class);
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        toast(errorMsg);
                    }
                });
    }

    private boolean validateOrders(List<BriefOrder> orders) {
        if (orders.size() == 0) {
            toast("请选择要修改的订单");
            return false;
        }

        String tag = orders.get(0).getTag();

        for (BriefOrder order :
                orders) {
            if (!tag.equals(order.getTag())) {
                toast("只有同次多次预约的订单可批量修改");
                return false;
            }

            if (!App.getUser().get_id().equals(order.getParent())) {
                toast("您只能修改自己是家长的订单");
                return false;
            }
        }

        return true;
    }

    /**
     * 根据 Order 的 type 来获取按钮的显示状态
     */
    private int getButtonTypeFromOrderType(int orderType) {
        if (!App.getUser().isParent()) {
            return BUTTON_TYPE_NONE;
        }

        if (orderType == Constants.Identifier.ORDER_RESERVATION ||
                orderType == Constants.Identifier.ORDER_TO_DO) {
            return BUTTON_TYPE_BOTH;
        }

        return BUTTON_TYPE_NONE;
    }

    /**
     * 更换当前页面状态（编辑状态还是普通状态）
     */
    private void changePageState() {
        if (mPagerAdapter.getItem(mSelectedPage).getState() == Constants.Identifier.STATE_NORMAL) {
            mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_EDIT);
        } else {
            mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_NORMAL);
        }
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

    public void onBackClick(View view) {
        redirectToActivity(mContext, MainActivity.class);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (null != mPagerAdapter.getItem(mSelectedPage) && mPagerAdapter.getItem(mSelectedPage).getState() == Constants.Identifier.STATE_EDIT) {
            mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_NORMAL);
            refreshButtonsState(BUTTON_TYPE_BOTH);
            return;
        }

        onBackClick(null);
    }
}
