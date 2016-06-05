package com.scut.easyfe.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.fragment.SpecialOrderFragment;
import com.scut.easyfe.utils.OtherUtils;

public class SearchSpecialOrderActivity extends BaseActivity {

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_search_special_order);
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("特价订单");
        SpecialOrderFragment fragment = new SpecialOrderFragment();
        fragment.setShowSearchResult(true);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.search_spread_fl_container, fragment)
                .commit();

        ((View)OtherUtils.findViewById(this, R.id.titlebar_iv_right_icon)).setVisibility(View.VISIBLE);
    }

    public void onRightIconClick(View view){
        if(OtherUtils.isFastDoubleClick()){
            return;
        }

        redirectToActivity(this, SearchSpecialOrderActivity.class);
    }

    public void onBackClick(View view){
        finish();
    }
}
