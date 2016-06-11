package com.scut.easyfe.ui.activity.order;

import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.fragment.SpecialOrderFragment;
import com.scut.easyfe.utils.OtherUtils;

public class SpreadActivity extends BaseActivity {

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_spread);
    }

    @Override
    protected void initView() {
        ((TextView) OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("特价订单");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.spread_fl_container, new SpecialOrderFragment())
                .commit();

        ((View)OtherUtils.findViewById(this, R.id.titlebar_iv_right_icon)).setVisibility(View.VISIBLE);
    }

    public void onRightIconClick(View view){
        if(OtherUtils.isFastDoubleClick()){
            return;
        }

        redirectToActivity(this, SearchSpreadActivity.class);
    }

    public void onBackClick(View view){
        finish();
    }
}
