package com.scut.easyfe.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.ui.fragment.SearchResultFragment;

public class SearchResultActivity extends BaseActivity {

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_search_result);
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.titlebar_tv_title)).setText("筛选结果");
        SearchResultFragment fragment = new SearchResultFragment();
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.search_result_fl_container, fragment).
                commit();
    }

    public void onBackClick(View view){
        finish();
    }

}
