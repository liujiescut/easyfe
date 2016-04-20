package com.scut.easyfe.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.scut.easyfe.R;
import com.scut.easyfe.app.App;
import com.scut.easyfe.app.Constants;
import com.scut.easyfe.entity.Comment;
import com.scut.easyfe.entity.test.ToSelectItem;
import com.scut.easyfe.network.RequestBase;
import com.scut.easyfe.network.RequestListener;
import com.scut.easyfe.network.RequestManager;
import com.scut.easyfe.network.request.user.parent.RGetTeacherComment;
import com.scut.easyfe.ui.adapter.SelectItemAdapter;
import com.scut.easyfe.ui.base.BaseActivity;
import com.scut.easyfe.utils.DensityUtil;
import com.scut.easyfe.utils.OtherUtils;
import com.scut.easyfe.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentsActivity extends BaseActivity {
    private ListView mCommentListView;
    private SelectItemAdapter mAdapter;

    ArrayList<ToSelectItem> mComments = new ArrayList<>();
    private boolean mIsLoadingCloseByUser = true;
    private String mTeacherId ="";


    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_comments);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle extras = intent.getExtras();
            if (null != extras) {
                mTeacherId = extras.getString(Constants.Key.TEACHER_ID);
            }
        }
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

        mAdapter = new SelectItemAdapter(mContext, mComments);
        mAdapter.setSelectable(false);
        mCommentListView.setAdapter(mAdapter);
    }

    @Override
    protected void fetchData() {
        startLoading("获取评论中", new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(mIsLoadingCloseByUser){
                    toast("获取评论失败");
                    finish();
                }
            }
        });

        RequestManager.get().execute(new RGetTeacherComment(App.getUser().getToken(), mTeacherId), new RequestListener<List<Comment>>() {
            @Override
            public void onSuccess(RequestBase request, List<Comment> comments) {
                mIsLoadingCloseByUser = false;
                stopLoading();

                if(comments.size() == 0){
                    toast("暂无相关评论");
                    return;
                }

                for (Comment comment :
                        comments) {
                    String commentString = comment.getParent().getName().substring(0, comment.getParent().getName().length() - 1) + "*   ";
                    commentString += comment.getParent().getGender() == Constants.Identifier.FEMALE ? "女士   " : "先生   ";
                    commentString += TimeUtils.getTime(new Date(comment.getTimestamp()), "yyyy-MM-dd HH: mm");
                    commentString += "\n\n";
                    commentString += comment.getContent();
                    mComments.add(new ToSelectItem(commentString, false));
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(RequestBase request, int errorCode, String errorMsg) {
                toast(errorMsg);
                mIsLoadingCloseByUser = false;
                stopLoading();
            }
        });
    }

    public void onBackClick(View view){
        finish();
    }
}
