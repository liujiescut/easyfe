package com.scut.easyfe.ui.activity.order;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.app.Variables;
import com.scut.easyfe.entity.PollingData;
import com.scut.easyfe.entity.order.BriefOrder;
import com.scut.easyfe.event.DataChangeEvent;
import com.scut.easyfe.event.PDHandler;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.user.parent.RParentCancelOrders;
import com.scut.easyfe.network.request.user.teacher.RTeacherCancelOrder;
import com.scut.easyfe.network.request.user.teacher.RTeacherConfirmOrder;
import com.scut.easyfe.ui.activity.MainActivity;
import com.scut.easyfe.ui.adapter.OrderPagerAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DialogUtils;
import com.scut.easyfe.utils.OtherUtils;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

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
    private TextView mSortByTimeTextView;
    private ImageView mSortByTimeImageView;
    private TextView mSortByNameTextView;
    private ImageView mSortByNameImageView;

    private ViewPager mViewPager;
    private OrderPagerAdapter mPagerAdapter;
    private List<TextView> mTabTextViews;
    private int mCurrentOrderType = Constants.Identifier.ORDER_TO_DO;
    private int mSelectedPage = 2;

    private PollingData.PollingPrivateData.NewOrderInfo newOrderInfo = new PollingData.PollingPrivateData.NewOrderInfo();

    private View.OnClickListener mModifyOrConfirmListener;
    private View.OnClickListener mDoModifyOrConfirmListener;
    private View.OnClickListener mCancelListener;
    private View.OnClickListener mDoCancelListener;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        App.get().getEventBus().register(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.get().getEventBus().unregister(mContext);
    }

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
        mSortByNameTextView = OtherUtils.findViewById(this, R.id.my_order_tv_sort_name);
        mSortByTimeTextView = OtherUtils.findViewById(this, R.id.my_order_tv_sort_time);
        mSortByNameImageView = OtherUtils.findViewById(this, R.id.my_order_iv_sort_name);
        mSortByTimeImageView = OtherUtils.findViewById(this, R.id.my_order_iv_sort_time);

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

        initTabTextView(mTabs);
        refreshUI(PDHandler.get().getLatestData());
    }

    private void initTabTextView(PagerSlidingTabStrip tab) {
        mTabTextViews = new ArrayList<>();
        try {
            ViewGroup container = (ViewGroup) tab.getChildAt(0);
            for (int i = 0; i < container.getChildCount(); i++) {
                mTabTextViews.add((TextView) container.getChildAt(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        mModifyOrConfirmListener = new View.OnClickListener() {
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

        mDoModifyOrConfirmListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPagerAdapter.getItem(mSelectedPage).setState(Constants.Identifier.STATE_NORMAL);
                refreshButtonsState(getButtonTypeFromOrderType(mCurrentOrderType));

                if (!validateOrders(mPagerAdapter.getItem(mSelectedPage).getSelectedOrders())) {
                    return;
                }

                if (App.getUser().isParent()) {
                    /** 家长修改订单 */
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.Key.ORDERS, new ArrayList<>(mPagerAdapter.getItem(mSelectedPage).getSelectedOrders()));
                    redirectToActivity(mContext, ModifyOrderActivity.class, bundle);

                } else {
                    /** 家教确认订单 */
                    doTeacherConfirmOrder();
                }
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
                    DialogUtils.makeConfirmDialog(mContext, "温馨提示", "您已经取消过订单两次,\n不能再取消订单了呦\n(完成6次订单可增加一次取消机会)");
                    return;
                }

                if (App.getUser().isParent()) {
                    /** 家长取消订单 */
                    if (mCurrentOrderType == Constants.Identifier.ORDER_TO_DO || isOrdersModify(mPagerAdapter.getItem(mSelectedPage).getSelectedOrders())) {

                        DialogUtils.makeChooseDialog(mContext, "温馨提示", "取消订单将会产生1次不良记录\n不良记录超过2次将不能再取消订单\n(完成6次订单可增加一次取消机会)\n确认取消?",
                                new DialogUtils.OnChooseListener() {
                                    @Override
                                    public void onChoose(boolean sure) {
                                        if (sure) {
                                            doParentCancelOrder();
                                        }
                                    }
                                });

                    } else {
                        doParentCancelOrder();
                    }

                } else {
                    /** 家教取消订单 */
                    if (mCurrentOrderType == Constants.Identifier.ORDER_RESERVATION) {
                        DialogUtils.makeChooseDialog(mContext, "温馨提示", "取消订单将会产生0.5次不良记录\n不良记录超过2次将不能再取消订单\n(完成6次订单可增加一次取消机会)\n确认取消?",
                                new DialogUtils.OnChooseListener() {
                                    @Override
                                    public void onChoose(boolean sure) {
                                        if (sure) {
                                            doTeacherCancelOrder();
                                        }
                                    }
                                });

                    } else if (mCurrentOrderType == Constants.Identifier.ORDER_MODIFIED_WAIT_CONFIRM) {
                        doTeacherCancelOrder();
                    }
                }
            }
        };

        refreshButtonsState(getButtonTypeFromOrderType(mCurrentOrderType));
    }

    /**
     * 家教取消订单
     */
    private void doTeacherCancelOrder() {
        RequestManager.get().execute(new RTeacherCancelOrder(App.getUser().getToken(), mPagerAdapter.getItem(mSelectedPage).getSelectedOrderIds()),
                new RequestListener<Integer>() {
                    @Override
                    public void onSuccess(RequestBase request, Integer badRecord) {
                        toast("取消成功");

                        if (null != mPagerAdapter) {
                            mPagerAdapter.updateAllFragment();
                        }

                        App.getUser().setBadRecord(badRecord);
                        App.getUser().save2Cache();
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        toast(errorMsg);
                    }
                });
    }

    /**
     * 家教确认订单
     */
    private void doTeacherConfirmOrder() {
        RequestManager.get().execute(new RTeacherConfirmOrder(App.getUser().getToken(), mPagerAdapter.getItem(mSelectedPage).getSelectedOrderIds()),
                new RequestListener<JSONObject>() {
                    @Override
                    public void onSuccess(RequestBase request, JSONObject result) {
                        toast(result.optString("message"));

                        if (null != mPagerAdapter) {
                            mPagerAdapter.updateAllFragment();
                        }
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        toast(errorMsg);
                    }
                });
    }

    /**
     * 家长取消订单
     */
    private void doParentCancelOrder() {
        RequestManager.get().execute(new RParentCancelOrders(App.getUser().getToken(), mPagerAdapter.getItem(mSelectedPage).getSelectedOrderIds()),
                new RequestListener<Integer>() {
                    @Override
                    public void onSuccess(RequestBase request, Integer result) {
                        toast("取消成功");

                        if (null != mPagerAdapter) {
                            mPagerAdapter.updateAllFragment();
                        }

                        App.getUser().setBadRecord(result);
                        App.getUser().save2Cache();
                    }

                    @Override
                    public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                        toast(errorMsg);
                    }
                });
    }

    /**
     * 是否有部分订单被修改过
     *
     * @param orders 待确认订单
     */
    private boolean isOrdersModify(List<BriefOrder> orders) {
        for (BriefOrder order :
                orders) {
            if (order.getState() == Constants.Identifier.ORDER_MODIFIED_WAIT_CONFIRM) {
                return true;
            }
        }
        return false;
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
        }

        return true;
    }

    /**
     * 根据 Order 的 type 来获取按钮的显示状态
     */
    private int getButtonTypeFromOrderType(int orderType) {
        if (orderType == Constants.Identifier.ORDER_RESERVATION) {
            return BUTTON_TYPE_BOTH;
        }

        if (App.getUser().isParent() && orderType == Constants.Identifier.ORDER_TO_DO) {
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
                mModifyTextView.setOnClickListener(mModifyOrConfirmListener);
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
                mModifyTextView.setOnClickListener(mDoModifyOrConfirmListener);
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

        mModifyTextView.setText(App.getUser().isParent() ? "修改" : "确定");
    }

    public void onSortByNameClick(View view) {
        mSortByNameImageView.setVisibility(View.VISIBLE);
        mSortByTimeImageView.setVisibility(View.INVISIBLE);
        mSortByNameTextView.setTextColor(getResources().getColor(R.color.theme_color));
        mSortByTimeTextView.setTextColor(getResources().getColor(R.color.text_area_text_color));

        if (null != mPagerAdapter) {
            mPagerAdapter.setSortWay(Constants.Identifier.SORT_BY_NAME);
        }
    }

    public void onSortByTimeClick(View view) {
        mSortByNameImageView.setVisibility(View.INVISIBLE);
        mSortByTimeImageView.setVisibility(View.VISIBLE);
        mSortByNameTextView.setTextColor(getResources().getColor(R.color.text_area_text_color));
        mSortByTimeTextView.setTextColor(getResources().getColor(R.color.theme_color));

        if (null != mPagerAdapter) {
            mPagerAdapter.setSortWay(Constants.Identifier.SORT_BY_TIME);
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

    @Subscribe
    public void onEvent(DataChangeEvent event) {
        if (null != event) {
            refreshUI(event.getData());
        }
    }

    private void refreshUI(PollingData data) {
        newOrderInfo = Variables.localData.getMine().getNewOrderInfo(data.getMine());

        for (int i = 0; i < mTabTextViews.size() && i < newOrderInfo.state.length; i++) {
            mTabTextViews.get(i).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, newOrderInfo.state[i] == 1 ? R.mipmap.icon_red_point_padding : 0, 0);
            mTabTextViews.get(i).setCompoundDrawablePadding(16);
        }

        if (null != mPagerAdapter && null != mPagerAdapter.getItem(mSelectedPage)) {
            mPagerAdapter.getItem(mSelectedPage).updateData();
        }
    }
}
