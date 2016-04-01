package com.scut.easyfe.ui.activity;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.entity.ToSelectItem;
import com.scut.easyfe.ui.adapter.SelectItemAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DensityUtil;
import com.scut.easyfe.utils.OtherUtils;

import java.util.ArrayList;

public class CommentsActivity extends BaseActivity {
    private ListView mCommentListView;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_comments);
    }

    @Override
    protected void initView() {
        ((TextView)OtherUtils.findViewById(this, R.id.titlebar_tv_title)).setText("家长评语");

        mCommentListView = OtherUtils.findViewById(this, R.id.comments_lv_container);
        mCommentListView.setDividerHeight(DensityUtil.dip2px(this, 5));
        mCommentListView.setHeaderDividersEnabled(true);

        View headView= new View(this);
        headView.setBackground(null);
        mCommentListView.addHeaderView(headView);

        ArrayList<ToSelectItem> comments = new ArrayList<>();
        for(int i = 0; i < 25; i++){
            String comment = "\n杨 * 杰 先生  2016-3-8  5：20 \n\n习惯好评   " + i +"\n";
            comments.add(new ToSelectItem(comment, false));
        }
        SelectItemAdapter adapter = new SelectItemAdapter(mContext, comments);
        adapter.setSelectable(false);
        mCommentListView.setAdapter(adapter);
    }

    public void onBackClick(View view){
        finish();
    }
}
