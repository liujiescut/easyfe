package com.scut.easyfe.ui.base;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.utils.LogUtils;

/**
 * Created by chuxin on 9/25/15.
 **/

public abstract class BaseRefreshFragment extends BaseFragment
        implements AdapterView.OnItemClickListener {

    protected ListView mDataListView;
    protected BaseListViewScrollStateAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private ImageView mTipsImageView;
    private TextView mTipsTextView;
    private LinearLayout mTipsArea;
    protected LinearLayout mHeadTab;
    boolean isLoading = false;

    boolean isScroll2Bottom = false;
    boolean isHideToolBar = false;

    float startY = 0;
    float endY = 0;

    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.fragment_refresh_list;
    }

    @Override
    protected void initView(View v) {
        mDataListView = (ListView) v.findViewById(R.id.lv_data);
        mRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.srl_refresh);
        mTipsImageView = (ImageView) v.findViewById(R.id.iv_tips);
        mTipsTextView = (TextView) v.findViewById(R.id.tv_tips);
        mTipsArea = (LinearLayout) v.findViewById(R.id.ll_tips);
        mHeadTab = (LinearLayout) v.findViewById(R.id.ll_header_tab);

        // Set progress bar's colors.
        TypedArray typedArray = getResources().obtainTypedArray(R.array.swipe_refresh_progress_colors);
        int[] colors = new int[typedArray.length()];
        for (int i = 0; i < typedArray.length(); ++i) {
            colors[i] = typedArray.getColor(i, getResources().getColor(R.color.primary));
        }
        typedArray.recycle();
        mRefreshLayout.setColorSchemeColors(colors);
    }

    @Override
    protected void initListener(View v) {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoading = true;
                onRefreshData();
            }
        });

        mDataListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mAdapter != null) {
                    if (scrollState == SCROLL_STATE_IDLE) {
                        mAdapter.ListViewStopScrolling();
                    } else {
                        mAdapter.ListViewScrolling();
                    }
                    if (!isLoading && scrollState == SCROLL_STATE_IDLE && isScroll2Bottom &&
                            mDataListView.getLastVisiblePosition() - mDataListView.getHeaderViewsCount() == mAdapter.getCount() - 1) {
                        isLoading = true;
                        isScroll2Bottom = false;
                        onLoadingData();

                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        mDataListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        endY = event.getY();
                        if (startY - endY > 300) {
                            isScroll2Bottom = true;
                        } else {
                            isScroll2Bottom = false;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        LogUtils.i("startY - event.getY() " + (startY - event.getY()));
//                        LogUtils.i("mDataListView.getFirstVisiblePosition() " + mDataListView.getFirstVisiblePosition());
//                        LogUtils.i("isHideToolBar " + isHideToolBar);
                        if (startY - event.getY() > 0 && mDataListView.getFirstVisiblePosition() > 1 && !isHideToolBar) {
                            hideToolBar();
                            isHideToolBar = true;
                        }
                        if (startY - event.getY() < 0 && isHideToolBar) {
                            showToolBar();
                            isHideToolBar = false;
                        }

                        break;
                }
                return false;
            }
        });

        mDataListView.setOnItemClickListener(this);
    }

    @Override
    protected void fetchData(View v) {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                setIsLoading(true);
                onLoadingData();
            }
        });
    }

    protected void setBaseAdapter(BaseListViewScrollStateAdapter adapter) {
        this.mAdapter = adapter;
        mDataListView.setAdapter(mAdapter);
    }

    protected <T> T getBaseAdapter() {
        return (T) this.mAdapter;
    }

    protected void setIsLoading(boolean flag) {
        this.isLoading = flag;
        mRefreshLayout.setRefreshing(flag);

    }

    protected ListView getListView() {
        return mDataListView;
    }

    /**
     * @param drawableId 显示的图片的 id
     * @param stringId   显示的文本的 id
     * @see BaseRefreshFragment#showTips(Drawable, String)
     */
    protected void showTips(@DrawableRes int drawableId, @StringRes int stringId) {

        if (isAdded()) {
            mTipsArea.setVisibility(View.VISIBLE);
            showTips(drawableId, getString(stringId));
        } else
            LogUtils.d("fragment not attach to activity");
    }

    /**
     * @param drawableId 显示的图片的 id
     * @param message    显示的文本
     * @see BaseRefreshFragment#showTips(Drawable, String)
     */
    protected void showTips(@DrawableRes int drawableId, String message) {
        if (isAdded())
            showTips(getResources().getDrawable(drawableId), message);
        else
            LogUtils.d("fragment not attach to activity");
    }

    /**
     * 在列表正中间显示一个提示, 含有一张图片和一段文本
     * 用于指示列表为空的操作, 或其他.
     *
     * @param drawable 显示的图片
     * @param message  显示的文本
     */
    protected void showTips(Drawable drawable, String message) {
        mTipsImageView.setImageDrawable(drawable);
        mTipsTextView.setText(message);
    }

    protected void hideTips() {
        mTipsArea.setVisibility(View.GONE);
    }

    protected SwipeRefreshLayout getRefreshLayout() {
        return this.mRefreshLayout;
    }

    /**
     * 第一次拉取数据; 当列表滚动至尾部, 也会调用此方法继续拉取其他数据
     */
    protected abstract void onLoadingData();

    /**
     * 重新拉取数据
     */
    protected abstract void onRefreshData();


    protected void hideToolBar(){}


    protected void showToolBar(){}

}
